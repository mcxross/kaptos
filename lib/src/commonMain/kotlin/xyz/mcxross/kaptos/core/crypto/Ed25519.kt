/*
 * Copyright 2024 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.mcxross.kaptos.core.crypto

import kotlinx.serialization.Serializable
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput
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
@Serializable
class Ed25519PublicKey(private val data: HexInput) : AccountPublicKey() {

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

  /**
   * Get the public key in BCS bytes (ByteArray).
   *
   * @return [ByteArray] representation of the public key
   */
  override fun toBcs(): ByteArray = Bcs.encodeToByteArray(toByteArray())

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

  constructor(data: String) : this(HexInput.fromString(data))

  /**
   * Sign the given message with the private key.
   *
   * @param message in HexInput format
   * @return [Signature]
   */
  override fun sign(message: HexInput): Ed25519Signature {
    val messageBytes = Hex.fromHexInput(message).toByteArray()
    return signingKeyPair.sign(messageBytes) as Ed25519Signature
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
@Serializable
class Ed25519Signature(private val hexInput: HexInput) : Signature() {

  private var data: Hex

  init {
    val hex = Hex.fromHexInput(hexInput)
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException("Ed25519 signature must be 64 bytes")
    }
    this.data = hex
  }

  override fun toByteArray(): ByteArray = data.toByteArray()

  override fun toBcs(): ByteArray = Bcs.encodeToByteArray(toByteArray())

  override fun toString(): String = data.toString()

  companion object {
    /** Length of an Ed25519 signature */
    const val LENGTH = 64
  }
}
