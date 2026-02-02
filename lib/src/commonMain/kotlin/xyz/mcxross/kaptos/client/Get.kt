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

import com.apollographql.apollo.ApolloClient
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerializationException
import xyz.mcxross.kaptos.exception.AptosApiError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosResponse
import xyz.mcxross.kaptos.model.RequestOptions

/**
 * Executes a GET request to an Aptos API endpoint using a configurable Ktor client.
 *
 * This function does not throw exceptions for network or HTTP errors. Instead, it returns a
 * `Result` object: `Ok<AptosResponse>` on success and `Err<AptosNetworkException>` on failure.
 *
 * ## Usage
 *
 * ```kotlin
 * // Assuming `aptos` is your client instance
 * val options = RequestOptions.AptosRequestOptions(...)
 * val result = aptos.get(options)
 *
 * when (result) {
 * is Ok -> println("Success! Status: ${result.value.status}")
 * is Err -> println("Failed! Reason: ${result.error.message}")
 * }
 * ```
 *
 * @param options The request configuration, including path, parameters, and client settings.
 * @param apiType The type of Aptos API to target (e.g., FULLNODE, INDEXER).
 * @return A `Result` which is either `Ok(AptosResponse)` on success or `Err(AptosNetworkException)`
 *   on failure.
 */
suspend fun get(
  options: RequestOptions.AptosRequestOptions,
  apiType: AptosApiType = AptosApiType.FULLNODE,
): Result<AptosResponse, AptosSdkError> {
  if (options.path.isBlank()) {
    return Err(
      AptosSdkError.UnknownError(IllegalArgumentException("Request path cannot be empty."))
    )
  }

  return try {
    val client = getClient(options.aptosConfig.clientConfig)
    val aptosResponse =
      client.get(options.aptosConfig.getRequestUrl(apiType)) {
        url { appendPathSegments(options.path) }
        options.params?.forEach { (key, value) -> parameter(key, value) }
      }

    if (aptosResponse.status.isSuccess()) {
      Ok(aptosResponse)
    } else {
      val apiError = aptosResponse.body<AptosApiError>()
      Err(AptosSdkError.ApiError(apiError))
    }
  } catch (e: Exception) {
    Err(AptosSdkError.NetworkError(e))
  }
}

/**
 * A high-level generic function to execute a GET request against an Aptos FullNode.
 *
 * This function handles the entire lifecycle of a read request: it builds and executes the request,
 * checks the HTTP response status, and deserializes the JSON body into the specified type `T`. It
 * is designed to be completely type-safe and will not throw exceptions for predictable API or
 * network errors.
 *
 * ## Usage
 *
 * ```kotlin
 * // Define the data class that matches the expected JSON response
 * @Serializable
 * data class AccountData(val sequence_number: String, val authentication_key: String)
 *
 * suspend fun fetchAccountData() {
 * // Setup request options
 * val address = AccountAddress.fromString("0x...")
 * val options = RequestOptions.GetAptosRequestOptions(
 * aptosConfig = aptos.config,
 * path = "accounts/${address}",
 * params = null
 * )
 *
 * // Make the call, specifying the target type
 * val result: Result<AccountData, AptosError> = aptos.getAptosFullNode(options)
 *
 * // Handle the result in a type-safe way
 * when (result) {
 * is Ok -> println("Sequence Number: ${result.value.sequence_number}")
 * is Err -> println("Error fetching account: ${result.error.message}")
 * }
 * }
 * ```
 *
 * @param T The data class type into which the successful JSON response body should be deserialized.
 *   Must be annotated with `@Serializable`.
 * @param options The configuration for the request, including the path and any query parameters.
 * @return A `Result` object:
 * - `Ok<T>`: On success, contains the deserialized data of type `T`.
 * - `Err<AptosError>`: On failure, contains a detailed error. This can be due to:
 * 1. **Network Failure**: Problems connecting to the node (from the underlying `get` call).
 * 2. **HTTP Error**: The API returned a non-2xx status code (e.g., 404 Not Found, 500 Internal
 *    Server Error).
 * 3. **Deserialization Error**: The response body could not be parsed into the target type `T`.
 */
suspend inline fun <reified T> getAptosFullNode(
  options: RequestOptions.GetAptosRequestOptions
): Result<T, AptosSdkError> {
  val responseResult =
    get(
      RequestOptions.AptosRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = options.originMethod,
        path = options.path,
        params = options.params,
        overrides = options.overrides,
      )
    )

  return responseResult.andThen { response ->
    try {
      val body =
        if (T::class == String::class) {
          response.bodyAsText() as T
        } else {
          response.body<T>()
        }
      Ok(body)
    } catch (e: Exception) {
      Err(AptosSdkError.UnknownError(e))
    }
  }
}

/**
 * Fetches and aggregates all pages of a paginated API resource using a cursor.
 *
 * This function repeatedly calls an endpoint, using the `x-aptos-cursor` header to request the next
 * page. This operation is **atomic**: it either successfully retrieves all pages or fails entirely
 * if any step encounters an error.
 *
 * ## Usage
 *
 * ```kotlin
 * // Define the data class for the items being paginated
 * @Serializable
 * data class MyEvent(val sequence_number: String, val data: MyEventData)
 *
 * suspend fun fetchAllEvents() {
 * val options = RequestOptions.AptosRequestOptions(...)
 *
 * // Make the call, specifying the list item's type
 * val result: Result<List<MyEvent>, AptosException> = aptos.paginateWithCursor(options)
 *
 * // Handle the result
 * when (result) {
 * is Ok -> println("Successfully fetched ${result.value.size} events in total.")
 * is Err -> println("Failed to fetch events: ${result.error.message}")
 * }
 * }
 * ```
 *
 * @param T The data class type for the individual items within the paginated list.
 * @param options The initial request configuration. The function will modify the `params` for
 *   subsequent page requests.
 * @return A `Result` object:
 * - `Ok<List<T>>`: On success, contains a single, flat list of all items `T` from all pages
 *   combined.
 * - `Err<AptosException>`: On failure, contains an error from the first failed step.
 */
suspend inline fun <reified T> paginateWithCursor(
  options: RequestOptions.AptosRequestOptions
): Result<List<T>, AptosSdkError> {
  val allItems = mutableListOf<T>()
  var currentCursor: String? = null
  var isFirstRequest = true

  do {
    val currentParams = options.params?.toMutableMap() ?: mutableMapOf()
    if (!isFirstRequest && currentCursor != null) {
      currentParams["start"] = currentCursor
    }

    val result = get(options.copy(params = currentParams))

    val response =
      result.get()
        ?: return Err(
          result.getError()
            ?: AptosSdkError.UnknownError(IllegalStateException("No response from GET request"))
        )

    val pageItems =
      try {
        response.body<List<T>>()
      } catch (e: SerializationException) {
        return Err(AptosSdkError.UnknownError(e))
      } catch (e: Exception) {
        return Err(AptosSdkError.UnknownError(e))
      }

    allItems.addAll(pageItems)
    currentCursor = response.headers["x-aptos-cursor"]
    isFirstRequest = false
  } while (currentCursor != null)

  return Ok(allItems)
}

fun getGraphqlClient(config: AptosConfig) =
  ApolloClient.Builder().serverUrl(config.getRequestUrl(AptosApiType.INDEXER)).build()

suspend fun getPageWithObfuscatedCursor(
  options: RequestOptions.GetAptosRequestOptions
): Result<Pair<AptosResponse, String?>, AptosSdkError> {

  val params = mutableMapOf<String, Any>()
  options.params?.get("cursor")?.let { params["start"] = it }
  options.params?.get("limit")?.let { params["limit"] = it }

  val result =
    get(
      RequestOptions.AptosRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = options.originMethod,
        path = options.path,
        params = params,
        overrides = options.overrides,
      )
    )

  return result
    .map { response ->
      val cursor = response.headers["x-aptos-cursor"]
      response to cursor
    }
    .mapError { networkError -> AptosSdkError.NetworkError(networkError) }
}
