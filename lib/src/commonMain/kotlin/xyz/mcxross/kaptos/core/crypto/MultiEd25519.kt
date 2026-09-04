/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme

/** A legacy Aptos K-of-N Ed25519 public key. */
class MultiEd25519PublicKey(
  publicKeys: List<Ed25519PublicKey>,
  val threshold: UByte,
) : AccountPublicKey() {
  val publicKeys: List<Ed25519PublicKey> = publicKeys.toList()

  init {
    require(this.publicKeys.size in MIN_KEYS..MAX_KEYS) {
      "MultiEd25519 requires between $MIN_KEYS and $MAX_KEYS public keys"
    }
    require(threshold.toInt() in 1..this.publicKeys.size) {
      "threshold must be between 1 and ${this.publicKeys.size}"
    }
  }

  override fun authKey(): AuthenticationKey =
    AuthenticationKey.fromSchemeAndBytes(
      AuthenticationKeyScheme.Signing(SigningScheme.MultiEd25519),
      HexInput.fromByteArray(toByteArray()),
    )

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    if (signature !is MultiEd25519Signature || signature.signatures.size < threshold.toInt()) {
      return false
    }
    return signature.signerIndices.zip(signature.signatures).all { (index, value) ->
      index < publicKeys.size && publicKeys[index].verifySignature(message, value)
    }
  }

  override fun toByteArray(): ByteArray =
    publicKeys.fold(ByteArray(0)) { bytes, key -> bytes + key.toByteArray() } +
      byteArrayOf(threshold.toByte())

  override fun toBcs(): ByteArray = encodeBcsBytes(toByteArray())

  override fun equals(other: Any?): Boolean =
    other is MultiEd25519PublicKey &&
      threshold == other.threshold &&
      publicKeys.map { it.toByteArray().toList() } == other.publicKeys.map { it.toByteArray().toList() }

  override fun hashCode(): Int = 31 * publicKeys.fold(1) { value, key ->
    31 * value + key.toByteArray().contentHashCode()
  } + threshold.hashCode()

  companion object {
    const val MIN_KEYS = 2
    const val MAX_KEYS = 32
  }
}

/** Ed25519 signatures and their canonical 32-bit Aptos signer bitmap. */
class MultiEd25519Signature(
  signatures: List<Ed25519Signature>,
  bitmap: ByteArray,
) : Signature() {
  val signatures: List<Ed25519Signature> = signatures.toList()
  private val bitmapBytes = bitmap.copyOf()
  val bitmap: ByteArray
    get() = bitmapBytes.copyOf()

  val signerIndices: List<Int>
    get() = buildList {
      bitmapBytes.forEachIndexed { byteIndex, byte ->
        repeat(8) { bit ->
          if ((byte.toInt() and (0x80 ushr bit)) != 0) add(byteIndex * 8 + bit)
        }
      }
    }

  init {
    require(signatures.size <= 32) { "MultiEd25519 supports at most 32 signatures" }
    require(bitmapBytes.size == BITMAP_LENGTH) { "MultiEd25519 bitmap must be 4 bytes" }
    require(signerIndices.size == signatures.size) {
      "Bitmap selects ${signerIndices.size} signers, but ${signatures.size} signatures were supplied"
    }
  }

  override fun toByteArray(): ByteArray =
    signatures.fold(ByteArray(0)) { bytes, signature -> bytes + signature.toByteArray() } + bitmapBytes

  override fun toBcs(): ByteArray = encodeBcsBytes(toByteArray())

  companion object {
    const val BITMAP_LENGTH = 4

    fun bitmapOf(indices: Iterable<Int>): ByteArray {
      val bitmap = ByteArray(BITMAP_LENGTH)
      val unique = mutableSetOf<Int>()
      indices.forEach { index ->
        require(index in 0..<32) { "Signer index must be between 0 and 31" }
        require(unique.add(index)) { "Duplicate signer index: $index" }
        bitmap[index / 8] = (bitmap[index / 8].toInt() or (0x80 ushr (index % 8))).toByte()
      }
      return bitmap
    }
  }
}
