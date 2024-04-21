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
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

actual fun httpClient(
  config: HttpClientConfig<*>.() -> Unit,
  engine: HttpClientEngine?,
  userConfig: ClientConfig,
) =
  HttpClient(Js) {
    config(this)

    // Set the content negotiation. This is required for the client to know how to handle JSON.
    install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
  }

actual class ClientConfig
