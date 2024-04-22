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

import xyz.mcxross.graphql.client.types.KotlinxGraphQLResponse
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.client.postAptosFullNode
import xyz.mcxross.kaptos.client.postAptosIndexer
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.generated.GetChainTopUserTransactions
import xyz.mcxross.kaptos.model.*

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

internal suspend fun queryIndexer(
  aptosConfig: AptosConfig,
  graphqlQuery: GraphqlQuery,
): AptosResponse {
  return postAptosIndexer(
    RequestOptions.PostAptosRequestOptions(
      aptosConfig = aptosConfig,
      originMethod = "queryIndexer",
      path = "",
      body = graphqlQuery,
    )
  )
}
