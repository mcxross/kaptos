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
package xyz.mcxross.kaptos.internal.operations

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

internal class GeneralOperations(val config: TransportConfig) {

  suspend fun getLedgerInfo(): Result<LedgerInfo, AptosSdkError> = getLedgerInfo(config)

  suspend fun getChainId(): Result<Long, AptosSdkError> {
    return getLedgerInfo(config).toInternalResult().map { it.chainId }.mapError { it }.toResult()
  }

  suspend fun getBlockByVersion(
    ledgerVersion: Long,
    withTransactions: Boolean? = null,
  ): Result<Block, AptosSdkError> = getBlockByVersion(config, ledgerVersion, withTransactions)

  suspend fun getBlockByHeight(
    ledgerHeight: Long,
    withTransactions: Boolean? = null,
  ): Result<Block, AptosSdkError> = getBlockByHeight(config, ledgerHeight, withTransactions)

  suspend fun getChainTopUserTransactions(
    limit: Int
  ): Result<GetChainTopUserTransactionsQuery.Data?, AptosIndexerError> =
    getChainTopUserTransactions(config, limit)

  suspend fun getIndexerLastSuccessVersion(): Result<Long, AptosIndexerError> =
    getIndexerLastSuccessVersion(config)

  suspend fun getProcessorStatus(
    processorType: ProcessorType
  ): Result<GetProcessorStatusQuery.Data?, AptosIndexerError> =
    getProcessorStatus(config, processorType)
}

internal suspend inline fun <reified T : List<MoveValue>> GeneralOperations.view(
  payload: InputViewFunctionData,
  bcs: Boolean = true,
  ledgerVersion: LedgerVersionArg? = null,
): Result<T, AptosSdkError> {
  return xyz.mcxross.kaptos.internal.view(config, payload, bcs, ledgerVersion)
}
