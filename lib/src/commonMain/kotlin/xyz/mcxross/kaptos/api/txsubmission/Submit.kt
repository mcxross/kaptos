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
package xyz.mcxross.kaptos.api.txsubmission

import xyz.mcxross.kaptos.internal.submitTransaction
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

class Submit(private val aptosConfig: AptosConfig) {

  /**
   * Submit a simple transaction
   *
   * @param transaction An instance of a raw transaction
   * @param senderAuthenticator optional. The sender account authenticator
   * @param feePayerAuthenticator optional. The fee payer account authenticator if it is a fee payer
   *   transaction
   * @returns PendingTransactionResponse
   */
  suspend fun simple(
    transaction: AnyRawTransaction,
    senderAuthenticator: AccountAuthenticator,
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): Option<PendingTransactionResponse> =
    submitTransaction(
      aptosConfig = aptosConfig,
      InputSubmitTransactionData(transaction, senderAuthenticator, feePayerAuthenticator),
    )
}
