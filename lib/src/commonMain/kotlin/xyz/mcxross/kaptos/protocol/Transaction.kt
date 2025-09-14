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

import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.api.txsubmission.Build
import xyz.mcxross.kaptos.api.txsubmission.Simulate
import xyz.mcxross.kaptos.api.txsubmission.Submit
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

/** An interface for reading and writing Aptos transactions. */
interface Transaction {

  /** Provides methods for building various transaction types. */
  val buildTransaction: Build

  /** Provides methods for submitting signed transactions. */
  val submitTransaction: Submit

  /** Provides methods for simulating transactions. */
  val simulateTransaction: Simulate

  /**
   * Queries for a list of committed on-chain transactions.
   *
   * Note: This function will not return pending transactions.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getTransactions(PaginationArgs(limit = 25))
   * when (resolution) {
   * is Result.Ok -> {
   * val transactions = resolution.value
   * println("Fetched ${transactions.size} transactions.")
   * }
   * is Result.Err -> {
   * println("Error fetching transactions: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param options Optional pagination arguments (`limit` and `offset`).
   * @return A `Result` containing a list of [TransactionResponse]s or an [AptosSdkError].
   */
  suspend fun getTransactions(
    options: PaginationArgs? = null
  ): Result<List<TransactionResponse>, AptosSdkError>

  /**
   * Retrieves a committed on-chain transaction by its version number.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getTransactionByVersion(123456789)
   * when (resolution) {
   * is Result.Ok -> {
   * val transaction = resolution.value
   * println("Transaction hash: ${transaction.hash}")
   * }
   * is Result.Err -> {
   * println("Error fetching transaction: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param ledgerVersion The version of the transaction to retrieve.
   * @return A `Result` containing the [TransactionResponse] or an [AptosSdkError].
   */
  suspend fun getTransactionByVersion(
    ledgerVersion: Long
  ): Result<TransactionResponse, AptosSdkError>

  /**
   * Retrieves a transaction by its hash.
   *
   * Note: This function can return both pending (mempool) and committed transactions.
   *
   * ## Usage
   *
   * ```kotlin
   * val hash = "0x..."
   * val resolution = aptos.getTransactionByHash(hash)
   * when (resolution) {
   * is Result.Ok -> {
   * val transaction = resolution.value
   * println("Transaction success status: ${transaction.success}")
   * }
   * is Result.Err -> {
   * println("Error fetching transaction: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param transactionHash The hex-encoded hash of the transaction.
   * @return A `Result` containing the [TransactionResponse] or an [AptosSdkError].
   */
  suspend fun getTransactionByHash(
    transactionHash: String
  ): Result<TransactionResponse, AptosSdkError>

  /**
   * Checks if a transaction is currently in a pending state.
   *
   * ## Usage
   *
   * ```kotlin
   * val hash = HexInput.fromString("0x...")
   * val isPending = aptos.isPendingTransaction(hash)
   * println("Is the transaction pending? $isPending")
   * ```
   *
   * @param transactionHash The hash of the transaction.
   * @return `true` if the transaction is pending, `false` otherwise.
   */
  suspend fun isPendingTransaction(transactionHash: HexInput): Boolean

  /**
   * Waits for a transaction to be successfully processed and included in the blockchain.
   *
   * ## Usage
   *
   * ```kotlin
   * val hash = HexInput.fromString("0x...")
   * val resolution = aptos.waitForTransaction(hash)
   * when (resolution) {
   * is Result.Ok -> {
   * val transaction = resolution.value
   * println("Transaction confirmed in version ${transaction.version}")
   * }
   * is Result.Err -> {
   * println("Error waiting for transaction: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param transactionHash The hash of the transaction to wait for.
   * @param options Optional configuration for the wait, such as timeout.
   * @return A `Result` containing the confirmed [TransactionResponse] or an [Exception].
   */
  suspend fun waitForTransaction(
    transactionHash: HexInput,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): Result<TransactionResponse, Exception>

  /**
   * Retrieves a gas price estimation from the network.
   *
   * This is useful for setting a gas unit price that is likely to be accepted in a reasonable
   * amount of time.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getGasPriceEstimation()
   * when (resolution) {
   * is Result.Ok -> {
   * val gasInfo = resolution.value
   * println("Standard gas unit price: ${gasInfo.gasEstimate}")
   * }
   * is Result.Err -> {
   * println("Error estimating gas price: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @return A `Result` containing [GasEstimation] data or an [AptosSdkError].
   */
  suspend fun getGasPriceEstimation(): Result<GasEstimation, AptosSdkError>

  /**
   * Signs a raw transaction with a signer's account.
   *
   * ## Usage
   *
   * ```kotlin
   * val alice = Account.generate()
   * val rawTxn = aptos.buildTransaction.simple(...)
   * val authenticator = aptos.sign(alice, rawTxn)
   * println("Transaction signed.")
   * ```
   *
   * @param signer The account to sign the transaction with.
   * @param transaction The raw transaction to sign.
   * @return An [AccountAuthenticator] containing the signature.
   */
  fun sign(signer: Account, transaction: AnyRawTransaction): AccountAuthenticator

  /**
   * Signs a raw transaction as the fee payer.
   *
   * @param signer The fee payer account to sign the transaction with.
   * @param transaction The raw transaction to sign.
   * @return An [AccountAuthenticator] containing the fee payer's signature.
   */
  fun signAsFeePayer(signer: Account, transaction: AnyRawTransaction): AccountAuthenticator

  /**
   * Signs and submits a single-signer transaction in one step.
   *
   * ## Usage
   *
   * ```kotlin
   * val alice = Account.generate()
   * val rawTxn = aptos.buildTransaction.simple(...)
   * val resolution = aptos.signAndSubmitTransaction(alice, rawTxn)
   * when (resolution) {
   * is Result.Ok -> {
   * val pendingTx = resolution.value
   * println("Transaction submitted with hash: ${pendingTx.hash}")
   * }
   * is Result.Err -> {
   * println("Error signing and submitting: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param signer The account to sign the transaction with.
   * @param transaction The raw transaction to sign and submit.
   * @return A `Result` containing the [PendingTransactionResponse] or an [Exception].
   */
  suspend fun signAndSubmitTransaction(
    signer: Account,
    transaction: AnyRawTransaction,
  ): Result<PendingTransactionResponse, Exception>

  /**
   * Signs a transaction as the fee payer and submits it to the network.
   *
   * @param feePayer The fee payer account to sign the transaction with.
   * @param senderAuthenticator The authenticator from the primary transaction sender.
   * @param transaction The raw transaction to sign and submit.
   * @return A `Result` containing the [PendingTransactionResponse] or an [Exception].
   */
  suspend fun signAndSubmitAsFeePayer(
    feePayer: Account,
    senderAuthenticator: AccountAuthenticator,
    transaction: AnyRawTransaction,
  ): Result<PendingTransactionResponse, Exception>

  /**
   * Builds a transaction to publish a new Move package.
   *
   * @param account The address of the publishing account.
   * @param metadataBytes The serialized package metadata.
   * @param moduleBytecode A list of serialized bytecodes for each module in the package.
   * @param options Optional configuration for the transaction.
   * @return A [SimpleTransaction] object ready to be signed and submitted.
   */
  suspend fun publishPackageTransaction(
    account: AccountAddressInput,
    metadataBytes: HexInput,
    moduleBytecode: List<HexInput>,
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction
}
