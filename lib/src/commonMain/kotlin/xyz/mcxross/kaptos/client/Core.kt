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
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.*
import xyz.mcxross.kaptos.exception.AptosApiError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.internal.rethrowCancellation
import xyz.mcxross.kaptos.model.AptosResponse

/**
 * Create a new Ktor client with the given configuration.
 *
 * Each client is platform-specific with a different engine. Each engine has its own configuration
 * options.
 */
internal expect fun httpClient(clientConfig: ClientConfig): HttpClient

internal expect class ClientConfig {
  companion object {
    val default: ClientConfig
  }
}

internal fun getClient(clientConfig: ClientConfig) = httpClient(clientConfig)

internal fun HttpRequestBuilder.applyAptosHeaders(
  requestHeaders: Map<String, String>,
  includeCredentials: Boolean = true,
) {
  val credentialHeaders = setOf("authorization", "cookie", "x-api-key")
  headers {
    requestHeaders.forEach { (name, value) ->
      if (includeCredentials || name.lowercase() !in credentialHeaders) {
        remove(name)
        append(name, value)
      }
    }
  }
}

/**
 * Checks an HTTP response, returning a `Result` that is either the successful response or a
 * structured error.
 */
internal suspend fun responseFitCheck(
  aptosResponse: AptosResponse
): Result<AptosResponse, AptosSdkError> {
  if (aptosResponse.status.isSuccess()) {
    return Ok(aptosResponse)
  }
  return try {
    val apiError = aptosResponse.body<AptosApiError>()
    Err(AptosSdkError.ApiError(apiError))
  } catch (e: Exception) {
    e.rethrowCancellation()
    Err(AptosSdkError.DeserializationError(e))
  }
}
