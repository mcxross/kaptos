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

import xyz.mcxross.kaptos.model.*

interface Account {

  /**
   * Queries the current state for an Aptos account given its account address
   *
   * @param accountAddress Aptos account address
   * @returns AccountData
   */
  suspend fun getAccountInfo(
    accountAddress: AccountAddressInput,
    params: LedgerVersionQueryParam.() -> Unit = {},
  ): Option<AccountData>

  /**
   * Queries for all modules in an account given an account address
   *
   * @param accountAddress Aptos account address
   * @return List<MoveModuleBytecode>
   */
  suspend fun getAccountModules(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit = {},
  ): Option<List<Option<List<MoveModuleBytecode>>>>

  /**
   * Queries for a specific account module given account address and module name
   *
   * @param accountAddress Aptos account address
   * @param moduleName Name of the module
   * @param param [LedgerVersionQueryParam] to optionally configure the ledger version
   * @returns [MoveModuleBytecode]
   */
  suspend fun getAccountModule(
    accountAddress: AccountAddressInput,
    moduleName: String,
    param: LedgerVersionQueryParam.() -> Unit = {},
  ): Option<MoveModuleBytecode>

  /**
   * Queries account transactions given an account address
   *
   * @param accountAddress Aptos account address
   * @param params [PaginationQueryParams] to optionally configure the pagination. This includes
   *   limit and offset.
   * @returns List<Option<List<[TransactionResponse]>>>
   */
  suspend fun getAccountTransactions(
    accountAddress: AccountAddressInput,
    params: PaginationQueryParams.() -> Unit = {},
  ): Option<List<Option<List<TransactionResponse>>>>

  /**
   * Queries all account resources given an account address
   *
   * @param accountAddress Aptos account address
   * @param params [SpecificPaginationQueryParams] to optionally configure the pagination. This
   *   includes ledger version, limit and start
   * @returns List<[MoveResource]>
   */
  suspend fun getAccountResources(
    accountAddress: AccountAddressInput,
    params: SpecificPaginationQueryParams.() -> Unit = {},
  ): Option<List<Option<List<MoveResource>>>>

  /**
   * Queries a specific account resource given an account address and resource name
   *
   * @param accountAddress Aptos account address
   * @param resourceName Name of the resource
   * @param param [LedgerVersionQueryParam] to optionally configure the ledger version.
   * @returns [MoveResource]
   */
  suspend fun getAccountResource(
    accountAddress: AccountAddressInput,
    resourceName: String,
    param: LedgerVersionQueryParam.() -> Unit = {},
  ): Option<MoveResource>
}
