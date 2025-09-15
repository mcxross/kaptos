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

/**
 * Represents core account metadata on the Aptos blockchain.
 *
 * This data structure corresponds to account information returned by the Aptos Fullnode API. It
 * provides essential details used for tracking account state and verifying ownership.
 *
 * @property sequenceNumber Monotonically increasing sequence number of transactions sent from this
 *   account. Used for preventing replay attacks and ensuring transaction ordering. Represented as a
 *   string-encoded u64 for cross-language compatibility.
 * @property authenticationKey A 32-byte authentication key in hex encoding (prefixed with "0x").
 *   Determines who is authorized to sign transactions for this account. Unlike `Address`, leading
 *   zeros are preserved.
 */
@Serializable
data class AccountData(
  /**
   * Sequence number for the account, represented as a string-encoded 64-bit unsigned integer (u64).
   *
   * The sequence number increases with each transaction submitted from this account. Returned as a
   * string to avoid precision loss in languages like JavaScript, which do not natively support
   * 64-bit integers in JSON.
   */
  @SerialName("sequence_number") val sequenceNumber: String,

  /**
   * Authentication key for the account, represented as a hex-encoded string.
   * - Always prefixed with "0x".
   * - Each byte is encoded as exactly two hex digits.
   * - Unlike the `Address` type, leading zeros are preserved and never trimmed.
   *
   * This key defines the set of public keys that are allowed to sign transactions for the account.
   */
  @SerialName("authentication_key") val authenticationKey: String,
)
