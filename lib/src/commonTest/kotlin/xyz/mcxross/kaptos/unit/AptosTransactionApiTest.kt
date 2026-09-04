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
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.AptosEndpoints
import xyz.mcxross.kaptos.TransactionDefaults
import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction

class AptosTransactionApiTest :
  StringSpec({
    "namespaced build inherits client defaults without making a network request" {
      val client =
        Aptos(
          AptosConfig(
            network = Network.LOCAL,
            transactionDefaults =
              TransactionDefaults(
                maxGasAmount = 9_999uL,
                expirationSecondsFromNow = 30uL,
              ),
          )
        )
      try {
        val result =
          client.transactions.build(
            sender = AccountAddress.ONE,
            payload = TransactionPayload.entryFunction("0x1::coin::transfer"),
            options =
              TransactionOptions(
                gasUnitPrice = 100uL,
                expirationTimestampSecs = 999_999uL,
                replayProtection = ReplayProtection.SequenceNumber(7uL),
              ),
          )

        val transaction =
          result.shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>().value
        transaction.rawTransaction.maxGasAmount shouldBe 9_999uL
        transaction.rawTransaction.sequenceNumber shouldBe 7uL
        transaction.rawTransaction.expirationTimestampSecs shouldBe 999_999uL
      } finally {
        client.close()
      }
    }

    "external suspend signers plug into the same transaction service" {
      val client = Aptos(AptosConfig(network = Network.LOCAL))
      try {
        val transaction = UnsignedTransaction.Simple(clientRawTransaction())
        val expected =
          AccountAuthenticator.Ed25519(
            Ed25519PublicKey(ByteArray(32) { 0x11 }),
            Ed25519Signature(ByteArray(64) { 0x22 }),
          )
        val externalSigner = externalSigner(expected)

        client.transactions.sign(externalSigner, transaction) shouldBe AptosResult.Success(expected)
      } finally {
        client.close()
      }
    }

    "user transaction hash matches the Aptos signed-transaction domain" {
      val client = Aptos(AptosConfig(network = Network.LOCAL))
      try {
        val transaction = UnsignedTransaction.Simple(clientRawTransaction())
        val authenticator =
          AccountAuthenticator.Ed25519(
            Ed25519PublicKey(ByteArray(32) { 0x11 }),
            Ed25519Signature(ByteArray(64) { 0x22 }),
          )

        client.transactions
          .userTransactionHash(transaction, authenticator)
          .shouldBeInstanceOf<AptosResult.Success<String>>()
          .value shouldBe "0x3b20bb1d29e2165d571506e684a970bd79eb9797aac7fd71c1f9d534569bc3ae"
      } finally {
        client.close()
      }
    }

    "external fee payer request exposes the canonical raw and authenticator BCS" {
      val client = Aptos(AptosConfig(network = Network.LOCAL))
      try {
        val transaction = UnsignedTransaction.FeePayer(clientRawTransaction())
        val authenticator =
          AccountAuthenticator.Ed25519(
            Ed25519PublicKey(ByteArray(32) { 0x11 }),
            Ed25519Signature(ByteArray(64) { 0x22 }),
          )

        val request =
          client.transactions
            .externalFeePayerRequest(transaction, authenticator)
            .shouldBeInstanceOf<AptosResult.Success<xyz.mcxross.kaptos.model.ExternalFeePayerRequest>>()
            .value

        request.transactionBytes.contentEquals(transaction.signingBcs()) shouldBe true
        request.senderAuthenticatorBytes.contentEquals(authenticator.toBcs()) shouldBe true
        request.additionalSignersAuthenticatorBytes shouldBe emptyList()
        request.fingerprint shouldBe "0x76796c439e76eb30dad14cabe98d99239a314a91848eb507c6077031dcc0ee5e"
      } finally {
        client.close()
      }
    }

    "submission preserves structured Aptos API errors" {
      val engine =
        MockEngine {
          respond(
            content =
              """{"message":"Feature is gated","error_code":"feature_under_gating","vm_error_code":26}""",
            status = HttpStatusCode.BadRequest,
            headers =
              headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
          )
        }
      val httpClient =
        HttpClient(engine) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val client =
        Aptos(
          AptosConfig(
            network = Network.CUSTOM,
            endpoints = AptosEndpoints(fullNode = "https://fullnode.example/v1"),
            httpClient = httpClient,
          )
        )
      try {
        val transaction = UnsignedTransaction.Simple(clientRawTransaction())
        val authenticator =
          AccountAuthenticator.Ed25519(
            Ed25519PublicKey(ByteArray(32) { 0x11 }),
            Ed25519Signature(ByteArray(64) { 0x22 }),
          )

        val error =
          client.transactions
            .submit(transaction, authenticator)
            .shouldBeInstanceOf<AptosResult.Failure>()
            .error
            .shouldBeInstanceOf<AptosError.UnsupportedFeature>()
        error.errorCode shouldBe "feature_under_gating"
        error.vmErrorCode shouldBe 26
      } finally {
        client.close()
        httpClient.close()
      }
    }
  })

private fun externalSigner(authenticator: AccountAuthenticator.Ed25519): TransactionSigner =
  object : TransactionSigner {
    override val accountAddress: AccountAddress = AccountAddress.ONE
    override val publicKey: PublicKey = authenticator.publicKey

    override suspend fun signBytes(message: ByteArray): AptosResult<Signature> =
      AptosResult.Success(authenticator.signature)

    override suspend fun signText(message: String): AptosResult<Signature> =
      signBytes(message.encodeToByteArray())

    override suspend fun signTransaction(
      transaction: UnsignedTransaction
    ): AptosResult<AccountAuthenticator> = AptosResult.Success(authenticator)

    override fun verifySignature(message: ByteArray, signature: Signature): Boolean = true
  }

private fun clientRawTransaction(): RawTransaction =
  RawTransaction(
    sender = AccountAddress.ONE,
    sequenceNumber = 0uL,
    payload = TransactionPayload.entryFunction("0x1::coin::transfer"),
    maxGasAmount = 2_000uL,
    gasUnitPrice = 100uL,
    expirationTimestampSecs = 999_999uL,
    chainId = ChainId(4u),
  )
