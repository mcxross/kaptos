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

/**
 * Options for creating a new collection in an account.
 *
 * @property maxSupply The maximum supply of tokens in the collection. Defaults to [Long.MAX_VALUE].
 * @property mutableDescription Indicates if the collection description can be modified. Defaults to
 *   true.
 * @property mutableRoyalty Indicates if the royalty information can be modified. Defaults to true.
 * @property mutableURI Indicates if the collection URI can be modified. Defaults to true.
 * @property mutableTokenDescription Indicates if the token description can be modified. Defaults to
 *   true.
 * @property mutableTokenName Indicates if the token name can be modified. Defaults to true.
 * @property mutableTokenProperties Indicates if the token properties can be modified. Defaults to
 *   true.
 * @property mutableTokenURI Indicates if the token URI can be modified. Defaults to true.
 * @property tokensBurnableByCreator Indicates if tokens can be burned by the creator. Defaults to
 *   true.
 * @property tokensFreezableByCreator Indicates if tokens can be frozen by the creator. Defaults to
 *   true.
 * @property royaltyNumerator The numerator for calculating royalties. Defaults to 0.
 * @property royaltyDenominator The denominator for calculating royalties. Defaults to 1.
 */
data class CreateCollectionOptions(
  val maxSupply: Long = Long.MAX_VALUE,
  val mutableDescription: Boolean = true,
  val mutableRoyalty: Boolean = true,
  val mutableURI: Boolean = true,
  val mutableTokenDescription: Boolean = true,
  val mutableTokenName: Boolean = true,
  val mutableTokenProperties: Boolean = true,
  val mutableTokenURI: Boolean = true,
  val tokensBurnableByCreator: Boolean = true,
  val tokensFreezableByCreator: Boolean = true,
  val royaltyNumerator: Long = 0,
  val royaltyDenominator: Long = 1,
)
