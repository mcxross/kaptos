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

interface InputGenerateTransactionPayloadData

/** The data needed to generate an Entry Function payload */
data class InputEntryFunctionData(
  val function: MoveFunctionId,
  val typeArguments: List<TypeTag>,
  val functionArguments: List<EntryFunctionArgument>,
  val abi: EntryFunctionABI? = null,
) : InputGenerateTransactionPayloadData

data class TypeArguments(val typeArguments: List<TypeTag>)

class TypeArgumentsBuilder {
  private var typeArguments: List<TypeTag>? = null

  operator fun TypeTag.unaryPlus() {
    if (typeArguments == null) {
      typeArguments = mutableListOf()
    }
    typeArguments = typeArguments!! + this
  }

  fun build(): TypeArguments {
    return TypeArguments(
      typeArguments = typeArguments ?: throw IllegalArgumentException("typeArguments must be set")
    )
  }
}

fun typeArguments(block: TypeArgumentsBuilder.() -> Unit): TypeArguments {
  return TypeArgumentsBuilder().apply(block).build()
}

fun emptyTypeArguments(): TypeArguments {
  return TypeArguments(emptyList())
}

data class FunctionArguments(val functionArguments: List<EntryFunctionArgument>)

class FunctionArgumentsBuilder {
  private var functionArguments: List<EntryFunctionArgument>? = null

  operator fun EntryFunctionArgument.unaryPlus() {
    if (functionArguments == null) {
      functionArguments = mutableListOf()
    }
    functionArguments = functionArguments!! + this
  }

  fun build(): FunctionArguments {
    return FunctionArguments(
      functionArguments =
        functionArguments ?: throw IllegalArgumentException("functionArguments must be set")
    )
  }
}

fun functionArguments(block: FunctionArgumentsBuilder.() -> Unit): FunctionArguments {
  return FunctionArgumentsBuilder().apply(block).build()
}

class InputEntryFunctionDataBuilder {
  var function: MoveFunctionId? = null
  var typeArguments: TypeArguments? = null
  var functionArguments: FunctionArguments? = null
  var abi: EntryFunctionABI? = null

  fun build(): InputEntryFunctionData {
    return InputEntryFunctionData(
      function = function ?: throw IllegalArgumentException("function must be set"),
      typeArguments = typeArguments?.typeArguments ?: emptyList(),
      functionArguments =
        functionArguments?.functionArguments
          ?: throw IllegalArgumentException("functionArguments must be set"),
      abi = abi,
    )
  }
}

fun entryFunctionData(
  block: InputEntryFunctionDataBuilder.() -> Unit
): InputEntryFunctionData {
  return InputEntryFunctionDataBuilder().apply(block).build()
}
