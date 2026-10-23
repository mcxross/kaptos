/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

/** Copying conversion avoids signed-byte truncation and unsupported ByteArray constructors. */
object ByteCodec {
  @Throws(Throwable::class)
  fun fromOctets(values: List<Int>): ByteArray {
    require(values.all { it in 0..255 }) { "Octets must be in 0...255" }
    return ByteArray(values.size) { values[it].toByte() }
  }

  fun toOctets(bytes: ByteArray): List<Int> = bytes.map { it.toInt() and 255 }
}
