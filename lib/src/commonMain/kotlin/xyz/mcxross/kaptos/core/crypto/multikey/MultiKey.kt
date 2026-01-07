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

import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme

class MultiKey(val pks: List<PublicKey>, val signaturesRequired: Int) : AbstractMultiKey(pks) {

  init {
    if (signaturesRequired < 1) {
      throw IllegalArgumentException("The number of required signatures needs to be greater than 0")
    }

    if (publicKeys.size < signaturesRequired) {
      throw IllegalArgumentException(
        "Provided ${publicKeys.size} public keys is smaller than the $signaturesRequired required signatures"
      )
    }
  }

  override fun getSigsRequired(): Int {
    TODO("Not yet implemented")
  }

  override fun authKey(): AuthenticationKey {
    return AuthenticationKey.fromSchemeAndBytes(
      AuthenticationKeyScheme.Signing(SigningScheme.MultiKey),
      HexInput.fromByteArray(
        Bcs.encodeToByteArray(publicKeys.map { it.toByteArray() }) +
          Bcs.encodeToByteArray(signaturesRequired.toUByte())
      ),
    )
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    val signerIndices = (signature as MultiKeySignature).bitMapToSignerIndices()

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

  override fun toByteArray(): ByteArray {
    return Bcs.encodeToByteArray(publicKeys.map { it.toByteArray() }) +
      Bcs.encodeToByteArray(signaturesRequired.toUByte())
  }

  override fun toBcs(): ByteArray {
    TODO("Not yet implemented")
  }

  /*fun index(pubKey: PublicKey): Int {
    return super.index(pubKey)
  }*/
}
