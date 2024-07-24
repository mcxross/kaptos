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
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.util.runBlocking

class CoinTest {

  // Generates a transfer coin transaction with AptosCoin coin type
  @Test
  fun testTransferCoinTransaction() = runBlocking {
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

    val typeArg =
      (rawTransaction.payload as TransactionPayloadEntryFunction).entryFunction.typeArgs[0]

    if (!typeArg.isStruct()) {
      throw Error("Transaction payload type arg is not a struct")
    }

    expect(true) { (typeArg as TypeTagStruct).type.address.toString() == "0x1" }
    expect(true) { (typeArg as TypeTagStruct).type.moduleName == "aptos_coin" }
    expect(true) { (typeArg as TypeTagStruct).type.name == "AptosCoin" }
  }

  // It generates a transfer coin transaction with a custom coin type
  @Test
  fun testTransferCoinTransactionWithCoinType() = runBlocking {
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

    val typeArg =
      (rawTransaction.payload as TransactionPayloadEntryFunction).entryFunction.typeArgs[0]

    if (!typeArg.isStruct()) {
      throw Error("Transaction payload type arg is not a struct")
    }

    expect(true) { (typeArg as TypeTagStruct).type.address.toString() == "0x1" }
    expect(true) { (typeArg as TypeTagStruct).type.moduleName == "my_coin" }
    expect(true) { (typeArg as TypeTagStruct).type.name == "type" }
  }

  @Test
  fun testTransfer() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.DEVNET)))

    val alice = Account.generate()
    val bob = Account.generate()

    aptos.fundAccount(alice.accountAddress, 100_000_000).expect("Failed to fund Alice's account")

    val aliceBalanceBefore =
      aptos.getAccountCoinsData(alice.accountAddress).expect("Failed to get Alice's balance")

    val txn =
      aptos.transferCoinTransaction(
        from = alice.accountAddress,
        to = bob.accountAddress,
        amount = 10U,
      )

    val commitedTransaction = aptos.signAndSubmitTransaction(alice, txn)

    val executedTransaction =
      aptos.waitForTransaction(
        HexInput.fromString(commitedTransaction.expect("Transaction failed").hash)
      )

    val aliceBalanceAfter =
      aptos.getAccountCoinsData(alice.accountAddress).expect("Failed to get Alice's balance")
    val bobBalance =
      aptos.getAccountCoinsData(bob.accountAddress).expect("Failed to get Bob's balance")

    expect(true) {
      aliceBalanceAfter.current_fungible_asset_balances[0].amount?.toUInt()!! <
        aliceBalanceBefore.current_fungible_asset_balances[0].amount?.toUInt()!!
    }
  }
}
