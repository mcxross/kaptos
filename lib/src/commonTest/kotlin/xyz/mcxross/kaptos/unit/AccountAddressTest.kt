/*
 * Copyright 2024 McXross
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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import xyz.mcxross.kaptos.exception.ParsingError
import xyz.mcxross.kaptos.model.AccountAddress

class AccountAddressTest {

  private val ADDRESS_ZERO: Addresses =
    mapOf(
      "shortWith0x" to "0x0",
      "shortWithout0x" to "0",
      "longWith0x" to "0x0000000000000000000000000000000000000000000000000000000000000000",
      "longWithout0x" to "0000000000000000000000000000000000000000000000000000000000000000",
      "bytes" to ByteArray(32) { 0 },
    )

  private val ADDRESS_ONE: Addresses =
    mapOf(
      "shortWith0x" to "0x1",
      "shortWithout0x" to "1",
      "longWith0x" to "0x0000000000000000000000000000000000000000000000000000000000000001",
      "longWithout0x" to "0000000000000000000000000000000000000000000000000000000000000001",
      "bytes" to ByteArray(32) { if (it == 31) 1 else 0 },
    )

  private val ADDRESS_TWO: Addresses =
    mapOf(
      "shortWith0x" to "0x2",
      "shortWithout0x" to "2",
      "longWith0x" to "0x0000000000000000000000000000000000000000000000000000000000000002",
      "longWithout0x" to "0000000000000000000000000000000000000000000000000000000000000002",
      "bytes" to ByteArray(32) { if (it == 31) 2 else 0 },
    )

  private val ADDRESS_THREE: Addresses =
    mapOf(
      "shortWith0x" to "0x3",
      "shortWithout0x" to "3",
      "longWith0x" to "0x0000000000000000000000000000000000000000000000000000000000000003",
      "longWithout0x" to "0000000000000000000000000000000000000000000000000000000000000003",
      "bytes" to ByteArray(32) { if (it == 31) 3 else 0 },
    )

  private val ADDRESS_FOUR: Addresses =
    mapOf(
      "shortWith0x" to "0x4",
      "shortWithout0x" to "4",
      "longWith0x" to "0x0000000000000000000000000000000000000000000000000000000000000004",
      "longWithout0x" to "0000000000000000000000000000000000000000000000000000000000000004",
      "bytes" to ByteArray(32) { if (it == 31) 4 else 0 },
    )

  private val ADDRESS_F: Addresses =
    mapOf(
      "shortWith0x" to "0xf",
      "shortWithout0x" to "f",
      "longWith0x" to "0x000000000000000000000000000000000000000000000000000000000000000f",
      "longWithout0x" to "000000000000000000000000000000000000000000000000000000000000000f",
      "bytes" to ByteArray(32) { if (it == 31) 15 else 0 },
    )

  val ADDRESS_F_PADDED_SHORT_FORM: Addresses =
    mapOf(
      "shortWith0x" to "0x0f",
      "shortWithout0x" to "0f",
      "longWith0x" to "0x000000000000000000000000000000000000000000000000000000000000000f",
      "longWithout0x" to "000000000000000000000000000000000000000000000000000000000000000f",
      "bytes" to ByteArray(32) { if (it == 31) 15 else 0 },
    )

  val ADDRESS_TEN: Addresses =
    mapOf(
      "shortWith0x" to "0x10",
      "shortWithout0x" to "10",
      "longWith0x" to "0x0000000000000000000000000000000000000000000000000000000000000010",
      "longWithout0x" to "0000000000000000000000000000000000000000000000000000000000000010",
      "bytes" to ByteArray(32) { if (it == 31) 16 else 0 },
    )

  val ADDRESS_OTHER: Addresses =
    mapOf(
      "shortWith0x" to "0xca843279e3427144cead5e4d5999a3d0ca843279e3427144cead5e4d5999a3d0",
      "shortWithout0x" to "ca843279e3427144cead5e4d5999a3d0ca843279e3427144cead5e4d5999a3d0",
      "longWith0x" to "0xca843279e3427144cead5e4d5999a3d0ca843279e3427144cead5e4d5999a3d0",
      "longWithout0x" to "ca843279e3427144cead5e4d5999a3d0ca843279e3427144cead5e4d5999a3d0",
      "bytes" to
        byteArrayOf(
          202.toByte(),
          132.toByte(),
          50.toByte(),
          121.toByte(),
          227.toByte(),
          66.toByte(),
          113.toByte(),
          68.toByte(),
          206.toByte(),
          173.toByte(),
          94.toByte(),
          77.toByte(),
          89.toByte(),
          153.toByte(),
          163.toByte(),
          208.toByte(),
          202.toByte(),
          132.toByte(),
          50.toByte(),
          121.toByte(),
          227.toByte(),
          66.toByte(),
          113.toByte(),
          68.toByte(),
          206.toByte(),
          173.toByte(),
          94.toByte(),
          77.toByte(),
          89.toByte(),
          153.toByte(),
          163.toByte(),
          208.toByte(),
        ),
    )

  // These tests show that fromStringRelaxed works fine, parses all formats.
  @Test
  fun testFromStringRelaxed() {

    assertEquals(
      ADDRESS_ZERO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ZERO["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_ZERO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ZERO["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_ZERO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ZERO["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_ZERO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ZERO["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_ONE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ONE["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_ONE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ONE["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_ONE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ONE["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_ONE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_ONE["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_TWO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_TWO["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_TWO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_TWO["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_TWO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_TWO["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_TWO["shortWith0x"],
      AccountAddress.fromString(ADDRESS_TWO["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_THREE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_THREE["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_THREE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_THREE["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_THREE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_THREE["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_THREE["shortWith0x"],
      AccountAddress.fromString(ADDRESS_THREE["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_FOUR["shortWith0x"],
      AccountAddress.fromString(ADDRESS_FOUR["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_FOUR["shortWith0x"],
      AccountAddress.fromString(ADDRESS_FOUR["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_FOUR["shortWith0x"],
      AccountAddress.fromString(ADDRESS_FOUR["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_FOUR["shortWith0x"],
      AccountAddress.fromString(ADDRESS_FOUR["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_F["shortWith0x"],
      AccountAddress.fromString(ADDRESS_F["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_F["shortWith0x"],
      AccountAddress.fromString(ADDRESS_F["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_F["shortWith0x"],
      AccountAddress.fromString(ADDRESS_F["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_F["shortWith0x"],
      AccountAddress.fromString(ADDRESS_F["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_F["shortWith0x"].toString(),
      AccountAddress.fromString(ADDRESS_F_PADDED_SHORT_FORM["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_F["shortWith0x"].toString(),
      AccountAddress.fromString(ADDRESS_F_PADDED_SHORT_FORM["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_TEN["longWith0x"],
      AccountAddress.fromString(ADDRESS_TEN["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_TEN["longWith0x"],
      AccountAddress.fromString(ADDRESS_TEN["longWithout0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_TEN["longWith0x"],
      AccountAddress.fromString(ADDRESS_TEN["shortWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_TEN["longWith0x"],
      AccountAddress.fromString(ADDRESS_TEN["shortWithout0x"].toString()).toString(),
    )

    assertEquals(
      ADDRESS_OTHER["longWith0x"],
      AccountAddress.fromString(ADDRESS_OTHER["longWith0x"].toString()).toString(),
    )
    assertEquals(
      ADDRESS_OTHER["longWith0x"],
      AccountAddress.fromString(ADDRESS_OTHER["longWithout0x"].toString()).toString(),
    )
  }

  // Tests ensure that the constant special addresses in the static AccountAddress class are
  // correct.
  @Test
  fun testStaticSpecialAddresses() {
    assertEquals(ADDRESS_ZERO["shortWith0x"], AccountAddress.ZERO.toString())
    assertEquals(ADDRESS_ONE["shortWith0x"], AccountAddress.ONE.toString())
    assertEquals(ADDRESS_TWO["shortWith0x"], AccountAddress.TWO.toString())
    assertEquals(ADDRESS_THREE["shortWith0x"], AccountAddress.THREE.toString())
    assertEquals(ADDRESS_FOUR["shortWith0x"], AccountAddress.FOUR.toString())
  }

  @Test
  fun testFromString() {
    assertEquals(
      ADDRESS_ZERO["shortWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_ZERO["longWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError>("Address must be 32 bytes long") {
      AccountAddress.fromStringStrict(ADDRESS_ZERO["longWithout0x"].toString())
    }
    assertEquals(
      ADDRESS_ZERO["shortWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_ZERO["shortWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_ZERO["shortWithout0x"].toString())
    }

    assertEquals(
      ADDRESS_ONE["shortWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_ONE["longWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_ONE["longWithout0x"].toString())
    }
    assertEquals(
      ADDRESS_ONE["shortWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_ONE["shortWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_ONE["shortWithout0x"].toString())
    }

    assertEquals(
      ADDRESS_F["shortWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_F["longWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_F["longWithout0x"].toString())
    }
    assertEquals(
      ADDRESS_F["shortWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_F["shortWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_F["shortWithout0x"].toString())
    }

    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_F_PADDED_SHORT_FORM["shortWith0x"].toString())
    }
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_F_PADDED_SHORT_FORM["shortWithout0x"].toString())
    }

    assertEquals(
      ADDRESS_TEN["longWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_TEN["longWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_TEN["longWithout0x"].toString())
    }
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_TEN["shortWith0x"].toString())
    }
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_TEN["shortWithout0x"].toString())
    }

    assertEquals(
      ADDRESS_OTHER["longWith0x"],
      AccountAddress.fromStringStrict(ADDRESS_OTHER["longWith0x"].toString()).toString(),
    )
    assertFailsWith<ParsingError> {
      AccountAddress.fromStringStrict(ADDRESS_OTHER["longWithout0x"].toString())
    }
  }
}

typealias Addresses = Map<String, Any>
