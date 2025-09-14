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
import kotlin.test.fail
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.view
import xyz.mcxross.kaptos.util.FUND_AMOUNT
import xyz.mcxross.kaptos.util.runBlocking

class FaucetTest {

  @Test
  fun `it should fund account with faucet and verify balance`() = runBlocking {
    val config = AptosConfig(AptosSettings(network = Network.LOCAL))
    val aptos = Aptos(config)
    val aliceAccount = Account.generate()

    when (val fundResolution = aptos.fundAccount(aliceAccount.accountAddress, FUND_AMOUNT)) {
      is Result.Ok -> {
        val payload =
          InputViewFunctionData(
            function = "0x1::coin::balance",
            typeArguments =
              listOf(TypeTagStruct(type = StructTag.fromString("0x1::aptos_coin::AptosCoin"))),
            functionArguments = listOf(aliceAccount.accountAddress),
          )

        when (val coinStoreResResolution = aptos.view<List<MoveValue.MoveUint64Type>>(payload)) {
          is Result.Ok -> {
            assertEquals(
              FUND_AMOUNT,
              coinStoreResResolution.value.firstOrNull()?.value ?: 0,
              "CoinStore balance should match the funded amount",
            )
          }
          is Result.Err ->
            fail("getAccountResource failed: ${coinStoreResResolution.error.message}")
        }
      }
      is Result.Err -> fail("fundAccount failed: ${fundResolution.error.message}")
    }
  }
}
