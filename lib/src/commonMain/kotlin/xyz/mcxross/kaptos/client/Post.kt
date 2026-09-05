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

package xyz.mcxross.kaptos.client

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.map
import io.ktor.client.call.*
import io.ktor.client.plugins.timeout
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.rethrowCancellation
import xyz.mcxross.kaptos.model.*

internal suspend inline fun <reified V> post(
  options: RequestOptions.PostRequestOptions<V>
): Result<AptosResponse, AptosSdkError> {
  return try {
    val requestHeaders = options.aptosConfig.requestHeadersFor(options.type)
    val aptosResponse =
      options.aptosConfig.httpClient.post(options.aptosConfig.getRequestUrl(options.type)) {
        url { appendPathSegments(options.path) }
        options.params?.forEach { (key, value) -> parameter(key, value) }
        timeout { requestTimeoutMillis = options.aptosConfig.requestTimeoutMillis }
        applyAptosHeaders(requestHeaders)
        contentType(ContentType.parse(options.contentType.type))
        accept(ContentType.parse(options.acceptType.type))
        setBody(options.body)
      }

    responseFitCheck(aptosResponse)
  } catch (e: Exception) {
    e.rethrowCancellation()
    Err(AptosSdkError.NetworkError(e))
  }
}

internal suspend inline fun <reified T, reified V> postAptosFullNode(
  options: RequestOptions.PostAptosRequestOptions<V>
): Result<Pair<AptosResponse, T>, AptosSdkError> {
  val postResult =
    post(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = options.originMethod,
        path = options.path,
        contentType = options.contentType,
        acceptType = options.acceptType,
        params = options.params,
        body = options.body,
        overrides = options.overrides,
      )
    )

  return postResult.andThen { response ->
    try {
      val body = response.body<T>()
      Ok(Pair(response, body))
    } catch (e: Exception) {
      e.rethrowCancellation()
      Err(AptosSdkError.DeserializationError(e))
    }
  }
}

/**
 * A convenience wrapper for `postAptosFullNode` that only returns the deserialized data on success.
 *
 * ## Usage
 *
 * ```kotlin
 * val resolution = aptos.postAptosFullNodeAndGetData<MyData, MyBody>(options)
 *
 * when (resolution) {
 * is Result.Ok -> {
 * val data = resolution.value
 * println("Success! Just the data: $data")
 * }
 * is Result.Err -> println("Error posting data: ${resolution.error.message}")
 * }
 * ```
 *
 * @return A `Result` which is either `Ok` containing only the deserialized data `T`, or `Err`
 *   containing an [AptosSdkError].
 */
internal suspend inline fun <reified T, reified V> postAptosFullNodeAndGetData(
  options: RequestOptions.PostAptosRequestOptions<V>
): Result<T, AptosSdkError> {
  return postAptosFullNode<T, V>(options).map { it.second }
}

/**
 * Submits a request to the Aptos Faucet.
 *
 * The faucet is used on test networks to fund accounts with testnet coins.
 *
 * ## Usage
 *
 * ```kotlin
 * // Assuming `fundAccountOptions` is configured correctly
 * val resolution = aptos.postAptosFaucet(fundAccountOptions)
 *
 * when (resolution) {
 * is Result.Ok -> {
 * val transactionHashes = resolution.value
 * println("Successfully funded account. Hashes: $transactionHashes")
 * }
 * is Result.Err -> println("Error funding account: ${resolution.error.message}")
 * }
 * ```
 *
 * @return A `Result` which is either `Ok` containing a list of submitted transaction hashes, or
 *   `Err` containing an [AptosSdkError].
 */
internal suspend inline fun <reified T> postAptosFaucet(
  options: RequestOptions.PostAptosRequestOptions<T>
): Result<FaucetResponse, AptosSdkError> {
  val postResult =
    post(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FAUCET,
        originMethod = options.originMethod,
        path = options.path,
        contentType = options.contentType,
        acceptType = options.acceptType,
        params = options.params,
        body = options.body,
        overrides = options.overrides,
      )
    )

  return postResult.andThen { response ->
    try {
      Ok(response.body<FaucetResponse>())
    } catch (e: Exception) {
      e.rethrowCancellation()
      Err(AptosSdkError.DeserializationError(e))
    }
  }
}
