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

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetChainTopUserTransactionsQuery
import xyz.mcxross.kaptos.generated.GetProcessorStatusQuery
import xyz.mcxross.kaptos.internal.getBlockByHeight
import xyz.mcxross.kaptos.internal.getBlockByVersion
import xyz.mcxross.kaptos.internal.getChainTopUserTransactions
import xyz.mcxross.kaptos.internal.getIndexerLastSuccessVersion
import xyz.mcxross.kaptos.internal.getLedgerInfo
import xyz.mcxross.kaptos.internal.getProcessorStatus
import xyz.mcxross.kaptos.internal.toInternalResult
import xyz.mcxross.kaptos.internal.toResult
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.General

/**
 * A class to handle reading and writing general ledger information.
 *
 * @property config AptosConfig object for configuration.
 */
class General(override val config: AptosConfig) : General {

  /**
   * Retrieves the latest ledger information from a fullnode.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getLedgerInfo()
   * when (resolution) {
   * is Result.Ok -> {
   * val ledgerInfo = resolution.value
   * println("Current ledger version: ${ledgerInfo.ledgerVersion}")
   * }
   * is Result.Err -> {
   * println("Error retrieving ledger info: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @return A `Result` which is either `Result.Ok` containing the [LedgerInfo], or `Result.Err`
   *   containing an [AptosSdkError].
   */
  override suspend fun getLedgerInfo(): Result<LedgerInfo, AptosSdkError> = getLedgerInfo(config)

  /**
   * Retrieves the chain ID of the network.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getChainId()
   * when (resolution) {
   * is Result.Ok -> {
   * val chainId = resolution.value
   * println("Network Chain ID: $chainId")
   * }
   * is Result.Err -> {
   * println("Error retrieving chain ID: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @return A `Result` which is either `Result.Ok` containing the chain ID as a `Long`, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  override suspend fun getChainId(): Result<Long, AptosSdkError> {
    return getLedgerInfo(config).toInternalResult().map { it.chainId }.mapError { it }.toResult()
  }

  /**
   * Retrieves a block by its transaction version number.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getBlockByVersion(123456)
   * when (resolution) {
   * is Result.Ok -> {
   * val block = resolution.value
   * println("Block found at version: ${block.blockHeight}")
   * }
   * is Result.Err -> {
   * println("Error retrieving block: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param ledgerVersion Ledger version to look up block information for.
   * @param withTransactions Whether to include the full transaction data in the response.
   * @return A `Result` which is either `Result.Ok` containing the [Block] information, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  override suspend fun getBlockByVersion(
    ledgerVersion: Long,
    withTransactions: Boolean?,
  ): Result<Block, AptosSdkError> = getBlockByVersion(config, ledgerVersion, withTransactions)

  /**
   * Retrieves a block by its height.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getBlockByHeight(100)
   * when (resolution) {
   * is Result.Ok -> {
   * val block = resolution.value
   * println("Block version is: ${block.ledgerVersion}")
   * }
   * is Result.Err -> {
   * println("Error retrieving block: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param ledgerHeight Ledger height to look up block information for.
   * @param withTransactions Whether to include the full transaction data in the response.
   * @return A `Result` which is either `Result.Ok` containing the [Block] information, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  override suspend fun getBlockByHeight(
    ledgerHeight: Long,
    withTransactions: Boolean?,
  ): Result<Block, AptosSdkError> = getBlockByHeight(config, ledgerHeight, withTransactions)

  /**
   * Queries the indexer for the top user transactions by gas unit.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getChainTopUserTransactions(limit = 5)
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Retrieved top user transactions data: $data")
   * }
   * is Result.Err -> {
   * println("Error querying transactions: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param limit The number of transactions to return.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  override suspend fun getChainTopUserTransactions(
    limit: Int
  ): Result<GetChainTopUserTransactionsQuery.Data?, AptosIndexerError> =
    getChainTopUserTransactions(config, limit)

  /**
   * Queries the indexer for the last ledger version it has successfully processed.
   *
   * This is useful for checking if the indexer is up-to-date with the fullnodes.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getIndexerLastSuccessVersion()
   * when (resolution) {
   * is Result.Ok -> {
   * val version = resolution.value
   * println("Indexer is synced to version: $version")
   * }
   * is Result.Err -> {
   * println("Error querying indexer status: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @return A `Result` which is either `Result.Ok` containing the last indexed version as a `Long`,
   *   or `Result.Err` containing an [AptosIndexerError].
   */
  override suspend fun getIndexerLastSuccessVersion(): Result<Long, AptosIndexerError> =
    getIndexerLastSuccessVersion(config)

  /**
   * Queries the status of a specific indexer processor.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getProcessorStatus(ProcessorType.ACCOUNT_TRANSACTION_PROCESSOR)
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Processor status data: $data")
   * }
   * is Result.Err -> {
   * println("Error querying processor status: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param processorType The processor type to query.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  override suspend fun getProcessorStatus(
    processorType: ProcessorType
  ): Result<GetProcessorStatusQuery.Data?, AptosIndexerError> =
    getProcessorStatus(config, processorType)
}
