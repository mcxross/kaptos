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
package xyz.mcxross.kaptos.serialize

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.Bool
import xyz.mcxross.kaptos.model.EntryFunctionArgument
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.MoveString
import xyz.mcxross.kaptos.model.MoveVector
import xyz.mcxross.kaptos.model.U16
import xyz.mcxross.kaptos.model.U32
import xyz.mcxross.kaptos.model.U64
import xyz.mcxross.kaptos.model.U8

object EntryFunctionArgumentSerializer : KSerializer<EntryFunctionArgument> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("EntryFunctionArgument", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: EntryFunctionArgument) {
    when (value) {
      is MoveString -> {
        encoder.encodeSerializableValue(MoveStringSerializer, value)
      }
      is Bool -> {
        encoder.beginCollection(descriptor, 1)
        encoder.encodeByte(if (value.value) 1 else 0)
      }
      is U8 -> {
        encoder.beginCollection(descriptor, 1)
        encoder.encodeByte(value.value)
      }
      is U16 -> {
        encoder.beginCollection(descriptor, 2)
        encoder.encodeShort(value.value.toShort())
      }
      is U32 -> {
        encoder.beginCollection(descriptor, 4)
        encoder.encodeInt(value.value.toInt())
      }
      is U64 -> {
        encoder.beginCollection(descriptor, 8)
        encoder.encodeLong(value.value.toLong())
      }
      is AccountAddress -> {
        encoder.encodeSerializableValue(HexInputSerializer, HexInput(value.toStringLong()))
      }
      is HexInput -> {
        encoder.encodeSerializableValue(HexInputSerializer, value)
      }
      is MoveVector<*> -> {
        val serializedVector = encodeMoveVectorToBcsBytes(value)
        encoder.beginCollection(descriptor, serializedVector.size)
        serializedVector.forEach { byte -> encoder.encodeByte(byte) }
      }
      else ->
        throw IllegalArgumentException(
          "Unimplemented transaction argument type ${value::class.simpleName}"
        )
    }
  }

  override fun deserialize(decoder: Decoder): EntryFunctionArgument {
    return MoveString(decoder.decodeString())
  }

  private fun encodeMoveVectorToBcsBytes(vector: MoveVector<*>): ByteArray {
    val values = vector.values
    val sizePrefix = encodeUleb128(values.size)
    if (values.isEmpty()) return sizePrefix

    val payload = ArrayList<Byte>()
    values.forEach { element -> payload.addAll(encodeEntryArgumentToBcsBytes(element).toList()) }

    return sizePrefix + payload.toByteArray()
  }

  private fun encodeEntryArgumentToBcsBytes(value: Any): ByteArray {
    return when (value) {
      is Bool -> byteArrayOf(if (value.value) 1 else 0)
      is U8 -> byteArrayOf(value.value)
      is U16 -> littleEndianU16(value.value.toInt())
      is U32 -> littleEndianU32(value.value.toLong())
      is U64 -> littleEndianU64(value.value.toLong())
      is MoveString -> {
        val utf8 = value.value.encodeToByteArray()
        encodeUleb128(utf8.size) + utf8
      }
      is MoveVector<*> -> encodeMoveVectorToBcsBytes(value)
      is AccountAddress -> parseHexBytes(value.toStringLong())
      is HexInput -> parseHexBytes(value.value)
      else ->
        throw IllegalArgumentException(
          "Unsupported MoveVector element type ${value::class.simpleName}"
        )
    }
  }

  private fun parseHexBytes(input: String): ByteArray {
    val normalized = input.removePrefix("0x").removePrefix("0X")
    require(normalized.length % 2 == 0) { "Hex input must have an even number of characters." }
    require(normalized.all { it.isDigit() || it in 'a'..'f' || it in 'A'..'F' }) {
      "Hex input must contain only hex characters."
    }
    return ByteArray(normalized.length / 2) { index ->
      normalized.substring(index * 2, index * 2 + 2).toInt(16).toByte()
    }
  }

  private fun littleEndianU16(value: Int): ByteArray {
    require(value in 0..0xFFFF) { "U16 out of range: $value" }
    return byteArrayOf(
      (value and 0xFF).toByte(),
      ((value ushr 8) and 0xFF).toByte(),
    )
  }

  private fun littleEndianU32(value: Long): ByteArray {
    require(value in 0..0xFFFF_FFFFL) { "U32 out of range: $value" }
    return byteArrayOf(
      (value and 0xFF).toByte(),
      ((value ushr 8) and 0xFF).toByte(),
      ((value ushr 16) and 0xFF).toByte(),
      ((value ushr 24) and 0xFF).toByte(),
    )
  }

  private fun littleEndianU64(value: Long): ByteArray {
    require(value >= 0) { "U64 out of range: $value" }
    return byteArrayOf(
      (value and 0xFF).toByte(),
      ((value ushr 8) and 0xFF).toByte(),
      ((value ushr 16) and 0xFF).toByte(),
      ((value ushr 24) and 0xFF).toByte(),
      ((value ushr 32) and 0xFF).toByte(),
      ((value ushr 40) and 0xFF).toByte(),
      ((value ushr 48) and 0xFF).toByte(),
      ((value ushr 56) and 0xFF).toByte(),
    )
  }

  private fun encodeUleb128(value: Int): ByteArray {
    require(value >= 0) { "ULEB128 value must be >= 0" }
    var mutable = value
    val out = ArrayList<Byte>()
    while (mutable >= 0x80) {
      out.add(((mutable and 0x7F) or 0x80).toByte())
      mutable = mutable ushr 7
    }
    out.add(mutable.toByte())
    return out.toByteArray()
  }
}
