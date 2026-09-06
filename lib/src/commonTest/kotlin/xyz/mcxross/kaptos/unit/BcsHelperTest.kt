package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.model.*

class BcsHelperTest :
  StringSpec({

    "serializes U8 correctly" { Bcs.encodeToByteArray(U8(1)).toList() shouldBe listOf<Byte>(1) }

    "serializes U8 zero" { Bcs.encodeToByteArray(U8(0)).toList() shouldBe listOf<Byte>(0) }

    "serializes U8 max value" {
      Bcs.encodeToByteArray(U8((-1).toByte())).toList() shouldBe listOf((-1).toByte())
    }

    "U8 roundtrip" {
      val original = U8(42)
      val decoded: U8 = Bcs.decodeFromByteArray(Bcs.encodeToByteArray(original))
      decoded.value shouldBe original.value
    }

    "!U64 roundtrip - BUG: U64Serializer adds ULEB128 length prefix that the deserializer does not consume" {
      val original = U64(123456789UL)
      val decoded: U64 = Bcs.decodeFromByteArray(Bcs.encodeToByteArray(original))
      decoded.value shouldBe original.value
    }

    "MoveString bcsBytes returns standard BCS string encoding" {
      val bcsBytes = MoveString("some string").bcsBytes()
      bcsBytes.toList() shouldBe
        listOf<Byte>(11, 115, 111, 109, 101, 32, 115, 116, 114, 105, 110, 103)
    }

    "MoveString empty bcsBytes" { MoveString("").bcsBytes().toList() shouldBe listOf<Byte>(0) }

    "MoveVector u16 factory" {
      val encoded =
        Bcs.encodeToByteArray(MoveVector.u16(listOf(1.toUShort(), 2.toUShort(), 3.toUShort())))
      encoded.toList() shouldBe listOf<Byte>(3, 1, 0, 2, 0, 3, 0)
    }

    "MoveVector u32 factory" {
      val encoded = Bcs.encodeToByteArray(MoveVector.u32(listOf(1u, 2u, 3u)))
      encoded.toList() shouldBe listOf<Byte>(3, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0)
    }

    "MoveOption Some U8" {
      Bcs.encodeToByteArray(MoveOption(U8(1))).toList() shouldBe listOf<Byte>(1, 1)
    }

    "MoveOption None U8" {
      Bcs.encodeToByteArray(MoveOption<U8>(null)).toList() shouldBe listOf<Byte>(0)
    }

    "MoveVector serialize method returns single zero byte for empty" {
      MoveVector.u8(byteArrayOf()).serialize().toList() shouldBe listOf<Byte>(0)
    }
  })
