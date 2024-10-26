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

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.model.Option.Some

const val ALICE_INITIAL_BALANCE = 100_000_000L
const val SPONSOR_INITIAL_BALANCE = 100_000_000L
const val BOB_INITIAL_BALANCE = 0u
const val TRANSFER_AMOUNT = 10u

fun main() = runBlocking {
  val aptos = Aptos()

  println("Creating accounts: Alice, Bob, and Sponsor.")

  val alice = Account.generate()
  val bob = Account.generate()
  val sponsor = Account.generate()

  println("=== Addresses ===")
  println("Alice's address: ${alice.accountAddress}")
  println("Bob's address: ${bob.accountAddress}")
  println("Sponsor's address: ${sponsor.accountAddress}")

  aptos.fundAccount(alice.accountAddress, ALICE_INITIAL_BALANCE)
  aptos.fundAccount(sponsor.accountAddress, SPONSOR_INITIAL_BALANCE)

  println("\n=== Initial Balances ===")
  val aliceBalanceBefore = aptos.getAccountCoinsData(alice.accountAddress)
  val sponsorBalanceBefore = aptos.getAccountCoinsData(sponsor.accountAddress)

  when (aliceBalanceBefore) {
    is Some -> println("Alice's balance: $aliceBalanceBefore")
    is Option.None -> throw Error("Alice's sponsor: None")
  }

  println("Bob's balance: $BOB_INITIAL_BALANCE")

  when (sponsorBalanceBefore) {
    is Some -> println("Sponsor's balance: $sponsorBalanceBefore")
    is Option.None -> throw Error("Sponsor's balance: None")
  }

  println("\n=== Submitting Transaction ===")

  val transaction =
    aptos.transferCoinTransaction(
      from = alice.accountAddress,
      to = bob.accountAddress,
      amount = TRANSFER_AMOUNT.toULong(),
      withFeePayer = true,
    )

  val senderSignature = aptos.sign(alice, transaction)

  val sponsorSignature = aptos.signAsFeePayer(sponsor, transaction)

  val committedTxn = aptos.submitTransaction.simple(transaction, senderSignature, sponsorSignature)
  println("Transaction submitted with hash: ${committedTxn.expect("").hash}")

  aptos.waitForTransaction(HexInput.fromString(committedTxn.expect("").hash))

  println("\n=== Balances after transfer ===")
  val aliceBalanceAfter =
    aptos
      .getAccountCoinsData(alice.accountAddress)
      .expect("Couldn't retrieve Alice's balance after transfer")
  val bobBalanceAfter =
    aptos
      .getAccountCoinsData(bob.accountAddress)
      .expect("Couldn't retrieve Bob's balance after transfer")
  val sponsorBalanceAfter =
    aptos
      .getAccountCoinsData(sponsor.accountAddress)
      .expect("Couldn't retrieve Sponsor's balanceAfter transfer")

  println("Alice's final balance: $aliceBalanceAfter")
  println("Bob's balance: $bobBalanceAfter")
  println("Sponsor's final balance: $sponsorBalanceAfter")
}
