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
interface General {
  val config: AptosConfig

  /**
   * Retrieves the latest ledger information from a fullnode.
   *
   * This includes details such as chain ID, epoch, and the current ledger version.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
   * val resolution = aptos.getLedgerInfo()
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val ledgerInfo = resolution.value
   * println("Current chain ID: ${ledgerInfo.chainId}")
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
  suspend fun getLedgerInfo(): Result<LedgerInfo, AptosSdkError>

  /**
   * Retrieves the chain ID of the connected network.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
   * val resolution = aptos.getChainId()
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val chainId = resolution.value
   * // Expected for localnet: 4
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
  suspend fun getChainId(): Result<Long, AptosSdkError>

  /**
   * Retrieves block information by a specific ledger version.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
   * val resolution = aptos.getBlockByVersion(2L)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val block = resolution.value
   * println("Block height is: ${block.blockHeight}")
   * }
   * is Result.Err -> {
   * println("Error retrieving block: ${resolution.error.message}")
   * }
   * }
   * ```
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
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
   * val resolution = aptos.getBlockByHeight(1L)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val block = resolution.value
   * println("Ledger version for block is: ${block.lastVersion}")
   * }
   * is Result.Err -> {
   * println("Error retrieving block: ${resolution.error.message}")
   * }
   * }
   * ```
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
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))
   * val resolution = aptos.getChainTopUserTransactions(limit = 5)
   *
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
  suspend fun getIndexerLastSuccessVersion(): Result<Long, AptosIndexerError>

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
 * ```kotlin
 * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
 * val payload = InputViewFunctionData(
 * function = "0x1::chain_id::get",
 * typeArguments = emptyList(),
 * functionArguments = emptyList(),
 * )
 * val resolution = aptos.view<List<MoveValue.MoveUint64Type>>(payload)
 *
 * when (resolution) {
 * is Result.Ok -> {
 * val data = resolution.value
 * println("View function returned: $data")
 * }
 * is Result.Err -> {
 * println("Error calling view function: ${resolution.error.message}")
 * }
 * }
 * ```
 *
 * @param payload The description of the view function to call.
 * @param bcs If true, uses BCS for the request payload. Defaults to true.
 * @param ledgerVersion An optional ledger version to query.
 * @return A `Result` which is either `Result.Ok` containing an array of [MoveValue]s, or
 *   `Result.Err` containing an [AptosSdkError].
 */
suspend inline fun <reified T : List<MoveValue>> General.view(
  payload: InputViewFunctionData,
  bcs: Boolean = true,
  ledgerVersion: LedgerVersionArg? = null,
): Result<T, AptosSdkError> {
  return xyz.mcxross.kaptos.internal.view(config, payload, bcs, ledgerVersion)
}
