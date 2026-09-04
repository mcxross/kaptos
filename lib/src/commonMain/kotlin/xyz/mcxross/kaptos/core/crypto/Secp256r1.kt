/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.core.crypto

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningSchemeInput

/** A NIST P-256 (Secp256r1) public key in canonical uncompressed SEC1 form. */
class Secp256r1PublicKey(key: ByteArray) : PublicKey() {
  private val bytes = normalizeSecp256r1PublicKey(key.copyOf())

  override fun verifySignature(message: HexInput, signature: Signature): Boolean =
    when (signature) {
      is Secp256r1Signature ->
        verifySecp256r1Signature(bytes, message.toByteArray(), signature.toByteArray())
      is WebAuthnSignature -> signature.verify(this, message.toByteArray())
      else -> false
    }

  fun verifyBytes(message: ByteArray, signature: Secp256r1Signature): Boolean =
    verifySecp256r1Signature(bytes, message, signature.toByteArray())

  fun verifyText(message: String, signature: Secp256r1Signature): Boolean =
    verifyBytes(message.encodeToByteArray(), signature)

  override fun toByteArray(): ByteArray = bytes.copyOf()

  override fun toBcs(): ByteArray = encodeBcsBytes(bytes)

  companion object {
    const val LENGTH = 65
    const val COMPRESSED_LENGTH = 33
  }
}

/** A NIST P-256 private key using Aptos' SHA3-256 signing convention. */
class Secp256r1PrivateKey(key: ByteArray) : PrivateKey {
  private val bytes = key.copyOf()

  override var isCleared: Boolean = false
    private set

  init {
    require(bytes.size == LENGTH) { "Secp256r1 private key must be $LENGTH bytes" }
    // Validate that the scalar derives a valid curve point immediately.
    generateSecp256r1PublicKey(bytes)
  }

  constructor(key: HexInput) : this(key.toByteArray())

  constructor(key: String) : this(HexInput.fromString(key))

  fun signBytes(message: ByteArray): Secp256r1Signature {
    checkNotCleared()
    return Secp256r1Signature(secp256r1SignAptos(message, bytes))
  }

  fun signText(message: String): Secp256r1Signature = signBytes(message.encodeToByteArray())

  override fun sign(message: HexInput): Secp256r1Signature = signBytes(message.toByteArray())

  override fun publicKey(): Secp256r1PublicKey {
    checkNotCleared()
    return Secp256r1PublicKey(generateSecp256r1PublicKey(bytes))
  }

  override fun toByteArray(): ByteArray {
    checkNotCleared()
    return bytes.copyOf()
  }

  override fun toAip80(): Aip80PrivateKey {
    checkNotCleared()
    return Aip80PrivateKey.fromBytes(PrivateKeyType.Secp256r1, toByteArray())
  }

  override fun clear() {
    if (isCleared) return
    bytes.fill(0)
    isCleared = true
  }

  override fun toString(): String =
    if (isCleared) "<cleared Secp256r1 private key>" else Hex(bytes).toString()

  private fun checkNotCleared() {
    check(!isCleared) { "Secp256r1 private key has been cleared" }
  }

  companion object {
    const val LENGTH = 32

    fun generate(): Secp256r1PrivateKey {
      val keyPair = generateKeypair(SigningSchemeInput.Secp256r1)
      return Secp256r1PrivateKey(keyPair.privateKey)
    }

    /** Import a P-256 private key from the safe, algorithm-prefixed AIP-80 format. */
    fun fromAip80(value: String): Secp256r1PrivateKey = fromAip80(Aip80PrivateKey.parse(value))

    fun fromAip80(value: Aip80PrivateKey): Secp256r1PrivateKey =
      Secp256r1PrivateKey(value.bytes(PrivateKeyType.Secp256r1))

    /** Explicit opt-in for applications migrating an unprefixed private-key hex value. */
    fun fromLegacyHex(value: String): Secp256r1PrivateKey =
      Secp256r1PrivateKey(decodeLegacyPrivateKeyHex(value))
  }
}

/** A canonical 64-byte `(r, s)` P-256 signature. */
class Secp256r1Signature(signature: ByteArray) : Signature() {
  private val bytes = signature.copyOf()

  init {
    require(bytes.size == LENGTH) { "Secp256r1 signature must be $LENGTH bytes" }
  }

  constructor(signature: HexInput) : this(signature.toByteArray())

  constructor(signature: String) : this(HexInput.fromString(signature))

  override fun toByteArray(): ByteArray = bytes.copyOf()

  override fun toBcs(): ByteArray = encodeBcsBytes(bytes)

  companion object {
    const val LENGTH = 64
  }
}

/** Validation policy for locally verifying a WebAuthn transaction signature. */
data class WebAuthnVerificationOptions(
  val expectedOrigin: String? = null,
  val requireUserPresence: Boolean = true,
  val requireUserVerification: Boolean = false,
)

/**
 * A WebAuthn assertion in the exact Aptos BCS layout.
 *
 * Credential acquisition stays application-owned; this type validates the assertion and packages
 * it for a SingleKey authenticator.
 */
class WebAuthnSignature(
  signature: ByteArray,
  authenticatorData: ByteArray,
  clientDataJson: ByteArray,
) : Signature() {
  private val signatureBytes = signature.copyOf()
  private val authenticatorBytes = authenticatorData.copyOf()
  private val clientDataBytes = clientDataJson.copyOf()

  init {
    require(signatureBytes.size == Secp256r1Signature.LENGTH) {
      "WebAuthn P-256 signature must be ${Secp256r1Signature.LENGTH} bytes"
    }
    require(authenticatorBytes.size >= MIN_AUTHENTICATOR_DATA_LENGTH) {
      "WebAuthn authenticator data must be at least $MIN_AUTHENTICATOR_DATA_LENGTH bytes"
    }
    require(clientDataBytes.isNotEmpty()) { "WebAuthn clientDataJSON must not be empty" }
  }

  constructor(
    signature: HexInput,
    authenticatorData: HexInput,
    clientDataJson: HexInput,
  ) : this(signature.toByteArray(), authenticatorData.toByteArray(), clientDataJson.toByteArray())

  val authenticatorData: ByteArray
    get() = authenticatorBytes.copyOf()

  val clientDataJson: ByteArray
    get() = clientDataBytes.copyOf()

  fun verify(
    publicKey: Secp256r1PublicKey,
    signingMessage: ByteArray,
    options: WebAuthnVerificationOptions = WebAuthnVerificationOptions(),
  ): Boolean {
    val clientData =
      runCatching { Json.parseToJsonElement(clientDataBytes.decodeToString()).jsonObject }
        .getOrNull() ?: return false
    fun stringField(name: String): String? =
      runCatching { clientData[name]?.jsonPrimitive?.content }.getOrNull()

    if (stringField("type") != "webauthn.get") return false
    if (stringField("challenge") != base64Url(sha3Hash(signingMessage))) {
      return false
    }
    if (options.expectedOrigin != null && stringField("origin") != options.expectedOrigin) {
      return false
    }
    if (stringField("crossOrigin") == "true") return false

    val flags = authenticatorBytes[32].toInt() and 0xff
    if (options.requireUserPresence && flags and USER_PRESENT_FLAG == 0) return false
    if (options.requireUserVerification && flags and USER_VERIFIED_FLAG == 0) return false

    return verifyWebAuthnSignature(
      publicKey = publicKey.toByteArray(),
      authenticatorData = authenticatorBytes,
      clientDataJson = clientDataBytes,
      signature = signatureBytes,
    )
  }

  override fun toByteArray(): ByteArray = signatureBytes.copyOf()

  override fun toBcs(): ByteArray =
    encodeUleb128(0) +
      encodeBcsBytes(signatureBytes) +
      encodeBcsBytes(authenticatorBytes) +
      encodeBcsBytes(clientDataBytes)

  companion object {
    private const val MIN_AUTHENTICATOR_DATA_LENGTH = 37
    private const val USER_PRESENT_FLAG = 0x01
    private const val USER_VERIFIED_FLAG = 0x04
  }
}

private fun base64Url(bytes: ByteArray): String {
  val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
  val output = StringBuilder((bytes.size * 4 + 2) / 3)
  var index = 0
  while (index < bytes.size) {
    val first = bytes[index].toInt() and 0xff
    val second = if (index + 1 < bytes.size) bytes[index + 1].toInt() and 0xff else -1
    val third = if (index + 2 < bytes.size) bytes[index + 2].toInt() and 0xff else -1
    output.append(alphabet[first ushr 2])
    output.append(alphabet[((first and 0x03) shl 4) or if (second >= 0) second ushr 4 else 0])
    if (second >= 0) {
      output.append(alphabet[((second and 0x0f) shl 2) or if (third >= 0) third ushr 6 else 0])
    }
    if (third >= 0) output.append(alphabet[third and 0x3f])
    index += 3
  }
  return output.toString()
}
