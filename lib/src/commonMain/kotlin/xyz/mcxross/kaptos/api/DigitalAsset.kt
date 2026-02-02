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

package xyz.mcxross.kaptos.api

import kotlin.coroutines.cancellation.CancellationException
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetCollectionDataQuery
import xyz.mcxross.kaptos.generated.GetCurrentTokenOwnershipQuery
import xyz.mcxross.kaptos.generated.GetTokenDataQuery
import xyz.mcxross.kaptos.internal.*
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.DigitalAsset
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

/**
 * Digital asset API namespace. This class provides functionality to reading and writing digital
 * assets' related information.
 *
 * @property config AptosConfig object for configuration
 */
class DigitalAsset(val config: AptosConfig) : DigitalAsset {

  override suspend fun getCollectionData(
    filter: CollectionOwnershipV2Filter
  ): Result<GetCollectionDataQuery.Data?, AptosIndexerError> = getCollectionData(config, filter)

  override suspend fun getCollectionDataByCollectionId(
    collectionId: String,
    minimumLedgerVersion: Long?,
  ): Result<GetCollectionDataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.TOKEN_V2_PROCESSOR)
    return getCollectionDataByCollectionId(config, collectionId)
  }

  @Throws(AptosSdkError::class, CancellationException::class)
  override suspend fun getTokenData(
    page: PaginationArgs?
  ): Result<GetTokenDataQuery.Data?, AptosIndexerError> = getTokenData(config, page)

  override suspend fun getCurrentDigitalAssetOwnership(
    digitalAssetAddress: AccountAddressInput,
    minimumLedgerVersion: Long?,
  ): Result<GetCurrentTokenOwnershipQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.TOKEN_V2_PROCESSOR)

    return getCurrentDigitalAssetOwnership(config, digitalAssetAddress)
  }

  override suspend fun createCollectionTransaction(
    creator: Account,
    name: String,
    description: String,
    uri: String,
    collectionOptions: CreateCollectionOptions,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    createCollectionTransaction(config, creator, name, description, uri, collectionOptions, options)

  override suspend fun mintDigitalAssetTransaction(
    creator: Account,
    collection: String,
    name: String,
    description: String,
    uri: String,
    propertyKeys: List<String>?,
    propertyTypes: List<String>?,
    propertyValues: List<String>?,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    mintDigitalAssetTransaction(
      config,
      creator,
      collection,
      description,
      name,
      uri,
      propertyKeys,
      propertyTypes,
      propertyValues,
      options,
    )

  override suspend fun transferDigitalAssetTransaction(
    sender: Account,
    digitalAssetAddress: AccountAddressInput,
    recipient: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    transferDigitalAssetTransaction(
      config,
      sender,
      digitalAssetAddress,
      recipient,
      digitalAssetType,
      options,
    )

  override suspend fun mintSoulBoundTransaction(
    account: Account,
    collection: String,
    name: String,
    description: String,
    uri: String,
    recipient: AccountAddressInput,
    propertyKeys: List<String>,
    propertyTypes: List<String>,
    propertyValues: List<String>,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    mintSoulBoundTransaction(
      config,
      account,
      collection,
      name,
      description,
      uri,
      recipient,
      propertyKeys,
      propertyTypes,
      propertyValues,
      options,
    )

  override suspend fun burnDigitalAssetTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    burnDigitalAssetTransaction(config, creator, digitalAssetAddress, digitalAssetType, options)

  override suspend fun freezeDigitalAssetTransferTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    freezeDigitalAssetTransferTransaction(
      config,
      creator,
      digitalAssetAddress,
      digitalAssetType,
      options,
    )

  override suspend fun unfreezeDigitalAssetTransferTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    unfreezeDigitalAssetTransferTransaction(
      config,
      creator,
      digitalAssetAddress,
      digitalAssetType,
      options,
    )

  override suspend fun setDigitalAssetNameTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    name: String,
    digitalAssetType: MoveStructId,
  ): SimpleTransaction =
    setDigitalAssetNameTransaction(
      config,
      creator,
      digitalAssetAddress,
      name,
      digitalAssetType,
      InputGenerateTransactionOptions(),
    )

  override suspend fun setDigitalAssetDescriptionTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    description: String,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    setDigitalAssetDescriptionTransaction(
      config,
      creator,
      digitalAssetAddress,
      description,
      digitalAssetType,
      options,
    )

  override suspend fun setDigitalAssetURITransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    uri: String,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    setDigitalAssetURITransaction(
      config,
      creator,
      digitalAssetAddress,
      uri,
      digitalAssetType,
      options,
    )

  override suspend fun addDigitalAssetPropertyTransaction(
    creator: Account,
    propertyKey: String,
    propertyType: PropertyType,
    propertyValue: PropertyValue,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    addDigitalAssetPropertyTransaction(
      config,
      creator,
      propertyKey,
      propertyType,
      propertyValue,
      digitalAssetAddress,
      digitalAssetType,
      options,
    )

  override suspend fun removeDigitalAssetPropertyTransaction(
    creator: Account,
    propertyKey: String,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    removeDigitalAssetPropertyTransaction(
      config,
      creator,
      propertyKey,
      digitalAssetAddress,
      digitalAssetType,
      options,
    )
}
