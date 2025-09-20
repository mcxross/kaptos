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

import com.apollographql.apollo.api.Error

sealed class AptosIndexerError(message: String, cause: Throwable? = null) :
  Exception(message, cause) {

  /**
   * Represents a list of GraphQL-specific errors returned in the response body. This is the most
   * common type of error for the Indexer.
   */
  data class GraphQL(val errors: List<GraphQLError>) :
    AptosIndexerError(
      "Indexer API returned error(s): ${errors.firstOrNull()?.message ?: "Unknown GraphQL error."}"
    )

  /**
   * Represents a failure because an expected, non-nullable field was missing from the GraphQL
   * response data.
   */
  data class MissingField(val field: String) :
    AptosIndexerError("Missing expected field in GraphQL response: $field")

  /** Represents an underlying HTTP or Network failure when trying to reach the Indexer. */
  data class NetworkError(override val cause: Throwable) :
    AptosIndexerError("Failed to connect to the Indexer", cause)

  /** Represents a failure to parse the GraphQL response. */
  data class DeserializationError(override val cause: Throwable) :
    AptosIndexerError("Failed to deserialize Indexer response", cause)

  companion object {
    fun from(errors: List<Error>): AptosIndexerError {
      return GraphQL(
        errors =
          errors.map {
            GraphQLError(
              message = it.message,
              locations = it.locations?.map { loc -> ErrorLocation(loc.line, loc.column) },
              path = it.path,
              extensions = it.extensions,
            )
          }
      )
    }
  }
}
