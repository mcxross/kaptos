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
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.mcxross.kaptos.model.MoveValue

object MoveListTypeSerializer : KSerializer<MoveValue.MoveListType> {
  override val descriptor =
    PrimitiveSerialDescriptor("MoveValue.MoveListType", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: MoveValue.MoveListType) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): MoveValue.MoveListType {
    val a = decoder.decodeSerializableValue(VEC.serializer())
    return MoveValue.MoveListType(a.vec.map { MoveValue.String(it) })
  }
}

@Serializable data class VEC(val vec: List<String>)
