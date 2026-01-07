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
import kotlin.test.expect
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.runBlocking

class SimulationTest {
  private val aptos = Aptos(AptosConfig(AptosSettings(Network.LOCAL)))

  private val contractPublisherAccount = Account.generate()
  private val sender = Account.generate()
  private val receiverAccounts = (1..3).map { Account.generate() }

  @Test
  fun entryFunctionTest() {
    runBlocking {
      val rawTxn =
        aptos.buildTransaction.simple(
          sender = sender.accountAddress,
          data =
            entryFunctionData {
              function = "${contractPublisherAccount.accountAddress}::transfer::transfer"
              typeArguments = emptyTypeArguments()
              functionArguments = functionArguments {
                +U64(1UL)
                +MoveString(receiverAccounts[0].accountAddress.value)
              }
            },
        )

      val response = aptos.simulateTransaction.simple(sender.publicKey, rawTxn)

      expect(true) {
        when (response) {
          is Result.Ok -> response.value[0].success
          else -> false
        }
      }
    }
  }
}
