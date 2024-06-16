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
package xyz.mcxross.kaptos.transaction.authenticatior

import kotlinx.serialization.Serializable
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.model.AccountAuthenticatorVariant
import xyz.mcxross.kaptos.serialize.TransactionAuthenticatorSerializer

@Serializable(with = TransactionAuthenticatorSerializer::class)
data class TransactionAuthenticator(
  val accountAuthenticatorVariant: AccountAuthenticatorVariant,
  val publicKey: Ed25519PublicKey,
  val signature: Ed25519Signature,
) {
  fun toBcs(): ByteArray {
    return Bcs.encodeToByteArray(this)
  }
}
