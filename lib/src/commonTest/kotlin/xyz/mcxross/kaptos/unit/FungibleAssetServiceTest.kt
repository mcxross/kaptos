/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import xyz.mcxross.kaptos.account.AccountAsset
import xyz.mcxross.kaptos.account.DefaultAccountRestorationDataSource
import xyz.mcxross.kaptos.fungible.DefaultFungibleAssetService
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.TypeTagStruct
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.move.MoveArgument

class FungibleAssetServiceTest :
  StringSpec({
    "primary-store transfer uses the official metadata type and argument order" {
      val transactions = RecordingTransactionService()
      val service = DefaultFungibleAssetService(transactions)

      service
        .buildTransfer(
          sender = AccountAddress.ONE,
          metadataAddress = AccountAddress.fromString("0xa"),
          recipient = AccountAddress.fromString("0xb"),
          amount = 42uL,
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()

      val payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.module.toString() shouldBe "0x1::primary_fungible_store"
      payload.call.function.toString() shouldBe "transfer"
      payload.call.typeArguments.single().shouldBeInstanceOf<TypeTagStruct>().toString() shouldBe
        "0x1::fungible_asset::Metadata"
      payload.call.arguments shouldBe
        listOf(
          MoveArgument.Address(AccountAddress.fromString("0xa")),
          MoveArgument.Address(AccountAddress.fromString("0xb")),
          MoveArgument.U64(42u),
        )
    }

    "explicit-store transfer targets dispatchable fungible assets" {
      val transactions = RecordingTransactionService()
      val service = DefaultFungibleAssetService(transactions)

      service
        .buildStoreTransfer(
          sender = AccountAddress.ONE,
          fromStore = AccountAddress.fromString("0xc"),
          toStore = AccountAddress.fromString("0xd"),
          amount = 7uL,
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()

      val payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.module.toString() shouldBe "0x1::dispatchable_fungible_asset"
      payload.call.typeArguments shouldHaveSize 1
      payload.call.typeArguments.single().toString() shouldBe "0x1::fungible_asset::FungibleStore"
      payload.call.arguments shouldBe
        listOf(
          MoveArgument.Address(AccountAddress.fromString("0xc")),
          MoveArgument.Address(AccountAddress.fromString("0xd")),
          MoveArgument.U64(7u),
        )
    }

    "unified balance lookup supports coin types and the full unsigned range" {
      val expected = ULong.MAX_VALUE
      val engine = MockEngine { request ->
        request.url.encodedPath shouldBe "/v1/accounts/0x1/balance/0x1::aptos_coin::AptosCoin"
        respond(
          content = "\"$expected\"",
          status = HttpStatusCode.OK,
          headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
      }
      val client = HttpClient(engine)
      val config =
        TransportConfig(AptosSettings(fullNode = "https://api.example.com/v1", client = client))

      val balance =
        DefaultAccountRestorationDataSource(config)
          .getBalance(
            AccountAddress.ONE,
            AccountAsset.coin("0x1::aptos_coin::AptosCoin"),
          )
          .shouldBeInstanceOf<AptosResult.Success<ULong>>()
          .value
      balance shouldBe expected

      config.close()
      client.close()
    }

    "fungible-asset balance identifiers use canonical metadata addresses" {
      AccountAsset.fungibleAsset("0xa").value shouldBe "0xa"
    }
  })
