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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GasEstimation(
  @SerialName("gas_estimate") val gasEstimate: Long,
  @SerialName("deprioritized_gas_estimate") val deprioritizedGasEstimate: Long? = null,
  @SerialName("prioritized_gas_estimate") val prioritizedGasEstimate: Long? = null,
)
