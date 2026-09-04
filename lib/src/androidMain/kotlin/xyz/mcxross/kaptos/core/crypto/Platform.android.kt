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

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import xyz.mcxross.fastkrypto.ed25519PublicKeyFromPrivate
import xyz.mcxross.fastkrypto.ed25519Sign
import xyz.mcxross.fastkrypto.ed25519Verify
import xyz.mcxross.fastkrypto.secp256k1PublicKeyFromPrivate
import xyz.mcxross.fastkrypto.secp256k1Sign
import xyz.mcxross.fastkrypto.secp256k1Verify
import xyz.mcxross.fastkrypto.secp256r1GenerateKeypair
import xyz.mcxross.fastkrypto.secp256r1NormalizePublicKey
import xyz.mcxross.fastkrypto.secp256r1PublicKeyFromPrivate
import xyz.mcxross.fastkrypto.secp256r1SignSha3256
import xyz.mcxross.fastkrypto.secp256r1Verify
import xyz.mcxross.fastkrypto.secp256r1VerifySha3256
import xyz.mcxross.fastkrypto.sha256
import xyz.mcxross.fastkrypto.sha3256
import xyz.mcxross.kaptos.model.SigningSchemeInput

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
    SigningSchemeInput.Secp256r1 -> {
      val keyPair = secp256r1GenerateKeypair()
      KeyPair(keyPair.privateKey, secp256r1NormalizePublicKey(keyPair.publicKey, false))
    }
  }
}

actual fun fromSeed(seed: ByteArray): KeyPair {
  val pk = ed25519PublicKeyFromPrivate(seed)
  return KeyPair(seed, pk)
}

actual fun sha3Hash(input: ByteArray): ByteArray {
  return sha3256(input)
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

internal actual fun secp256r1SignAptos(
  message: ByteArray,
  privateKey: ByteArray,
): ByteArray = secp256r1SignSha3256(privateKey, message)

internal actual fun generateSecp256r1PublicKey(privateKey: ByteArray): ByteArray =
  secp256r1NormalizePublicKey(secp256r1PublicKeyFromPrivate(privateKey), false)

internal actual fun normalizeSecp256r1PublicKey(publicKey: ByteArray): ByteArray =
  secp256r1NormalizePublicKey(publicKey, false)

internal actual fun verifySecp256r1Signature(
  publicKey: ByteArray,
  message: ByteArray,
  signature: ByteArray,
): Boolean = secp256r1VerifySha3256(publicKey, message, signature)

internal actual fun verifyWebAuthnSignature(
  publicKey: ByteArray,
  authenticatorData: ByteArray,
  clientDataJson: ByteArray,
  signature: ByteArray,
): Boolean = secp256r1Verify(publicKey, authenticatorData + sha256(clientDataJson), signature)

internal actual fun generateMnemonic(wordCount: UInt): String =
  xyz.mcxross.fastkrypto.mnemonicGenerate(wordCount)

internal actual fun validateMnemonic(phrase: String): Boolean =
  xyz.mcxross.fastkrypto.mnemonicValidate(phrase)

internal actual fun deriveMnemonicPrivateKey(
  phrase: String,
  passphrase: String,
  type: PrivateKeyType,
  path: String,
): ByteArray =
  xyz.mcxross.fastkrypto.mnemonicDerivePrivateKey(
    phrase,
    passphrase,
    type.toFastKryptoScheme(),
    path,
  )

private fun PrivateKeyType.toFastKryptoScheme(): xyz.mcxross.fastkrypto.SignatureScheme =
  when (this) {
    PrivateKeyType.Ed25519 -> xyz.mcxross.fastkrypto.SignatureScheme.ED25519
    PrivateKeyType.Secp256k1 -> xyz.mcxross.fastkrypto.SignatureScheme.SECP256K1
    PrivateKeyType.Secp256r1 -> xyz.mcxross.fastkrypto.SignatureScheme.SECP256R1
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

    is Secp256r1PublicKey -> {
      secp256r1VerifySha3256(publicKey.toByteArray(), message, signature)
    }

    else -> false
  }
}
