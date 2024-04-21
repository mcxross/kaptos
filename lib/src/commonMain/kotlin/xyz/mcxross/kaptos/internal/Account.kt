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

import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.client.paginateWithCursor
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

internal suspend fun getResource(
  aptosConfig: AptosConfig,
  accountAddress: AccountAddressInput,
  resourceType: String,
  params: Map<String, Any?>? = null,
): Option<MoveResource> {
  return getAptosFullNode<MoveResource>(
      RequestOptions.GetAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getResource",
        path = "accounts/${accountAddress.value}/resource/${resourceType}",
        params = params,
      )
    )
    .second
}
