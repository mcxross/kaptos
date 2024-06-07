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
package xyz.mcxross.kaptos.transaction.typetag

import xyz.mcxross.kaptos.model.*

enum class TypeTagParserErrorType(val errorMessage: String) {
  InvalidTypeTag("unknown type"),
  UnexpectedGenericType("unexpected generic type"),
  UnexpectedTypeArgumentClose("unexpected '>'"),
  UnexpectedWhitespaceCharacter("unexpected whitespace character"),
  UnexpectedComma("unexpected ','"),
  TypeArgumentCountMismatch("type argument count doesn't match expected amount"),
  MissingTypeArgumentClose("no matching '>' for '<'"),
  UnexpectedPrimitiveTypeArguments("primitive types not expected to have type arguments"),
  UnexpectedVectorTypeArgumentCount("vector type expected to have exactly one type argument"),
  UnexpectedStructFormat(
    "unexpected struct format, must be of the form 0xaddress::module_name::struct_name"
  ),
  InvalidModuleNameCharacter("module name must only contain alphanumeric or '_' characters"),
  InvalidStructNameCharacter("struct name must only contain alphanumeric or '_' characters"),
}

class TypeTagParserError(val typeTagStr: String, val invalidReason: TypeTagParserErrorType) :
  Exception("Failed to parse typeTag '$typeTagStr', ${invalidReason.errorMessage}")

object TypeTagParser {
  fun isValidIdentifier(str: String) = str.matches(Regex("^[_a-zA-Z0-9]+$"))

  fun isValidWhitespaceCharacter(char: Char) = char.isWhitespace()

  fun isGeneric(str: String) = str.matches(Regex("^T[0-9]+$"))

  fun parseTypeTag(typeStr: String, allowGenerics: Boolean = false): TypeTag {
    val saved: MutableList<TypeTagState> = mutableListOf()
    var innerTypes: MutableList<TypeTag> = mutableListOf()
    var curTypes: MutableList<TypeTag> = mutableListOf()
    var cur = 0
    var currentStr = ""
    var expectedTypes = 1

    while (cur < typeStr.length) {
      when (val char = typeStr[cur]) {
        '<' -> {
          saved.add(TypeTagState(expectedTypes, currentStr, curTypes))
          currentStr = ""
          curTypes = mutableListOf()
          expectedTypes = 1
        }
        '>' -> {
          if (currentStr.isNotEmpty()) {
            val newType = parseTypeTagInner(currentStr, innerTypes, allowGenerics)
            curTypes.add(newType)
          }

          val savedPop =
            saved.removeLastOrNull()
              ?: throw TypeTagParserError(
                typeStr,
                TypeTagParserErrorType.UnexpectedTypeArgumentClose,
              )

          if (expectedTypes != curTypes.size)
            throw TypeTagParserError(typeStr, TypeTagParserErrorType.TypeArgumentCountMismatch)

          innerTypes = curTypes
          curTypes = savedPop.savedTypes
          currentStr = savedPop.savedStr
          expectedTypes = savedPop.savedExpectedTypes
        }
        ',' -> {
          if (currentStr.isNotEmpty()) {
            val newType = parseTypeTagInner(currentStr, innerTypes, allowGenerics)
            curTypes.add(newType)
            currentStr = ""
          }
          expectedTypes++
        }
        else ->
          if (isValidWhitespaceCharacter(char)) {
            cur = consumeWhitespace(typeStr, cur)
          } else {
            currentStr += char
          }
      }
      cur++
    }

    if (saved.isNotEmpty()) {
      throw TypeTagParserError(typeStr, TypeTagParserErrorType.MissingTypeArgumentClose)
    }

    return when {
      curTypes.isEmpty() -> parseTypeTagInner(currentStr, innerTypes, allowGenerics)
      curTypes.size == 1 && currentStr.isEmpty() -> curTypes[0]
      else ->
        throw TypeTagParserError(typeStr, TypeTagParserErrorType.UnexpectedWhitespaceCharacter)
    }
  }

  private fun consumeWhitespace(tagStr: String, pos: Int): Int {
    var i = pos
    while (i < tagStr.length && isValidWhitespaceCharacter(tagStr[i])) i++
    return i - 1 // to offset the increment in the main loop
  }

  private fun parseTypeTagInner(
    str: String,
    types: List<TypeTag>,
    allowGenerics: Boolean,
  ): TypeTag {
    return when (str) {
      "&signer" ->
        if (types.isNotEmpty())
          throw TypeTagParserError(str, TypeTagParserErrorType.UnexpectedPrimitiveTypeArguments)
        else TypeTagReference(ref = TypeTagSigner())
      "signer",
      "bool",
      "address",
      "u8",
      "u16",
      "u32",
      "u64",
      "u128",
      "u256" -> {
        if (types.isNotEmpty())
          throw TypeTagParserError(str, TypeTagParserErrorType.UnexpectedPrimitiveTypeArguments)
        when (str) {
          "signer" -> TypeTagSigner()
          "bool" -> TypeTagBool()
          "address" -> TypeTagAddress()
          "u8" -> TypeTagU8()
          "u16" -> TypeTagU16()
          "u32" -> TypeTagU32()
          "u64" -> TypeTagU64()
          "u128" -> TypeTagU128()
          "u256" -> TypeTagU256()
          else -> throw IllegalArgumentException("Unknown primitive type")
        }
      }
      "vector" ->
        if (types.size != 1)
          throw TypeTagParserError(str, TypeTagParserErrorType.UnexpectedVectorTypeArgumentCount)
        else TypeTagVector(type = types[0])
      else -> {
        if (isGeneric(str)) {
          if (allowGenerics) TypeTagGeneric(id = str.substring(1).toUShort())
          else throw TypeTagParserError(str, TypeTagParserErrorType.UnexpectedGenericType)
        } else {
          val structParts = str.split("::")
          if (structParts.size != 3)
            throw TypeTagParserError(str, TypeTagParserErrorType.UnexpectedStructFormat)
          if (!isValidIdentifier(structParts[1]))
            throw TypeTagParserError(str, TypeTagParserErrorType.InvalidModuleNameCharacter)
          if (!isValidIdentifier(structParts[2]))
            throw TypeTagParserError(str, TypeTagParserErrorType.InvalidStructNameCharacter)
          TypeTagStruct(
            type =
              StructTag(
                address = AccountAddress.fromString(structParts[0]),
                moduleName = structParts[1],
                name = structParts[2],
                typeArgs = types,
              )
          )
        }
      }
    }
  }
}

data class TypeTagState(
  val savedExpectedTypes: Int,
  val savedStr: String,
  val savedTypes: MutableList<TypeTag>,
)
