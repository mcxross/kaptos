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
import kotlinx.serialization.encoding.encodeCollection
import xyz.mcxross.kaptos.model.HexInput

object HexInputSerializer : KSerializer<HexInput> {
  override val descriptor = PrimitiveSerialDescriptor("HexInput", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: HexInput) {
    val bytes = hexStringToByteArray(value.toString())
    encoder.encodeCollection(descriptor, bytes.size) {
      bytes.forEach { byte -> encoder.encodeByte(byte) }
    }
  }

  private fun hexStringToByteArray(hexString: String): ByteArray {
    val cleanedHexString = hexString.removePrefix("0x").replace(Regex("[^0-9A-Fa-f]"), "")
    return ByteArray((cleanedHexString.length + 1) / 2) { i ->
      val index = i * 2
      cleanedHexString
        .substring(index, (index + 2).coerceAtMost(cleanedHexString.length))
        .toInt(16)
        .toByte()
    }
  }

  override fun deserialize(decoder: Decoder): HexInput {
    return HexInput(decoder.decodeString())
  }
}
