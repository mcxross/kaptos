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

import com.github.michaelbull.result.expect
import kotlinx.coroutines.delay
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.paginateWithCursor
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.operations.submission.Submit
import xyz.mcxross.kaptos.model.*

internal suspend fun getTransactions(
  config: TransportConfig,
  options: PaginationArgs?,
): Result<List<TransactionResponse>, AptosSdkError> {
  val params =
    mutableMapOf<String, Any>().apply {
      options?.offset?.let { put("start", it) }
      options?.limit?.let { put("limit", it) }
    }

  return paginateWithCursor<TransactionResponse>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = config,
        type = AptosApiType.FULLNODE,
        originMethod = "getTransactions",
        path = "transactions",
        params = params.ifEmpty { null },
      )
    )
    .toResult()
}

internal suspend fun getGasPriceEstimation(
  config: TransportConfig
): Result<GasEstimation, AptosSdkError> =
  getAptosFullNode<GasEstimation>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "getGasPriceEstimation",
        path = "estimate_gas_price",
      )
    )
    .toResult()

internal suspend fun getTransactionByVersion(
  config: TransportConfig,
  ledgerVersion: Long,
): Result<TransactionResponse, AptosSdkError> =
  getAptosFullNode<TransactionResponse>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "getTransactionByVersion",
        path = "transactions/by_version/${ledgerVersion}",
      )
    )
    .toResult()

internal suspend fun getTransactionByHash(
  config: TransportConfig,
  ledgerHash: String,
): Result<TransactionResponse, AptosSdkError> =
  getAptosFullNode<TransactionResponse>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "getTransactionByHash",
        path = "transactions/by_hash/${ledgerHash}",
      )
    )
    .toResult()

internal suspend fun isTransactionPending(config: TransportConfig, txnHash: HexInput): Boolean =
  getTransactionByHash(config, txnHash.value)
    .toInternalResult()
    .expect { "Failed to fetch transaction $txnHash" }
    .type == TransactionResponseType.PENDING

internal suspend fun longWaitForTransaction(
  config: TransportConfig,
  txnHas: HexInput,
): Result<TransactionResponse, AptosSdkError> {

  return getAptosFullNode<TransactionResponse>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = config,
        originMethod = "longWaitForTransaction",
        path = "transactions/wait_by_hash/${txnHas.value}",
      )
    )
    .toResult()
}

internal suspend fun waitForTransaction(
  config: TransportConfig,
  txnHash: String,
  options: WaitForTransactionOptions,
): Result<TransactionResponse, AptosSdkError> {
  suspend fun poll(): Result<TransactionResponse, AptosSdkError> {
    var backoffMs = 200L
    while (true) {
      when (val response = getTransactionByHash(config, txnHash)) {
        is Result.Ok -> {
          val transaction = response.value
          if (transaction.type != TransactionResponseType.PENDING) {
            if (
              options.checkSuccess && transaction is UserTransactionResponse && !transaction.success
            ) {
              return Result.Err(
                AptosSdkError.ApiError(
                  xyz.mcxross.kaptos.exception.AptosApiError(
                    transaction.vmStatus,
                    "transaction_execution_failed",
                  )
                )
              )
            }
            return response
          }
        }
        is Result.Err -> {
          val error = response.error
          // A sponsor can submit to a different node before our read node has observed the hash.
          if (
            error !is AptosSdkError.ApiError || error.apiError.errorCode != "transaction_not_found"
          ) {
            return response
          }
        }
      }
      delay(backoffMs)
      backoffMs = (backoffMs * 3 / 2).coerceAtMost(2_000L)
    }
  }
  // Bound the entire operation, including slow requests. Parent cancellation still propagates.
  return kotlinx.coroutines.withTimeoutOrNull(options.timeoutSecs.toLong() * 1_000L) { poll() }
    ?: Result.Err(
      AptosSdkError.Timeout(
        "Transaction $txnHash was not confirmed within ${options.timeoutSecs} seconds"
      )
    )
}

internal suspend fun signAndSubmitTransaction(
  aptosConfig: TransportConfig,
  signer: Account,
  transaction: UnsignedTransaction,
): Result<PendingTransactionResponse, Exception> {
  val senderAuthenticator = signTransaction(signer, transaction)
  val submit = Submit(aptosConfig)
  return submit.simple(transaction = transaction, senderAuthenticator = senderAuthenticator)
}
