package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser

class TypeTagTest :
  StringSpec({
    "TypeTagBool type checks" {
      TypeTagBool.isBool() shouldBe true
      TypeTagBool.isU8() shouldBe false
      TypeTagBool.isU64() shouldBe false
      TypeTagBool.isVector() shouldBe false
      TypeTagBool.isStruct() shouldBe false
      TypeTagBool.isGeneric() shouldBe false
      TypeTagBool.isSigner() shouldBe false
      TypeTagBool.isAddress() shouldBe false
      TypeTagBool.isReference() shouldBe false
      TypeTagBool.toString() shouldBe "bool"
    }

    "TypeTagU8 type checks" {
      TypeTagU8.isU8() shouldBe true
      TypeTagU8.isBool() shouldBe false
      TypeTagU8.isU16() shouldBe false
      TypeTagU8.toString() shouldBe "u8"
    }

    "TypeTagU16 type checks" {
      TypeTagU16.isU16() shouldBe true
      TypeTagU16.isU8() shouldBe false
      TypeTagU16.toString() shouldBe "u16"
    }

    "TypeTagU32 type checks" {
      TypeTagU32.isU32() shouldBe true
      TypeTagU32.isU64() shouldBe false
      TypeTagU32.toString() shouldBe "u32"
    }

    "TypeTagU64 type checks" {
      TypeTagU64.isU64() shouldBe true
      TypeTagU64.isU128() shouldBe false
      TypeTagU64.toString() shouldBe "u64"
    }

    "TypeTagU128 type checks" {
      TypeTagU128.isU128() shouldBe true
      TypeTagU128.isU256() shouldBe false
      TypeTagU128.toString() shouldBe "u128"
    }

    "TypeTagU256 type checks" {
      TypeTagU256.isU256() shouldBe true
      TypeTagU256.isU128() shouldBe false
      TypeTagU256.toString() shouldBe "u256"
    }

    "TypeTagAddress type checks" {
      TypeTagAddress.isAddress() shouldBe true
      TypeTagAddress.isSigner() shouldBe false
      TypeTagAddress.toString() shouldBe "address"
    }

    "TypeTagSigner type checks" {
      TypeTagSigner.isSigner() shouldBe true
      TypeTagSigner.isAddress() shouldBe false
      TypeTagSigner.toString() shouldBe "signer"
    }

    "TypeTagVector type checks" {
      val tag = TypeTagVector(TypeTagU32)
      tag.isVector() shouldBe true
      tag.isStruct() shouldBe false
      tag.isBool() shouldBe false
      tag.toString() shouldBe "vector<u32>"
    }

    "TypeTagVector u8 factory" {
      val tag = TypeTagVector.u8()
      tag.isVector() shouldBe true
      tag.type.shouldBeInstanceOf<TypeTagU8>()
      tag.toString() shouldBe "vector<u8>"
    }

    "TypeTagGeneric type checks" {
      val tag = TypeTagGeneric(0.toUShort())
      tag.isGeneric() shouldBe true
      tag.isStruct() shouldBe false
      tag.toString() shouldBe "T0"
    }

    "TypeTagGeneric with different ids" {
      TypeTagGeneric(0.toUShort()).toString() shouldBe "T0"
      TypeTagGeneric(1.toUShort()).toString() shouldBe "T1"
      TypeTagGeneric(42.toUShort()).toString() shouldBe "T42"
    }

    "TypeTagReference type checks" {
      val tag = TypeTagReference(TypeTagSigner)
      tag.isReference() shouldBe true
      tag.isSigner() shouldBe false
      tag.toString() shouldBe "&signer"
    }

    "TypeTagStruct type checks" {
      val tag = TypeTagParser.parseTypeTag("0x1::some_module::SomeResource")
      tag.isStruct() shouldBe true
      tag.isVector() shouldBe false
      tag.isBool() shouldBe false
    }

    "TypeTagStruct properties" {
      val tag = TypeTagParser.parseTypeTag("0x1::some_module::SomeResource") as TypeTagStruct
      tag.type.address shouldBe AccountAddress.ONE
      tag.type.moduleName shouldBe "some_module"
      tag.type.name shouldBe "SomeResource"
      tag.type.typeArgs.size shouldBe 0
    }

    "TypeTagStruct toString" {
      TypeTagParser.parseTypeTag("0x1::some_module::SomeResource").toString() shouldBe
        "0x1::some_module::SomeResource"
    }

    "TypeTagStruct with type args toString" {
      val tag = TypeTagParser.parseTypeTag("0x1::coin::Coin<0x1::aptos_coin::AptosCoin>")
      tag.toString() shouldBe "0x1::coin::Coin<0x1::aptos_coin::AptosCoin>"
    }

    "TypeTagStruct isString check" {
      val stringTag = TypeTagParser.parseTypeTag("0x1::string::String") as TypeTagStruct
      stringTag.isString() shouldBe true

      val nonStringTag = TypeTagParser.parseTypeTag("0x1::coin::Coin") as TypeTagStruct
      nonStringTag.isString() shouldBe false
    }

    "TypeTag valueOf parses primitives" {
      TypeTag.valueOf("bool") shouldBe TypeTagBool
      TypeTag.valueOf("u8") shouldBe TypeTagU8
      TypeTag.valueOf("u16") shouldBe TypeTagU16
      TypeTag.valueOf("u32") shouldBe TypeTagU32
      TypeTag.valueOf("u64") shouldBe TypeTagU64
      TypeTag.valueOf("u128") shouldBe TypeTagU128
      TypeTag.valueOf("u256") shouldBe TypeTagU256
      TypeTag.valueOf("address") shouldBe TypeTagAddress
      TypeTag.valueOf("signer") shouldBe TypeTagSigner
    }

    "aptosCoinStructTag helper" {
      val tag = aptosCoinStructTag()
      tag.address shouldBe AccountAddress.ONE
      tag.moduleName shouldBe "aptos_coin"
      tag.name shouldBe "AptosCoin"
      tag.typeArgs.size shouldBe 0
    }

    "stringStructTag helper" {
      val tag = stringStructTag()
      tag.address shouldBe AccountAddress.ONE
      tag.moduleName shouldBe "string"
      tag.name shouldBe "String"
      tag.typeArgs.size shouldBe 0
    }

    "optionStructTag helper" {
      val tag = optionStructTag(TypeTagU8)
      tag.address shouldBe AccountAddress.ONE
      tag.moduleName shouldBe "option"
      tag.name shouldBe "Option"
      tag.typeArgs.size shouldBe 1
      tag.typeArgs[0].shouldBeInstanceOf<TypeTagU8>()
    }

    "objectStructTag helper" {
      val tag = objectStructTag(TypeTagU8)
      tag.address shouldBe AccountAddress.ONE
      tag.moduleName shouldBe "object"
      tag.name shouldBe "Object"
      tag.typeArgs.size shouldBe 1
      tag.typeArgs[0].shouldBeInstanceOf<TypeTagU8>()
    }

    "nested vector toString" {
      TypeTagVector(TypeTagVector(TypeTagU8)).toString() shouldBe "vector<vector<u8>>"
    }

    "struct with nested struct type args" {
      val inner = TypeTagStruct(StructTag(AccountAddress.ONE, "coin", "Coin", listOf(TypeTagU64)))
      val outer =
        TypeTagStruct(StructTag(AccountAddress.ONE, "wrapper", "Wrap", listOf(inner, TypeTagBool)))
      outer.toString() shouldBe "0x1::wrapper::Wrap<0x1::coin::Coin<u64>, bool>"
    }
  })
