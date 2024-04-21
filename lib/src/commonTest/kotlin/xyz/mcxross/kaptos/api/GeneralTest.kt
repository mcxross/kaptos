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

package xyz.mcxross.kaptos.api

import kotlin.test.*
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.util.runBlocking

class GeneralTest {

  @Test
  fun testGetLedgerInfo() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getLedgerInfo()) {
        is Option.Some -> {
          assertEquals(response.value.chainId, 4, "Chain ID should be 4")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetChainId() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getChainId()) {
        is Option.Some -> {
          assertEquals(response.value, 4, "Chain ID should be 4")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetBlockByBlockHeight() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      val height = 1L
      when (val response = aptos.getBlockByHeight(height)) {
        is Option.Some -> {
          assertEquals(response.value.blockHeight.toLong(), height, "Block version should be 0")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetBlockByVersion() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      val version = 1L
      when (val response = aptos.getBlockByVersion(version)) {
        is Option.Some -> {
          assertEquals(response.value.lastVersion.toLong(), version, "Block version should be 0")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }
}
