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

import xyz.mcxross.graphql.client.types.KotlinxGraphQLResponse
import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.client.paginateWithCursor
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.generated.GetAccountCoinsData
import xyz.mcxross.kaptos.generated.GetAccountTransactionsCount
import xyz.mcxross.kaptos.generated.inputs.String_comparison_exp
import xyz.mcxross.kaptos.generated.inputs.current_fungible_asset_balances_bool_exp
import xyz.mcxross.kaptos.model.*

internal suspend fun getInfo(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Option<AccountData> {
  return getAptosFullNode<AccountData>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getInfo",
        path = "accounts/${accountAddressInput.value}",
        params = params,
      )
    )
    .second
}

internal suspend fun getModules(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Option<List<Option<List<MoveModuleBytecode>>>> {
  val response =
    paginateWithCursor<MoveModuleBytecode>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = "getModules",
        path = "accounts/${accountAddressInput.value}/modules",
        params = params,
      )
    )

  return when (response) {
    is Option.Some -> {
      Option.Some(response.value.map { it.second })
    }
    is Option.None -> {
      Option.None
    }
  }
}

internal suspend fun getModule(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  moduleName: String,
  param: Map<String, Any?>? = null,
): Option<MoveModuleBytecode> {
  return getAptosFullNode<MoveModuleBytecode>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getModule",
        path = "accounts/${accountAddressInput.value}/module/$moduleName",
        params = param,
      )
    )
    .second
}

internal suspend fun getTransactions(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Option<List<Option<List<TransactionResponse>>>> {
  val response =
    paginateWithCursor<TransactionResponse>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = "getTransactions",
        path = "accounts/${accountAddressInput.value}/transactions",
        params = params,
      )
    )

  return when (response) {
    is Option.Some -> {
      Option.Some(response.value.map { it.second })
    }
    is Option.None -> {
      Option.None
    }
  }
}

internal suspend fun getResources(
  aptosConfig: AptosConfig,
  accountAddressInput: AccountAddressInput,
  params: Map<String, Any?>? = null,
): Option<List<Option<List<MoveResource>>>> {

  val response =
    paginateWithCursor<MoveResource>(
      RequestOptions.AptosRequestOptions(
        aptosConfig = aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = "getResources",
        path = "accounts/${accountAddressInput.value}/resources",
        params = params,
      )
    )

  return when (response) {
    is Option.Some -> {
      Option.Some(response.value.map { it.second })
    }
    is Option.None -> {
      Option.None
    }
  }
}

suspend inline fun <reified T> getResource(
  aptosConfig: AptosConfig,
  accountAddress: AccountAddressInput,
  resourceType: String,
  params: Map<String, Any?>? = null,
): Option<T> {
  return getAptosFullNode<T>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getResource",
        path = "accounts/${accountAddress.value}/resource/${resourceType}",
        params = params,
      )
    )
    .second
}

internal suspend fun getAccountTransactionsCount(
  config: AptosConfig,
  accountAddressInput: AccountAddressInput,
  minimumLedgerVersion: Long? = null,
): Option<Long> {
  val txCount =
    GetAccountTransactionsCount(
      GetAccountTransactionsCount.Variables(address = accountAddressInput.value)
    )
  val response: KotlinxGraphQLResponse<GetAccountTransactionsCount.Result> =
    try {
      indexerClient(config).execute(txCount)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data.account_transactions_aggregate.aggregate?.count?.toLong() ?: 0)
}

internal suspend fun getAccountCoinsData(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  minimumLedgerVersion: Long? = null,
): Option<AccountCoinsData> {
  val coinsData =
    GetAccountCoinsData(
      GetAccountCoinsData.Variables(
        where_condition =
          current_fungible_asset_balances_bool_exp(
            owner_address = String_comparison_exp(_eq = accountAddress.value)
          )
      )
    )

  val response: KotlinxGraphQLResponse<AccountCoinsData> =
    try {
      indexerClient(config).execute(coinsData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

internal suspend fun getAccountCoinsCount(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  minimumLedgerVersion: Long? = null,
): Option<Int> {
  val data =
    GetAccountCoinsData(
      GetAccountCoinsData.Variables(
        where_condition =
          current_fungible_asset_balances_bool_exp(
            owner_address = String_comparison_exp(_eq = accountAddress.value)
          )
      )
    )

  val response: KotlinxGraphQLResponse<GetAccountCoinsData.Result> =
    try {
      indexerClient(config).execute(data)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val result = response.data ?: return Option.None

  return Option.Some(result.current_fungible_asset_balances.size)
}

internal suspend fun getAccountCoinAmount(
  config: AptosConfig,
  accountAddress: AccountAddressInput,
  coinType: MoveValue.MoveStructId,
  minimumLedgerVersion: Long? = null,
): Option<Int> {
  val data =
    GetAccountCoinsData(
      GetAccountCoinsData.Variables(
        where_condition =
          current_fungible_asset_balances_bool_exp(
            owner_address = String_comparison_exp(_eq = accountAddress.value),
            asset_type = String_comparison_exp(_eq = coinType.value),
          )
      )
    )

  val response: KotlinxGraphQLResponse<GetAccountCoinsData.Result> =
    try {
      indexerClient(config).execute(data)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val result = response.data ?: return Option.None

  return Option.Some(result.current_fungible_asset_balances.firstOrNull()?.amount?.toInt() ?: 0)
}
