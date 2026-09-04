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

/** Immutable options used when building a transaction. */
data class TransactionOptions(
  /** Null inherits the value configured on [xyz.mcxross.kaptos.Aptos]. */
  val maxGasAmount: ULong? = null,
  val gasUnitPrice: ULong? = null,
  val expirationTimestampSecs: ULong? = null,
  val expirationSecondsFromNow: ULong? = null,
  val replayProtection: ReplayProtection? = null,
) {
  init {
    require(maxGasAmount == null || maxGasAmount >= 2_000uL) {
      "maxGasAmount must be at least 2000"
    }
    require(expirationTimestampSecs == null || expirationSecondsFromNow == null) {
      "Set either expirationTimestampSecs or expirationSecondsFromNow, not both"
    }
  }
}
