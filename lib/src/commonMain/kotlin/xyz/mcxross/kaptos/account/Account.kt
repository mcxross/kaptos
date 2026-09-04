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

import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.core.crypto.PrivateKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

abstract class Account : TransactionSigner, AutoCloseable {

  /** Public key associated with the account */
  abstract override val publicKey: AccountPublicKey

  /** Account address associated with the account */
  abstract override val accountAddress: AccountAddress

  /** Signing scheme used to sign transactions */
  abstract val signingScheme: SigningScheme

  /** Whether all private key material owned by this account has been cleared. */
  abstract val isPrivateKeyCleared: Boolean

  /** Explicitly clear private key material owned by this account. */
  abstract fun clearPrivateKey()

  /** Clears private key material when the account is owned by a managed SDK scope. */
  final override fun close() = clearPrivateKey()

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

  abstract fun signTransactionSignature(tx: UnsignedTransaction): Signature

  abstract fun verifySignature(message: HexInput, signature: Signature): Boolean

  final override suspend fun signBytes(message: ByteArray): AptosResult<Signature> =
    try {
      AptosResult.Success(sign(HexInput.fromByteArray(message)))
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Crypto("Unable to sign bytes", error))
    }

  final override suspend fun signText(message: String): AptosResult<Signature> =
    signBytes(message.encodeToByteArray())

  override suspend fun signTransaction(
    transaction: UnsignedTransaction,
  ): AptosResult<AccountAuthenticator> =
    try {
      AptosResult.Success(
        signWithAuthenticator(HexInput.fromByteArray(transaction.signingMessage()))
      )
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Crypto("Unable to sign transaction", error))
    }

  final override fun verifySignature(message: ByteArray, signature: Signature): Boolean =
    verifySignature(HexInput.fromByteArray(message), signature)

  override fun toString(): String {
    return "${signingScheme}Account { address: $accountAddress, publicKey: $publicKey }"
  }

  companion object {
    /** Generate the conventional legacy Ed25519 account used by most Aptos applications. */
    fun generate(): Ed25519Account = Ed25519Account.generate()

    /**
     * Create an account from a private key, inferring the correct account authentication scheme.
     * Ed25519 uses the legacy authenticator; other locally signable keys use SingleKey.
     */
    fun fromPrivateKey(
      privateKey: PrivateKey,
      address: AccountAddressInput? = null,
    ): Account {
      return if (privateKey is Ed25519PrivateKey) {
        Ed25519Account(privateKey, address)
      } else {
        SingleKeyAccount(privateKey, address)
      }
    }

    /** Explicitly wrap Ed25519 or Secp256k1 key material in a SingleKey account. */
    fun fromSingleKey(
      privateKey: PrivateKey,
      address: AccountAddressInput? = null,
    ): SingleKeyAccount = SingleKeyAccount(privateKey, address)
  }
}
