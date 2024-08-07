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

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.mcxross.kaptos.exception.AptosApiError
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.AptosResponse
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.model.RequestOptions

/** Meaningful errors map */
val errors: Map<Int, String> =
  mapOf(
    400 to "Bad Request",
    401 to "Unauthorized",
    403 to "Forbidden",
    404 to "Not Found",
    429 to "Too Many Requests",
    500 to "Internal Server Error",
    502 to "Bad Gateway",
    503 to "Service Unavailable",
  )

suspend fun get(
  options: RequestOptions.AptosRequestOptions,
  apiType: AptosApiType = AptosApiType.FULLNODE,
): AptosResponse {

  val aptosResponse =
    getClient(options.aptosConfig.clientConfig).get(
      options.aptosConfig.getRequestUrl(options.type)
    ) {
      url { appendPathSegments(options.path) }
      options.params?.forEach { (k, v) -> parameter(k, v) }
    }

  return responseFitCheck(aptosResponse, apiType)
}

suspend inline fun <reified T> getAptosFullNode(
  options: RequestOptions.GetAptosRequestOptions
): Pair<AptosResponse, Option<T>> {
  val response =
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

  if (response.status == HttpStatusCode.NotFound) {
    return Pair(response, Option.None)
  }

  if (response.status == HttpStatusCode.InternalServerError) {
    throw AptosApiError(response.call.request, response, "Error: ${response.bodyAsText()}")
  }

  return Pair(response, Option.Some(response.body()))
}

// This function is a helper for paginating using a function wrapping an API
suspend inline fun <reified T> paginateWithCursor(
  options: RequestOptions.AptosRequestOptions
): Option<List<Pair<AptosResponse, Option<List<T>>>>> {
  val out = mutableListOf<Pair<AptosResponse, Option<List<T>>>>()
  var cursor: String?
  do {
    val response =
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

    cursor = response.headers["x-aptos-cursor"]
    out.add(Pair(response, Option.Some(response.body<List<T>>())))
  } while (cursor != null)

  if (out.isEmpty()) return Option.None

  return Option.Some(out)
}
