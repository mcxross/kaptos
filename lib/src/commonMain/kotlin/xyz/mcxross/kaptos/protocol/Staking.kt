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

package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetDelegatedStakingActivitiesQuery
import xyz.mcxross.kaptos.generated.GetNumberOfDelegatorsQuery
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.ActiveDelegatorPerPoolOrder
import xyz.mcxross.kaptos.model.Result

/** Interface for querying all `Staking` related information. */
interface Staking {

  /**
   * Queries the current number of delegators in a specified pool.
   *
   * This function can wait for the indexer to be synchronized to a specific ledger version before
   * querying.
   *
   * ## Usage
   *
   * ```kotlin
   * val poolAddress = AccountAddress.fromString("0x...")
   * val resolution = aptos.getNumberOfDelegators(poolAddress)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val count = resolution.value
   * println("Number of delegators: $count")
   * }
   * is Result.Err -> {
   * println("Error querying delegator count: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param poolAddress The address of the staking pool to query.
   * @param sortOrder An optional list of sorting options for the results.
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the number of delegators as a `Long`,
   *   or `Result.Err` containing an [AptosIndexerError].
   */
  suspend fun getNumberOfDelegators(
    poolAddress: AccountAddressInput,
    sortOrder: List<ActiveDelegatorPerPoolOrder>? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError>

  /**
   * Queries the current number of delegators across all staking pools.
   *
   * This function can wait for the indexer to be synchronized to a specific ledger version before
   * querying.
   *
   * ## Usage
   *
   * ```kotlin
   * val resolution = aptos.getNumberOfDelegatorsForAllPools()
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved delegator data for all pools: $data")
   * }
   * is Result.Err -> {
   * println("Error querying delegators for all pools: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param sortOrder An optional list of sorting options for the results.
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getNumberOfDelegatorsForAllPools(
    sortOrder: List<ActiveDelegatorPerPoolOrder>? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetNumberOfDelegatorsQuery.Data?, AptosIndexerError>

  /**
   * Queries delegated staking activities for a specific delegator and pool.
   *
   * This function can wait for the indexer to be synchronized to a specific ledger version before
   * querying.
   *
   * ## Usage
   *
   * ```kotlin
   * val poolAddr = AccountAddress.fromString("0x...")
   * val delegatorAddr = AccountAddress.fromString("0x...")
   * val resolution = aptos.getDelegatedStakingActivities(poolAddr, delegatorAddr)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved staking activities: $data")
   * }
   * is Result.Err -> {
   * println("Error querying staking activities: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param poolAddress The address of the staking pool.
   * @param delegatorAddress The address of the delegator.
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getDelegatedStakingActivities(
    poolAddress: AccountAddressInput,
    delegatorAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<GetDelegatedStakingActivitiesQuery.Data?, AptosIndexerError>
}
