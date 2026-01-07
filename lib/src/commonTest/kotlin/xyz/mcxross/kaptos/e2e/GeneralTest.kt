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

import kotlin.test.*
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.view
import xyz.mcxross.kaptos.util.runBlocking

class GeneralTest {

  @Test
  fun `it fetches ledger info`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    when (val response = aptos.getLedgerInfo()) {
      is Result.Ok -> {
        val ledger = response.value
        assertEquals(4, ledger.chainId, "Chain ID should be 4")
        assertTrue(ledger.ledgerVersion.toLong() > 0, "Ledger version should be positive")
        assertTrue(ledger.blockHeight.toLong() >= 0, "Block height should not be negative")
        assertTrue(ledger.epoch.toLong() >= 0, "Epoch should not be negative")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }

  @Test
  fun `it fetches chain id`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    when (val response = aptos.getChainId()) {
      is Result.Ok -> {
        assertEquals(4, response.value, "Chain ID should be 4 on localnet")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }

  @Test
  fun testGetBlockByBlockHeight() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    val height = 1L
    when (val response = aptos.getBlockByHeight(height)) {
      is Result.Ok -> {
        assertEquals(response.value.blockHeight.toLong(), height, "Block version should be 0")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }

  @Test
  fun testGetBlockByVersion() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))
    val version = 3L
    when (val response = aptos.getBlockByVersion(version)) {
      is Result.Ok -> {
        assertEquals(response.value.lastVersion.toLong(), version, "Block version should be 0")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }

  @Test
  fun `it should fetch chain top user transactions`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))
    val limit = 10
    when (val response = aptos.getChainTopUserTransactions(limit)) {
      is Result.Ok -> {
        assertEquals(
          response.value?.user_transactions?.size ?: 0,
          limit,
          "Should return 10 transactions",
        )
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }

  @Test
  fun testFetchViewFunctionData() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

    val response =
      aptos.view<List<MoveValue.MoveUint64Type>>(
        InputViewFunctionData(
          function = "0x1::chain_id::get",
          typeArguments = emptyList(),
          functionArguments = emptyList(),
        )
      )

    when (response) {
      is Result.Ok -> {
        assertEquals(response.value[0].value, 4, "Chain ID should be 4")
      }
      is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
    }
  }

  @Test
  fun testFetchViewFunctionWithBool() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))

      val response =
        aptos.view<List<MoveValue.Bool>>(
          InputViewFunctionData(
            function = "0x1::account::exists_at",
            typeArguments = emptyList(),
            functionArguments = listOf(AccountAddress.fromString("0x1").toLongAddress()),
          )
        )

      when (response) {
        is Result.Ok -> {
          assertEquals(response.value[0].value, true, "Should return true")
        }
        is Result.Err -> fail("Expected Ok but got Err: ${response.error}")
      }

      /*val response1 =
          aptos.view<List<MoveValue.Bool>>(
              InputViewFunctionData(
                  function = "0x1::account::exists_at",
                  typeArguments = emptyList(),
                  functionArguments =
                      listOf(MoveString(AccountAddress.fromString("0x123456").toStringLong())),
              ))

      when (response1) {
        is Result.Ok -> {
          assertEquals(response1.value[0].value, false, "Should return false")
        }
        is Result.Err -> fail("Expected Ok but got Err: ${response1.error}")
      }*/
    }
  }

  @Test
  fun testFetchViewFunctionWithUint128() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val response =
        aptos.view<List<MoveValue.MoveUint128Type>>(
          InputViewFunctionData(
            function = "0x1::account::get_sequence_number",
            typeArguments = emptyList(),
            functionArguments = listOf(AccountAddress.fromString("0x1").toLongAddress()),
          )
        )

    }
  }

  @Test
  fun testFetchViewFunctionUint256Output() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val resolution =
        aptos.view<List<MoveValue.MoveUint256Type>>(
          InputViewFunctionData(
            function = "0x1::account::get_sequence_number",
            typeArguments = emptyList(),
            functionArguments = listOf(AccountAddress.fromString("0x1").toLongAddress()),
          )
        )
    }
  }

  @Test
  fun testFetchViewFunctionStringOutput() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val resolution =
        aptos.view<List<MoveValue.String>>(
          InputViewFunctionData(
            function = "0x1::account::get_authentication_key",
            typeArguments = emptyList(),
            functionArguments = listOf(AccountAddress.fromString("0x1").toLongAddress()),
          )
        )

      when (resolution) {
        is Result.Ok -> {
          assertEquals(
            resolution.value[0].value,
            "0x0000000000000000000000000000000000000000000000000000000000000001",
            "Should return 0x1",
          )
        }
        is Result.Err -> fail("Expected Result.Ok")
      }
    }
  }

  @Test
  fun testFetchViewFunctionGenericOutput() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val resolution =
        aptos.view<List<MoveValue.MoveListType<MoveValue.String>>>(
          InputViewFunctionData(
            function = "0x1::coin::supply",
            typeArguments =
              listOf(TypeTagStruct(type = StructTag.fromString("0x1::aptos_coin::AptosCoin"))),
            functionArguments = emptyList(),
          )
        )

      when (resolution) {
        is Result.Ok -> {
          assertNotEquals(
            resolution.value[0].value[0].value,
            0.toString(),
            "Should return a non-zero value",
          )
        }
        is Result.Err -> fail("Expected Result.Ok")
      }
    }
  }

  @Test
  fun testFetchViewFunctionVMFail() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val resolution =
        aptos.view<List<MoveValue.MoveUint64Type>>(
          InputViewFunctionData(
            function = "0x1::account::get_sequence_number",
            typeArguments = emptyList(),
            functionArguments =
              listOf(MoveString(AccountAddress.fromString("0x123456").toStringLong())),
          )
        )

      when (resolution) {
        is Result.Ok -> {
          fail("")
        }
        is Result.Err -> {}
      }
    }
  }
}
