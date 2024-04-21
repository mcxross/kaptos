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

interface AccountAddressInput {
    val value: String
}

@Serializable
data class AccountAddress(val data: ByteArray) : AccountAddressInput {

    constructor(hex: String) : this(hex.removePrefix("0x").chunked(2) { 0.toByte() }.toByteArray())

    init {
        if (data.size != LENGTH) {
            throw TODO("AccountAddress data should be exactly 32 bytes long")
        }
    }

    fun isSpecial(): Boolean {
        return data.sliceArray(0 until data.size - 1).all { it == 0.toByte() } && data.last() < 16
    }

    override fun toString(): String = "0x${toStringWithoutPrefix()}"

    fun toStringWithoutPrefix(): String {
        val hex = data.joinToString("") { byte -> "%02x${byte}" }
        return if (isSpecial()) {
            hex.takeLast(1)
        } else {
            hex
        }
    }

    fun toStringLong(): String = "0x${toStringLongWithoutPrefix()}"

    fun toStringLongWithoutPrefix(): String {
        return data.joinToString("") { byte -> "%02x$byte" }
    }

    override val value: String
        get() = toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AccountAddress

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    companion object {
        const val LENGTH: Int = 32
        const val LONG_STRING_LENGTH: Int = 64

        val ZERO: AccountAddress = AccountAddress(ByteArray(LENGTH) { 0 })
        val ONE: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 1 else 0 })
        val TWO: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 2 else 0 })
        val THREE: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 3 else 0 })
        val FOUR: AccountAddress = AccountAddress(ByteArray(LENGTH) { if (it == LENGTH - 1) 4 else 0 })

        fun fromHexString(input: String): AccountAddress {
            if (!input.startsWith("0x")) {
                throw IllegalArgumentException("Hex string must start with a leading 0x.")
            }
            val hex = input.drop(2)
            val byteArray = ByteArray(LENGTH) { idx ->
                val hexIndex = hex.length - 2 * (LENGTH - idx)
                if (hexIndex < 0) 0
                else hex.substring(hexIndex, hexIndex + 2).toInt(16).toByte()
            }
            return AccountAddress(byteArray)
        }
    }
}

data class HexInput(override val value: String) : AccountAddressInput