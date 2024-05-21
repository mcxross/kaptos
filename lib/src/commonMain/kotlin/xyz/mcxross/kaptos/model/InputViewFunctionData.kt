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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import xyz.mcxross.kaptos.serialize.EntryFunctionArgumentSerializer
import xyz.mcxross.kaptos.serialize.TypeTagSerializer

/** The data needed to generate a View Function payload */
@Serializable
data class InputViewFunctionData(
  val function: MoveFunctionId,
  @SerialName("type_arguments")
  val typeArguments: List<@Serializable(with = TypeTagSerializer::class) TypeTag>,
  @SerialName("arguments")
  val functionArguments:
    List<@Serializable(with = EntryFunctionArgumentSerializer::class) EntryFunctionArgument>,
  @Transient val abi: ViewFunctionABI? = null,
)
