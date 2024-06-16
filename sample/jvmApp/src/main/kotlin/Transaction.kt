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
import xyz.mcxross.kaptos.core.account.Account
import xyz.mcxross.kaptos.extension.asAccountAddress
import xyz.mcxross.kaptos.extension.asPrivateKey
import xyz.mcxross.kaptos.extension.toStructTag
import xyz.mcxross.kaptos.model.*

const val FUNDING_AMOUNT = 1_000_000_000L
const val SEND_AMOUNT = 100_000_000UL

suspend fun transfer() {
  val aptos = Aptos()

  // Let's create a private key from a hex string
  val alicePrivateKey =
    "0x1893cc5626444a03978480882de32faf9a820afb31b8075159dda107db1b470d".asPrivateKey()

  // And now that we have a private key, let's create an account from it
  val aliceAccount = Account from alicePrivateKey

  // Assuming we have an account, let's check the balance
  val aliceInitialBalance =
    when (val aliceInitialBalance = aptos.getAccountAPTAmount(aliceAccount.accountAddress)) {
      is Option.None -> throw IllegalStateException("Alice's account does not exist")
      is Option.Some -> aliceInitialBalance.unwrap()
    }

  println("Alice's initial balance: $aliceInitialBalance")
  println(
    "Bob's initial balance: ${aptos.getAccountAPTAmount("0x088698359f12ef2b19ba3bda04e129173d0672b5de8d77ce9e8eb0a149c23f04".asAccountAddress()).unwrap()}"
  )
  println("=============================================")

  // Yes, we have an account, but let's see if we need to fund it
  if (aliceInitialBalance < SEND_AMOUNT.toLong() + 1_000) {
    aptos.fundAccount(aliceAccount.accountAddress, FUNDING_AMOUNT)
    println(
      "Alice's new balance after funding: ${aptos.getAccountAPTAmount(aliceAccount.accountAddress)}"
    )
  }

  val bobAccountAddress =
    "0x088698359f12ef2b19ba3bda04e129173d0672b5de8d77ce9e8eb0a149c23f04".asAccountAddress()

  println(
    "Building transaction to send ${SEND_AMOUNT / 100_000_000u} APT to Bob: $bobAccountAddress"
  )

  val txn =
    aptos.buildTransaction.simple(
      sender = aliceAccount.accountAddress,
      data =
        inputEntryFunctionData {
          function = "0x1::coin::transfer"
          typeArguments = typeArguments {
            +TypeTagStruct(type = "0x1::aptos_coin::AptosCoin".toStructTag())
          }
          functionArguments = functionArguments {
            +MoveString(bobAccountAddress.value)
            +U64(SEND_AMOUNT)
          }
        },
    )

  // Sign and submit the transaction
  val pendingTransactionResponse = aptos.signAndSubmitTransaction(aliceAccount, txn)

  val response =
    aptos.waitForTransaction(HexInput.fromString(pendingTransactionResponse.unwrap().hash))

  println("Transaction wait response: $response")
  println("=============================================")
  println("Alice's new balance: ${aptos.getAccountAPTAmount(aliceAccount.accountAddress).unwrap()}")
  println("Bob's new balance: ${aptos.getAccountAPTAmount(bobAccountAddress).unwrap()}")
}
