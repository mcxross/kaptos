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
package xyz.mcxross.kaptos.extension

fun Any.longOrNull(): Long? {
  return when (val v = this) {
    is Long -> v
    is Int -> v.toLong()
    is Number -> v.toLong()
    is String -> v.toLongOrNull()
    else -> null
  }
}

fun String.isValidAptosAddress(): Boolean {
  val hexString = this.removePrefix("0x")

  if (hexString.length > 64 || hexString.isEmpty()) {
    return false
  }
  return hexString.matches("^[0-9a-fA-F]+$".toRegex())
}
