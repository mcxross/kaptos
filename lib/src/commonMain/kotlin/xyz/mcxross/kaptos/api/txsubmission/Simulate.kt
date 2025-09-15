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

import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.internal.simulateTransaction
import xyz.mcxross.kaptos.model.*

class Simulate(val aptosConfig: AptosConfig) {
  suspend fun simple(
    signerPublicKey: PublicKey,
    transaction: AnyRawTransaction,
    feePayerPublicKey: PublicKey? = null,
    options: InputSimulateTransactionOptions = InputSimulateTransactionOptions(),
  ): Result<List<UserTransactionResponse>, Exception> =
    simulateTransaction(
      aptosConfig = aptosConfig,
      data =
        InputSimulateTransactionData(
          signerPublicKey = signerPublicKey,
          transaction = transaction,
          feePayerPublicKey = feePayerPublicKey,
          options = options,
        ),
    )
}
