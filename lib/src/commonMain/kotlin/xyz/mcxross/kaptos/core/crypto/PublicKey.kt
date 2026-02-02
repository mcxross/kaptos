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
package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.model.HexInput

/**
 * An abstract representation of a public key.
 *
 * Provides a common interface for verifying any signature.
 */
abstract class PublicKey {

  /**
   * Verifies that the private key associated with this public key signed the message with the given
   * signature.
   *
   * @param message The message that was signed
   * @param signature The signature to verify
   */
  abstract fun verifySignature(message: HexInput, signature: Signature): Boolean

  /** Get the raw public key bytes */
  abstract fun toByteArray(): ByteArray

  /** Get the BCS bytes */
  abstract fun toBcs(): ByteArray

  /** Get the public key as a hex string with a 0x prefix e.g. 0x123456... */
  override fun toString(): String =
    "0x${toByteArray().joinToString("") { it.toUByte().toString(16).padStart(2, '0') }}"
}

/**
 * An abstract representation of an account public key.
 *
 * Provides a common interface for deriving an authentication key.
 */
abstract class AccountPublicKey : PublicKey() {
  /** Get the authentication key associated with this public key */
  abstract fun authKey(): AuthenticationKey
}
