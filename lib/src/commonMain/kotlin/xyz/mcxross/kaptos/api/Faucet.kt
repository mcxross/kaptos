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
import xyz.mcxross.kaptos.internal.fundAccount
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.Faucet

/**
 * Faucet API namespace. This class provides functionality to create and fund accounts.
 *
 * @property config AptosConfig object for configuration
 */
class Faucet(private val config: AptosConfig) : Faucet {

  /**
   * Requests test coins from the network's Faucet for a given account.
   *
   * If the account does not exist, this function will create it and then fund it with the specified
   * amount of coins.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * // Request 1 APT (100,000,000 Octas)
   * val resolution = aptos.fundAccount(address, 100_000_000)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val transaction = resolution.value
   * println("Successfully funded account. Transaction: $transaction")
   * }
   * is Result.Err -> {
   * println("Error funding account: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param accountAddress The address of the account to fund.
   * @param amount The amount of coins (in Octas) to fund the account with.
   * @param options Optional configuration for waiting for the funding transaction to be processed.
   * @return A `Result` which is either `Result.Ok` containing the final [TransactionResponse], or
   *   `Result.Err` containing an [AptosSdkError].
   */
  override suspend fun fundAccount(
    accountAddress: AccountAddressInput,
    amount: Long,
    options: WaitForTransactionOptions,
  ): Result<TransactionResponse, AptosSdkError> =
    fundAccount(config, accountAddress, amount, options)
}
