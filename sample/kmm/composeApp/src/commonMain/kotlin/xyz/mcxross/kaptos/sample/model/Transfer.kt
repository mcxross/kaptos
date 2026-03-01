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
package xyz.mcxross.kaptos.sample.model

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.HexInput

suspend fun transfer(aptos: Aptos, sender: Account, receiver: Account, amount: Int) {
  val txn =
    aptos.buildSimpleTransaction(sender = sender.accountAddress) {
      function = "0x1::coin::transfer"
      typeArgs("0x1::aptos_coin::AptosCoin")
      args(receiver.accountAddress, (amount.toLong() * 100_000_000).toULong())
    }

  val commitedTransaction = aptos.signAndSubmitTransaction(sender, txn)

  val executedTransaction =
    aptos.waitForTransaction(
      HexInput.fromString(commitedTransaction.expect("Transaction failed").hash)
    )
}
