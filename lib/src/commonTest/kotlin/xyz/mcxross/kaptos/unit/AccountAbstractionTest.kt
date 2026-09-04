/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.kaptos.account.AbstractedAccount
import xyz.mcxross.kaptos.account.AbstractionSigner
import xyz.mcxross.kaptos.account.AccountAbstractionDataSource
import xyz.mcxross.kaptos.account.DefaultAccountAbstractionService
import xyz.mcxross.kaptos.account.DefaultAccountAbstractionDataSource
import xyz.mcxross.kaptos.account.DerivableAbstractedAccount
import xyz.mcxross.kaptos.account.SolanaDerivableAccount
import xyz.mcxross.kaptos.account.SolanaMessageSigner
import xyz.mcxross.kaptos.core.crypto.AbstractPublicKey
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction

class AccountAbstractionTest :
  StringSpec({
    "authentication functions have a validated canonical representation" {
      val function = AuthenticationFunction.parse(AUTHENTICATION_FUNCTION)

      function.toString() shouldBe AUTHENTICATION_FUNCTION
      shouldThrow<IllegalArgumentException> { AuthenticationFunction.parse("0x1::missing") }
    }

    "abstract public identities have deterministic BCS bytes" {
      val key = AbstractPublicKey(AccountAddress.ONE)

      key.toByteArray().toHex() shouldBe AccountAddress.ONE.data.toHex()
      key.toBcs().toHex() shouldBe "20${AccountAddress.ONE.data.toHex()}"
    }

    "status and function-specific checks use typed authentication functions" {
      val function = AuthenticationFunction.parse(AUTHENTICATION_FUNCTION)
      val service =
        DefaultAccountAbstractionService(
          FakeAbstractionDataSource(listOf(function, function)),
          RecordingTransactionService(),
        )

      val status =
        service
          .status(AccountAddress.ONE)
          .shouldBeInstanceOf<AptosResult.Success<xyz.mcxross.kaptos.account.AccountAbstractionStatus>>()
          .value
      status.isEnabled shouldBe true
      status.authenticationFunctions shouldBe listOf(function)
      service
        .isEnabled(AccountAddress.ONE, function)
        .shouldBeInstanceOf<AptosResult.Success<Boolean>>()
        .value shouldBe true
      service
        .isEnabled(
          AccountAddress.ONE,
          AuthenticationFunction.parse("0x2::other::authenticate"),
        )
        .shouldBeInstanceOf<AptosResult.Success<Boolean>>()
        .value shouldBe false
    }

    "enable and disable builders encode the account-abstraction entry functions" {
      val function = AuthenticationFunction.parse(AUTHENTICATION_FUNCTION)
      val transactions = RecordingTransactionService()
      val service =
        DefaultAccountAbstractionService(FakeAbstractionDataSource(), transactions)

      service
        .buildEnable(AccountAddress.ONE, function)
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val enable = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      enable.call.module.toString() shouldBe "0x1::account_abstraction"
      enable.call.function.toString() shouldBe "add_authentication_function"
      enable.call.arguments shouldBe
        listOf(
          MoveArgument.Address(AccountAddress.ONE),
          MoveArgument.StringValue("permissioned_delegation"),
          MoveArgument.StringValue("authenticate"),
        )

      service
        .buildDisable(AccountAddress.ONE, function)
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val disableFunction =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      disableFunction.call.function.toString() shouldBe "remove_authentication_function"

      service
        .buildDisable(AccountAddress.ONE)
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val disableAll =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      disableAll.call.function.toString() shouldBe "remove_authenticator"
      disableAll.call.arguments.shouldBeEmpty()
    }

    "fullnode view response is decoded without leaking wire models" {
      val engine =
        MockEngine { request ->
          request.url.encodedPath shouldBe "/v1/view"
          val requestJson =
            Json.parseToJsonElement(request.body.toByteArray().decodeToString()).jsonObject
          requestJson["arguments"]!!.jsonArray.single().jsonPrimitive.content shouldBe
            AccountAddress.ONE.toStringLong()
          respond(
            content =
              """[{"vec":[[{"module_address":"0x1","module_name":"permissioned_delegation","function_name":"authenticate"}]]}]""",
            status = HttpStatusCode.OK,
            headers =
              headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
          )
        }
      val client =
        HttpClient(engine) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val config =
        TransportConfig(
          AptosSettings(fullNode = "https://api.example.com/v1", client = client)
        )

      val functions =
        DefaultAccountAbstractionDataSource(config)
          .getAuthenticationFunctions(AccountAddress.ONE)
          .shouldBeInstanceOf<AptosResult.Success<List<AuthenticationFunction>>>()
          .value
      functions shouldBe listOf(AuthenticationFunction.parse(AUTHENTICATION_FUNCTION))

      config.close()
      client.close()
    }

    "abstracted signer and authenticator match the pinned TypeScript SDK vectors" {
      val function = AuthenticationFunction.parse(AUTHENTICATION_FUNCTION)
      var receivedDigest = ByteArray(0)
      val account =
        AbstractedAccount(
          accountAddress = AccountAddress.ONE,
          authenticationFunction = function,
          signer =
            AbstractionSigner { digest ->
              receivedDigest = digest.copyOf()
              AptosResult.Success(ABSTRACTION_SIGNATURE.hexBytes())
            },
        )
      val transaction = officialSimpleTransaction()

      AbstractedAccount.signingMessage(transaction.signingMessage(), function).toHex() shouldBe
        ABSTRACTION_SIGNING_MESSAGE
      val authenticator =
        account
          .signTransaction(transaction)
          .shouldBeInstanceOf<AptosResult.Success<AccountAuthenticator>>()
          .value
      receivedDigest.toHex() shouldBe ABSTRACTION_SIGNING_MESSAGE_DIGEST
      authenticator.toBcs().toHex() shouldBe ABSTRACTION_AUTHENTICATOR
      AccountAuthenticator.fromBcs(authenticator.toBcs()) shouldBe authenticator
    }

    "derivable abstracted accounts match the official derived address and wire layout" {
      val function = AuthenticationFunction.parse(AUTHENTICATION_FUNCTION)
      val account =
        DerivableAbstractedAccount(
          authenticationFunction = function,
          abstractPublicKey = DERIVABLE_IDENTITY.hexBytes(),
          signer = AbstractionSigner { AptosResult.Success(ABSTRACTION_SIGNATURE.hexBytes()) },
        )

      account.accountAddress.toStringLong() shouldBe DERIVABLE_ADDRESS
      account.abstractPublicKey.toHex() shouldBe DERIVABLE_IDENTITY
      account
        .signTransaction(officialSimpleTransaction())
        .shouldBeInstanceOf<AptosResult.Success<AccountAuthenticator>>()
        .value
        .toBcs()
        .toHex() shouldBe DERIVABLE_AUTHENTICATOR
    }

    "Solana SIWS messages match the Aptos Framework fixture" {
      SolanaDerivableAccount
        .siwsMessage(
          domain = "localhost:3000",
          base58PublicKey = "G56zT1K6AQab7FzwHdQ8hiHXusR14Rmddw6Vz5MFbbmV",
          entryFunction = "0x1::coin::transfer",
          chainId = 2u,
          digest =
            "9509edc861070b2848d8161c9453159139f867745dc87d32864a71e796c7d279"
              .hexBytes(),
        )
        .decodeToString() shouldBe
        "localhost:3000 wants you to sign in with your Solana account:\n" +
          "G56zT1K6AQab7FzwHdQ8hiHXusR14Rmddw6Vz5MFbbmV\n\n" +
          "Please confirm you explicitly initiated this request from localhost:3000. " +
          "You are approving to execute transaction 0x1::coin::transfer on Aptos blockchain " +
          "(testnet).\n\nNonce: " +
          "0x9509edc861070b2848d8161c9453159139f867745dc87d32864a71e796c7d279"
    }

    "Solana derivable accounts wrap wallet signatures in the framework wire format" {
      var message = ByteArray(0)
      val signature = ByteArray(64) { it.toByte() }
      val account =
        SolanaDerivableAccount(
          publicKey = ByteArray(32),
          domain = "wallet.example",
          signer =
            SolanaMessageSigner {
              message = it.copyOf()
              AptosResult.Success(signature)
            },
        )

      account.base58PublicKey shouldBe "1".repeat(32)
      val authenticator =
        account
          .signTransaction(officialSimpleTransaction())
          .shouldBeInstanceOf<AptosResult.Success<AccountAuthenticator.Abstraction>>()
          .value
      message.decodeToString() shouldBe
        SolanaDerivableAccount.siwsMessage(
            domain = "wallet.example",
            base58PublicKey = account.base58PublicKey,
            entryFunction = "0x1::coin::transfer",
            chainId = 4u,
            digest = authenticator.signingMessageDigest.toByteArray(),
          )
          .decodeToString()
      authenticator.signature.toByteArray().toHex() shouldBe
        "0040${signature.toHex()}"
      authenticator.accountIdentity?.toByteArray()?.toHex() shouldBe
        account.abstractPublicKey.toHex()
    }
  })

private class FakeAbstractionDataSource(
  private val functions: List<AuthenticationFunction> = emptyList(),
) : AccountAbstractionDataSource {
  override suspend fun getAuthenticationFunctions(
    accountAddress: AccountAddress
  ): AptosResult<List<AuthenticationFunction>> = AptosResult.Success(functions)
}

private fun officialSimpleTransaction(): UnsignedTransaction.Simple =
  UnsignedTransaction.Simple(
    RawTransaction(
      sender = AccountAddress.ONE,
      sequenceNumber = 7uL,
      payload =
        TransactionPayload.entryFunction(
          function = "0x1::coin::transfer",
          arguments = listOf(MoveArgument.U64(123u)),
        ),
      maxGasAmount = 2_000_000uL,
      gasUnitPrice = 100uL,
      expirationTimestampSecs = 999_999uL,
      chainId = ChainId(4u),
    )
  )

private fun ByteArray.toHex(): String =
  joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }

private fun String.hexBytes(): ByteArray =
  chunked(2).map { it.toInt(16).toByte() }.toByteArray()

private const val AUTHENTICATION_FUNCTION =
  "0x1::permissioned_delegation::authenticate"
private const val ABSTRACTION_SIGNATURE = "aabbcc"
private const val ABSTRACTION_SIGNING_MESSAGE_DIGEST =
  "4c0f1aab1515dceb891d7ca157f5a994b0881a1d4b4afded3cc531e0278c9043"
private const val ABSTRACTION_SIGNING_MESSAGE =
  "b020f512f57d4b4f801a6fde7f38f45303d294a1d610a906ce26f75c17ee86b0009b01b5e97db07fa0bd0e5598aa3643a9bc6f6693bddc1a9fec9e674a461eaa00b1930000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f0000000000040000000000000000000000000000000000000000000000000000000000000001177065726d697373696f6e65645f64656c65676174696f6e0c61757468656e746963617465"
private const val ABSTRACTION_AUTHENTICATOR =
  "050000000000000000000000000000000000000000000000000000000000000001177065726d697373696f6e65645f64656c65676174696f6e0c61757468656e74696361746500204c0f1aab1515dceb891d7ca157f5a994b0881a1d4b4afded3cc531e0278c904303aabbcc"
private const val DERIVABLE_IDENTITY = "01020304"
private const val DERIVABLE_ADDRESS =
  "0x1bbfeb59f8e1a6363fc286f1cc82a5fab4c7fa284d7c125020aa38feb1c60b81"
private const val DERIVABLE_AUTHENTICATOR =
  "050000000000000000000000000000000000000000000000000000000000000001177065726d697373696f6e65645f64656c65676174696f6e0c61757468656e74696361746501204c0f1aab1515dceb891d7ca157f5a994b0881a1d4b4afded3cc531e0278c904303aabbcc0401020304"
