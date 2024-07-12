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

import xyz.mcxross.kaptos.client.ClientConfig
import xyz.mcxross.kaptos.util.NetworkToFaucetAPI
import xyz.mcxross.kaptos.util.NetworkToIndexerAPI
import xyz.mcxross.kaptos.util.NetworkToNodeAPI

/**
 * The `AptosConfig` class holds the config information for the SDK client instance. It is
 * initialized with an instance of `AptosSettings` and sets up various configurations based on the
 * provided settings.
 *
 * @param settings The `AptosSettings` instance to initialize the `AptosConfig` with. If not
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
class AptosConfig(settings: AptosSettings = AptosSettings()) {
  val network: Network = settings.network ?: Network.DEVNET
  val fullNode: String? = settings.fullNode
  val faucet: String? = settings.faucet
  val indexer: String? = settings.indexer
  val clientConfig: ClientConfig = settings.clientConfig
  val fullNodeConfig: FullNodeConfig = settings.fullNodeConfig ?: FullNodeConfig()
  val indexerConfig: IndexerConfig = settings.indexerConfig ?: IndexerConfig()
  val faucetConfig: FaucetConfig = settings.faucetConfig ?: FaucetConfig()

  fun getRequestUrl(apiType: AptosApiType): String {
    return when (apiType) {
      AptosApiType.FULLNODE -> {
        fullNode
          ?: if (network == Network.CUSTOM) throw Exception("Please provide a custom full node url")
          else
            NetworkToNodeAPI.getOrElse(network.name.lowercase()) {
              throw Exception("Invalid network")
            }
      }
      AptosApiType.FAUCET -> {
        faucet
          ?: if (network == Network.CUSTOM) throw Exception("Please provide a custom faucet url")
          else
            NetworkToFaucetAPI.getOrElse(network.name.lowercase()) {
              throw Exception("Invalid network")
            }
      }
      AptosApiType.INDEXER -> {
        indexer
          ?: if (network == Network.CUSTOM) throw Exception("Please provide a custom indexer url")
          else
            NetworkToIndexerAPI.getOrElse(network.name.lowercase()) {
              throw Exception("Invalid network")
            }
      }
    }
  }
}

/** General type definition for client headers */
open class ClientHeadersType {
  open var headers: Map<String, Any>? = null
}

/**
 * A Fullnode only configuration object.
 *
 * @param headers - extra headers we want to send with the request
 */
data class FullNodeConfig(override var headers: Map<String, Any>? = null) : ClientHeadersType()

/**
 * An Indexer only configuration object.
 *
 * @param headers - extra headers we want to send with the request
 */
data class IndexerConfig(override var headers: Map<String, Any>? = null) : ClientHeadersType()

/**
 * A Faucet only configuration object
 *
 * @param headers - extra headers we want to send with the request
 * @param authToken - an auth token to send with a faucet request
 */
data class FaucetConfig(val headers: Map<String, Any>? = null, val authToken: String? = null)
