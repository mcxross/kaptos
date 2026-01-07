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

import io.ktor.util.reflect.instanceOf
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AnyRawTransaction
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticatorMultiKey

class MultiKeyAccount(
  val multiKey: MultiKey,
  signers: List<Account> = emptyList(),
  address: AccountAddressInput? = null,
) : Account() {
  override val publicKey: PublicKey
    get() = multiKey

  override val accountAddress: AccountAddress
    get() = multiKey.authKey().deriveAddress()

  override val signingScheme: SigningScheme
    get() = SigningScheme.MultiKey

  var signers: List<Account>

  var signerIndicies: List<Int>

  var signaturesBitmap: ByteArray = byteArrayOf()

  init {
    val bitPositions: MutableList<Int> = mutableListOf()

    signers.forEach { bitPositions.plus((this.publicKey as MultiKey).index(it.publicKey)) }

    val signerAndBitKeys: List<Pair<Account, Int>> =
      signers.mapIndexed { index, acc -> acc to index }.sortedBy { it.second }

    this.signers = signerAndBitKeys.map { it.first }
    this.signerIndicies = signerAndBitKeys.map { it.second }

    this.signaturesBitmap = (this.publicKey as MultiKey).createBitmap(bitPositions)
  }

  override fun signTransaction(tx: AnyRawTransaction): MultiKeySignature {
    val sigs: List<AnySignature> = emptyList()
    signers.forEach { sigs.plus(it.signTransaction(tx)) }
    return MultiKeySignature(sigs, signaturesBitmap)
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean =
    publicKey.verifySignature(message, signature)

  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator {
    return AccountAuthenticatorMultiKey(pubKeys = emptyList(), sigs = emptyList())
  }

  override fun sign(message: HexInput): MultiKeySignature {
    val sigs: List<AnySignature> = emptyList()
    signers.forEach { sigs.plus(it.sign(message)) }

    return MultiKeySignature(sigs, this.signaturesBitmap)
  }

  companion object {
    fun isMultiKeySigner(account: xyz.mcxross.kaptos.protocol.Account): Boolean {
      return account.instanceOf(MultiKeyAccount::class)
    }
  }
}
