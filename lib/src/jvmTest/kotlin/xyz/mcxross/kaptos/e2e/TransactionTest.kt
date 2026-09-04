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
import kotlin.test.assertTrue
import kotlin.test.fail
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.api.Coin as CoinApi
import xyz.mcxross.kaptos.api.Faucet as FaucetApi
import xyz.mcxross.kaptos.api.General as GeneralApi
import xyz.mcxross.kaptos.api.Transaction as TransactionApi
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.unit.TRANSFER_AMOUNT
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.localTransportConfig
import xyz.mcxross.kaptos.util.runBlocking

class TransactionTest {

  @Test
  fun testGetGasPriceEstimation() = runBlocking {
    val transportConfig = localTransportConfig()
    when (val resolution = TransactionApi(transportConfig).getGasPriceEstimation()) {
      is Result.Ok -> {
        assertTrue(resolution.value.gasEstimate > 0, "Gas estimate should be greater than 0")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it queries for transactions on the chain`() = runBlocking {
    val transportConfig = localTransportConfig()
    when (val resolution = TransactionApi(transportConfig).getTransactions()) {
      is Result.Ok -> {
        assertTrue(resolution.value.isNotEmpty(), "Chain should contain transactions.")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it queries for transactions by version`() = runBlocking {
    val transportConfig = localTransportConfig()
    val (alice, bob) = createAndFundAccounts()
    val wait = submitAndWaitForTransaction(alice, bob)

    when (
      val resolution =
        TransactionApi(transportConfig).getTransactionByVersion(wait.version.toLong())
    ) {
      is Result.Ok -> {
        assertEquals(resolution.value, wait, "Could not retrieve txn by version.")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it queries for transactions by hash`() = runBlocking {
    val transportConfig = localTransportConfig()
    val (alice, bob) = createAndFundAccounts()
    val wait = submitAndWaitForTransaction(alice, bob)

    when (val resolution = TransactionApi(transportConfig).getTransactionByHash(wait.hash)) {
      is Result.Ok -> {
        assertEquals(resolution.value, wait, "Unable to retrieve txn by hash.")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches block data by block height`() = runBlocking {
    val transportConfig = localTransportConfig()
    val blockHeight = 1L
    when (val resolution = GeneralApi(transportConfig).getBlockByHeight(blockHeight)) {
      is Result.Ok -> {
        assertEquals(
          resolution.value.blockHeight.toLong(),
          blockHeight,
          "Cannot fetch block data by height.",
        )
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches block data by block version`() = runBlocking {
    val transportConfig = localTransportConfig()
    val blockVersion = 1L
    when (val resolution = GeneralApi(transportConfig).getBlockByVersion(blockVersion)) {
      is Result.Ok -> {
        assertEquals(
          resolution.value.blockHeight.toLong(),
          blockVersion,
          "Cannot fetch block data by version.",
        )
      }
      is Result.Err -> fail("")
    }
  }

  private suspend fun createAndFundAccounts(): Pair<Account, Account> {
    val transportConfig = localTransportConfig()
    val alice = Account.generate()
    val bob = Account.generate()
    FaucetApi(transportConfig)
      .fundAccount(alice.accountAddress, FUND_AMOUNT)
      .expect("Failed to fund Alice's account")
    return alice to bob
  }

  private suspend fun submitAndWaitForTransaction(
    alice: Account,
    bob: Account,
  ): UserTransactionResponse {
    val transportConfig = localTransportConfig()
    val txn =
      CoinApi(transportConfig)
        .transferCoinTransaction(alice.accountAddress, bob.accountAddress, TRANSFER_AMOUNT)
    val sub =
      TransactionApi(transportConfig)
        .signAndSubmitTransaction(alice, txn)
        .expect("Could not sign and transfer transaction.")
    return TransactionApi(transportConfig)
      .waitForTransaction(HexInput(sub.hash))
      .expect("Unable to wait for transaction.") as UserTransactionResponse
  }
}
