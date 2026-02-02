/*
 * Copyright 2025 McXross
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
package xyz.mcxross.kaptos.core.crypto.multikey

import kotlin.IllegalArgumentException
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.encodeBcsBytes
import xyz.mcxross.kaptos.core.crypto.encodeUleb128

/**
 * Represents a multi-signature transaction using Ed25519 signatures. This class allows for the
 * creation and management of a K-of-N multi-signature scheme, where a specified number of
 * signatures are required to authorize a transaction.
 *
 * This implementation is compatible with Kotlin Multiplatform (Common, JVM, JS, Native).
 *
 * @property signatures The list of underlying signatures.
 * @property bitmap 4-byte (32-bit) array representing which of the N possible signers have signed.
 */
class MultiKeySignature(val signatures: List<AnySignature>, val bitmap: ByteArray) : Signature() {

  init {
    if (signatures.size > MAX_SIGNATURES_SUPPORTED) {
      throw IllegalArgumentException(
        "The number of signatures cannot be greater than $MAX_SIGNATURES_SUPPORTED"
      )
    }

    if (bitmap.size != BITMAP_LEN) {
      throw IllegalArgumentException("Bitmap length should be $BITMAP_LEN bytes")
    }

    // KMP-safe way to count set bits.
    // `it.toInt() and 0xFF` treats the signed Byte as an unsigned Int.
    // `countOneBits()` is the platform-agnostic Kotlin function for counting set bits.
    val nSignatures = bitmap.sumOf { (it.toInt() and 0xFF).countOneBits() }

    if (nSignatures != signatures.size) {
      throw IllegalArgumentException(
        "Expecting $nSignatures signatures from the bitmap, but got ${signatures.size}"
      )
    }
  }

  /**
   * Serializes the MultiKeySignature into its raw byte representation in a KMP-compatible way. The
   * format is the concatenation of all individual signatures followed by the 4-byte bitmap.
   *
   * @return The serialized byte array.
   */
  override fun toBcs(): ByteArray {
    // BCS encoding for vector<AnySignature> followed by BCS bytes(bitmap).
    val allSignaturesBytes =
      signatures.fold(ByteArray(0)) { acc, signature -> acc + signature.toBcs() }
    return encodeUleb128(signatures.size) + allSignaturesBytes + encodeBcsBytes(bitmap)
  }

  /** Returns the raw byte representation of the signature. This is an alias for `toBcs()`. */
  override fun toByteArray(): ByteArray = toBcs()

  /**
   * Converts the bitmap to a list of signer indices.
   *
   * @return A list of integer indices for each signer who provided a signature.
   */
  fun bitMapToSignerIndices(): List<Int> {
    val signerIndices = mutableListOf<Int>()
    bitmap.forEachIndexed { byteIndex, byteValue ->
      for (bit in 0..7) {
        if ((byteValue.toInt() and 0xFF) and (128 ushr bit) != 0) {
          signerIndices.add(byteIndex * 8 + bit)
        }
      }
    }
    return signerIndices
  }

  companion object {
    /** Number of bytes in the bitmap (32-bits). */
    const val BITMAP_LEN = 4

    /** Maximum number of signatures supported. */
    const val MAX_SIGNATURES_SUPPORTED = BITMAP_LEN * 8

    /**
     * Helper method to create a bitmap from a list of specified bit positions.
     *
     * @param bits The list of bit positions (0-31) that should be set to '1'.
     * @return A 4-byte array representing the bitmap.
     */
    fun createBitmap(bits: List<Int>): ByteArray {
      val bitmap = ByteArray(BITMAP_LEN) { 0 }
      val dupCheckSet = mutableSetOf<Int>()

      val firstBitInByte = 128 // 0b10000000

      bits.forEach { bit ->
        if (bit >= MAX_SIGNATURES_SUPPORTED) {
          throw IllegalArgumentException(
            "Signature index cannot be greater than ${MAX_SIGNATURES_SUPPORTED - 1}."
          )
        }
        if (!dupCheckSet.add(bit)) {
          throw IllegalArgumentException("Duplicate bit index detected: $bit")
        }

        val byteOffset = bit / 8
        val bitOffsetInByte = bit % 8

        val mask = firstBitInByte ushr bitOffsetInByte
        bitmap[byteOffset] = (bitmap[byteOffset].toInt() or mask).toByte()
      }
      return bitmap
    }
  }
}
