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

import xyz.mcxross.kaptos.core.crypto.PrivateKey

/**
 * Input for creating an account from a private key.
 *
 * This is a wrapper around the private key, and optionally an address to associate with the
 * account. We can use this when we want to pass a single object to a function that needs the
 * private key, address, and legacy flag. For example, with infix functions.
 *
 * @param privateKey the private key to create the account from
 * @param address the address to associate with the account
 * @param legacy whether to use the legacy address format
 */
data class PrivateKeyInput(
  val privateKey: PrivateKey,
  val address: AccountAddressInput? = null,
  val legacy: Boolean = true,
)
