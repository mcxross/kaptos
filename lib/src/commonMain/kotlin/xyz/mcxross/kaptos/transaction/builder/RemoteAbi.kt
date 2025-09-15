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

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.GraphQLError
import xyz.mcxross.kaptos.internal.getModule
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser
import xyz.mcxross.kaptos.util.findFirstNonSignerArg

/**
 * Fetches a function ABI from the on-chain module ABI. It doesn't validate whether it's a view or
 * entry function.
 *
 * @param moduleAddress
 * @param moduleName
 * @param functionName
 * @param aptosConfig
 */
suspend fun fetchFunctionAbi(
  moduleAddress: String,
  moduleName: String,
  functionName: String,
  aptosConfig: AptosConfig,
): Result<MoveFunction, Exception> {
  return when (val module = getModule(aptosConfig, HexInput(moduleAddress), moduleName)) {
    is Result.Ok -> {
      val fn = module.value.abi?.exposedFunctions?.find { it.name == functionName }
      if (fn != null) {
        Result.Ok(fn)
      } else {
        Result.Err(
          AptosIndexerError.GraphQL(
            listOf(GraphQLError("Function $functionName not found in module $moduleName"))
          )
        )
      }
    }
    is Result.Err -> Result.Err(module.error)
  }
}

suspend fun fetchEntryFunctionAbi(
  aptosConfig: AptosConfig,
  moduleAddress: String,
  moduleName: String,
  functionName: String,
): Result<EntryFunctionABI, Exception> {

  val functionAbi =
    fetchFunctionAbi(moduleAddress, moduleName, functionName, aptosConfig)
      .expect(
        "Could not find entry function ABI for '${moduleAddress}::${moduleName}::${functionName}"
      )

  if (!functionAbi.isEntry) {
    throw IllegalArgumentException(
      "Function '${moduleAddress}::${moduleName}::${functionName}' is not an entry function"
    )
  }

  val numSigners = findFirstNonSignerArg(functionAbi)

  val params: MutableList<TypeTag> = mutableListOf()

  for (i in numSigners until functionAbi.params.size) {
    params.add(TypeTagParser.parseTypeTag(functionAbi.params[i], true))
  }

  return Result.Ok(
    EntryFunctionABI(typeParameters = functionAbi.genericTypeParams, parameters = params)
  )
}

/**
 * Fetches the ABI for a view function from the module
 *
 * @param moduleAddress
 * @param moduleName
 * @param functionName
 * @param aptosConfig
 */
suspend fun fetchViewFunctionAbi(
  aptosConfig: AptosConfig,
  moduleAddress: String,
  moduleName: String,
  functionName: String,
): Result<ViewFunctionABI, Exception> {
  return when (
    val functionAbi = fetchFunctionAbi(moduleAddress, moduleName, functionName, aptosConfig)
  ) {
    is Result.Ok -> {
      val fn = functionAbi.value

      if (!fn.isView) {
        return Result.Err(
          AptosIndexerError.GraphQL(
            listOf(
              GraphQLError(
                "Function '${moduleAddress}::${moduleName}::${functionName}' is not a view function"
              )
            )
          )
        )
      }

      val params = fn.params.map { TypeTagParser.parseTypeTag(it, true) }
      val returnTypes = fn.`return`.map { TypeTagParser.parseTypeTag(it, true) }

      Result.Ok(
        ViewFunctionABI(
          typeParameters = fn.genericTypeParams,
          parameters = params,
          returnTypes = returnTypes,
        )
      )
    }
    is Result.Err -> functionAbi
  }
}
