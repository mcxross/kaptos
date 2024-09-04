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
package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.model.AnyRawTransaction
import xyz.mcxross.kaptos.model.SigningSchemeInput

expect fun generateKeypair(scheme: SigningSchemeInput): KeyPair

expect fun fromSeed(seed: ByteArray): KeyPair

expect fun sha3Hash(input: ByteArray): ByteArray

expect fun sign(message: ByteArray, privateKey: ByteArray): ByteArray

expect fun secp256k1Sign(message: ByteArray, privateKey: ByteArray): ByteArray

expect fun generateSigningMessage(transaction: AnyRawTransaction): ByteArray

expect fun generateSecp256k1PublicKey(privateKey: ByteArray) : ByteArray