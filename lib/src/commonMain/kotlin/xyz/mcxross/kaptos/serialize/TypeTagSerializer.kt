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
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.TypeTagStruct

object TypeTagSerializer : KSerializer<TypeTag> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTag", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTag) {

    when (value) {
      is TypeTagStruct -> {
        encoder.beginStructure(descriptor).apply {
          encodeSerializableElement(descriptor, 0, TypeTagStruct.serializer(), value)
          endStructure(descriptor)
        }
      }
      else -> encoder.encodeString(value.toString())
    }
  }

  override fun deserialize(decoder: Decoder): TypeTag {
    return TypeTag.valueOf(decoder.decodeString())
  }
}
