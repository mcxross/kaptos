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