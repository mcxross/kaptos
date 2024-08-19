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

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeCollection
import xyz.mcxross.kaptos.model.EntryFunctionArgument
import xyz.mcxross.kaptos.model.MoveVector

class MoveVectorSerializer<T : EntryFunctionArgument>(
  private val elementSerializer: KSerializer<T>
) : KSerializer<MoveVector<T>> {
  @OptIn(ExperimentalSerializationApi::class)
  override val descriptor: SerialDescriptor = listSerialDescriptor(elementSerializer.descriptor)

  override fun serialize(encoder: Encoder, value: MoveVector<T>) {
    encoder.encodeCollection(descriptor, value.serialize().size) {}
    encoder.encodeCollection(descriptor, value.values.size) {
      for (element in value.values) {
        elementSerializer.serialize(encoder, element)
      }
    }
  }

  override fun deserialize(decoder: Decoder): MoveVector<T> {
    return MoveVector(listOf())
  }
}
