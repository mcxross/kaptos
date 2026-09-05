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

import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.internal.simulateTransaction
import xyz.mcxross.kaptos.model.*

internal class Simulate(val aptosConfig: TransportConfig) {
  suspend fun simple(
    signerPublicKey: PublicKey,
    transaction: UnsignedTransaction,
    secondarySignerPublicKeys: List<PublicKey> = emptyList(),
    feePayerPublicKey: PublicKey? = null,
    options: SimulationOptions = SimulationOptions(),
  ): Result<List<UserTransactionResponse>, Exception> =
    simulateTransaction(
      aptosConfig = aptosConfig,
      data =
        InputSimulateTransactionData(
          signerPublicKey = signerPublicKey,
          transaction = transaction,
          secondarySignerPublicKeys = secondarySignerPublicKeys,
          feePayerPublicKey = feePayerPublicKey,
          options = options,
        ),
    )
}
