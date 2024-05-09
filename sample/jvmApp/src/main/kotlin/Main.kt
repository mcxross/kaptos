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

package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.util.toAccountAddress

fun main() {
  val aptos = Aptos()
  runBlocking {
    val modules = aptos.fundAccount(HexInput("0x088698359f12ef2b19ba3bda04e129173d0672b5de8d77ce9e8eb0a149c23f04"), 5000_000_000)
    println(modules)
  }
}
