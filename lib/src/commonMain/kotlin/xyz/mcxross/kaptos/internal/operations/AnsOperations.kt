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
package xyz.mcxross.kaptos.internal.operations

import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.getExpiration
import xyz.mcxross.kaptos.internal.getOwnerAddress
import xyz.mcxross.kaptos.internal.getPrimaryName
import xyz.mcxross.kaptos.internal.getTargetAddress
import xyz.mcxross.kaptos.internal.setTargetAddress
import xyz.mcxross.kaptos.model.*

internal class AnsOperations(val config: TransportConfig) {

  suspend fun getOwnerAddress(name: String): Result<AccountAddress, AptosSdkError> =
    getOwnerAddress(config, name)

  suspend fun getExpiration(name: String): Result<Long, AptosSdkError> = getExpiration(config, name)

  suspend fun getTargetAddress(name: String): Result<AccountAddress, AptosSdkError> =
    getTargetAddress(config, name)

  suspend fun setTargetAddress(
    sender: AccountAddress,
    name: String,
    address: AccountAddressInput,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple = setTargetAddress(config, sender, name, address, options)

  suspend fun getPrimaryName(address: AccountAddressInput): Result<String, AptosSdkError> =
    getPrimaryName(config, address)
}
