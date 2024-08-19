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

object TypeTagAddressSerializer : KSerializer<TypeTagAddress> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagAddress", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagAddress) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Address.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagAddress {
    return TypeTagAddress()
  }
}

object TypeTagBoolSerializer : KSerializer<TypeTagBool> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagBool", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagBool) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Bool.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagBool {
    return TypeTagBool()
  }
}

object TypeTagGenericSerializer : KSerializer<TypeTagGeneric> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagGeneric", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagGeneric) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Generic.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagGeneric {
    TODO("Not yet implemented")
  }
}

object TypeTagReferenceSerializer : KSerializer<TypeTagReference> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagReference", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagReference) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Reference.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagReference {
    TODO("Not yet implemented")
  }
}

object TypeTagSignerSerializer : KSerializer<TypeTagSigner> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagSigner", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagSigner) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Signer.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagSigner {
    TODO("Not yet implemented")
  }
}

object TypeTagU8serializer : KSerializer<TypeTagU8> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagU8", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagU8) {
    encoder.encodeEnum(descriptor, TypeTagVariants.U8.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagU8 {
    TODO("Not yet implemented")
  }
}

object TypeTagU16Serializer : KSerializer<TypeTagU16> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagU16", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagU16) {
    encoder.encodeEnum(descriptor, TypeTagVariants.U16.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagU16 {
    TODO("Not yet implemented")
  }
}

object TypeTagU32Serializer : KSerializer<TypeTagU32> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagU32", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagU32) {
    encoder.encodeEnum(descriptor, TypeTagVariants.U32.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagU32 {
    TODO("Not yet implemented")
  }
}

object TypeTagU64Serializer : KSerializer<TypeTagU64> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagU64", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagU64) {
    encoder.encodeEnum(descriptor, TypeTagVariants.U64.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagU64 {
    TODO("Not yet implemented")
  }
}

object TypeTagU128Serializer : KSerializer<TypeTagU128> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagU128", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagU128) {
    encoder.encodeEnum(descriptor, TypeTagVariants.U128.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagU128 {
    TODO("Not yet implemented")
  }
}

object TypeTagU256Serializer : KSerializer<TypeTagU256> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagU256", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagU256) {
    encoder.encodeEnum(descriptor, TypeTagVariants.U256.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagU256 {
    TODO("Not yet implemented")
  }
}

object TypeTagVectorSerializer : KSerializer<TypeTagVector> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagVector", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagVector) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Vector.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagVector {
    TODO("Not yet implemented")
  }
}

object TypeTagStructSerializer : KSerializer<TypeTagStruct> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("TypeTagStruct", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: TypeTagStruct) {
    encoder.encodeEnum(descriptor, TypeTagVariants.Struct.ordinal)
  }

  override fun deserialize(decoder: Decoder): TypeTagStruct {
    TODO("Not yet implemented")
  }
}
