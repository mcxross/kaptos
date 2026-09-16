/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package xyz.mcxross.kaptos.move

import kotlin.time.TimeSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.MoveAbility
import xyz.mcxross.kaptos.model.MoveModuleBytecode
import xyz.mcxross.kaptos.model.MoveStruct
import xyz.mcxross.kaptos.model.StructTag
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.TypeTagAddress
import xyz.mcxross.kaptos.model.TypeTagBool
import xyz.mcxross.kaptos.model.TypeTagGeneric
import xyz.mcxross.kaptos.model.TypeTagI128
import xyz.mcxross.kaptos.model.TypeTagI16
import xyz.mcxross.kaptos.model.TypeTagI256
import xyz.mcxross.kaptos.model.TypeTagI32
import xyz.mcxross.kaptos.model.TypeTagI64
import xyz.mcxross.kaptos.model.TypeTagI8
import xyz.mcxross.kaptos.model.TypeTagReference
import xyz.mcxross.kaptos.model.TypeTagSigner
import xyz.mcxross.kaptos.model.TypeTagStruct
import xyz.mcxross.kaptos.model.TypeTagU128
import xyz.mcxross.kaptos.model.TypeTagU16
import xyz.mcxross.kaptos.model.TypeTagU256
import xyz.mcxross.kaptos.model.TypeTagU32
import xyz.mcxross.kaptos.model.TypeTagU64
import xyz.mcxross.kaptos.model.TypeTagU8
import xyz.mcxross.kaptos.model.TypeTagVector
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.bcs.signedDecimalToLittleEndian
import xyz.mcxross.kaptos.transaction.bcs.unsignedDecimalToLittleEndian
import xyz.mcxross.kaptos.util.findFirstNonSignerArg

/** Application- or transport-provided source of Move module ABIs. */
fun interface MoveModuleLoader {
  suspend fun load(address: AccountAddress, moduleName: String): AptosResult<MoveModuleBytecode>
}

/**
 * ABI-aware codec for recursive Move struct and enum arguments.
 *
 * Fetched ABIs expire according to [AbiCachePolicy]. Preloaded ABIs stay pinned until invalidated.
 */
class MoveArgumentCodec(
  private val moduleLoader: MoveModuleLoader,
  private val cachePolicy: AbiCachePolicy = AbiCachePolicy(),
) {
  private data class CachedModule(val module: MoveModuleBytecode, val loadedAt: Long?)

  private val epoch = TimeSource.Monotonic.markNow()
  internal var nowMillis: () -> Long = { epoch.elapsedNow().inWholeMilliseconds }
  private val modules = mutableMapOf<String, CachedModule>()
  private val moduleMutex = Mutex()

  /** Add trusted module ABIs to this codec's cache. */
  suspend fun preload(vararg values: MoveModuleBytecode) {
    val validated = values.map { module ->
      val abi = requireNotNull(module.abi) { "A preloaded module must contain an ABI" }
      moduleKey(AccountAddress.fromString(abi.address), abi.name) to CachedModule(module, null)
    }
    moduleMutex.withLock { modules.putAll(validated) }
  }

  /** Clear fetched and preloaded ABIs. In-flight loads finish before this invalidation returns. */
  suspend fun clearCache() {
    moduleMutex.withLock { modules.clear() }
  }

  /** Remove one fetched or preloaded module after a known deployment or upgrade. */
  suspend fun invalidateModule(address: AccountAddress, name: String) {
    moduleMutex.withLock { modules.remove(moduleKey(address, name)) }
  }

  /** Encode one value according to its instantiated Move type. */
  suspend fun encode(
    type: TypeTag,
    argument: MoveArgument,
    typeArguments: List<TypeTag> = emptyList(),
  ): AptosResult<MoveArgument.PreSerialized> =
    try {
      val writer = AptosBcsWriter()
      writer.encodeValue(substitute(type, typeArguments), argument, depth = 0)
      AptosResult.Success(MoveArgument.PreSerialized(writer.toByteArray()))
    } catch (error: CancellationException) {
      throw error
    } catch (error: AbiLoadException) {
      AptosResult.Failure(error.error)
    } catch (error: Throwable) {
      AptosResult.Failure(
        AptosError.Serialization(
          "Unable to encode ${type} Move argument: ${error.message ?: "invalid value"}",
          error,
        )
      )
    }

  /** Resolve an entry-function ABI and return a payload with every argument encoded. */
  suspend fun entryFunctionPayload(
    function: String,
    typeArguments: List<TypeTag> = emptyList(),
    arguments: List<MoveArgument> = emptyList(),
  ): AptosResult<TransactionPayload.EntryFunction> =
    try {
      val parameters = functionParameters(function, typeArguments, arguments.size, view = false)
      val encoded =
        parameters.zip(arguments).map { (type, argument) ->
          when (val result = encode(type, argument, typeArguments)) {
            is AptosResult.Success -> result.value
            is AptosResult.Failure -> throw AbiLoadException(result.error)
          }
        }
      AptosResult.Success(TransactionPayload.entryFunction(function, typeArguments, encoded))
    } catch (error: CancellationException) {
      throw error
    } catch (error: AbiLoadException) {
      AptosResult.Failure(error.error)
    } catch (error: Throwable) {
      AptosResult.Failure(
        AptosError.Serialization(
          "Unable to build entry-function payload for $function: ${error.message ?: "invalid ABI"}",
          error,
        )
      )
    }

  /** Validate a view ABI and encode its request using Aptos' BCS view wire format. */
  internal suspend fun viewRequest(
    function: String,
    typeArguments: List<TypeTag>,
    arguments: List<MoveArgument>,
  ): AptosResult<ByteArray> =
    try {
      val parameters = functionParameters(function, typeArguments, arguments.size, view = true)
      val encoded =
        parameters.zip(arguments).map { (type, value) ->
          validateViewArgument(value, 0)
          val concrete = substitute(type, typeArguments)
          validateConcreteType(concrete, 0)
          require(concrete !is TypeTagReference && concrete != TypeTagSigner) {
            "View arguments cannot be signers or references"
          }
          val writer = AptosBcsWriter()
          writer.encodeValue(concrete, value, 0)
          writer.toByteArray()
        }
      val call = TransactionPayload.entryFunction(function, typeArguments).call
      AptosResult.Success(
        AptosBcsWriter()
          .also { writer ->
            writer.accountAddress(call.module.address)
            writer.string(call.module.name.toString())
            writer.string(call.function.toString())
            writer.vector(typeArguments) { typeTag(it) }
            writer.vector(encoded) { bytes(it) }
          }
          .toByteArray()
      )
    } catch (error: CancellationException) {
      throw error
    } catch (error: AbiLoadException) {
      AptosResult.Failure(error.error)
    } catch (error: Exception) {
      AptosResult.Failure(
        AptosError.Validation("Invalid view call $function: ${error.message}", error)
      )
    }

  private suspend fun functionParameters(
    function: String,
    typeArguments: List<TypeTag>,
    argumentCount: Int,
    view: Boolean,
  ): List<TypeTag> {
    val parts = function.split("::")
    require(parts.size == 3 && parts.none(String::isBlank)) {
      "Move function must use address::module::function"
    }
    require(parts.drop(1).all { it.matches(Regex("[a-zA-Z_][a-zA-Z0-9_]*")) }) {
      "Invalid Move module or function identifier"
    }
    val address = AccountAddress.fromString(parts[0])
    val abi = requireNotNull(module(address, parts[1]).abi) { "Module has no ABI" }
    val definition =
      requireNotNull(abi.exposedFunctions.singleOrNull { it.name == parts[2] }) {
        "Function $function was not found or is ambiguous"
      }
    require(if (view) definition.isView else definition.isEntry) {
      "$function is not ${if (view) "a view" else "an entry"} function"
    }
    require(typeArguments.size == definition.genericTypeParams.size) {
      "Expected ${definition.genericTypeParams.size} type arguments, got ${typeArguments.size}"
    }
    typeArguments.forEach { validateConcreteType(it, 0) }
    val params =
      if (view) definition.params else definition.params.drop(findFirstNonSignerArg(definition))
    require(argumentCount == params.size) {
      "Expected ${params.size} function arguments, got $argumentCount"
    }
    return params.map { TypeTag.fromString(it, allowGenerics = true) }
  }

  private fun validateConcreteType(type: TypeTag, depth: Int) {
    require(depth <= MAX_NESTING_DEPTH) {
      "Type argument nesting exceeds $MAX_NESTING_DEPTH levels"
    }
    when (type) {
      is TypeTagGeneric,
      is TypeTagReference,
      TypeTagSigner ->
        throw IllegalArgumentException("Type argument $type is not a concrete value type")
      is TypeTagVector -> validateConcreteType(type.type, depth + 1)
      is TypeTagStruct -> type.type.typeArgs.forEach { validateConcreteType(it, depth + 1) }
      else -> Unit
    }
  }

  private fun validateViewArgument(argument: MoveArgument, depth: Int) {
    require(depth <= MAX_NESTING_DEPTH) {
      "Move argument nesting exceeds $MAX_NESTING_DEPTH levels"
    }
    when (argument) {
      is MoveArgument.PreSerialized ->
        throw IllegalArgumentException(
          "PreSerialized arguments cannot be validated; supply typed values or use callRaw with JSON"
        )
      is MoveArgument.Enum -> argument.fields.values.forEach { validateViewArgument(it, depth + 1) }
      is MoveArgument.Vector -> argument.values.forEach { validateViewArgument(it, depth + 1) }
      is MoveArgument.Struct ->
        argument.fields.values.forEach { validateViewArgument(it, depth + 1) }
      is MoveArgument.Option -> argument.value?.let { validateViewArgument(it, depth + 1) }
      else -> Unit
    }
  }

  private suspend fun AptosBcsWriter.encodeValue(
    type: TypeTag,
    argument: MoveArgument,
    depth: Int,
  ) {
    require(depth <= MAX_NESTING_DEPTH) {
      "Move argument nesting exceeds $MAX_NESTING_DEPTH levels"
    }
    if (argument is MoveArgument.PreSerialized) {
      fixed(argument.value)
      return
    }
    when (type) {
      TypeTagBool -> bool(coerceBool(argument))
      TypeTagU8 -> u8(coerceU8(argument))
      TypeTagU16 -> u16(coerceU16(argument))
      TypeTagU32 -> u32(coerceU32(argument))
      TypeTagU64 -> u64(coerceU64(argument))
      TypeTagU128 -> fixed(unsignedDecimalToLittleEndian(coerceU128(argument), 16))
      TypeTagU256 -> fixed(unsignedDecimalToLittleEndian(coerceU256(argument), 32))
      TypeTagI8 -> u8(coerceI8(argument).toUByte())
      TypeTagI16 -> u16(coerceI16(argument).toUShort())
      TypeTagI32 -> u32(coerceI32(argument).toUInt())
      TypeTagI64 -> u64(coerceI64(argument).toULong())
      TypeTagI128 -> fixed(signedDecimalToLittleEndian(coerceI128(argument), 16))
      TypeTagI256 -> fixed(signedDecimalToLittleEndian(coerceI256(argument), 32))
      TypeTagAddress -> accountAddress(coerceAddress(argument))
      is TypeTagVector -> encodeVector(type, argument, depth)
      is TypeTagStruct -> encodeStruct(type, argument, depth)
      is TypeTagReference -> encodeValue(type.ref, argument, depth)
      is TypeTagGeneric -> error("Unresolved generic type $type")
      TypeTagSigner -> error("Signer arguments are supplied by transaction authenticators")
    }
  }

  private suspend fun AptosBcsWriter.encodeVector(
    type: TypeTagVector,
    argument: MoveArgument,
    depth: Int,
  ) {
    if (type.type == TypeTagU8) {
      when (argument) {
        is MoveArgument.Bytes -> {
          bytes(argument.value)
          return
        }
        is MoveArgument.StringValue -> {
          val hexCandidate = argument.value.removePrefix("0x")
          if (
            argument.value.startsWith("0x") &&
              hexCandidate.length % 2 == 0 &&
              hexCandidate.all { it in "0123456789abcdefABCDEF" }
          ) {
            bytes(hexCandidate.chunked(2).map { it.toInt(16).toByte() }.toByteArray())
            return
          }
        }
        else -> Unit
      }
    }
    val values =
      when (argument) {
        is MoveArgument.Vector -> argument.values
        else ->
          throw IllegalArgumentException(
            "Expected MoveArgument.Vector for $type, got ${argument::class.simpleName}"
          )
      }
    require(values.size.toLong() <= UInt.MAX_VALUE.toLong()) { "Move vector is too large" }
    uleb128(values.size.toUInt())
    values.forEach { encodeValue(type.type, it, depth + 1) }
  }

  private suspend fun AptosBcsWriter.encodeStruct(
    type: TypeTagStruct,
    argument: MoveArgument,
    depth: Int,
  ) {
    val tag = type.type
    when {
      tag.isBuiltin("string", "String") -> {
        require(tag.typeArgs.isEmpty()) { "Move String does not accept type arguments" }
        when (argument) {
          is MoveArgument.StringValue -> string(argument.value)
          is MoveArgument.Bytes -> string(argument.value.decodeToString())
          else -> string(expect<MoveArgument.StringValue>(type, argument).value)
        }
      }
      tag.isBuiltin("object", "Object") -> {
        require(tag.typeArgs.size == 1) { "Move Object requires one type argument" }
        accountAddress(coerceAddress(argument))
      }
      tag.isBuiltin("option", "Option") -> {
        require(tag.typeArgs.size == 1) { "Move Option must have one type argument" }
        val innerType = tag.typeArgs.single()
        when (argument) {
          is MoveArgument.Option -> {
            if (argument.value == null) {
              uleb128(0u)
            } else {
              uleb128(1u)
              encodeValue(innerType, argument.value, depth + 1)
            }
          }
          else -> {
            uleb128(1u)
            encodeValue(innerType, argument, depth + 1)
          }
        }
      }
      else -> encodeCustomStruct(tag, argument, depth)
    }
  }

  private fun coerceAddress(argument: MoveArgument): AccountAddress =
    when (argument) {
      is MoveArgument.Address -> argument.value
      is MoveArgument.StringValue ->
        try {
          AccountAddress.fromString(argument.value)
        } catch (e: Exception) {
          throw IllegalArgumentException("Cannot parse '${argument.value}' as an AccountAddress", e)
        }
      else ->
        throw IllegalArgumentException(
          "Expected AccountAddress or hex string, got ${argument::class.simpleName}"
        )
    }

  private fun coerceU8(argument: MoveArgument): UByte =
    when (argument) {
      is MoveArgument.U8 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value in 0..UByte.MAX_VALUE.toInt()) {
          "Value ${argument.value} out of range for u8"
        }
        argument.value.toUByte()
      }
      is MoveArgument.StringValue -> {
        val num =
          argument.value.toULongOrNull()
            ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as u8")
        require(num <= UByte.MAX_VALUE.toULong()) { "Value ${argument.value} exceeds u8 maximum" }
        num.toUByte()
      }
      else ->
        throw IllegalArgumentException("Expected u8 number, got ${argument::class.simpleName}")
    }

  private fun coerceU16(argument: MoveArgument): UShort =
    when (argument) {
      is MoveArgument.U16 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value in 0..UShort.MAX_VALUE.toInt()) {
          "Value ${argument.value} out of range for u16"
        }
        argument.value.toUShort()
      }
      is MoveArgument.StringValue -> {
        val num =
          argument.value.toULongOrNull()
            ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as u16")
        require(num <= UShort.MAX_VALUE.toULong()) {
          "Value ${argument.value} exceeds u16 maximum"
        }
        num.toUShort()
      }
      else ->
        throw IllegalArgumentException("Expected u16 number, got ${argument::class.simpleName}")
    }

  private fun coerceU32(argument: MoveArgument): UInt =
    when (argument) {
      is MoveArgument.U32 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value >= 0) { "Negative value ${argument.value} cannot be coerced to u32" }
        argument.value.toUInt()
      }
      is MoveArgument.StringValue -> {
        val num =
          argument.value.toULongOrNull()
            ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as u32")
        require(num <= UInt.MAX_VALUE.toULong()) { "Value ${argument.value} exceeds u32 maximum" }
        num.toUInt()
      }
      else ->
        throw IllegalArgumentException("Expected u32 number, got ${argument::class.simpleName}")
    }

  private fun coerceU64(argument: MoveArgument): ULong =
    when (argument) {
      is MoveArgument.U64 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value >= 0) { "Negative value ${argument.value} cannot be coerced to u64" }
        argument.value.toULong()
      }
      is MoveArgument.I64 -> {
        require(argument.value >= 0) { "Negative value ${argument.value} cannot be coerced to u64" }
        argument.value.toULong()
      }
      is MoveArgument.StringValue ->
        argument.value.toULongOrNull()
          ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as u64")
      else ->
        throw IllegalArgumentException("Expected u64 number, got ${argument::class.simpleName}")
    }

  private fun coerceU128(argument: MoveArgument): String =
    when (argument) {
      is MoveArgument.U128 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value >= 0) {
          "Negative value ${argument.value} cannot be coerced to u128"
        }
        argument.value.toString()
      }
      is MoveArgument.I64 -> {
        require(argument.value >= 0) {
          "Negative value ${argument.value} cannot be coerced to u128"
        }
        argument.value.toString()
      }
      is MoveArgument.StringValue -> argument.value
      else ->
        throw IllegalArgumentException("Expected u128 number, got ${argument::class.simpleName}")
    }

  private fun coerceU256(argument: MoveArgument): String =
    when (argument) {
      is MoveArgument.U256 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value >= 0) {
          "Negative value ${argument.value} cannot be coerced to u256"
        }
        argument.value.toString()
      }
      is MoveArgument.I64 -> {
        require(argument.value >= 0) {
          "Negative value ${argument.value} cannot be coerced to u256"
        }
        argument.value.toString()
      }
      is MoveArgument.StringValue -> argument.value
      else ->
        throw IllegalArgumentException("Expected u256 number, got ${argument::class.simpleName}")
    }

  private fun coerceI8(argument: MoveArgument): Byte =
    when (argument) {
      is MoveArgument.I8 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value in Byte.MIN_VALUE..Byte.MAX_VALUE) {
          "Value ${argument.value} out of range for i8"
        }
        argument.value.toByte()
      }
      is MoveArgument.StringValue ->
        argument.value.toByteOrNull()
          ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as i8")
      else ->
        throw IllegalArgumentException("Expected i8 number, got ${argument::class.simpleName}")
    }

  private fun coerceI16(argument: MoveArgument): Short =
    when (argument) {
      is MoveArgument.I16 -> argument.value
      is MoveArgument.I32 -> {
        require(argument.value in Short.MIN_VALUE..Short.MAX_VALUE) {
          "Value ${argument.value} out of range for i16"
        }
        argument.value.toShort()
      }
      is MoveArgument.StringValue ->
        argument.value.toShortOrNull()
          ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as i16")
      else ->
        throw IllegalArgumentException("Expected i16 number, got ${argument::class.simpleName}")
    }

  private fun coerceI32(argument: MoveArgument): Int =
    when (argument) {
      is MoveArgument.I32 -> argument.value
      is MoveArgument.StringValue ->
        argument.value.toIntOrNull()
          ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as i32")
      else ->
        throw IllegalArgumentException("Expected i32 number, got ${argument::class.simpleName}")
    }

  private fun coerceI64(argument: MoveArgument): Long =
    when (argument) {
      is MoveArgument.I64 -> argument.value
      is MoveArgument.I32 -> argument.value.toLong()
      is MoveArgument.StringValue ->
        argument.value.toLongOrNull()
          ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as i64")
      else ->
        throw IllegalArgumentException("Expected i64 number, got ${argument::class.simpleName}")
    }

  private fun coerceI128(argument: MoveArgument): String =
    when (argument) {
      is MoveArgument.I128 -> argument.value
      is MoveArgument.I32 -> argument.value.toString()
      is MoveArgument.I64 -> argument.value.toString()
      is MoveArgument.StringValue -> argument.value
      else ->
        throw IllegalArgumentException("Expected i128 number, got ${argument::class.simpleName}")
    }

  private fun coerceI256(argument: MoveArgument): String =
    when (argument) {
      is MoveArgument.I256 -> argument.value
      is MoveArgument.I32 -> argument.value.toString()
      is MoveArgument.I64 -> argument.value.toString()
      is MoveArgument.StringValue -> argument.value
      else ->
        throw IllegalArgumentException("Expected i256 number, got ${argument::class.simpleName}")
    }

  private fun coerceBool(argument: MoveArgument): Boolean =
    when (argument) {
      is MoveArgument.Bool -> argument.value
      is MoveArgument.StringValue ->
        argument.value.toBooleanStrictOrNull()
          ?: throw IllegalArgumentException("Cannot parse '${argument.value}' as boolean")
      else -> throw IllegalArgumentException("Expected boolean, got ${argument::class.simpleName}")
    }

  private suspend fun AptosBcsWriter.encodeCustomStruct(
    tag: StructTag,
    argument: MoveArgument,
    depth: Int,
  ) {
    val module = module(tag.address, tag.moduleName)
    val abi = requireNotNull(module.abi) { "Module ${tag.address}::${tag.moduleName} has no ABI" }
    val definition =
      requireNotNull(abi.structs.singleOrNull { it.name == tag.name }) {
        "Type ${tag.name} was not found in ${tag.address}::${tag.moduleName}"
      }
    require(!definition.isNative) { "Native Move type ${tag.name} cannot be an argument" }
    require(MoveAbility.COPY in definition.abilities && MoveAbility.KEY !in definition.abilities) {
      "Move type ${tag.name} must have copy and must not have key"
    }
    require(tag.typeArgs.size == definition.genericTypeParams.size) {
      "Expected ${definition.genericTypeParams.size} type arguments for ${tag.name}, got ${tag.typeArgs.size}"
    }
    if (definition.isEnum) {
      encodeEnum(definition, tag, expect(type = TypeTagStruct(tag), argument), depth)
    } else {
      encodeFields(definition, tag, expect(type = TypeTagStruct(tag), argument), depth)
    }
  }

  private suspend fun AptosBcsWriter.encodeFields(
    definition: MoveStruct,
    tag: StructTag,
    argument: MoveArgument.Struct,
    depth: Int,
  ) {
    require(definition.variants.isEmpty()) { "Non-enum ${definition.name} cannot declare variants" }
    require(definition.fields.map { it.name }.distinct().size == definition.fields.size) {
      "Struct ${definition.name} contains duplicate field names"
    }
    val expectedFields = definition.fields.map { it.name }.toSet()
    require(argument.fields.keys == expectedFields) {
      val missing = expectedFields - argument.fields.keys
      val extra = argument.fields.keys - expectedFields
      "Fields for ${definition.name} do not match its ABI (missing=$missing, extra=$extra)"
    }
    definition.fields.forEach { field ->
      val fieldType = substitute(TypeTag.fromString(field.type, true), tag.typeArgs)
      encodeValue(fieldType, requireNotNull(argument.fields[field.name]), depth + 1)
    }
  }

  private suspend fun AptosBcsWriter.encodeEnum(
    definition: MoveStruct,
    tag: StructTag,
    argument: MoveArgument.Enum,
    depth: Int,
  ) {
    require(definition.fields.isEmpty() && definition.variants.isNotEmpty()) {
      "Enum ${definition.name} requires explicit ABI variants and no struct fields"
    }
    require(definition.variants.map { it.name }.distinct().size == definition.variants.size) {
      "Enum ${definition.name} contains duplicate variant names"
    }
    val variantIndex = definition.variants.indexOfFirst { it.name == argument.variant }
    require(variantIndex >= 0) { "Unknown ${definition.name} variant '${argument.variant}'" }
    val variant = definition.variants[variantIndex]
    require(variant.fields.map { it.name }.distinct().size == variant.fields.size) {
      "Enum ${definition.name} variant ${variant.name} contains duplicate fields"
    }
    require(argument.fields.keys == variant.fields.map { it.name }.toSet()) {
      "Fields for ${definition.name}::${variant.name} do not match its ABI"
    }
    uleb128(variantIndex.toUInt())
    variant.fields.forEach { field ->
      val type = substitute(TypeTag.fromString(field.type, true), tag.typeArgs)
      encodeValue(type, requireNotNull(argument.fields[field.name]), depth + 1)
    }
  }

  private suspend fun module(address: AccountAddress, name: String): MoveModuleBytecode {
    val key = moduleKey(address, name)
    // Loading under the lock coalesces misses and orders loads against preload/invalidation.
    return moduleMutex.withLock {
      val cached = modules[key]
      if (
        cached != null &&
          (cached.loadedAt == null || nowMillis() - cached.loadedAt < cachePolicy.ttlMillis)
      ) {
        modules.remove(key)
        modules[key] = cached
        return@withLock cached.module
      }
      modules.remove(key)
      val loaded =
        when (val result = moduleLoader.load(address, name)) {
          is AptosResult.Success -> result.value
          is AptosResult.Failure -> throw AbiLoadException(result.error)
        }
      val abi = requireNotNull(loaded.abi) { "Module $key has no ABI" }
      require(AccountAddress.fromString(abi.address) == address && abi.name == name) {
        "Loaded module ABI does not match $key"
      }
      if (cachePolicy.ttlMillis > 0) {
        while (modules.values.count { it.loadedAt != null } >= cachePolicy.maxEntries) {
          modules.remove(modules.entries.first { it.value.loadedAt != null }.key)
        }
        modules[key] = CachedModule(loaded, nowMillis())
      }
      loaded
    }
  }

  private fun substitute(type: TypeTag, arguments: List<TypeTag>): TypeTag =
    when (type) {
      is TypeTagGeneric ->
        arguments.getOrNull(type.id.toInt())
          ?: error("Generic type $type has no corresponding type argument")
      is TypeTagVector -> TypeTagVector(substitute(type.type, arguments))
      is TypeTagReference -> TypeTagReference(substitute(type.ref, arguments))
      is TypeTagStruct ->
        TypeTagStruct(
          StructTag(
            address = type.type.address,
            moduleName = type.type.moduleName,
            name = type.type.name,
            typeArgs = type.type.typeArgs.map { substitute(it, arguments) },
          )
        )
      else -> type
    }

  private inline fun <reified T : MoveArgument> expect(type: TypeTag, value: MoveArgument): T =
    value as? T
      ?: throw IllegalArgumentException(
        "Expected ${T::class.simpleName} for $type, got ${value::class.simpleName}"
      )

  private fun StructTag.isBuiltin(module: String, type: String): Boolean =
    address == AccountAddress.ONE && moduleName == module && name == type

  private fun moduleKey(address: AccountAddress, moduleName: String): String =
    "${address.toStringLong()}::$moduleName"

  private class AbiLoadException(val error: AptosError) : RuntimeException(error.message)

  private companion object {
    const val MAX_NESTING_DEPTH = 7
  }
}
