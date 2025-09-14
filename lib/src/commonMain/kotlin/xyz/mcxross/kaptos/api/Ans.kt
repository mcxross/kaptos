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
package xyz.mcxross.kaptos.api

import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.getExpiration
import xyz.mcxross.kaptos.internal.getOwnerAddress
import xyz.mcxross.kaptos.internal.getPrimaryName
import xyz.mcxross.kaptos.internal.getTargetAddress
import xyz.mcxross.kaptos.internal.setTargetAddress
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.Ans

/** A class to handle all Aptos Name Service (ANS) operations. */
class Ans(val config: AptosConfig) : Ans {

  override suspend fun getOwnerAddress(name: String): Result<AccountAddress, AptosSdkError> =
    getOwnerAddress(config, name)

  override suspend fun getExpiration(name: String): Result<Long, AptosSdkError> =
    getExpiration(config, name)

  override suspend fun getTargetAddress(name: String): Result<AccountAddress, AptosSdkError> =
    getTargetAddress(config, name)

  override suspend fun setTargetAddress(
    sender: AccountAddress,
    name: String,
    address: AccountAddressInput,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction = setTargetAddress(config, sender, name, address, options)

  override suspend fun getPrimaryName(address: AccountAddressInput): Result<String, AptosSdkError> =
    getPrimaryName(config, address)
}
