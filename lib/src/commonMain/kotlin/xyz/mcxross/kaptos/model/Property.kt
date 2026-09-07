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
package xyz.mcxross.kaptos.model

sealed class PropertyType {
  data object U8 : PropertyType() {
    override fun toString(): String {
      return "u8"
    }
  }

  data object U16 : PropertyType() {
    override fun toString(): String {
      return "u16"
    }
  }

  data object U32 : PropertyType() {
    override fun toString(): String {
      return "u32"
    }
  }

  data object U64 : PropertyType() {
    override fun toString(): String {
      return "u64"
    }
  }

  data object U128 : PropertyType() {
    override fun toString(): String {
      return "u128"
    }
  }

  data object U256 : PropertyType() {
    override fun toString(): String {
      return "u256"
    }
  }

  data object BOOLEAN : PropertyType() {
    override fun toString(): String {
      return "bool"
    }
  }

  data object ADDRESS : PropertyType() {
    override fun toString(): String {
      return "address"
    }
  }

  data object STRING : PropertyType() {
    override fun toString(): String {
      return "0x1::string::String"
    }
  }

  data object ARRAY : PropertyType() {
    override fun toString(): String {
      return "vector<u8>"
    }
  }
}

sealed class PropertyValue {

  abstract fun toByteArray(): ByteArray

  data class BooleanValue(val value: Boolean) : PropertyValue() {
    override fun toByteArray(): ByteArray = byteArrayOf(if (value) 1 else 0)
  }

  data class NumberValue(val value: Number) : PropertyValue() {
    override fun toByteArray(): ByteArray = encodeUnsignedDecimal(value.toString(), 8)
  }

  data class BigIntValue(val value: String) : PropertyValue() {
    override fun toByteArray(): ByteArray = encodeUnsignedDecimal(value, 16)
  }

  data class StringValue(val value: String) : PropertyValue() {
    override fun toByteArray(): ByteArray {
      val utf8 = value.encodeToByteArray()
      return encodeUleb128(utf8.size) + utf8
    }
  }

  data class AccountAddressValue(val value: AccountAddress) : PropertyValue() {
    override fun toByteArray(): ByteArray = value.data.copyOf()
  }

  data class Uint8ArrayValue(val value: ByteArray) : PropertyValue() {
    override fun toByteArray(): ByteArray = encodeUleb128(value.size) + value

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as Uint8ArrayValue

      return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
      return value.contentHashCode()
    }
  }
}

internal fun PropertyValue.encodeAs(type: PropertyType): ByteArray =
  when (type) {
    PropertyType.BOOLEAN ->
      (this as? PropertyValue.BooleanValue)?.toByteArray() ?: propertyTypeMismatch(type)
    PropertyType.U8 -> encodeNumber(this, 1, type)
    PropertyType.U16 -> encodeNumber(this, 2, type)
    PropertyType.U32 -> encodeNumber(this, 4, type)
    PropertyType.U64 -> encodeNumber(this, 8, type)
    PropertyType.U128 -> encodeNumber(this, 16, type)
    PropertyType.U256 -> encodeNumber(this, 32, type)
    PropertyType.ADDRESS ->
      (this as? PropertyValue.AccountAddressValue)?.toByteArray() ?: propertyTypeMismatch(type)
    PropertyType.STRING ->
      (this as? PropertyValue.StringValue)?.toByteArray() ?: propertyTypeMismatch(type)
    PropertyType.ARRAY ->
      (this as? PropertyValue.Uint8ArrayValue)?.toByteArray() ?: propertyTypeMismatch(type)
  }

private fun encodeNumber(value: PropertyValue, width: Int, type: PropertyType): ByteArray {
  val decimal =
    when (value) {
      is PropertyValue.NumberValue -> value.value.toString()
      is PropertyValue.BigIntValue -> value.value
      else -> propertyTypeMismatch(type)
    }
  return encodeUnsignedDecimal(decimal, width)
}

private fun propertyTypeMismatch(expected: PropertyType): Nothing =
  throw IllegalArgumentException("Property value does not match type $expected")

private fun encodeUnsignedDecimal(value: String, width: Int): ByteArray {
  require(value.isNotEmpty() && value.all(Char::isDigit)) { "Invalid unsigned integer: $value" }
  val bytes = ByteArray(width)
  value.forEach { digitChar ->
    var carry = digitChar.digitToInt()
    for (index in bytes.indices) {
      val next = (bytes[index].toInt() and 0xff) * 10 + carry
      bytes[index] = (next and 0xff).toByte()
      carry = next ushr 8
    }
    require(carry == 0) { "Unsigned integer does not fit in ${width * 8} bits: $value" }
  }
  return bytes
}

private fun encodeUleb128(value: Int): ByteArray {
  require(value >= 0)
  var remaining = value
  val output = mutableListOf<Byte>()
  do {
    var byte = remaining and 0x7f
    remaining = remaining ushr 7
    if (remaining != 0) byte = byte or 0x80
    output += byte.toByte()
  } while (remaining != 0)
  return output.toByteArray()
}
