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

import xyz.mcxross.kaptos.internal.generateTransaction
import xyz.mcxross.kaptos.model.*

/** A class to handle all `Build` transaction operations */
class Build(val config: AptosConfig) {

  /**
   * Build a simple transaction
   *
   * @param sender The sender account address
   * @param data The transaction data
   * @param options optional. Optional transaction configurations
   * @param withFeePayer optional. Whether there is a fee payer for the transaction
   * @returns [SimpleTransaction]
   */
  suspend fun simple(
    sender: AccountAddressInput,
    data: InputGenerateTransactionPayloadData,
    options: InputGenerateTransactionOptions? = null,
    withFeePayer: Boolean = false,
  ): SimpleTransaction {
    val singleSignerRawTransactionData =
      InputGenerateSingleSignerRawTransactionData(
        sender = sender,
        data = data,
        options = options,
        withFeePayer = withFeePayer,
        secondarySignerAddresses = null,
      )
    return generateTransaction(config, singleSignerRawTransactionData) as SimpleTransaction
  }
}
