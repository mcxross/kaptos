/*
 * Copyright 2025 McXross
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

import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetCurrentFungibleAssetBalancesQuery
import xyz.mcxross.kaptos.generated.GetFungibleAssetActivitiesQuery
import xyz.mcxross.kaptos.generated.GetFungibleAssetMetadataQuery
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.FungibleAssetActivityFilter
import xyz.mcxross.kaptos.model.FungibleAssetBalanceFilter
import xyz.mcxross.kaptos.model.FungibleAssetMetadataFilter
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.util.toOptional

internal suspend fun getCurrentFungibleAssetBalances(
  config: AptosConfig,
  filter: FungibleAssetBalanceFilter?,
  page: PaginationArgs?,
): Result<GetCurrentFungibleAssetBalancesQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetCurrentFungibleAssetBalancesQuery(
            where_condition = filter.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getFungibleAssetActivities(
  config: AptosConfig,
  filter: FungibleAssetActivityFilter,
  page: PaginationArgs?,
): Result<GetFungibleAssetActivitiesQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetFungibleAssetActivitiesQuery(
            where_condition = filter.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getFungibleAssetMetadata(
  config: AptosConfig,
  filter: FungibleAssetMetadataFilter?,
  page: PaginationArgs?,
): Result<GetFungibleAssetMetadataQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetFungibleAssetMetadataQuery(
            where_condition = filter.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .toResult()
