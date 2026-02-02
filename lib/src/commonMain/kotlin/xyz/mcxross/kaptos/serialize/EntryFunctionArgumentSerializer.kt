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
import xyz.mcxross.kaptos.model.*

object EntryFunctionArgumentSerializer : KSerializer<EntryFunctionArgument> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("EntryFunctionArgument", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: EntryFunctionArgument) {
    when (value) {
      is MoveString -> {
        encoder.encodeSerializableValue(MoveStringSerializer, value)
      }
      is Bool -> {
        encoder.encodeSerializableValue(BoolSerializer, value)
      }
      is U8 -> {
        encoder.encodeSerializableValue(U8Serializer, value)
      }
      is U64 -> {
        encoder.encodeSerializableValue(U64Serializer, value)
      }
      is AccountAddress -> {
        encoder.encodeSerializableValue(HexInputSerializer, HexInput(value.toStringLong()))
      }
      is HexInput -> {
        encoder.encodeSerializableValue(HexInputSerializer, value)
      }
      is MoveVector<*> -> {
        encoder.encodeSerializableValue(
          MoveVectorSerializer(EntryFunctionArgumentSerializer),
          value as MoveVector<EntryFunctionArgument>,
        )
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
}
