/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.move

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.bcs.signedDecimalToLittleEndian
import xyz.mcxross.kaptos.transaction.bcs.unsignedDecimalToLittleEndian

/** Kotlin-native representation of values accepted by Move transaction and view functions. */
sealed interface MoveArgument {
  /** Serializes ABI-independent primitive/container arguments to canonical BCS. */
  fun toBcs(): ByteArray = AptosBcsWriter().also { encode(it) }.toByteArray()

  data class Bool(val value: Boolean) : MoveArgument

  data class U8(val value: UByte) : MoveArgument

  data class U16(val value: UShort) : MoveArgument

  data class U32(val value: UInt) : MoveArgument

  data class U64(val value: ULong) : MoveArgument

  data class U128(val value: String) : MoveArgument {
    init {
      unsignedDecimalToLittleEndian(value, 16)
    }
  }

  data class U256(val value: String) : MoveArgument {
    init {
      unsignedDecimalToLittleEndian(value, 32)
    }
  }

  data class I8(val value: Byte) : MoveArgument

  data class I16(val value: Short) : MoveArgument

  data class I32(val value: Int) : MoveArgument

  data class I64(val value: Long) : MoveArgument

  data class I128(val value: String) : MoveArgument {
    init {
      signedDecimalToLittleEndian(value, 16)
    }
  }

  data class I256(val value: String) : MoveArgument {
    init {
      signedDecimalToLittleEndian(value, 32)
    }
  }

  data class Address(val value: AccountAddress) : MoveArgument

  data class StringValue(val value: String) : MoveArgument

  data class Vector(val values: List<MoveArgument>) : MoveArgument

  class Bytes(value: ByteArray) : MoveArgument {
    private val bytes = value.copyOf()
    val value: ByteArray
      get() = bytes.copyOf()

    override fun equals(other: Any?): Boolean = other is Bytes && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "Bytes(${bytes.size})"
  }

  data class Option(val value: MoveArgument?) : MoveArgument

  /** A Move struct expressed by field name; ABI resolution determines declaration order. */
  data class Struct(val fields: Map<String, MoveArgument>) : MoveArgument

  /** A Move enum expressed by variant and field name; ABI resolution determines indices/order. */
  data class Enum(
    val variant: String,
    val fields: Map<String, MoveArgument> = emptyMap(),
  ) : MoveArgument

  /** Escape hatch for a value that has already been serialized according to its Move ABI type. */
  class PreSerialized(value: ByteArray) : MoveArgument {
    private val bytes = value.copyOf()
    val value: ByteArray
      get() = bytes.copyOf()

    override fun equals(other: Any?): Boolean =
      other is PreSerialized && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "PreSerialized(${bytes.size})"
  }
}

private fun MoveArgument.encode(writer: AptosBcsWriter) {
  when (this) {
    is MoveArgument.Bool -> writer.bool(value)
    is MoveArgument.U8 -> writer.u8(value)
    is MoveArgument.U16 -> writer.u16(value)
    is MoveArgument.U32 -> writer.u32(value)
    is MoveArgument.U64 -> writer.u64(value)
    is MoveArgument.U128 -> writer.fixed(unsignedDecimalToLittleEndian(value, 16))
    is MoveArgument.U256 -> writer.fixed(unsignedDecimalToLittleEndian(value, 32))
    is MoveArgument.I8 -> writer.u8(value.toUByte())
    is MoveArgument.I16 -> writer.u16(value.toUShort())
    is MoveArgument.I32 -> writer.u32(value.toUInt())
    is MoveArgument.I64 -> writer.u64(value.toULong())
    is MoveArgument.I128 -> writer.fixed(signedDecimalToLittleEndian(value, 16))
    is MoveArgument.I256 -> writer.fixed(signedDecimalToLittleEndian(value, 32))
    is MoveArgument.Address -> writer.accountAddress(value)
    is MoveArgument.StringValue -> writer.string(value)
    is MoveArgument.Vector -> writer.vector(values) { it.encode(this) }
    is MoveArgument.Bytes -> writer.bytes(value)
    is MoveArgument.Option -> writer.option(value) { it.encode(this) }
    is MoveArgument.Struct ->
      throw IllegalArgumentException("Move struct arguments must be encoded with their ABI")
    is MoveArgument.Enum ->
      throw IllegalArgumentException("Move enum arguments must be encoded with their ABI")
    is MoveArgument.PreSerialized -> writer.fixed(value)
  }
}
