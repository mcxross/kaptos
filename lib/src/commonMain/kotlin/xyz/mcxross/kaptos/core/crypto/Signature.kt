package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.Hex

/**
 * An abstract representation of a crypto signature, associated to a specific signature scheme e.g.
 * Ed25519 or Secp256k1
 *
 * This is the product of signing a message directly from a PrivateKey and can be verified against a
 * CryptoPublicKey.
 */
abstract class Signature {

  /** Get the raw signature bytes */
  abstract fun toByteArray(): ByteArray

  /** Get the signature as a hex string with a 0x prefix e.g. 0x123456... */
  override fun toString(): String = Hex.fromHexInput(toByteArray()).toString()
}
