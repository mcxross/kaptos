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
package xyz.mcxross.kaptos.internal.operations

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.internal.getObjectDataByObjectAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.ObjectSortOrder
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.ProcessorType
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

internal class ObjectOperations(val config: TransportConfig) {

  suspend fun getObjectDataByObjectAddress(
    objectAddress: AccountAddressInput,
    sortOrder: List<ObjectSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetObjectDataQuery.Current_object?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.OBJECT_PROCESSOR)
    return getObjectDataByObjectAddress(config, objectAddress, sortOrder, page)
  }
}
