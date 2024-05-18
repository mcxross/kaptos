package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.SigningScheme

/**
 * Represents the public key of an Ed25519 key pair.
 *
 * Since [AIP-55](https://github.com/aptos-foundation/AIPs/pull/263) Aptos supports `Legacy` and
 * `Unified` authentication keys.
 *
 * Ed25519 scheme is represented in the SDK as `Legacy authentication key` and also as
 * `AnyPublicKey` that represents any `Unified authentication key`
 */
class Ed25519PublicKey(data: HexInput) : AccountPublicKey() {

  private var hex: Hex

  init {
    val hex = Hex.fromHexInput(data)
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException("Ed25519 public key must be 32 bytes")
    }
    this.hex = hex
  }

  override fun authKey(): AuthenticationKey =
    AuthenticationKey.fromSchemeAndBytes(
      AuthenticationKeyScheme.Signing(scheme = SigningScheme.Ed25519),
      HexInput.fromByteArray(hex.toByteArray()),
    )

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    if (signature !is Ed25519Signature) {
      return false
    }

    val messageBytes = Hex.fromHexInput(message).toByteArray()
    val signatureBytes = signature.toByteArray()
    val publicKeyBytes = hex.toByteArray()

    TODO("Not yet implemented: We should call the actual Ed25519 verify function here")
  }

  /**
   * Get the public key in bytes (ByteArray).
   *
   * @return [ByteArray] representation of the public key
   */
  override fun toByteArray(): ByteArray = hex.toByteArray()

  companion object {
    /** Length of an Ed25519 public key */
    const val LENGTH = 32
  }
}

/** Represents the private key of an Ed25519 key pair. */
class Ed25519PrivateKey(data: HexInput) : PrivateKey {

  /** The Ed25519 signing key */
  private val signingKeyPair: KeyPair

  init {
    val hex = Hex.fromHexInput(data)
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException(
        "Ed25519 private key must be 32 bytes, but instead got ${hex.toByteArray().size} bytes"
      )
    }
    signingKeyPair = KeyPair.fromSecretSeed(hex.toByteArray())
  }

  /**
   * Sign the given message with the private key.
   *
   * @param message in HexInput format
   * @return [Signature]
   */
  override fun sign(message: HexInput): Signature {
    val messageBytes = Hex.fromHexInput(message).toByteArray()
    return signingKeyPair.sign(messageBytes)
  }

  /**
   * Derive the Ed25519PublicKey for this private key.
   *
   * @return Ed25519PublicKey
   */
  override fun publicKey(): Ed25519PublicKey =
    Ed25519PublicKey(HexInput.fromByteArray(signingKeyPair.publicKey))

  /**
   * Get the private key in bytes (ByteArray).
   *
   * @return [ByteArray] representation of the private key
   */
  override fun toByteArray(): ByteArray = signingKeyPair.privateKey

  /**
   * Get the private key as a hex string with the 0x prefix.
   *
   * @return string representation of the private key
   */
  override fun toString(): String = Hex.fromHexInput(this.toByteArray()).toString()

  companion object {
    /** Length of an Ed25519 private key */
    const val LENGTH = 32

    /** Generate a new Ed25519 key pair */
    fun generate(): Ed25519PrivateKey {
      val keyPair = generateKeypair(SigningScheme.Ed25519)
      return Ed25519PrivateKey(HexInput.fromByteArray(keyPair.privateKey))
    }
  }
}

/** A signature of a message signed using an Ed25519 private key */
class Ed25519Signature(hexInput: HexInput) : Signature() {

  private var data: Hex

  init {
    val hex = Hex.fromHexInput(hexInput)
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException("Ed25519 signature must be 64 bytes")
    }
    this.data = hex
  }

  override fun toByteArray(): ByteArray = data.toByteArray()

  override fun toString(): String = data.toString()

  companion object {
    /** Length of an Ed25519 signature */
    const val LENGTH = 64
  }
}
