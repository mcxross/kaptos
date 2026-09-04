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
import kotlin.time.Instant
import xyz.mcxross.kaptos.digitalasset.CollectionRecord
import xyz.mcxross.kaptos.digitalasset.DefaultDigitalAssetService
import xyz.mcxross.kaptos.digitalasset.DigitalAssetActivity
import xyz.mcxross.kaptos.digitalasset.DigitalAssetDataSource
import xyz.mcxross.kaptos.digitalasset.DigitalAssetMetadata
import xyz.mcxross.kaptos.digitalasset.DigitalAssetOwnership
import xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty
import xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue
import xyz.mcxross.kaptos.digitalasset.toRecord
import xyz.mcxross.kaptos.generated.GetCollectionDataQuery
import xyz.mcxross.kaptos.generated.fragment.CurrentTokenOwnershipFields
import xyz.mcxross.kaptos.generated.fragment.TokenActivitiesFields
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTagStruct
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument

class DigitalAssetServiceTest :
  StringSpec({
    "property builders infer exact Move types and encode raw property bytes" {
      val transactions = RecordingTransactionService()
      val service = DefaultDigitalAssetService(FakeDigitalAssetDataSource(), transactions)

      service
        .buildAddProperty(
          sender = AccountAddress.ONE,
          assetId = AccountAddress.fromString("0xa"),
          property =
            DigitalAssetProperty(
              key = "supply",
              value = DigitalAssetPropertyValue.U64(ULong.MAX_VALUE),
            ),
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()

      val add = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      add.call.module.toString() shouldBe "0x4::aptos_token"
      add.call.function.toString() shouldBe "add_property"
      add.call.typeArguments.single().shouldBeInstanceOf<TypeTagStruct>().toString() shouldBe
        "0x4::token::Token"
      add.call.arguments.take(3) shouldBe
        listOf(
          MoveArgument.Address(AccountAddress.fromString("0xa")),
          MoveArgument.StringValue("supply"),
          MoveArgument.StringValue("u64"),
        )
      add.call.arguments[3]
        .shouldBeInstanceOf<MoveArgument.Bytes>()
        .value shouldBe ByteArray(8) { 0xff.toByte() }

      service
        .buildUpdateProperty(
          sender = AccountAddress.ONE,
          assetId = AccountAddress.fromString("0xa"),
          property =
            DigitalAssetProperty(
              key = "title",
              value = DigitalAssetPropertyValue.Text("apt"),
            ),
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val update =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      update.call.function.toString() shouldBe "update_property"
      update.call.arguments[2] shouldBe MoveArgument.StringValue("0x1::string::String")
      update.call.arguments[3]
        .shouldBeInstanceOf<MoveArgument.Bytes>()
        .value shouldBe byteArrayOf(3, 'a'.code.toByte(), 'p'.code.toByte(), 't'.code.toByte())
    }

    "collection and mint builders use ordered official arguments with typed properties" {
      val transactions = RecordingTransactionService()
      val service = DefaultDigitalAssetService(FakeDigitalAssetDataSource(), transactions)

      service
        .buildCreateCollection(
          sender = AccountAddress.ONE,
          name = "Kaptos",
          description = "Collection",
          uri = "https://example.com/collection.json",
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val create =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      create.call.function.toString() shouldBe "create_collection"
      create.call.arguments[0] shouldBe MoveArgument.StringValue("Collection")
      create.call.arguments[1] shouldBe MoveArgument.U64(ULong.MAX_VALUE)
      create.call.arguments[2] shouldBe MoveArgument.StringValue("Kaptos")
      create.call.arguments[13] shouldBe MoveArgument.U64(0u)
      create.call.arguments[14] shouldBe MoveArgument.U64(1u)

      val properties =
        listOf(
          DigitalAssetProperty("level", DigitalAssetPropertyValue.U16(7u)),
          DigitalAssetProperty("title", DigitalAssetPropertyValue.Text("apt")),
        )
      service
        .buildMint(
          sender = AccountAddress.ONE,
          collection = "Kaptos",
          name = "Kaptos #1",
          properties = properties,
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val mint = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      mint.call.function.toString() shouldBe "mint"
      mint.call.arguments[4] shouldBe
        MoveArgument.Vector(
          listOf(MoveArgument.StringValue("level"), MoveArgument.StringValue("title"))
        )
      mint.call.arguments[5] shouldBe
        MoveArgument.Vector(
          listOf(MoveArgument.StringValue("u16"), MoveArgument.StringValue("0x1::string::String"))
        )
      mint.call.arguments[6] shouldBe
        MoveArgument.Vector(
          listOf(
            MoveArgument.Bytes(byteArrayOf(7, 0)),
            MoveArgument.Bytes(byteArrayOf(3, 97, 112, 116)),
          )
        )

      service
        .buildMintSoulbound(
          sender = AccountAddress.ONE,
          recipient = AccountAddress.fromString("0xb"),
          collection = "Kaptos",
          name = "Badge",
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val soulbound =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      soulbound.call.function.toString() shouldBe "mint_soul_bound"
      soulbound.call.arguments.last() shouldBe
        MoveArgument.Address(AccountAddress.fromString("0xb"))
    }

    "asset lifecycle builders consistently use the default object type" {
      val transactions = RecordingTransactionService()
      val service = DefaultDigitalAssetService(FakeDigitalAssetDataSource(), transactions)
      val asset = AccountAddress.fromString("0xa")

      service
        .buildTransfer(AccountAddress.ONE, asset, AccountAddress.fromString("0xb"))
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      var payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.module.toString() shouldBe "0x1::object"
      payload.call.function.toString() shouldBe "transfer"
      payload.call.typeArguments.single().toString() shouldBe "0x4::token::Token"
      payload.call.arguments shouldBe
        listOf(
          MoveArgument.Address(asset),
          MoveArgument.Address(AccountAddress.fromString("0xb")),
        )

      service.buildBurn(AccountAddress.ONE, asset)
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .function
        .toString() shouldBe "burn"
      service.buildFreezeTransfer(AccountAddress.ONE, asset)
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .function
        .toString() shouldBe "freeze_transfer"
      service.buildUnfreezeTransfer(AccountAddress.ONE, asset)
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .function
        .toString() shouldBe "unfreeze_transfer"

      service.buildSetName(AccountAddress.ONE, asset, "Renamed")
      payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.function.toString() shouldBe "set_name"
      payload.call.arguments.last() shouldBe MoveArgument.StringValue("Renamed")
      service.buildSetDescription(AccountAddress.ONE, asset, "Updated")
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .function
        .toString() shouldBe "set_description"
      service.buildSetUri(AccountAddress.ONE, asset, "https://example.com/updated.json")
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .function
        .toString() shouldBe "set_uri"
      service.buildRemoveProperty(AccountAddress.ONE, asset, "level")
      payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.function.toString() shouldBe "remove_property"
      payload.call.arguments.last() shouldBe MoveArgument.StringValue("level")
    }

    "invalid digital-asset input returns typed validation failures" {
      val service =
        DefaultDigitalAssetService(FakeDigitalAssetDataSource(), RecordingTransactionService())

      service
        .buildMint(AccountAddress.ONE, collection = "", name = "Asset")
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.Validation>()
      service
        .buildSetUri(AccountAddress.ONE, AccountAddress.fromString("0xa"), "x".repeat(513))
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.Validation>()
    }

    "lookups and pages expose stable domain models rather than generated GraphQL data" {
      val request = PageRequest(offset = 5, limit = 2)
      val collection = collectionRecord()
      val ownership = ownershipRecord()
      val activity = activityRecord()
      val dataSource =
        FakeDigitalAssetDataSource(
          collection = collection,
          owned = AptosPage(listOf(ownership), totalCount = 9, request = request),
          activity = AptosPage(listOf(activity), totalCount = 7, request = request),
        )
      val service = DefaultDigitalAssetService(dataSource, RecordingTransactionService())

      service
        .getCollection(AccountAddress.fromString("0xa"))
        .shouldBeInstanceOf<AptosResult.Success<CollectionRecord?>>()
        .value shouldBe collection
      dataSource.collectionId shouldBe AccountAddress.fromString("0xa")

      service
        .getCollection(AccountAddress.ONE, "Kaptos")
        .shouldBeInstanceOf<AptosResult.Success<CollectionRecord?>>()
        .value shouldBe collection
      dataSource.creator shouldBe AccountAddress.ONE
      dataSource.collectionName shouldBe "Kaptos"

      service
        .getOwned(AccountAddress.ONE, request)
        .shouldBeInstanceOf<AptosResult.Success<AptosPage<DigitalAssetOwnership>>>()
        .value
        .hasNextPage shouldBe true
      service
        .getActivity(AccountAddress.fromString("0xb"), request)
        .shouldBeInstanceOf<AptosResult.Success<AptosPage<DigitalAssetActivity>>>()
        .value
        .hasNextPage shouldBe true
      dataSource.lastPage shouldBe request
    }

    "collection GraphQL rows retain the complete unsigned range" {
      val record =
        GetCollectionDataQuery.Current_collections_v2(
            uri = "https://example.com/collection.json",
            total_minted_v2 = "18446744073709551615",
            token_standard = "v2",
            table_handle_v1 = null,
            mutable_uri = true,
            mutable_description = false,
            max_supply = "18446744073709551615",
            collection_id = "0xa",
            collection_name = "Kaptos",
            creator_address = "0x1",
            current_supply = "18446744073709551615",
            description = "Collection",
            last_transaction_timestamp = "2026-08-26T10:11:12",
            last_transaction_version = "18446744073709551615",
          )
          .toRecord()

      record.id shouldBe AccountAddress.fromString("0xa")
      record.currentSupply shouldBe ULong.MAX_VALUE
      record.maxSupply shouldBe ULong.MAX_VALUE
      record.totalMinted shouldBe ULong.MAX_VALUE
      record.lastTransactionVersion shouldBe ULong.MAX_VALUE
      record.lastTransactionTimestamp shouldBe Instant.parse("2026-08-26T10:11:12Z")
    }

    "ownership and activity GraphQL rows map into typed addresses and unsigned values" {
      val ownership =
        CurrentTokenOwnershipFields(
            token_standard = "v2",
            token_properties_mutated_v1 = null,
            token_data_id = "0xb",
            table_type_v1 = null,
            storage_id = "0xc",
            property_version_v1 = "0",
            owner_address = "0x1",
            last_transaction_version = "18446744073709551615",
            last_transaction_timestamp = "2026-08-26T10:11:12Z",
            is_soulbound_v2 = false,
            is_fungible_v2 = false,
            amount = "18446744073709551615",
            current_token_data =
              CurrentTokenOwnershipFields.Current_token_data(
                collection_id = "0xa",
                description = "Asset",
                is_fungible_v2 = false,
                largest_property_version_v1 = null,
                last_transaction_timestamp = "2026-08-26T10:11:12Z",
                last_transaction_version = "1",
                maximum = "1",
                supply = "1",
                token_data_id = "0xb",
                token_name = "Kaptos #1",
                token_properties = emptyMap<String, String>(),
                token_standard = "v2",
                token_uri = "https://example.com/1.json",
                decimals = null,
                current_collection = null,
              ),
          )
          .toRecord()
      ownership.amount shouldBe ULong.MAX_VALUE
      ownership.asset?.collectionId shouldBe AccountAddress.fromString("0xa")
      ownership.lastTransactionVersion shouldBe ULong.MAX_VALUE

      val activity =
        TokenActivitiesFields(
            after_value = null,
            before_value = null,
            entry_function_id_str = "0x1::object::transfer",
            event_account_address = "0x1",
            event_index = "0",
            from_address = "0x1",
            is_fungible_v2 = false,
            property_version_v1 = "0",
            to_address = "0x2",
            token_amount = "18446744073709551615",
            token_data_id = "0xb",
            token_standard = "v2",
            transaction_timestamp = "2026-08-26T10:11:12+00:00",
            transaction_version = "18446744073709551615",
            type = "0x1::object::TransferEvent",
          )
          .toRecord()
      activity.amount shouldBe ULong.MAX_VALUE
      activity.to shouldBe AccountAddress.fromString("0x2")
      activity.transactionVersion shouldBe ULong.MAX_VALUE
      activity.transactionTimestamp shouldBe Instant.parse("2026-08-26T10:11:12Z")
    }
  })

private class FakeDigitalAssetDataSource(
  private val collection: CollectionRecord? = collectionRecord(),
  private val owned: AptosPage<DigitalAssetOwnership> =
    AptosPage(emptyList(), 0, PageRequest()),
  private val activity: AptosPage<DigitalAssetActivity> =
    AptosPage(emptyList(), 0, PageRequest()),
) : DigitalAssetDataSource {
  var collectionId: AccountAddress? = null
  var creator: AccountAddress? = null
  var collectionName: String? = null
  var lastPage: PageRequest? = null

  override suspend fun getCollection(
    collectionId: AccountAddress?,
    creator: AccountAddress?,
    name: String?,
  ): AptosResult<CollectionRecord?> {
    this.collectionId = collectionId
    this.creator = creator
    collectionName = name
    return AptosResult.Success(collection)
  }

  override suspend fun getOwned(
    owner: AccountAddress,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetOwnership>> {
    lastPage = page
    return AptosResult.Success(owned)
  }

  override suspend fun getActivity(
    assetId: AccountAddress,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetActivity>> {
    lastPage = page
    return AptosResult.Success(activity)
  }
}

private fun collectionRecord(): CollectionRecord =
  CollectionRecord(
    id = AccountAddress.fromString("0xa"),
    creator = AccountAddress.ONE,
    name = "Kaptos",
    description = "Collection",
    uri = "https://example.com/collection.json",
    tokenStandard = "v2",
    currentSupply = 1uL,
    maxSupply = 100uL,
    totalMinted = 1uL,
    mutableDescription = true,
    mutableUri = true,
    lastTransactionVersion = 1uL,
    lastTransactionTimestamp = Instant.parse("2026-08-26T10:11:12Z"),
  )

private fun ownershipRecord(): DigitalAssetOwnership =
  DigitalAssetOwnership(
    asset =
      DigitalAssetMetadata(
        id = AccountAddress.fromString("0xb"),
        collectionId = AccountAddress.fromString("0xa"),
        name = "Kaptos #1",
        description = "Asset",
        uri = "https://example.com/1.json",
        tokenStandard = "v2",
      ),
    assetId = AccountAddress.fromString("0xb"),
    owner = AccountAddress.ONE,
    storageId = AccountAddress.fromString("0xc"),
    amount = 1uL,
    tokenStandard = "v2",
    isSoulbound = false,
    isFungible = false,
    lastTransactionVersion = 1uL,
    lastTransactionTimestamp = Instant.parse("2026-08-26T10:11:12Z"),
  )

private fun activityRecord(): DigitalAssetActivity =
  DigitalAssetActivity(
    assetId = AccountAddress.fromString("0xb"),
    type = "transfer",
    from = AccountAddress.ONE,
    to = AccountAddress.fromString("0x2"),
    eventAccount = AccountAddress.ONE,
    amount = 1uL,
    tokenStandard = "v2",
    entryFunction = "0x1::object::transfer",
    transactionVersion = 1uL,
    transactionTimestamp = Instant.parse("2026-08-26T10:11:12Z"),
  )
