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

package xyz.mcxross.kaptos.internal

import com.github.michaelbull.result.map
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.ObjectFilter
import xyz.mcxross.kaptos.model.ObjectSortOrder
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.types.currentObjectsFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.util.toOptional

internal suspend fun getObjectData(
  aptosConfig: AptosConfig,
  filter: ObjectFilter,
  sortOrder: List<ObjectSortOrder>?,
  page: PaginationArgs?,
): Result<GetObjectDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(aptosConfig)
        .query(
          GetObjectDataQuery(
            where_condition = filter.toOptional(),
            order_by = sortOrder.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getObjectDataByObjectAddress(
  aptosConfig: AptosConfig,
  objectAddress: AccountAddressInput,
  sortOrder: List<ObjectSortOrder>?,
  page: PaginationArgs?,
): Result<GetObjectDataQuery.Current_object?, AptosIndexerError> {
  val filter = currentObjectsFilter {
    this.objectAddress = stringFilter { eq = objectAddress.toString() }
  }

  val resolution = getObjectData(aptosConfig, filter, sortOrder, page)

  return resolution
    .toInternalResult()
    .map { data -> data?.current_objects?.firstOrNull() }
    .toResult()
}
