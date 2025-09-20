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
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TableItemRequest
import xyz.mcxross.kaptos.model.types.bigintFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.model.types.tableItemsFilter
import xyz.mcxross.kaptos.model.types.tableMetadatasFilter
import xyz.mcxross.kaptos.protocol.getAccountResource
import xyz.mcxross.kaptos.protocol.getTableItem
import xyz.mcxross.kaptos.util.runBlocking

class TableTest {

  @Test
  fun `it fetches table item`() = runBlocking {
    val aptos = Aptos()
    val resResource =
      aptos.getAccountResource<SupplyWrapper>(
        accountAddress = HexInput("0x1"),
        resourceName = "0x1::coin::CoinInfo<0x1::aptos_coin::AptosCoin>",
      )

    when (resResource) {
      is Result.Ok -> {
        val (handle, key) = resResource.value.data.supply.vec.first().aggregator.vec.first()
        val resItem =
          aptos.getTableItem<Long>(
            handle = handle,
            data = TableItemRequest(key_type = "address", value_type = "u128", key = key),
          )
        when (resItem) {
          is Result.Ok -> {
            assertTrue(resItem.value > 0, "Supply value should be greater than zero")
          }

          is Result.Err -> fail("Expected Ok but got Err: ${resItem.error}")
        }
      }

      is Result.Err -> fail("Expected Ok but got Err: ${resResource.error}")
    }
  }

  @Test
  fun `it fetches table items data`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))
    val resResource =
      aptos.getAccountResource<SupplyWrapper>(
        accountAddress = HexInput("0x1"),
        resourceName = "0x1::coin::CoinInfo<0x1::aptos_coin::AptosCoin>",
      )

    when (resResource) {
      is Result.Ok -> {

        val (handle, _) = resResource.value.data.supply.vec.first().aggregator.vec.first()

        val resItem =
          aptos.getTableItemsData(
            filter =
              tableItemsFilter {
                tableHandle = stringFilter { eq = handle }
                transactionVersion = bigintFilter { eq = 0 }
              }
          )
        when (resItem) {
          is Result.Ok -> {
            assertEquals(
              "0x619dc29a0aac8fa146714058e8dd6d2d0f3bdf5f6331907bf91f3acd81e6935",
              resItem.value?.table_items?.first()?.decoded_key.toString(),
              "Decoded key should match the expected value",
            )
            assertEquals(
              "0x0619dc29a0aac8fa146714058e8dd6d2d0f3bdf5f6331907bf91f3acd81e6935",
              resItem.value?.table_items?.first()?.key.toString(),
              "Key should match the expected value",
            )
          }
          is Result.Err -> fail("Expected Ok but got Err: ${resItem.error}")
        }
      }
      is Result.Err -> fail("Expected Ok but got Err: ${resResource.error}")
    }
  }

  @Test
  fun `it fetches table items metadata data`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.MAINNET)))
    val resResource =
      aptos.getAccountResource<SupplyWrapper>(
        accountAddress = HexInput("0x1"),
        resourceName = "0x1::coin::CoinInfo<0x1::aptos_coin::AptosCoin>",
      )

    when (resResource) {
      is Result.Ok -> {

        val (handle, _) = resResource.value.data.supply.vec.first().aggregator.vec.first()

        val resItem =
          aptos.getTableItemsMetadata(
            filter = tableMetadatasFilter { this.handle = stringFilter { eq = handle } }
          )
        when (resItem) {
          is Result.Ok -> {
            assertEquals(
              "u128",
              resItem.value?.table_metadatas?.first()?.value_type.toString(),
              "Value type should be 'u128'",
            )
            assertEquals(
              "address",
              resItem.value?.table_metadatas?.first()?.key_type.toString(),
              "Key type should be 'address'",
            )
          }
          is Result.Err -> fail("Expected Ok but got Err: ${resItem.error}")
        }
      }
      is Result.Err -> fail("Expected Ok but got Err: ${resResource.error}")
    }
  }
}

@Serializable data class SupplyWrapper(val data: Supply)

@Serializable data class Supply(val supply: SupplyData)

@Serializable data class SupplyData(val vec: List<Aggregator>)

@Serializable data class Aggregator(val aggregator: AggregatorData)

@Serializable data class AggregatorData(val vec: List<HandleKey>)

@Serializable data class HandleKey(val handle: String, val key: String)
