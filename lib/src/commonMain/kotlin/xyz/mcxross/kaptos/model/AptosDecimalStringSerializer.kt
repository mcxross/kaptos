/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** Internal decimal-string adapter for Kotlin [ULong] REST fields. */
internal object AptosDecimalStringULongSerializer : KSerializer<ULong> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("aptos.u64.decimal-string", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: ULong) =
    encoder.encodeString(value.toString())

  override fun deserialize(decoder: Decoder): ULong {
    val raw = decoder.decodeString()
    return raw.toULongOrNull() ?: throw IllegalArgumentException("Invalid Aptos u64: $raw")
  }
}
