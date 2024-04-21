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

package xyz.mcxross.kaptos.internal

import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.model.*

internal suspend fun getGasPriceEstimation(config: AptosConfig): Option<GasEstimation> =
  getAptosFullNode<GasEstimation>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "getGasPriceEstimation",
        path = "estimate_gas_price",
      )
    )
    .second

internal suspend fun getTransactionByVersion(
  config: AptosConfig,
  ledgerVersion: Long,
): Option<TransactionResponse> =
  getAptosFullNode<TransactionResponse>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "getTransactionByVersion",
        path = "transactions/by_version/${ledgerVersion}",
      )
    )
    .second

internal suspend fun getTransactionByHash(
  config: AptosConfig,
  ledgerHash: String,
): Option<TransactionResponse> =
  getAptosFullNode<TransactionResponse>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "getTransactionByHash",
        path = "transactions/by_hash/${ledgerHash}",
      )
    )
    .second
