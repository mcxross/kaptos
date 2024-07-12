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
import xyz.mcxross.kaptos.extension.parts
import xyz.mcxross.kaptos.serialize.TypeTagSerializer

@Serializable(with = TypeTagSerializer::class)
sealed class TypeTag {

  abstract val enumIndex: TypeTagVariants

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
    fun fromString() {}

    fun valueOf(string: String): TypeTag {
      return when (string) {
        "address" -> TypeTagAddress()
        "bool" -> TypeTagBool()
        "signer" -> TypeTagSigner()
        "u8" -> TypeTagU8()
        "u16" -> TypeTagU16()
        "u32" -> TypeTagU32()
        "u64" -> TypeTagU64()
        "u128" -> TypeTagU128()
        "u256" -> TypeTagU256()
        else -> throw IllegalArgumentException("Invalid TypeTag string: $string")
      }
    }
  }
}

data class TypeTagAddress(override val enumIndex: TypeTagVariants = TypeTagVariants.Address) :
  TypeTag() {

  @Transient override val value: String = "address"

  override fun toString(): String = value
}

data class TypeTagBool(override val enumIndex: TypeTagVariants = TypeTagVariants.Bool) : TypeTag() {

  override val value: String
    get() = "bool"

  override fun toString(): String = value
}

class TypeTagGeneric(
  override val enumIndex: TypeTagVariants = TypeTagVariants.Generic,
  val id: UShort,
) : TypeTag() {

  override val value: String
    get() = "$id"

  override fun toString(): String = "T$value"
}

class TypeTagReference(
  override val enumIndex: TypeTagVariants = TypeTagVariants.Reference,
  @Transient val ref: TypeTag,
) : TypeTag() {

  override val value: String
    get() = "&$ref"

  override fun toString(): String = value
}

data class TypeTagSigner(override val enumIndex: TypeTagVariants = TypeTagVariants.Signer) :
  TypeTag() {

  override val value: String
    get() = "signer"

  override fun toString(): String = value
}

data class TypeTagU8(override val enumIndex: TypeTagVariants = TypeTagVariants.U8) : TypeTag() {

  override val value: String
    get() = "u8"

  override fun toString(): String = value
}

data class TypeTagU16(override val enumIndex: TypeTagVariants = TypeTagVariants.U16) : TypeTag() {

  override val value: String
    get() = "u16"

  override fun toString(): String = value
}

data class TypeTagU32(override val enumIndex: TypeTagVariants = TypeTagVariants.U32) : TypeTag() {
  override val value: String
    get() = "u32"

  override fun toString(): String = value
}

data class TypeTagU64(override val enumIndex: TypeTagVariants = TypeTagVariants.U64) : TypeTag() {
  override val value: String
    get() = "u64"

  override fun toString(): String = value
}

data class TypeTagU128(override val enumIndex: TypeTagVariants = TypeTagVariants.U128) : TypeTag() {

  override val value: String
    get() = "u128"

  override fun toString(): String {
    return "u128"
  }
}

data class TypeTagU256(override val enumIndex: TypeTagVariants = TypeTagVariants.U256) : TypeTag() {
  override val value: String
    get() = "u256"

  override fun toString(): String = value
}

class TypeTagVector(
  override val enumIndex: TypeTagVariants = TypeTagVariants.Vector,
  val type: TypeTag,
) : TypeTag() {

  override val value: String
    get() = type.toString()

  override fun toString(): String {
    return "vector<${type}>"
  }

  companion object {
    fun u8(): TypeTagVector {
      return TypeTagVector(type = TypeTagU8())
    }
  }
}

@Serializable
class TypeTagStruct(
  override val enumIndex: TypeTagVariants = TypeTagVariants.Struct,
  val type: StructTag,
) : TypeTag() {

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
  companion object {
    fun fromString(string: String): StructTag {
      val parts = string.parts()
      if (parts.toList().size != 3) {
        throw IllegalArgumentException("Invalid StructTag string: $string")
      }
      val address = AccountAddress.fromString(parts.first)
      val moduleName = parts.second
      val name = parts.third
      // TODO: Parse type args
      return StructTag(address, moduleName, name, emptyList())
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
