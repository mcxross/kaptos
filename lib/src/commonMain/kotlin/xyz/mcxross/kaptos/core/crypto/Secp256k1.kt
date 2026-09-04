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

import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningSchemeInput

/**
 * Represents the Secp256k1 ecdsa public key
 *
 * Secp256k1 authentication key is represented in the SDK as `AnyPublicKey`.
 */
class Secp256k1PublicKey(val hexInput: HexInput) : PublicKey() {

  private val hex: Hex

  constructor(pk: String) : this(HexInput.fromString(pk))

  init {
    val hex = Hex.fromHexInput(hexInput)

    if (hex.toByteArray().size != LENGTH && hex.toByteArray().size != 33) {
      throw IllegalArgumentException("Secp256k1 public key must be 65 or 33 bytes")
    }

    this.hex = hex
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean =
    verifySignature(this, message.toByteArray(), signature.toByteArray())

  override fun toByteArray(): ByteArray = hex.toByteArray()

  override fun toBcs(): ByteArray = encodeBcsBytes(toByteArray())

  companion object {
    const val LENGTH = 65
  }
}

/** A Secp256k1 ecdsa private key */
class Secp256k1PrivateKey(key: ByteArray) : PrivateKey {

  private val hex: Hex
  override var isCleared: Boolean = false
    private set

  init {
    val hex = Hex.fromHexInput(HexInput.fromByteArray(key.copyOf()))
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException("Secp256k1 private key must be 32 bytes")
    }
    this.hex = hex
  }

  override fun sign(message: HexInput): Secp256k1Signature {
    checkNotCleared()
    val hash = sha3Hash(message.toByteArray())
    return Secp256k1Signature(HexInput.fromByteArray(secp256k1Sign(hash, hex.toByteArray())))
  }

  override fun publicKey(): Secp256k1PublicKey {
    checkNotCleared()
    val pk = generateSecp256k1PublicKey(hex.toByteArray())
    return Secp256k1PublicKey(HexInput.fromByteArray(pk))
  }

  override fun toByteArray(): ByteArray {
    checkNotCleared()
    return hex.toByteArray().copyOf()
  }

  override fun toAip80(): Aip80PrivateKey {
    checkNotCleared()
    return Aip80PrivateKey.fromBytes(PrivateKeyType.Secp256k1, toByteArray())
  }

  override fun clear() {
    if (isCleared) return
    hex.toByteArray().fill(0)
    isCleared = true
  }

  private fun checkNotCleared() {
    check(!isCleared) { "Secp256k1 private key has been cleared" }
  }

  override fun toString(): String {
    return if (isCleared) "<cleared Secp256k1 private key>" else hex.toString()
  }

  companion object {
    const val LENGTH = 32

    fun generate(): Secp256k1PrivateKey {
      val keyPair = generateKeypair(SigningSchemeInput.Secp256k1)
      return Secp256k1PrivateKey(keyPair.privateKey)
    }

    /** Import a Secp256k1 private key from the safe, algorithm-prefixed AIP-80 format. */
    fun fromAip80(value: String): Secp256k1PrivateKey = fromAip80(Aip80PrivateKey.parse(value))

    fun fromAip80(value: Aip80PrivateKey): Secp256k1PrivateKey =
      Secp256k1PrivateKey(value.bytes(PrivateKeyType.Secp256k1))

    /** Explicit opt-in for applications migrating an unprefixed private-key hex value. */
    fun fromLegacyHex(value: String): Secp256k1PrivateKey =
      Secp256k1PrivateKey(decodeLegacyPrivateKeyHex(value))
  }
}

class Secp256k1Signature(hexInput: HexInput) : Signature() {

  private val hex: Hex

  init {
    val hex = Hex.fromHexInput(hexInput)
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException("Secp256k1 signature must be 64 bytes")
    }
    this.hex = hex
  }

  override fun toByteArray(): ByteArray = hex.toByteArray()

  override fun toBcs(): ByteArray = encodeBcsBytes(toByteArray())

  companion object {
    /** Secp256k1 ecdsa signatures are 256-bit. */
    const val LENGTH = 64
  }
}
