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

import xyz.mcxross.kaptos.core.crypto.*
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/**
 * Signer implementation for the SingleKey authentication scheme. This extends a SingleKeyAccount by
 * adding signing capabilities through a valid private key. Currently, the only supported signature
 * schemes are Ed25519 and Secp256k1. P-256 transaction signing uses [PasskeyAccount], because
 * WebAuthn credential acquisition is application-owned.
 *
 * Note: Generating a signer instance does not create the account on-chain.
 */
class SingleKeyAccount(val privateKey: PrivateKey, val address: AccountAddressInput? = null) :
  Account() {

  init {
    require(privateKey is Ed25519PrivateKey || privateKey is Secp256k1PrivateKey) {
      "SingleKeyAccount supports Ed25519 and Secp256k1 private keys; use PasskeyAccount for Secp256r1/WebAuthn"
    }
  }

  override val publicKey: AnyPublicKey = AnyPublicKey(privateKey.publicKey())

  override val accountAddress: AccountAddress
    get() =
      if (address != null) {
        AccountAddress.from(address)
      } else {
        publicKey.authKey().deriveAddress()
      }

  override val signingScheme: SigningScheme
    get() = SigningScheme.SingleKey

  override val isPrivateKeyCleared: Boolean
    get() = privateKey.isCleared

  override fun clearPrivateKey() = privateKey.clear()

  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator =
    AccountAuthenticator.SingleKey(publicKey = publicKey, signature = sign(message))

  override fun sign(message: HexInput): AnySignature = AnySignature(privateKey.sign(message))

  override fun signTransactionSignature(tx: UnsignedTransaction): Signature =
    sign(HexInput.fromByteArray(tx.signingMessage()))

  override fun verifySignature(message: HexInput, signature: Signature): Boolean =
    publicKey.verifySignature(message, signature)

  companion object {

    /**
     * Derives an account from a randomly generated private key. Default generation is using an
     * Ed25519 key
     *
     * @returns Account with the given signature scheme
     */
    fun generate(scheme: SigningSchemeInput = SigningSchemeInput.Ed25519): SingleKeyAccount {
      val privateKey: PrivateKey =
        when (scheme) {
          SigningSchemeInput.Ed25519 -> {
            Ed25519PrivateKey.generate()
          }
          SigningSchemeInput.Secp256k1 -> {
            Secp256k1PrivateKey.generate()
          }
          SigningSchemeInput.Secp256r1 -> {
            throw IllegalArgumentException("Use PasskeyAccount for Secp256r1 transaction signing")
          }
        }
      return SingleKeyAccount(privateKey)
    }

    /** Derive an Ed25519 SingleKey account from an Aptos hardened BIP-44 path. */
    fun fromMnemonic(
      mnemonic: MnemonicPhrase,
      path: AptosDerivationPath.Ed25519 = AptosDerivationPath.Ed25519(),
      passphrase: String = "",
    ): SingleKeyAccount = SingleKeyAccount(mnemonic.derivePrivateKey(path, passphrase))

    /** Derive a Secp256k1 SingleKey account from an Aptos BIP-44 path. */
    fun fromSecp256k1Mnemonic(
      mnemonic: MnemonicPhrase,
      path: AptosDerivationPath.Ecdsa = AptosDerivationPath.Ecdsa(),
      passphrase: String = "",
    ): SingleKeyAccount = SingleKeyAccount(mnemonic.deriveSecp256k1PrivateKey(path, passphrase))
  }
}
