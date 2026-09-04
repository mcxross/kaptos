/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.http.content.TextContent
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.AptosEndpointConfig
import xyz.mcxross.kaptos.AptosEndpoints
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Network

class KeylessServiceTest {
  @Test
  fun pepperUsesCustomEndpointNumericExpiryAndEndpointHeaderPrecedence() = runTest {
    var requestBody: String? = null
    val client =
      jsonClient { request ->
        if (request.url.encodedPath.endsWith("/fetch")) {
          assertEquals("pepper.example", request.url.host)
          assertEquals("/keyless/pepper/v0/fetch", request.url.encodedPath)
          assertEquals("endpoint", request.headers["X-Order"])
          assertEquals("common", request.headers["X-Common"])
          assertNull(request.headers[HttpHeaders.Authorization])
          requestBody = (request.body as TextContent).text
          respondJson("""{"pepper":"${"ab".repeat(31)}"}""")
        } else {
          respondOk("{}")
        }
      }
    val aptos = clientWith(client)
    val keyless =
      aptos.keyless(
        KeylessClientConfig(
          pepperService =
            KeylessEndpointConfig(
              url = "https://pepper.example/keyless/pepper/v0",
              headers = mapOf("X-Order" to "endpoint"),
          ),
          commonHeaders = mapOf("X-Order" to "common", "X-Common" to "common"),
        )
      )

    val result = keyless.getPepper("test.jwt", fixtureEphemeral())
    val pepper = assertIs<AptosResult.Success<ByteArray>>(result).value
    assertEquals(31, pepper.size)
    val body = Json.parseToJsonElement(checkNotNull(requestBody)).jsonObject
    assertEquals(9_876_543_210L, body.getValue("exp_date_secs").jsonPrimitive.content.toLong())
    assertFalse(body.getValue("exp_date_secs").jsonPrimitive.isString)
    assertEquals("sub", body.getValue("uid_key").jsonPrimitive.content)

    keyless.close()
    aptos.close()
    // Both Aptos and KeylessService treat an injected client as caller-owned.
    assertTrue(client.get("https://still-open.example").status.value in 200..299)
    client.close()
  }

  @Test
  fun proverUsesNumericU64FieldsAndParsesProof() = runTest {
    var proverBody: String? = null
    val client =
      jsonClient { request ->
        when {
          request.url.encodedPath.contains("keyless_account::Configuration") ->
            respondJson(CONFIG_RESOURCE)
          request.url.encodedPath.contains("Groth16VerificationKey") ->
            respondJson(VK_RESOURCE)
          request.url.encodedPath.endsWith("/prove") -> {
            proverBody = (request.body as TextContent).text
            respondJson(
              """{
                "proof": {
                  "a": "${"01".repeat(32)}",
                  "b": "${"02".repeat(64)}",
                  "c": "${"03".repeat(32)}"
                },
                "training_wheels_signature": "0040${"04".repeat(64)}"
              }""".trimIndent()
            )
          }
          else -> respondOk("{}")
        }
      }
    val aptos = clientWith(client)
    val keyless =
      aptos.keyless(
        KeylessClientConfig(
          proverService = KeylessEndpointConfig("https://prover.example/keyless/prover/v0"),
          httpClient = client,
        )
      )

    val result =
      keyless.getProof(
        jwt = SHORT_JWT,
        ephemeralKeyPair = fixtureEphemeral(),
        pepper = ByteArray(31) { 7 },
      )
    val proof = assertIs<AptosResult.Success<ZeroKnowledgeSignature>>(result).value
    assertEquals(10_000_000uL, proof.expirationHorizonSecs)
    assertEquals(64, proof.proof.b.size)
    val body = Json.parseToJsonElement(checkNotNull(proverBody)).jsonObject
    assertFalse(body.getValue("exp_date_secs").jsonPrimitive.isString)
    assertFalse(body.getValue("exp_horizon_secs").jsonPrimitive.isString)
    assertEquals(9_876_543_210L, body.getValue("exp_date_secs").jsonPrimitive.content.toLong())
    assertEquals(10_000_000L, body.getValue("exp_horizon_secs").jsonPrimitive.content.toLong())

    keyless.close()
    aptos.close()
    client.close()
  }

  @Test
  fun malformedResponsesAndMissingCustomEndpointsReturnTypedFailures() = runTest {
    val client = jsonClient { respondJson("""{"pepper":"00"}""") }
    val aptos = clientWith(client)
    val malformed =
      aptos.keyless(
          KeylessClientConfig(
            pepperService = KeylessEndpointConfig("https://pepper.example"),
            httpClient = client,
          )
        )
        .use { it.getPepper("test.jwt", fixtureEphemeral()) }
    assertIs<AptosError.Crypto>(assertIs<AptosResult.Failure>(malformed).error)

    val missing =
      aptos.keyless(KeylessClientConfig(httpClient = client)).use {
        it.getPepper("test.jwt", fixtureEphemeral())
      }
    assertIs<AptosError.Validation>(assertIs<AptosResult.Failure>(missing).error)

    aptos.close()
    client.close()
  }

  private fun fixtureEphemeral(): EphemeralKeyPair =
    EphemeralKeyPair(
      privateKey = Ed25519PrivateKey(ByteArray(32) { 0x11 }),
      expiryDateSecs = 9_876_543_210uL,
      blinder = ByteArray(31),
    )

  private fun clientWith(httpClient: HttpClient): Aptos =
    Aptos(
      AptosConfig(
        network = Network.CUSTOM,
        endpoints = AptosEndpoints(fullNode = "https://fullnode.example/v1"),
        commonHeaders = mapOf(HttpHeaders.Authorization to "Bearer fullnode-secret"),
        fullNode = AptosEndpointConfig(headers = mapOf("X-Fullnode" to "yes")),
        httpClient = httpClient,
      )
    )

  private fun jsonClient(
    handler: suspend io.ktor.client.engine.mock.MockRequestHandleScope.(io.ktor.client.request.HttpRequestData) -> io.ktor.client.request.HttpResponseData
  ): HttpClient =
    HttpClient(MockEngine(handler)) {
      install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true; explicitNulls = false })
      }
    }

  private fun io.ktor.client.engine.mock.MockRequestHandleScope.respondJson(value: String) =
    respond(
      content = value,
      headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
    )

  companion object {
    private const val SHORT_JWT =
      "eyJhbGciOiJSUzI1NiIsImtpZCI6InRlc3QifQ.eyJpc3MiOiJpc3N1ZXIiLCJhdWQiOiJhdWQiLCJzdWIiOiJ1c2VyIiwiaWF0Ijo5ODc2NTQwMDAwfQ.signature"
    private val CONFIG_RESOURCE =
      """{
        "data": {
          "max_commited_epk_bytes": 93,
          "max_exp_horizon_secs": "10000000",
          "max_extra_field_bytes": 350,
          "max_iss_val_bytes": 120,
          "max_jwt_header_b64_bytes": 300,
          "training_wheels_pubkey": {"vec": []}
        }
      }""".trimIndent()
    private val VK_RESOURCE =
      """{
        "data": {
          "alpha_g1": "${"00".repeat(32)}",
          "beta_g2": "${"00".repeat(64)}",
          "delta_g2": "${"00".repeat(64)}",
          "gamma_abc_g1": ["${"00".repeat(32)}", "${"00".repeat(32)}"],
          "gamma_g2": "${"00".repeat(64)}"
        }
      }""".trimIndent()
  }
}
