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

import xyz.mcxross.kaptos.extension.toStructTag
import xyz.mcxross.kaptos.model.*

internal suspend fun transferCoinTransaction(
  aptosConfig: AptosConfig,
  from: AccountAddressInput,
  to: AccountAddressInput,
  amount: ULong,
  coinType: String,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  val data =
    InputGenerateSingleSignerRawTransactionData(
      sender = from,
      data =
        inputEntryFunctionData {
          function = "0x1::coin::transfer"
          typeArguments = typeArguments { +TypeTagStruct(type = coinType.toStructTag()) }
          functionArguments = functionArguments {
            +MoveString(to.toString())
            +U64(amount)
          }
        },
      options = options,
      withFeePayer = false,
      secondarySignerAddresses = null,
    )
  return generateTransaction(aptosConfig, data) as SimpleTransaction
}
