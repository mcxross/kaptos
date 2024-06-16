package xyz.mcxross.kaptos.serialize

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.model.AccountAuthenticatorVariant
import xyz.mcxross.kaptos.transaction.authenticatior.TransactionAuthenticator

object TransactionAuthenticatorSerializer : KSerializer<TransactionAuthenticator> {
  override val descriptor: SerialDescriptor =
    buildClassSerialDescriptor("TransactionAuthenticator") {
      element("accountAuthenticatorVariant", AccountAuthenticatorVariant.serializer().descriptor)
      element("publicKey", Ed25519PublicKey.serializer().descriptor)
      element("signature", Ed25519Signature.serializer().descriptor)
    }

  override fun serialize(encoder: Encoder, value: TransactionAuthenticator) {
    val composite = encoder.beginStructure(descriptor)
    composite.encodeSerializableElement(
      descriptor,
      0,
      AccountAuthenticatorVariant.serializer(),
      value.accountAuthenticatorVariant,
    )
    composite.encodeSerializableElement(
      descriptor,
      1,
      Ed25519PublicKey.serializer(),
      value.publicKey,
    )
    composite.encodeSerializableElement(
      descriptor,
      2,
      Ed25519Signature.serializer(),
      value.signature,
    )
    composite.endStructure(descriptor)
  }

  override fun deserialize(decoder: Decoder): TransactionAuthenticator {
    val composite = decoder.beginStructure(descriptor)
    lateinit var accountAuthenticatorVariant: AccountAuthenticatorVariant
    lateinit var publicKey: Ed25519PublicKey
    lateinit var signature: Ed25519Signature
    loop@ while (true) {
      when (val index = composite.decodeElementIndex(descriptor)) {
        CompositeDecoder.DECODE_DONE -> break@loop
        0 ->
          accountAuthenticatorVariant =
            composite.decodeSerializableElement(
              descriptor,
              index,
              AccountAuthenticatorVariant.serializer(),
            )
        1 ->
          publicKey =
            composite.decodeSerializableElement(descriptor, index, Ed25519PublicKey.serializer())
        2 ->
          signature =
            composite.decodeSerializableElement(descriptor, index, Ed25519Signature.serializer())
        else -> throw SerializationException("Unknown index $index")
      }
    }
    composite.endStructure(descriptor)
    return TransactionAuthenticator(accountAuthenticatorVariant, publicKey, signature)
  }
}
