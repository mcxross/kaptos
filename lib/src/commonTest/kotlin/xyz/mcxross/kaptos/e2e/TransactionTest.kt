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
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.unit.TRANSFER_AMOUNT
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.runBlocking

class TransactionTest {

  private val aptos = Aptos()
  private val aptosLocal = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

  @Test
  fun testGetGasPriceEstimation() = runBlocking {
    val response = aptosLocal.getGasPriceEstimation()
    assertTrue(response.gasEstimate > 0, "Gas estimate should be greater than 0")
  }

  @Test
  fun `it queries for transactions on the chain`() = runBlocking {
    val txns = aptos.getTransactions().expect("Couldn't retrieve transactions.")
    assertTrue(txns.isNotEmpty(), "Chain should contain transactions.")
  }

  @Test
  fun `it queries for transactions by version`() = runBlocking {
    val (alice, bob) = createAndFundAccounts()
    val wait = submitAndWaitForTransaction(alice, bob)

    val respByVersion =
      aptos
        .getTransactionByVersion(wait.version.toLong())
        .expect("Could not retrieve txn by version.")

    assertTrue(respByVersion == wait)
  }

  @Test
  fun `it queries for transactions by hash`() = runBlocking {
    val (alice, bob) = createAndFundAccounts()
    val wait = submitAndWaitForTransaction(alice, bob)

    val resp = aptos.getTransactionByHash(wait.hash).expect("Unable to retrieve txn by hash.")

    assertTrue(resp == wait)
  }

  @Test
  fun `it fetches block data by block height`() = runBlocking {
    val blockHeight = 1L
    val blockData = aptos.getBlockByHeight(blockHeight).expect("Cannot fetch block data by height.")
    assertTrue(blockData.blockHeight.toLong() == blockHeight)
  }

  @Test
  fun `it fetches block data by block version`() = runBlocking {
    val blockVersion = 1L
    val blockData =
      aptos.getBlockByVersion(blockVersion).expect("Cannot fetch block data by version.")
    assertTrue(blockData.blockHeight.toLong() == blockVersion)
  }

  private suspend fun createAndFundAccounts(): Pair<Account, Account> {
    val alice = Account.generate()
    val bob = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    return alice to bob
  }

  private suspend fun submitAndWaitForTransaction(
    alice: Account,
    bob: Account,
  ): UserTransactionResponse {
    val txn =
      aptos.transferCoinTransaction(alice.accountAddress, bob.accountAddress, TRANSFER_AMOUNT)
    val sub =
      aptos.signAndSubmitTransaction(alice, txn).expect("Could not sign and transfer transaction.")
    return aptos.waitForTransaction(HexInput(sub.hash)).expect("Unable to wait for transaction.")
      as UserTransactionResponse
  }
}
