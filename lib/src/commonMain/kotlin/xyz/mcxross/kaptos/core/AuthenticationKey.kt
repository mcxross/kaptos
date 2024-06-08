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
package xyz.mcxross.kaptos.core

import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput

/**
 * Each account stores an authentication key. Authentication key enables account owners to rotate
 * their private key(s) associated with the account without changing the address that hosts their
 * account.
 *
 * @see [https://aptos.dev/concepts/accounts | Account Basics]
 *
 * Account addresses can be derived from AuthenticationKey
 */
class AuthenticationKey(data: HexInput) {

  private var data: Hex

  init {
    val hex = Hex.fromHexInput(data)
    if (hex.toByteArray().size != LENGTH) {
      throw IllegalArgumentException("Authentication key must be 32 bytes")
    }
    this.data = hex
  }

  override fun toString(): String = data.toString()

  fun toByteArray(): ByteArray = data.toByteArray()

  /**
   * Derives an account address from an AuthenticationKey. Since an AccountAddress is also 32 bytes,
   * the AuthenticationKey bytes are directly translated to an AccountAddress.
   *
   * @return [AccountAddress]
   */
  fun deriveAddress(): AccountAddress {
    return AccountAddress(data.toByteArray())
  }

  companion object {
    /**
     * An authentication key is always a SHA3-256 hash of data, and is always 32 bytes.
     *
     * The data to hash depends on the underlying public key type and the derivation scheme.
     */
    const val LENGTH = 32

    fun fromSchemeAndBytes(scheme: AuthenticationKeyScheme, input: HexInput): AuthenticationKey {
      val inputBytes = Hex.fromHexInput(input).toByteArray()
      val hashInput =
        inputBytes +
          when (scheme) {
            is AuthenticationKeyScheme.Derive -> byteArrayOf(scheme.scheme.value.toByte())
            is AuthenticationKeyScheme.Signing -> byteArrayOf(scheme.scheme.value.toByte())
          }
      val hashDigest: ByteArray = sha3Hash(hashInput)

      return AuthenticationKey(HexInput.fromByteArray(hashDigest))
    }
  }
}
