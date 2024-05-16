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

import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.exception.ParsingException

/**
 * This enum is used to explain why an address was invalid.
 *
 * @property reason The reason why the address was invalid.
 */
enum class AddressInvalidReason(val reason: String) {
  INCORRECT_NUMBER_OF_BYTES("Incorrect number of bytes"),
  INVALID_NUM_OF_HEX_CHARS("Invalid num of hex chars"),
  INVALID_HEX_CHARS("Invalid hex chars"),
  TOO_SHORT("Too short"),
  TOO_LONG("Too long"),
  LEADING_ZERO_X_REQUIRED("Leading zero x required"),
  LONG_FORM_REQUIRED_UNLESS_SPECIAL("Long form required unless special"),
  INVALID_PADDING_ZEROES("INVALID PADDING ZEROES"),
}

/**
 * This interface is used to define the input for an account address.
 *
 * @property value The value of the account address.
 */
interface AccountAddressInput {
  val value: String
}

/**
 * This class is used to represent an account address.
 *
 * It is used for working with account addresses. Account addresses, when represented as a string,
 * generally look like these examples:
 * - 0x1
 * - 0xaa86fe99004361f747f91342ca13c426ca0cccb0c1217677180c9493bad6ef0c
 *
 * @constructor Creates an account address from a hex string.
 * @property data The data of the account address.
 */
@Serializable
data class AccountAddress(val data: ByteArray) : AccountAddressInput {

  /**
   * This constructor is used to create an account address from a hex string.
   *
   * It is made to be as sensitive as possible to invalid hex characters. There are instances where
   * a character less is considered valid, but we are not allowing that here. It must fail at both
   * the char level and the byte level.
   *
   * @param hex The hex string to create the account address from.
   */
  constructor(
    hex: String
  ) : this(
    hex.removePrefix("0x").let {
      // We need this to be as sensitive as possible to invalid hex characters
      if (it.length % 2 != 0)
        throw ParsingException(AddressInvalidReason.INVALID_NUM_OF_HEX_CHARS.reason)
      it.chunked(2).map { pair -> pair.toInt(16).toByte() }.toByteArray()
    }
  )

  init {
    if (data.size < LENGTH) {
      throw ParsingException(
        AddressInvalidReason.INCORRECT_NUMBER_OF_BYTES.reason +
          " Expected $LENGTH bytes, got ${data.size}."
      )
    }
  }

  fun isSpecial(): Boolean {
    return this.data.sliceArray(0 until this.data.size - 1).all { byte -> byte.toInt() == 0 } &&
      this.data[this.data.size - 1].toInt() < 0b10000
  }

  override fun toString(): String = "0x${toStringWithoutPrefix()}"

  fun toStringWithoutPrefix(): String {
    val hex =
      data.joinToString("") {
        val str = it.toInt().and(0xff).toString(16)
        if (str.length == 1) {
          "0$str"
        } else {
          str
        }
      }
    return if (isSpecial()) {
      hex.takeLast(1)
    } else {
      hex
    }
  }

  fun toStringLong(): String = "0x${toStringLongWithoutPrefix()}"

  fun toStringLongWithoutPrefix(): String {
    return data.joinToString("") { byte -> "${byte}${byte}" }
  }

  override val value: String
    get() = toString()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as AccountAddress

    return data.contentEquals(other.data)
  }

  override fun hashCode(): Int {
    return data.contentHashCode()
  }

  companion object {
    const val LENGTH: Int = 32
    const val LONG_STRING_LENGTH: Int = 64

    val ZERO: AccountAddress = AccountAddress(ByteArray(LENGTH) { 0 })
    val ONE: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 1 else 0 })
    val TWO: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 2 else 0 })
    val THREE: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 3 else 0 })
    val FOUR: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 4 else 0 })

    /**
     * NOTE: This function has strict parsing behavior. For relaxed behavior, please use the
     * `[fromString]` function.
     *
     * Creates an instance of AccountAddress from a hex string.
     *
     * This function allows only the strictest formats defined by AIP-40. In short this means only
     * the following formats are accepted:
     * - LONG
     * - SHORT for special addresses
     *
     * Where:
     * - LONG is defined as 0x + 64 hex characters.
     * - SHORT for special addresses is 0x0 to 0xf inclusive without padding zeroes.
     *
     * This means the following are not accepted:
     * - SHORT for non-special addresses.
     * - Any address without a leading 0x.
     *
     * Learn more about the different address formats by reading AIP-40:
     * https://github.com/aptos-foundation/AIPs/blob/main/aips/aip-40.md.
     *
     * @param input A hex string representing an account address.
     * @returns An instance of [AccountAddress].
     */
    fun fromStringStrict(input: String): AccountAddress {
      if (!input.startsWith("0x")) {
        throw ParsingException(AddressInvalidReason.LEADING_ZERO_X_REQUIRED.reason)
      }

      val address = fromString(input)

      // Check if the address is in LONG form. If it is not, this is only allowed for
      // special addresses, in which case we check it is in proper SHORT form.
      if (input.length != LONG_STRING_LENGTH + 2) {
        if (!address.isSpecial()) {
          throw ParsingException(AddressInvalidReason.LONG_FORM_REQUIRED_UNLESS_SPECIAL.reason)
        } else if (input.length != 3) {
          throw ParsingException(AddressInvalidReason.INVALID_PADDING_ZEROES.reason)
        }
      }

      return address
    }

    /**
     * NOTE: This function has relaxed parsing behavior. For strict behavior, please use the
     * `[fromStringStrict]` function. Where possible use `fromStringStrict` rather than this
     * function, `[fromString]` is only provided for backwards compatibility.
     *
     * Creates an instance of [AccountAddress] from a hex string.
     *
     * This function allows all formats defined by AIP-40. In short this means the following formats
     * are accepted:
     * - LONG, with or without leading 0x
     * - SHORT, with or without leading 0x
     *
     * Where:
     * - LONG is 64 hex characters.
     * - SHORT is 1 to 63 hex characters inclusive.
     * - Padding zeroes are allowed, e.g. 0x0123 is valid.
     *
     * Learn more about the different address formats by reading AIP-40:
     * https://github.com/aptos-foundation/AIPs/blob/main/aips/aip-40.md.
     *
     * @param input A hex string representing an account address.
     * @returns An instance of [AccountAddress].
     */
    fun fromString(input: String): AccountAddress {
      val parsedInput = input.removePrefix("0x")

      if (parsedInput.isEmpty()) {
        throw ParsingException(AddressInvalidReason.TOO_SHORT.reason)
      }

      if (parsedInput.length > 64) {
        throw ParsingException(AddressInvalidReason.TOO_LONG.reason)
      }

      val paddedInput = parsedInput.padStart(64, '0')
      return try {
        AccountAddress(paddedInput.chunked(2).map { it.toInt(16).and(0xff).toByte() }.toByteArray())
      } catch (e: NumberFormatException) {
        throw ParsingException(AddressInvalidReason.INVALID_HEX_CHARS.reason)
      }
    }

    /**
     * Convenience method for creating an AccountAddress from all known inputs.
     *
     * This handles, HexInput, and AccountAddress itself
     *
     * @param input
     */
    fun from(input: AccountAddressInput): AccountAddress {
      if (input is AccountAddress) {
        return input
      }
      return fromString(input.value)
    }

    /**
     * Check if the string is a valid [AccountAddress].
     *
     * @param input A hex string representing an account address.
     * @param strict If true, use strict parsing behavior. If false, use relaxed parsing behavior.
     *   Default is false.
     * @returns valid = true if the string is valid, valid = false if not. If the string is not
     *   valid, invalidReason will be set explaining why it is invalid.
     */
    fun isValid(input: String, strict: Boolean = false): Boolean {
      return try {
        if (strict) {
          fromStringStrict(input)
        } else {
          fromString(input)
        }
        true
      } catch (e: ParsingException) {
        false
      }
    }
  }
}

data class HexInput(override val value: String) : AccountAddressInput {
  override fun toString(): String {
    return value
  }
}
