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
package xyz.mcxross.kaptos.transaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.model.EntryFunctionArgument
import xyz.mcxross.kaptos.model.Identifier
import xyz.mcxross.kaptos.model.ModuleId
import xyz.mcxross.kaptos.model.TypeTag

@Serializable
data class EntryFunction(
  @SerialName("module_name") val moduleName: ModuleId,
  @SerialName("function_name") val functionName: Identifier,
  @SerialName("type_args") val typeArgs: List<TypeTag>,
  val args: List<EntryFunctionArgument>,
)
