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
package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.model.GenesisTransactionResponse
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.TransactionResponseType
import xyz.mcxross.kaptos.model.ValidatorTransactionResponse

class TransactionResponseSerializationTest {
  private val json = Json { ignoreUnknownKeys = true }

  @Test
  fun `deserializes genesis transactions`() {
    val response =
      json.decodeFromString<TransactionResponse>(
        systemTransactionJson(
          type = "genesis_transaction",
          tail = "\"payload\":{},\"events\":[]",
        )
      )

    val genesis = assertIs<GenesisTransactionResponse>(response)
    assertEquals(TransactionResponseType.GENESIS, genesis.type)
  }

  @Test
  fun `deserializes validator transactions`() {
    val response =
      json.decodeFromString<TransactionResponse>(
        systemTransactionJson(
          type = "validator_transaction",
          tail =
            "\"events\":[],\"timestamp\":\"1\"," +
              "\"validator_transaction_type\":\"dkg_result\"," +
              "\"dkg_transcript\":{\"epoch\":\"1\"}",
        )
      )

    val validator = assertIs<ValidatorTransactionResponse>(response)
    assertEquals(TransactionResponseType.VALIDATOR, validator.type)
    assertEquals("dkg_result", validator.validatorTransactionType)
  }

  private fun systemTransactionJson(type: String, tail: String): String =
    """{
      "type":"$type",
      "version":"0",
      "hash":"0x01",
      "state_change_hash":"0x02",
      "event_root_hash":"0x03",
      "state_checkpoint_hash":null,
      "gas_used":"0",
      "success":true,
      "vm_status":"Executed successfully",
      "accumulator_root_hash":"0x04",
      $tail
    }"""
}
