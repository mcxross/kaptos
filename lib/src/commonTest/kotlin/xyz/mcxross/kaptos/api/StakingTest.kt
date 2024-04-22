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

package xyz.mcxross.kaptos.api

import kotlin.test.Test
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.util.runBlocking
import xyz.mcxross.kaptos.util.toAccountAddress

class StakingTest {
  @Test
  fun testGetNumberOfDelegators() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.DEVNET)))
      val response =
        aptos.getNumberOfDelegators(
          "0x12345678901234567850020dfd67646b1e46282999483e7064e70f02f7e12345".toAccountAddress()
        )

      when (response) {
        is Option.Some -> {
          assertTrue(
            response.value.num_active_delegator_per_pool.isEmpty(),
            "Should have no active delegators since it is a bad address",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }
}
