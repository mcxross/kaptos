/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction.bcs

import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey
import xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.StructTag
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.TypeTagAddress
import xyz.mcxross.kaptos.model.TypeTagBool
import xyz.mcxross.kaptos.model.TypeTagGeneric
import xyz.mcxross.kaptos.model.TypeTagI128
import xyz.mcxross.kaptos.model.TypeTagI16
import xyz.mcxross.kaptos.model.TypeTagI256
import xyz.mcxross.kaptos.model.TypeTagI32
import xyz.mcxross.kaptos.model.TypeTagI64
import xyz.mcxross.kaptos.model.TypeTagI8
import xyz.mcxross.kaptos.model.TypeTagReference
import xyz.mcxross.kaptos.model.TypeTagSigner
import xyz.mcxross.kaptos.model.TypeTagStruct
import xyz.mcxross.kaptos.model.TypeTagU128
import xyz.mcxross.kaptos.model.TypeTagU16
import xyz.mcxross.kaptos.model.TypeTagU256
import xyz.mcxross.kaptos.model.TypeTagU32
import xyz.mcxross.kaptos.model.TypeTagU64
import xyz.mcxross.kaptos.model.TypeTagU8
import xyz.mcxross.kaptos.model.TypeTagVector

internal class AptosBcsWriter {
  private val output = ArrayList<Byte>()

  fun uleb128(value: UInt) {
    var remaining = value
    do {
      var byte = (remaining and 0x7fu).toInt()
      remaining = remaining shr 7
      if (remaining != 0u) byte = byte or 0x80
      output += byte.toByte()
    } while (remaining != 0u)
  }

  fun bool(value: Boolean) = u8(if (value) 1u else 0u)

  fun u8(value: UByte) {
    output += value.toByte()
  }

  fun u16(value: UShort) = fixedUnsigned(value.toULong(), 2)

  fun u32(value: UInt) = fixedUnsigned(value.toULong(), 4)

  fun u64(value: ULong) = fixedUnsigned(value, 8)

  fun fixed(bytes: ByteArray) {
    output.addAll(bytes.asList())
  }

  fun bytes(bytes: ByteArray) {
    require(bytes.size.toLong() <= UInt.MAX_VALUE.toLong()) { "BCS byte sequence is too large" }
    uleb128(bytes.size.toUInt())
    fixed(bytes)
  }

  fun string(value: String) = bytes(value.encodeToByteArray())

  fun accountAddress(value: AccountAddress) {
    require(value.data.size == AccountAddress.LENGTH) {
      "Account address must be ${AccountAddress.LENGTH} bytes"
    }
    fixed(value.data)
  }

  fun typeTag(value: TypeTag) {
    when (value) {
      TypeTagBool -> uleb128(0u)
      TypeTagU8 -> uleb128(1u)
      TypeTagU64 -> uleb128(2u)
      TypeTagU128 -> uleb128(3u)
      TypeTagAddress -> uleb128(4u)
      TypeTagSigner -> uleb128(5u)
      is TypeTagVector -> {
        uleb128(6u)
        typeTag(value.type)
      }
      is TypeTagStruct -> {
        uleb128(7u)
        structTag(value.type)
      }
      TypeTagU16 -> uleb128(8u)
      TypeTagU32 -> uleb128(9u)
      TypeTagU256 -> uleb128(10u)
      TypeTagI8 -> uleb128(11u)
      TypeTagI16 -> uleb128(12u)
      TypeTagI32 -> uleb128(13u)
      TypeTagI64 -> uleb128(14u)
      TypeTagI128 -> uleb128(15u)
      TypeTagI256 -> uleb128(16u)
      is TypeTagGeneric ->
        throw IllegalArgumentException("Generic type parameters cannot be serialized on the wire")
      is TypeTagReference ->
        throw IllegalArgumentException("Reference type tags cannot be serialized on the wire")
    }
  }

  fun structTag(value: StructTag) {
    accountAddress(value.address)
    string(value.moduleName)
    string(value.name)
    vector(value.typeArgs) { typeTag(it) }
  }

  fun <T> vector(values: List<T>, encode: AptosBcsWriter.(T) -> Unit) {
    require(values.size.toLong() <= UInt.MAX_VALUE.toLong()) { "BCS vector is too large" }
    uleb128(values.size.toUInt())
    values.forEach { encode(it) }
  }

  fun <T> option(value: T?, encode: AptosBcsWriter.(T) -> Unit) {
    if (value == null) {
      uleb128(0u)
    } else {
      uleb128(1u)
      encode(value)
    }
  }

  fun toByteArray(): ByteArray = output.toByteArray()

  private fun fixedUnsigned(value: ULong, width: Int) {
    var remaining = value
    repeat(width) {
      output += (remaining and 0xffu).toByte()
      remaining = remaining shr 8
    }
    require(remaining == 0uL) { "Unsigned integer does not fit in ${width * 8} bits" }
  }
}

internal class AptosBcsReader(private val input: ByteArray) {
  private var offset = 0

  val remaining: Int
    get() = input.size - offset

  fun uleb128(): UInt {
    var result = 0u
    var shift = 0
    repeat(5) { index ->
      val byte = u8().toInt()
      val digit = byte and 0x7f
      if (shift == 28 && digit > 0x0f) throw IllegalArgumentException("ULEB128 value exceeds u32")
      result = result or (digit.toUInt() shl shift)
      if (byte and 0x80 == 0) {
        if (index > 0 && digit == 0) throw IllegalArgumentException("Non-canonical ULEB128 value")
        return result
      }
      shift += 7
    }
    throw IllegalArgumentException("ULEB128 value exceeds u32")
  }

  fun bool(): Boolean =
    when (val value = u8().toInt()) {
      0 -> false
      1 -> true
      else -> throw IllegalArgumentException("Invalid BCS boolean: $value")
    }

  fun u8(): UByte {
    requireRemaining(1)
    return input[offset++].toUByte()
  }

  fun u16(): UShort = fixedUnsigned(2).toUShort()

  fun u32(): UInt = fixedUnsigned(4).toUInt()

  fun u64(): ULong = fixedUnsigned(8)

  fun fixed(length: Int): ByteArray {
    require(length >= 0) { "Length cannot be negative" }
    requireRemaining(length)
    return input.copyOfRange(offset, offset + length).also { offset += length }
  }

  fun bytes(): ByteArray {
    val length = uleb128().toLong()
    require(length <= Int.MAX_VALUE) { "BCS sequence length exceeds platform limits" }
    return fixed(length.toInt())
  }

  fun string(): String = bytes().decodeToString(throwOnInvalidSequence = true)

  fun accountAddress(): AccountAddress = AccountAddress(fixed(AccountAddress.LENGTH))

  fun anyPublicKey(): AnyPublicKey =
    when (val variant = uleb128()) {
      0u -> AnyPublicKey(Ed25519PublicKey(bytes()))
      1u -> AnyPublicKey(Secp256k1PublicKey(xyz.mcxross.kaptos.model.HexInput.fromByteArray(bytes())))
      2u -> AnyPublicKey(Secp256r1PublicKey(bytes()))
      else -> throw IllegalArgumentException("Unsupported AnyPublicKey variant: $variant")
    }

  fun multiKey(): MultiKey = MultiKey(vector { anyPublicKey() }, u8().toInt())

  fun typeTag(): TypeTag =
    when (val variant = uleb128()) {
      0u -> TypeTagBool
      1u -> TypeTagU8
      2u -> TypeTagU64
      3u -> TypeTagU128
      4u -> TypeTagAddress
      5u -> TypeTagSigner
      6u -> TypeTagVector(typeTag())
      7u -> TypeTagStruct(structTag())
      8u -> TypeTagU16
      9u -> TypeTagU32
      10u -> TypeTagU256
      11u -> TypeTagI8
      12u -> TypeTagI16
      13u -> TypeTagI32
      14u -> TypeTagI64
      15u -> TypeTagI128
      16u -> TypeTagI256
      else -> throw IllegalArgumentException("Unsupported TypeTag variant: $variant")
    }

  fun structTag(): StructTag =
    StructTag(
      address = accountAddress(),
      moduleName = string(),
      name = string(),
      typeArgs = vector { typeTag() },
    )

  fun <T> vector(decode: AptosBcsReader.() -> T): List<T> {
    val length = uleb128().toLong()
    require(length <= Int.MAX_VALUE) { "BCS vector length exceeds platform limits" }
    return List(length.toInt()) { decode() }
  }

  fun <T> option(decode: AptosBcsReader.() -> T): T? =
    when (val length = uleb128()) {
      0u -> null
      1u -> decode()
      else -> throw IllegalArgumentException("Invalid BCS option length: $length")
    }

  fun ensureFinished() {
    require(remaining == 0) { "${remaining} trailing BCS bytes remain" }
  }

  private fun fixedUnsigned(width: Int): ULong {
    requireRemaining(width)
    var result = 0uL
    repeat(width) { index ->
      result = result or (input[offset++].toUByte().toULong() shl (index * 8))
    }
    return result
  }

  private fun requireRemaining(length: Int) {
    require(length <= remaining) {
      "Unexpected end of BCS input: need $length bytes, have $remaining"
    }
  }
}

internal fun unsignedDecimalToLittleEndian(value: String, width: Int): ByteArray {
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

internal fun signedDecimalToLittleEndian(value: String, width: Int): ByteArray {
  require(value.isNotEmpty()) { "Signed integer cannot be empty" }
  val negative = value.startsWith('-')
  val magnitudeText = if (negative) value.drop(1) else value
  require(magnitudeText.isNotEmpty() && magnitudeText.all(Char::isDigit)) {
    "Invalid signed integer: $value"
  }
  val magnitude = unsignedDecimalToLittleEndian(magnitudeText, width)
  val mostSignificant = magnitude.last().toInt() and 0xff
  if (!negative) {
    require(mostSignificant and 0x80 == 0) {
      "Signed integer does not fit in ${width * 8} bits: $value"
    }
    return magnitude
  }
  val lowerBytesAreZero = magnitude.dropLast(1).all { it == 0.toByte() }
  require(mostSignificant < 0x80 || (mostSignificant == 0x80 && lowerBytesAreZero)) {
    "Signed integer does not fit in ${width * 8} bits: $value"
  }
  require(magnitude.any { it != 0.toByte() }) { "Negative zero is not canonical" }

  var carry = 1
  for (index in magnitude.indices) {
    val next = (magnitude[index].toInt() xor 0xff) + carry
    magnitude[index] = next.toByte()
    carry = next ushr 8
  }
  return magnitude
}

internal fun littleEndianUnsignedToDecimal(value: ByteArray): String {
  val digits = mutableListOf(0)
  for (byte in value.reversedArray()) {
    var carry = byte.toInt() and 0xff
    for (index in digits.indices) {
      val next = digits[index] * 256 + carry
      digits[index] = next % 10
      carry = next / 10
    }
    while (carry > 0) {
      digits += carry % 10
      carry /= 10
    }
  }
  return digits.asReversed().joinToString("")
}

internal fun littleEndianSignedToDecimal(value: ByteArray): String {
  require(value.isNotEmpty()) { "Signed integer bytes cannot be empty" }
  if ((value.last().toInt() and 0x80) == 0) return littleEndianUnsignedToDecimal(value)

  val magnitude = value.copyOf()
  var carry = 1
  for (index in magnitude.indices) {
    val next = (magnitude[index].toInt() xor 0xff) + carry
    magnitude[index] = next.toByte()
    carry = next ushr 8
  }
  return "-${littleEndianUnsignedToDecimal(magnitude)}"
}
