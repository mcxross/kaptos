/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.ledger.DefaultLedgerService
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.TransportConfig

class LedgerServiceTest :
  StringSpec({
    "ledger info and blocks decode all u64 fields without signed truncation" {
      var requestIndex = 0
      val engine = MockEngine { request ->
        val content =
          when (requestIndex++) {
            0 -> {
              request.url.encodedPath shouldBe "/v1/"
              """
              {
                "chain_id": 4,
                "epoch": "18446744073709551615",
                "ledger_version": "18446744073709551615",
                "oldest_ledger_version": "0",
                "ledger_timestamp": "18446744073709551615",
                "node_role": "full_node",
                "oldest_block_height": "0",
                "block_height": "18446744073709551615",
                "encryption_key": "0x0001"
              }
              """
                .trimIndent()
            }
            1 -> {
              request.url.encodedPath shouldBe "/v1/blocks/by_version/18446744073709551615"
              blockJson()
            }
            else -> {
              request.url.encodedPath shouldBe "/v1/blocks/by_height/18446744073709551615"
              blockJson()
            }
          }
        respond(
          content = content,
          status = HttpStatusCode.OK,
          headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
      }
      val client =
        HttpClient(engine) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val config =
        TransportConfig(AptosSettings(fullNode = "https://api.example.com/v1", client = client))
      val service = DefaultLedgerService(config)

      val info =
        service
          .info()
          .shouldBeInstanceOf<AptosResult.Success<xyz.mcxross.kaptos.ledger.LedgerState>>()
          .value
      info.chainId.chainId shouldBe 4u
      info.epoch shouldBe ULong.MAX_VALUE
      info.version shouldBe ULong.MAX_VALUE
      info.timestampMicros shouldBe ULong.MAX_VALUE
      info.blockHeight shouldBe ULong.MAX_VALUE
      info.gitHash shouldBe null
      info.encryptionKey shouldBe ByteString(byteArrayOf(0, 1))

      service
        .blockAtVersion(ULong.MAX_VALUE)
        .shouldBeInstanceOf<AptosResult.Success<xyz.mcxross.kaptos.ledger.LedgerBlock>>()
        .value
        .lastVersion shouldBe ULong.MAX_VALUE
      service
        .blockAtHeight(ULong.MAX_VALUE)
        .shouldBeInstanceOf<AptosResult.Success<xyz.mcxross.kaptos.ledger.LedgerBlock>>()
        .value
        .height shouldBe ULong.MAX_VALUE

      config.close()
      client.close()
    }

    "malformed ledger wire values become typed serialization errors" {
      val engine = MockEngine {
        respond(
          content =
            """
            {
              "chain_id": 4,
              "epoch": "not-a-u64",
              "ledger_version": "1",
              "oldest_ledger_version": "0",
              "ledger_timestamp": "1",
              "node_role": "full_node",
              "oldest_block_height": "0",
              "block_height": "1"
            }
            """
              .trimIndent(),
          status = HttpStatusCode.OK,
          headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
      }
      val client =
        HttpClient(engine) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val config =
        TransportConfig(AptosSettings(fullNode = "https://api.example.com/v1", client = client))

      DefaultLedgerService(config)
        .info()
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.Serialization>()

      config.close()
      client.close()
    }
  })

private fun blockJson(): String =
  """
  {
    "block_height": "18446744073709551615",
    "block_hash": "0xabc",
    "block_timestamp": "18446744073709551615",
    "first_version": "0",
    "last_version": "18446744073709551615"
  }
  """
    .trimIndent()
