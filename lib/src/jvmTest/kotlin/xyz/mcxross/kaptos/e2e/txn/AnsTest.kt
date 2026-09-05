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
package xyz.mcxross.kaptos.e2e.txn

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import xyz.mcxross.kaptos.extension.isValidAptosAddress
import xyz.mcxross.kaptos.internal.operations.AnsOperations as AnsApi
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.util.runBlocking

class AnsTest {
  @Test
  fun testGetOwnerAddress() = runBlocking {
    val transportConfig = TransportConfig(AptosSettings(network = Network.MAINNET))
    when (val result = AnsApi(transportConfig).getOwnerAddress("greg.apt")) {
      is Result.Ok -> {
        assertEquals(
          "0xc67545d6f3d36ed01efc9b28cbfd0c1ae326d5d262dd077a29539bcee0edce9e",
          result.value.toString(),
        )
      }
      is Result.Err -> {
        fail(result.error.message)
      }
    }
  }

  @Test
  fun testGetExpiration() = runBlocking {
    val transportConfig = TransportConfig(AptosSettings(network = Network.MAINNET))
    when (val result = AnsApi(transportConfig).getExpiration("greg.apt")) {
      is Result.Ok -> {
        assertTrue(result.value > 0)
      }
      is Result.Err -> {
        fail(result.error.message)
      }
    }
  }

  @Test
  fun testGetTargetAddress() = runBlocking {
    val transportConfig = TransportConfig(AptosSettings(network = Network.MAINNET))
    when (val result = AnsApi(transportConfig).getTargetAddress("greg.apt")) {
      is Result.Ok -> {
        assertTrue(result.value.toString().isValidAptosAddress())
      }
      is Result.Err -> {
        fail(result.error.message)
      }
    }
  }

  @Test
  fun testGetPrimaryName() = runBlocking {
    val transportConfig = TransportConfig(AptosSettings(network = Network.MAINNET))
    when (
      val result =
        AnsApi(transportConfig)
          .getPrimaryName(
            AccountAddress.fromString(
              "0xc67545d6f3d36ed01efc9b28cbfd0c1ae326d5d262dd077a29539bcee0edce9e"
            )
          )
    ) {
      is Result.Ok -> {
        assertEquals("greg", result.value)
      }
      is Result.Err -> {
        fail(result.error.message)
      }
    }
  }

  @Test
  fun `it gets owner address of an invalid name`() = runBlocking {
    val transportConfig = TransportConfig(AptosSettings(network = Network.MAINNET))
    val result = AnsApi(transportConfig).getOwnerAddress("!!!.apt")
    assertTrue(result is Result.Err, "Expected Err for invalid domain")
  }

  @Test
  fun `it gets owner address of a non existent name`() = runBlocking {
    val transportConfig = TransportConfig(AptosSettings(network = Network.MAINNET))
    val result = AnsApi(transportConfig).getOwnerAddress("thisdoesnotexist123.apt")

    assertTrue(result is Result.Err, "Expected Err for non-existent domain")
  }
}
