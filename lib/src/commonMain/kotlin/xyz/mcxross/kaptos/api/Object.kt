/*
 * Copyright 2025 McXross
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

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.internal.getObjectDataByObjectAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.ObjectSortOrder
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.ProcessorType
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.protocol.Object
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

/**
 * A class for querying Aptos `Object` related data from the indexer.
 *
 * @property config The [AptosConfig] for connecting to the network.
 */
class Object(override val config: AptosConfig) : Object {
  /**
   * Queries for object data based on a specified object address.
   *
   * This function can wait for the indexer to be synchronized to a specific ledger version before
   * querying.
   *
   * ## Usage
   *
   * ```kotlin
   * val objectAddress = AccountAddress.fromString("0x000000000000000000000000000000000000000000000000000000000000000a")
   * val resolution = aptos.getObjectDataByObjectAddress(objectAddress)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved object data: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving object data: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param objectAddress The address of the object to retrieve data for.
   * @param sortOrder An optional list of sorting options for the results.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  override suspend fun getObjectDataByObjectAddress(
    objectAddress: AccountAddressInput,
    sortOrder: List<ObjectSortOrder>?,
    page: PaginationArgs?,
    minimumLedgerVersion: Long?,
  ): Result<GetObjectDataQuery.Current_object?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.OBJECT_PROCESSOR)
    return getObjectDataByObjectAddress(config, objectAddress, sortOrder, page)
  }
}
