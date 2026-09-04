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
internal interface FungibleAsset {
  /**
   * Queries for fungible asset metadata.
   *
   * ## Usage
   *
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
