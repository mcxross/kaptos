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
package xyz.mcxross.kaptos.internal.operations

import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.*
import xyz.mcxross.kaptos.internal.operations.submission.Build
import xyz.mcxross.kaptos.internal.operations.submission.Simulate
import xyz.mcxross.kaptos.internal.operations.submission.Submit
import xyz.mcxross.kaptos.internal.signAndSubmitAsFeePayer
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

internal class TransactionOperations(val config: TransportConfig) {

  val buildTransaction: Build = Build(config)
  val submitTransaction: Submit = Submit(config)
  val simulateTransaction: Simulate = Simulate(config)

  suspend fun getTransactions(
    options: PaginationArgs? = null
  ): Result<List<TransactionResponse>, AptosSdkError> = getTransactions(config, options)

  suspend fun getTransactionByVersion(
    ledgerVersion: Long
  ): Result<TransactionResponse, AptosSdkError> = getTransactionByVersion(config, ledgerVersion)

  suspend fun getTransactionByHash(
    transactionHash: String
  ): Result<TransactionResponse, AptosSdkError> = getTransactionByHash(config, transactionHash)

  suspend fun isPendingTransaction(transactionHash: HexInput): Boolean =
    isTransactionPending(config, transactionHash)

  suspend fun waitForTransaction(
    transactionHash: HexInput,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): Result<TransactionResponse, Exception> =
    waitForTransaction(config, transactionHash.value, options)

  suspend fun getGasPriceEstimation(): Result<GasEstimation, AptosSdkError> =
    getGasPriceEstimation(config)

  fun sign(signer: Account, transaction: UnsignedTransaction): AccountAuthenticator =
    signTransaction(signer, transaction)

  fun signAsFeePayer(signer: Account, transaction: UnsignedTransaction): FeePayerSignature =
    xyz.mcxross.kaptos.internal.signAsFeePayer(signer, transaction)

  suspend fun signAndSubmitTransaction(
    signer: Account,
    transaction: UnsignedTransaction,
  ): Result<PendingTransactionResponse, Exception> =
    signAndSubmitTransaction(config, signer, transaction)

  suspend fun signAndSubmitAsFeePayer(
    feePayer: Account,
    senderAuthenticator: AccountAuthenticator,
    transaction: UnsignedTransaction,
  ): Result<PendingTransactionResponse, Exception> =
    signAndSubmitAsFeePayer(
      aptosConfig = config,
      feePayer = feePayer,
      senderAuthenticator = senderAuthenticator,
      transaction = transaction,
    )

  suspend fun publishPackageTransaction(
    account: AccountAddressInput,
    metadataBytes: HexInput,
    moduleBytecode: List<HexInput>,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    publicPackageTransaction(config, account, metadataBytes, moduleBytecode, options)

  suspend fun buildSimpleTransaction(
    sender: AccountAddressInput,
    options: TransactionOptions? = null,
    withFeePayer: Boolean = false,
    builder: InputEntryFunctionDataBuilder.() -> Unit,
  ): UnsignedTransaction.Simple =
    buildTransaction.simple(
      sender = sender,
      data = entryFunctionData(builder),
      options = options,
      withFeePayer = withFeePayer,
    )

  suspend fun execute(
    signer: Account,
    options: TransactionOptions? = null,
    withFeePayer: Boolean = false,
    builder: InputEntryFunctionDataBuilder.() -> Unit,
  ): Result<PendingTransactionResponse, Exception> {
    val txn = buildSimpleTransaction(signer.accountAddress, options, withFeePayer, builder)
    return signAndSubmitTransaction(signer, txn)
  }
}
