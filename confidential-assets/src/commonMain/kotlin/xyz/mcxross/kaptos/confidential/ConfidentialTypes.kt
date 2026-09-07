/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import xyz.mcxross.fastkrypto.twistedEd25519GenerateKeypair
import xyz.mcxross.fastkrypto.twistedEd25519PrivateKeyFromSignature
import xyz.mcxross.fastkrypto.twistedEd25519PublicKeyFromPrivate
import xyz.mcxross.fastkrypto.twistedElgamalDecrypt
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult

/** Number of 16-bit limbs in an Aptos confidential available balance. */
const val AVAILABLE_BALANCE_CHUNK_COUNT: Int = 8

/** Number of 16-bit limbs accepted by confidential transfer and withdrawal amounts. */
const val TRANSFER_AMOUNT_CHUNK_COUNT: Int = 4

/** Bit width of each Aptos confidential-amount limb. */
const val CONFIDENTIAL_CHUNK_BITS: Int = 16

/** Aptos twisted-Ed25519 encryption key, represented by one compressed Ristretto point. */
class ConfidentialEncryptionKey(bytes: ByteArray) {
  private val encoded = bytes.copyOf()

  init {
    require(encoded.size == 32) { "A confidential encryption key must contain 32 bytes" }
  }

  fun toByteArray(): ByteArray = encoded.copyOf()

  override fun equals(other: Any?): Boolean =
    other is ConfidentialEncryptionKey && encoded.contentEquals(other.encoded)

  override fun hashCode(): Int = encoded.contentHashCode()

  override fun toString(): String = "ConfidentialEncryptionKey(32 bytes)"
}

/** Clearable Aptos twisted-Ed25519 decryption key. */
class ConfidentialDecryptionKey private constructor(bytes: ByteArray) : AutoCloseable {
  private val secret = bytes.copyOf()
  var isCleared: Boolean = false
    private set

  init {
    require(secret.size == 32) { "A confidential decryption key must contain 32 bytes" }
  }

  val encryptionKey: ConfidentialEncryptionKey
    get() = withSecret { ConfidentialEncryptionKey(twistedEd25519PublicKeyFromPrivate(it)) }

  fun clear() {
    secret.fill(0)
    isCleared = true
  }

  override fun close() = clear()

  internal inline fun <T> withSecret(block: (ByteArray) -> T): T {
    check(!isCleared) { "The confidential decryption key has been cleared" }
    val copy = secret.copyOf()
    return try {
      block(copy)
    } finally {
      copy.fill(0)
    }
  }

  override fun toString(): String =
    if (isCleared) "ConfidentialDecryptionKey(cleared)" else "ConfidentialDecryptionKey(redacted)"

  companion object {
    fun generate(): ConfidentialDecryptionKey {
      val generated = twistedEd25519GenerateKeypair().privateKey
      return try {
        ConfidentialDecryptionKey(generated)
      } finally {
        generated.fill(0)
      }
    }

    /** Derive the key from the 64-byte account signature required by the Aptos protocol. */
    fun fromSignature(signature: ByteArray): ConfidentialDecryptionKey {
      val derived = twistedEd25519PrivateKeyFromSignature(signature.copyOf())
      return try {
        ConfidentialDecryptionKey(derived)
      } finally {
        derived.fill(0)
      }
    }

    /** Import one canonical little-endian scalar. Callers remain responsible for key custody. */
    fun fromBytes(bytes: ByteArray): ConfidentialDecryptionKey {
      val copy = bytes.copyOf()
      return try {
        // The backend validates canonicality and rejects zero without exposing the secret.
        twistedEd25519PublicKeyFromPrivate(copy)
        ConfidentialDecryptionKey(copy)
      } finally {
        copy.fill(0)
      }
    }
  }
}

/** Generates a confidential decryption key owned by this client's managed lifecycle. */
fun Aptos.confidentialDecryptionKey(): ConfidentialDecryptionKey =
  own(ConfidentialDecryptionKey.generate())

/** One Aptos twisted-ElGamal ciphertext `(C, D)`. */
class ConfidentialCiphertext(commitment: ByteArray, handle: ByteArray) {
  private val commitmentBytes = commitment.copyOf()
  private val handleBytes = handle.copyOf()

  init {
    require(commitmentBytes.size == 32) { "A confidential commitment must contain 32 bytes" }
    require(handleBytes.size == 32) { "A confidential handle must contain 32 bytes" }
  }

  val commitment: ByteArray
    get() = commitmentBytes.copyOf()

  val handle: ByteArray
    get() = handleBytes.copyOf()

  internal fun decrypt(key: ConfidentialDecryptionKey): AptosResult<UInt> =
    try {
      val value = key.withSecret {
        twistedElgamalDecrypt(
          privateKey = it,
          commitment = commitmentBytes,
          handle = handleBytes,
          // Homomorphic deposits can temporarily push an individual chunk above 16 bits.
          bitWidth = 32u,
        )
      }
      AptosResult.Success(value.toUInt())
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Crypto("Unable to decrypt confidential balance chunk", error))
    }

  override fun equals(other: Any?): Boolean =
    other is ConfidentialCiphertext &&
      commitmentBytes.contentEquals(other.commitmentBytes) &&
      handleBytes.contentEquals(other.handleBytes)

  override fun hashCode(): Int =
    31 * commitmentBytes.contentHashCode() + handleBytes.contentHashCode()
}

/** A fixed-width confidential amount, least-significant base-2^16 limb first. */
data class ConfidentialAmount(val chunks: List<UInt>) {
  init {
    require(chunks.isNotEmpty()) { "A confidential amount must contain at least one chunk" }
  }

  /** Lossless base-10 representation, including values wider than Kotlin's [ULong]. */
  val decimal: String
    get() =
      chunks.asReversed().fold("0") { value, chunk ->
        decimalAdd(decimalMultiply(value, 65_536), chunk)
      }

  val normalizedChunks: List<UInt>
    get() = normalizeChunks(chunks)

  fun fitsInULong(): Boolean = normalizedChunks.drop(4).all { it == 0u }

  fun toULong(): ULong {
    require(fitsInULong()) { "Confidential amount does not fit in ULong" }
    return normalizedChunks.take(4).foldIndexed(0uL) { index, value, chunk ->
      value or (chunk.toULong() shl (index * CONFIDENTIAL_CHUNK_BITS))
    }
  }
}

/** Available and pending ciphertexts plus their locally decrypted values. */
data class ConfidentialBalance(
  val available: List<ConfidentialCiphertext>,
  val pending: List<ConfidentialCiphertext>,
  val availableAmount: ConfidentialAmount,
  val pendingAmount: ConfidentialAmount,
)

/** Identifies whether the effective auditor is global and the key epoch to use. */
data class EffectiveAuditorHint(val isGlobal: Boolean, val epoch: ULong)

/** Current registration, normalization, and incoming-transfer state. */
data class ConfidentialAssetStatus(
  val registered: Boolean,
  val normalized: Boolean,
  val incomingTransfersPaused: Boolean,
)

internal data class EncryptedChunks(
  val ciphertexts: List<ConfidentialCiphertext>,
  val randomness: List<ByteArray>,
)

internal fun encryptChunks(
  key: ConfidentialEncryptionKey,
  chunks: List<UInt>,
  randomness: List<ByteArray>? = null,
): AptosResult<EncryptedChunks> =
  try {
    require(randomness == null || randomness.size == chunks.size) {
      "Randomness count must match the chunk count"
    }
    require(chunks.all { it <= 65_535u }) { "Normalized confidential chunks must fit in 16 bits" }
    val encrypted = chunks.mapIndexed { index, chunk ->
      xyz.mcxross.fastkrypto.twistedElgamalEncrypt(
        publicKey = key.toByteArray(),
        amount = chunk.toULong(),
        randomness = randomness?.get(index)?.copyOf(),
      )
    }
    AptosResult.Success(
      EncryptedChunks(
        ciphertexts = encrypted.map { ConfidentialCiphertext(it.commitment, it.handle) },
        randomness = encrypted.map { it.randomness.copyOf() },
      )
    )
  } catch (error: Throwable) {
    AptosResult.Failure(AptosError.Crypto("Unable to encrypt confidential amount", error))
  }

internal fun ULong.toChunks(count: Int): List<UInt> =
  List(count) { index ->
    if (index >= 4) 0u else ((this shr (index * CONFIDENTIAL_CHUNK_BITS)) and 0xffffu).toUInt()
  }

internal fun subtractChunks(value: List<UInt>, amount: ULong): AptosResult<List<UInt>> {
  val normalized = normalizeChunks(value)
  val subtrahend = amount.toChunks(value.size)
  var borrow = 0
  val result = MutableList(value.size) { 0u }
  for (index in value.indices) {
    val difference = normalized[index].toInt() - subtrahend[index].toInt() - borrow
    if (difference < 0) {
      result[index] = (difference + 65_536).toUInt()
      borrow = 1
    } else {
      result[index] = difference.toUInt()
      borrow = 0
    }
  }
  return if (borrow == 0) AptosResult.Success(result)
  else
    AptosResult.Failure(AptosError.Validation("Amount exceeds the available confidential balance"))
}

internal fun normalizeChunks(value: List<UInt>): List<UInt> {
  var carry = 0uL
  val result = MutableList(value.size) { 0u }
  for (index in value.indices) {
    val total = value[index].toULong() + carry
    result[index] = (total and 0xffffu).toUInt()
    carry = total shr CONFIDENTIAL_CHUNK_BITS
  }
  require(carry == 0uL) {
    "Confidential amount exceeds its ${value.size * CONFIDENTIAL_CHUNK_BITS}-bit width"
  }
  return result
}

private fun decimalMultiply(value: String, multiplier: Int): String {
  var carry = 0
  val reversed = StringBuilder()
  for (character in value.reversed()) {
    val product = (character - '0') * multiplier + carry
    reversed.append(product % 10)
    carry = product / 10
  }
  while (carry > 0) {
    reversed.append(carry % 10)
    carry /= 10
  }
  return reversed.reverse().toString().trimStart('0').ifEmpty { "0" }
}

private fun decimalAdd(value: String, addend: UInt): String {
  var carry = addend.toULong()
  val reversed = StringBuilder()
  for (character in value.reversed()) {
    val sum = (character - '0').toULong() + carry
    reversed.append(sum % 10u)
    carry = sum / 10u
  }
  while (carry > 0u) {
    reversed.append(carry % 10u)
    carry /= 10u
  }
  return reversed.reverse().toString().trimStart('0').ifEmpty { "0" }
}
