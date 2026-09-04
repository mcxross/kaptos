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
package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.*

/** Interface for all Aptos Name Service (ANS) operations. */
internal interface Ans {

  /**
   * Retrieves the owner address of a domain or subdomain name.
   *
   * ## Usage
   *
   *
   * @param name The ANS name to query.
   * @return A `Result` which is either `Result.Ok` containing the owner's [AccountAddress], or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getOwnerAddress(name: String): Result<AccountAddress, AptosSdkError>

  /**
   * Retrieves the expiration time of a domain or subdomain name.
   *
   * ## Usage
   *
   *
   * @param name The ANS name to query.
   * @return A `Result` which is either `Result.Ok` containing the expiration timestamp as a `Long`,
   *   or `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getExpiration(name: String): Result<Long, AptosSdkError>

  /**
   * Retrieves the target address a domain or subdomain name points to.
   *
   * Note: The target address can be different from the owner's address.
   *
   * ## Usage
   *
   *
   * @param name The ANS name to query.
   * @return A `Result` which is either `Result.Ok` containing the target [AccountAddress], or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getTargetAddress(name: String): Result<AccountAddress, AptosSdkError>

  /**
   * Builds a transaction to set the target address for a domain or subdomain name.
   *
   * ## Usage
   *
   *
   * @param sender The sender of the transaction, who must be the owner of the name.
   * @param name The ANS name to update.
   * @param address The new target address for the name.
   * @param options Optional configuration for the transaction.
   * @return A [UnsignedTransaction.Simple] object ready to be signed and submitted.
   */
  suspend fun setTargetAddress(
    sender: AccountAddress,
    name: String,
    address: AccountAddressInput,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple

  /**
   * Retrieves the primary name for an account, if one is set.
   *
   * An account can have multiple names that point to it, but only one can be its primary name.
   *
   * ## Usage
   *
   *
   * @param address The account address to query.
   * @return A `Result` which is either `Result.Ok` containing the primary name `String`, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun getPrimaryName(address: AccountAddressInput): Result<String, AptosSdkError>
}
