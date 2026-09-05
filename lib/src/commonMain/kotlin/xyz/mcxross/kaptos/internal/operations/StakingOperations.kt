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

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetDelegatedStakingActivitiesQuery
import xyz.mcxross.kaptos.generated.GetNumberOfDelegatorsQuery
import xyz.mcxross.kaptos.internal.getNumberOfDelegators
import xyz.mcxross.kaptos.internal.getNumberOfDelegatorsForAllPools
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.ActiveDelegatorPerPoolOrder
import xyz.mcxross.kaptos.model.ProcessorType
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

internal class StakingOperations(private val config: TransportConfig) {

  suspend fun getNumberOfDelegators(
    poolAddress: AccountAddressInput,
    sortOrder: List<ActiveDelegatorPerPoolOrder>? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.STAKE_PROCESSOR)
    return getNumberOfDelegators(config, poolAddress, sortOrder)
  }

  suspend fun getNumberOfDelegatorsForAllPools(
    sortOrder: List<ActiveDelegatorPerPoolOrder>? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetNumberOfDelegatorsQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.STAKE_PROCESSOR)
    return getNumberOfDelegatorsForAllPools(config, sortOrder)
  }

  suspend fun getDelegatedStakingActivities(
    poolAddress: AccountAddressInput,
    delegatorAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<GetDelegatedStakingActivitiesQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.STAKE_PROCESSOR)
    return xyz.mcxross.kaptos.internal.getDelegatedStakingActivities(
      config,
      poolAddress,
      delegatorAddress,
    )
  }
}
