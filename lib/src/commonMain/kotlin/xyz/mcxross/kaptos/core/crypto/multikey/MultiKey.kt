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

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.encodeUleb128
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme

class MultiKey(val pks: List<PublicKey>, val signaturesRequired: Int) :
  AbstractMultiKey(pks.map { if (it is AnyPublicKey) it else AnyPublicKey(it) }) {

  @Suppress("UNCHECKED_CAST")
  override val publicKeys: List<AnyPublicKey> = super.publicKeys as List<AnyPublicKey>

  init {
    if (signaturesRequired < 1) {
      throw IllegalArgumentException("The number of required signatures needs to be greater than 0")
    }
    if (signaturesRequired > UByte.MAX_VALUE.toInt()) {
      throw IllegalArgumentException("The number of required signatures must fit in a single byte")
    }

    if (publicKeys.size < signaturesRequired) {
      throw IllegalArgumentException(
        "Provided ${publicKeys.size} public keys is smaller than the $signaturesRequired required signatures"
      )
    }
  }

  override fun getSigsRequired(): Int = signaturesRequired

  override fun authKey(): AuthenticationKey {
    return AuthenticationKey.fromSchemeAndBytes(
      AuthenticationKeyScheme.Signing(SigningScheme.MultiKey),
      HexInput.fromByteArray(toBcs()),
    )
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    if (signature !is MultiKeySignature) return false

    if (signature.signatures.size != signaturesRequired) {
      return false
    }

    val signerIndices = signature.bitMapToSignerIndices()

    val signatureIndexPairs = signature.signatures.zip(signerIndices)

    for ((singleSignature, keyIndex) in signatureIndexPairs) {
      if (keyIndex >= publicKeys.size) {
        return false
      }
      val publicKey = this.publicKeys[keyIndex]

      if (!publicKey.verifySignature(message, singleSignature)) {
        return false
      }
    }

    return true
  }

  override fun toByteArray(): ByteArray = toBcs()

  override fun toBcs(): ByteArray {
    val encodedPublicKeys =
      publicKeys.fold(ByteArray(0)) { acc, publicKey -> acc + publicKey.toBcs() }

    return encodeUleb128(publicKeys.size) +
      encodedPublicKeys +
      byteArrayOf(signaturesRequired.toByte())
  }

  companion object {
    fun isInstance(publicKey: PublicKey): Boolean = publicKey is MultiKey
  }
}
