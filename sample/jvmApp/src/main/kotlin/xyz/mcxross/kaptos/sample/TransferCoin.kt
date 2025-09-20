/*
 * Copyright 2025 McXross
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
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.util.runBlocking

fun main() = runBlocking {
  val aptos = Aptos(AptosConfig(AptosSettings(Network.LOCAL)))

  val alice = Account.generate()
  val bob = Account.generate()

  println("=== Generated accounts ===")
  println("Alice: ${alice.accountAddress}")
  println("Bob: ${bob.accountAddress}")
  println("===========================")

  aptos.fundAccount(alice.accountAddress, 100_000_000)
  aptos.fundAccount(bob.accountAddress, 100_000_000)

  println("=== Funded accounts ===")
  println("Alice: ${aptos.getAccountAPTAmount(alice.accountAddress)}")
  println("Bob: ${aptos.getAccountAPTAmount(alice.accountAddress)}")

  val transfer =
    aptos.transferCoinTransaction(alice.accountAddress, bob.accountAddress, 1_000_000UL)

  val signedTransfer = aptos.sign(alice, transfer)

  val submittedTransfer = aptos.submitTransaction.simple(transfer, signedTransfer)

  val res =
    aptos.waitForTransaction(HexInput(submittedTransfer.expect("Failed to submit transfer").hash))

  println("=== Transfer ===")
  println(res)
  println("Alice: ${aptos.getAccountAPTAmount(alice.accountAddress)}")
  println("Bob: ${aptos.getAccountAPTAmount(bob.accountAddress)}")
  println("================")
}
