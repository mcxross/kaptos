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
package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.runBlocking

const val FUNDING_AMOUNT = 100_000_000L
const val SEND_AMOUNT_APT = 0.5f
const val UNIT_CONVERSION = 100_000_000
const val SEND_AMOUNT_UNITS = (SEND_AMOUNT_APT * UNIT_CONVERSION)
const val SEND_AMOUNT = 1_000_000UL

/**
 * This example demonstrates how to transfer APT from one account to another.
 *
 * Each run generates and creates new accounts on-chain using faucet funding. After funding, the APT
 * balance of each account is printed; if funding fails, an error is thrown.
 *
 * Next, a transaction is constructed to send 0.5 APT from Alice to Bob. The transaction is then
 * signed and submitted using the one-step `signAndSubmitTransaction` method. We wait for the
 * transaction to complete and print the updated balances of Alice and Bob. If the transaction
 * fails, an error is thrown.
 */
fun main() = runBlocking {
  val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))

  println("Generating Alice and Bob's accounts")

  val alice = Account.generate()
  val bob = Account.generate()

  aptos.fundAccount(alice.accountAddress, FUNDING_AMOUNT).expect("Failed to fund Alice's account")
  aptos.fundAccount(bob.accountAddress, FUNDING_AMOUNT).expect("Failed to fund Bob's account")

  println("Created accounts on chain")
  println("Alice's balance: ${aptos.getAccountAPTAmount(alice.accountAddress)}")
  println("Bob's balance: ${aptos.getAccountAPTAmount(bob.accountAddress)}")
  println("=============================================")
  println(
    "Building transaction to send ${SEND_AMOUNT / 100_000_000u} APT to Bob: ${bob.accountAddress}"
  )

  val txn =
    aptos.buildTransaction.simple(
      sender = alice.accountAddress,
      data =
        entryFunctionData {
          function = "0x1::coin::transfer"
          typeArguments = typeArguments { +TypeTagStruct("0x1::aptos_coin::AptosCoin") }
          functionArguments = functionArguments {
            +bob.accountAddress
            +U64(SEND_AMOUNT_UNITS.toULong())
          }
        },
    )

  // Sign and submit the transaction
  val commitedTransaction = aptos.signAndSubmitTransaction(alice, txn)

  val executedTransaction =
    aptos.waitForTransaction(
      HexInput.fromString(commitedTransaction.expect("Transaction failed").hash)
    )

  println(
    "Transaction wait response: $executedTransaction\n============================================="
  )

  val aliceNewBalance =
    aptos.getAccountAPTAmount(alice.accountAddress).expect("Alice's account does not exist")
  val bobNewBalance =
    aptos.getAccountAPTAmount(bob.accountAddress).expect("Bob's account does not exist")

  println("Alice's new balance: $aliceNewBalance")
  println("Bob's new balance: $bobNewBalance")
}
