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
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.runBlocking

class TransactionTest {

  @Test
  fun testGetGasPriceEstimation() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      val response = aptos.getGasPriceEstimation()
      assertTrue(response.gasEstimate > 0, "Gas estimate should be greater than 0")
    }
  }

  @Test
  fun `it queries for transactions on the chain`() = runBlocking {
    val aptos = Aptos()
    val txns = aptos.getTransactions().expect("Couldn't retrieve transactions.")
    assertTrue(
      txns.isNotEmpty(),
      "Transactions on the chain should have zero size of transactions.",
    )
  }
}
