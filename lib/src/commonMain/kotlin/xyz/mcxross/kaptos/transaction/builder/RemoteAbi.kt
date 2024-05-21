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

import xyz.mcxross.kaptos.internal.getModule
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser

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
): Option<MoveFunction> {
  return when (val module = getModule(aptosConfig, HexInput(moduleAddress), moduleName)) {
    is Option.Some -> {
      val function = module.value.abi?.exposedFunctions?.find { it.name == functionName }
      if (function != null) {
        Option.Some(function)
      } else {
        Option.None
      }
    }
    is Option.None -> {
      Option.None
    }
  }
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
): Option<ViewFunctionABI> {

  when (val functionAbi = fetchFunctionAbi(moduleAddress, moduleName, functionName, aptosConfig)) {
    is Option.None -> {
      throw IllegalArgumentException(
        "Could not find view function ABI for '${moduleAddress}::${moduleName}::${functionName}"
      )
    }
    is Option.Some -> {
      if (!functionAbi.value.isView) {
        throw IllegalArgumentException(
          "Function '${moduleAddress}::${moduleName}::${functionName}' is not a view function"
        )
      }

      val params = functionAbi.value.params.map { TypeTagParser.parseTypeTag(it, true) }

      val returnTypes = functionAbi.value.`return`.map { TypeTagParser.parseTypeTag(it, true) }

      return Option.Some(
        ViewFunctionABI(
          typeParameters = functionAbi.value.genericTypeParams,
          parameters = params,
          returnTypes = returnTypes,
        )
      )
    }
  }
}
