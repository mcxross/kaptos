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
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.mcxross.kaptos.model.TransactionPayloadVariants

object TransactionPayloadVariantsSerializer : KSerializer<TransactionPayloadVariants> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TransactionPayloadVariants", PrimitiveKind.INT)

  override fun serialize(encoder: Encoder, value: TransactionPayloadVariants) {
    encoder.encodeEnum(descriptor, value.value)
  }

  override fun deserialize(decoder: Decoder): TransactionPayloadVariants {
    val value = decoder.decodeEnum(descriptor)
    return TransactionPayloadVariants.entries.firstOrNull { it.value == value }
      ?: throw SerializationException("Unknown value: $value")
  }
}
