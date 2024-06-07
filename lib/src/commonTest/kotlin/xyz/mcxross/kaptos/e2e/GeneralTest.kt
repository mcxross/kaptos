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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.queryIndexer
import xyz.mcxross.kaptos.protocol.view
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

  @Serializable data class LedgerInfo(@SerialName("chain_id") val chainId: Int)

  @Serializable data class Data(@SerialName("ledger_infos") val ledgerInfos: List<LedgerInfo>)

  @Serializable data class MyQueryResponse(val data: Data)

  @Test
  fun testGraphqlQuery() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
      val query = GraphqlQuery(query = "query MyQuery {\nledger_infos {\nchain_id\n}\n}")

      when (val response = aptos.queryIndexer<MyQueryResponse>(query)) {
        is Option.Some -> {
          assertEquals(response.value.data.ledgerInfos[0].chainId, 2, "Chain ID should be 2")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testGetChainTopUserTransactions() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.DEVNET)))
      val limit = 10
      when (val response = aptos.getChainTopUserTransactions(limit)) {
        is Option.Some -> {
          assertEquals(
            response.value.user_transactions.size,
            limit,
            "Should return 10 transactions",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testFetchViewFunctionData() {
    runBlocking {
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
        is Option.Some -> {
          assertEquals(response.value[0].value, 4, "Chain ID should be 4")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testFetchViewFunctionWithBool() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val response =
        aptos.view<List<MoveValue.Bool>>(
          InputViewFunctionData(
            function = "0x1::account::exists_at",
            typeArguments = emptyList(),
            functionArguments = listOf(MoveString(AccountAddress.fromString("0x1").toStringLong())),
          )
        )

      when (response) {
        is Option.Some -> {
          assertEquals(response.value[0].value, true, "Should return true")
        }
        is Option.None -> assertTrue(false)
      }

      val response1 =
        aptos.view<List<MoveValue.Bool>>(
          InputViewFunctionData(
            function = "0x1::account::exists_at",
            typeArguments = emptyList(),
            functionArguments =
              listOf(MoveString(AccountAddress.fromString("0x123456").toStringLong())),
          )
        )

      when (response1) {
        is Option.Some -> {
          assertEquals(response1.value[0].value, false, "Should return false")
        }
        is Option.None -> assertTrue(false)
      }
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
            functionArguments = listOf(MoveString(AccountAddress.fromString("0x1").toStringLong())),
          )
        )

      when (response) {
        is Option.Some -> {
          assertEquals(response.value[0].value, 0.toString(), "Should return 0")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testFetchViewFunctionUint256Output() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val response =
        aptos.view<List<MoveValue.MoveUint256Type>>(
          InputViewFunctionData(
            function = "0x1::account::get_sequence_number",
            typeArguments = emptyList(),
            functionArguments = listOf(MoveString(AccountAddress.fromString("0x1").toStringLong())),
          )
        )

      when (response) {
        is Option.Some -> {
          assertEquals(response.value[0].value, 0.toString(), "Should return 0")
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testFetchViewFunctionStringOutput() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val response =
        aptos.view<List<MoveValue.String>>(
          InputViewFunctionData(
            function = "0x1::account::get_authentication_key",
            typeArguments = emptyList(),
            functionArguments = listOf(MoveString(AccountAddress.fromString("0x1").toStringLong())),
          )
        )

      when (response) {
        is Option.Some -> {
          assertEquals(
            response.value[0].value,
            "0x0000000000000000000000000000000000000000000000000000000000000001",
            "Should return 0x1",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testFetchViewFunctionGenericOutput() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      val response =
        aptos.view<List<MoveValue.MoveListType<MoveValue.String>>>(
          InputViewFunctionData(
            function = "0x1::coin::supply",
            typeArguments =
              listOf(TypeTagStruct(type = StructTag.fromString("0x1::aptos_coin::AptosCoin"))),
            functionArguments = emptyList(),
          )
        )

      when (response) {
        is Option.Some -> {
          assertNotEquals(
            response.value[0].value[0].value,
            0.toString(),
            "Should return a non-zero value",
          )
        }
        is Option.None -> assertTrue(false)
      }
    }
  }

  @Test
  fun testFetchViewFunctionVMFail() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

      assertFailsWith(AptosException::class) {
        aptos.view<List<MoveValue.MoveUint64Type>>(
          InputViewFunctionData(
            function = "0x1::account::get_sequence_number",
            typeArguments = emptyList(),
            functionArguments =
              listOf(MoveString(AccountAddress.fromString("0x123456").toStringLong())),
          )
        )
      }
    }
  }
}
