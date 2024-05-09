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

import xyz.mcxross.kaptos.internal.getBlockByHeight
import xyz.mcxross.kaptos.internal.getBlockByVersion
import xyz.mcxross.kaptos.internal.getLedgerInfo
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.General

/**
 * General API namespace. This interface provides functionality to reading and writing general
 * ledger information.
 *
 * @property config AptosConfig object for configuration
 */
class General(override val config: AptosConfig) : General {

  /**
   * Queries for the Aptos ledger info
   *
   * @returns [LedgerInfo]
   */
  override suspend fun getLedgerInfo(): Option<LedgerInfo> = getLedgerInfo(config)

  /**
   * Queries for the chain id
   *
   * @returns [Long] chain id
   */
  override suspend fun getChainId(): Option<Long> {
    val ledgerInfo = getLedgerInfo(config)
    return if (ledgerInfo is Option.Some) {
      Option.Some(ledgerInfo.value.chainId)
    } else {
      Option.None
    }
  }

  /**
   * Queries for block by transaction version
   *
   * @param ledgerVersion Ledger version to lookup block information for
   * @returns [Block] information
   */
  override suspend fun getBlockByVersion(ledgerVersion: Long): Option<Block> =
    getBlockByVersion(config, ledgerVersion)

  /**
   * Queries for block by height
   *
   * @param ledgerHeight Ledger height to lookup block information for
   * @returns [Block] information
   */
  override suspend fun getBlockByHeight(ledgerHeight: Long): Option<Block> =
    getBlockByHeight(config, ledgerHeight)

  /**
   * Queries top user transactions
   *
   * @param limit The number of transactions to return
   * @returns [ChainTopUserTransactions]
   */
  override suspend fun getChainTopUserTransactions(limit: Int): Option<ChainTopUserTransactions> =
    xyz.mcxross.kaptos.internal.getChainTopUserTransactions(config, limit)
}
