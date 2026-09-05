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
      TypeTagBool -> bool(expect<MoveArgument.Bool>(type, argument).value)
      TypeTagU8 -> u8(expect<MoveArgument.U8>(type, argument).value)
      TypeTagU16 -> u16(expect<MoveArgument.U16>(type, argument).value)
      TypeTagU32 -> u32(expect<MoveArgument.U32>(type, argument).value)
      TypeTagU64 -> u64(expect<MoveArgument.U64>(type, argument).value)
      TypeTagU128 ->
        fixed(unsignedDecimalToLittleEndian(expect<MoveArgument.U128>(type, argument).value, 16))
      TypeTagU256 ->
        fixed(unsignedDecimalToLittleEndian(expect<MoveArgument.U256>(type, argument).value, 32))
      TypeTagI8 -> u8(expect<MoveArgument.I8>(type, argument).value.toUByte())
      TypeTagI16 -> u16(expect<MoveArgument.I16>(type, argument).value.toUShort())
      TypeTagI32 -> u32(expect<MoveArgument.I32>(type, argument).value.toUInt())
      TypeTagI64 -> u64(expect<MoveArgument.I64>(type, argument).value.toULong())
      TypeTagI128 ->
        fixed(signedDecimalToLittleEndian(expect<MoveArgument.I128>(type, argument).value, 16))
      TypeTagI256 ->
        fixed(signedDecimalToLittleEndian(expect<MoveArgument.I256>(type, argument).value, 32))
      TypeTagAddress -> accountAddress(expect<MoveArgument.Address>(type, argument).value)
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
    if (type.type == TypeTagU8 && argument is MoveArgument.Bytes) {
      bytes(argument.value)
      return
    }
    val values = expect<MoveArgument.Vector>(type, argument).values
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
        string(expect<MoveArgument.StringValue>(type, argument).value)
      }
      tag.isBuiltin("object", "Object") -> {
        require(tag.typeArgs.size == 1) { "Move Object requires one type argument" }
        accountAddress(expect<MoveArgument.Address>(type, argument).value)
      }
      tag.isBuiltin("option", "Option") -> {
        require(tag.typeArgs.size == 1) { "Move Option must have one type argument" }
        val value = expect<MoveArgument.Option>(type, argument).value
        if (value == null) {
          uleb128(0u)
        } else {
          uleb128(1u)
          encodeValue(tag.typeArgs.single(), value, depth + 1)
        }
      }
      else -> encodeCustomStruct(tag, argument, depth)
    }
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
