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
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.model.U64

object U64Serializer : KSerializer<U64> {
  override val descriptor = PrimitiveSerialDescriptor("U64", PrimitiveKind.LONG)

  override fun serialize(encoder: Encoder, value: U64) {
    val length = Bcs.encodeToByteArray(value.value).size
    encoder.beginCollection(descriptor, length)
    encoder.encodeLong(value.value.toLong())
  }

  override fun deserialize(decoder: Decoder): U64 {
    return U64(decoder.decodeLong().toULong())
  }
}
