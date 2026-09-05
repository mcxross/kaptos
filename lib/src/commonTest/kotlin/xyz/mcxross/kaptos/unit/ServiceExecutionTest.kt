/*
 * Copyright 2026 McXross
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
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
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Result

class ServiceExecutionTest :
  StringSpec({
    val operations: Map<String, suspend (Aptos) -> Unit> =
      mapOf(
        "account GET" to
          {
            it.accounts.get(AccountAddress.ONE)
            Unit
          },
        "ledger GET" to
          {
            it.ledger.info()
            Unit
          },
        "view POST" to
          {
            it.views.callRaw("0x1::coin::balance")
            Unit
          },
        "indexer POST" to
          {
            it.indexer.query("query { ledger_infos { chain_id } }")
            Unit
          },
        "transaction polling" to
          {
            it.transactions.waitForTransaction("0x" + "1".repeat(64))
            Unit
          },
      )
    for ((name, operation) in operations) {
      "$name propagates transport cancellation" {
        val http =
          HttpClient(MockEngine { throw CancellationException("request cancelled") }) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
          }
        val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
        try {
          shouldThrow<CancellationException> { operation(aptos) }
        } finally {
          aptos.close()
          http.close()
        }
      }
    }

    "view failures retain the fullnode error code and VM code" {
      val http =
        HttpClient(
          MockEngine {
            respond(
              """{"message":"Move failed","error_code":"vm_error","vm_error_code":42}""",
              HttpStatusCode.BadRequest,
              headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
          }
        ) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
      try {
        val failure =
          aptos.views.callRaw("0x1::coin::balance").shouldBeInstanceOf<AptosResult.Failure>()
        val error = failure.error.shouldBeInstanceOf<AptosError.Api>()
        error.errorCode shouldBe "vm_error"
        error.vmErrorCode shouldBe 42L
      } finally {
        aptos.close()
        http.close()
      }
    }

    "malformed indexer responses are serialization failures" {
      val http =
        HttpClient(
          MockEngine {
            respond(
              "not json",
              HttpStatusCode.OK,
              headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
          }
        ) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
      try {
        aptos.indexer
          .query("query { ledger_infos { chain_id } }")
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Serialization>()
      } finally {
        aptos.close()
        http.close()
      }
    }

    "wrapped cancellation escapes internal result conversion" {
      val cancellation = CancellationException("cancelled before conversion")
      shouldThrow<CancellationException> {
        executeAptos {
          Result.Err(AptosSdkError.NetworkError(cancellation)).toAptosResult()
        }
      } shouldBe cancellation
    }

    "execution does not intercept fatal runtime errors" {
      val fatal = AssertionError("internal invariant failed")
      shouldThrow<AssertionError> { executeAptos<Unit> { throw fatal } } shouldBe fatal
    }
  })
