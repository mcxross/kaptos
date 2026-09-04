/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.staking

import xyz.mcxross.kaptos.account.toAptosError
import xyz.mcxross.kaptos.generated.GetDelegatedStakingActivitiesQuery
import xyz.mcxross.kaptos.generated.GetNumberOfDelegatorsQuery
import xyz.mcxross.kaptos.internal.getDelegatedStakingActivities
import xyz.mcxross.kaptos.internal.getNumberOfDelegatorsData
import xyz.mcxross.kaptos.internal.getNumberOfDelegatorsForAllPools
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Result

/** Active delegator count for one staking pool. */
data class StakingPoolDelegatorCount(
  val pool: AccountAddress,
  val activeDelegators: ULong,
)

/** One indexed delegated-staking balance change. */
data class DelegatedStakingActivity(
  val amount: ULong,
  val delegator: AccountAddress,
  val eventIndex: ULong,
  val eventType: String,
  val pool: AccountAddress,
  val transactionVersion: ULong,
)

/** Read-only delegated-staking queries exposed as `client.staking`. */
interface StakingService {
  /** Returns the active delegator count for [pool]. */
  suspend fun getDelegatorCount(pool: AccountAddressInput): AptosResult<ULong>

  /** Returns active delegator counts for every indexed pool. */
  suspend fun getDelegatorCounts(): AptosResult<List<StakingPoolDelegatorCount>>

  /** Returns delegated-staking activity for one [delegator] in [pool]. */
  suspend fun getActivities(
    pool: AccountAddressInput,
    delegator: AccountAddressInput,
  ): AptosResult<List<DelegatedStakingActivity>>
}

internal class DefaultStakingService(
  private val config: TransportConfig,
) : StakingService {
  override suspend fun getDelegatorCount(pool: AccountAddressInput): AptosResult<ULong> =
    try {
      when (val result = getNumberOfDelegatorsData(config, AccountAddress.from(pool), null)) {
        is Result.Err -> AptosResult.Failure(result.error.toAptosError())
        is Result.Ok ->
          AptosResult.Success(
            (result.value?.num_active_delegator_per_pool?.firstOrNull()?.num_active_delegator ?: 0)
              .requiredU64("num_active_delegator")
          )
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid staking pool address", error))
    }

  override suspend fun getDelegatorCounts(): AptosResult<List<StakingPoolDelegatorCount>> =
    when (val result = getNumberOfDelegatorsForAllPools(config, null)) {
      is Result.Err -> AptosResult.Failure(result.error.toAptosError())
      is Result.Ok ->
        try {
          AptosResult.Success(
            result.value?.num_active_delegator_per_pool.orEmpty().mapNotNull { row ->
              val pool = row.pool_address ?: return@mapNotNull null
              row.toRecord(pool)
            }
          )
        } catch (error: Throwable) {
          AptosResult.Failure(AptosError.Serialization("Invalid staking count returned by indexer", error))
        }
    }

  override suspend fun getActivities(
    pool: AccountAddressInput,
    delegator: AccountAddressInput,
  ): AptosResult<List<DelegatedStakingActivity>> =
    try {
      when (
        val result =
          getDelegatedStakingActivities(
            config,
            AccountAddress.from(pool),
            AccountAddress.from(delegator),
          )
      ) {
        is Result.Err -> AptosResult.Failure(result.error.toAptosError())
        is Result.Ok ->
          AptosResult.Success(
            result.value?.delegated_staking_activities.orEmpty().map(
              GetDelegatedStakingActivitiesQuery.Delegated_staking_activity::toRecord
            )
          )
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Serialization("Invalid staking activity returned by indexer", error))
    }
}

internal fun GetNumberOfDelegatorsQuery.Num_active_delegator_per_pool.toRecord(
  requiredPool: String = requireNotNull(pool_address) { "Missing staking pool address" },
): StakingPoolDelegatorCount =
  StakingPoolDelegatorCount(
    pool = AccountAddress.fromString(requiredPool),
    activeDelegators = (num_active_delegator ?: 0).requiredU64("num_active_delegator"),
  )

internal fun GetDelegatedStakingActivitiesQuery.Delegated_staking_activity.toRecord():
  DelegatedStakingActivity =
  DelegatedStakingActivity(
    amount = amount.requiredU64("amount"),
    delegator = AccountAddress.fromString(delegator_address),
    eventIndex = event_index.requiredU64("event_index"),
    eventType = event_type,
    pool = AccountAddress.fromString(pool_address),
    transactionVersion = transaction_version.requiredU64("transaction_version"),
  )

private fun Any.requiredU64(field: String): ULong =
  toString().trim('"').toULongOrNull()
    ?: throw IllegalArgumentException("Invalid $field")
