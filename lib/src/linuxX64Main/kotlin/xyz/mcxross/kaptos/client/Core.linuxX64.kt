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
import io.ktor.client.engine.curl.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.util.DEFAULT_CLIENT_HEADERS

actual fun httpClient(clientConfig: ClientConfig) =
  HttpClient(Curl) {
    followRedirects = clientConfig.followRedirects

    install(DefaultRequest) {
      headers { DEFAULT_CLIENT_HEADERS.forEach { (key, value) -> append(key, value) } }
    }

    // Set the user agent. If the user wants to use a like agent, use that instead, otherwise use
    // the user's agent.
    if (clientConfig.likeAgent == null) {
      install(UserAgent) { agent = clientConfig.agent }
    } else {
      when (clientConfig.likeAgent) {
        xyz.mcxross.kaptos.model.UserAgent.BROWSER -> BrowserUserAgent()
        xyz.mcxross.kaptos.model.UserAgent.CURL -> CurlUserAgent()
        else -> {
          install(UserAgent) { agent = clientConfig.agent }
        }
      }
    }

    // Set the timeouts.
    install(HttpTimeout) {
      requestTimeoutMillis = clientConfig.requestTimeout
      connectTimeoutMillis = clientConfig.connectTimeout
    }

    // Set the content negotiation. This is required for the client to know how to handle JSON.
    install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }

    // How about retries? Things can go wrong, so let's retry a few times.
    install(HttpRequestRetry) {
      retryOnServerErrors(maxRetries = clientConfig.retryOnServerErrors)
      maxRetries = clientConfig.maxRetries
      exponentialDelay()
    }

    // Enable caching if the user wants it.
    if (clientConfig.cache) install(HttpCache)

    engine {
      // Set the proxy if the user wants it.
      clientConfig.proxy?.let { proxy = ProxyBuilder.http(it) }
    }
  }

actual class ClientConfig(

  /** Specifies whether the client should follow redirects. Default is `true`. */
  var followRedirects: Boolean = true,

  /** Specifies whether the client should follow SSL redirects. Default is `true`. */
  var cache: Boolean = true,

  /** Specifies the user agent. Default is `Kaptos`. */
  var agent: String = "Kaptos/Linux",

  /** Use a like agent. If this is set, the `agent` field will be ignored. */
  var likeAgent: xyz.mcxross.kaptos.model.UserAgent? = null,

  /** Specifies how many times the client should retry on server errors. Default is `0`. */
  var retryOnServerErrors: Int = 0,

  /** Specifies how many times the client should retry on connection errors. Default is `0`. */
  var maxRetries: Int = 0,

  /** Specifies a timeout for a whole HTTP call, from sending a request to receiving a response. */
  var requestTimeout: Long = 10000,

  /** Specifies a timeout for establishing a connection with a node. */
  var connectTimeout: Long = 10000,

  /** Specifies the maximum number of connections the client can have. */
  var proxy: String? = null,
) {
  actual companion object {
    actual val default: ClientConfig
      get() = ClientConfig()
  }
}
