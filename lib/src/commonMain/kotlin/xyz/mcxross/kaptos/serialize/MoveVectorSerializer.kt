package xyz.mcxross.kaptos.serialize

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.mcxross.kaptos.model.MoveVector

object MoveVectorSerializer : KSerializer<MoveVector<*>> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("MoveVectorSerializer", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: MoveVector<*>) {
    throw IllegalArgumentException("Unimplemented transaction argument type")
  }

  override fun deserialize(decoder: Decoder): MoveVector<*> {
    return MoveVector(emptyList())
  }
}
