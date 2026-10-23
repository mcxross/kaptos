/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.move.MoveArgument

/** Explicit Move values; large integers use validated decimal strings without floating point. */
object Move {
  fun bool(value: Boolean): MoveArgument = MoveArgument.Bool(value)

  fun u8(value: UByte): MoveArgument = MoveArgument.U8(value)

  fun u16(value: UShort): MoveArgument = MoveArgument.U16(value)

  fun u32(value: UInt): MoveArgument = MoveArgument.U32(value)

  fun u64(value: ULong): MoveArgument = MoveArgument.U64(value)

  @Throws(Throwable::class) fun u128(decimal: String): MoveArgument = MoveArgument.U128(decimal)

  @Throws(Throwable::class) fun u256(decimal: String): MoveArgument = MoveArgument.U256(decimal)

  fun string(value: String): MoveArgument = MoveArgument.StringValue(value)

  @Throws(Throwable::class)
  fun address(value: String): MoveArgument = MoveArgument.Address(AccountAddress.fromString(value))

  fun bytes(value: ByteArray): MoveArgument = MoveArgument.Bytes(value)

  fun vector(values: List<MoveArgument>): MoveArgument = MoveArgument.Vector(values)

  fun option(value: MoveArgument?): MoveArgument = MoveArgument.Option(value)

  fun struct(fields: Map<String, MoveArgument>): MoveArgument = MoveArgument.Struct(fields)

  fun variant(name: String, fields: Map<String, MoveArgument>): MoveArgument =
    MoveArgument.Enum(name, fields)

  fun preSerialized(bcs: ByteArray): MoveArgument = MoveArgument.PreSerialized(bcs)
}

object Addresses {
  @Throws(Throwable::class)
  fun parse(value: String): AccountAddress = AccountAddress.fromString(value)
}
