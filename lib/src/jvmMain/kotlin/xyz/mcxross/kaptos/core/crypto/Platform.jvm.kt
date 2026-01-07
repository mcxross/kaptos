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

import io.ktor.util.reflect.*
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import xyz.mcxross.kaptos.model.AnyRawTransaction
import xyz.mcxross.kaptos.model.SigningSchemeInput
import xyz.mcxross.kaptos.transaction.builder.deriveTransactionType
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.util.RAW_TRANSACTION_SALT
import xyz.mcxross.bcs.Bcs

import xyz.mcxross.fastkrypto.ed25519Sign
import xyz.mcxross.fastkrypto.ed25519PublicKeyFromPrivate
import xyz.mcxross.fastkrypto.ed25519Verify
import xyz.mcxross.fastkrypto.secp256k1Sign
import xyz.mcxross.fastkrypto.secp256k1PublicKeyFromPrivate
import xyz.mcxross.fastkrypto.secp256k1Verify
import xyz.mcxross.fastkrypto.sha3256

@Throws(NoSuchAlgorithmException::class)
actual fun generateKeypair(scheme: SigningSchemeInput): KeyPair {
  return when (scheme) {
    SigningSchemeInput.Ed25519 -> {
        val seed = ByteArray(32)
        SecureRandom().nextBytes(seed)
        val pk = ed25519PublicKeyFromPrivate(seed)
        KeyPair(seed, pk)
    }
    SigningSchemeInput.Secp256k1 -> {
        val seed = ByteArray(32)
        SecureRandom().nextBytes(seed)
        val pk = secp256k1PublicKeyFromPrivate(seed)
        KeyPair(seed, pk)
    }
    else -> throw NotImplementedError("Only Ed25519 and Secp256k1 are supported at the moment")
  }
}

actual fun fromSeed(seed: ByteArray): KeyPair {
  val pk = ed25519PublicKeyFromPrivate(seed)
  return KeyPair(seed, pk)
}

actual fun sha3Hash(input: ByteArray): ByteArray {
  return sha3256(input)
}

actual fun generateSigningMessage(transaction: AnyRawTransaction): ByteArray {
  val anyRawTxnInstance = deriveTransactionType(transaction)

  // Concatenate prefix and body for hashing
  val prefix = if (anyRawTxnInstance.instanceOf(RawTransaction::class)) {
    sha3256(RAW_TRANSACTION_SALT.encodeToByteArray())
  } else {
    ByteArray(0)
  }

  val body = Bcs.encodeToByteArray<RawTransaction>(anyRawTxnInstance as RawTransaction)

  return prefix + body
}

actual fun sign(message: ByteArray, privateKey: ByteArray): ByteArray {
    return ed25519Sign(privateKey, message)
}

actual fun secp256k1Sign(message: ByteArray, privateKey: ByteArray): ByteArray {
    return secp256k1Sign(privateKey, message)
}

actual fun generateSecp256k1PublicKey(privateKey: ByteArray): ByteArray {
    return secp256k1PublicKeyFromPrivate(privateKey)
}

actual fun verifySignature(
  publicKey: PublicKey,
  message: ByteArray,
  signature: ByteArray,
): Boolean {
  return when (publicKey) {
    is Ed25519PublicKey -> {
        ed25519Verify(publicKey.data, message, signature)
    }

    is Secp256k1PublicKey -> {
        secp256k1Verify(publicKey.hexInput.toByteArray(), message, signature)
    }

    else -> false
  }
}
