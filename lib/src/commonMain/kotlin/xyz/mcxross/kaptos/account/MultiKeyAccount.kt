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
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticatorMultiKey

class MultiKeyAccount(
  val multiKey: MultiKey,
  val signers: List<Account>,
  address: AccountAddressInput? = null,
) : Account() {

  override val publicKey: MultiKey = multiKey

  override val accountAddress: AccountAddress =
    address?.let { AccountAddress.from(it) } ?: publicKey.authKey().deriveAddress()

  override val signingScheme: SigningScheme = SigningScheme.MultiKey

  private val sortedSigners: List<Account>
  private val signaturesBitmap: ByteArray

  init {
    if (multiKey.signaturesRequired > signers.size) {
      throw IllegalArgumentException(
        "Not enough signers provided to satisfy the required signatures. Need ${multiKey.signaturesRequired} signers, but only ${signers.size} provided"
      )
    } else if (multiKey.signaturesRequired < signers.size) {
      throw IllegalArgumentException(
        "More signers provided than required. Need ${multiKey.signaturesRequired} signers, but ${signers.size} provided"
      )
    }

    // For each signer, find its corresponding position in the MultiKey's public keys array
    val bitPositions =
      signers.map { signer ->
        val anyPubKey =
          if (signer.publicKey is AnyPublicKey) signer.publicKey as AnyPublicKey
          else AnyPublicKey(signer.publicKey)
        multiKey.index(anyPubKey)
      }

    // Create pairs of [signer, position] and sort them by position
    val signersAndBitPosition = signers.zip(bitPositions).sortedBy { it.second }

    // Extract the sorted signers
    this.sortedSigners = signersAndBitPosition.map { it.first }

    // Create a bitmap representing which public keys from the MultiKey are being used
    this.signaturesBitmap = multiKey.createBitmap(bitPositions)
  }

  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator {
    return AccountAuthenticatorMultiKey(multiKey = multiKey, signature = sign(message))
  }

  override fun sign(message: HexInput): MultiKeySignature {
    val signatures =
      sortedSigners.map { signer ->
        val sig = signer.sign(message)
        if (sig is AnySignature) sig else AnySignature(sig)
      }
    return MultiKeySignature(signatures, signaturesBitmap)
  }

  override fun signTransaction(tx: AnyRawTransaction): MultiKeySignature {
    val signatures =
      sortedSigners.map { signer ->
        val sig = signer.signTransaction(tx)
        if (sig is AnySignature) sig else AnySignature(sig)
      }
    return MultiKeySignature(signatures, signaturesBitmap)
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    return multiKey.verifySignature(message, signature)
  }

  companion object {
    fun fromPublicKeysAndSigners(
      publicKeys: List<PublicKey>,
      signaturesRequired: Int,
      signers: List<Account>,
      address: AccountAddressInput? = null,
    ): MultiKeyAccount {
      val multiKey = MultiKey(publicKeys, signaturesRequired)
      return MultiKeyAccount(multiKey, signers, address)
    }
  }
}
