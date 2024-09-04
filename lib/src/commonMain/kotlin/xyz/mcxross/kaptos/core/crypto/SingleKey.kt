package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.model.*

/**
 * Represents any public key supported by Aptos.
 *
 * Since [AIP-55](https://github.com/aptos-foundation/AIPs/pull/263) Aptos supports `Legacy` and
 * `Unified` authentication keys.
 *
 * Any unified authentication key is represented in the SDK as `AnyPublicKey`.
 */
class AnyPublicKey(val publicKey: PublicKey) : AccountPublicKey() {

  val variant: AnyPublicKeyVariant
    get() =
      when (publicKey) {
        is Ed25519PublicKey -> AnyPublicKeyVariant.Ed25519
        is Secp256k1PublicKey -> AnyPublicKeyVariant.Secp256k1
        else -> throw IllegalArgumentException("Unsupported public key type")
      }

  override fun authKey(): AuthenticationKey {
    return AuthenticationKey.fromSchemeAndBytes(
      AuthenticationKeyScheme.Signing(SigningScheme.SingleKey),
      HexInput.fromByteArray(
        Bcs.encodeToByteArray(variant) + Bcs.encodeToByteArray(publicKey.toByteArray())
      ),
    )
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    TODO("Not yet implemented")
  }

  override fun toByteArray(): ByteArray = publicKey.toByteArray()

  override fun toBcs(): ByteArray {
    TODO("Not yet implemented")
  }
}

/**
 * Instance of signature that uses the SingleKey authentication scheme. This signature can only be
 * generated by a `SingleKeySigner`, since it uses the same authentication scheme.
 */
class AnySignature(val signature: Signature) : Signature() {

  var variant: AnySignatureVariant =
    when (signature) {
      is Ed25519Signature -> AnySignatureVariant.Ed25519
      is Secp256k1Signature -> AnySignatureVariant.Secp256k1
      else -> throw IllegalArgumentException("Unsupported signature type")
    }

  override fun toByteArray(): ByteArray =
    Bcs.encodeToByteArray(variant.value) + signature.toByteArray()

  override fun toBcs(): ByteArray {
    TODO("Not yet implemented")
  }
}
