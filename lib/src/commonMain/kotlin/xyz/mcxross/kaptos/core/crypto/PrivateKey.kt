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

import xyz.mcxross.kaptos.model.HexInput

/**
 * An interface of a private key. It is associated to a signature scheme and provides signing
 * capabilities.
 */
interface PrivateKey {

  /**
   * Sign the given message with the private key.
   *
   * @param message in [HexInput] format
   */
  fun sign(message: HexInput): Signature

  /** Derive the public key associated with the private key */
  fun publicKey(): PublicKey

  /** Get the private key in bytes. */
  fun toByteArray(): ByteArray
}
