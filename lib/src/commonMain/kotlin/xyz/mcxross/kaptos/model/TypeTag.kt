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
import xyz.mcxross.kaptos.extension.parts

@Serializable
sealed class TypeTag {

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
        "address" -> TypeTagAddress
        "bool" -> TypeTagBool
        "signer" -> TypeTagSigner
        "u8" -> TypeTagU8
        "u16" -> TypeTagU16
        "u32" -> TypeTagU32
        "u64" -> TypeTagU64
        "u128" -> TypeTagU128
        "u256" -> TypeTagU256
        else -> throw IllegalArgumentException("Invalid TypeTag string: $string")
      }
    }
  }
}

data object TypeTagAddress : TypeTag() {
  override fun toString(): String {
    return "address"
  }
}

data object TypeTagBool : TypeTag() {
  override fun toString(): String {
    return "bool"
  }
}

class TypeTagGeneric(val id: Int) : TypeTag() {
  override fun toString(): String {
    return "T$id"
  }
}

class TypeTagReference(val ref: TypeTag) : TypeTag() {
  override fun toString(): String {
    return "&$ref"
  }
}

data object TypeTagSigner : TypeTag() {
  override fun toString(): String {
    return "signer"
  }
}

data object TypeTagU8 : TypeTag() {
  override fun toString(): String {
    return "u8"
  }
}

data object TypeTagU16 : TypeTag() {
  override fun toString(): String {
    return "u16"
  }
}

data object TypeTagU32 : TypeTag() {
  override fun toString(): String {
    return "u32"
  }
}

data object TypeTagU64 : TypeTag() {
  override fun toString(): String {
    return "u64"
  }
}

data object TypeTagU128 : TypeTag() {
  override fun toString(): String {
    return "u128"
  }
}

data object TypeTagU256 : TypeTag() {
  override fun toString(): String {
    return "u256"
  }
}

class TypeTagVector(val type: TypeTag) : TypeTag() {
  override fun toString(): String {
    return "vector<${type}>"
  }
}

class TypeTagStruct(val type: StructTag) : TypeTag() {
  override fun toString(): String {
    var typePredicate = ""
    if (this.type.typeArgs.isNotEmpty()) {
      typePredicate = "<${this.type.typeArgs.joinToString(", ") { it.toString() }}>"
    }
    return "${this.type.address}::${this.type.moduleName}::${this.type.name}$typePredicate"
  }
}

class StructTag(
  val address: AccountAddress,
  val moduleName: String,
  val name: String,
  val typeArgs: List<TypeTag>,
) : TypeTag() {
  companion object {
    fun fromString(string: String)  : StructTag {
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
