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

package xyz.mcxross.kaptos.util

import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlin.String
import xyz.mcxross.kaptos.model.HexInput

fun String.toAccountAddress(): HexInput {
  return HexInput(this)
}

val HEX_ARRAY: ByteArray = "0123456789abcdef".toByteArray(Charsets.UTF_8)

fun bytesToHex(bytes: ByteArray): String {
  val hexChars = ByteArray(bytes.size * 2)
  for (j in bytes.indices) {
    val v = bytes[j].toInt() and 0xFF
    hexChars[j * 2] = HEX_ARRAY[v ushr 4]
    hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
  }
  return String(hexChars, charset = Charsets.UTF_8)
}
