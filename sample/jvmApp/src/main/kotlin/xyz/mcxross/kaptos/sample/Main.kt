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
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Result

fun main() = runBlocking {
  val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))

  /* val res = aptos.getLedgerInfo()
   */
  /* aptos.view<List<MoveValue.MoveUint64Type>>(
    InputViewFunctionData(
      function = "0x1::account::get_sequence_number",
      typeArguments = emptyList(),
      functionArguments = listOf(),
    )
  )*/
  /*

  when (res) {
    is Result.Ok -> println("Response: ${res.value}")
    is Result.Err -> println("Error: ${res.error}")
  }*/

  val result = aptos.getChainTopUserTransactions(10)

  when (result) {
    is Result.Ok -> {
      println(result.value)
    }
    is Result.Err -> {
      println(result.error.message)
    }
  }
}
