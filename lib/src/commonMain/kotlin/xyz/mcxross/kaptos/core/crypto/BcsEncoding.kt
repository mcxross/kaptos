/*
 * Copyright 2026 McXross
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
package xyz.mcxross.kaptos.core.crypto

/** Encodes an unsigned integer using BCS ULEB128 format. */
internal fun encodeUleb128(value: Int): ByteArray {
  require(value >= 0) { "ULEB128 value must be non-negative" }

  var remaining = value
  val output = ArrayList<Byte>(5)

  while (remaining >= 0x80) {
    output.add(((remaining and 0x7F) or 0x80).toByte())
    remaining = remaining ushr 7
  }
  output.add(remaining.toByte())

  return output.toByteArray()
}

/** Encodes BCS `bytes` as `<uleb128 length><raw bytes>`. */
internal fun encodeBcsBytes(bytes: ByteArray): ByteArray = encodeUleb128(bytes.size) + bytes
