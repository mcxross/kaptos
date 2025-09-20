/*
 * Copyright 2025 McXross
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

package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetCurrentFungibleAssetBalancesQuery
import xyz.mcxross.kaptos.generated.GetFungibleAssetActivitiesQuery
import xyz.mcxross.kaptos.generated.GetFungibleAssetMetadataQuery
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.FungibleAssetActivityFilter
import xyz.mcxross.kaptos.model.FungibleAssetBalanceFilter
import xyz.mcxross.kaptos.model.FungibleAssetMetadataFilter
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.Result

/** An interface for querying fungible asset-related operations. */
interface FungibleAsset {
  /**
   * Queries for fungible asset metadata.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
   * val filter = fungibleAssetMetadataFilter { assetType = stringFilter { eq = APTOS_COIN } }
   * val resolution = aptos.getFungibleAssetMetadata(filter = filter)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved metadata: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving metadata: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param filter Filtering options for the query.
   * @param page Optional pagination arguments.
   * @param minimumLedgerVersion Optional ledger version to sync up to before querying.
   * @return A `Result` containing the query data or an [AptosIndexerError].
   */
  suspend fun getFungibleAssetMetadata(
    filter: FungibleAssetMetadataFilter,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetMetadataQuery.Data?, AptosIndexerError>

  /**
   * Queries the fungible asset metadata for a specific asset type.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
   * val resolution = aptos.getFungibleAssetMetadataByAssetType(APTOS_COIN)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Asset type from response: ${data?.asset_type}")
   * }
   * is Result.Err -> {
   * println("Error retrieving metadata by asset type: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param assetType The asset type to query for, e.g., "0x1::aptos_coin::AptosCoin".
   * @param page Optional pagination arguments.
   * @param minimumLedgerVersion Optional ledger version to sync up to before querying.
   * @return A `Result` containing a single fungible asset metadata object or an
   *   [AptosIndexerError].
   */
  suspend fun getFungibleAssetMetadataByAssetType(
    assetType: String,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetMetadataQuery.Fungible_asset_metadatum?, AptosIndexerError>

  /**
   * Queries for fungible asset metadata based on the creator's address.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
   * val creator = AccountAddress.fromString("0x00...001")
   * val resolution = aptos.getFungibleAssetMetadataByCreatorAddress(creator)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved metadata: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving metadata by creator: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param creatorAddress The address of the asset's creator.
   * @param page Optional pagination arguments.
   * @param minimumLedgerVersion Optional ledger version to sync up to before querying.
   * @return A `Result` containing the query data or an [AptosIndexerError].
   */
  suspend fun getFungibleAssetMetadataByCreatorAddress(
    creatorAddress: AccountAddressInput,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetMetadataQuery.Data?, AptosIndexerError>

  /**
   * Queries for fungible asset activities.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
   * val filter = fungibleAssetActivitiesFilter { assetType = stringFilter { eq = APTOS_COIN } }
   * val resolution = aptos.getFungibleAssetActivities(filter = filter, page = PaginationArgs(limit = 2))
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved activities: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving activities: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param filter Filtering options for the query.
   * @param page Optional pagination arguments.
   * @param minimumLedgerVersion Optional ledger version to sync up to before querying.
   * @return A `Result` containing the query data or an [AptosIndexerError].
   */
  suspend fun getFungibleAssetActivities(
    filter: FungibleAssetActivityFilter,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetActivitiesQuery.Data?, AptosIndexerError>

  /**
   * Queries for current fungible asset balances.
   *
   * ## Usage
   *
   * ```kotlin
   * val aptos = Aptos(AptosConfig(AptosSettings(network = Network.DEVNET)))
   * val userAccount = Account.generate()
   * aptos.fundAccount(userAccount.accountAddress, 1_000)
   *
   * val filter = currentFungibleAssetBalancesFilter {
   * ownerAddress = stringFilter { eq = userAccount.accountAddress.toString() }
   * assetType = stringFilter { eq = "0x1::aptos_coin::AptosCoin" }
   * }
   * val resolution = aptos.getCurrentFungibleAssetBalances(filter = filter)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved balances: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving balances: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param filter Filtering options for the query.
   * @param page Optional pagination arguments.
   * @param minimumLedgerVersion Optional ledger version to sync up to before querying.
   * @return A `Result` containing the query data or an [AptosIndexerError].
   */
  suspend fun getCurrentFungibleAssetBalances(
    filter: FungibleAssetBalanceFilter? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetCurrentFungibleAssetBalancesQuery.Data?, AptosIndexerError>
}
