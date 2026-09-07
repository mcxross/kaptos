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

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import xyz.mcxross.kaptos.extension.toStructTag
import xyz.mcxross.kaptos.serialize.*
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser

@Serializable(with = TypeTagSerializer::class)
sealed class TypeTag {

  abstract val value: String

  fun isBool(): Boolean {
    return this is TypeTagBool
  }

  fun isU8(): Boolean {
    return this is TypeTagU8
  }

  fun isU16(): Boolean {
    return this is TypeTagU16
  }

  fun isU32(): Boolean {
    return this is TypeTagU32
  }

  fun isU64(): Boolean {
    return this is TypeTagU64
  }

  fun isU128(): Boolean {
    return this is TypeTagU128
  }

  fun isU256(): Boolean {
    return this is TypeTagU256
  }

  fun isSignedInteger(): Boolean =
    this is TypeTagI8 ||
      this is TypeTagI16 ||
      this is TypeTagI32 ||
      this is TypeTagI64 ||
      this is TypeTagI128 ||
      this is TypeTagI256

  fun isVector(): Boolean {
    return this is TypeTagVector
  }

  fun isStruct(): Boolean {
    return this is TypeTagStruct
  }

  fun isGeneric(): Boolean {
    return this is TypeTagGeneric
  }

  fun isSigner(): Boolean {
    return this is TypeTagSigner
  }

  fun isAddress(): Boolean {
    return this is TypeTagAddress
  }

  fun isReference(): Boolean {
    return this is TypeTagReference
  }

  companion object {
    fun fromString(string: String, allowGenerics: Boolean = false): TypeTag =
      TypeTagParser.parseTypeTag(string, allowGenerics)

    fun valueOf(string: String): TypeTag {
      return when (string) {
        "address" -> TypeTagAddress
        "bool" -> TypeTagBool
        "signer" -> TypeTagSigner
        "u8" -> TypeTagU8
        "u16" -> TypeTagU16
        "u32" -> TypeTagU32
        "u64" -> TypeTagU64
        "u128" -> TypeTagU128
        "u256" -> TypeTagU256
        "i8" -> TypeTagI8
        "i16" -> TypeTagI16
        "i32" -> TypeTagI32
        "i64" -> TypeTagI64
        "i128" -> TypeTagI128
        "i256" -> TypeTagI256
        else -> throw IllegalArgumentException("Invalid TypeTag string: $string")
      }
    }
  }
}

data object TypeTagAddress : TypeTag() {

  @Transient override val value: String = "address"

  override fun toString(): String = value
}

data object TypeTagBool : TypeTag() {

  override val value: String
    get() = "bool"

  override fun toString(): String = value
}

class TypeTagGeneric(val id: UShort) : TypeTag() {

  override val value: String
    get() = "$id"

  override fun toString(): String = "T$value"
}

class TypeTagReference(@Transient val ref: TypeTag) : TypeTag() {

  override val value: String
    get() = "&$ref"

  override fun toString(): String = value
}

data object TypeTagSigner : TypeTag() {

  override val value: String
    get() = "signer"

  override fun toString(): String = value
}

data object TypeTagU8 : TypeTag() {

  override val value: String
    get() = "u8"

  override fun toString(): String = value
}

data object TypeTagU16 : TypeTag() {

  override val value: String
    get() = "u16"

  override fun toString(): String = value
}

data object TypeTagU32 : TypeTag() {
  override val value: String
    get() = "u32"

  override fun toString(): String = value
}

data object TypeTagU64 : TypeTag() {
  override val value: String
    get() = "u64"

  override fun toString(): String = value
}

data object TypeTagU128 : TypeTag() {

  override val value: String
    get() = "u128"

  override fun toString(): String {
    return "u128"
  }
}

data object TypeTagU256 : TypeTag() {
  override val value: String
    get() = "u256"

  override fun toString(): String = value
}

data object TypeTagI8 : TypeTag() {
  override val value: String = "i8"

  override fun toString(): String = value
}

data object TypeTagI16 : TypeTag() {
  override val value: String = "i16"

  override fun toString(): String = value
}

data object TypeTagI32 : TypeTag() {
  override val value: String = "i32"

  override fun toString(): String = value
}

data object TypeTagI64 : TypeTag() {
  override val value: String = "i64"

  override fun toString(): String = value
}

data object TypeTagI128 : TypeTag() {
  override val value: String = "i128"

  override fun toString(): String = value
}

data object TypeTagI256 : TypeTag() {
  override val value: String = "i256"

  override fun toString(): String = value
}

class TypeTagVector(val type: TypeTag) : TypeTag() {

  override val value: String
    get() = type.toString()

  override fun toString(): String {
    return "vector<${type}>"
  }

  companion object {
    fun u8(): TypeTagVector {
      return TypeTagVector(type = TypeTagU8)
    }
  }
}

@Serializable
class TypeTagStruct(val type: StructTag) : TypeTag() {

  constructor(type: String) : this(type = type.toStructTag())

  private fun isTypeTag(address: AccountAddress, moduleName: String, structName: String): Boolean {
    return this.type.address == address &&
      this.type.moduleName == moduleName &&
      this.type.name == structName
  }

  fun isString(): Boolean {
    return isTypeTag(AccountAddress.ONE, "string", "String")
  }

  // We add this just to comply, but it's not used
  override val value: String
    get() = type.toString()

  override fun toString(): String {
    var typePredicate = ""
    if (this.type.typeArgs.isNotEmpty()) {
      typePredicate = "<${this.type.typeArgs.joinToString(", ") { it.toString() }}>"
    }
    return "${this.type.address}::${this.type.moduleName}::${this.type.name}$typePredicate"
  }
}

@Serializable
class StructTag(
  val address: AccountAddress,
  val moduleName: String,
  val name: String,
  val typeArgs: List<TypeTag>,
) {
  override fun toString(): String = buildString {
    append(address)
    append("::")
    append(moduleName)
    append("::")
    append(name)
    if (typeArgs.isNotEmpty()) {
      append(typeArgs.joinToString(prefix = "<", postfix = ">", separator = ","))
    }
  }

  companion object {
    fun fromString(string: String): StructTag {
      val parsed = TypeTagParser.parseTypeTag(string)
      return (parsed as? TypeTagStruct)?.type
        ?: throw IllegalArgumentException("Expected a struct type tag: $string")
    }
  }
}

fun aptosCoinStructTag(): StructTag {
  return StructTag(
    AccountAddress.ONE,
    Identifier("aptos_coin").toString(),
    Identifier("AptosCoin").toString(),
    emptyList(),
  )
}

fun stringStructTag(): StructTag {
  return StructTag(
    AccountAddress.ONE,
    Identifier("string").toString(),
    Identifier("String").toString(),
    emptyList(),
  )
}

fun optionStructTag(typeArg: TypeTag): StructTag {
  return StructTag(
    AccountAddress.ONE,
    Identifier("option").toString(),
    Identifier("Option").toString(),
    listOf(typeArg),
  )
}

fun objectStructTag(typeArg: TypeTag): StructTag {
  return StructTag(
    AccountAddress.ONE,
    Identifier("object").toString(),
    Identifier("Object").toString(),
    listOf(typeArg),
  )
}
