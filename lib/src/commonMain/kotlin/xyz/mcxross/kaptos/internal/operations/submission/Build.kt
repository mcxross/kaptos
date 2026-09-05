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
package xyz.mcxross.kaptos.internal.operations.submission

import xyz.mcxross.kaptos.internal.generateTransaction
import xyz.mcxross.kaptos.model.*

/** A class to handle all `Build` transaction operations */
internal class Build(val config: TransportConfig) {

  /**
   * Build a simple transaction
   *
   * @param sender The sender account address
   * @param data The transaction data
   * @param options optional. Optional transaction configurations
   * @param withFeePayer optional. Whether there is a fee payer for the transaction
   * @returns [UnsignedTransaction.Simple]
   */
  suspend fun simple(
    sender: AccountAddressInput,
    data: InputGenerateTransactionPayloadData,
    options: TransactionOptions? = null,
    withFeePayer: Boolean = false,
  ): UnsignedTransaction.Simple {
    val singleSignerRawTransactionData =
      InputGenerateSingleSignerRawTransactionData(
        sender = sender,
        data = data,
        options = options,
        withFeePayer = withFeePayer,
      )
    return generateTransaction(config, singleSignerRawTransactionData) as UnsignedTransaction.Simple
  }
}
