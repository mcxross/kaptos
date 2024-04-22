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
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.generated.GetCollectionData
import xyz.mcxross.kaptos.generated.GetTokenData
import xyz.mcxross.kaptos.generated.inputs.String_comparison_exp
import xyz.mcxross.kaptos.generated.inputs.current_collections_v2_bool_exp
import xyz.mcxross.kaptos.model.*

suspend fun getCollectionData(
  config: AptosConfig,
  creatorAddress: AccountAddressInput,
  collectionName: String,
  tokenStandard: TokenStandard?,
): Option<CollectionData> {
  val collectionData =
    GetCollectionData(
      GetCollectionData.Variables(
        where_condition =
          current_collections_v2_bool_exp(
            creator_address = String_comparison_exp(_eq = creatorAddress.value),
            collection_name = String_comparison_exp(_eq = collectionName),
            token_standard = tokenStandard?.let { String_comparison_exp(_eq = it.name) },
          )
      )
    )

  val response: KotlinxGraphQLResponse<CollectionData> =
    try {
      indexerClient(config).execute(collectionData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

suspend fun getCollectionDataByCollectionId(
  config: AptosConfig,
  collectionId: String,
  minimumLedgerVersion: Long?,
): Option<CollectionData> {
  val collectionData =
    GetCollectionData(
      GetCollectionData.Variables(
        where_condition =
          current_collections_v2_bool_exp(collection_id = String_comparison_exp(_eq = collectionId))
      )
    )

  val response: KotlinxGraphQLResponse<CollectionData> =
    try {
      indexerClient(config).execute(collectionData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

suspend fun getTokenData(config: AptosConfig, offset: Int?, limit: Int?): Option<TokenData> {
  val tokenData = GetTokenData(GetTokenData.Variables(offset = offset, limit = limit))

  val response: KotlinxGraphQLResponse<GetTokenData.Result> =
    try {
      indexerClient(config).execute(tokenData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}
