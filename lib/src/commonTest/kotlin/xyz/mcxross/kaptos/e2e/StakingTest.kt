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

package xyz.mcxross.kaptos.e2e

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.ActiveDelegatorCountSortOrder
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.types.OrderBy
import xyz.mcxross.kaptos.util.runBlocking
import xyz.mcxross.kaptos.util.toAccountAddress

class StakingTest {

  @Test
  fun `it queries for the number of delegators`() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))
      val sortOrder = ActiveDelegatorCountSortOrder(num_active_delegator = OrderBy.DESC.optional())

      when (val result = aptos.getNumberOfDelegatorsForAllPools(sortOrder = listOf(sortOrder))) {
        is Result.Ok -> {
          val data = assertNotNull(result.value, "API response data should not be null.")
          val pools =
            assertNotNull(data.num_active_delegator_per_pool, "Pools list should not be null.")

          assertTrue(pools.size > 5, "Expected at least 6 pools for a valid sort check.")

          pools.zipWithNext().forEach { (current, next) ->
            val currentDelegators = current.num_active_delegator.toString().toLong()
            val nextDelegators = next.num_active_delegator.toString().toLong()
            assertTrue(
              currentDelegators >= nextDelegators,
              "Pools should be sorted in descending order. Found $currentDelegators then $nextDelegators.",
            )
          }

          val topPool = pools.first()
          val poolAddress = topPool.pool_address

          if (poolAddress != null) {
            val topPoolAddress = AccountAddress.fromString(poolAddress)
            val expectedCount = topPool.num_active_delegator.toString().toLong()

            val specificPoolResult = aptos.getNumberOfDelegators(topPoolAddress)

            if (specificPoolResult is Result.Ok) {
              assertEquals(
                expectedCount,
                specificPoolResult.value,
                "Direct query for the top pool's delegator count should match the list's value.",
              )
            } else if (specificPoolResult is Result.Err) {
              fail("Direct query for top pool failed: ${specificPoolResult.error}")
            }
          }
        }
        is Result.Err -> {
          fail("API call to get all delegators failed: ${result.error}")
        }
      }
    }
  }

  @Test
  fun `it returns 0 if the poolAddress does not exist`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.DEVNET)))
    val poolAddress =
      "0x12345678901234567850020dfd67646b1e46282999483e7064e70f02f7e12345".toAccountAddress()
    when (val result = aptos.getNumberOfDelegators(poolAddress)) {
      is Result.Ok -> {
        val value = result.value
        assertEquals(0, value, "Expected number of delegators to be 0")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${result.error}")
    }
  }

  @Test
  fun `it queries for the activity of a delegator for a given pool`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))

    val poolAddress =
      "0x06099edbe54f242bad50020dfd67646b1e46282999483e7064e70f02f7ea3c15".toAccountAddress()
    val delegatorAddress =
      "0x5aa16d9f590b635f8cc17ba4abf40f60c77df0078cf5296a539cfbb9e87a285a".toAccountAddress()

    when (val response = aptos.getDelegatedStakingActivities(poolAddress, delegatorAddress)) {
      is Result.Ok -> {
        val activity = response.value
        assertEquals(
          activity?.delegated_staking_activities?.isNotEmpty(),
          true,
          "Expected delegated staking activities to be non-empty",
        )
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }
}
