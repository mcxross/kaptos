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

package xyz.mcxross.kaptos.internal

import io.ktor.client.call.*
import xyz.mcxross.kaptos.client.postAptosFaucet
import xyz.mcxross.kaptos.model.*

internal suspend fun fundAccount(
  aptosConfig: AptosConfig,
  accountAddress: AccountAddressInput,
  amount: Long,
  options: WaitForTransactionOptions = WaitForTransactionOptions(),
): Option<TransactionResponse> {
  val response =
    postAptosFaucet(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "fundAccount",
        path = "fund",
        body = FaucetRequest(address = accountAddress.value, amount = amount),
      )
    )

  val faucetResponse: FaucetResponse = response.body()

  val txResponse = waitForTransaction(aptosConfig, faucetResponse.txnHashes[0], options)

  return txResponse
}
