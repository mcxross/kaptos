@file:OptIn(org.jetbrains.kotlin.gradle.swiftexport.ExperimentalSwiftExportDsl::class)

import java.io.OutputStream
import java.security.DigestInputStream
import java.security.MessageDigest
import org.jetbrains.kotlin.gradle.plugin.mpp.export.SwiftExportVisibility
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins { alias(libs.plugins.kotlin.multiplatform) }

kotlin {
  jvm {
    testRuns["test"].executionTask.configure { useJUnitPlatform() }
    val mainCompilation = compilations.getByName("main")
    compilations.create("swiftPackageTest") {
      associateWith(mainCompilation)
      defaultSourceSet.dependencies { implementation(libs.kotlin.test.junit5) }
    }
  }
  iosArm64()
  iosSimulatorArm64()
  iosX64()
  macosArm64()

  export {
    swift {
      moduleName = "AptosKit"
      rootPackage = "xyz.mcxross.kaptos.swift"
      xcodeIntegration {
        configure(libs.ktor.client.core, SwiftExportVisibility.HIDDEN)
        configure(libs.kotlinx.serialization.core, SwiftExportVisibility.HIDDEN)
      }
      swiftPackageIntegration {
        outputDirectory.set(layout.buildDirectory.dir("swift-package"))
        configure(libs.ktor.client.core, SwiftExportVisibility.HIDDEN)
        configure(libs.kotlinx.serialization.core, SwiftExportVisibility.HIDDEN)
      }
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(project(":kaptos"))
      implementation(project(":kaptos-encrypted-transactions"))
      implementation(project(":kaptos-confidential-assets"))
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.serialization.core)
      implementation(libs.ktor.serialization.kotlinx.json)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(libs.kotlinx.coroutines.test)
    }
    jvmTest.dependencies { implementation(libs.kotlin.test.junit5) }
  }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

// The generated export task owns its destination. Assemble distributions directly from its output
// and the handwritten overlay so these tasks never overwrite each other's files.
val swiftOverlay = fileTree("overlay") { include("*.swift") }
val swiftPackageArchives =
  listOf("Debug", "Release").associateWith { configuration ->
    tasks.register<Tar>("archive${configuration}SwiftPackage") {
      group = "distribution"
      description = "Assembles the self-contained $configuration Swift package archive."
      dependsOn("export${configuration}SwiftPackage")
      archiveFileName.set("AptosKit-Swift-${project.version}-$configuration.tar.gz")
      destinationDirectory.set(rootProject.layout.buildDirectory.dir("distributions"))
      compression = Compression.GZIP
      isPreserveFileTimestamps = false
      isReproducibleFileOrder = true
      duplicatesStrategy = DuplicatesStrategy.FAIL
      into("AptosKit") {
        from(layout.buildDirectory.dir("swift-package/$configuration")) {
          exclude("**/.build/**", "**/.swiftpm/**", "LICENSE")
          // Ignore a stale overlay left by an older packaging command; use the source below.
          exclude(swiftOverlay.files.map { "Sources/AptosKit/${it.name}" })
        }
        from(swiftOverlay) { into("Sources/AptosKit") }
        from(rootProject.layout.projectDirectory.file("LICENSE"))
      }
    }
  }

val swiftPackageTestCompilation =
  kotlin.targets.getByName<KotlinJvmTarget>("jvm").compilations.getByName("swiftPackageTest")
val swiftConsumerProject = rootProject.layout.projectDirectory.dir("sample/swiftApp")

fun registerInstalledPackageCheck(name: String, configuration: String) =
  tasks.register<Test>(name) {
    group = "verification"
    description = "Installs the $configuration Swift archive and runs consumer tests and sample."
    val archive = swiftPackageArchives.getValue(configuration)
    dependsOn(archive)
    testClassesDirs = swiftPackageTestCompilation.output.classesDirs
    classpath =
      swiftPackageTestCompilation.output.allOutputs +
        swiftPackageTestCompilation.runtimeDependencyFiles
    useJUnitPlatform()
    filter { includeTestsMatching("xyz.mcxross.kaptos.swift.InstalledSwiftPackageTest") }
    inputs.file(archive.flatMap { it.archiveFile })
    inputs.files(
      fileTree(swiftConsumerProject) {
        include("Package.swift", "Sources/**", "Tests/**")
      }
    )
    systemProperty(
      "kaptos.swift.archive",
      archive.get().archiveFile.get().asFile.absolutePath,
    )
    systemProperty("kaptos.swift.consumer", swiftConsumerProject.asFile.absolutePath)
    systemProperty(
      "kaptos.swift.logs",
      layout.buildDirectory.dir("reports/swift-package/$configuration").get().asFile.absolutePath,
    )
    // Swift/Xcode are external tools whose state is not represented by JVM test inputs.
    outputs.upToDateWhen { false }
  }

registerInstalledPackageCheck("checkSwiftPackage", "Debug")

registerInstalledPackageCheck("checkReleaseSwiftPackage", "Release")

val swiftSdkVersion = project.version.toString()
val swiftSourceRevision =
  providers.exec { commandLine("git", "rev-parse", "HEAD") }.standardOutput.asText.map { it.trim() }
val swiftSnapshot = swiftSdkVersion.endsWith("-SNAPSHOT")
val swiftSourceTimestamp =
  providers
    .exec { commandLine("git", "show", "-s", "--format=%ct", "HEAD") }
    .standardOutput
    .asText
    .map { it.trim() }
// Maven snapshots can be replaced. SwiftPM tags and binary URLs must identify one source commit.
val swiftReleaseVersion =
  if (swiftSnapshot)
    "$swiftSdkVersion.${swiftSourceTimestamp.get()}.g${swiftSourceRevision.get().take(12)}"
  else swiftSdkVersion
val swiftReleaseChannel = if (swiftSnapshot) "snapshot" else "stable"
val swiftDistributionBranch = if (swiftSnapshot) "swift-package-snapshot" else "swift-package"
val swiftReleaseTag = "v$swiftReleaseVersion"
val swiftRepository = "mcxross/kaptos"
val swiftBinaryName = "AptosKitKotlin-$swiftReleaseVersion.xcframework.zip"
val swiftBinaryUrl =
  "https://github.com/$swiftRepository/releases/download/$swiftReleaseTag/$swiftBinaryName"
val releaseExport = layout.buildDirectory.dir("swift-package/Release")
val swiftIdentityJson = swiftSourceRevision.map { revision ->
  """
    {"version":"$swiftReleaseVersion","sdkVersion":"$swiftSdkVersion","channel":"$swiftReleaseChannel","distributionBranch":"$swiftDistributionBranch","tag":"$swiftReleaseTag","repository":"$swiftRepository","sourceRevision":"$revision"}
    """
    .trimIndent()
}

val validateSwiftPackageVersion =
  tasks.register("validateSwiftPackageVersion") {
    group = "verification"
    description = "Validates the semantic version used for the SwiftPM tag and release assets."
    inputs.property("version", swiftReleaseVersion)
    doLast {
      require(
        Regex("(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)")
          .matches(swiftReleaseVersion.substringBefore('-'))
      ) {
        "SwiftPM requires major.minor.patch and optional prerelease identifiers, got $swiftReleaseVersion"
      }
      if ('-' in swiftReleaseVersion) {
        swiftReleaseVersion.substringAfter('-').split('.').forEach {
          require(
            Regex("[0-9A-Za-z-]+").matches(it) &&
              (it.length <= 1 || !it.all(Char::isDigit) || !it.startsWith('0'))
          ) {
            "Invalid SwiftPM prerelease identifier: $it (numeric identifiers cannot have leading zeroes)"
          }
        }
      }
    }
  }

val swiftIdentityFile = layout.buildDirectory.file("swift-distribution/identity.json")
val writeSwiftPackageReleaseIdentity =
  tasks.register("writeSwiftPackageReleaseIdentity") {
    group = "distribution"
    description = "Writes the SDK version, SwiftPM version, channel and source revision for CI."
    dependsOn(validateSwiftPackageVersion)
    inputs.property("identity", swiftIdentityJson)
    outputs.file(swiftIdentityFile)
    doLast {
      swiftIdentityFile.get().asFile.apply {
        parentFile.mkdirs()
        writeText(swiftIdentityJson.get() + "\n")
      }
    }
  }

val zipReleaseSwiftPackageBinary =
  tasks.register<Zip>("zipReleaseSwiftPackageBinary") {
    group = "distribution"
    description = "Archives the Release XCFramework for SwiftPM binary distribution."
    dependsOn("exportReleaseSwiftPackage", validateSwiftPackageVersion)
    archiveFileName.set(swiftBinaryName)
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("distributions/swift-package"))
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
    from(releaseExport.map { it.dir("AptosKitKotlin.xcframework") }) {
      into("AptosKitKotlin.xcframework")
    }
  }

val prepareSwiftPackageRelease =
  tasks.register<Sync>("prepareSwiftPackageRelease") {
    group = "distribution"
    description =
      "Prepares the source-only SwiftPM distribution with immutable binary URL/checksum."
    dependsOn(zipReleaseSwiftPackageBinary, writeSwiftPackageReleaseIdentity)
    inputs.file(zipReleaseSwiftPackageBinary.flatMap { it.archiveFile })
    inputs.property("version", swiftReleaseVersion)
    inputs.property("sourceRevision", swiftSourceRevision)
    into(layout.buildDirectory.dir("swift-distribution/package"))
    from(releaseExport) {
      exclude(
        "**/.build/**",
        "**/.swiftpm/**",
        "**/*.xcframework/**",
        "Package.swift",
        "LICENSE",
        "README.md",
      )
      exclude(swiftOverlay.files.map { "Sources/AptosKit/${it.name}" })
    }
    from(swiftOverlay) { into("Sources/AptosKit") }
    from(rootProject.layout.projectDirectory.file("LICENSE"))
    from(layout.projectDirectory.file("README.md"))
    doLast {
      val digest = MessageDigest.getInstance("SHA-256")
      zipReleaseSwiftPackageBinary.get().archiveFile.get().asFile.inputStream().use {
        DigestInputStream(it, digest).transferTo(OutputStream.nullOutputStream())
      }
      val checksum = digest.digest().joinToString("") { "%02x".format(it) }
      val manifest = releaseExport.get().file("Package.swift").asFile.readText()
      val localBinary =
        Regex(
          """\.binaryTarget\(\s*name:\s*"AptosKitKotlin",\s*path:\s*"AptosKitKotlin\.xcframework"\s*\)"""
        )
      require(localBinary.findAll(manifest).count() == 1) {
        "Expected exactly one AptosKitKotlin local binary target"
      }
      destinationDir
        .resolve("Package.swift")
        .writeText(
          localBinary.replace(
            manifest,
            ".binaryTarget(name: \"AptosKitKotlin\", url: \"$swiftBinaryUrl\", checksum: \"$checksum\")",
          )
        )
      destinationDir
        .resolve("release-metadata.json")
        .writeText(
          swiftIdentityJson.get().dropLast(1) +
            ""","binary":"$swiftBinaryName","binaryUrl":"$swiftBinaryUrl","checksum":"$checksum"}""" +
            "\n"
        )
    }
  }

val archiveSwiftPackageReleaseSources =
  tasks.register<Tar>("archiveSwiftPackageReleaseSources") {
    group = "distribution"
    description =
      "Archives the root SwiftPM manifest, sources, overlay and release provenance without binaries."
    dependsOn(prepareSwiftPackageRelease)
    from(prepareSwiftPackageRelease.map { it.destinationDir })
    archiveFileName.set("AptosKit-Swift-$swiftReleaseVersion-sources.tar.gz")
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("distributions/swift-package"))
    compression = Compression.GZIP
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
  }

tasks.register("assembleSwiftPackageRelease") {
  group = "distribution"
  description = "Assembles source-only SwiftPM distribution and versioned Release XCFramework ZIP."
  dependsOn(archiveSwiftPackageReleaseSources)
}

fun registerDistributionCheck(name: String, remote: Boolean) =
  tasks.register<Test>(name) {
    group = "verification"
    description =
      if (remote) "Verifies published SwiftPM Git/version and HTTPS binary consumption."
      else "Verifies SwiftPM Git/version and binary download using isolated local mirrors."
    testClassesDirs = swiftPackageTestCompilation.output.classesDirs
    classpath =
      swiftPackageTestCompilation.output.allOutputs +
        swiftPackageTestCompilation.runtimeDependencyFiles
    useJUnitPlatform()
    filter { includeTestsMatching("xyz.mcxross.kaptos.swift.SwiftPackageDistributionTest") }
    if (!remote) {
      dependsOn(prepareSwiftPackageRelease)
      inputs.dir(prepareSwiftPackageRelease.map { it.destinationDir })
      inputs.file(zipReleaseSwiftPackageBinary.flatMap { it.archiveFile })
      systemProperty(
        "kaptos.swift.distribution",
        prepareSwiftPackageRelease.get().destinationDir.absolutePath,
      )
      systemProperty(
        "kaptos.swift.binary",
        zipReleaseSwiftPackageBinary.get().archiveFile.get().asFile.absolutePath,
      )
    }
    inputs.files(
      fileTree(swiftConsumerProject) { include("Package.swift", "Sources/**", "Tests/**") }
    )
    systemProperty("kaptos.swift.remote", remote.toString())
    systemProperty(
      "kaptos.swift.version",
      providers.gradleProperty("swiftPackageVersion").getOrElse(swiftReleaseVersion),
    )
    systemProperty("kaptos.swift.consumer", swiftConsumerProject.asFile.absolutePath)
    systemProperty(
      "kaptos.swift.logs",
      layout.buildDirectory
        .dir("reports/swift-package/${if (remote) "Remote" else "Git"}")
        .get()
        .asFile
        .absolutePath,
    )
    outputs.upToDateWhen { false }
  }

registerDistributionCheck("checkSwiftPackageDistribution", false)

registerDistributionCheck("checkRemoteSwiftPackage", true)

tasks.named("checkSwiftPackageDistribution") { mustRunAfter("checkReleaseSwiftPackage") }
