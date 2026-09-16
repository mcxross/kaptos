/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.move.toMoveArgument
import xyz.mcxross.kaptos.move.toMoveArguments

class MoveArgumentInferenceTest :
  StringSpec({
    val address = AccountAddress.fromString("0x1")

    "MoveArgument.from maps Kotlin standard and unsigned types faithfully" {
      MoveArgument.from(true) shouldBe MoveArgument.Bool(true)
      MoveArgument.from(false) shouldBe MoveArgument.Bool(false)

      MoveArgument.from(42u.toUByte()) shouldBe MoveArgument.U8(42u)
      MoveArgument.from(1000u.toUShort()) shouldBe MoveArgument.U16(1000u)
      MoveArgument.from(100_000u) shouldBe MoveArgument.U32(100_000u)
      MoveArgument.from(1_000_000uL) shouldBe MoveArgument.U64(1_000_000uL)

      MoveArgument.from((-10).toByte()) shouldBe MoveArgument.I8(-10)
      MoveArgument.from((-200).toShort()) shouldBe MoveArgument.I16(-200)
      MoveArgument.from(-50_000) shouldBe MoveArgument.I32(-50_000)
      MoveArgument.from(-9_000_000_000L) shouldBe MoveArgument.I64(-9_000_000_000L)

      MoveArgument.from(address) shouldBe MoveArgument.Address(address)

      val addressInput =
        object : AccountAddressInput {
          override val value: String = "0x1"
        }
      MoveArgument.from(addressInput) shouldBe MoveArgument.Address(address)

      val bytes = byteArrayOf(1, 2, 3)
      val bytesArg = MoveArgument.from(bytes) as MoveArgument.Bytes
      bytesArg.value shouldBe bytes

      MoveArgument.from("aptos") shouldBe MoveArgument.StringValue("aptos")
      MoveArgument.from(null) shouldBe MoveArgument.Option(null)
    }

    "MoveArgument.from maps nested collections and maps recursively" {
      val list = listOf(10uL, 20uL)
      MoveArgument.from(list) shouldBe
        MoveArgument.Vector(listOf(MoveArgument.U64(10uL), MoveArgument.U64(20uL)))

      val array = arrayOf(1u, 2u)
      MoveArgument.from(array) shouldBe
        MoveArgument.Vector(listOf(MoveArgument.U32(1u), MoveArgument.U32(2u)))

      val map = mapOf("amount" to 500uL, "active" to true)
      MoveArgument.from(map) shouldBe
        MoveArgument.Struct(
          mapOf(
            "amount" to MoveArgument.U64(500uL),
            "active" to MoveArgument.Bool(true),
          )
        )
    }

    "MoveArgument.from passes through existing MoveArgument instances" {
      val explicitU64 = MoveArgument.U64(99uL)
      MoveArgument.from(explicitU64) shouldBe explicitU64

      val explicitU128 = MoveArgument.U128("340282366920938463463374607431768211455")
      MoveArgument.from(explicitU128) shouldBe explicitU128

      val explicitAddress = MoveArgument.Address(address)
      MoveArgument.from(explicitAddress) shouldBe explicitAddress
    }

    "MoveArgument.from throws IllegalArgumentException for unsupported types" {
      class UnsupportedType

      shouldThrow<IllegalArgumentException> {
        MoveArgument.from(UnsupportedType())
      }
    }

    "extension functions toMoveArgument and toMoveArguments work as expected" {
      100uL.toMoveArgument() shouldBe MoveArgument.U64(100uL)
      address.toMoveArgument() shouldBe MoveArgument.Address(address)

      listOf(address, 500uL).toMoveArguments() shouldBe
        listOf(MoveArgument.Address(address), MoveArgument.U64(500uL))
    }

    "entryFunctionOf matches long-hand entryFunction payload exactly" {
      val longHand =
        TransactionPayload.entryFunction(
          function = "0x1::aptos_account::transfer",
          arguments = listOf(MoveArgument.Address(address), MoveArgument.U64(1_000_000uL)),
        )

      val conciseVararg =
        TransactionPayload.entryFunctionOf(
          "0x1::aptos_account::transfer",
          address,
          1_000_000uL,
        )

      conciseVararg shouldBe longHand
      conciseVararg.toBcs() shouldBe longHand.toBcs()
    }

    "entryFunctionOf with typeArguments matches long-hand payload" {
      val typeTag = TypeTag.fromString("0x1::aptos_coin::AptosCoin")

      val longHand =
        TransactionPayload.entryFunction(
          function = "0x1::coin::transfer",
          typeArguments = listOf(typeTag),
          arguments = listOf(MoveArgument.Address(address), MoveArgument.U64(5_000uL)),
        )

      val concise =
        TransactionPayload.entryFunctionOf(
          "0x1::coin::transfer",
          listOf(typeTag),
          address,
          5_000uL,
        )

      concise shouldBe longHand
      concise.toBcs() shouldBe longHand.toBcs()
    }

    "entryFunction DSL builder matches long-hand payload" {
      val longHand =
        TransactionPayload.entryFunction(
          function = "0x1::aptos_account::transfer",
          arguments = listOf(MoveArgument.Address(address), MoveArgument.U64(1_000_000uL)),
        )

      val dslUnaryPlus =
        TransactionPayload.entryFunction("0x1::aptos_account::transfer") {
          +address
          +1_000_000uL
        }

      val dslArg =
        TransactionPayload.entryFunction("0x1::aptos_account::transfer") {
          arg(address)
          arg(1_000_000uL)
        }

      val dslArgs =
        TransactionPayload.entryFunction("0x1::aptos_account::transfer") {
          args(address, 1_000_000uL)
        }

      dslUnaryPlus shouldBe longHand
      dslArg shouldBe longHand
      dslArgs shouldBe longHand
      dslUnaryPlus.toBcs() shouldBe longHand.toBcs()
    }

    "mixing raw Kotlin values and explicit MoveArgument instances works seamlessly" {
      val longHand =
        TransactionPayload.entryFunction(
          function = "0x1::aptos_account::transfer",
          arguments = listOf(MoveArgument.Address(address), MoveArgument.U64(1_000_000uL)),
        )

      val mixedVararg =
        TransactionPayload.entryFunctionOf(
          "0x1::aptos_account::transfer",
          address, // raw AccountAddress
          MoveArgument.U64(1_000_000uL), // explicit MoveArgument
        )

      val mixedDsl =
        TransactionPayload.entryFunction("0x1::aptos_account::transfer") {
          +address
          +MoveArgument.U64(1_000_000uL)
        }

      mixedVararg shouldBe longHand
      mixedDsl shouldBe longHand
    }
  })
