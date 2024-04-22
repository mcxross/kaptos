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

import kotlin.test.Test
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.getAccountResource
import xyz.mcxross.kaptos.util.runBlocking

class AccountTest {
  @Test
  fun testGetAccountInfo() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountInfo(HexInput("0x1"))) {
        is Option.Some -> {
          assertTrue(response.value.sequenceNumber == "0", "Sequence number should be 0")
          assertTrue(
            response.value.authenticationKey ==
              "0x0000000000000000000000000000000000000000000000000000000000000001",
            "Authentication key should be 0x0000000000000000000000000000000000000000000000000000000000000001",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountModules() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountModules(HexInput("0x1"))) {
        is Option.Some -> {
          assertTrue(response.value.isNotEmpty(), "Should have 1 module")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountModule() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountModule(HexInput("0x1"), "coin")) {
        is Option.Some -> {
          assertTrue(response.value.bytecode.isNotEmpty(), "Bytecode should not be empty")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountResources() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (val response = aptos.getAccountResources(HexInput("0x1"))) {
        is Option.Some -> {
          assertTrue(response.value.isNotEmpty(), "Should have 1 resource")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetAccountResource() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
      when (
        val response =
          aptos.getAccountResource<AccountResource>(HexInput("0x1"), "0x1::account::Account")
      ) {
        is Option.Some -> {
          assertTrue(
            response.value.data.sequenceNumber?.toInt() == 0,
            "Sequence number should be 0",
          )
          assertTrue(
            response.value.data.authenticationKey ==
              "0x0000000000000000000000000000000000000000000000000000000000000001",
            "Authentication key should be 0x0000000000000000000000000000000000000000000000000000000000000001)",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }
}
