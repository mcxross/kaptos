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
import xyz.mcxross.kaptos.model.*

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

fun getFunctionParts(function: MoveFunctionId): Triple<String, String, String> {
  val parts = function.split("::")

  if (parts.size != 3) throw IllegalArgumentException("Invalid function id")

  return Triple(parts[0], parts[1], parts[2])
}

/**
 * Finds first non-signer arg.
 *
 * A function is often defined with a `signer` or `&signer` arguments at the start, which are filled
 * in by signatures, and not by the caller.
 *
 * @param functionAbi
 */
fun findFirstNonSignerArg(functionAbi: MoveFunction): Int {
  val index = functionAbi.params.indexOfFirst { it != "signer" && it != "&signer" }

  if (index < 0) {
    return functionAbi.params.size
  }
  return index
}

// A really simple function to check if a string is a hex string. This
// is currently used during function argument serialization to determine
// if a MoveString should be serialized as a hex string or a regular string
fun isHex(input: String): Boolean {
  return input.matches(Regex("0x[0-9a-fA-F]+"))
}
