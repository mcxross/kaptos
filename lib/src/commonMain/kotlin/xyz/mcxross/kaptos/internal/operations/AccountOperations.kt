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
package xyz.mcxross.kaptos.internal.operations

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.*
import xyz.mcxross.kaptos.generated.GetAccountCoinsDataQuery
import xyz.mcxross.kaptos.generated.GetAccountCollectionsWithOwnedTokensQuery
import xyz.mcxross.kaptos.generated.GetAccountOwnedTokensFromCollectionQuery
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.internal.*
import xyz.mcxross.kaptos.internal.getResource
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.APTOS_COIN
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

internal class AccountOperations(val config: TransportConfig) {

  // ======================================= REST APIs ========================================

  suspend fun getAccountInfo(
    accountAddress: AccountAddressInput,
    params: LedgerVersionQueryParam.() -> Unit = {},
  ): Result<AccountData, AptosSdkError> {
    val queryParams = LedgerVersionQueryParam().apply(params)
    return getInfo(this.config, accountAddress, queryParams.toMap())
  }

  suspend fun getAccountModules(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit = {},
  ): Result<List<MoveModuleBytecode>, AptosSdkError> {
    val paginationParams = SpecificPaginationQueryParams().apply(params)
    return getModules(config, accountAddress, paginationParams.toMap())
  }

  suspend fun getAccountModule(
    accountAddress: AccountAddressInput,
    moduleName: String,
    param: LedgerVersionQueryParam.() -> Unit = {},
  ): Result<MoveModuleBytecode, AptosSdkError> {
    val ledgerVersionQueryParam = LedgerVersionQueryParam().apply(param)
    return getModule(config, accountAddress, moduleName, ledgerVersionQueryParam.toMap())
  }

  suspend fun getAccountResources(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit = {},
  ): Result<List<MoveResource>, AptosSdkError> {
    val paginationParams = SpecificPaginationQueryParams().apply(params)
    return getResources(config, accountAddress, paginationParams.toMap())
  }

  // ======================================= Indexer APIs ========================================

  suspend fun getAccountTransactions(
    accountAddress: AccountAddressInput,
    params: PaginationQueryParams.() -> Unit = {},
  ): Result<List<TransactionResponse>, AptosSdkError> {
    val paginationParams = PaginationQueryParams().apply(params)
    return getTransactions(config, accountAddress, paginationParams.toMap())
  }

  suspend fun getAccountTransactionsCount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError> {
    waitForIndexerOnVersion(
      config,
      minimumLedgerVersion,
      ProcessorType.ACCOUNT_TRANSACTION_PROCESSOR,
    )
    return getAccountTransactionsCount(config, accountAddress)
  }

  suspend fun getAccountCoinsData(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
    sortOrder: List<FungibleAssetSortOrder>? = null,
    page: PaginationArgs? = null,
  ): Result<GetAccountCoinsDataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)
    return getAccountCoinsData(config, accountAddress, sortOrder, page)
  }

  suspend fun getAccountCoinsCount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)
    return getAccountCoinsCount(config, accountAddress)
  }

  suspend fun getAccountAPTAmount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.FUNGIBLE_ASSET_PROCESSOR)
    return when (
      val result =
        getAccountCoinAmount(
          config,
          accountAddress,
          MoveValue.MoveStructId(APTOS_COIN),
          page = PaginationArgs(limit = 1),
        )
    ) {
      is Result.Ok -> {
        val amount =
          result.value
            ?.current_fungible_asset_balances
            ?.firstOrNull()
            ?.amount
            ?.toString()
            ?.toLongOrNull() ?: 0L
        Result.Ok(amount)
      }
      is Result.Err -> result
    }
  }

  suspend fun getAccountCoinAmount(
    accountAddress: AccountAddressInput,
    coinType: MoveValue.MoveStructId = MoveValue.MoveStructId(APTOS_COIN),
    page: PaginationArgs? = null,
  ): Result<GetAccountCoinsDataQuery.Data?, AptosIndexerError> =
    getAccountCoinAmount(config, accountAddress, coinType, page)

  suspend fun getAccountCoinAmountFromSmartContract(
    accountAddress: AccountAddressInput,
    coinType: MoveValue.MoveStructId? = null,
    faMetadataAddress: AccountAddressInput? = null,
  ): Result<Long, AptosSdkError> =
    getAccountCoinAmountFromSmartContract(config, accountAddress, coinType, faMetadataAddress)

  suspend fun getAccountCollectionsWithOwnedTokens(
    accountAddress: AccountAddressInput,
    tokenStandard: TokenStandard? = null,
    sortOrder: List<CollectionOwnershipV2ViewSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetAccountCollectionsWithOwnedTokensQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.TOKEN_V2_PROCESSOR)
    return getAccountCollectionsWithOwnedTokens(
      config,
      accountAddress,
      tokenStandard,
      sortOrder,
      page,
    )
  }

  suspend fun getAccountOwnedTokensFromCollectionAddress(
    accountAddress: AccountAddressInput,
    collectionAddress: AccountAddressInput,
    tokenStandard: TokenStandard? = null,
    sortOrder: List<TokenOwnershipV2SortOrder>? = null,
    page: PaginationArgs? = null,
  ): Result<GetAccountOwnedTokensFromCollectionQuery.Data?, AptosIndexerError> =
    getAccountOwnedTokensFromCollectionAddress(
      config,
      accountAddress,
      collectionAddress,
      tokenStandard,
      sortOrder,
      page,
    )

  suspend fun getAccountOwnedObjects(
    accountAddress: AccountAddressInput,
    sortOrder: List<ObjectSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetObjectDataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.DEFAULT)
    return getAccountOwnedObjects(config, accountAddress, sortOrder, page)
  }

  suspend fun getAccountTokensCount(
    accountAddress: AccountAddressInput,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<Long, AptosIndexerError> {
    waitForIndexerOnVersion(
      config,
      minimumLedgerVersion,
      ProcessorType.ACCOUNT_TRANSACTION_PROCESSOR,
    )
    return getAccountTokensCount(config, accountAddress, page)
  }

  suspend fun lookupOriginalAccountAddress(
    authenticationKey: AccountAddressInput,
    ledgerVersion: Int? = null,
  ): Result<AccountAddressInput, AptosSdkError> =
    lookupOriginalAccountAddress(
      config,
      AccountAddress.from(authenticationKey),
      LedgerVersionArg(ledgerVersion),
    )
}

internal suspend inline fun <reified T> AccountOperations.getAccountResource(
  accountAddress: AccountAddressInput,
  resourceName: String,
  param: LedgerVersionQueryParam.() -> Unit = {},
): Result<T, AptosSdkError> {
  val ledgerVersionQueryParam = LedgerVersionQueryParam().apply(param)
  return getResource(this.config, accountAddress, resourceName, ledgerVersionQueryParam.toMap())
}
