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

package xyz.mcxross.kaptos.protocol

import kotlin.coroutines.cancellation.CancellationException
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.model.*

/**
 * Digital asset API namespace. This interface provides functionality to reading and writing digital
 * assets' related information.
 */
interface DigitalAsset {

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
   * @returns GetCollectionDataResponse response type
   */
  suspend fun getCollectionData(
    creatorAddress: AccountAddressInput,
    collectionName: String,
    minimumLedgerVersion: Long?,
    tokenStandard: TokenStandard?,
  ): Option<CollectionData?>

  /**
   * Queries data of a specific collection by the collection ID.
   *
   * @param collectionId the ID of the collection, it's the same thing as the address of the
   *   collection object
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns [CollectionData] response type
   */
  suspend fun getCollectionDataByCollectionId(
    collectionId: String,
    minimumLedgerVersion: Long?,
  ): Option<CollectionData?>

  @Throws(AptosException::class, CancellationException::class)
  suspend fun getTokenData(offset: Int? = null, limit: Int? = null): Option<TokenData?>

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
  suspend fun createCollectionTransaction(
    creator: Account,
    name: String,
    description: String = "",
    uri: String = "",
    collectionOptions: CreateCollectionOptions = CreateCollectionOptions(),
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

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
  suspend fun mintDigitalAssetTransaction(
    creator: Account,
    collection: String,
    name: String,
    description: String,
    uri: String,
    propertyKeys: List<String>? = emptyList(),
    propertyTypes: List<String>? = emptyList(),
    propertyValues: List<String>? = emptyList(),
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

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
  suspend fun transferDigitalAssetTransaction(
    sender: Account,
    digitalAssetAddress: AccountAddressInput,
    recipient: AccountAddressInput,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

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
  suspend fun mintSoulBoundTransaction(
    account: Account,
    collection: String,
    name: String,
    description: String,
    uri: String,
    recipient: AccountAddressInput,
    propertyKeys: List<String> = emptyList(),
    propertyTypes: List<String> = emptyList(),
    propertyValues: List<String> = emptyList(),
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

  /**
   * Burn a digital asset by its creator
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
  suspend fun burnDigitalAssetTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

  /**
   * Freeze digital asset transfer ability
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
  suspend fun freezeDigitalAssetTransferTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

  /**
   * Unfreeze digital asset transfer ability
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @param options Optional parameters for generating the transaction
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
  suspend fun unfreezeDigitalAssetTransferTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

  /**
   * Set the digital asset name
   *
   * @param creator The creator account
   * @param digitalAssetAddress The digital asset address
   * @param name The digital asset name
   * @param digitalAssetType The digital asset type. Default to "0x4::token::Token"
   * @returns A [SimpleTransaction] that can be simulated or submitted to chain
   */
  suspend fun setDigitalAssetNameTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    name: String,
    digitalAssetType: MoveStructId = "0x4::token::Token",
  ): SimpleTransaction

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
  suspend fun setDigitalAssetDescriptionTransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    description: String,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

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
  suspend fun setDigitalAssetURITransaction(
    creator: Account,
    digitalAssetAddress: AccountAddressInput,
    uri: String,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

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
  suspend fun addDigitalAssetPropertyTransaction(
    creator: Account,
    propertyKey: String,
    propertyType: PropertyType,
    propertyValue: PropertyValue,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

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
  suspend fun removeDigitalAssetPropertyTransaction(
    creator: Account,
    propertyKey: String,
    digitalAssetAddress: AccountAddressInput,
    digitalAssetType: MoveStructId = "0x4::token::Token",
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction
}
