/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.AptosEndpoints
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PageRequest

class ConfidentialTransportTest {
  @Test
  fun viewsDecryptBalancesAndExposeTypedStatus() = runTest {
    val key = ConfidentialDecryptionKey.generate()
    val available =
      assertIs<AptosResult.Success<EncryptedChunks>>(
        encryptChunks(key.encryptionKey, listOf(42u) + List(7) { 0u })
      ).value.ciphertexts
    val pending =
      assertIs<AptosResult.Success<EncryptedChunks>>(
        encryptChunks(key.encryptionKey, listOf(7u) + List(7) { 0u })
      ).value.ciphertexts
    var balanceCalls = 0
    val client = client { request ->
      val body = (request.body as TextContent).text
      val response =
        when {
          "get_available_balance" in body -> {
            balanceCalls += 1
            "[${ciphertextJson(available)}]"
          }
          "get_pending_balance" in body -> {
            balanceCalls += 1
            "[${ciphertextJson(pending)}]"
          }
          "has_confidential_store" in body -> "[true]"
          "is_normalized" in body -> "[false]"
          "incoming_transfers_paused" in body -> "[true]"
          "get_effective_auditor_config" in body ->
            """[{"is_global":false,"config":{"ek":{"vec":[]},"epoch":"0"}}]"""
          else -> error("Unexpected view body: $body")
        }
      jsonResponse(response)
    }
    val service = client.confidentialAssets()
    val owner = address(1)
    val token = address(2)

    val balance =
      assertIs<AptosResult.Success<ConfidentialBalance>>(
        service.getBalance(owner, token, key, useCache = true)
      ).value
    assertEquals("42", balance.availableAmount.decimal)
    assertEquals("7", balance.pendingAmount.decimal)
    assertIs<AptosResult.Success<ConfidentialBalance>>(
      service.getBalance(owner, token, key, useCache = true)
    )
    assertEquals(2, balanceCalls)

    val status =
      assertIs<AptosResult.Success<ConfidentialAssetStatus>>(service.getStatus(owner, token)).value
    assertTrue(status.registered)
    assertTrue(!status.normalized)
    assertTrue(status.incomingTransfersPaused)
    assertNull(
      assertIs<AptosResult.Success<ConfidentialEncryptionKey?>>(
        service.getAssetAuditorEncryptionKey(token)
      ).value
    )
    client.close()
  }

  @Test
  fun activitiesReturnDomainModelsWithStableCounts() = runTest {
    val owner = address(3)
    val asset = address(4)
    val client = client { request ->
      assertEquals("indexer.example", request.url.host)
      jsonResponse(
        """
        {
          "data": {
            "confidential_asset_activities": [{
              "transaction_version":"12",
              "event_index":"1",
              "event_type":"Deposited",
              "owner_address":"$owner",
              "owner_primary_aptos_name":[{"domain":"alice","subdomain":null}],
              "asset_type":"$asset",
              "counterparty_address":null,
              "counterparty_primary_aptos_name":[],
              "amount":"99",
              "event_data":{},
              "event_data_version":"1.0.0",
              "block_height":"8",
              "is_transaction_success":true,
              "entry_function_id_str":"0x1::confidential_asset::deposit",
              "transaction_timestamp":"2026-08-28T00:00:00Z"
            }],
            "confidential_asset_activities_aggregate":{"aggregate":{"count":3}}
          }
        }
        """.trimIndent()
      )
    }

    val page =
      assertIs<AptosResult.Success<xyz.mcxross.kaptos.model.AptosPage<ConfidentialAssetActivity>>>(
        client.confidentialAssets().getActivities(
          ConfidentialActivityQuery(owner = owner, page = PageRequest(offset = 0, limit = 1))
        )
      ).value
    assertEquals(3, page.totalCount)
    assertTrue(page.hasNextPage)
    assertEquals(ConfidentialActivityType.Deposited, page.items.single().type)
    assertEquals("alice.apt", page.items.single().ownerPrimaryName)
    assertEquals(99uL, page.items.single().amount)
    client.close()
  }

  private fun client(
    handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
  ): Aptos {
    val http =
      HttpClient(MockEngine(handler)) {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
      }
    return Aptos(
      AptosConfig(
        endpoints =
          AptosEndpoints(
            fullNode = "https://fullnode.example/v1",
            indexer = "https://indexer.example/v1/graphql",
          ),
        httpClient = http,
      )
    )
  }

  private fun MockRequestHandleScope.jsonResponse(content: String) =
    respond(
      content = content,
      status = HttpStatusCode.OK,
      headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
    )

  private fun ciphertextJson(values: List<ConfidentialCiphertext>): String =
    """{"P":[${values.joinToString { "{\"data\":\"${it.commitment.hex()}\"}" }}],"R":[${values.joinToString { "{\"data\":\"${it.handle.hex()}\"}" }}]}"""

  private fun ByteArray.hex(): String =
    "0x" + joinToString("") { it.toUByte().toString(16).padStart(2, '0') }

  private fun address(last: Int): AccountAddress =
    AccountAddress(ByteArray(32).also { it[31] = last.toByte() })
}
