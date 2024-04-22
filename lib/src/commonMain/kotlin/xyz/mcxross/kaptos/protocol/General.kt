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

  suspend fun getChainTopUserTransactions(limit: Int): Option<ChainTopUserTransactions>
}

suspend inline fun <reified T> General.getTableItem(
  handle: String,
  data: TableItemRequest,
  param: LedgerVersionQueryParam? = null,
): T {
  return xyz.mcxross.kaptos.internal.getTableItem<T>(this.config, handle, data, param?.toMap())
}
