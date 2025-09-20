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

import com.github.michaelbull.result.map
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetDelegatedStakingActivitiesQuery
import xyz.mcxross.kaptos.generated.GetNumberOfDelegatorsQuery
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.ActiveDelegatorPerPoolOrder
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.types.numActiveDelegatorPerPoolFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.util.toOptional

internal suspend fun getNumberOfDelegators(
  aptosConfig: AptosConfig,
  poolAddress: AccountAddressInput,
  sortOrder: List<ActiveDelegatorPerPoolOrder>?,
): Result<Long, AptosIndexerError> =
  handleQuery {
      val filter = numActiveDelegatorPerPoolFilter {
        this.poolAddress = stringFilter { eq = poolAddress.toString() }
      }

      getGraphqlClient(aptosConfig)
        .query(
          GetNumberOfDelegatorsQuery(
            where_condition = filter.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .map { data ->
      val count = data?.num_active_delegator_per_pool?.firstOrNull()?.num_active_delegator
      count?.toString()?.toLong() ?: 0L
    }
    .toResult()

internal suspend fun getNumberOfDelegatorsForAllPools(
  aptosConfig: AptosConfig,
  sortOrder: List<ActiveDelegatorPerPoolOrder>?,
): Result<GetNumberOfDelegatorsQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(aptosConfig)
        .query(GetNumberOfDelegatorsQuery(order_by = sortOrder.toOptional()))
    }
    .toResult()

internal suspend fun getDelegatedStakingActivities(
  aptosConfig: AptosConfig,
  poolAddress: AccountAddressInput,
  delegatorAddress: AccountAddressInput,
): Result<GetDelegatedStakingActivitiesQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(aptosConfig)
        .query(
          GetDelegatedStakingActivitiesQuery(
            poolAddress = AccountAddress.from(poolAddress).toStringLong().toOptional(),
            delegatorAddress = AccountAddress.from(delegatorAddress).toStringLong().toOptional(),
          )
        )
    }
    .toResult()
