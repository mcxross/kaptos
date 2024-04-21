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

import xyz.mcxross.kaptos.internal.*
import xyz.mcxross.kaptos.internal.getInfo
import xyz.mcxross.kaptos.internal.getModule
import xyz.mcxross.kaptos.internal.getModules
import xyz.mcxross.kaptos.internal.getResources
import xyz.mcxross.kaptos.internal.getTransactions
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.Account

class Account(private val config: AptosConfig) : Account {

  /**
   * Queries the current state for an Aptos account given its account address
   *
   * @param accountAddress Aptos account address
   * @returns AccountData
   */
  override suspend fun getAccountInfo(
    accountAddress: AccountAddressInput,
    params: LedgerVersionQueryParam.() -> Unit,
  ): Option<AccountData> {
    val queryParams = LedgerVersionQueryParam().apply(params)
    return getInfo(this.config, accountAddress, queryParams.toMap())
  }

  /**
   * Queries for all modules in an account given an account address
   *
   * Note: In order to get all account modules, this function may call the API multiple times as it
   * auto paginates.
   *
   * @param accountAddress Aptos account address
   * @return List<MoveModuleBytecode>
   */
  override suspend fun getAccountModules(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit,
  ): Option<List<Option<List<MoveModuleBytecode>>>> {
    val paginationParams = SpecificPaginationQueryParams().apply(params)
    return getModules(config, accountAddress, paginationParams.toMap())
  }

  /**
   * Queries for a specific account module given account address and module name
   *
   * @param accountAddress Aptos account address
   * @returns [MoveModuleBytecode]
   */
  override suspend fun getAccountModule(
    accountAddress: AccountAddressInput,
    moduleName: String,
    param: LedgerVersionQueryParam.() -> Unit,
  ): Option<MoveModuleBytecode> {
    val ledgerVersionQueryParam = LedgerVersionQueryParam().apply(param)
    return getModule(config, accountAddress, moduleName, ledgerVersionQueryParam.toMap())
  }

  /**
   * Queries account transactions given an account address
   *
   * Note: In order to get all account transactions, this function may call the API multiple times
   * as it auto paginates.
   *
   * @param accountAddress Aptos account address
   * @param params [PaginationQueryParams] to optionally configure the pagination. This includes
   *   limit and offset.
   * @returns List<Option<List<[TransactionResponse]>>>
   */
  override suspend fun getAccountTransactions(
    accountAddress: AccountAddressInput,
    params: PaginationQueryParams.() -> Unit,
  ): Option<List<Option<List<TransactionResponse>>>> {
    val paginationParams = PaginationQueryParams().apply(params)
    return getTransactions(config, accountAddress, paginationParams.toMap())
  }

  /**
   * Queries all account resources given an account address
   *
   * Note: In order to get all account resources, this function may call the API multiple times as
   * it auto paginates.
   *
   * @param accountAddress Aptos account address
   * @returns List<[MoveResource]>
   */
  override suspend fun getAccountResources(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit,
  ): Option<List<Option<List<MoveResource>>>> {
    val paginationParams = SpecificPaginationQueryParams().apply(params)
    return getResources(config, accountAddress, paginationParams.toMap())
  }

  /**
   * Queries a specific account resource given an account address and resource name
   *
   * @param accountAddress Aptos account address
   * @param resourceName Name of the resource
   * @param param [LedgerVersionQueryParam] to optionally configure the ledger version.
   * @returns [MoveResource]
   */
  override suspend fun getAccountResource(
    accountAddress: AccountAddressInput,
    resourceName: String,
    param: LedgerVersionQueryParam.() -> Unit,
  ): Option<MoveResource> {
    val ledgerVersionQueryParam = LedgerVersionQueryParam().apply(param)
    return getResource(config, accountAddress, resourceName, ledgerVersionQueryParam.toMap())
  }
}
