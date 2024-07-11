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

import xyz.mcxross.kaptos.util.DEFAULT_MAX_GAS_AMOUNT

/** Optional options to set when generating a transaction */
data class InputGenerateTransactionOptions(
  val maxGasAmount: Long = DEFAULT_MAX_GAS_AMOUNT,
  val gasUnitPrice: Long? = null,
  val expireTimestamp: Long? = null,
  val accountSequenceNumber: Number? = null,
)
