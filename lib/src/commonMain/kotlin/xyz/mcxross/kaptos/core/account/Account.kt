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
package xyz.mcxross.kaptos.core.account

import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.PrivateKey
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

abstract class Account {

  /** Public key associated with the account */
  abstract val publicKey: PublicKey

  /** Account address associated with the account */
  abstract val accountAddress: AccountAddress

  /** Signing scheme used to sign transactions */
  abstract val signingScheme: SigningScheme

  /**
   * Sign a message using the available signing capabilities.
   *
   * @param message the signing message, as binary input
   * @return the [AccountAuthenticator] containing the signature, together with the account's public
   *   key
   */
  abstract fun signWithAuthenticator(message: HexInput): AccountAuthenticator

  /**
   * Sign the given message with the private key.
   *
   * @param message in HexInput format
   * @returns AccountSignature
   */
  abstract fun sign(message: HexInput): Signature

  companion object {
    fun generate(
      scheme: SigningSchemeInput = SigningSchemeInput.Ed25519,
      legacy: Boolean = true,
    ): Account {
      if (scheme === SigningSchemeInput.Ed25519 && legacy) {
        return Ed25519Account.generate()
      }

      throw NotImplementedError("Only Ed25519 is supported at the moment")
    }

    infix fun from(privateKey: PrivateKey): Account {
      return fromPrivateKey(privateKey, null, true)
    }

    infix fun from(privateKeyInput: PrivateKeyInput): Account {
      return fromPrivateKey(
        privateKeyInput.privateKey,
        privateKeyInput.address,
        privateKeyInput.legacy,
      )
    }

    fun fromPrivateKey(
      privateKey: PrivateKey,
      address: AccountAddressInput? = null,
      legacy: Boolean = true,
    ): Account {
      if (privateKey is Ed25519PrivateKey && legacy) {
        return Ed25519Account(privateKey, address)
      }
      throw NotImplementedError("Only Ed25519 is supported at the moment")
    }
  }
}
