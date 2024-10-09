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
import xyz.mcxross.kaptos.protocol.getAccountResource
import xyz.mcxross.kaptos.util.APTOS_COIN
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.getLocalNetwork
import xyz.mcxross.kaptos.util.runBlocking

class AccountTest {
  @Test
  fun testGetAccountInfo() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountInfo(HexInput("0x1"))) {
        is Option.Some -> {
          assertTrue(response.value.sequenceNumber == "0", "Sequence number should be 0")
          assertTrue(
            response.value.authenticationKey ==
              "0x0000000000000000000000000000000000000000000000000000000000000001",
            "Authentication key should be 0x0000000000000000000000000000000000000000000000000000000000000001",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountModules() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountModules(HexInput("0x1"))) {
        is Option.Some -> {
          assertTrue(response.value.isNotEmpty(), "Should have 1 module")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountModule() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountModule(HexInput("0x1"), "coin")) {
        is Option.Some -> {
          assertTrue(response.value.bytecode.isNotEmpty(), "Bytecode should not be empty")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountResources() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountResources(HexInput("0x1"))) {
        is Option.Some -> {
          assertTrue(response.value.isNotEmpty(), "Should have 1 resource")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountResource() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (
        val response =
          aptos.getAccountResource<AccountResource>(HexInput("0x1"), "0x1::account::Account")
      ) {
        is Option.Some -> {
          assertTrue(
            response.value.data.sequenceNumber?.toInt() == 0,
            "Sequence number should be 0",
          )
          assertTrue(
            response.value.data.authenticationKey ==
              "0x0000000000000000000000000000000000000000000000000000000000000001",
            "Authentication key should be 0x0000000000000000000000000000000000000000000000000000000000000001)",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountTransactions() = runBlocking {
    val aptos = getLocalNetwork()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    val bob = Account.generate()
    val rawTxn =
      aptos.buildTransaction.simple(
        sender = alice.accountAddress,
        data =
          entryFunctionData {
            function = "0x1::aptos_account::transfer"
            functionArguments = functionArguments {
              +bob.accountAddress
              +U64(10UL)
            }
          },
      )

    val authenticator = aptos.sign(alice, rawTxn)
    val response = aptos.submitTransaction.simple(rawTxn, authenticator)
    val txn = aptos.waitForTransaction(HexInput(response.expect("").hash))
    val accountTransactions = aptos.getAccountTransactions(accountAddress = alice.accountAddress)
    assertTrue(
      accountTransactions.expect("Couldn't get Account Txns")[0].expect("")[0] ==
        txn.expect("Couldn't get Txn")
    )
  }

  @Test
  fun testGetAccountTransactionCount() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    val accountTransactionsCount =
      aptos.getAccountTransactionsCount(accountAddress = alice.accountAddress)
    assertTrue(accountTransactionsCount.expect("").toInt() == 1)
  }

  @Test
  fun testGetAccountCoinsData() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    val accountCoinsData =
      aptos
        .getAccountCoinsData(accountAddress = alice.accountAddress)
        .expect("Couldn't get account coins data")
        .current_fungible_asset_balances[0]
    assertTrue(accountCoinsData.amount == FUND_AMOUNT)
    assertTrue(accountCoinsData.asset_type == "0x1::aptos_coin::AptosCoin")
  }

  @Test
  fun testGetAccountCoinsCount() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    val accountCoinsCount =
      aptos
        .getAccountCoinsCount(accountAddress = alice.accountAddress)
        .expect("Couldn't get account coins data")
    assertTrue(accountCoinsCount == 1)
  }

  @Test
  fun testGetAccountCoinAmount() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)

    val otherCoinAmount =
      aptos
        .getAccountCoinAmount(
          accountAddress = alice.accountAddress,
          MoveValue.MoveStructId("0x1::string::String"),
        )
        .expect("Couldn't get account coin amount")

    assertTrue(otherCoinAmount == 0L)

    val accountAPTAmount =
      aptos
        .getAccountCoinAmount(
          accountAddress = alice.accountAddress,
          coinType = MoveValue.MoveStructId(APTOS_COIN),
        )
        .expect("Couldn't get account coins data")
    assertTrue(accountAPTAmount == FUND_AMOUNT)
  }
}
