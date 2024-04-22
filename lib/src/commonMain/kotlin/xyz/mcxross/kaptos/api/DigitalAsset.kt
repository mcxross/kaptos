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

import kotlin.coroutines.cancellation.CancellationException
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.internal.getCollectionData
import xyz.mcxross.kaptos.internal.getCollectionDataByCollectionId
import xyz.mcxross.kaptos.internal.getTokenData
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.DigitalAsset

/**
 * Digital asset API namespace. This class provides functionality to reading and writing digital
 * assets' related information.
 *
 * @property config AptosConfig object for configuration
 */
class DigitalAsset(val config: AptosConfig) : DigitalAsset {

  /**
   * Queries data of a specific collection by the collection creator address and the collection
   * name.
   *
   * If, for some reason, a creator account has 2 collections with the same name in v1 and v2, can
   * pass an optional `tokenStandard` parameter to query a specific standard
   *
   * @param creatorAddress the address of the collection's creator
   * @param collectionName the name of the collection
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @param tokenStandard the token standard to query
   * @returns [CollectionData] response type
   */
  override suspend fun getCollectionData(
    creatorAddress: AccountAddressInput,
    collectionName: String,
    minimumLedgerVersion: Long?,
    tokenStandard: TokenStandard?,
  ): Option<CollectionData?> =
    getCollectionData(config, creatorAddress, collectionName, tokenStandard)

  /**
   * Queries data of a specific collection by the collection ID.
   *
   * @param collectionId the ID of the collection, it's the same thing as the address of the
   *   collection object
   * @param minimumLedgerVersion Optional ledger version to sync up to, before querying
   * @returns [CollectionData] response type
   */
  override suspend fun getCollectionDataByCollectionId(
    collectionId: String,
    minimumLedgerVersion: Long?,
  ): Option<CollectionData?> =
    getCollectionDataByCollectionId(config, collectionId, minimumLedgerVersion)

  @Throws(AptosException::class, CancellationException::class)
  override suspend fun getTokenData(offset: Int?, limit: Int?): Option<TokenData> =
    getTokenData(config, offset, limit)
}
