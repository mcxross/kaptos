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
package xyz.mcxross.kaptos.model

import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.core.Hex

@Serializable
data class MoveVector<T : EntryFunctionArgument>(var values: List<T>) : TransactionArgument() {

  companion object {
    /**
     * Factory method to generate a MoveVector of U8s from an array of numbers.
     *
     * @returns a `MoveVector<U8>`
     */
    fun u8(value: HexInput): MoveVector<U8> =
      MoveVector(Hex.fromHexInput(value).toByteArray().map { U8(it) })
  }
}

@Serializable
data class MoveString(val value: String) : TransactionArgument() {
  override fun toString(): String {
    return value
  }
}

@Serializable
data class MoveOption<T : EntryFunctionArgument>(val value: T?) : TransactionArgument() {
  fun unwrap(): T {
    return value ?: throw IllegalArgumentException("Option is empty")
  }
}
