package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.model.AnyPublicKeyVariant
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme

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
      HexInput.fromByteArray(publicKey.toByteArray()),
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
