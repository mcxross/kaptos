/*
 * Copyright 2025 McXross
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
package xyz.mcxross.kaptos.exception

/** Represents an error that occurred during a GET request to the Aptos REST API. */
sealed class AptosSdkError(message: String, cause: Throwable? = null) :
  AptosClientException(message, cause) {

  /**
   * A network, serialization, or other I/O-related issue.
   *
   * @param cause The original throwable.
   */
  data class NetworkError(override val cause: Throwable) :
    AptosSdkError("Network request failed", cause)

  /**
   * Represents a structured error returned by the Aptos REST API. This means the server
   * successfully responded but indicated a failure.
   */
  data class ApiError(val apiError: AptosApiError) : AptosSdkError(apiError.message)

  /** Represents a failure to parse or handle a response from the server. */
  data class DeserializationError(override val cause: Throwable) :
    AptosSdkError("Failed to deserialize response", cause)

  /**
   * An unexpected error during the request processing.
   *
   * @param cause The original throwable.
   */
  data class UnknownError(override val cause: Throwable) :
    AptosSdkError("An unknown error occurred", cause)
}
