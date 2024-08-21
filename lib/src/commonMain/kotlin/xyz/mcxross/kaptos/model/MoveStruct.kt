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
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.serialize.MoveStringSerializer
import xyz.mcxross.kaptos.serialize.MoveVectorSerializer

/**
 * This class is the Kotlin representation of a Move `vector<T>`, where `T` is a type that
 * represents either a primitive type (`bool`, `u8`, `u64`, ...) or a BCS-serializable struct
 * itself.
 *
 * The `MoveVector` class is a BCS-serializable subclass of `TransactionArgument`, which is a
 * superclass for all types that can be used as arguments in a transaction.
 *
 * The purpose of this class is to facilitate easy construction of BCS-serializable Move `vector<T>`
 * types.
 *
 * @sample
 *
 * ```kotlin
 * val shorts = MoveVector.u16(listOf(1u, 2u, 3u))
 *
 * val bools = MoveVector.bool(listOf(true, false, true))
 *
 * val strings = MoveVector.string(listOf("hello", "world"))
 * ```
 *
 * @param T: a serializable type that represents either a primitive type (`bool`, `u8`, `u64`, ...)
 * @property values: a list of `T` values that the `MoveVector` will contain
 */
@Serializable(with = MoveVectorSerializer::class)
data class MoveVector<T : EntryFunctionArgument>(var values: List<T>) : TransactionArgument() {

  fun serialize(): ByteArray {
    if (values.isEmpty()) {
      return byteArrayOf(0)
    }
    return Bcs.encodeToByteArray(values)
  }

  companion object {
    /**
     * Factory method to generate a MoveVector of U8s from an array of numbers.
     *
     * @returns a `MoveVector<U8>`
     */
    fun u8(value: HexInput): MoveVector<U8> =
      MoveVector(Hex.fromHexInput(value).toByteArray().map { U8(it) })

    fun u8(value: ByteArray): MoveVector<U8> = MoveVector(value.map { U8(it) })

    /**
     * Factory method to generate a MoveVector of U16s from an array of numbers.
     *
     * @sample
     *
     * ```kotlin
     * val vector = MoveVector.u16(listOf(1u, 2u, 3u))
     * ```
     *
     * @params values: an array of `numbers` to convert to U16s
     * @returns a `MoveVector<U16>`
     */
    fun u16(value: List<UShort>): MoveVector<U16> = MoveVector(value.map { U16(it) })

    /**
     * Factory method to generate a MoveVector of U32s from an array of numbers.
     *
     * @sample
     *
     * ```kotlin
     * val vector = MoveVector.u32(listOf(1u, 2u, 3u))
     * ```
     *
     * @params values: an array of `numbers` to convert to U32s
     * @returns a `MoveVector<U32>`
     */
    fun u32(value: List<UInt>): MoveVector<U32> = MoveVector(value.map { U32(it) })

    /**
     * Factory method to generate a MoveVector of U64s from an array of numbers.
     *
     * @sample
     *
     * ```kotlin
     * val vector = MoveVector.u64(listOf(1u, 2u, 3u))
     * ```
     *
     * @params values: an array of `numbers` to convert to U64s
     * @returns a `MoveVector<U64>`
     */
    fun u64(value: List<ULong>): MoveVector<U64> = MoveVector(value.map { U64(it) })

    /**
     * Factory method to generate a MoveVector of boolean from an array of booleans.
     *
     * @sample
     *
     * ```kotlin
     * val vector = MoveVector.bool(listOf(true, false, true))
     * ```
     *
     * @params values: an array of `booleans` to convert to Booleans
     * @returns a `MoveVector<Bool>`
     */
    fun bool(value: List<Boolean>): MoveVector<Bool> = MoveVector(value.map { Bool(it) })

    fun string(value: List<String>): MoveVector<MoveString> =
      MoveVector(value.map { MoveString(it) })
  }
}

@Serializable(with = MoveStringSerializer::class)
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
