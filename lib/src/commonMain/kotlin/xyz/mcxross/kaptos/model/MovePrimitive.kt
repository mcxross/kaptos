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

@Serializable data class Bool(val value: Boolean) : TransactionArgument()

@Serializable data class U8(val value: Byte) : TransactionArgument()

@Serializable data class U16(val value: UShort) : TransactionArgument()

@Serializable data class U32(val value: UInt) : TransactionArgument()

@Serializable data class U64(val value: ULong) : TransactionArgument()

@Serializable data class U128(val value: String) : TransactionArgument()

@Serializable data class U256(val value: String) : TransactionArgument()
