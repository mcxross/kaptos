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

package xyz.mcxross.kaptos.util

import xyz.mcxross.kaptos.model.Network

val NetworkToIndexerAPI =
  mapOf(
    "mainnet" to "https://api.mainnet.aptoslabs.com/v1/graphql",
    "testnet" to "https://api.testnet.aptoslabs.com/v1/graphql",
    "devnet" to "https://api.devnet.aptoslabs.com/v1/graphql",
    "randomnet" to "https://indexer-randomnet.hasura.app/v1/graphql",
    "local" to "http://127.0.0.1:8090/v1/graphql",
  )

val NetworkToNodeAPI =
  mapOf(
    "mainnet" to "https://api.mainnet.aptoslabs.com/v1",
    "testnet" to "https://api.testnet.aptoslabs.com/v1",
    "devnet" to "https://api.devnet.aptoslabs.com/v1",
    "randomnet" to "https://fullnode.random.aptoslabs.com/v1",
    "local" to "http://127.0.0.1:8080/v1",
  )

val NetworkToFaucetAPI =
  mapOf(
    "mainnet" to "https://faucet.mainnet.aptoslabs.com",
    "testnet" to "https://faucet.testnet.aptoslabs.com",
    "devnet" to "https://faucet.devnet.aptoslabs.com",
    "randomnet" to "https://faucet.random.aptoslabs.com",
    "local" to "http://127.0.0.1:8081",
  )

val NetworkToChainId = mapOf("mainnet" to 1, "testnet" to 2, "randomnet" to 70)

val NetworkToNetworkName =
  mapOf(
    "mainnet" to Network.MAINNET,
    "testnet" to Network.TESTNET,
    "devnet" to Network.DEVNET,
    "custom" to Network.CUSTOM,
  )
