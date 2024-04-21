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

package xyz.mcxross.kaptos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LedgerInfo(
  @SerialName("chain_id") val chainId: Long,
  val epoch: String,
  @SerialName("ledger_version") val ledgerVersion: String,
  @SerialName("oldest_ledger_version") val oldestLedgerVersion: String,
  @SerialName("ledger_timestamp") val ledgerTimestamp: String,
  @SerialName("node_role") val nodeRole: String,
  @SerialName("oldest_block_height") val oldestBlockHeight: String,
  @SerialName("block_height") val blockHeight: String,
  @SerialName("git_hash") val gitHash: String,
)
