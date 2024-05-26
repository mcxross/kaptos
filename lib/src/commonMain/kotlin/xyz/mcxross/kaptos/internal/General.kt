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

import io.ktor.client.call.*
import io.ktor.http.*
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.graphql.client.types.KotlinxGraphQLResponse
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.client.postAptosFullNode
import xyz.mcxross.kaptos.client.postAptosIndexer
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.generated.GetChainTopUserTransactions
import xyz.mcxross.kaptos.generated.GetProcessorStatus
import xyz.mcxross.kaptos.generated.inputs.String_comparison_exp
import xyz.mcxross.kaptos.generated.inputs.processor_status_bool_exp
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.builder.generateViewFunctionPayload

internal suspend fun getLedgerInfo(aptosConfig: AptosConfig): Option<LedgerInfo> =
  getAptosFullNode<LedgerInfo>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getLedgerInfo",
        path = "",
      )
    )
    .second

internal suspend fun getBlockByVersion(
  aptosConfig: AptosConfig,
  ledgerVersion: Long,
): Option<Block> =
  getAptosFullNode<Block>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getBlockByVersion",
        path = "blocks/by_version/${ledgerVersion}",
      )
    )
    .second

internal suspend fun getBlockByHeight(aptosConfig: AptosConfig, ledgerHeight: Long): Option<Block> =
  getAptosFullNode<Block>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getBlockByHeight",
        path = "blocks/by_height/${ledgerHeight}",
      )
    )
    .second

suspend inline fun <reified T> getTableItem(
  aptosConfig: AptosConfig,
  handle: String,
  data: TableItemRequest,
  param: Map<String, Any?>? = null,
): T {
  val response =
    postAptosFullNode<T, TableItemRequest>(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getTableItem",
        path = "tables/${handle}/item",
        body = data,
        params = param,
      )
    )

  return response.second
}

internal suspend fun getChainTopUserTransactions(
  aptosConfig: AptosConfig,
  limit: Int,
): Option<ChainTopUserTransactions> {
  val topUserTransactions =
    GetChainTopUserTransactions(GetChainTopUserTransactions.Variables(limit = limit))

  val response: KotlinxGraphQLResponse<ChainTopUserTransactions> =
    try {
      indexerClient(aptosConfig).execute(topUserTransactions)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

suspend inline fun <reified T> queryIndexer(
  aptosConfig: AptosConfig,
  graphqlQuery: GraphqlQuery,
): Option<T> {
  val response =
    postAptosIndexer(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "queryIndexer",
        path = "",
        body = graphqlQuery,
      )
    )

  if (response.status != HttpStatusCode.OK) {
    throw AptosException("GraphQL query execution failed: ${response.call}")
  }

  return Option.Some(response.body())
}

internal suspend fun getProcessorStatuses(aptosConfig: AptosConfig): Option<ProcessorStatus> {
  val statuses = GetProcessorStatus(GetProcessorStatus.Variables())

  val response: KotlinxGraphQLResponse<ProcessorStatus> =
    try {
      indexerClient(aptosConfig).execute(statuses)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

internal suspend fun getIndexerLastSuccessVersion(aptosConfig: AptosConfig): Option<Long> {
  val statuses = getProcessorStatuses(aptosConfig)

  return if (statuses is Option.Some) {
    Option.Some(statuses.value.processor_status.first().last_success_version.toLong())
  } else {
    Option.None
  }
}

internal suspend fun getProcessorStatus(
  aptosConfig: AptosConfig,
  processorType: ProcessorType,
): Option<ProcessorStatus> {

  val condition =
    processor_status_bool_exp(processor = String_comparison_exp(_eq = processorType.value))
  val statuses = GetProcessorStatus(GetProcessorStatus.Variables(condition))

  val response: KotlinxGraphQLResponse<ProcessorStatus> =
    try {
      indexerClient(aptosConfig).execute(statuses)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

suspend inline fun <reified T : List<MoveValue>> view(
  aptosConfig: AptosConfig,
  payload: InputViewFunctionData,
  bcs: Boolean,
  options: LedgerVersionArg?,
): Option<T> {

  if (bcs) {
    val viewFunctionPayload = generateViewFunctionPayload(aptosConfig, payload)

    val bytes = Bcs.encodeToByteArray(viewFunctionPayload)

    val response =
      postAptosFullNode<T, ByteArray>(
        RequestOptions.PostAptosRequestOptions(
          aptosConfig = aptosConfig,
          originMethod = "view",
          path = "view",
          contentType = MimeType.BCS_VIEW_FUNCTION,
          params = mapOf("ledger_version" to options?.ledgerVersion),
          body = bytes,
        )
      )

    return Option.Some(response.second)
  }

  val response =
    postAptosFullNode<T, InputViewFunctionData>(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "view",
        path = "view",
        body = payload,
        params = mapOf("ledger_version" to options?.ledgerVersion),
      )
    )

  return Option.Some(response.second)
}
