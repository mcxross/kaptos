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
import xyz.mcxross.kaptos.coin.DefaultCoinService
import xyz.mcxross.kaptos.generated.GetDelegatedStakingActivitiesQuery
import xyz.mcxross.kaptos.generated.GetNumberOfDelegatorsQuery
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsMetadataQuery
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.objects.toRecord
import xyz.mcxross.kaptos.staking.toRecord
import xyz.mcxross.kaptos.table.toRecord
import xyz.mcxross.kaptos.transaction.MoveArgument

class ModernNamespaceServiceTest :
  StringSpec({
    "coin transfers use the current aptos_account entry function and unsigned amount" {
      val transactions = RecordingTransactionService()
      val service = DefaultCoinService(transactions)

      service
        .buildTransfer(
          sender = AccountAddress.ONE,
          recipient = AccountAddress.fromString("0xb"),
          amount = ULong.MAX_VALUE,
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()

      val payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.module.toString() shouldBe "0x1::aptos_account"
      payload.call.function.toString() shouldBe "transfer_coins"
      payload.call.typeArguments.single().toString() shouldBe "0x1::aptos_coin::AptosCoin"
      payload.call.arguments shouldBe
        listOf(
          MoveArgument.Address(AccountAddress.fromString("0xb")),
          MoveArgument.U64(ULong.MAX_VALUE),
        )
    }

    "table records map GraphQL scalars without signed truncation" {
      val item =
        GetTableItemsDataQuery.Table_item(
            decoded_key = mapOf("owner" to "0x1"),
            decoded_value = listOf(1, 2),
            key = "0xab",
            table_handle = "0xcd",
            transaction_version = ULong.MAX_VALUE.toString(),
            write_set_change_index = ULong.MAX_VALUE.toString(),
          )
          .toRecord()
      item.transactionVersion shouldBe ULong.MAX_VALUE
      item.writeSetChangeIndex shouldBe ULong.MAX_VALUE
      item.tableHandle shouldBe "0xcd"

      GetTableItemsMetadataQuery.Table_metadata("0xcd", "address", "u64")
        .toRecord() shouldBe
        xyz.mcxross.kaptos.table.TableMetadata("0xcd", "address", "u64")
    }

    "object records map address and u64 fields into domain types" {
      val record =
        GetObjectDataQuery.Current_object(
            allow_ungated_transfer = true,
            state_key_hash = "0xbeef",
            owner_address = "0x1",
            object_address = "0xa",
            last_transaction_version = ULong.MAX_VALUE.toString(),
            last_guid_creation_num = "7",
            is_deleted = false,
          )
          .toRecord()

      record.address shouldBe AccountAddress.fromString("0xa")
      record.owner shouldBe AccountAddress.ONE
      record.lastTransactionVersion shouldBe ULong.MAX_VALUE
      record.lastGuidCreationNumber shouldBe 7uL
    }

    "staking counts and activities map to unsigned domain records" {
      val count =
        GetNumberOfDelegatorsQuery.Num_active_delegator_per_pool(
            num_active_delegator = ULong.MAX_VALUE.toString(),
            pool_address = "0xa",
          )
          .toRecord()
      count.pool shouldBe AccountAddress.fromString("0xa")
      count.activeDelegators shouldBe ULong.MAX_VALUE

      val activity =
        GetDelegatedStakingActivitiesQuery.Delegated_staking_activity(
            amount = ULong.MAX_VALUE.toString(),
            delegator_address = "0x1",
            event_index = "2",
            event_type = "add_stake",
            pool_address = "0xa",
            transaction_version = "3",
          )
          .toRecord()
      activity.amount shouldBe ULong.MAX_VALUE
      activity.eventIndex shouldBe 2uL
      activity.transactionVersion shouldBe 3uL
    }
  })
