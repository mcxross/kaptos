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

import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser

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

  private var typeArgsList: List<TypeTag>? = null
  private var argsList: List<EntryFunctionArgument>? = null

  fun typeArgs(vararg types: String) {
    typeArgsList = types.map { TypeTagParser.parseTypeTag(it) }
  }

  fun typeArgs(vararg types: TypeTag) {
    typeArgsList = types.toList()
  }

  fun args(vararg args: Any) {
    argsList = args.map { coerceToEntryFunctionArgument(it) }
  }

  fun build(): InputEntryFunctionData {
    return InputEntryFunctionData(
      function = function ?: throw IllegalArgumentException("function must be set"),
      typeArguments = typeArgsList ?: typeArguments?.typeArguments ?: emptyList(),
      functionArguments = argsList ?: functionArguments?.functionArguments ?: emptyList(),
      abi = abi,
    )
  }
}

internal fun coerceToEntryFunctionArgument(value: Any): EntryFunctionArgument =
  when (value) {
    is EntryFunctionArgument -> value
    is Boolean -> Bool(value)
    is Byte -> U8(value)
    is UByte -> U8(value.toByte())
    is Short -> U16(value.toUShort())
    is UShort -> U16(value)
    is Int -> U32(value.toUInt())
    is UInt -> U32(value)
    is Long -> U64(value.toULong())
    is ULong -> U64(value)
    is String -> MoveString(value)
    is ByteArray -> MoveVector.u8(value)
    is List<*> -> coerceList(value)
    else -> throw IllegalArgumentException(
      "Cannot coerce ${value::class.simpleName} to EntryFunctionArgument. " +
        "Supported types: Boolean, Byte, UByte, Short, UShort, Int, UInt, Long, ULong, " +
        "String, ByteArray, List, AccountAddress, HexInput, and EntryFunctionArgument subtypes."
    )
  }

private fun coerceList(list: List<*>): MoveVector<EntryFunctionArgument> {
  if (list.isEmpty()) return MoveVector(emptyList())
  return MoveVector(list.map { coerceToEntryFunctionArgument(it ?: throw IllegalArgumentException("null elements in list are not supported")) })
}

fun entryFunctionData(block: InputEntryFunctionDataBuilder.() -> Unit): InputEntryFunctionData {
  return InputEntryFunctionDataBuilder().apply(block).build()
}
