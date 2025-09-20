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
  fun `it fetches account data`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

    when (val response = aptos.getAccountInfo(HexInput("0x1"))) {
      is Result.Ok -> {
        assertEquals(response.value.sequenceNumber, "0", "Sequence number should be 0")
        assertEquals(
          response.value.authenticationKey,
          "0x0000000000000000000000000000000000000000000000000000000000000001",
          "Authentication key should be 0x0000000000000000000000000000000000000000000000000000000000000001",
        )
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account modules`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    when (val response = aptos.getAccountModules(HexInput("0x1"))) {
      is Result.Ok -> {
        assertTrue(response.value.isNotEmpty(), "Should have 1 module")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches an account module`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    when (val response = aptos.getAccountModule(HexInput("0x1"), "coin")) {
      is Result.Ok -> {
        assertTrue(response.value.bytecode.isNotEmpty(), "Bytecode should not be empty")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account resources`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    when (val response = aptos.getAccountResources(HexInput("0x1"))) {
      is Result.Ok -> {
        assertTrue(response.value.isNotEmpty(), "Should have 1 resource")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches an account resource with a type`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    when (
      val response =
        aptos.getAccountResource<AccountResource>(HexInput("0x1"), "0x1::account::Account")
    ) {
      is Result.Ok -> {
        assertEquals(response.value.data.sequenceNumber?.toInt(), 0, "Sequence number should be 0")
        assertEquals(
          response.value.data.authenticationKey,
          "0x0000000000000000000000000000000000000000000000000000000000000001",
          "Authentication key should be 0x0000000000000000000000000000000000000000000000000000000000000001)",
        )
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account transactions`() = runBlocking {
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
    when (val response = aptos.submitTransaction.simple(rawTxn, authenticator)) {
      is Result.Ok -> {
        when (val txn = aptos.waitForTransaction(HexInput(response.value.hash))) {
          is Result.Ok -> {
            when (
              val accountTransactions =
                aptos.getAccountTransactions(accountAddress = alice.accountAddress)
            ) {
              is Result.Ok -> {
                assertEquals(accountTransactions.value[0], txn.value)
              }

              is Result.Err -> fail("")
            }
          }
          is Result.Err -> fail("")
        }
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account transactions count`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)

    when (
      val resolution = aptos.getAccountTransactionsCount(accountAddress = alice.accountAddress)
    ) {
      is Result.Ok -> {
        assertEquals(resolution.value.toInt(), 1)
      }
      is Result.Err -> fail("Expected Ok but got Err: ${resolution.error.message}")
    }
  }

  @Test
  fun `it fetches account coins data`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    when (val accountCoinsData = aptos.getAccountCoinsData(accountAddress = alice.accountAddress)) {
      is Result.Ok -> {
        assertEquals(
          accountCoinsData.value
            ?.current_fungible_asset_balances
            ?.firstOrNull()
            ?.amount
            .toString()
            .toLong(),
          FUND_AMOUNT,
          "Couldn't get account coins data",
        )
        assertEquals(
          accountCoinsData.value?.current_fungible_asset_balances?.firstOrNull()?.asset_type,
          "0x1::aptos_coin::AptosCoin",
          "Couldn't get account coins data",
        )
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account coins count`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)
    when (
      val accountCoinsCount = aptos.getAccountCoinsCount(accountAddress = alice.accountAddress)
    ) {
      is Result.Ok -> {
        assertEquals(accountCoinsCount.value, 1L, "Couldn't get account coins data")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account's coin amount`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)

    val otherCoinAmount =
      aptos.getAccountCoinAmount(
        accountAddress = alice.accountAddress,
        MoveValue.MoveStructId("0x1::string::String"),
      )

    when (otherCoinAmount) {
      is Result.Ok -> {
        assertEquals(
          otherCoinAmount.value?.current_fungible_asset_balances?.firstOrNull()?.amount ?: "0",
          "0",
          "Couldn't get account coin amount",
        )
      }
      is Result.Err -> fail("")
    }

    val accountAPTAmount =
      aptos.getAccountCoinAmount(
        accountAddress = alice.accountAddress,
        coinType = MoveValue.MoveStructId(APTOS_COIN),
      )

    when (accountAPTAmount) {
      is Result.Ok -> {
        assertEquals(
          accountAPTAmount.value
            ?.current_fungible_asset_balances
            ?.firstOrNull()
            ?.amount
            .toString()
            .toLong(),
          FUND_AMOUNT,
          "Couldn't get account coins data",
        )
      }
      is Result.Err -> fail("")
    }
  }
}
