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

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetAccountCoinsDataQuery
import xyz.mcxross.kaptos.generated.GetAccountCollectionsWithOwnedTokensQuery
import xyz.mcxross.kaptos.generated.GetAccountOwnedTokensFromCollectionQuery
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.internal.getResource
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.APTOS_COIN

/**
 * Account API namespace. This interface provides functionality to reading and writing account
 * related information.
 *
 * @property config AptosConfig object for configuration
 */
interface Account {

  val config: AptosConfig

  // ======================================= REST APIs ========================================

  /**
   * Queries the Aptos blockchain for an account's on-chain state.
   *
   * This function retrieves core information about a specific account, such as its sequence number
   * and authentication key. The sequence number is crucial for transaction ordering and preventing
   * replay attacks.
   *
   * Optionally, you can query the account's state at a specific historical ledger version,
   * providing a snapshot of the account at that point in time.
   *
   * ## Technical Details
   * This function makes a GET request to the `/accounts/{address}` endpoint of the Aptos REST API.
   * The `AccountData` object it returns contains the fundamental properties required to interact
   * with the account.
   * - **`sequence_number`**: A `u64` integer that must be included in any transaction initiated by
   *   this account. The sequence number of a transaction must be equal to the sequence number of
   *   the sender's account. After the transaction is executed, the account's sequence number is
   *   incremented by one.
   * - **`authentication_key`**: A key derived from the account's public key, used by the Aptos
   *   network to verify transaction signatures.
   *
   * ## Usage
   * The function returns a `Result` object that must be handled. Using a `when` expression is the
   * safest way to process the outcome.
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val accountInfoResult = aptos.getAccountInfo(address)
   *
   * // Example 1: Robust handling with a 'when' expression
   * when (accountInfoResult) {
   * is Result.Ok -> {
   * println("Success! Sequence Number: ${accountInfoResult.value.sequenceNumber}")
   * }
   * is Result.Err -> {
   * println("Error fetching account: ${accountInfoResult.error}")
   * }
   * }
   *
   * // Example 2: Safely getting the value or null
   * val accountData = accountInfoResult.getOrNull()
   * if (accountData != null) {
   * println("Account exists with sequence number: ${accountData.sequenceNumber}")
   * } else {
   * println("Could not retrieve account data.")
   * }
   * ```
   *
   * @param accountAddress The 32-byte address of the Aptos account to query. This can be provided
   *   in various formats via the `AccountAddressInput` type.
   * @param params A lambda function to configure optional query parameters, such as specifying a
   *   `ledgerVersion` to query the state from the past.
   * @return A `Result` object which will be either `Result.Ok<AccountData>` on success or
   *   `Result.Err<AptosError>` on failure.
   * - `Result.Ok`: Contains the `AccountData`, including the `sequenceNumber` and
   *   `authenticationKey`.
   * - `Result.Err`: Encapsulates an `AptosError` detailing what went wrong (e.g., account not
   *   found, network issue).
   */
  suspend fun getAccountInfo(
    accountAddress: AccountAddressInput,
    params: LedgerVersionQueryParam.() -> Unit = {},
  ): Result<AccountData, AptosSdkError>

  /**
   * Retrieves all Move modules deployed to a specific Aptos account.
   *
   * This function automatically handles pagination, fetching the complete list of modules across
   * multiple API calls if necessary.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val modulesResult = aptos.getAccountModules(address)
   *
   * when (modulesResult) {
   * is Result.Ok -> println("Successfully fetched ${modulesResult.value.size} modules.")
   * is Result.Err -> println("Error fetching modules: ${modulesResult.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the Aptos account to query.
   * @param params A lambda to configure optional pagination parameters, such as `limit`.
   * @return A `Result` which is either `Ok` containing the full list of modules, or `Err`
   *   containing a categorized [AptosSdkError].
   */
  suspend fun getAccountModules(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit = {},
  ): Result<List<MoveModuleBytecode>, AptosSdkError>

  /**
   * Retrieves a specific Move module from an account by its name.
   *
   * An optional ledger version can be provided to view the module as it existed at a specific point
   * in time.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val moduleName = "coin"
   * val moduleResult = aptos.getAccountModule(address, moduleName)
   *
   * when (moduleResult) {
   * is Result.Ok -> println("Successfully retrieved module ABI: ${moduleResult.value.abi}")
   * is Result.Err -> println("Error retrieving module: ${moduleResult.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the account that owns the module.
   * @param moduleName The name of the module to retrieve.
   * @param param A lambda to configure optional query parameters, such as `ledgerVersion`.
   * @return A `Result` which is either `Ok` containing the [MoveModuleBytecode], or `Err`
   *   containing an [AptosSdkError] if the module is not found or another error occurs.
   */
  suspend fun getAccountModule(
    accountAddress: AccountAddressInput,
    moduleName: String,
    param: LedgerVersionQueryParam.() -> Unit = {},
  ): Result<MoveModuleBytecode, AptosSdkError>

  /**
   * Retrieves all Move resources for a specific account.
   *
   * This function automatically handles pagination, fetching the complete list of resources across
   * multiple API calls if necessary. You can also provide an optional ledger version to view the
   * account's state at a specific point in time.
   *
   * **Note**: If the requested ledger version has been pruned by the node, the API will return a
   * 410 HTTP error.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val resourcesResult = aptos.getAccountResources(address)
   *
   * when (resourcesResult) {
   * is Result.Ok -> println("Account has ${resourcesResult.value.size} resources.")
   * is Result.Err -> println("Error retrieving resources: ${resourcesResult.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the Aptos account to query.
   * @param params A lambda to configure optional query parameters, such as `limit` or
   *   `ledgerVersion`.
   * @return A `Result` which is either `Ok` containing the full list of [MoveResource]s, or `Err`
   *   containing a categorized [AptosSdkError].
   */
  suspend fun getAccountResources(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit = {},
  ): Result<List<MoveResource>, AptosSdkError>

  // ======================================= Indexer APIs ========================================

  /**
   * Queries for transactions submitted by a specific account.
   *
   * This function automatically handles pagination, fetching the complete list of transactions
   * across multiple API calls if necessary.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val resolution = aptos.getAccountTransactions(address) {
   * limit = 25
   * }
   *
   * when (resolution) {
   * is Result.Ok -> println("Successfully fetched ${resolution.value.size} transactions.")
   * is Result.Err -> println("Error fetching transactions: ${resolution.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the account to query.
   * @param params A lambda to configure optional pagination parameters, such as `limit` and
   *   `offset`.
   * @return A `Result` which is either `Ok` containing the full list of [TransactionResponse]s, or
   *   `Err` containing a categorized [AptosSdkError].
   */
  suspend fun getAccountTransactions(
    accountAddress: AccountAddressInput,
    params: PaginationQueryParams.() -> Unit = {},
  ): Result<List<TransactionResponse>, AptosSdkError>

  /**
   * Queries the current count of transactions submitted by an account
   *
   * @param accountAddress The account address we want to get the total count for
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Current count of transactions made by an account
   */
  suspend fun getAccountTransactionsCount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError>

  /**
   * Queries an account's coins data
   *
   * @param accountAddress The account address we want to get the coins data for
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Array with the coins data
   */
  suspend fun getAccountCoinsData(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
    sortOrder: List<FungibleAssetSortOrder>? = null,
    page: PaginationArgs? = null,
  ): Result<GetAccountCoinsDataQuery.Data?, AptosIndexerError>

  /**
   * Queries the current count of an account's coins aggregated
   *
   * @param accountAddress The account address we want to get the total count for
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Current count of the aggregated count of all account's coins
   */
  suspend fun getAccountCoinsCount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError>

  /**
   * Queries the account's APT amount
   *
   * @param accountAddress The account address we want to get the total count for
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Current amount of account's APT
   */
  suspend fun getAccountAPTAmount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError>

  /**
   * Queries the account's coin amount by the coin type
   *
   * @param accountAddress The account address we want to get the total count for
   * @param coinType The coin type to query
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Current amount of account's coin
   */
  suspend fun getAccountCoinAmount(
    accountAddress: AccountAddressInput,
    coinType: MoveValue.MoveStructId = MoveValue.MoveStructId(APTOS_COIN),
    page: PaginationArgs? = null,
  ): Result<GetAccountCoinsDataQuery.Data?, AptosIndexerError>

  /**
   * Queries for all collections that an account currently has tokens for.
   *
   * This includes NFTs, fungible tokens, and soulbound tokens. This function first ensures the
   * indexer is synchronized up to the specified `minimumLedgerVersion` before querying to guarantee
   * up-to-date data.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val resolution = aptos.getAccountCollectionsWithOwnedTokens(
   * accountAddress = address,
   * tokenStandard = TokenStandard.V2
   * )
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val collectionsData = resolution.value
   * println("Successfully retrieved collections data: $collectionsData")
   * }
   * is Result.Err -> println("Error retrieving collections: ${resolution.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the account to query.
   * @param tokenStandard An optional token standard to filter the results by (e.g., `NFT`).
   * @param sortOrder An optional list of sorting options for the results.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getAccountCollectionsWithOwnedTokens(
    accountAddress: AccountAddressInput,
    tokenStandard: TokenStandard? = null,
    sortOrder: List<CollectionOwnershipV2ViewSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetAccountCollectionsWithOwnedTokensQuery.Data?, AptosIndexerError>

  /**
   * Queries for all tokens an account owns within a specific collection.
   *
   * This includes all token standards (v1 and v2) such as NFTs, fungible tokens, and soulbound
   * tokens. You can optionally filter by a specific token standard.
   *
   * ## Usage
   *
   * ```kotlin
   * val accountAddr = AccountAddress.fromString("0x...")
   * val collectionAddr = AccountAddress.fromString("0x...")
   * val resolution = aptos.getAccountOwnedTokensFromCollectionAddress(
   * accountAddress = accountAddr,
   * collectionAddress = collectionAddr
   * )
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved owned tokens data: $data")
   * }
   * is Result.Err -> println("Error retrieving owned tokens: ${resolution.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the account that owns the tokens.
   * @param collectionAddress The address of the collection to query.
   * @param tokenStandard An optional token standard to filter the results by.
   * @param sortOrder An optional list of sorting options for the results.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getAccountOwnedTokensFromCollectionAddress(
    accountAddress: AccountAddressInput,
    collectionAddress: AccountAddressInput,
    tokenStandard: TokenStandard? = null,
    sortOrder: List<TokenOwnershipV2SortOrder>? = null,
    page: PaginationArgs? = null,
  ): Result<GetAccountOwnedTokensFromCollectionQuery.Data?, AptosIndexerError>

  /**
   * Queries for objects owned by a specific account.
   *
   * This function can wait for the indexer to be synchronized to a specific ledger version before
   * querying to ensure the data is up-to-date.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val resolution = aptos.getAccountOwnedObjects(address)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved owned objects data: $data")
   * }
   * is Result.Err -> println("Error retrieving owned objects: ${resolution.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the account to query.
   * @param sortOrder An optional list of sorting options for the results.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getAccountOwnedObjects(
    accountAddress: AccountAddressInput,
    sortOrder: List<ObjectSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetObjectDataQuery.Data?, AptosIndexerError>

  /**
   * Queries the total count of tokens owned by a specific account.
   *
   * This function first ensures the indexer is synchronized up to the specified
   * `minimumLedgerVersion` before querying to guarantee the count is up-to-date.
   *
   * ## Usage
   *
   * ```kotlin
   * val address = AccountAddress.fromString("0x...")
   * val resolution = aptos.getAccountTokensCount(address)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val count = resolution.value
   * println("Account owns $count tokens.")
   * }
   * is Result.Err -> println("Error retrieving tokens count: ${resolution.error.message}")
   * }
   * ```
   *
   * @param accountAddress The address of the account to query.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the total token count as a `Long`, or
   *   `Result.Err` containing an [AptosIndexerError].
   */
  suspend fun getAccountTokensCount(
    accountAddress: AccountAddressInput,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError>

  /**
   * Retrieves the original account address for a given authentication key.
   *
   * This function can resolve the address for both rotated and non-rotated keys.
   *
   * ## Usage
   *
   * ```kotlin
   * // Note: An authentication key can be created from an account address
   * val authKey = AccountAddress.fromString("0x...")
   * val resolution = aptos.lookupOriginalAccountAddress(authKey)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val address = resolution.value
   * println("Original account address: $address")
   * }
   * is Result.Err -> println("Error looking up address: ${resolution.error.message}")
   * }
   * ```
   *
   * @param authenticationKey The authentication key to look up.
   * @param ledgerVersion An optional ledger version to query; if not provided, it will use the
   *   latest version.
   * @return A `Result` which is either `Result.Ok` containing the associated account address, or
   *   `Result.Err` containing an [AptosSdkError].
   */
  suspend fun lookupOriginalAccountAddress(
    authenticationKey: AccountAddressInput,
    ledgerVersion: Int? = null,
  ): Result<AccountAddressInput, AptosSdkError>
}

/**
 * Queries a specific account resource given an account address and resource name
 *
 * @param accountAddress Aptos account address
 * @param resourceName Name of the resource
 * @param param [LedgerVersionQueryParam] to optionally configure the ledger version.
 * @returns [MoveResource]
 */
suspend inline fun <reified T> Account.getAccountResource(
  accountAddress: AccountAddressInput,
  resourceName: String,
  param: LedgerVersionQueryParam.() -> Unit = {},
): Result<T, AptosSdkError> {
  val ledgerVersionQueryParam = LedgerVersionQueryParam().apply(param)
  return getResource(this.config, accountAddress, resourceName, ledgerVersionQueryParam.toMap())
}
