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

package xyz.mcxross.kaptos.internal

import com.apollographql.apollo.api.Optional
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.map
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.client.paginateWithCursor
import xyz.mcxross.kaptos.exception.AptosApiError
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.extension.longOrNull
import xyz.mcxross.kaptos.generated.GetAccountAddressesForAuthKeyQuery
import xyz.mcxross.kaptos.generated.GetAccountCoinsCountQuery
import xyz.mcxross.kaptos.generated.GetAccountCoinsDataQuery
import xyz.mcxross.kaptos.generated.GetAccountCollectionsWithOwnedTokensQuery
import xyz.mcxross.kaptos.generated.GetAccountOwnedTokensByTokenDataQuery
import xyz.mcxross.kaptos.generated.GetAccountOwnedTokensFromCollectionQuery
import xyz.mcxross.kaptos.generated.GetAccountOwnedTokensQuery
import xyz.mcxross.kaptos.generated.GetAccountTokensCountQuery
import xyz.mcxross.kaptos.generated.GetAccountTransactionsCountQuery
import xyz.mcxross.kaptos.generated.GetAuthKeysForPublicKeyQuery
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_bool_exp
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.model.types.currentCollectionOwnershipV2ViewFilter
import xyz.mcxross.kaptos.model.types.currentCollectionsV2Filter
import xyz.mcxross.kaptos.model.types.currentFungibleAssetBalancesFilter
import xyz.mcxross.kaptos.model.types.currentObjectsFilter
import xyz.mcxross.kaptos.model.types.currentTokenDatasV2Filter
import xyz.mcxross.kaptos.model.types.currentTokenOwnershipsV2Filter
import xyz.mcxross.kaptos.model.types.numericFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.util.APTOS_COIN
import xyz.mcxross.kaptos.util.APTOS_FA
import xyz.mcxross.kaptos.util.toOptional

internal suspend fun getInfo(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Result<AccountData, AptosSdkError> =
  getAptosFullNode<AccountData>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getInfo",
        path = "accounts/${accountAddressInput.value}",
        params = params,
      )
    )
    .toResult()

internal suspend fun getModules(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Result<List<MoveModuleBytecode>, AptosSdkError> =
  paginateWithCursor<MoveModuleBytecode>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = "getModules",
        path = "accounts/${accountAddressInput.value}/modules",
        params = params,
      )
    )
    .toResult()

internal suspend fun getModule(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  moduleName: String,
  param: Map<String, Any?>? = null,
): Result<MoveModuleBytecode, AptosSdkError> =
  getAptosFullNode<MoveModuleBytecode>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getModule",
        path = "accounts/${accountAddressInput.value}/module/$moduleName",
        params = param,
      )
    )
    .toResult()

internal suspend fun getTransactions(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Result<List<TransactionResponse>, AptosSdkError> =
  paginateWithCursor<TransactionResponse>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = "getTransactions",
        path = "accounts/${accountAddressInput.value}/transactions",
        params = params,
      )
    )
    .toResult()

internal suspend fun getResources(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Result<List<MoveResource>, AptosSdkError> =
  paginateWithCursor<MoveResource>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = "getResources",
        path = "accounts/${accountAddressInput.value}/resources",
        params = params,
      )
    )
    .toResult()

suspend inline fun <reified T> getResource(
  aptosConfig: AptosConfig,
  accountAddress: AccountAddressInput,
  resourceType: String,
  params: Map<String, Any?>? = null,
): Result<T, AptosSdkError> =
  getAptosFullNode<T>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getResource",
        path = "accounts/${accountAddress.value}/resource/${resourceType}",
        params = params,
      )
    )
    .toResult()

internal suspend fun lookupOriginalAccountAddress(
  aptosConfig: AptosConfig,
  authenticationKey: AccountAddressInput,
  options: LedgerVersionArg,
): Result<AccountAddressInput, AptosSdkError> {
  val resourceResult =
    getResource<OriginatingAddressResponse>(
      aptosConfig,
      AccountAddress.fromString("0x1"),
      "0x1::account::OriginatingAddress",
      mapOf("ledgerVersion" to options.ledgerVersion),
    )

  val resource =
    when (resourceResult) {
      is Result.Ok -> {
        resourceResult.value.data.addressMap.handle
      }
      is Result.Err -> {
        return resourceResult
      }
    }

  val authKeyAddress = AccountAddress.from(authenticationKey)

  val originalAddress =
    getTableItem<String>(
      aptosConfig,
      resource,
      TableItemRequest(
        key = authKeyAddress.toStringLong(),
        key_type = "address",
        value_type = "address",
      ),
    )

  return when (originalAddress) {
    is Result.Ok -> Result.Ok(AccountAddress.fromString(originalAddress.value))
    is Result.Err -> Result.Ok(authenticationKey)
  }
}

internal suspend fun getAccountTransactionsCount(
  config: AptosConfig,
  accountAddressInput: AccountAddressInput,
): Result<Long, AptosIndexerError> {
  val result = handleQuery {
    getGraphqlClient(config)
      .query(GetAccountTransactionsCountQuery(Optional.Present(accountAddressInput.toString())))
  }

  return result
    .andThen { data ->
      val count = data?.account_transactions_aggregate?.aggregate?.count
      if (count != null) {
        Ok(count.toLong())
      } else {
        Err(AptosIndexerError.MissingField("account_transactions_aggregate.aggregate.count"))
      }
    }
    .toResult()
}

internal suspend fun getAccountCoinsData(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  sortOrder: List<FungibleAssetSortOrder>?,
  page: PaginationArgs?,
): Result<GetAccountCoinsDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      val address = AccountAddress.from(accountAddress)

      val filter = currentFungibleAssetBalancesFilter {
        ownerAddress = stringFilter { eq = address.toString() }
      }

      getGraphqlClient(config)
        .query(
          GetAccountCoinsDataQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            page?.limit.toOptional(),
            sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountCoinsCount(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
): Result<Long, AptosIndexerError> {
  val result = handleQuery {
    getGraphqlClient(config).query(GetAccountCoinsCountQuery(accountAddress.value.toOptional()))
  }
  return result
    .andThen { data ->
      val count = data?.current_fungible_asset_balances_aggregate?.aggregate?.count
      if (count != null) {
        Ok(count.toLong())
      } else {
        Err(
          AptosIndexerError.MissingField(
            "current_fungible_asset_balances_aggregate.aggregate.count"
          )
        )
      }
    }
    .toResult()
}

internal suspend fun getAccountCoinAmount(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  coinType: MoveValue.MoveStructId,
  page: PaginationArgs?,
): Result<GetAccountCoinsDataQuery.Data?, AptosIndexerError> {

  val filter = currentFungibleAssetBalancesFilter {
    ownerAddress = stringFilter { eq = accountAddress.value }
    assetType = stringFilter { eq = coinType.value }
  }

  return handleQuery {
      getGraphqlClient(config)
        .query(
          GetAccountCoinsDataQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .toResult()
}

internal suspend fun getAccountCoinAmountFromSmartContract(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  coinType: MoveValue.MoveStructId?,
  faMetadataAddress: AccountAddressInput?,
): Result<Long, AptosSdkError> {
  val senderAddress = AccountAddress.from(accountAddress)
  val normalizedFaAddress = faMetadataAddress?.let { AccountAddress.from(it).toStringLong() }

  val effectiveCoinType =
    coinType
      ?: if (normalizedFaAddress == APTOS_FA) {
        MoveValue.MoveStructId(APTOS_COIN)
      } else {
        null
      }

  val balanceResult =
    if (effectiveCoinType != null) {
      val typeTag =
        try {
          TypeTagStruct(type = StructTag.fromString(effectiveCoinType.value))
        } catch (e: IllegalArgumentException) {
          return Result.Err(
            AptosSdkError.ApiError(
              AptosApiError(
                message = "Invalid coinType: ${effectiveCoinType.value}",
                errorCode = "INVALID_INPUT",
              )
            )
          )
        }

      view<List<MoveValue.String>>(
        config,
        InputViewFunctionData(
          function = "0x1::coin::balance",
          typeArguments = listOf(typeTag),
          functionArguments = listOf(senderAddress),
        ),
      )
    } else if (faMetadataAddress != null) {
      view<List<MoveValue.String>>(
        config,
        InputViewFunctionData(
          function = "0x1::primary_fungible_store::balance",
          typeArguments =
            listOf(TypeTagStruct(type = StructTag.fromString("0x1::object::ObjectCore"))),
          functionArguments = listOf(senderAddress, AccountAddress.from(faMetadataAddress)),
        ),
      )
    } else {
      return Result.Err(
        AptosSdkError.ApiError(
          AptosApiError(
            message = "Either coinType or faMetadataAddress must be provided",
            errorCode = "INVALID_INPUT",
          )
        )
      )
    }

  return when (balanceResult) {
    is Result.Ok -> {
      val amount = balanceResult.value.firstOrNull()?.value?.toLongOrNull() ?: 0L
      Result.Ok(amount)
    }
    is Result.Err -> balanceResult
  }
}

internal suspend fun getAccountAddressesForAuthKey(
  config: AptosConfig,
  filter: AuthKeyAddressFilter,
  sortOrder: List<AuthKeyAddressSortOrder>,
): Result<GetAccountAddressesForAuthKeyQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(GetAccountAddressesForAuthKeyQuery(filter.toOptional(), sortOrder.toOptional()))
    }
    .toResult()

internal suspend fun getAccountCollectionsWithOwnedTokens(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  tokenStd: TokenStandard?,
  sortOrder: List<CollectionOwnershipV2ViewSortOrder>?,
  page: PaginationArgs?,
): Result<GetAccountCollectionsWithOwnedTokensQuery.Data?, AptosIndexerError> =
  handleQuery {
      val filter = currentCollectionOwnershipV2ViewFilter {
        ownerAddress = stringFilter { eq = accountAddress.value }
        currentCollection = currentCollectionsV2Filter {
          this.tokenStandard = stringFilter {
            if (tokenStd != null) {
              eq = tokenStd.name
            }
          }
        }
      }

      getGraphqlClient(config)
        .query(
          GetAccountCollectionsWithOwnedTokensQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountOwnedTokensByTokenData(
  config: AptosConfig,
  filter: TokenOwnershipV2Filter,
  page: PaginationArgs?,
  sortOrder: List<TokenOwnershipV2SortOrder>,
): Result<GetAccountOwnedTokensByTokenDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetAccountOwnedTokensByTokenDataQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountOwnedTokensFromCollection(
  config: AptosConfig,
  filter: TokenOwnershipV2Filter,
  page: PaginationArgs?,
  sortOrder: List<TokenOwnershipV2SortOrder>?,
): Result<GetAccountOwnedTokensFromCollectionQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetAccountOwnedTokensFromCollectionQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountOwnedTokens(
  config: AptosConfig,
  filter: TokenOwnershipV2Filter,
  page: PaginationArgs?,
  sortOrder: List<TokenOwnershipV2SortOrder>,
): Result<GetAccountOwnedTokensQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetAccountOwnedTokensQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountTokensCount(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  page: PaginationArgs?,
): Result<Long, AptosIndexerError> {
  return handleQuery {
      val address = AccountAddress.from(accountAddress).toStringLong()
      val filter = currentTokenOwnershipsV2Filter {
        ownerAddress = stringFilter { eq = address }
        amount = numericFilter { gt = 0 }
      }

      getGraphqlClient(config)
        .query(
          GetAccountTokensCountQuery(
            where_condition = filter.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .map { response ->
      response?.current_token_ownerships_v2_aggregate?.aggregate?.count?.toLong() ?: 0L
    }
    .toResult()
}

internal suspend fun getAccountTransactionsCount(
  config: AptosConfig,
  address: AccountAddress,
): Result<GetAccountTransactionsCountQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config).query(GetAccountTransactionsCountQuery(address.value.toOptional()))
    }
    .toResult()

internal suspend fun getAuthKeysForPublicKey(
  config: AptosConfig,
  filter: PublicKeyAuthKeyFilter?,
  sortOrder: List<PublicKeyAuthKeySortOrder>?,
): Result<GetAuthKeysForPublicKeyQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetAuthKeysForPublicKeyQuery(
            where_condition = filter.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountOwnedTokensFromCollectionAddress(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  collectionAddress: AccountAddressInput,
  tokenStandard: TokenStandard?,
  sortOrder: List<TokenOwnershipV2SortOrder>?,
  page: PaginationArgs?,
): Result<GetAccountOwnedTokensFromCollectionQuery.Data?, AptosIndexerError> =
  handleQuery {
      val accAddress = AccountAddress.from(accountAddress).toLongAddress()
      val collAddress = AccountAddress.from(collectionAddress).toLongAddress()

      val filter =
        Current_token_ownerships_v2_bool_exp(
          owner_address = stringFilter { eq = accAddress.value }.toOptional(),
          current_token_data =
            currentTokenDatasV2Filter { collectionId = stringFilter { eq = collAddress.value } }
              .toOptional(),
          amount = numericFilter { gt = 0 }.toOptional(),
          token_standard = stringFilter { eq = tokenStandard?.name?.lowercase() }.toOptional(),
        )

      getGraphqlClient(config)
        .query(
          GetAccountOwnedTokensFromCollectionQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getAccountOwnedObjects(
  aptosConfig: AptosConfig,
  accountAddress: AccountAddressInput,
  sortOrder: List<ObjectSortOrder>? = null,
  page: PaginationArgs? = null,
): Result<GetObjectDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      val addr = AccountAddress.from(accountAddress).toLongAddress()
      val filter = currentObjectsFilter { ownerAddress = stringFilter { eq = addr.toStringLong() } }
      getGraphqlClient(aptosConfig)
        .query(
          GetObjectDataQuery(
            where_condition = filter.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()

/**
 * Waits for the indexer to sync up to the specified ledger version. The timeout is 3 seconds.
 *
 * @param aptosConfig The configuration object for Aptos.
 * @param minimumLedgerVersion The minimum ledger version that the indexer should sync to.
 * @param processorType Optional: The type of processor to check the last success version from.
 * @throws kotlinx.coroutines.TimeoutCancellationException if the indexer does not reach the
 *   `minimumLedgerVersion` within the 3-second timeout.
 */
internal suspend fun waitForIndexer(
  aptosConfig: AptosConfig,
  minimumLedgerVersion: Long,
  processorType: ProcessorType?,
) = coroutineScope {
  var indexerVersion = -1L
  withTimeout(3000) {
    while (indexerVersion < minimumLedgerVersion) {
      if (processorType == null) {
        indexerVersion = getIndexerLastSuccessVersion(aptosConfig).getOrNull() ?: -1L
      } else {
        val version: Long? =
          getProcessorStatus(aptosConfig, processorType)
            .getOrNull()
            ?.processor_status
            ?.firstOrNull()
            ?.last_success_version
            ?.longOrNull()

        indexerVersion = version ?: -1L
      }

      if (indexerVersion >= minimumLedgerVersion) {
        break
      }

      delay(200.milliseconds)
    }
  }
}
