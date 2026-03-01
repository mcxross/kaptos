package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParserError
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParserErrorType

class TypeTagParserTest : StringSpec({

  val TAG_STRUCT_NAME = "0x1::tag::Tag"

  fun assertParseError(input: String, errorType: TypeTagParserErrorType) {
    val ex = shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag(input) }
    ex.invalidReason shouldBe errorType
  }

  // --- Primitive parsing ---

  "parses bool" {
    TypeTagParser.parseTypeTag("bool") shouldBe TypeTagBool
  }

  "parses u8" {
    TypeTagParser.parseTypeTag("u8") shouldBe TypeTagU8
  }

  "parses u16" {
    TypeTagParser.parseTypeTag("u16") shouldBe TypeTagU16
  }

  "parses u32" {
    TypeTagParser.parseTypeTag("u32") shouldBe TypeTagU32
  }

  "parses u64" {
    TypeTagParser.parseTypeTag("u64") shouldBe TypeTagU64
  }

  "parses u128" {
    TypeTagParser.parseTypeTag("u128") shouldBe TypeTagU128
  }

  "parses u256" {
    TypeTagParser.parseTypeTag("u256") shouldBe TypeTagU256
  }

  "parses signer" {
    TypeTagParser.parseTypeTag("signer") shouldBe TypeTagSigner
  }

  "parses address" {
    TypeTagParser.parseTypeTag("address") shouldBe TypeTagAddress
  }

  // --- Primitives with whitespace ---

  "parses primitives with leading whitespace" {
    TypeTagParser.parseTypeTag(" bool") shouldBe TypeTagBool
    TypeTagParser.parseTypeTag(" u64") shouldBe TypeTagU64
  }

  "parses primitives with trailing whitespace" {
    TypeTagParser.parseTypeTag("bool ") shouldBe TypeTagBool
    TypeTagParser.parseTypeTag("u64 ") shouldBe TypeTagU64
  }

  "parses primitives with surrounding whitespace" {
    TypeTagParser.parseTypeTag(" bool ") shouldBe TypeTagBool
    TypeTagParser.parseTypeTag(" u64 ") shouldBe TypeTagU64
  }

  // --- Struct types ---

  "parses string struct tag" {
    val result = TypeTagParser.parseTypeTag("0x1::string::String")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "0x1::string::String"
  }

  "parses aptos coin struct tag" {
    val result = TypeTagParser.parseTypeTag("0x1::aptos_coin::AptosCoin")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "0x1::aptos_coin::AptosCoin"
  }

  "parses option with type arg" {
    val result = TypeTagParser.parseTypeTag("0x1::option::Option<u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "0x1::option::Option<u8>"
  }

  "parses object with type arg" {
    val result = TypeTagParser.parseTypeTag("0x1::object::Object<u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "0x1::object::Object<u8>"
  }

  "parses struct with no type args" {
    val result = TypeTagParser.parseTypeTag(TAG_STRUCT_NAME)
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe TAG_STRUCT_NAME
  }

  "parses struct with single type arg" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<u8>"
  }

  "parses struct with two type args" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<u8, u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<u8, u8>"
  }

  "parses struct with mixed type args" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<u64, u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<u64, u8>"
  }

  "!parses nested struct in first type arg - BUG: parser does not reset innerTypes after comma" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<$TAG_STRUCT_NAME<u8>, u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<$TAG_STRUCT_NAME<u8>, u8>"
  }

  "parses nested struct in second type arg" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<u8, $TAG_STRUCT_NAME<u8>>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<u8, $TAG_STRUCT_NAME<u8>>"
  }

  "!parses deeply nested struct - BUG: same innerTypes leak issue" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<$TAG_STRUCT_NAME<$TAG_STRUCT_NAME<u8>>, u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<$TAG_STRUCT_NAME<$TAG_STRUCT_NAME<u8>>, u8>"
  }

  // --- Vector ---

  "parses vector of primitives" {
    val primitives = listOf("bool", "u8", "u16", "u32", "u64", "u128", "u256", "address", "signer")
    for (prim in primitives) {
      val result = TypeTagParser.parseTypeTag("vector<$prim>")
      result.shouldBeInstanceOf<TypeTagVector>()
      result.toString() shouldBe "vector<$prim>"
    }
  }

  "parses vector with inner whitespace" {
    TypeTagParser.parseTypeTag("vector< u8>").toString() shouldBe "vector<u8>"
    TypeTagParser.parseTypeTag("vector<u8 >").toString() shouldBe "vector<u8>"
    TypeTagParser.parseTypeTag("vector< u8 >").toString() shouldBe "vector<u8>"
  }

  "parses vector of struct" {
    val result = TypeTagParser.parseTypeTag("vector<$TAG_STRUCT_NAME>")
    result.shouldBeInstanceOf<TypeTagVector>()
    result.toString() shouldBe "vector<$TAG_STRUCT_NAME>"
  }

  "parses nested vector" {
    val result = TypeTagParser.parseTypeTag("vector<vector<u8>>")
    result.shouldBeInstanceOf<TypeTagVector>()
    result.toString() shouldBe "vector<vector<u8>>"
  }

  "parses deeply nested vector" {
    val result = TypeTagParser.parseTypeTag("vector<vector<vector<bool>>>")
    result.shouldBeInstanceOf<TypeTagVector>()
    result.toString() shouldBe "vector<vector<vector<bool>>>"
  }

  // --- Generics ---

  "parses generic T0 with allowGenerics" {
    val result = TypeTagParser.parseTypeTag("T0", allowGenerics = true)
    result.shouldBeInstanceOf<TypeTagGeneric>()
    result.id shouldBe 0.toUShort()
  }

  "parses generic T1 with allowGenerics" {
    val result = TypeTagParser.parseTypeTag("T1", allowGenerics = true)
    result.shouldBeInstanceOf<TypeTagGeneric>()
    result.id shouldBe 1.toUShort()
  }

  "parses generic T1337 with allowGenerics" {
    val result = TypeTagParser.parseTypeTag("T1337", allowGenerics = true)
    result.shouldBeInstanceOf<TypeTagGeneric>()
    result.id shouldBe 1337.toUShort()
  }

  "parses struct with generic type arg" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<T0>", allowGenerics = true)
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<T0>"
  }

  "parses struct with multiple generic type args" {
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<T0, T1>", allowGenerics = true)
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<T0, T1>"
  }

  "parses vector with generic inner type" {
    val result = TypeTagParser.parseTypeTag("vector<T0>", allowGenerics = true)
    result.shouldBeInstanceOf<TypeTagVector>()
    result.toString() shouldBe "vector<T0>"
  }

  // --- Reference types ---

  "parses reference to signer" {
    val result = TypeTagParser.parseTypeTag("&signer")
    result.shouldBeInstanceOf<TypeTagReference>()
    result.toString() shouldBe "&signer"
  }

  // --- Option and Object ---

  "parses option types" {
    val types = listOf("bool", "u8", "u16", "u32", "u64", "u128", "u256")
    for (t in types) {
      val result = TypeTagParser.parseTypeTag("0x1::option::Option<$t>")
      result.shouldBeInstanceOf<TypeTagStruct>()
      result.toString() shouldBe "0x1::option::Option<$t>"
    }
  }

  "parses object with struct type arg" {
    val result = TypeTagParser.parseTypeTag("0x1::object::Object<$TAG_STRUCT_NAME>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "0x1::object::Object<$TAG_STRUCT_NAME>"
  }

  "parses object with nested struct type arg" {
    val result = TypeTagParser.parseTypeTag("0x1::object::Object<$TAG_STRUCT_NAME<u8>>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "0x1::object::Object<$TAG_STRUCT_NAME<u8>>"
  }

  // --- Invalid types ---

  "rejects standalone number" {
    assertParseError("8", TypeTagParserErrorType.UnexpectedStructFormat)
  }

  "rejects unknown type name" {
    assertParseError("addr", TypeTagParserErrorType.UnexpectedStructFormat)
  }

  "rejects generic without allowGenerics" {
    assertParseError("T1", TypeTagParserErrorType.UnexpectedGenericType)
  }

  "rejects struct with single colon" {
    assertParseError("0x1:tag::Tag", TypeTagParserErrorType.UnexpectedStructFormat)
    assertParseError("0x1::tag:Tag", TypeTagParserErrorType.UnexpectedStructFormat)
  }

  "rejects invalid inner type" {
    shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<8>") }
  }

  "accepts whitespace before angle bracket" {
    // Unlike TS SDK, the Kotlin parser treats whitespace before '<' as ignorable
    val result = TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME <u8>")
    result.shouldBeInstanceOf<TypeTagStruct>()
    result.toString() shouldBe "$TAG_STRUCT_NAME<u8>"
  }

  "rejects double open angle bracket" {
    shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<<u8>") }
  }

  "rejects extra closing bracket" {
    assertParseError("$TAG_STRUCT_NAME<u8>>", TypeTagParserErrorType.UnexpectedTypeArgumentClose)
  }

  "rejects comma separated types outside brackets" {
    shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("u8, u8") }
    shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("u8,u8") }
  }

  "rejects comma after struct type arg" {
    shouldThrow<TypeTagParserError> {
      TypeTagParser.parseTypeTag("$TAG_STRUCT_NAME<u8>,$TAG_STRUCT_NAME")
    }
  }

  "rejects primitive with type arguments" {
    assertParseError("$TAG_STRUCT_NAME<u8<u8>>", TypeTagParserErrorType.UnexpectedPrimitiveTypeArguments)
  }

  "rejects type args on primitives" {
    val prims = listOf("bool", "u8", "u16", "u32", "u64", "u128", "u256", "signer", "address")
    for (prim in prims) {
      shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("$prim<u8>") }
    }
  }

  "rejects empty type arguments" {
    assertParseError("$TAG_STRUCT_NAME<>", TypeTagParserErrorType.TypeArgumentCountMismatch)
  }

  "rejects trailing comma in type arguments" {
    assertParseError("$TAG_STRUCT_NAME<u8,>", TypeTagParserErrorType.TypeArgumentCountMismatch)
  }

  "rejects empty vector type argument" {
    assertParseError("vector<>", TypeTagParserErrorType.TypeArgumentCountMismatch)
  }

  "rejects vector with multiple type arguments" {
    assertParseError("vector<u8, u8>", TypeTagParserErrorType.UnexpectedVectorTypeArgumentCount)
  }

  "rejects invalid module name character" {
    shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("0x1::not-a-module::Tag<u8>") }
  }

  "rejects invalid struct name character" {
    shouldThrow<TypeTagParserError> { TypeTagParser.parseTypeTag("0x1::tag::Not-A-Name<u8>") }
  }

  // --- Struct tag property verification ---

  "struct tag has correct address" {
    val result = TypeTagParser.parseTypeTag("0x1::coin::Transfer") as TypeTagStruct
    result.type.address shouldBe AccountAddress.ONE
    result.type.moduleName shouldBe "coin"
    result.type.name shouldBe "Transfer"
    result.type.typeArgs.size shouldBe 0
  }

  "struct tag with type args has correct properties" {
    val result = TypeTagParser.parseTypeTag("0x1::coin::Transfer<u64, bool>") as TypeTagStruct
    result.type.address shouldBe AccountAddress.ONE
    result.type.moduleName shouldBe "coin"
    result.type.name shouldBe "Transfer"
    result.type.typeArgs.size shouldBe 2
    result.type.typeArgs[0].shouldBeInstanceOf<TypeTagU64>()
    result.type.typeArgs[1].shouldBeInstanceOf<TypeTagBool>()
  }
})
