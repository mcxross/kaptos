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
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.internal.addDigitalAssetPropertyTransaction
import xyz.mcxross.kaptos.internal.burnDigitalAssetTransaction
import xyz.mcxross.kaptos.internal.createCollectionTransaction
import xyz.mcxross.kaptos.internal.freezeDigitalAssetTransferTransaction
import xyz.mcxross.kaptos.internal.getCollectionData
import xyz.mcxross.kaptos.internal.getCollectionDataByCollectionId
import xyz.mcxross.kaptos.internal.getTokenData
import xyz.mcxross.kaptos.internal.mintDigitalAssetTransaction
import xyz.mcxross.kaptos.internal.mintSoulBoundTransaction
import xyz.mcxross.kaptos.internal.removeDigitalAssetPropertyTransaction
import xyz.mcxross.kaptos.internal.setDigitalAssetDescriptionTransaction
import xyz.mcxross.kaptos.internal.setDigitalAssetNameTransaction
import xyz.mcxross.kaptos.internal.setDigitalAssetURITransaction
import xyz.mcxross.kaptos.internal.transferDigitalAssetTransaction
import xyz.mcxross.kaptos.internal.unfreezeDigitalAssetTransferTransaction
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.DigitalAsset

/**
 * Digital asset API namespace. This class provides functionality to reading and writing digital
 * assets' related information.
 *
 * @property config AptosConfig object for configuration
 */
class DigitalAsset(val config: AptosConfig) : DigitalAsset {

  /**
   * Queries data of a specific collection by the collection creator address and the collection
   * name.
   *
   * If, for some reason, a creator account has 2 collections with the same name in v1 and v2, can
   * pass an optional `tokenStandard` parameter to query a specific standard
   *
   * @param creatorAddress the address of the collection's creator
   * @param collectionName the name of the collection
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @param tokenStandard the token standard to query
   * @returns [CollectionData] response type
   */
  override suspend fun getCollectionData(
    creatorAddress: AccountAddressInput,
    collectionName: String,
    minimumLedgerVersion: Long?,
    tokenStandard: TokenStandard?,
  ): Option<CollectionData?> =
    getCollectionData(config, creatorAddress, collectionName, tokenStandard)

  /**
   * Queries data of a specific collection by the collection ID.
   *
   * @param collectionId the ID of the collection, it's the same thing as the address of the
   *   collection object
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns [CollectionData] response type
   */
  override suspend fun getCollectionDataByCollectionId(
    collectionId: String,
    minimumLedgerVersion: Long?,
  ): Option<CollectionData?> =
    getCollectionDataByCollectionId(config, collectionId, minimumLedgerVersion)

  @Throws(AptosException::class, CancellationException::class)
  override suspend fun getTokenData(offset: Int?, limit: Int?): Option<TokenData> =
    getTokenData(config, offset, limit)

  /**
   * Creates a new collection within the specified account.
   *
   * @sample
   *
   * ```kotlin
   * val txn = aptos.createCollectionTransaction(
   *       creator = alice,
   *       name = "McXross",
   *       description = "Lazy",
   *       uri = "https://mcxross.xyz"
   *     )
   * ```
   *
   * @param creator The account under which the collection will be created. This is the account of
   *   the collection's creator.
   * @param name The unique name of the collection within the creator's account. Each account can
   *   only have one collection with a given name.
   * @param description An optional description of the collection. It must be less than 2048
   *   characters.
   * @param uri An optional link to content related to the collection. It must be less than 512
   *   characters.
   * @param collectionOptions Optional parameters for configuring the collection.
   * @param options Optional parameters for generating the transaction.
   * @return A [SimpleTransaction] that, when submitted, will create the specified collection.
   */
  override suspend fun createCollectionTransaction(
    creator: Account,
    name: String,
    description: String,
    uri: String,
    collectionOptions: CreateCollectionOptions,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    createCollectionTransaction(config, creator, name, description, uri, collectionOptions, options)

  /**
   * Create a transaction to mint a digital asset into the creators account within an existing
   * collection.
   *
   * @sample
   *
   * ```kotlin
   *     val txn = aptos.mintDigitalAssetTransaction(
   *          creator = alice,
   *          collection = "McXross", name = "Digi",
   *          description = "Lazy",
   *          uri = "https://mcxross.xyz"
   *     )
   * ```
   *
   * @param creator the creator of the collection
   * @param collection the name of the collection the digital asset belongs to
   * @param name the name of the digital asset
   * @param description the description of the digital asset
   * @param uri the URI to additional info about the digital asset
   *
   * Optional parameters for configuring the digital asset
   *
   * @param propertyKeys the keys of the properties
   * @param propertyTypes the types of the properties
   * @param propertyValues the values of the properties
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Transfer a digital asset (non-fungible digital asset) ownership.
   *
   * We can transfer a digital asset only when the digital asset is not frozen (i.e. owner transfer
   * is not disabled such as for soul bound digital assets)
   *
   * @param sender The sender account of the current digital asset owner
   * @param digitalAssetAddress The digital asset address
   * @param recipient The recipient account address
   * @param digitalAssetType optional. The digital asset type, default to "0x4::token::Token"
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Mint a soul bound digital asset.
   *
   * @param account The account that will mint the digital asset.
   * @param collection The collection name.
   * @param name The name of the digital asset.
   * @param description The description of the digital asset.
   * @param uri The URI of the digital asset.
   * @param recipient The account address of the recipient.
   * @param propertyKeys The keys of the properties.
   * @param propertyTypes The types of the properties.
   * @param propertyValues The values of the properties.
   * @param options Optional parameters for generating the transaction.
   * @return A [SimpleTransaction] that, when submitted, will mint the specified soul bound digital
   *   asset.
   */
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

  /**
   * Burn a digital asset by its creator
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
  override suspend fun burnDigitalAssetTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId,
    options: InputGenerateTransactionOptions,
  ): SimpleTransaction =
    burnDigitalAssetTransaction(config, creator, digitalAssetAddress, digitalAssetType, options)

  /**
   * Freeze digital asset transfer ability
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Unfreeze digital asset transfer ability
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Set the digital asset name
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @param name The digital asset name
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Set the digital asset description
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @param description The digital asset description
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Set the digital asset URI
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @param uri The digital asset URI
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Add a property to a digital asset
   *
   * @param creator The creator account
   * @param propertyKey The property key
   * @param propertyType The property type
   * @param propertyValue The property value
   * @param digitalAssetAddress The digital asset address
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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

  /**
   * Remove a property from a digital asset
   *
   * @param creator The creator account
   * @param propertyKey The property key
   * @param digitalAssetAddress The digital asset address
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
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
