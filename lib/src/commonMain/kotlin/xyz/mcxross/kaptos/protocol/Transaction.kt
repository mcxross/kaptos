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
package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.api.txsubmission.Build
import xyz.mcxross.kaptos.api.txsubmission.Submit
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

/**
 * Transaction API namespace. This interface provides functionality to reading and writing
 * transactions.
 */
interface Transaction {

  /** Builds a transaction to be submitted to the chain */
  val buildTransaction: Build

  /** Submits a transaction to the chain */
  val submitTransaction: Submit

  /**
   * Queries on-chain transaction by version. This function will not return pending transactions.
   *
   * @param ledgerVersion - Transaction version is an unsigned 64-bit number.
   * @returns [TransactionResponse] On-chain transaction. Only on-chain transactions have versions,
   *   so this function cannot be used to query pending transactions.
   */
  suspend fun getTransactionByVersion(ledgerVersion: Long): Option<TransactionResponse>

  /**
   * Queries on-chain transaction by transaction hash. This function will return pending
   * transactions.
   *
   * @param transactionHash - Transaction hash should be hex-encoded bytes string with 0x prefix.
   * @returns [TransactionResponse] from mempool (pending) or on-chain (committed) transaction
   */
  suspend fun getTransactionByHash(transactionHash: String): Option<TransactionResponse>

  /**
   * Defines if specified transaction is currently in pending state
   *
   * To create a transaction hash:
   * 1. Create a hash message from the bytes: "Aptos::Transaction" bytes + the BCS-serialized
   *    Transaction bytes.
   * 2. Apply hash algorithm SHA3-256 to the hash message bytes.
   * 3. Hex-encode the hash bytes with 0x prefix.
   *
   * @param transactionHash A hash of transaction
   * @returns `true` if transaction is in pending state and `false` otherwise
   */
  suspend fun isPendingTransaction(transactionHash: HexInput): Boolean

  suspend fun waitForTransaction(
    transactionHash: HexInput,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): Option<TransactionResponse>

  /**
   * Gives an estimate of the gas unit price required to get a transaction on chain in a reasonable
   * amount of time. For more information {@link
   * https://api.mainnet.aptoslabs.com/v1/spec#/operations/estimate_gas_price}
   *
   * @returns [GasEstimation]
   */
  suspend fun getGasPriceEstimation(): GasEstimation

  /**
   * Sign a transaction that can later be submitted to chain
   *
   * @param signer The signer account
   * @param transaction A raw transaction to sign on
   * @returns [AccountAuthenticator]
   */
  fun sign(signer: Account, transaction: AnyRawTransaction): AccountAuthenticator

  /**
   * Sign and submit a single signer transaction to chain
   *
   * @param signer The signer account to sign the transaction
   * @param transaction An instance of a RawTransaction, plus optional secondary/fee payer addresses
   *
   * ```
   * {
   *  rawTransaction: RawTransaction,
   *  secondarySignerAddresses? : Array<AccountAddress>,
   *  feePayerAddress?: AccountAddress
   * }
   * ```
   *
   * @return PendingTransactionResponse
   */
  suspend fun signAndSubmitTransaction(
      signer: Account,
      transaction: AnyRawTransaction,
  ): Option<PendingTransactionResponse>
}
