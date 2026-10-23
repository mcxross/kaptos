/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class SwiftPackageDistributionTest {
  @Test
  fun consumesVersionedGitPackageAndDownloadedBinary() {
    check(System.getProperty("os.name").startsWith("Mac")) { "SwiftPM verification requires macOS" }
    val remote = requiredProperty("kaptos.swift.remote").toBooleanStrict()
    val version = requiredProperty("kaptos.swift.version")
    val logs = Files.createDirectories(Path(requiredProperty("kaptos.swift.logs")))
    val temporary = Files.createTempDirectory("aptoskit-spm-")
    try {
      val consumer = temporary.resolve("consumer")
      val source = Path(requiredProperty("kaptos.swift.consumer"))
      listOf("Package.swift", "Sources", "Tests").forEach {
        copyTree(source.resolve(it), consumer.resolve(it))
      }
      val binary = if (remote) null else Path(requiredProperty("kaptos.swift.binary"))
      SwiftHttpFixture(binary).use { fixture ->
        val arguments =
          listOf(
            "--package-path",
            consumer.toString(),
            "--scratch-path",
            temporary.resolve("build").toString(),
            "--cache-path",
            temporary.resolve("cache").toString(),
            "--config-path",
            temporary.resolve("config").toString(),
            "--security-path",
            temporary.resolve("security").toString(),
          )
        if (!remote) {
          val distribution = Path(requiredProperty("kaptos.swift.distribution"))
          val metadata =
            Json.parseToJsonElement(Files.readString(distribution.resolve("release-metadata.json")))
              .jsonObject
          assertEquals(version, metadata.getValue("version").jsonPrimitive.content)
          val checksumLog = logs.resolve("checksum.log")
          runCommand(listOf("swift", "package", "compute-checksum", binary.toString()), checksumLog)
          assertEquals(
            metadata.getValue("checksum").jsonPrimitive.content,
            Files.readString(checksumLog).trim(),
          )
          val repository = temporary.resolve("mirror/kaptos.git")
          Files.createDirectories(repository)
          fun git(vararg command: String) =
            runCommand(
              listOf(
                "git",
                "-C",
                repository.toString(),
                "-c",
                "user.name=AptosKit Fixture",
                "-c",
                "user.email=fixture@example.invalid",
                "-c",
                "commit.gpgsign=false",
                "-c",
                "core.hooksPath=/dev/null",
              ) + command,
              logs.resolve("git.log"),
            )
          git("init", "--initial-branch=main")
          Files.writeString(
            repository.resolve("README.md"),
            "The SDK source branch has no root Swift package.\n",
          )
          git("add", ".")
          git("commit", "-m", "SDK source branch")
          assertFalse(Files.exists(repository.resolve("Package.swift")))
          git("checkout", "--orphan", "swift-package")
          git("rm", "-f", "README.md")
          copyTree(distribution, repository)
          Files.walk(repository).use { paths ->
            assertFalse(
              paths.anyMatch {
                it.fileName.toString().endsWith(".xcframework") ||
                  it.fileName.toString().endsWith(".a")
              }
            )
          }
          git("add", ".")
          git("commit", "-m", "AptosKit $version distribution")
          git("tag", "v$version")
          git("checkout", "main")
          assertFalse(Files.exists(repository.resolve("Package.swift")))
          fun mirror(original: String, replacement: String, name: String) =
            runCommand(
              listOf("swift", "package") +
                arguments +
                listOf("config", "set-mirror", "--original", original, "--mirror", replacement),
              logs.resolve("mirror-$name.log"),
            )
          mirror("https://github.com/mcxross/kaptos.git", repository.toUri().toString(), "git")
          mirror(metadata.getValue("binaryUrl").jsonPrimitive.content, fixture.binaryUrl, "binary")
        }
        val environment =
          mapOf(
            "APTOSKIT_PACKAGE_URL" to "https://github.com/mcxross/kaptos.git",
            "APTOSKIT_PACKAGE_VERSION" to version,
            "KAPTOS_TEST_URL" to fixture.url,
          )
        // A local install override inherited from the caller must not bypass Git resolution.
        if (remote) {
          // Verify this release even when a newer compatible tag has already been published.
          runSwift(
            listOf("swift", "package") +
              arguments +
              listOf("resolve", "kaptos", "--version", version),
            logs.resolve("resolve.log"),
            environment,
          )
        }
        runSwift(
          listOf("swift", "test") + arguments,
          logs.resolve("consumer-tests.log"),
          environment,
        )
        runSwift(
          listOf("swift", "run") + arguments + "KaptosSample",
          logs.resolve("sample.log"),
          environment,
        )
        val resolved =
          Json.parseToJsonElement(Files.readString(consumer.resolve("Package.resolved"))).jsonObject
        val pin =
          resolved
            .getValue("pins")
            .jsonArray
            .single { it.jsonObject.getValue("identity").jsonPrimitive.content == "kaptos" }
            .jsonObject
        assertEquals(
          version,
          pin.getValue("state").jsonObject.getValue("version").jsonPrimitive.content,
        )
        if (!remote)
          assertTrue(
            fixture.binaryDownloads.get() > 0,
            "SwiftPM must download the mirrored binary ZIP",
          )
        assertTrue(fixture.failures.isEmpty(), fixture.failures.joinToString("\n"))
      }
    } finally {
      Files.walk(temporary).use { paths ->
        paths.sorted(Comparator.reverseOrder()).forEach(Files::delete)
      }
    }
  }
}

private fun copyTree(source: Path, destination: Path) {
  if (!Files.isDirectory(source)) {
    Files.createDirectories(destination.parent)
    Files.copy(source, destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
    return
  }
  Files.walk(source).use { paths ->
    paths.forEach { path ->
      val target = destination.resolve(source.relativize(path))
      if (Files.isDirectory(path)) Files.createDirectories(target)
      else Files.copy(path, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
    }
  }
}

private fun runSwift(command: List<String>, log: Path, environment: Map<String, String>) {
  // runCommand accepts only overrides, while this check needs to remove the local-install override.
  val process =
    ProcessBuilder(command)
      .redirectErrorStream(true)
      .redirectOutput(log.toFile())
      .apply {
        environment().putAll(environment)
        environment().remove("KAPTOS_SWIFT_PACKAGE")
      }
      .start()
  try {
    assertTrue(
      process.waitFor(10, java.util.concurrent.TimeUnit.MINUTES),
      "Timed out: $command; see $log",
    )
    assertEquals(0, process.exitValue(), "Command failed: $command; see $log")
  } finally {
    if (process.isAlive) {
      process.destroyForcibly()
      process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)
    }
  }
}
