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

import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.api.Query
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.andThen
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.client.postAptosFullNode
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.exception.GraphQLError
import xyz.mcxross.kaptos.generated.GetChainTopUserTransactionsQuery
import xyz.mcxross.kaptos.generated.GetProcessorStatusQuery
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.model.types.processorStatusFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.transaction.builder.generateViewFunctionPayload
import xyz.mcxross.kaptos.util.toOptional

internal suspend fun getLedgerInfo(aptosConfig: AptosConfig): Result<LedgerInfo, AptosSdkError> =
  getAptosFullNode<LedgerInfo>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getLedgerInfo",
        path = "/",
      )
    )
    .toResult()

internal suspend fun getBlockByVersion(
  aptosConfig: AptosConfig,
  ledgerVersion: Long,
  withTransactions: Boolean?,
): Result<Block, AptosSdkError> =
  getAptosFullNode<Block>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getBlockByVersion",
        path = "blocks/by_version/${ledgerVersion}",
        params = mapOf("with_transactions" to withTransactions),
      )
    )
    .toResult()

internal suspend fun getBlockByHeight(
  aptosConfig: AptosConfig,
  ledgerHeight: Long,
  withTransactions: Boolean?,
): Result<Block, AptosSdkError> {

  return getAptosFullNode<Block>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getBlockByHeight",
        path = "blocks/by_height/${ledgerHeight}",
        params = mapOf("with_transactions" to withTransactions),
      )
    )
    .toResult()
}

internal suspend fun getChainTopUserTransactions(
  config: AptosConfig,
  limit: Int,
): Result<GetChainTopUserTransactionsQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config).query(GetChainTopUserTransactionsQuery(limit.toOptional()))
    }
    .toResult()

internal suspend fun getProcessorStatuses(
  aptosConfig: AptosConfig
): Result<GetProcessorStatusQuery.Data?, AptosIndexerError> =
  handleQuery { getGraphqlClient(aptosConfig).query(GetProcessorStatusQuery()) }.toResult()

internal suspend fun getIndexerLastSuccessVersion(
  aptosConfig: AptosConfig
): Result<Long, AptosIndexerError> {
  val statuses = getProcessorStatuses(aptosConfig).expect("Couldn't Retrieve Processor Statuses")

  val version: Long? =
    when (val v = statuses?.processor_status?.firstOrNull()?.last_success_version) {
      is Long -> v
      is Int -> v.toLong()
      is Number -> v.toLong()
      is String -> v.toLongOrNull()
      else -> null
    }

  return version?.let { v -> Result.Ok<Long>(v) }
    ?: Result.Err(
      AptosIndexerError.GraphQL(
        listOf(GraphQLError(message = "No valid last_success_version found"))
      )
    )
}

internal suspend fun getProcessorStatus(
  aptosConfig: AptosConfig,
  processorType: ProcessorType,
): Result<GetProcessorStatusQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(aptosConfig)
        .query(
          GetProcessorStatusQuery(
            Optional.present(
              processorStatusFilter {
                processor = stringFilter { eq = processorType.value.lowercase() }
              }
            )
          )
        )
    }
    .toResult()

suspend inline fun <reified T : List<MoveValue>> view(
  aptosConfig: AptosConfig,
  payload: InputViewFunctionData,
  bcs: Boolean = true,
  options: LedgerVersionArg? = null,
): Result<T, AptosSdkError> {

  val responseResult =
    if (bcs) {
      val viewFunctionPayload = generateViewFunctionPayload(aptosConfig, payload)
      val bytes = Bcs.encodeToByteArray(viewFunctionPayload)
      postAptosFullNode<T, ByteArray>(
        RequestOptions.PostAptosRequestOptions(
          aptosConfig = aptosConfig,
          originMethod = "view",
          path = "view",
          contentType = MimeType.BCS_VIEW_FUNCTION,
          params = options?.ledgerVersion?.let { mapOf("ledger_version" to it) },
          body = bytes,
        )
      )
    } else {
      postAptosFullNode<T, InputViewFunctionData>(
        RequestOptions.PostAptosRequestOptions(
          aptosConfig = aptosConfig,
          originMethod = "view",
          path = "view",
          body = payload,
          params = options?.ledgerVersion?.let { mapOf("ledger_version" to it) },
        )
      )
    }

  return responseResult.andThen { pair -> Ok(pair.second) }.toResult()
}
