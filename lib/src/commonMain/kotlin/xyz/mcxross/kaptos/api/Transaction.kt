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
import xyz.mcxross.kaptos.internal.*
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.Transaction
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

/**
 * Transaction API namespace. This class provides functionality to reading and writing transaction
 * information.
 *
 * @property config AptosConfig object for configuration
 */
class Transaction(val config: AptosConfig) : Transaction {

  override val buildTransaction: Build = Build(config)
  override val submitTransaction: Submit = Submit(config)
  override val simulateTransaction: Simulate = Simulate(config)

  /**
   * Queries on-chain transaction by version. This function will not return pending transactions.
   *
   * @param ledgerVersion - Transaction version is an unsigned 64-bit number.
   * @returns [TransactionResponse] On-chain transaction. Only on-chain transactions have versions,
   *   so this function cannot be used to query pending transactions.
   */
  override suspend fun getTransactionByVersion(ledgerVersion: Long): Option<TransactionResponse> =
    getTransactionByVersion(config, ledgerVersion)

  /**
   * Queries on-chain transaction by transaction hash. This function will return pending
   * transactions.
   *
   * @param transactionHash - Transaction hash should be hex-encoded bytes string with 0x prefix.
   * @returns [TransactionResponse] from mempool (pending) or on-chain (committed) transaction
   */
  override suspend fun getTransactionByHash(transactionHash: String): Option<TransactionResponse> =
    getTransactionByHash(config, transactionHash)

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
  override suspend fun isPendingTransaction(transactionHash: HexInput): Boolean =
    isTransactionPending(config, transactionHash)

  override suspend fun waitForTransaction(
    transactionHash: HexInput,
    options: WaitForTransactionOptions,
  ): Option<TransactionResponse> = waitForTransaction(config, transactionHash.value, options)

  /**
   * Gives an estimate of the gas unit price required to get a transaction on chain in a reasonable
   * amount of time. For more information {@link
   * https://api.mainnet.aptoslabs.com/v1/spec#/operations/estimate_gas_price}
   *
   * @returns [GasEstimation]
   */
  override suspend fun getGasPriceEstimation(): GasEstimation {
    val option = getGasPriceEstimation(config)
    return if (option is Option.Some) {
      option.value
    } else {
      throw Exception("Failed to get gas price estimation")
    }
  }

  /**
   * Sign a transaction that can later be submitted to chain
   *
   * @param signer The signer account
   * @param transaction A raw transaction to sign on
   * @returns [AccountAuthenticator]
   */
  override fun sign(signer: Account, transaction: AnyRawTransaction): AccountAuthenticator =
    signTransaction(signer, transaction)

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
  override suspend fun signAndSubmitTransaction(
    signer: Account,
    transaction: AnyRawTransaction,
  ): Option<PendingTransactionResponse> = signAndSubmitTransaction(config, signer, transaction)

  /**
   * Generates a transaction to publish a move package to chain.
   *
   * To get the `metadataBytes` and `byteCode`, can compile using Aptos CLI with command `aptos move
   * compile --save-metadata ...`,
   *
   * @param account The publisher account
   * @param metadataBytes The package metadata bytes
   * @param moduleBytecode An array of the bytecode of each module in the package in compiler output
   *   order
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
  override suspend fun publishPackageTransaction(
    account: AccountAddressInput,
    metadataBytes: HexInput,
    moduleBytecode: List<HexInput>,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    publicPackageTransaction(config, account, metadataBytes, moduleBytecode, options)
}
