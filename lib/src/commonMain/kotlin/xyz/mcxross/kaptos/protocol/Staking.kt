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

import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.NumberOfDelegators
import xyz.mcxross.kaptos.model.Option

/** Staking API namespace. This interface provides functionality to reading and writing staking. */
interface Staking {

  /**
   * Queries current number of delegators in a pool. Throws an error if the pool is not found.
   *
   * @param poolAddress Pool address
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns The number of delegators for the given pool
   */
  suspend fun getNumberOfDelegators(
    poolAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Option<NumberOfDelegators>
}
