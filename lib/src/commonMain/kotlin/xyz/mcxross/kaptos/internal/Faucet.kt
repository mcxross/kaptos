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

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import xyz.mcxross.kaptos.client.postAptosFaucet
import xyz.mcxross.kaptos.exception.AptosApiError
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.*

internal suspend fun fundAccount(
  aptosConfig: AptosConfig,
  accountAddress: AccountAddressInput,
  amount: Long,
  options: WaitForTransactionOptions = WaitForTransactionOptions(),
): Result<TransactionResponse, AptosSdkError> {
  val faucetResult =
    postAptosFaucet(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "fundAccount",
        path = "fund",
        body = FaucetRequest(address = accountAddress.value, amount = amount),
      )
    )

  val txnHashes =
    faucetResult.get()
      ?: return Result.Err(
        faucetResult.getError()
          ?: AptosSdkError.UnknownError(
            IllegalStateException("Faucet request failed without an error")
          )
      )

  val hashToWaitFor =
    txnHashes.txnHashes.firstOrNull()
      ?: return Result.Err(
        AptosSdkError.ApiError(
          AptosApiError(
            message = "Faucet did not return any transaction hashes",
            errorCode = "NO_TXN_HASH_RETURNED_FROM_FAUCET",
          )
        )
      )

  val waitResult: Result<TransactionResponse, AptosIndexerError> =
    try {
      waitForTransaction(aptosConfig, hashToWaitFor, options)
    } catch (e: Exception) {
      return Result.Err(
        AptosSdkError.ApiError(
          AptosApiError(
            message = e.message ?: "waitForTransaction failed unexpectedly",
            errorCode = "WAIT_FOR_TRANSACTION_THREW_EXCEPTION",
          )
        )
      )
    }

  return when (waitResult) {
    is Result.Ok -> Result.Ok(waitResult.value)
    is Result.Err -> {
      Result.Err(
        AptosSdkError.ApiError(
          AptosApiError(
            message = waitResult.error.message ?: "waitForTransaction returned an error",
            errorCode = "WAIT_FOR_TRANSACTION_FAILED",
          )
        )
      )
    }
  }
}
