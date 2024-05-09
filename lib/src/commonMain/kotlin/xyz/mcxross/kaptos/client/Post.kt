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
import io.ktor.http.*
import xyz.mcxross.kaptos.model.*

suspend inline fun <reified V> post(options: RequestOptions.PostRequestOptions<V>): AptosResponse {
  return client.post(options.aptosConfig.getRequestUrl(options.type)) {
    url { appendPathSegments(options.path) }
    contentType(ContentType.Application.Json)
    setBody(options.body)
  }
}

suspend inline fun <reified T, reified V> postAptosFullNode(
  options: RequestOptions.PostAptosRequestOptions<V>
): Pair<AptosResponse, T> {
  val response =
    post<V>(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = options.originMethod,
        path = options.path,
        body = options.body,
      )
    )
  return Pair(response, response.body())
}

suspend fun postAptosIndexer(
  options: RequestOptions.PostAptosRequestOptions<GraphqlQuery>
): AptosResponse {
  val response =
    post(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.INDEXER,
        originMethod = options.originMethod,
        path = "",
        body = options.body,
      )
    )
  return response
}

suspend inline fun <reified T> postAptosFaucet(
  options: RequestOptions.PostAptosRequestOptions<T>
): AptosResponse {
  val response =
    post<T>(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FAUCET,
        originMethod = options.originMethod,
        path = options.path,
        body = options.body,
      )
    )
  return response
}
