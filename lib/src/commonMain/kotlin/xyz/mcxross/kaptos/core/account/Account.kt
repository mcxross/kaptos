package xyz.mcxross.kaptos.core.account

import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.PrivateKey
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.*

abstract class Account {

  /** Public key associated with the account */
  abstract val publicKey: PublicKey

  /** Account address associated with the account */
  abstract val accountAddress: AccountAddress

  /** Signing scheme used to sign transactions */
  abstract val signingScheme: SigningScheme

  /**
   * Sign the given message with the private key.
   *
   * @param message in HexInput format
   * @returns AccountSignature
   */
  abstract fun sign(message: HexInput): Signature

  companion object {
    fun generate(
      scheme: SigningSchemeInput = SigningSchemeInput.Ed25519,
      legacy: Boolean = true,
    ): Account {
      if (scheme === SigningSchemeInput.Ed25519 && legacy) {
        return Ed25519Account.generate()
      }

      throw NotImplementedError("Only Ed25519 is supported at the moment")
    }

    fun fromPrivateKey(
      privateKey: PrivateKey,
      address: AccountAddressInput? = null,
      legacy: Boolean = true,
    ): Account {
      if (privateKey is Ed25519PrivateKey && legacy) {
        return Ed25519Account(privateKey, address)
      }
      throw NotImplementedError("Only Ed25519 is supported at the moment")
    }
  }
}
