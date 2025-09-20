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

package xyz.mcxross.kaptos.api

import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.api.txsubmission.Build
import xyz.mcxross.kaptos.api.txsubmission.Simulate
import xyz.mcxross.kaptos.api.txsubmission.Submit
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.*
import xyz.mcxross.kaptos.internal.signAndSubmitAsFeePayer
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.Transaction
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

/**
 * A class for reading and writing Aptos transactions.
 *
 * @property config AptosConfig object for configuration.
 */
class Transaction(val config: AptosConfig) : Transaction {

  override val buildTransaction: Build = Build(config)
  override val submitTransaction: Submit = Submit(config)
  override val simulateTransaction: Simulate = Simulate(config)

  override suspend fun getTransactions(
    options: PaginationArgs?
  ): Result<List<TransactionResponse>, AptosSdkError> = getTransactions(config, options)

  override suspend fun getTransactionByVersion(
    ledgerVersion: Long
  ): Result<TransactionResponse, AptosSdkError> = getTransactionByVersion(config, ledgerVersion)

  override suspend fun getTransactionByHash(
    transactionHash: String
  ): Result<TransactionResponse, AptosSdkError> = getTransactionByHash(config, transactionHash)

  override suspend fun isPendingTransaction(transactionHash: HexInput): Boolean =
    isTransactionPending(config, transactionHash)

  override suspend fun waitForTransaction(
    transactionHash: HexInput,
    options: WaitForTransactionOptions,
  ): Result<TransactionResponse, Exception> =
    waitForTransaction(config, transactionHash.value, options)

  override suspend fun getGasPriceEstimation(): Result<GasEstimation, AptosSdkError> =
    getGasPriceEstimation(config)

  override fun sign(signer: Account, transaction: AnyRawTransaction): AccountAuthenticator =
    signTransaction(signer, transaction)

  override fun signAsFeePayer(
    signer: Account,
    transaction: AnyRawTransaction,
  ): AccountAuthenticator = xyz.mcxross.kaptos.internal.signAsFeePayer(signer, transaction)

  override suspend fun signAndSubmitTransaction(
    signer: Account,
    transaction: AnyRawTransaction,
  ): Result<PendingTransactionResponse, Exception> =
    signAndSubmitTransaction(config, signer, transaction)

  override suspend fun signAndSubmitAsFeePayer(
    feePayer: Account,
    senderAuthenticator: AccountAuthenticator,
    transaction: AnyRawTransaction,
  ): Result<PendingTransactionResponse, Exception> =
    signAndSubmitAsFeePayer(
      aptosConfig = config,
      feePayer = feePayer,
      senderAuthenticator = senderAuthenticator,
      transaction = transaction,
    )

  override suspend fun publishPackageTransaction(
    account: AccountAddressInput,
    metadataBytes: HexInput,
    moduleBytecode: List<HexInput>,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    publicPackageTransaction(config, account, metadataBytes, moduleBytecode, options)
}
