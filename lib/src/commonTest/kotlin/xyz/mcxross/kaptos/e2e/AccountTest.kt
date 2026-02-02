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
import xyz.mcxross.kaptos.extension.longOrNull
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.getAccountResource
import xyz.mcxross.kaptos.util.APTOS_COIN
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.getLocalNetwork
import xyz.mcxross.kaptos.util.runBlocking

class AccountTest {
  private fun ledgerVersionOf(txn: TransactionResponse): Long? =
    when (txn) {
      is UserTransactionResponse -> txn.version.toLongOrNull()
      is BlockMetadataTransactionResponse -> txn.version.toLongOrNull()
      is StateCheckpointTransactionResponse -> txn.version.toLongOrNull()
      is BlockEpilogueTransactionResponse -> txn.version.toLongOrNull()
      is PendingTransactionResponse -> null
    }

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
    val minimumLedgerVersion =
      when (val fundResponse = aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)) {
        is Result.Ok -> ledgerVersionOf(fundResponse.value)
        is Result.Err -> fail("Funding failed: ${fundResponse.error.message}")
      }

    when (
      val resolution =
        aptos.getAccountTransactionsCount(
          accountAddress = alice.accountAddress,
          minimumLedgerVersion = minimumLedgerVersion,
        )
    ) {
      is Result.Ok -> {
        assertTrue(resolution.value >= 1L, "Expected at least one transaction after funding")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${resolution.error.message}")
    }
  }

  @Test
  fun `it fetches account coins data`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    val minimumLedgerVersion =
      when (val fundResponse = aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)) {
        is Result.Ok -> ledgerVersionOf(fundResponse.value)
        is Result.Err -> fail("Funding failed: ${fundResponse.error.message}")
      }

    when (
      val accountCoinsData =
        aptos.getAccountCoinsData(
          accountAddress = alice.accountAddress,
          minimumLedgerVersion = minimumLedgerVersion,
        )
    ) {
      is Result.Ok -> {
        val aptBalance =
          accountCoinsData.value?.current_fungible_asset_balances?.firstOrNull {
            it?.asset_type == APTOS_COIN
          }

        val aptBalanceValue = aptBalance ?: fail("Couldn't find AptosCoin balance")
        assertEquals(
          aptBalanceValue.amount.longOrNull(),
          FUND_AMOUNT,
          "Couldn't get account coins data",
        )
        assertEquals(aptBalanceValue.asset_type, APTOS_COIN, "Couldn't get account coins data")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account coins count`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    val minimumLedgerVersion =
      when (val fundResponse = aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)) {
        is Result.Ok -> ledgerVersionOf(fundResponse.value)
        is Result.Err -> fail("Funding failed: ${fundResponse.error.message}")
      }

    when (
      val accountCoinsCount =
        aptos.getAccountCoinsCount(
          accountAddress = alice.accountAddress,
          minimumLedgerVersion = minimumLedgerVersion,
        )
    ) {
      is Result.Ok -> {
        assertTrue(accountCoinsCount.value >= 1L, "Couldn't get account coins data")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account's coin amount`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()
    val minimumLedgerVersion =
      when (val fundResponse = aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)) {
        is Result.Ok -> ledgerVersionOf(fundResponse.value)
        is Result.Err -> fail("Funding failed: ${fundResponse.error.message}")
      }

    val otherCoinAmount =
      aptos.getAccountCoinAmount(
        accountAddress = alice.accountAddress,
        MoveValue.MoveStructId("0x1::string::String"),
      )

    when (otherCoinAmount) {
      is Result.Ok -> {
        assertEquals(
          otherCoinAmount.value
            ?.current_fungible_asset_balances
            ?.firstOrNull()
            ?.amount
            ?.longOrNull() ?: 0L,
          0L,
          "Couldn't get account coin amount",
        )
      }
      is Result.Err -> fail("")
    }

    // Ensure indexer has caught up before asserting APT balance.
    when (
      val accountCoinsData =
        aptos.getAccountCoinsData(
          accountAddress = alice.accountAddress,
          minimumLedgerVersion = minimumLedgerVersion,
        )
    ) {
      is Result.Ok -> {
        val aptBalance =
          accountCoinsData.value?.current_fungible_asset_balances?.firstOrNull {
            it?.asset_type == APTOS_COIN
          } ?: fail("Couldn't get account coins data")
        assertEquals(FUND_AMOUNT, aptBalance.amount.longOrNull(), "Couldn't get account coins data")
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
        val aptBalance =
          accountAPTAmount.value?.current_fungible_asset_balances?.firstOrNull {
            it?.asset_type == APTOS_COIN
          } ?: fail("Couldn't get account coins data")
        assertEquals(aptBalance.amount.longOrNull(), FUND_AMOUNT, "Couldn't get account coins data")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it fetches account's coin amount from smart contract`() = runBlocking {
    val aptos = Aptos()
    val alice = Account.generate()

    when (val fundResponse = aptos.fundAccount(alice.accountAddress, FUND_AMOUNT)) {
      is Result.Ok -> {
        assertTrue(
          ledgerVersionOf(fundResponse.value) != null,
          "Funding transaction should have a ledger version",
        )
      }
      is Result.Err -> fail("Funding failed: ${fundResponse.error.message}")
    }

    val otherCoinAmount =
      aptos.getAccountCoinAmountFromSmartContract(
        accountAddress = alice.accountAddress,
        coinType = MoveValue.MoveStructId("0x1::string::String"),
      )

    when (otherCoinAmount) {
      is Result.Ok ->
        assertEquals(0L, otherCoinAmount.value, "Expected non-coin type balance to be 0")
      is Result.Err -> fail("Expected Ok but got Err: ${otherCoinAmount.error.message}")
    }

    val aptByType =
      aptos.getAccountCoinAmountFromSmartContract(
        accountAddress = alice.accountAddress,
        coinType = MoveValue.MoveStructId(APTOS_COIN),
      )

    when (aptByType) {
      is Result.Ok ->
        assertEquals(FUND_AMOUNT, aptByType.value, "Couldn't get APT balance by coin type")
      is Result.Err -> fail("Expected Ok but got Err: ${aptByType.error.message}")
    }

    val aptByMetadata =
      aptos.getAccountCoinAmountFromSmartContract(
        accountAddress = alice.accountAddress,
        faMetadataAddress = HexInput("0xA"),
      )

    when (aptByMetadata) {
      is Result.Ok ->
        assertEquals(
          FUND_AMOUNT,
          aptByMetadata.value,
          "Couldn't get APT balance by metadata address",
        )
      is Result.Err -> fail("Expected Ok but got Err: ${aptByMetadata.error.message}")
    }

    when (val missingInputs = aptos.getAccountCoinAmountFromSmartContract(alice.accountAddress)) {
      is Result.Ok -> fail("Expected Err when both coinType and faMetadataAddress are missing")
      is Result.Err -> {
        val errorMessage = missingInputs.error.message ?: ""
        assertTrue(
          errorMessage.contains("Either coinType or faMetadataAddress"),
          "Unexpected error message: $errorMessage",
        )
      }
    }
  }
}
