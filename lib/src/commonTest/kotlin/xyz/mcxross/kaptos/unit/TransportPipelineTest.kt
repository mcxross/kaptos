/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import com.github.michaelbull.result.getError
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import xyz.mcxross.kaptos.client.get
import xyz.mcxross.kaptos.client.post
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.FullNodeConfig
import xyz.mcxross.kaptos.model.RequestOptions

class TransportPipelineTest :
  StringSpec({
    "POST preserves query parameters and endpoint headers override common headers" {
      var calls = 0
      val engine =
        MockEngine { request ->
          calls += 1
          request.url.encodedPath shouldBe "/v1/transactions/simulate"
          request.url.parameters["estimate_gas_unit_price"] shouldBe "true"
          request.url.parameters["estimate_max_gas_amount"] shouldBe "true"
          request.headers["X-Order"] shouldBe "endpoint"
          request.headers["X-Common"] shouldBe "present"
          respondOk("{}")
        }
      val client = HttpClient(engine)
      val config =
        TransportConfig(
          AptosSettings(
            fullNode = "https://api.example.com/v1",
            client = client,
            commonHeaders = mapOf("X-Order" to "common", "X-Common" to "present"),
            fullNodeConfig = FullNodeConfig(mapOf("X-Order" to "endpoint")),
          )
        )

      val result = post(
          RequestOptions.PostRequestOptions(
            aptosConfig = config,
            type = AptosApiType.FULLNODE,
            originMethod = "simulate",
            path = "transactions/simulate",
            params =
              mapOf(
                "estimate_gas_unit_price" to true,
                "estimate_max_gas_amount" to true,
              ),
            body = "{}",
          )
        )
      result.isOk shouldBe true
      calls shouldBe 1
      config.close()
      client.close()
    }

    "request headers are resolved for every call and override static endpoint headers" {
      var token = "first"
      val observed = mutableListOf<String?>()
      val client =
        HttpClient(
          MockEngine { request ->
            observed += request.headers[HttpHeaders.Authorization]
            respondOk("{}")
          }
        )
      val config =
        TransportConfig(
          AptosSettings(
            fullNode = "https://api.example.com/v1",
            client = client,
            fullNodeConfig =
              FullNodeConfig(
                headers = mapOf(HttpHeaders.Authorization to "Bearer static"),
                requestHeaders = { mapOf(HttpHeaders.Authorization to "Bearer $token") },
              ),
          )
        )
      val request =
        RequestOptions.AptosRequestOptions(
          aptosConfig = config,
          type = AptosApiType.FULLNODE,
          originMethod = "ledger",
          path = "",
        )

      // Use a nonblank ledger path because the transport rejects blank request paths.
      val ledgerRequest = request.copy(path = "transactions/by_hash/0x1")
      get(ledgerRequest).isOk shouldBe true
      token = "second"
      get(ledgerRequest).isOk shouldBe true
      observed shouldBe listOf("Bearer first", "Bearer second")
      config.close()
      client.close()
    }

    "410 retries once and strips credentials for a cross-site archival endpoint" {
      var calls = 0
      val engine =
        MockEngine { request ->
          calls += 1
          if (calls == 1) {
            request.headers[HttpHeaders.Authorization] shouldBe "Bearer secret"
            respond(
              content =
                """{"message":"pruned","error_code":"version_pruned","archival_endpoint":"https://archive.other.net/v1"}""",
              status = HttpStatusCode.Gone,
              headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
          } else {
            request.url.host shouldBe "archive.other.net"
            request.url.encodedPath shouldBe "/v1/accounts/0x1"
            request.headers[HttpHeaders.Authorization] shouldBe null
            request.headers["X-Trace"] shouldBe "keep"
            respondOk("{}")
          }
        }
      val client = HttpClient(engine)
      val config =
        TransportConfig(
          AptosSettings(
            fullNode = "https://api.example.com/v1",
            client = client,
            commonHeaders =
              mapOf(HttpHeaders.Authorization to "Bearer secret", "X-Trace" to "keep"),
          )
        )

      val result = get(
          RequestOptions.AptosRequestOptions(
            aptosConfig = config,
            type = AptosApiType.FULLNODE,
            originMethod = "getAccount",
            path = "accounts/0x1",
          )
        )
      result.isOk shouldBe true
      calls shouldBe 2
      config.close()
      client.close()
    }

    "archival fallback can be disabled" {
      var calls = 0
      val engine =
        MockEngine {
          calls += 1
          respond(
            content =
              """{"message":"pruned","error_code":"version_pruned","archival_endpoint":"https://archive.example.com/v1"}""",
            status = HttpStatusCode.Gone,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
          )
        }
      val client = HttpClient(engine)
      val config =
        TransportConfig(
          AptosSettings(
            fullNode = "https://api.example.com/v1",
            client = client,
            archivalFallback = false,
          )
        )

      val result =
        get(
          RequestOptions.AptosRequestOptions(
            aptosConfig = config,
            type = AptosApiType.FULLNODE,
            originMethod = "getAccount",
            path = "accounts/0x1",
          )
        )
      result.isErr shouldBe true
      result.getError().shouldBeInstanceOf<AptosSdkError.ApiError>()
      calls shouldBe 1
      config.close()
      client.close()
    }

    "closing config does not close an injected client" {
      val client = HttpClient(MockEngine { respondOk("{}") })
      val config = TransportConfig(AptosSettings(fullNode = "https://api.example.com/v1", client = client))
      config.close()
      client.get("https://api.example.com/v1").status shouldBe HttpStatusCode.OK
      client.close()
    }
  })
