package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.model.HexInput

data class VerifySignatureArgs(val message: HexInput, val signature: Signature)

/**
 * An abstract representation of a public key.
 *
 * Provides a common interface for verifying any signature.
 */
abstract class PublicKey {

  /**
   * Verifies that the private key associated with this public key signed the message with the given
   * signature.
   *
   * @param message The message that was signed
   * @param signature The signature to verify
   */
  abstract fun verifySignature(message: HexInput, signature: Signature): Boolean

  /** Get the raw public key bytes */
  abstract fun toByteArray(): ByteArray

  /** Get the public key as a hex string with a 0x prefix e.g. 0x123456... */
  override fun toString(): String =
    "0x${toByteArray().joinToString("") { it.toUByte().toString(16).padStart(2, '0') }}"
}

/**
 * An abstract representation of an account public key.
 *
 * Provides a common interface for deriving an authentication key.
 */
abstract class AccountPublicKey : PublicKey() {
  /** Get the authentication key associated with this public key */
  abstract fun authKey(): AuthenticationKey
}
