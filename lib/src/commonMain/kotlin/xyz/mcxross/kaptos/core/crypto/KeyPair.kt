package xyz.mcxross.kaptos.core.crypto

/**
 * Represents a pair of signing keys: a public key and a private key.
 *
 * @property privateKey The public key in hex format.
 * @property publicKey The private key in hex format.
 */
data class KeyPair(val privateKey: ByteArray, val publicKey: ByteArray) {

  fun sign(message: ByteArray): Signature {
    TODO()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as KeyPair

    if (!publicKey.contentEquals(other.publicKey)) return false
    if (!privateKey.contentEquals(other.privateKey)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = publicKey.contentHashCode()
    result = 31 * result + privateKey.contentHashCode()
    return result
  }

  companion object {
    fun fromSecretSeed(secretSeed: ByteArray): KeyPair {
      return fromSeed(secretSeed)
    }
  }
}
