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

package xyz.mcxross.kaptos.model

import com.apollographql.apollo.ApolloClient
import io.ktor.client.HttpClient
import xyz.mcxross.kaptos.client.ClientConfig
import xyz.mcxross.kaptos.client.getClient
import xyz.mcxross.kaptos.util.NetworkToFaucetAPI
import xyz.mcxross.kaptos.util.NetworkToIndexerAPI
import xyz.mcxross.kaptos.util.NetworkToNodeAPI

/**
 * The `TransportConfig` class holds the config information for the SDK client instance. It is
 * initialized with an instance of `AptosSettings` and sets up various configurations based on the
 * provided settings.
 *
 * @param settings The `AptosSettings` instance to initialize the `TransportConfig` with. If not
 *   provided, default values are used.
 * @property network The network configuration, defaults to `Network.DEVNET` if not provided in
 *   `AptosSettings`.
 * @property clientConfig The client configuration, taken from `AptosSettings` if provided.
 * @property fullNode The fullnode configuration, taken from `AptosSettings` if provided.
 * @property faucet The faucet configuration, taken from `AptosSettings` if provided.
 * @property indexer The indexer configuration, taken from `AptosSettings` if provided.
 * @property clientConfig The client configuration, defaults to a new `ClientConfig` instance if not
 *   provided in `AptosSettings`.
 * @property fullNodeConfig The fullnode configuration, defaults to a new `FullNodeConfig` instance
 *   if not provided in `AptosSettings`.
 * @property indexerConfig The indexer configuration, defaults to a new `IndexerConfig` instance if
 *   not provided in `AptosSettings`.
 * @property faucetConfig The faucet configuration, defaults to a new `FaucetConfig` instance if not
 *   provided in `AptosSettings`.
 */
internal class TransportConfig(settings: AptosSettings = AptosSettings()) {
  val network: Network = settings.network ?: Network.DEVNET
  val fullNode: String? = settings.fullNode
  val faucet: String? = settings.faucet
  val indexer: String? = settings.indexer
  val clientConfig: ClientConfig = settings.clientConfig
  val commonHeaders: Map<String, String> = settings.commonHeaders.toMap()
  val fullNodeConfig: FullNodeConfig = settings.fullNodeConfig ?: FullNodeConfig()
  val indexerConfig: IndexerConfig = settings.indexerConfig ?: IndexerConfig()
  val faucetConfig: FaucetConfig = settings.faucetConfig ?: FaucetConfig()
  val archivalFallback: Boolean = settings.archivalFallback
  val requestTimeoutMillis: Long = settings.requestTimeoutMillis
  val indexerWaitTimeoutMillis: Long = settings.indexerWaitTimeoutMillis
  internal val httpClient: HttpClient = settings.client ?: getClient(clientConfig)
  private val ownsHttpClient: Boolean = settings.client == null
  private var apolloClient: ApolloClient? = null

  init {
    require(requestTimeoutMillis > 0) { "requestTimeoutMillis must be positive" }
    require(indexerWaitTimeoutMillis > 0) { "indexerWaitTimeoutMillis must be positive" }
  }

  internal fun headersFor(apiType: AptosApiType): Map<String, String> = buildMap {
    putAll(commonHeaders)
    when (apiType) {
      AptosApiType.FULLNODE -> putAll(fullNodeConfig.headers)
      AptosApiType.INDEXER -> putAll(indexerConfig.headers)
      AptosApiType.FAUCET -> {
        putAll(faucetConfig.headers)
        faucetConfig.authToken?.let { put("Authorization", "Bearer $it") }
      }
    }
  }

  internal suspend fun requestHeadersFor(apiType: AptosApiType): Map<String, String> = buildMap {
    putAll(headersFor(apiType))
    val dynamic =
      when (apiType) {
        AptosApiType.FULLNODE -> fullNodeConfig.requestHeaders()
        AptosApiType.INDEXER -> indexerConfig.requestHeaders()
        AptosApiType.FAUCET -> faucetConfig.requestHeaders()
      }
    putAll(dynamic)
  }

  internal fun graphqlClient(): ApolloClient =
    apolloClient
      ?: ApolloClient.Builder()
        .serverUrl(getRequestUrl(AptosApiType.INDEXER))
        .apply {
          headersFor(AptosApiType.INDEXER).forEach { (name, value) -> addHttpHeader(name, value) }
        }
        .build()
        .also { apolloClient = it }

  fun close() {
    apolloClient?.close()
    apolloClient = null
    if (ownsHttpClient) httpClient.close()
  }

  fun getRequestUrl(apiType: AptosApiType): String {
    return when (apiType) {
      AptosApiType.FULLNODE -> {
        fullNode
          ?: if (network == Network.CUSTOM) {
            throw AptosConfigurationException(
              AptosError.Validation("Please provide a custom full node URL")
            )
          } else
            NetworkToNodeAPI.getOrElse(network.name.lowercase()) {
              throw AptosConfigurationException(
                AptosError.UnsupportedFeature("Fullnode is not available for $network")
              )
            }
      }
      AptosApiType.FAUCET -> {
        faucet
          ?: when (network) {
            Network.TESTNET ->
              throw AptosConfigurationException(
                AptosError.UnsupportedFeature(
                  "There is no programmatic testnet faucet; use the Aptos minting site"
                )
              )
            Network.MAINNET ->
              throw AptosConfigurationException(
                AptosError.UnsupportedFeature("There is no mainnet faucet")
              )
            Network.CUSTOM ->
              throw AptosConfigurationException(
                AptosError.Validation("Please provide a custom faucet URL")
              )
            else ->
              NetworkToFaucetAPI.getOrElse(network.name.lowercase()) {
                throw AptosConfigurationException(
                  AptosError.UnsupportedFeature("Faucet is not available for $network")
                )
              }
          }
      }
      AptosApiType.INDEXER -> {
        indexer
          ?: if (network == Network.CUSTOM) {
            throw AptosConfigurationException(
              AptosError.Validation("Please provide a custom indexer URL")
            )
          } else
            NetworkToIndexerAPI.getOrElse(network.name.lowercase()) {
              throw AptosConfigurationException(
                AptosError.UnsupportedFeature("Indexer is not available for $network")
              )
            }
      }
    }
  }
}

/** General type definition for client headers */
internal open class ClientHeadersType {
  open val headers: Map<String, String> = emptyMap()
}

/**
 * A Fullnode only configuration object.
 *
 * @param headers - extra headers we want to send with the request
 */
internal data class FullNodeConfig(
  override val headers: Map<String, String> = emptyMap(),
  val requestHeaders: suspend () -> Map<String, String> = { emptyMap() },
) : ClientHeadersType()

/**
 * An Indexer only configuration object.
 *
 * @param headers - extra headers we want to send with the request
 */
internal data class IndexerConfig(
  override val headers: Map<String, String> = emptyMap(),
  val requestHeaders: suspend () -> Map<String, String> = { emptyMap() },
) : ClientHeadersType()

/**
 * A Faucet only configuration object
 *
 * @param headers - extra headers we want to send with the request
 * @param authToken - an auth token to send with a faucet request
 */
internal data class FaucetConfig(
  val headers: Map<String, String> = emptyMap(),
  val authToken: String? = null,
  val requestHeaders: suspend () -> Map<String, String> = { emptyMap() },
)
