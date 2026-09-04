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

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetChainTopUserTransactionsQuery
import xyz.mcxross.kaptos.generated.GetProcessorStatusQuery
import xyz.mcxross.kaptos.model.*

/** An interface for reading general information from the Aptos blockchain. */
internal interface General {
  val config: TransportConfig

  /**
   * Retrieves the latest ledger information from a fullnode.
   *
   * This includes details such as chain ID, epoch, and the current ledger version.
   *
   * ## Usage
   *
   *
   * @return A `Result` which is either `Result.Ok` containing the [LedgerInfo], or `Result.Err`
   *   containing an [AptosSdkError].
   */
  suspend fun getLedgerInfo(): Result<LedgerInfo, AptosSdkError>

  /**
   * Retrieves the chain ID of the connected network.
   *
   * ## Usage
   *
   *
   * @return A `Result` which is either `Result.Ok` containing the chain ID as a `Long`, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getChainId(): Result<Long, AptosSdkError>

  /**
   * Retrieves block information by a specific ledger version.
   *
   * ## Usage
   *
   *
   * @param ledgerVersion The ledger version to look up block information for.
   * @param withTransactions If set to true, includes all transactions in the block.
   * @return A `Result` which is either `Result.Ok` containing the [Block] information, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getBlockByVersion(
    ledgerVersion: Long,
    withTransactions: Boolean? = null,
  ): Result<Block, AptosSdkError>

  /**
   * Retrieves block information by a specific block height.
   *
   * ## Usage
   *
   *
   * @param ledgerHeight The block height to look up, starting at 0.
   * @param withTransactions If set to true, includes all transactions in the block.
   * @return A `Result` which is either `Result.Ok` containing the [Block] information, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getBlockByHeight(
    ledgerHeight: Long,
    withTransactions: Boolean? = null,
  ): Result<Block, AptosSdkError>

  /**
   * Queries the indexer for the top user transactions by gas unit.
   *
   * ## Usage
   *
   *
   * @param limit The number of transactions to return.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getChainTopUserTransactions(
    limit: Int
  ): Result<GetChainTopUserTransactionsQuery.Data?, AptosIndexerError>

  /**
   * Queries the indexer for the last ledger version it has successfully processed.
   *
   * This is useful for checking if the indexer is up-to-date with the fullnodes.
   *
   * ## Usage
   *
   *
   * @return A `Result` which is either `Result.Ok` containing the last indexed version as a `Long`,
   *   or `Result.Err` containing an [AptosIndexerError].
   */
  suspend fun getIndexerLastSuccessVersion(): Result<Long, AptosIndexerError>

  /**
   * Queries the status of a specific indexer processor.
   *
   * ## Usage
   *
   *
   * @param processorType The processor type to query.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getProcessorStatus(
    processorType: ProcessorType
  ): Result<GetProcessorStatusQuery.Data?, AptosIndexerError>
}

/**
 * Queries a Move view function on the Aptos blockchain.
 *
 * View functions are read-only and do not require gas or a transaction signature.
 *
 * ## Usage
 *
 *
 * @param payload The description of the view function to call.
 * @param bcs If true, uses BCS for the request payload. Defaults to true.
 * @param ledgerVersion An optional ledger version to query.
 * @return A `Result` which is either `Result.Ok` containing an array of [MoveValue]s, or
 *   `Result.Err` containing an [AptosSdkError].
 */
internal suspend inline fun <reified T : List<MoveValue>> General.view(
  payload: InputViewFunctionData,
  bcs: Boolean = true,
  ledgerVersion: LedgerVersionArg? = null,
): Result<T, AptosSdkError> {
  return xyz.mcxross.kaptos.internal.view(config, payload, bcs, ledgerVersion)
}
