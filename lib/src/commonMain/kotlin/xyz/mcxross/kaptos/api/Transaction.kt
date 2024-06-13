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

import xyz.mcxross.kaptos.internal.getGasPriceEstimation
import xyz.mcxross.kaptos.internal.getTransactionByHash
import xyz.mcxross.kaptos.internal.getTransactionByVersion
import xyz.mcxross.kaptos.internal.signTransaction
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
  override fun sign(
    signer: xyz.mcxross.kaptos.core.account.Account,
    transaction: AnyRawTransaction,
  ): AccountAuthenticator = signTransaction(signer, transaction)
}
