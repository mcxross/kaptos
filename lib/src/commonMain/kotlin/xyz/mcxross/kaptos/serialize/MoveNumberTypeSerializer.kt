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
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.mcxross.kaptos.model.MoveValue

object MoveUint8TypeSerializer : KSerializer<MoveValue.MoveUint8Type> {
  override val descriptor = PrimitiveSerialDescriptor("MoveValue.MoveUint8Type", PrimitiveKind.INT)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveUint8Type) {
    encoder.encodeByte(value.value.toByte())
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveUint8Type {
    return MoveValue.MoveUint8Type(decoder.decodeByte().toUByte())
  }
}

object MoveUint16TypeSerializer : KSerializer<MoveValue.MoveUint16Type> {
  override val descriptor = PrimitiveSerialDescriptor("MoveValue.MoveUint16Type", PrimitiveKind.INT)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveUint16Type) {
    encoder.encodeShort(value.value.toShort())
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveUint16Type {
    return MoveValue.MoveUint16Type(decoder.decodeShort().toUShort())
  }
}

object MoveUint32TypeSerializer : KSerializer<MoveValue.MoveUint32Type> {
  override val descriptor = PrimitiveSerialDescriptor("MoveValue.MoveUint32Type", PrimitiveKind.INT)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveUint32Type) {
    encoder.encodeInt(value.value.toInt())
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveUint32Type {
    return MoveValue.MoveUint32Type(decoder.decodeInt().toUInt())
  }
}


object MoveUint64TypeSerializer : KSerializer<MoveValue.MoveUint64Type> {
  override val descriptor = PrimitiveSerialDescriptor("MoveValue.MoveUint64Type", PrimitiveKind.LONG)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveUint64Type) {
    encoder.encodeLong(value.value)
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveUint64Type {
    return MoveValue.MoveUint64Type(decoder.decodeLong())
  }
}


object MoveUint128TypeSerializer : KSerializer<MoveValue.MoveUint128Type> {
  override val descriptor = PrimitiveSerialDescriptor("MoveValue.MoveUint128Type", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveUint128Type) {
    encoder.encodeString(value.value)
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveUint128Type {
    return MoveValue.MoveUint128Type(decoder.decodeString())
  }
}


object MoveUint256TypeSerializer : KSerializer<MoveValue.MoveUint256Type> {
  override val descriptor = PrimitiveSerialDescriptor("MoveValue.MoveUint256Type", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveUint256Type) {
    encoder.encodeString(value.value)
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveUint256Type {
    return MoveValue.MoveUint256Type(decoder.decodeString())
  }
}