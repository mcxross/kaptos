/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosCoin
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.MoveStructType
import xyz.mcxross.kaptos.model.MoveTypeRegistry
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.TypeTagAddress
import xyz.mcxross.kaptos.model.TypeTagBool
import xyz.mcxross.kaptos.model.TypeTagI128
import xyz.mcxross.kaptos.model.TypeTagI16
import xyz.mcxross.kaptos.model.TypeTagI256
import xyz.mcxross.kaptos.model.TypeTagI32
import xyz.mcxross.kaptos.model.TypeTagI64
import xyz.mcxross.kaptos.model.TypeTagI8
import xyz.mcxross.kaptos.model.TypeTagSigner
import xyz.mcxross.kaptos.model.TypeTagU128
import xyz.mcxross.kaptos.model.TypeTagU16
import xyz.mcxross.kaptos.model.TypeTagU256
import xyz.mcxross.kaptos.model.TypeTagU32
import xyz.mcxross.kaptos.model.TypeTagU64
import xyz.mcxross.kaptos.model.TypeTagU8
import xyz.mcxross.kaptos.model.TypeTagVector
import xyz.mcxross.kaptos.model.toTypeTag
import xyz.mcxross.kaptos.model.toTypeTags
import xyz.mcxross.kaptos.model.typeTag
import xyz.mcxross.kaptos.model.typeTagOf
import xyz.mcxross.kaptos.transaction.build

private object CustomTestCoin : MoveStructType("0x42::my_coin::MoonCoin")

class TypeTagErgonomicsTest :
  StringSpec({
    "TypeTag invoke operator parses strings" {
      TypeTag("0x1::aptos_coin::AptosCoin") shouldBe TypeTag.AptosCoin
      TypeTag("vector<u8>") shouldBe TypeTagVector(TypeTagU8)
      TypeTag("address") shouldBe TypeTagAddress
      TypeTag("u64") shouldBe TypeTagU64
    }

    "String toTypeTag extension parses correctly" {
      "0x1::aptos_coin::AptosCoin".toTypeTag() shouldBe TypeTag.AptosCoin
      "vector<u8>".toTypeTag() shouldBe TypeTagVector(TypeTagU8)
      "u64".toTypeTag() shouldBe TypeTagU64

      val list = listOf("0x1::aptos_coin::AptosCoin", "u64").toTypeTags()
      list shouldBe listOf(TypeTag.AptosCoin, TypeTagU64)
    }

    "TypeTag from and fromAll polymorphic converters" {
      TypeTag.from(TypeTagU64) shouldBe TypeTagU64
      TypeTag.from("u64") shouldBe TypeTagU64
      TypeTag.from("0x1::aptos_coin::AptosCoin") shouldBe TypeTag.AptosCoin
      TypeTag.from(AptosCoin) shouldBe TypeTag.AptosCoin

      val tags = TypeTag.fromAll("u64", AptosCoin, TypeTagAddress)
      tags shouldBe listOf(TypeTagU64, TypeTag.AptosCoin, TypeTagAddress)

      val anyTags = listOf("u64", AptosCoin).toTypeTags()
      anyTags shouldBe listOf(TypeTagU64, TypeTag.AptosCoin)
    }

    "Predefined singletons and factories" {
      TypeTag.Bool shouldBe TypeTagBool
      TypeTag.U8 shouldBe TypeTagU8
      TypeTag.U16 shouldBe TypeTagU16
      TypeTag.U32 shouldBe TypeTagU32
      TypeTag.U64 shouldBe TypeTagU64
      TypeTag.U128 shouldBe TypeTagU128
      TypeTag.U256 shouldBe TypeTagU256
      TypeTag.I8 shouldBe TypeTagI8
      TypeTag.I16 shouldBe TypeTagI16
      TypeTag.I32 shouldBe TypeTagI32
      TypeTag.I64 shouldBe TypeTagI64
      TypeTag.I128 shouldBe TypeTagI128
      TypeTag.I256 shouldBe TypeTagI256
      TypeTag.Address shouldBe TypeTagAddress
      TypeTag.Signer shouldBe TypeTagSigner
      TypeTag.APT shouldBe TypeTag.AptosCoin

      TypeTag.vector(TypeTag.U8) shouldBe TypeTagVector(TypeTagU8)
      TypeTag.vector("u8") shouldBe TypeTagVector(TypeTagU8)
      TypeTag.option("u64").toString() shouldBe "0x1::option::Option<u64>"
      TypeTag.objectTag(TypeTag.AptosCoin).toString() shouldBe
        "0x1::object::Object<0x1::aptos_coin::AptosCoin>"
      TypeTag.struct("0x1::aptos_coin::AptosCoin") shouldBe TypeTag.AptosCoin
    }

    "typeTagOf infers standard primitives and classes" {
      typeTagOf<Boolean>() shouldBe TypeTagBool
      typeTagOf<UByte>() shouldBe TypeTagU8
      typeTagOf<UShort>() shouldBe TypeTagU16
      typeTagOf<UInt>() shouldBe TypeTagU32
      typeTagOf<ULong>() shouldBe TypeTagU64
      typeTagOf<Byte>() shouldBe TypeTagI8
      typeTagOf<Short>() shouldBe TypeTagI16
      typeTagOf<Int>() shouldBe TypeTagI32
      typeTagOf<Long>() shouldBe TypeTagI64
      typeTagOf<AccountAddress>() shouldBe TypeTagAddress
      typeTagOf<String>() shouldBe TypeTag.String
      typeTagOf<ByteArray>() shouldBe TypeTagVector(TypeTagU8)
      typeTagOf<AptosCoin>() shouldBe TypeTag.AptosCoin

      // alias check
      typeTag<ULong>() shouldBe TypeTagU64
    }

    "MoveTypeRegistry supports custom types" {
      MoveTypeRegistry.register<CustomTestCoin>("0x42::my_coin::MoonCoin")
      typeTagOf<CustomTestCoin>() shouldBe TypeTag("0x42::my_coin::MoonCoin")
    }

    "TransactionPayload entryFunctionOf with string type arguments" {
      val payload =
        TransactionPayload.entryFunctionOf(
          "0x1::coin::transfer",
          listOf("0x1::aptos_coin::AptosCoin"),
          AccountAddress.ONE,
          1000uL,
        )

      payload.call.typeArguments shouldBe listOf(TypeTag.AptosCoin)
    }

    "TransactionService build with string and reified type arguments" {
      val aptos = Aptos(AptosConfig(Network.DEVNET))

      val resStrings =
        aptos.transactions.build(
          sender = AccountAddress.ONE,
          function = "0x1::coin::transfer",
          typeArguments = listOf("0x1::aptos_coin::AptosCoin"),
          arguments = listOf(AccountAddress.ONE, 1000uL),
          options = TransactionOptions(replayProtection = ReplayProtection.SequenceNumber(0uL)),
        )

      val txStrings = resStrings.shouldBeInstanceOf<AptosResult.Success<*>>().value
      txStrings.shouldBeInstanceOf<xyz.mcxross.kaptos.model.UnsignedTransaction.Simple>()

      val resReified =
        aptos.transactions.build<AptosCoin>(
          sender = AccountAddress.ONE,
          function = "0x1::coin::transfer",
          arguments = listOf(AccountAddress.ONE, 1000uL),
          options = TransactionOptions(replayProtection = ReplayProtection.SequenceNumber(0uL)),
        )

      val txReified = resReified.shouldBeInstanceOf<AptosResult.Success<*>>().value
      txReified.shouldBeInstanceOf<xyz.mcxross.kaptos.model.UnsignedTransaction.Simple>()
    }
  })
