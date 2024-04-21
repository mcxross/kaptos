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
import xyz.mcxross.graphql.client.types.GraphQLClientResponse
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.generated.GetTokenData
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.model.TokenData
import xyz.mcxross.kaptos.protocol.DigitalAsset

class DigitalAsset(val settings: AptosConfig) : DigitalAsset {
  @Throws(AptosException::class, CancellationException::class)
  override suspend fun getTokenData(offset: Int?, limit: Int?): Option<TokenData> {

    val tokenData = GetTokenData(GetTokenData.Variables(offset = offset, limit = limit))
    indexerClient(settings).demo(tokenData)

   /* val response: GraphQLClientResponse<GetTokenData.Result> =
      try {
        indexerClient(settings).demo()
      } catch (e: Exception) {
        throw AptosException("GraphQL query execution failed: $e")
      }

    val data = response.data ?: throw AptosException("GraphQL query returned no data")*/

    return Option.None
  }
}
