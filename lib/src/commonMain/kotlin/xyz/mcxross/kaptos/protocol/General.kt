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

import xyz.mcxross.kaptos.model.*

/**
 * General API namespace. This interface provides functionality to reading and writing general
 * information.
 */
interface General {
  val config: AptosConfig

  /**
   * Queries for the Aptos ledger info
   *
   * @returns [LedgerInfo]
   */
  suspend fun getLedgerInfo(): Option<LedgerInfo>

  /**
   * Queries for the chain id
   *
   * @returns [Long] chain id
   */
  suspend fun getChainId(): Option<Long>

  /**
   * Queries for block by transaction version
   *
   * @param ledgerVersion Ledger version to lookup block information for
   * @returns [Block] information
   */
  suspend fun getBlockByVersion(ledgerVersion: Long): Option<Block>

  /**
   * Queries for block by height
   *
   * @param ledgerHeight Ledger height to lookup block information for
   * @returns [Block] information
   */
  suspend fun getBlockByHeight(ledgerHeight: Long): Option<Block>

  /**
   * Queries top user transactions
   *
   * @param limit The number of transactions to return
   * @returns [ChainTopUserTransactions]
   */
  suspend fun getChainTopUserTransactions(limit: Int): Option<ChainTopUserTransactions>

  /**
   * Queries for the last successful indexer version
   *
   * This is useful to tell what ledger version the indexer is updated to, as it can be behind the
   * full nodes.
   */
  suspend fun getIndexerLastSuccessVersion(): Option<Long>

  /**
   * Query the processor status for a specific processor type.
   *
   * @param processorType The processor type to query
   * @returns an Option of ProcessorStatus if found or None if not found
   */
  suspend fun getProcessorStatus(processorType: ProcessorType): Option<ProcessorStatus>
}

suspend inline fun <reified T> General.getTableItem(
  handle: String,
  data: TableItemRequest,
  param: LedgerVersionQueryParam? = null,
): T {
  return xyz.mcxross.kaptos.internal.getTableItem<T>(this.config, handle, data, param?.toMap())
}

/**
 * A generic function for retrieving data from Aptos Indexer.
 *
 * @param query The GraphQL query to execute
 * @returns an Option of the response type provided
 */
suspend inline fun <reified T> General.queryIndexer(query: GraphqlQuery): Option<T> =
  xyz.mcxross.kaptos.internal.queryIndexer(this.config, query)

/**
 * Queries for a Move view function
 *
 * @param payload Payload for the view function
 * @param ledgerVersion The ledger version to query, if not provided it will get the latest version
 * @returns an array of Move values
 * @example ` const payload: ViewRequest = { function: "0x1::coin::balance", typeArguments:
 *   ["0x1::aptos_coin::AptosCoin"], functionArguments: [accountAddress], }; `
 */
suspend inline fun <reified T : List<MoveValue>> General.view(
  payload: InputViewFunctionData,
  bcs: Boolean = false,
  ledgerVersion: LedgerVersionArg? = null,
): Option<T> {
  return xyz.mcxross.kaptos.internal.view(config, payload, bcs, ledgerVersion)
}
