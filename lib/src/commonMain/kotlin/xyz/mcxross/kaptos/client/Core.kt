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
import xyz.mcxross.graphql.client.DefaultGraphQLClient
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.AptosConfig

/**
 * Create a new Ktor client with the given configuration.
 *
 * Each client is platform-specific with a different engine. Each engine has its own configuration
 * options.
 */
expect fun httpClient(clientConfig: ClientConfig): HttpClient

expect class ClientConfig()

val client = httpClient(clientConfig = ClientConfig())

fun indexerClient(config: AptosConfig) =
  DefaultGraphQLClient(config.getRequestUrl(AptosApiType.INDEXER))
