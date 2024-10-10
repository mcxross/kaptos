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
import kotlin.test.assertTrue
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.protocol.getAccountResource
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.runBlocking

@Serializable data class Data(val data: Coin)

@Serializable data class Coin(val coin: CoinValue)

@Serializable data class CoinValue(val value: String)

class FaucetTest {

  @Test
  fun `should fund account with faucet and verify balance`() = runBlocking {
    val aptosClient = Aptos()
    val aliceAccount = Account.generate()

    aptosClient.fundAccount(aliceAccount.accountAddress, FUND_AMOUNT)

    val coinStoreResource =
      aptosClient
        .getAccountResource<Data>(
          aliceAccount.accountAddress,
          "0x1::coin::CoinStore<0x1::aptos_coin::AptosCoin>",
        )
        .expect("Failed to retrieve account resource: CoinStore<0x1::aptos_coin::AptosCoin>")

    assertTrue(FUND_AMOUNT.toString() == coinStoreResource.data.coin.value)
  }
}
