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

import io.ktor.util.reflect.*
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.model.AccountAuthenticatorVariant

@Serializable
open class AccountAuthenticator {
  fun isEd25519(): Boolean = this.instanceOf(AccountAuthenticatorEd25519::class)
}

@Serializable
data class AccountAuthenticatorEd25519(
  val publicKey: Ed25519PublicKey,
  val signature: Ed25519Signature,
) : AccountAuthenticator()

/**
 * AccountAuthenticatorSingleKey for a single signer
 *
 * @param publicKey AnyPublicKey
 * @param signature AnySignature
 */
data class AccountAuthenticatorSingleKey(
  val variant: AccountAuthenticatorVariant = AccountAuthenticatorVariant.SingleKey,
  val publicKey: AnyPublicKey,
  val signature: AnySignature,
) : AccountAuthenticator()
