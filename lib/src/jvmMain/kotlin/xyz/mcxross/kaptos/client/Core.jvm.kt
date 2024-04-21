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

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.model.UserAgent

/** Create a new Ktor client with the given configuration. */
actual fun httpClient(
  config: HttpClientConfig<*>.() -> Unit,
  engine: HttpClientEngine?,
  userConfig: ClientConfig,
) =
  HttpClient(engine ?: CIO.create()) {
    config(this)

    install(DefaultRequest) { headers { append("x-aptos-client", "aptos-kmp-sdk/${"VERSION"}") } }

    // Set the user agent. If the user wants to use a like agent, use that instead, otherwise use
    // the user's agent.
    if (userConfig.likeAgent == null) {
      install(UserAgent) { agent = userConfig.agent }
    } else {
      when (userConfig.likeAgent) {
        UserAgent.BROWSER -> BrowserUserAgent()
        UserAgent.CURL -> CurlUserAgent()
        else -> {
          install(UserAgent) { agent = userConfig.agent }
        }
      }
    }

    // Set the timeouts.
    install(HttpTimeout) {
      requestTimeoutMillis = userConfig.requestTimeout
      connectTimeoutMillis = userConfig.connectTimeout
    }

    // Set the content negotiation. This is required for the client to know how to handle JSON.
    install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }

    // How about retries? Things can go wrong, so let's retry a few times.
    install(HttpRequestRetry) {
      retryOnServerErrors(maxRetries = userConfig.retryOnServerErrors)
      maxRetries = userConfig.maxRetries
      exponentialDelay()
    }

    // Enable caching if the user wants it.
    if (userConfig.cache) install(HttpCache)

    engine {}
  }

actual class ClientConfig {
  var followRedirects = true
  var followSslRedirects = true
  var connectTimeoutMillis = 10000L
  var readTimeoutMillis = 10000L
  /**
   * Specifies how many times the client should retry on server errors. Default is `-1`, which means
   * no retries.
   */
  var retryOnServerErrors = -1
  /**
   * Specifies how many times the client should retry on connection errors. Default is `-1`, which
   * means no retries.
   */
  var maxRetries = -1
  /** Enables or disables caching. Default is `false`. */
  var cache: Boolean = false
  var agent: String = "Kaptos"

  /** Use a like agent. If this is set, the `agent` field will be ignored. */
  var likeAgent: UserAgent? = null

  /** Specifies a timeout for a whole HTTP call, from sending a request to receiving a response. */
  var requestTimeout = 10000L
  /** Specifies a timeout for establishing a connection with a node. */
  var connectTimeout = 10000L
}
