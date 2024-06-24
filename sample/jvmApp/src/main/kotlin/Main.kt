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
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network

suspend fun main() = runBlocking {
  val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))
  val resolution =
    aptos.getPrimaryName(
      AccountAddress.fromString(
        "0xc67545d6f3d36ed01efc9b28cbfd0c1ae326d5d262dd077a29539bcee0edce9e"
      )
    )
  println(resolution)
  // val a = Bcs.decodeFromByteArray<String>(byteArrayOf(5,4,103,114,101,103))

  // println(a)
}

// 134,126,209,246,191,145,97,113,177,222,62,233,40,73,184,151,139,125,27,158,10,140,201,130,163,209,157,83,93,253,156,12,6,114,111,117,116,101,114,14,103,101,116,95,111,119,110,101,114,95,97,100,100,114,0,2,5,4,103,114,101,103,1,0
// 134,126,209,246,191,145,97,113,177,222,62,233,40,73,184,151,139,125,27,158,10,140,201,130,163,209,157,83,93,253,156,12,6,114,111,117,116,101,114,14,103,101,116,95,111,119,110,101,114,95,97,100,100,114,0,2,4,103,114,101,103,0]
