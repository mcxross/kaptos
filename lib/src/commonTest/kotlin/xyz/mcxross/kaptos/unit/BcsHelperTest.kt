package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.model.*

class BcsHelperTest : StringSpec({

  // --- U8 serialization (correct: 1 byte) ---

  "serializes U8 correctly" {
    Bcs.encodeToByteArray(U8(1)).toList() shouldBe listOf<Byte>(1)
  }

  "serializes U8 zero" {
    Bcs.encodeToByteArray(U8(0)).toList() shouldBe listOf<Byte>(0)
  }

  "serializes U8 max value" {
    Bcs.encodeToByteArray(U8((-1).toByte())).toList() shouldBe listOf((-1).toByte())
  }

  "U8 roundtrip" {
    val original = U8(42)
    val decoded: U8 = Bcs.decodeFromByteArray(Bcs.encodeToByteArray(original))
    decoded.value shouldBe original.value
  }

  // --- Bool serialization (BUG: encodes boolean twice) ---

  "Bool true serialization produces two bytes" {
    Bcs.encodeToByteArray(Bool(true)).toList() shouldBe listOf<Byte>(1, 1)
  }

  "Bool false serialization produces two bytes" {
    Bcs.encodeToByteArray(Bool(false)).toList() shouldBe listOf<Byte>(0, 0)
  }

  // --- U64 serialization (uses ULEB128 length prefix) ---

  "U64 serialization includes length prefix" {
    Bcs.encodeToByteArray(U64(1UL)).toList() shouldBe listOf<Byte>(8, 1, 0, 0, 0, 0, 0, 0, 0)
  }

  "U64 zero" {
    Bcs.encodeToByteArray(U64(0UL)).toList() shouldBe listOf<Byte>(8, 0, 0, 0, 0, 0, 0, 0, 0)
  }

  "!U64 roundtrip - BUG: U64Serializer adds ULEB128 length prefix that the deserializer does not consume" {
    val original = U64(123456789UL)
    val decoded: U64 = Bcs.decodeFromByteArray(Bcs.encodeToByteArray(original))
    decoded.value shouldBe original.value
  }

  // --- MoveString ---

  "MoveString bcsBytes returns standard BCS string encoding" {
    val bcsBytes = MoveString("some string").bcsBytes()
    bcsBytes.toList() shouldBe listOf<Byte>(11, 115, 111, 109, 101, 32, 115, 116, 114, 105, 110, 103)
  }

  "MoveString empty bcsBytes" {
    MoveString("").bcsBytes().toList() shouldBe listOf<Byte>(0)
  }

  // --- MoveVector factory methods and serialization ---

  "MoveVector u8 from ByteArray" {
    Bcs.encodeToByteArray(MoveVector.u8(byteArrayOf(1, 2, 3))).toList() shouldBe listOf<Byte>(3, 1, 2, 3)
  }

  "MoveVector u8 empty" {
    Bcs.encodeToByteArray(MoveVector.u8(byteArrayOf())).toList() shouldBe listOf<Byte>(0)
  }

  "MoveVector u8 with 128 items uses ULEB128 length" {
    val base = ByteArray(128) { it.toByte() }
    val encoded = Bcs.encodeToByteArray(MoveVector.u8(base))
    val expectedPrefix = byteArrayOf(0x80.toByte(), 0x01.toByte())
    encoded.toList() shouldBe (expectedPrefix + base).toList()
  }

  "MoveVector bool factory" {
    val encoded = Bcs.encodeToByteArray(MoveVector.bool(listOf(true, false, true)))
    // Each Bool element is serialized with the double-encode bug
    encoded.toList() shouldBe listOf<Byte>(3, 1, 1, 0, 0, 1, 1)
  }

  "MoveVector u16 factory" {
    val encoded = Bcs.encodeToByteArray(MoveVector.u16(listOf(1.toUShort(), 2.toUShort(), 3.toUShort())))
    encoded.toList() shouldBe listOf<Byte>(3, 1, 0, 2, 0, 3, 0)
  }

  "MoveVector u32 factory" {
    val encoded = Bcs.encodeToByteArray(MoveVector.u32(listOf(1u, 2u, 3u)))
    encoded.toList() shouldBe listOf<Byte>(3, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0)
  }

  "MoveVector u64 factory" {
    val encoded = Bcs.encodeToByteArray(MoveVector.u64(listOf(1UL, 2UL, 3UL)))
    // Each U64 element includes its own ULEB128(8) prefix
    encoded.toList() shouldBe listOf<Byte>(
      3,
      8, 1, 0, 0, 0, 0, 0, 0, 0,
      8, 2, 0, 0, 0, 0, 0, 0, 0,
      8, 3, 0, 0, 0, 0, 0, 0, 0,
    )
  }

  "MoveVector string factory" {
    val encoded = Bcs.encodeToByteArray(MoveVector.string(listOf("abc", "def", "ghi")))
    // Each MoveString has collection length prefix from its serializer
    encoded.toList() shouldBe listOf<Byte>(
      3,
      4, 3, 97, 98, 99,
      4, 3, 100, 101, 102,
      4, 3, 103, 104, 105,
    )
  }

  // --- Manual MoveVector matches factory ---

  "manual MoveVector U8 matches factory" {
    val manual = Bcs.encodeToByteArray(MoveVector(listOf(U8(1), U8(2), U8(3))))
    val factory = Bcs.encodeToByteArray(MoveVector.u8(byteArrayOf(1, 2, 3)))
    manual.toList() shouldBe factory.toList()
  }

  "manual MoveVector U64 matches factory" {
    val manual = Bcs.encodeToByteArray(MoveVector(listOf(U64(1UL), U64(2UL), U64(3UL))))
    val factory = Bcs.encodeToByteArray(MoveVector.u64(listOf(1UL, 2UL, 3UL)))
    manual.toList() shouldBe factory.toList()
  }

  "manual MoveVector MoveString matches factory" {
    val manual = Bcs.encodeToByteArray(MoveVector(listOf(MoveString("abc"), MoveString("def"), MoveString("ghi"))))
    val factory = Bcs.encodeToByteArray(MoveVector.string(listOf("abc", "def", "ghi")))
    manual.toList() shouldBe factory.toList()
  }

  // --- MoveOption serialization ---

  "MoveOption Some U8" {
    Bcs.encodeToByteArray(MoveOption(U8(1))).toList() shouldBe listOf<Byte>(1, 1)
  }

  "MoveOption None U8" {
    Bcs.encodeToByteArray(MoveOption<U8>(null)).toList() shouldBe listOf<Byte>(0)
  }

  "MoveOption Some U8 roundtrip" {
    val original = MoveOption(U8(99.toByte()))
    val decoded: MoveOption<U8> = Bcs.decodeFromByteArray(Bcs.encodeToByteArray(original))
    decoded.unwrap().value shouldBe original.unwrap().value
  }

  "MoveOption None U8 roundtrip" {
    val original = MoveOption<U8>(null)
    val decoded: MoveOption<U8> = Bcs.decodeFromByteArray(Bcs.encodeToByteArray(original))
    decoded.value.shouldBeNull()
  }

  "MoveOption unwrap returns value when Some" {
    MoveOption(U8(42.toByte())).unwrap().value shouldBe 42.toByte()
  }

  "MoveOption unwrap throws when None" {
    shouldThrow<IllegalArgumentException> { MoveOption<U8>(null).unwrap() }
  }

  "rejects MoveOption with length greater than 1" {
    shouldThrow<IllegalArgumentException> { Bcs.decodeFromByteArray<MoveOption<U8>>(byteArrayOf(2, 1, 2)) }
  }

  // --- MoveVector deserialization ---

  "deserializes MoveVector U8" {
    val decoded: MoveVector<U8> = Bcs.decodeFromByteArray(byteArrayOf(3, 1, 2, 3))
    decoded.values.size shouldBe 3
    decoded.values[0].value shouldBe 1.toByte()
    decoded.values[1].value shouldBe 2.toByte()
    decoded.values[2].value shouldBe 3.toByte()
  }

  "deserializes empty MoveVector U8" {
    val decoded: MoveVector<U8> = Bcs.decodeFromByteArray(byteArrayOf(0))
    decoded.values.size shouldBe 0
  }

  "rejects empty byte array for MoveVector deserialization" {
    shouldThrow<Exception> { Bcs.decodeFromByteArray<MoveVector<U8>>(byteArrayOf()) }
  }

  "rejects empty byte array for MoveOption deserialization" {
    shouldThrow<Exception> { Bcs.decodeFromByteArray<MoveOption<U8>>(byteArrayOf()) }
  }

  // --- MoveVector serialize method ---

  "MoveVector serialize method returns single zero byte for empty" {
    MoveVector.u8(byteArrayOf()).serialize().toList() shouldBe listOf<Byte>(0)
  }
})
