/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.staking.DelegatedStakingActivity
import xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount
import xyz.mcxross.kaptos.staking.StakingService

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Staking internal constructor(private val service: StakingService) {
  @Throws(Throwable::class)
  suspend fun getDelegatorCount(pool: AccountAddressInput): ULong =
    service.getDelegatorCount(pool = pool).swiftValue()

  @Throws(Throwable::class)
  suspend fun getDelegatorCounts(): List<StakingPoolDelegatorCount> =
    service.getDelegatorCounts().swiftValue()

  @Throws(Throwable::class)
  suspend fun getActivities(
    pool: AccountAddressInput,
    delegator: AccountAddressInput,
  ): List<DelegatedStakingActivity> =
    service.getActivities(pool = pool, delegator = delegator).swiftValue()
}
