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

import xyz.mcxross.kaptos.core.Hex

/**
 * An abstract representation of a crypto signature, associated to a specific signature scheme e.g.
 * Ed25519 or Secp256k1
 *
 * This is the product of signing a message directly from a PrivateKey and can be verified against a
 * CryptoPublicKey.
 */
abstract class Signature {

  /** Get the raw signature bytes */
  abstract fun toByteArray(): ByteArray

  abstract fun toBcs(): ByteArray

  /** Get the signature as a hex string with a 0x prefix e.g. 0x123456... */
  override fun toString(): String = Hex.fromHexInput(toByteArray()).toString()
}
