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

import xyz.mcxross.bcs.Bcs

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
    override fun toByteArray(): ByteArray {
      TODO("Not yet implemented")
    }
  }

  data class NumberValue(val value: Number) : PropertyValue() {
    override fun toByteArray(): ByteArray {
      TODO("Not yet implemented")
    }
  }

  data class BigIntValue(val value: String) : PropertyValue() {
    override fun toByteArray(): ByteArray {
      TODO("Not yet implemented")
    }
  }

  data class StringValue(val value: String) : PropertyValue() {
    override fun toByteArray(): ByteArray = Bcs.encodeToByteArray(MoveString(value))
  }

  data class AccountAddressValue(val value: AccountAddress) : PropertyValue() {
    override fun toByteArray(): ByteArray {
      TODO("Not yet implemented")
    }
  }

  data class Uint8ArrayValue(val value: ByteArray) : PropertyValue() {
    override fun toByteArray(): ByteArray {
      TODO("Not yet implemented")
    }

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
