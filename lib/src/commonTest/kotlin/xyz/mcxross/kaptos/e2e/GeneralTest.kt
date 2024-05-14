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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.test.*
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.queryIndexer
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

  @Serializable
  data class LedgerInfo(@SerialName("chain_id") val chainId: Int)

  @Serializable
  data class Data(@SerialName("ledger_infos") val ledgerInfos: List<LedgerInfo>)

  @Serializable
  data class MyQueryResponse(val data: Data)

  @Test
  fun testGraphqlQuery() {
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
      val query =
        GraphqlQuery(
          query = "query MyQuery {\nledger_infos {\nchain_id\n}\n}"
        )

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
}
