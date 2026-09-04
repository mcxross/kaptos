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

import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.TransportConfig

// Transaction validation reserves maxGasAmount * gasUnitPrice before execution. The SDK's
// Aptos-compatible 2,000,000 max-gas default therefore needs more than 1 APT on localnet.
const val FUND_AMOUNT = 1_000_000_000L

internal fun localTransportConfig() = TransportConfig(AptosSettings(network = Network.LOCAL))
