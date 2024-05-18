package xyz.mcxross.kaptos.core

import xyz.mcxross.kaptos.exception.ParsingException
import xyz.mcxross.kaptos.model.HexInput

/** This enum is used to explain why parsing might have failed. */
enum class HexInvalidReason(val reason: String) {
  TOO_SHORT("Too Short"),
  INVALID_LENGTH("Invalid Length"),
  INVALID_HEX_CHARS("Invalid Hex Chars"),
}

/**
 * This class is used to represent a hex string.
 *
 * It is used for working with hex strings. Hex strings, when represented as a string, generally
 * look like these examples:
 * - 0x1
 * - 0xaa86fe99004361f747f91342ca13c426ca0cccb0c1217677180c9493bad6ef0c
 *
 * @constructor Creates a hex string from a string.
 * @property data The data of the hex string.
 */
class Hex() {
  private var data: ByteArray = byteArrayOf()

  /**
   * This constructor is used to create a new Hex from a byte array.
   *
   * @param data The byte array to create the hex from.
   */
  constructor(data: ByteArray) : this() {
    this.data = data
  }

  fun toByteArray(): ByteArray {
    return data
  }

  fun toStringWithoutPrefix(): String {
    return data.joinToString("") { it.toUByte().toString(16).padStart(2, '0') }
  }

  override fun toString(): String {
    return "0x${toStringWithoutPrefix()}"
  }

  companion object {

    fun fromString(hex: String): Hex {
      val hexString = hex.removePrefix("0x")
      if (hexString.length % 2 != 0) {
        throw ParsingException(HexInvalidReason.INVALID_LENGTH.reason)
      }
      return Hex(hexString.chunked(2).map { it.toInt(16).toByte() }.toByteArray())
    }

    // TODO: Implement fromHexInput
    fun fromHexInput(hexInput: HexInput): Hex {
      return fromString(hexInput.value)
    }

    fun fromHexInput(byteArray: ByteArray): Hex {
      return Hex(byteArray)
    }
  }
}
