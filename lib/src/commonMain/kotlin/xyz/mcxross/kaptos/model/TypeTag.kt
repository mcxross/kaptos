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

import kotlin.jvm.JvmName
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
    /** Parses [string] into a [TypeTag], allowing invoke syntax: `TypeTag("0x1::coin::Coin")`. */
    operator fun invoke(string: String, allowGenerics: Boolean = false): TypeTag =
      fromString(string, allowGenerics)

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

    /**
     * Converts [value] to [TypeTag] from supported types ([TypeTag], [StructTag], [String],
     * [MoveType]).
     */
    fun from(value: Any?): TypeTag =
      when (value) {
        is TypeTag -> value
        is StructTag -> TypeTagStruct(value)
        is String -> fromString(value)
        is MoveType -> value.typeTag
        else ->
          throw IllegalArgumentException(
            "Cannot convert ${value?.let { it::class.simpleName } ?: "null"} to TypeTag"
          )
      }

    /** Converts each value in [values] to a [TypeTag]. */
    fun fromAll(vararg values: Any?): List<TypeTag> = values.map { from(it) }

    /** Converts each value in [values] to a [TypeTag]. */
    fun fromAll(values: Iterable<Any?>): List<TypeTag> = values.map { from(it) }

    // Predefined standard type singletons & constants
    val Bool: TypeTag
      get() = TypeTagBool

    val U8: TypeTag
      get() = TypeTagU8

    val U16: TypeTag
      get() = TypeTagU16

    val U32: TypeTag
      get() = TypeTagU32

    val U64: TypeTag
      get() = TypeTagU64

    val U128: TypeTag
      get() = TypeTagU128

    val U256: TypeTag
      get() = TypeTagU256

    val I8: TypeTag
      get() = TypeTagI8

    val I16: TypeTag
      get() = TypeTagI16

    val I32: TypeTag
      get() = TypeTagI32

    val I64: TypeTag
      get() = TypeTagI64

    val I128: TypeTag
      get() = TypeTagI128

    val I256: TypeTag
      get() = TypeTagI256

    val Address: TypeTag
      get() = TypeTagAddress

    val Signer: TypeTag
      get() = TypeTagSigner

    val String: TypeTag
      get() = TypeTagStruct(stringStructTag())

    val AptosCoin: TypeTag
      get() = TypeTagStruct(aptosCoinStructTag())

    val APT: TypeTag
      get() = AptosCoin

    // Factory helpers
    fun vector(elementType: TypeTag): TypeTagVector = TypeTagVector(elementType)

    fun vector(elementType: String): TypeTagVector = TypeTagVector(fromString(elementType))

    fun option(elementType: TypeTag): TypeTagStruct = TypeTagStruct(optionStructTag(elementType))

    fun option(elementType: String): TypeTagStruct =
      TypeTagStruct(optionStructTag(fromString(elementType)))

    fun objectTag(elementType: TypeTag): TypeTagStruct = TypeTagStruct(objectStructTag(elementType))

    fun objectTag(elementType: String): TypeTagStruct =
      TypeTagStruct(objectStructTag(fromString(elementType)))

    fun struct(type: String): TypeTagStruct = TypeTagStruct(type)

    fun struct(type: StructTag): TypeTagStruct = TypeTagStruct(type)
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

  override fun equals(other: Any?): Boolean =
    this === other || (other is TypeTagGeneric && id == other.id)

  override fun hashCode(): Int = id.hashCode()
}

class TypeTagReference(@Transient val ref: TypeTag) : TypeTag() {

  override val value: String
    get() = "&$ref"

  override fun toString(): String = value

  override fun equals(other: Any?): Boolean =
    this === other || (other is TypeTagReference && ref == other.ref)

  override fun hashCode(): Int = ref.hashCode()
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

  override fun equals(other: Any?): Boolean =
    this === other || (other is TypeTagVector && type == other.type)

  override fun hashCode(): Int = type.hashCode()

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

  override fun equals(other: Any?): Boolean =
    this === other || (other is TypeTagStruct && type == other.type)

  override fun hashCode(): Int = type.hashCode()
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

  override fun equals(other: Any?): Boolean =
    this === other ||
      (other is StructTag &&
        address == other.address &&
        moduleName == other.moduleName &&
        name == other.name &&
        typeArgs == other.typeArgs)

  override fun hashCode(): Int {
    var result = address.hashCode()
    result = 31 * result + moduleName.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + typeArgs.hashCode()
    return result
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

/** Parses this string into a [TypeTag]. */
fun String.toTypeTag(allowGenerics: Boolean = false): TypeTag =
  TypeTag.fromString(this, allowGenerics)

/** Parses each string in this iterable into a [TypeTag]. */
fun Iterable<String>.toTypeTags(allowGenerics: Boolean = false): List<TypeTag> = map {
  it.toTypeTag(allowGenerics)
}

/** Converts each element in this iterable to a [TypeTag]. */
@JvmName("toTypeTagsFromAny")
fun Iterable<Any?>.toTypeTags(): List<TypeTag> = map { TypeTag.from(it) }
