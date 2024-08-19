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

package xyz.mcxross.kaptos.model

import io.ktor.client.request.*
import io.ktor.client.statement.*
import xyz.mcxross.kaptos.generated.*

typealias AptosResponse = HttpResponse

typealias AptosRequest = HttpRequest

typealias AccountCoinsData = GetAccountCoinsData.Result

typealias ChainTopUserTransactions = GetChainTopUserTransactions.Result

typealias CollectionData = GetCollectionData.Result

typealias TokenData = GetTokenData.Result

typealias NumberOfDelegators = GetNumberOfDelegators.Result

typealias MoveModuleId = String

typealias MoveStructId = String

typealias ProcessorStatus = GetProcessorStatus.Result

typealias MoveFunctionId = MoveModuleId

typealias AnyTransactionPayloadInstance = TransactionPayload
