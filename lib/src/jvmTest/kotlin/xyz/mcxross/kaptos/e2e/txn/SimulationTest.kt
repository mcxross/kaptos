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
package xyz.mcxross.kaptos.e2e.txn

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.api.Faucet as FaucetApi
import xyz.mcxross.kaptos.api.Transaction as TransactionApi
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.runBlocking

class SimulationTest {
  private val transportConfig = TransportConfig(AptosSettings(Network.LOCAL))

  private val sender = Account.generate()
  private val receiverAccounts = (1..3).map { Account.generate() }

  @Test
  fun entryFunctionTest() {
    runBlocking {
      FaucetApi(transportConfig)
        .fundAccount(sender.accountAddress, FUND_AMOUNT)
        .expect("Failed to fund sender")

      val rawTxn =
        TransactionApi(transportConfig).buildSimpleTransaction(sender = sender.accountAddress) {
          function = "0x1::aptos_account::transfer"
          args(receiverAccounts[0].accountAddress, 1UL)
        }

      val response =
        TransactionApi(transportConfig).simulateTransaction.simple(sender.publicKey, rawTxn)

      when (response) {
        is Result.Ok -> {
          assertTrue(response.value.isNotEmpty(), "Expected at least one simulation output")
          assertTrue(response.value[0].success, "Simulation failed: ${response.value[0].vmStatus}")
        }
        is Result.Err -> fail("Simulation request failed: ${response.error.message}")
      }
    }
  }
}
