/*
 * Copyright 2025 McXross
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
import kotlin.test.fail
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.util.runBlocking
import xyz.mcxross.kaptos.util.toAccountAddress

class ObjectTest {
  @Test
  fun `it fetches an object data`() = runBlocking {
    val aptos = Aptos()

    val resolution =
      aptos.getObjectDataByObjectAddress(
        objectAddress =
          "0x000000000000000000000000000000000000000000000000000000000000000a".toAccountAddress()
      )

    when (resolution) {
      is Result.Ok -> {
        assertEquals(
          "0x000000000000000000000000000000000000000000000000000000000000000a",
          resolution.value?.object_address ?: "",
        )
      }
      is Result.Err -> fail("Expected Ok but got Err: ${resolution.error}")
    }
  }
}
