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
   * This creates an account if it does not exist and mints the specified amount of coins into that
   * account
   *
   * @param accountAddress Address of the account to fund
   * @param amount Amount of tokens to fund the account with
   * @param options Configuration options for waitForTransaction
   * @returns Transaction hash of the transaction that funded the account
   */
  override suspend fun fundAccount(
    accountAddress: AccountAddressInput,
    amount: Long,
    options: WaitForTransactionOptions,
  ): Option<TransactionResponse> = fundAccount(config, accountAddress, amount, options)
}
