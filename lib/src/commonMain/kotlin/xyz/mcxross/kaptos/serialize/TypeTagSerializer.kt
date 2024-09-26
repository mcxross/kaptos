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

object TypeTagSerializer : KSerializer<TypeTag> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTag", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTag) {

    when (value) {
      is TypeTagStruct -> {
        encoder.encodeEnum(TypeTagVariants.serializer().descriptor, TypeTagVariants.Struct.ordinal)
        encoder.encodeSerializableValue(TypeTagStruct.serializer(), value)
      }
      is TypeTagAddress -> encodeTypeTag(encoder, TypeTagVariants.Address, value)
      is TypeTagBool -> encodeTypeTag(encoder, TypeTagVariants.Bool, value)
      is TypeTagGeneric -> encodeTypeTag(encoder, TypeTagVariants.Generic, value)
      is TypeTagReference -> encodeTypeTag(encoder, TypeTagVariants.Reference, value)
      is TypeTagSigner -> encodeTypeTag(encoder, TypeTagVariants.Signer, value)
      is TypeTagU8 -> encodeTypeTag(encoder, TypeTagVariants.U8, value)
      is TypeTagU16 -> encodeTypeTag(encoder, TypeTagVariants.U16, value)
      is TypeTagU32 -> encodeTypeTag(encoder, TypeTagVariants.U32, value)
      is TypeTagU64 -> encodeTypeTag(encoder, TypeTagVariants.U64, value)
      is TypeTagU128 -> encodeTypeTag(encoder, TypeTagVariants.U128, value)
      is TypeTagU256 -> encodeTypeTag(encoder, TypeTagVariants.U256, value)
      is TypeTagVector -> encodeTypeTag(encoder, TypeTagVariants.Vector, value)
    }
  }

  private fun encodeTypeTag(encoder: Encoder, variant: TypeTagVariants, value: TypeTag) {
    encoder.encodeEnum(TypeTagVariants.serializer().descriptor, variant.ordinal)
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): TypeTag {
    return TypeTag.valueOf(decoder.decodeString())
  }
}
