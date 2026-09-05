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
package xyz.mcxross.kaptos.internal.operations

import com.github.michaelbull.result.map
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetCurrentFungibleAssetBalancesQuery
import xyz.mcxross.kaptos.generated.GetFungibleAssetActivitiesQuery
import xyz.mcxross.kaptos.generated.GetFungibleAssetMetadataQuery
import xyz.mcxross.kaptos.internal.getCurrentFungibleAssetBalances
import xyz.mcxross.kaptos.internal.getFungibleAssetActivities
import xyz.mcxross.kaptos.internal.getFungibleAssetMetadata
import xyz.mcxross.kaptos.internal.toInternalResult
import xyz.mcxross.kaptos.internal.toResult
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.FungibleAssetActivityFilter
import xyz.mcxross.kaptos.model.FungibleAssetBalanceFilter
import xyz.mcxross.kaptos.model.FungibleAssetMetadataFilter
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.types.fungibleAssetMetadataFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

internal class FungibleAssetOperations(val config: TransportConfig) {
  suspend fun getFungibleAssetMetadata(
    filter: FungibleAssetMetadataFilter,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetMetadataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)

    return getFungibleAssetMetadata(config, filter, page)
  }

  suspend fun getFungibleAssetMetadataByAssetType(
    assetType: String,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetMetadataQuery.Fungible_asset_metadatum?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)
    val filter = fungibleAssetMetadataFilter { this.assetType = stringFilter { eq = assetType } }

    val resolution = getFungibleAssetMetadata(config, filter, page)

    return resolution
      .toInternalResult()
      .map { it?.fungible_asset_metadata?.firstOrNull() }
      .toResult()
  }

  suspend fun getFungibleAssetMetadataByCreatorAddress(
    creatorAddress: AccountAddressInput,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetMetadataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)

    val filter = fungibleAssetMetadataFilter {
      this.creatorAddress = stringFilter { eq = creatorAddress.value }
    }

    return getFungibleAssetMetadata(config, filter, page)
  }

  suspend fun getFungibleAssetActivities(
    filter: FungibleAssetActivityFilter,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetFungibleAssetActivitiesQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)

    return getFungibleAssetActivities(config, filter, page)
  }

  suspend fun getCurrentFungibleAssetBalances(
    filter: FungibleAssetBalanceFilter? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetCurrentFungibleAssetBalancesQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)
    return getCurrentFungibleAssetBalances(config, filter, page)
  }
}
