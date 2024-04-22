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
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.generated.GetNumberOfDelegators
import xyz.mcxross.kaptos.generated.inputs.String_comparison_exp
import xyz.mcxross.kaptos.generated.inputs.num_active_delegator_per_pool_bool_exp
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.NumberOfDelegators
import xyz.mcxross.kaptos.model.Option

internal suspend fun getNumberOfDelegators(
  aptosConfig: AptosConfig,
  poolAddress: AccountAddressInput,
): Option<NumberOfDelegators> {
  val delegators =
    GetNumberOfDelegators(
      GetNumberOfDelegators.Variables(
        where_condition =
          num_active_delegator_per_pool_bool_exp(
            pool_address = String_comparison_exp(_eq = poolAddress.value)
          )
      )
    )

  val response: KotlinxGraphQLResponse<NumberOfDelegators> =
    try {
      indexerClient(aptosConfig).execute(delegators)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}
