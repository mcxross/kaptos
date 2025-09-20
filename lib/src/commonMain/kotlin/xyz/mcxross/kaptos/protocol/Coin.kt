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
package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.InputGenerateTransactionOptions
import xyz.mcxross.kaptos.model.SimpleTransaction
import xyz.mcxross.kaptos.util.APTOS_COIN

/** An interface to handle all coin related operations. */
interface Coin {

  /**
   * Generates a Transaction that can be simulated and/or signed and submitted to the chain.
   *
   * @param from Sender's account address.
   * @param to Recipient's account address.
   * @param amount Amount of coins to transfer.
   * @param coinType Optional Coin type to transfer. Defaults to `0x1::aptos_coin::AptosCoin`
   * @param withFeePayer Optional flag whether transaction is sponsored or not. Defaults to `false`
   * @param options Optional parameters to generate the transaction. These include the max gas
   *   amount, gas unit price, and expiration time. Reasonable defaults are provided.
   * @return [SimpleTransaction] object that can be simulated and/or signed and submitted to the
   *   chain.
   */
  suspend fun transferCoinTransaction(
      from: AccountAddressInput,
      to: AccountAddressInput,
      amount: ULong,
      coinType: String = APTOS_COIN,
      withFeePayer: Boolean = false,
      options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction
}
