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

import xyz.mcxross.kaptos.internal.getResource
import xyz.mcxross.kaptos.model.*

/**
 * Account API namespace. This interface provides functionality to reading and writing account
 * related information.
 *
 * @property config AptosConfig object for configuration
 */
interface Account {

  val config: AptosConfig

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
   * Queries the current count of transactions submitted by an account
   *
   * @param accountAddress The account address we want to get the total count for
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Current count of transactions made by an account
   */
  suspend fun getAccountTransactionsCount(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Option<Long>

  /**
   * Queries an account's coins data
   *
   * @param accountAddress The account address we want to get the coins data for
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns Array with the coins data
   */
  // TODO: Add more parameters to the query
  suspend fun getAccountCoinsData(
    accountAddress: AccountAddressInput,
    minimumLedgerVersion: Long? = null,
  ): Option<AccountCoinsData>

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
  ): Option<Int>

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
  ): Option<Long>

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
    coinType: MoveValue.MoveStructId,
    minimumLedgerVersion: Long? = null,
  ): Option<Long>
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
): Option<T> {
  val ledgerVersionQueryParam = LedgerVersionQueryParam().apply(param)
  return getResource(this.config, accountAddress, resourceName, ledgerVersionQueryParam.toMap())
}
