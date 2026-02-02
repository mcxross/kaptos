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
import kotlin.test.expect
import kotlin.test.fail
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.extension.longOrNull
import xyz.mcxross.kaptos.generated.GetAccountCoinsDataQuery
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.util.APTOS_COIN
import xyz.mcxross.kaptos.util.runBlocking

class CoinTest {
  private fun ledgerVersionOf(txn: TransactionResponse): Long? =
    when (txn) {
      is UserTransactionResponse -> txn.version.toLongOrNull()
      is BlockMetadataTransactionResponse -> txn.version.toLongOrNull()
      is StateCheckpointTransactionResponse -> txn.version.toLongOrNull()
      is BlockEpilogueTransactionResponse -> txn.version.toLongOrNull()
      is PendingTransactionResponse -> null
    }

  private fun aptAmount(data: GetAccountCoinsDataQuery.Data?): ULong {
    val amount =
      data?.current_fungible_asset_balances?.firstOrNull { it?.asset_type == APTOS_COIN }?.amount
        ?: return 0UL
    return amount.longOrNull()?.toULong() ?: 0UL
  }

  @Test
  fun `it generates a transfer coin transaction with AptosCoin coin type`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

    val alice = Account.generate()
    val bob = Account.generate()

    aptos.fundAccount(alice.accountAddress, 100_000_00).expect("Failed to fund Alice's account")

    val txn =
      aptos.transferCoinTransaction(
        from = alice.accountAddress,
        to = bob.accountAddress,
        amount = 10U,
      )

    val rawTransaction: RawTransaction = txn.rawTransaction

    if (rawTransaction.payload !is TransactionPayloadEntryFunction) {
      throw Error("Transaction payload is not an entry function")
    }

    val typeArg = rawTransaction.payload.entryFunction.typeArgs[0]

    if (!typeArg.isStruct()) {
      throw Error("Transaction payload type arg is not a struct")
    }

    expect(true) { (typeArg as TypeTagStruct).type.address.toString() == "0x1" }
    expect(true) { (typeArg as TypeTagStruct).type.moduleName == "aptos_coin" }
    expect(true) { (typeArg as TypeTagStruct).type.name == "AptosCoin" }
  }

  @Test
  fun ` it generates a transfer coin transaction with a custom coin type`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

    val alice = Account.generate()
    val bob = Account.generate()

    aptos.fundAccount(alice.accountAddress, 100_000_00).expect("Failed to fund Alice's account")

    val txn =
      aptos.transferCoinTransaction(
        from = alice.accountAddress,
        to = bob.accountAddress,
        amount = 10U,
        coinType = "0x1::my_coin::type",
      )

    val rawTransaction: RawTransaction = txn.rawTransaction

    if (rawTransaction.payload !is TransactionPayloadEntryFunction) {
      throw Error("Transaction payload is not an entry function")
    }

    val typeArg = rawTransaction.payload.entryFunction.typeArgs[0]

    if (!typeArg.isStruct()) {
      throw Error("Transaction payload type arg is not a struct")
    }

    expect(true) { (typeArg as TypeTagStruct).type.address.toString() == "0x1" }
    expect(true) { (typeArg as TypeTagStruct).type.moduleName == "my_coin" }
    expect(true) { (typeArg as TypeTagStruct).type.name == "type" }
  }

  @Test
  fun `it transfers APT coin amount from sender to recipient`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.DEVNET)))

    val alice = Account.generate()
    val bob = Account.generate()

    val fundingResponse =
      aptos.fundAccount(alice.accountAddress, 100_000_000).expect("Failed to fund Alice's account")
    val fundingVersion = ledgerVersionOf(fundingResponse)

    val aliceBalanceBefore =
      aptos
        .getAccountCoinsData(alice.accountAddress, minimumLedgerVersion = fundingVersion)
        .expect("Failed to get Alice's balance")

    val txn =
      aptos.transferCoinTransaction(
        from = alice.accountAddress,
        to = bob.accountAddress,
        amount = 10U,
      )

    val committedTransaction = aptos.signAndSubmitTransaction(alice, txn)

    val executedTransaction =
      aptos.waitForTransaction(
        HexInput.fromString(committedTransaction.expect("Transaction failed").hash)
      )

    val executedVersion =
      when (executedTransaction) {
        is Result.Ok -> {
          val response = executedTransaction.value
          if (response is UserTransactionResponse) {
            expect(true) { response.success }
          }
          ledgerVersionOf(response)
        }
        is Result.Err -> fail("waitForTransaction failed: ${executedTransaction.error.message}")
      }

    val aliceBalanceAfter =
      aptos
        .getAccountCoinsData(alice.accountAddress, minimumLedgerVersion = executedVersion)
        .expect("Failed to get Alice's balance")
    val bobBalance =
      aptos
        .getAccountCoinsData(bob.accountAddress, minimumLedgerVersion = executedVersion)
        .expect("Failed to get Bob's balance")

    expect(true) { aptAmount(aliceBalanceAfter) < aptAmount(aliceBalanceBefore) }
    expect(true) { aptAmount(bobBalance) >= 10UL }
  }
}
