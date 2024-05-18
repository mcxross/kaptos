package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.model.HexInput

/**
 * An interface of a private key. It is associated to a signature scheme and provides signing
 * capabilities.
 */
interface PrivateKey {

  /**
   * Sign the given message with the private key.
   *
   * @param message in [HexInput] format
   */
  fun sign(message: HexInput): Signature

  /** Derive the public key associated with the private key */
  fun publicKey(): PublicKey

  /** Get the private key in bytes. */
  fun toByteArray(): ByteArray
}
