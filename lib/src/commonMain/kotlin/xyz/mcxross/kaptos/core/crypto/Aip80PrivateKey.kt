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

import kotlin.jvm.JvmInline
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.model.HexInput

/** The private-key algorithms with an AIP-80 textual representation. */
enum class PrivateKeyType(internal val aip80Prefix: String) {
  Ed25519("ed25519-priv-"),
  Secp256k1("secp256k1-priv-"),
  Secp256r1("secp256r1-priv-"),
}

/**
 * A validated AIP-80 private-key string.
 *
 * Keeping this distinct from an ordinary [String] makes secret-bearing APIs explicit and prevents
 * an unprefixed legacy hex value from being accepted accidentally.
 */
@JvmInline
value class Aip80PrivateKey private constructor(val value: String) {
  val type: PrivateKeyType
    get() = PrivateKeyType.entries.first { value.startsWith(it.aip80Prefix) }

  override fun toString(): String = value

  internal fun bytes(expectedType: PrivateKeyType): ByteArray {
    require(type == expectedType) {
      "Expected an ${expectedType.name} private key, but found ${type.name}"
    }
    return decodeHex(value.removePrefix(type.aip80Prefix))
  }

  companion object {
    /** Parse and validate a canonical AIP-80 private-key string. */
    fun parse(value: String): Aip80PrivateKey {
      val type =
        PrivateKeyType.entries.firstOrNull { value.startsWith(it.aip80Prefix) }
          ?: throw IllegalArgumentException("Unsupported or missing AIP-80 private-key prefix")
      val payload = value.removePrefix(type.aip80Prefix)
      require(payload.startsWith("0x")) { "AIP-80 private-key payload must start with 0x" }
      require(decodeHex(payload).size == PRIVATE_KEY_LENGTH) {
        "AIP-80 private key must contain $PRIVATE_KEY_LENGTH bytes"
      }
      return Aip80PrivateKey(type.aip80Prefix + canonicalHex(payload))
    }

    internal fun fromBytes(type: PrivateKeyType, bytes: ByteArray): Aip80PrivateKey {
      require(bytes.size == PRIVATE_KEY_LENGTH) {
        "AIP-80 private key must contain $PRIVATE_KEY_LENGTH bytes"
      }
      return Aip80PrivateKey(type.aip80Prefix + Hex(bytes).toString())
    }

    private const val PRIVATE_KEY_LENGTH = 32
  }
}

internal fun decodeLegacyPrivateKeyHex(value: String): ByteArray = decodeHex(value)

private fun canonicalHex(value: String): String =
  Hex.fromHexInput(HexInput.fromString(value)).toString()

private fun decodeHex(value: String): ByteArray =
  try {
    Hex.fromHexInput(HexInput.fromString(value)).toByteArray()
  } catch (error: Throwable) {
    throw IllegalArgumentException("Invalid private-key hex", error)
  }
