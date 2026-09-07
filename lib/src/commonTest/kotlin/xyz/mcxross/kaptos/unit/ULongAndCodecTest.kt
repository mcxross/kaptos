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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.exception.ParsingException
import xyz.mcxross.kaptos.model.AptosDecimalStringULongSerializer
import xyz.mcxross.kaptos.model.PropertyType
import xyz.mcxross.kaptos.model.PropertyValue
import xyz.mcxross.kaptos.model.StructTag
import xyz.mcxross.kaptos.model.encodeAs

class ULongAndCodecTest :
  StringSpec({
    "ULong preserves the full unsigned range as a REST string" {
      val value = ULong.MAX_VALUE
      Json.encodeToString(AptosDecimalStringULongSerializer, value) shouldBe
        "\"18446744073709551615\""
      Json.decodeFromString(AptosDecimalStringULongSerializer, "\"18446744073709551615\"") shouldBe
        value
    }

    "ULong rejects negative and overflowing input" {
      shouldThrow<IllegalArgumentException> {
        Json.decodeFromString(AptosDecimalStringULongSerializer, "\"-1\"")
      }
      shouldThrow<IllegalArgumentException> {
        Json.decodeFromString(AptosDecimalStringULongSerializer, "\"18446744073709551616\"")
      }
    }

    "property integer codecs use validated fixed-width little endian" {
      PropertyValue.NumberValue(65_535).encodeAs(PropertyType.U16).toList() shouldBe
        listOf(0xff.toByte(), 0xff.toByte())
      shouldThrow<IllegalArgumentException> {
        PropertyValue.NumberValue(65_536).encodeAs(PropertyType.U16)
      }
    }

    "property strings and byte vectors include their BCS length" {
      PropertyValue.StringValue("apt").encodeAs(PropertyType.STRING).toList() shouldBe
        listOf(3, 'a'.code, 'p'.code, 't'.code).map(Int::toByte)
      PropertyValue.Uint8ArrayValue(byteArrayOf(1, 2))
        .encodeAs(PropertyType.ARRAY)
        .toList() shouldBe listOf(2, 1, 2).map(Int::toByte)
    }

    "hex parsing validates empty odd and non-hex input" {
      shouldThrow<ParsingException> { Hex.fromString("0x") }
      shouldThrow<ParsingException> { Hex.fromString("0x1") }
      shouldThrow<ParsingException> { Hex.fromString("0xzz") }
      Hex.fromString("0X00ff").toString() shouldBe "0x00ff"
    }

    "StructTag parses nested generic type arguments" {
      StructTag.fromString("0x1::outer::Box<vector<0x1::coin::Coin<u64>>>").toString() shouldBe
        "0x1::outer::Box<vector<0x1::coin::Coin<u64>>>"
    }
  })
