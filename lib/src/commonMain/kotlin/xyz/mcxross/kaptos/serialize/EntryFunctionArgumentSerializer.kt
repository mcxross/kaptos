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
import kotlinx.serialization.serializer
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.isHex

object EntryFunctionArgumentSerializer : KSerializer<EntryFunctionArgument> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("EntryFunctionArgument", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: EntryFunctionArgument) {
    when (value) {
      is MoveString -> {
        if (isHex(value.toString())) {
          encoder.beginCollection(descriptor, hexStringToByteArray(value.toString()).size)
          hexStringToByteArray(value.toString()).map { encoder.encodeByte(it) }
        } else {
          val length = Bcs.encodeToByteArray(value).size
          encoder.beginCollection(descriptor, length)
          encoder.encodeString(value.value)
        }
      }
      is U64 -> {
        val length = Bcs.encodeToByteArray(value).size
        encoder.beginCollection(descriptor, length)
        encoder.encodeSerializableValue(U64.serializer(), value)
      }
      is MoveVector<*> -> {
        encoder.encodeSerializableValue(MoveVectorSerializer, value)
      }
      else -> throw IllegalArgumentException("Unimplemented transaction argument type")
    }
  }

  fun hexStringToByteArray(hexString: String): ByteArray {
    var cleanedHexString = hexString.removePrefix("0x").replace(Regex("[^0-9A-Fa-f]"), "")
    if (cleanedHexString.length % 2 != 0) {
      cleanedHexString = "0$cleanedHexString"
    }

    return ByteArray(cleanedHexString.length / 2) { i ->
      val index = i * 2
      cleanedHexString.substring(index, index + 2).toInt(16).toByte()
    }
  }

  override fun deserialize(decoder: Decoder): EntryFunctionArgument {
    return MoveString(decoder.decodeString())
  }
}
