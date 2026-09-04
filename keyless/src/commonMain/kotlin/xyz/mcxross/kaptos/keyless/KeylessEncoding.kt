/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.fastkrypto.bn254PoseidonHash
import xyz.mcxross.kaptos.model.AccountAddress

internal class KeylessBcsWriter {
  private val bytes = ArrayList<Byte>()

  fun uleb128(value: UInt) {
    var remaining = value
    do {
      var byte = (remaining and 0x7fu).toInt()
      remaining = remaining shr 7
      if (remaining != 0u) byte = byte or 0x80
      bytes += byte.toByte()
    } while (remaining != 0u)
  }

  fun u64(value: ULong) {
    var remaining = value
    repeat(8) {
      bytes += (remaining and 0xffu).toByte()
      remaining = remaining shr 8
    }
  }

  fun fixed(value: ByteArray) {
    bytes.addAll(value.asList())
  }

  fun bytes(value: ByteArray) {
    require(value.size.toLong() <= UInt.MAX_VALUE.toLong()) { "BCS value is too large" }
    uleb128(value.size.toUInt())
    fixed(value)
  }

  fun string(value: String) = bytes(value.encodeToByteArray())

  fun address(value: AccountAddress) = fixed(value.data)

  fun <T> option(value: T?, encode: KeylessBcsWriter.(T) -> Unit) {
    if (value == null) uleb128(0u)
    else {
      uleb128(1u)
      encode(value)
    }
  }

  fun toByteArray(): ByteArray = bytes.toByteArray()
}

internal class KeylessBcsReader(private val input: ByteArray) {
  private var offset = 0

  fun uleb128(): UInt {
    var result = 0u
    var shift = 0
    repeat(5) { index ->
      val byte = u8().toInt()
      val digit = byte and 0x7f
      if (shift == 28 && digit > 0x0f) throw IllegalArgumentException("ULEB128 exceeds u32")
      result = result or (digit.toUInt() shl shift)
      if (byte and 0x80 == 0) {
        if (index > 0 && digit == 0) throw IllegalArgumentException("Non-canonical ULEB128")
        return result
      }
      shift += 7
    }
    throw IllegalArgumentException("ULEB128 exceeds u32")
  }

  fun u8(): UByte {
    require(offset < input.size) { "Unexpected end of BCS input" }
    return input[offset++].toUByte()
  }

  fun u64(): ULong {
    var value = 0uL
    repeat(8) { index -> value = value or (u8().toULong() shl (index * 8)) }
    return value
  }

  fun fixed(length: Int): ByteArray {
    require(length >= 0 && offset + length <= input.size) { "Unexpected end of BCS input" }
    return input.copyOfRange(offset, offset + length).also { offset += length }
  }

  fun bytes(): ByteArray {
    val length = uleb128().toLong()
    require(length <= Int.MAX_VALUE) { "BCS value exceeds platform limits" }
    return fixed(length.toInt())
  }

  fun string(): String = bytes().decodeToString(throwOnInvalidSequence = true)

  fun address(): AccountAddress = AccountAddress(fixed(AccountAddress.LENGTH))

  fun <T> option(decode: KeylessBcsReader.() -> T): T? =
    when (val length = uleb128()) {
      0u -> null
      1u -> decode()
      else -> throw IllegalArgumentException("Invalid BCS option length: $length")
    }

  fun zeroKnowledgeSignature(): ZeroKnowledgeSignature {
    require(uleb128() == 0u) { "Only Groth16 Keyless proofs are supported" }
    val proof = Groth16Proof.fromBcs(fixed(128))
    val horizon = u64()
    val extra = option { string() }
    val audience = option { string() }
    val training =
      option {
        require(uleb128() == 0u) { "Only Ed25519 training-wheels signatures are supported" }
        xyz.mcxross.kaptos.core.crypto.Ed25519Signature(bytes())
      }
    return ZeroKnowledgeSignature(proof, horizon, extra, audience, training)
  }

  fun ensureFinished() {
    require(offset == input.size) { "${input.size - offset} trailing BCS bytes remain" }
  }
}

internal val keylessJson = Json {
  ignoreUnknownKeys = true
  explicitNulls = false
}

internal fun decodeBase64Url(value: String): ByteArray {
  val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
  val clean = value.trimEnd('=')
  require(clean.length % 4 != 1) { "Invalid base64url input" }
  val output = ArrayList<Byte>((clean.length * 3) / 4)
  var accumulator = 0
  var bits = 0
  clean.forEach { character ->
    val digit = alphabet.indexOf(character)
    require(digit >= 0) { "Invalid base64url input" }
    accumulator = (accumulator shl 6) or digit
    bits += 6
    if (bits >= 8) {
      bits -= 8
      output += ((accumulator shr bits) and 0xff).toByte()
    }
  }
  return output.toByteArray()
}

internal data class JwtClaims(
  val issuer: String,
  val audience: String,
  val uid: String,
  val issuedAt: Long?,
  val headerJson: String,
  val keyId: String,
)

internal fun parseJwt(jwt: String, uidKey: String): JwtClaims {
  val parts = jwt.split('.')
  require(parts.size == 3) { "JWT must contain three base64url segments" }
  val headerJson = decodeBase64Url(parts[0]).decodeToString(throwOnInvalidSequence = true)
  val payloadJson = decodeBase64Url(parts[1]).decodeToString(throwOnInvalidSequence = true)
  val header = keylessJson.parseToJsonElement(headerJson).jsonObject
  val payload = keylessJson.parseToJsonElement(payloadJson).jsonObject
  fun JsonObject.requiredString(name: String): String =
    this[name]?.jsonPrimitive?.content?.takeIf(String::isNotBlank)
      ?: throw IllegalArgumentException("JWT is missing required claim '$name'")
  return JwtClaims(
    issuer = payload.requiredString("iss"),
    audience = payload.requiredString("aud"),
    uid = payload.requiredString(uidKey),
    issuedAt = payload["iat"]?.jsonPrimitive?.content?.toLongOrNull(),
    headerJson = headerJson,
    keyId = header.requiredString("kid"),
  )
}

internal fun field(value: ULong): ByteArray = ByteArray(32).also { output ->
  var remaining = value
  repeat(8) { index ->
    output[index] = (remaining and 0xffu).toByte()
    remaining = remaining shr 8
  }
}

internal fun padAndPackBytesWithLength(value: ByteArray, maxBytes: Int): List<ByteArray> {
  require(value.size <= maxBytes) { "Input is ${value.size} bytes; maximum is $maxBytes" }
  require(maxBytes <= 465) { "At most 465 bytes can be packed into one Poseidon hash" }
  val padded = ByteArray(maxBytes)
  value.copyInto(padded)
  return padded.asList().chunked(31).map { chunk ->
    ByteArray(32).also { output -> chunk.forEachIndexed { index, byte -> output[index] = byte } }
  } + field(value.size.toULong())
}

internal fun poseidon(inputs: List<ByteArray>): ByteArray {
  require(inputs.size in 1..16) { "Poseidon requires between 1 and 16 fields" }
  inputs.forEach { require(it.size == 32) { "Poseidon fields must be 32 bytes" } }
  return bn254PoseidonHash(inputs)
}

internal fun hashStringToField(value: String, maxBytes: Int): ByteArray =
  poseidon(padAndPackBytesWithLength(value.encodeToByteArray(), maxBytes))

internal fun ByteArray.toUnsignedDecimal(): String {
  if (isEmpty() || all { it == 0.toByte() }) return "0"
  var magnitude = reversedArray().dropWhile { it == 0.toByte() }.map(Byte::toUByte).toMutableList()
  val digits = StringBuilder()
  while (magnitude.isNotEmpty()) {
    var carry = 0
    val quotient = ArrayList<UByte>(magnitude.size)
    magnitude.forEach { byte ->
      val current = carry * 256 + byte.toInt()
      val digit = current / 10
      carry = current % 10
      if (quotient.isNotEmpty() || digit != 0) quotient += digit.toUByte()
    }
    digits.append(('0'.code + carry).toChar())
    magnitude = quotient
  }
  return digits.reverse().toString()
}

internal fun ByteArray.hex(): String = joinToString("") { it.toUByte().toString(16).padStart(2, '0') }

internal fun decodeHex(value: String): ByteArray {
  val clean = value.removePrefix("0x")
  require(clean.length % 2 == 0) { "Hex input must contain whole bytes" }
  return ByteArray(clean.length / 2) { index ->
    clean.substring(index * 2, index * 2 + 2).toInt(16).toByte()
  }
}
