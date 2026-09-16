/*
 * Copyright 2026 McXross
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

import kotlin.reflect.KClass

/** Represents a Move type in Kotlin that can provide its corresponding [TypeTag]. */
interface MoveType {
  val typeTag: TypeTag
}

/**
 * Base class for defining typed Move struct markers or wrappers.
 *
 * Example:
 * ```kotlin
 * object AptosCoin : MoveStructType("0x1::aptos_coin::AptosCoin")
 * ```
 */
abstract class MoveStructType(typeString: String) : MoveType {
  override val typeTag: TypeTag by lazy { TypeTag.fromString(typeString) }
}

/** Predefined marker for the canonical AptosCoin. */
object AptosCoin : MoveStructType("0x1::aptos_coin::AptosCoin")

/** Registry mapping Kotlin types to Move [TypeTag]s for reified type inference. */
object MoveTypeRegistry {
  private val registry = mutableMapOf<KClass<*>, TypeTag>()

  init {
    registry[AptosCoin::class] = AptosCoin.typeTag
    registry[String::class] = TypeTag.String
  }

  fun register(kClass: KClass<*>, typeTag: TypeTag) {
    registry[kClass] = typeTag
  }

  inline fun <reified T : Any> register(typeTag: TypeTag) {
    register(T::class, typeTag)
  }

  inline fun <reified T : Any> register(typeString: String) {
    register(T::class, TypeTag.fromString(typeString))
  }

  fun find(kClass: KClass<*>): TypeTag? = registry[kClass]
}

/**
 * Infers a [TypeTag] from a reified Kotlin type.
 *
 * Supports primitives (`Boolean`, `UByte`, `UShort`, `UInt`, `ULong`, `Byte`, `Short`, `Int`,
 * `Long`), standard Move structures (`AccountAddress`, `String`, `ByteArray`), and any registered
 * [MoveType]s.
 */
inline fun <reified T> typeTagOf(): TypeTag =
  when (val kClass = T::class) {
    Boolean::class -> TypeTagBool
    UByte::class -> TypeTagU8
    UShort::class -> TypeTagU16
    UInt::class -> TypeTagU32
    ULong::class -> TypeTagU64
    Byte::class -> TypeTagI8
    Short::class -> TypeTagI16
    Int::class -> TypeTagI32
    Long::class -> TypeTagI64
    AccountAddress::class -> TypeTagAddress
    String::class -> TypeTag.String
    ByteArray::class -> TypeTagVector(TypeTagU8)
    else ->
      MoveTypeRegistry.find(kClass)
        ?: throw IllegalArgumentException(
          "Cannot infer TypeTag for type '${kClass.simpleName}'. " +
            "Implement MoveType or register with MoveTypeRegistry.register<${kClass.simpleName}>(\"...\")"
        )
  }

/** Alias for [typeTagOf]. */
inline fun <reified T> typeTag(): TypeTag = typeTagOf<T>()
