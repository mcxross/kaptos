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
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.TransactionDefaults
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.MoveAbility
import xyz.mcxross.kaptos.model.MoveFunction
import xyz.mcxross.kaptos.model.MoveModule
import xyz.mcxross.kaptos.model.MoveModuleBytecode
import xyz.mcxross.kaptos.model.MoveStruct
import xyz.mcxross.kaptos.model.MoveStructField
import xyz.mcxross.kaptos.model.MoveVisibility
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.move.MoveArgumentCodec
import xyz.mcxross.kaptos.move.MoveModuleLoader
import xyz.mcxross.kaptos.move.toMoveArguments

class AbiCoercionTest :
  StringSpec({
    val codec =
      MoveArgumentCodec(
        MoveModuleLoader { _, _ ->
          AptosResult.Failure(AptosError.Transport("offline loader must not be called"))
        }
      )

    beforeTest { codec.preload(TEST_MODULE) }

    "coerces string address and integer to address and u64" {
      val payload =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::transfer",
            typeArguments = emptyList(),
            arguments = listOf("0x1", 1_000_000).toMoveArguments(),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      val expectedLongHand =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::transfer",
            typeArguments = emptyList(),
            arguments =
              listOf(
                MoveArgument.Address(AccountAddress.fromString("0x1")),
                MoveArgument.U64(1_000_000uL),
              ),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      payload.toBcs() shouldBe expectedLongHand.toBcs()
    }

    "coerces numbers across different integer widths" {
      val payload =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::numbers",
            arguments = listOf(42, 1000, 50_000, "99999999999999999", -500).toMoveArguments(),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      val expectedLongHand =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::numbers",
            arguments =
              listOf(
                MoveArgument.U8(42u),
                MoveArgument.U16(1000u),
                MoveArgument.U32(50_000u),
                MoveArgument.U128("99999999999999999"),
                MoveArgument.I64(-500L),
              ),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      payload.toBcs() shouldBe expectedLongHand.toBcs()
    }

    "rejects negative numbers and overflows for unsigned integer types" {
      val negativeResult =
        codec.entryFunctionPayload(
          function = "0x1::test_contract::numbers",
          arguments = listOf(-1, 1000, 50_000, "100", 0).toMoveArguments(),
        )
      negativeResult.shouldBeInstanceOf<AptosResult.Failure>()

      val overflowResult =
        codec.entryFunctionPayload(
          function = "0x1::test_contract::numbers",
          arguments = listOf(300, 1000, 50_000, "100", 0).toMoveArguments(),
        )
      overflowResult.shouldBeInstanceOf<AptosResult.Failure>()
    }

    "coerces object address and option value" {
      val payloadWithSome =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::options_and_objects",
            arguments = listOf("0x2", 500uL).toMoveArguments(),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      val payloadWithNone =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::options_and_objects",
            arguments = listOf("0x2", null).toMoveArguments(),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      payloadWithSome.toBcs() shouldBe
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::options_and_objects",
            arguments =
              listOf(
                MoveArgument.Address(AccountAddress.fromString("0x2")),
                MoveArgument.Option(MoveArgument.U64(500uL)),
              ),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value
          .toBcs()

      payloadWithNone.toBcs() shouldBe
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::options_and_objects",
            arguments =
              listOf(
                MoveArgument.Address(AccountAddress.fromString("0x2")),
                MoveArgument.Option(null),
              ),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value
          .toBcs()
    }

    "coerces hex string into vector<u8>" {
      val payload =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::byte_vector",
            arguments = listOf("0xdeadbeef").toMoveArguments(),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      val expected =
        codec
          .entryFunctionPayload(
            function = "0x1::test_contract::byte_vector",
            arguments =
              listOf(
                MoveArgument.Bytes(
                  byteArrayOf(0xde.toByte(), 0xad.toByte(), 0xbe.toByte(), 0xef.toByte())
                )
              ),
          )
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
          .value

      payload.toBcs() shouldBe expected.toBcs()
    }

    "TransactionService.build accepts function name and raw arguments directly" {
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
      client.transactions.preloadModuleAbis(TEST_MODULE)

      val result =
        client.transactions.build(
          sender = AccountAddress.fromString("0xcafe"),
          function = "0x1::test_contract::transfer",
          arguments = listOf("0x1", 1_000_000),
          options =
            TransactionOptions(
              gasUnitPrice = 100uL,
              expirationTimestampSecs = 999_999uL,
              replayProtection = ReplayProtection.SequenceNumber(1uL),
            ),
        )

      val unsigned =
        result.shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>().value
      unsigned.rawTransaction.sender shouldBe AccountAddress.fromString("0xcafe")
    }
  })

private val TEST_MODULE =
  MoveModuleBytecode(
    bytecode = "0x",
    abi =
      MoveModule(
        address = "0x1",
        name = "test_contract",
        friends = emptyList(),
        exposedFunctions =
          listOf(
            MoveFunction(
              name = "transfer",
              visibility = MoveVisibility.PUBLIC,
              isEntry = true,
              isView = false,
              genericTypeParams = emptyList(),
              params = listOf("&signer", "address", "u64"),
              `return` = emptyList(),
            ),
            MoveFunction(
              name = "numbers",
              visibility = MoveVisibility.PUBLIC,
              isEntry = true,
              isView = false,
              genericTypeParams = emptyList(),
              params = listOf("u8", "u16", "u32", "u128", "i64"),
              `return` = emptyList(),
            ),
            MoveFunction(
              name = "options_and_objects",
              visibility = MoveVisibility.PUBLIC,
              isEntry = true,
              isView = false,
              genericTypeParams = emptyList(),
              params =
                listOf(
                  "0x1::object::Object<0x1::test_contract::Asset>",
                  "0x1::option::Option<u64>",
                ),
              `return` = emptyList(),
            ),
            MoveFunction(
              name = "byte_vector",
              visibility = MoveVisibility.PUBLIC,
              isEntry = true,
              isView = false,
              genericTypeParams = emptyList(),
              params = listOf("vector<u8>"),
              `return` = emptyList(),
            ),
          ),
        structs =
          listOf(
            MoveStruct(
              name = "Asset",
              isNative = false,
              abilities = listOf(MoveAbility.KEY),
              genericTypeParams = emptyList(),
              fields = listOf(MoveStructField("value", "u64")),
            )
          ),
      ),
  )
