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
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionPayloadEntryFunction
import xyz.mcxross.kaptos.model.TransactionPayloadVariants

object TransactionPayloadSerializer : KSerializer<TransactionPayload> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TransactionPayload", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TransactionPayload) {
    when (value) {
      is TransactionPayloadEntryFunction -> {
        encoder.encodeEnum(descriptor, TransactionPayloadVariants.EntryFunction.value)
        encoder.beginStructure(descriptor).apply {
          encodeSerializableElement(
            descriptor,
            0,
            TransactionPayloadEntryFunction.serializer(),
            value,
          )
          endStructure(descriptor)
        }
      }
      else -> throw IllegalArgumentException("Unimplemented transaction payload type")
    }
  }

  override fun deserialize(decoder: Decoder): TransactionPayload {
    val input = decoder as JsonDecoder
    val tree = input.decodeJsonElement()
    val payloadType = tree.jsonObject["type"]?.jsonPrimitive?.int
    return when (payloadType) {
      TransactionPayloadVariants.EntryFunction.value -> {
        TransactionPayloadEntryFunction.serializer().deserialize(input)
      }
      else -> throw IllegalArgumentException("Unimplemented transaction payload type")
    }
  }
}
