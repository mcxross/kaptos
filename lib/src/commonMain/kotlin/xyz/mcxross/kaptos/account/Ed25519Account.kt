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
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.AptosDerivationPath
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.MnemonicPhrase
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/**
 * Signer implementation for the Ed25519 authentication scheme. This extends an [Ed25519Account] by
 * adding signing capabilities through an [Ed25519PrivateKey].
 *
 * Note: Generating a signer instance does not create the account on-chain.
 */
class Ed25519Account(val privateKey: Ed25519PrivateKey, val address: AccountAddressInput? = null) :
  Account() {

  /** Public key associated with the account */
  override val publicKey: Ed25519PublicKey = privateKey.publicKey()

  /** Account address associated with the account */
  override val accountAddress: AccountAddress =
    address?.let(AccountAddress::from) ?: publicKey.authKey().deriveAddress()

  /** Signing scheme used to sign transactions */
  override val signingScheme: SigningScheme
    get() = SigningScheme.Ed25519

  override val isPrivateKeyCleared: Boolean
    get() = privateKey.isCleared

  override fun clearPrivateKey() = privateKey.clear()

  /**
   * Sign a message using the available signing capabilities.
   *
   * @param message the signing message, as binary input
   * @return the [AccountAuthenticator] containing the signature, together with the account's public
   *   key
   */
  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator {
    val signature = this.privateKey.sign(message)
    return AccountAuthenticator.Ed25519(this.publicKey, signature)
  }

  override fun sign(message: HexInput): Signature {
    return privateKey.sign(message)
  }

  override fun signTransactionSignature(tx: UnsignedTransaction): Signature {
    return sign(HexInput.fromByteArray(tx.signingMessage()))
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean =
    publicKey.verifySignature(message, signature)

  companion object {
    fun generate(): Ed25519Account {
      val privateKey = Ed25519PrivateKey.generate()
      return Ed25519Account(privateKey)
    }

    /** Derive a legacy Ed25519 account from an Aptos BIP-44 path. */
    fun fromMnemonic(
      mnemonic: MnemonicPhrase,
      path: AptosDerivationPath.Ed25519 = AptosDerivationPath.Ed25519(),
      passphrase: String = "",
    ): Ed25519Account = Ed25519Account(mnemonic.derivePrivateKey(path, passphrase))
  }
}
