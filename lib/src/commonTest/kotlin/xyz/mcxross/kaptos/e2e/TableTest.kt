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
import kotlin.test.assertTrue
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.TableItemRequest
import xyz.mcxross.kaptos.protocol.getAccountResource
import xyz.mcxross.kaptos.protocol.getTableItem
import xyz.mcxross.kaptos.util.runBlocking

class TableTest {

  @Test
  fun `it fetches table item`() = runBlocking {
    val aptos = Aptos()
    val resource =
      aptos.getAccountResource<SupplyWrapper>(
        accountAddress = HexInput("0x1"),
        resourceName = "0x1::coin::CoinInfo<0x1::aptos_coin::AptosCoin>",
      )

    val (handle, key) = resource.expect("No resource found").data.supply.vec[0].aggregator.vec[0]

    val supply =
      aptos.getTableItem<Long>(
        handle = handle,
        data = TableItemRequest(key_type = "address", value_type = "u128", key = key),
      )

    assertTrue(supply > 0)
  }

}

@Serializable data class SupplyWrapper(val data: Supply)

@Serializable data class Supply(val supply: SupplyData)

@Serializable data class SupplyData(val vec: List<Aggregator>)

@Serializable data class Aggregator(val aggregator: AggregatorData)

@Serializable data class AggregatorData(val vec: List<HandleKey>)

@Serializable data class HandleKey(val handle: String, val key: String)
