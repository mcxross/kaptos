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
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticatorSingleKey

/**
 * Signer implementation for the SingleKey authentication scheme. This extends a SingleKeyAccount by
 * adding signing capabilities through a valid private key. Currently, the only supported signature
 * schemes are Ed25519 and Secp256k1.
 *
 * Note: Generating a signer instance does not create the account on-chain.
 */
class SingleKeyAccount(val privateKey: PrivateKey, val address: AccountAddressInput? = null) :
  Account() {

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

  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator =
    AccountAuthenticatorSingleKey(publicKey = publicKey, signature = sign(message))

  override fun sign(message: HexInput): AnySignature = AnySignature(privateKey.sign(message))

  override fun signTransaction(tx: AnyRawTransaction): Signature {
    TODO("Not yet implemented")
  }

  companion object {

    /**
     * Derives an account from a randomly generated private key. Default generation is using an
     * Ed25519 key
     *
     * @returns Account with the given signature scheme
     */
    fun generate(scheme: SigningSchemeInput): SingleKeyAccount {
      val privateKey: PrivateKey =
        when (scheme) {
          SigningSchemeInput.Ed25519 -> {
            Ed25519PrivateKey.generate()
          }
          SigningSchemeInput.Secp256k1 -> {
            Secp256k1PrivateKey.generate()
          }
        }
      return SingleKeyAccount(privateKey)
    }
  }
}
