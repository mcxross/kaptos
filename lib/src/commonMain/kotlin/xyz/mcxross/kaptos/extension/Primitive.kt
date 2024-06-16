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
package xyz.mcxross.kaptos.extension

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.Bool

fun Boolean.toMoveType(): Bool {
  return Bool(this)
}

fun Byte.toMoveType(): xyz.mcxross.kaptos.model.U8 {
  return xyz.mcxross.kaptos.model.U8(this)
}

fun Short.toMoveType(): xyz.mcxross.kaptos.model.U16 {
  // We need it signed
  if (this < 0) {
    throw IllegalArgumentException("U16 must be unsigned")
  }
  return xyz.mcxross.kaptos.model.U16(this.toUShort())
}

fun UShort.toMoveType(): xyz.mcxross.kaptos.model.U16 {
  return xyz.mcxross.kaptos.model.U16(this)
}

fun Int.toMoveType(): xyz.mcxross.kaptos.model.U32 {
  // We need it signed
  if (this < 0) {
    throw IllegalArgumentException("U32 must be unsigned")
  }
  return xyz.mcxross.kaptos.model.U32(this.toUInt())
}

fun UInt.toMoveType(): xyz.mcxross.kaptos.model.U32 {
  return xyz.mcxross.kaptos.model.U32(this)
}

fun Long.toMoveType(): xyz.mcxross.kaptos.model.U64 {
  return xyz.mcxross.kaptos.model.U64(this.toULong())
}

fun String.toMoveType(): xyz.mcxross.kaptos.model.U128 {
  return xyz.mcxross.kaptos.model.U128(this)
}

fun String.toMoveType256(): xyz.mcxross.kaptos.model.U256 {
  return xyz.mcxross.kaptos.model.U256(this)
}

fun String.asPrivateKey(): xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
  return xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey(this)
}

fun String.asAccountAddress(): AccountAddress {
  return AccountAddress.fromString(this)
}

fun String.toStructTag(): xyz.mcxross.kaptos.model.StructTag {
  return xyz.mcxross.kaptos.model.StructTag.fromString(this)
}
