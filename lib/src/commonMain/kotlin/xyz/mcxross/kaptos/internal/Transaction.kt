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
import xyz.mcxross.kaptos.api.txsubmission.Submit
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.paginateWithCursor
import xyz.mcxross.kaptos.exception.AptosApiErrorV1
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.exception.WaitForTransactionException
import xyz.mcxross.kaptos.model.*

internal suspend fun getTransactions(
  config: AptosConfig,
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
  config: AptosConfig
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
  config: AptosConfig,
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
  config: AptosConfig,
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

internal suspend fun isTransactionPending(config: AptosConfig, txnHash: HexInput): Boolean =
  getTransactionByHash(config, txnHash.value)
    .toInternalResult()
    .expect { "Failed to fetch transaction $txnHash" }
    .type == TransactionResponseType.PENDING

internal suspend fun longWaitForTransaction(
  config: AptosConfig,
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
  config: AptosConfig,
  txnHash: String,
  options: WaitForTransactionOptions,
): Result<TransactionResponse, AptosIndexerError> {
  var isPending = true
  var timeElapsed = 0
  var lastTxn: TransactionResponse? = null
  var lastError: AptosApiErrorV1? = null
  var backoffIntervalMs = 200
  val backoffMultiplier = 1.5

  fun handleAPIError(e: Exception) {
    val isAptosApiErrorV1 = e is AptosApiErrorV1
    if (!isAptosApiErrorV1) {
      throw e // This would be unexpected
    }

    lastError = e

    val isRequestError = e.status != 404 && e.status >= 400 && e.status < 500

    if (isRequestError) {
      throw e
    }
  }

  // check to see if the txn is already on the blockchain
  try {
    val txnResponse = getTransactionByHash(config, txnHash).toInternalResult()

    if (txnResponse.isOk) {
      lastTxn = txnResponse.value
    } else {
      return Result.Err(AptosIndexerError.GraphQL(listOf()))
    }
    isPending = lastTxn.type == TransactionResponseType.PENDING
  } catch (e: Exception) {
    handleAPIError(e)
  }

  while (isPending) {
    if (timeElapsed >= options.timeoutSecs) {
      break
    }

    try {

      val txnResponse = getTransactionByHash(config, txnHash).toInternalResult()

      if (txnResponse.isOk) {
        lastTxn = txnResponse.value
      } else {
        Result.Err(AptosIndexerError.GraphQL(listOf()))
      }

      isPending = lastTxn?.type == TransactionResponseType.PENDING

      if (!isPending) {
        break
      }
    } catch (e: AptosApiErrorV1) {
      lastError = e
    }

    delay(backoffIntervalMs.toLong())
    timeElapsed += backoffIntervalMs / 1000
    backoffIntervalMs = (backoffIntervalMs * backoffMultiplier).toInt()
  }

  if (lastTxn == null) {
    if (lastError != null) {
      throw lastError
    } else {
      throw WaitForTransactionException(
        "Fetching transaction $txnHash failed and timed out after ${options.timeoutSecs} seconds"
      )
    }
  }

  if (lastTxn.type == TransactionResponseType.PENDING) {
    throw WaitForTransactionException(
      "Transaction $txnHash timed out in pending state after ${options.timeoutSecs} seconds"
    )
  }

  if (!options.checkSuccess) {
    return Result.Ok(lastTxn)
  }

  return Result.Ok(lastTxn)
}

internal suspend fun signAndSubmitTransaction(
  aptosConfig: AptosConfig,
  signer: Account,
  transaction: AnyRawTransaction,
): Result<PendingTransactionResponse, Exception> {
  val senderAuthenticator = signTransaction(signer, transaction)
  val submit = Submit(aptosConfig)
  return submit.simple(transaction = transaction, senderAuthenticator = senderAuthenticator)
}
