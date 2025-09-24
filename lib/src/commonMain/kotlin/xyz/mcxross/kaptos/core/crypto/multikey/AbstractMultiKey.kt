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

import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.core.crypto.PublicKey


const val BITMAP_SIZE_IN_BYTES = 4
const val MAX_SIGNATURES_SUPPORTED = BITMAP_SIZE_IN_BYTES * 8

abstract class AbstractMultiKey(open val publicKeys: List<PublicKey>) : AccountPublicKey() {

  /**
   * Creates a bitmap that holds the mapping from the original public keys to the signatures passed
   * in.
   *
   * @param bits A list of indices (0-31) mapping to the matching public keys that have provided
   *   signatures.
   * @return A 4-byte ByteArray representing the bitmap.
   * @throws IllegalArgumentException if validation fails (e.g., too many signatures, duplicate
   *   bits, invalid bit index).
   */
  fun createBitmap(bits: List<Int>): ByteArray {

    val firstBitInByte = 128

    val bitmap = ByteArray(BITMAP_SIZE_IN_BYTES)

    val dupCheckSet = mutableSetOf<Int>()

    bits.forEachIndexed { index, bit ->
      if (index + 1 > publicKeys.size) {
        throw IllegalArgumentException(
          "Signature count ${index + 1} is out of public keys range (${this.publicKeys.size})."
        )
      }

      if (bit !in 0 until MAX_SIGNATURES_SUPPORTED) {
        throw IllegalArgumentException(
          "Bit index $bit is out of the valid range (0-${MAX_SIGNATURES_SUPPORTED - 1})."
        )
      }

      if (!dupCheckSet.add(bit)) {
        throw IllegalArgumentException("Duplicate bit $bit detected.")
      }

      val byteOffset = bit / 8

      var byteValue = bitmap[byteOffset].toInt() and 0xFF

      byteValue = byteValue or (firstBitInByte shr (bit % 8))

      bitmap[byteOffset] = byteValue.toByte()
    }

    return bitmap
  }

    /**
     * Gets the number of signatures required for this multi-key set.
     *
     * @return The number of required signatures.
     */
    abstract fun getSigsRequired(): Int

    /**
     * Gets the index of the provided public key.
     *
     * This function retrieves the index of a specified public key within the MultiKey.
     * If the public key does not exist, it throws an error.
     *
     * @param publicKey The public key to find the index for. Can be any type whose `toString()`
     * representation matches one in the `publicKeys` list.
     * @return The corresponding index of the public key, if it exists.
     * @throws IllegalArgumentException If the public key is not found in the MultiKey.
     */
    fun index(publicKey: Any): Int {
        val index = publicKeys.indexOfFirst { it.toString() == publicKey.toString() }

        if (index != -1) {
            return index
        }
        throw IllegalArgumentException("Public key '$publicKey' not found in multi key set.")
    }
}

