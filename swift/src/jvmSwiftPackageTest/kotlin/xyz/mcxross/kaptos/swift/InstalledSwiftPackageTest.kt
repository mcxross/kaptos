/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.io.IOException
import java.net.InetSocketAddress
import java.net.URLDecoder
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.Comparator
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/** Separate JVM compilation: this fixture is neither shipped nor run by ordinary jvmTest. */
class InstalledSwiftPackageTest {
  @Test
  fun installedArchivePassesSwiftConsumerTestsAndSample() {
    check(System.getProperty("os.name").startsWith("Mac")) {
      "Swift package verification requires macOS and Xcode"
    }
    val archive = Path(requiredProperty("kaptos.swift.archive"))
    val consumer = Path(requiredProperty("kaptos.swift.consumer"))
    val logs = Files.createDirectories(Path(requiredProperty("kaptos.swift.logs")))
    val installation = Files.createTempDirectory("kaptos-swift-")
    try {
      runCommand(
        listOf("tar", "-xzf", archive.toString(), "-C", installation.toString()),
        logs.resolve("extract.log"),
      )
      val installedPackage = installation.resolve("AptosKit")
      assertTrue(Files.isRegularFile(installedPackage.resolve("Package.swift")))
      assertTrue(
        Files.isRegularFile(installedPackage.resolve("Sources/AptosKit/Conveniences.swift"))
      )
      assertTrue(Files.isRegularFile(installedPackage.resolve("LICENSE")))
      val swiftArguments =
        listOf(
          "--package-path",
          consumer.toString(),
          "--scratch-path",
          installation.resolve("consumer-build").toString(),
        )
      SwiftHttpFixture().use { fixture ->
        val environment =
          mapOf(
            "KAPTOS_SWIFT_PACKAGE" to installedPackage.toString(),
            "KAPTOS_TEST_URL" to fixture.url,
          )
        runCommand(
          listOf("swift", "test") + swiftArguments,
          logs.resolve("consumer-tests.log"),
          environment,
        )
        runCommand(
          listOf("swift", "run") + swiftArguments + "KaptosSample",
          logs.resolve("sample.log"),
          environment,
        )
        assertTrue(fixture.failures.isEmpty(), fixture.failures.joinToString("\n"))
      }
    } finally {
      Files.walk(installation).use { paths ->
        paths.sorted(Comparator.reverseOrder()).forEach(Files::delete)
      }
    }
  }
}

private fun requiredProperty(name: String): String =
  checkNotNull(System.getProperty(name)) { "Missing Gradle test property $name" }

private fun runCommand(
  command: List<String>,
  log: Path,
  environment: Map<String, String> = emptyMap(),
) {
  val process =
    ProcessBuilder(command)
      .redirectErrorStream(true)
      .redirectOutput(log.toFile())
      .apply { environment().putAll(environment) }
      .start()
  try {
    assertTrue(process.waitFor(10, TimeUnit.MINUTES), "Timed out: $command; see $log")
    assertEquals(0, process.exitValue(), "Command failed: $command; see $log")
  } finally {
    if (process.isAlive) {
      process.destroyForcibly()
      process.waitFor(10, TimeUnit.SECONDS)
    }
  }
}

private class SwiftHttpFixture : AutoCloseable {
  val failures = ConcurrentLinkedQueue<Throwable>()
  private val executor = Executors.newCachedThreadPool()
  private val server =
    HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0).apply {
      executor = this@SwiftHttpFixture.executor
      createContext("/") { exchange ->
        try {
          handle(exchange)
        } catch (_: IOException) {
          // A cancelled Swift request may close its connection before the fixture responds.
        } catch (_: InterruptedException) {
          // Closing the fixture interrupts any delayed request whose Swift task was cancelled.
          Thread.currentThread().interrupt()
        } catch (error: Throwable) {
          failures += error
          respond(exchange, buildJsonObject { put("message", error.message) }, 500)
        } finally {
          exchange.close()
        }
      }
      start()
    }
  val url: String = "http://127.0.0.1:${server.address.port}/v1"

  private fun handle(exchange: HttpExchange) {
    val path = exchange.requestURI.path
    when (exchange.requestMethod) {
      "GET" -> get(exchange, path)
      "POST" -> post(exchange, path)
      else -> error("Unexpected fixture method ${exchange.requestMethod}")
    }
  }

  private fun get(exchange: HttpExchange, path: String) {
    val response =
      when {
        path.trimEnd('/') == "/v1" ->
          buildJsonObject {
            put("chain_id", 4)
            put("epoch", "1")
            put("ledger_version", "1")
            put("oldest_ledger_version", "0")
            put("ledger_timestamp", "1000000")
            put("node_role", "full_node")
            put("oldest_block_height", "0")
            put("block_height", "1")
          }
        "estimate_gas_price" in path ->
          buildJsonObject {
            put("gas_estimate", 100)
            put("deprioritized_gas_estimate", 80)
            put("prioritized_gas_estimate", 120)
          }
        "/module/test" in path -> testModule()
        "/balance/" in path -> JsonPrimitive(ULong.MAX_VALUE.toString())
        "/transactions/by_hash/" in path -> committed(path.substringAfterLast('/'))
        "/accounts/" in path -> {
          val address = path.substringAfterLast('/').removePrefix("0x").toBigInteger(16)
          if (address == 0x404.toBigInteger()) {
            respond(
              exchange,
              buildJsonObject {
                put("message", "fixture rejection")
                put("error_code", "sequence_number_too_old")
                put("vm_error_code", 42)
              },
              400,
            )
            return
          }
          if (address == 2.toBigInteger()) Thread.sleep(3_000)
          buildJsonObject {
            put(
              "sequence_number",
              if (address == 10.toBigInteger()) ULong.MAX_VALUE.toString() else "7",
            )
            put("authentication_key", "0x" + "11".repeat(32))
          }
        }
        else -> error("Unexpected fixture GET $path")
      }
    respond(exchange, response)
  }

  private fun post(exchange: HttpExchange, path: String) {
    val body = exchange.requestBody.use { it.readBytes() }
    val ledgerVersion =
      exchange.requestURI.rawQuery
        ?.split('&')
        ?.map { it.split('=', limit = 2) }
        ?.firstOrNull { it[0] == "ledger_version" }
        ?.getOrNull(1)
        ?.let { URLDecoder.decode(it, UTF_8) } ?: "none"
    when {
      path == "/v1/view" -> respond(exchange, JsonArray(listOf(JsonPrimitive(ledgerVersion))))
      "/tables/" in path -> respond(exchange, JsonPrimitive(ledgerVersion))
      "/transactions/simulate" in path ->
        respond(exchange, JsonArray(listOf(committed("0x" + "11".repeat(32)))))
      path.trimEnd('/') == "/v1/transactions" -> {
        fun sha3(bytes: ByteArray): ByteArray = MessageDigest.getInstance("SHA3-256").digest(bytes)
        val hash =
          "0x" +
            sha3(sha3("APTOS::Transaction".toByteArray(UTF_8)) + byteArrayOf(0) + body)
              .joinToString("") { it.toUByte().toString(16).padStart(2, '0') }
        respond(exchange, pending(hash), 202)
      }
      else -> error("Unexpected fixture POST $path")
    }
  }

  override fun close() {
    server.stop(0)
    executor.shutdownNow()
    executor.awaitTermination(5, TimeUnit.SECONDS)
  }
}

private fun pending(hash: String): JsonObject = buildJsonObject {
  put("type", "pending_transaction")
  put("hash", hash)
  put("sender", "0x1")
  put("sequence_number", "7")
  put("max_gas_amount", "2000000")
  put("gas_unit_price", "100")
  put("expiration_timestamp_secs", "9999999999")
}

private fun committed(hash: String): JsonObject = buildJsonObject {
  pending(hash).forEach { (key, value) -> put(key, value) }
  put("type", "user_transaction")
  put("version", "1")
  put("state_change_hash", hash)
  put("event_root_hash", hash)
  put("state_checkpoint_hash", JsonNull)
  put("gas_used", "2")
  put("success", true)
  put("vm_status", "Executed successfully")
  put("accumulator_root_hash", hash)
  put("events", JsonArray(emptyList()))
  put("timestamp", "1")
}

private fun testModule(): JsonObject = buildJsonObject {
  put("bytecode", "0x")
  put(
    "abi",
    buildJsonObject {
      put("address", "0x1")
      put("name", "test")
      put("friends", JsonArray(emptyList()))
      put("structs", JsonArray(emptyList()))
      put(
        "exposed_functions",
        JsonArray(
          listOf(
            buildJsonObject {
              put("name", "transfer")
              put("visibility", "public")
              put("is_entry", true)
              put("is_view", false)
              put("generic_type_params", JsonArray(emptyList()))
              put("params", JsonArray(listOf("&signer", "address", "u64").map(::JsonPrimitive)))
              put("return", JsonArray(emptyList()))
            },
            buildJsonObject {
              put("name", "read")
              put("visibility", "public")
              put("is_entry", false)
              put("is_view", true)
              put("generic_type_params", JsonArray(emptyList()))
              put("params", JsonArray(emptyList()))
              put("return", JsonArray(listOf(JsonPrimitive("u64"))))
            },
          )
        ),
      )
    },
  )
}

private fun respond(exchange: HttpExchange, response: JsonElement, status: Int = 200) {
  val bytes = response.toString().toByteArray(UTF_8)
  exchange.responseHeaders.set("Content-Type", "application/json")
  exchange.sendResponseHeaders(status, bytes.size.toLong())
  exchange.responseBody.use { it.write(bytes) }
}
