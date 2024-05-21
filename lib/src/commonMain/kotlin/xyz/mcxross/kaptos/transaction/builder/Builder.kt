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
package xyz.mcxross.kaptos.transaction.builder

import xyz.mcxross.kaptos.extension.parts
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.EntryFunction

suspend fun generateViewFunctionPayload(
  aptosConfig: AptosConfig,
  inputViewFunctionData: InputViewFunctionData,
) {

  val functionParts = inputViewFunctionData.function.parts()

  val functionAbi: FunctionABI =
    if (inputViewFunctionData.abi != null) {
      inputViewFunctionData.abi
    } else {
      val response =
        fetchViewFunctionAbi(
          aptosConfig,
          functionParts.first,
          functionParts.second,
          functionParts.third,
        )
      when (response) {
        is Option.Some -> response.value
        is Option.None ->
          throw IllegalArgumentException(
            "Could not find view function ABI for '${functionParts.first}::${functionParts.second}::${functionParts.third}"
          )
      }
    }
}

fun generateViewFunctionPayloadWithABI(
  aptosConfig: AptosConfig,
  inputViewFunctionData: InputViewFunctionData,
  functionAbi: FunctionABI,
): EntryFunction {
  val parts = inputViewFunctionData.function.parts()

  // Check the type argument count against the ABI
  if ((inputViewFunctionData.typeArguments?.size ?: 0) != functionAbi.typeParameters.size) {
    throw IllegalArgumentException(
      "Type argument count does not match the function ABI for '${functionAbi}. Expected ${functionAbi.typeParameters.size}, got '${inputViewFunctionData.typeArguments?.size ?: 0}'"
    )
  }

  if ((inputViewFunctionData.functionArguments?.size ?: 0) != functionAbi.parameters.size) {
    throw IllegalArgumentException(
      "Too few arguments for '${parts.first}::${parts.second}::${parts.third}', expected ${functionAbi.parameters.size} but got ${inputViewFunctionData.functionArguments?.size ?: 0}"
    )
  }

  return EntryFunction(
    moduleName = ModuleId(AccountAddress(parts.first), Identifier(parts.second)),
    functionName = Identifier(parts.third),
    typeArgs = inputViewFunctionData.typeArguments ?: emptyList(),
    args = inputViewFunctionData.functionArguments ?: emptyList(),
  )
}
