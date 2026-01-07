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

import java.math.BigInteger
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.Security
import java.util.*
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.crypto.generators.ECKeyPairGenerator
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.*
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.math.ec.FixedPointCombMultiplier
import xyz.mcxross.kaptos.model.SigningSchemeInput

@Throws(NoSuchAlgorithmException::class)
actual fun generateKeypair(scheme: SigningSchemeInput): KeyPair {
  return when (scheme) {
    SigningSchemeInput.Ed25519 -> {
      val kpg = Ed25519KeyPairGenerator()
      kpg.init(Ed25519KeyGenerationParameters(SecureRandom()))
      val kp = kpg.generateKeyPair()
      val sk = kp.private as Ed25519PrivateKeyParameters
      val pk = kp.public as Ed25519PublicKeyParameters
      KeyPair(sk.encoded, pk.encoded)
    }
    SigningSchemeInput.Secp256k1 -> {
      generateSecp256k1KeyPair()
    }
    else -> throw NotImplementedError("Only Ed25519 and Secp256k1Ecdsa are supported at the moment")
  }
}

fun generateSecp256k1KeyPair(): KeyPair {
  // Get the Secp256k1 domain parameters
  val params = SECNamedCurves.getByName("secp256k1")
  val domainParams = ECDomainParameters(params.curve, params.g, params.n, params.h)

  // Initialize the key pair generator
  val keyPairGenerator = ECKeyPairGenerator()
  val keyGenParams = ECKeyGenerationParameters(domainParams, SecureRandom())
  keyPairGenerator.init(keyGenParams)

  // Generate the key pair
  val keyPair = keyPairGenerator.generateKeyPair()

  // Extract the public and private keys
  val publicKey = keyPair.public as ECPublicKeyParameters
  val privateKey = keyPair.private as ECPrivateKeyParameters

  // Convert the keys to byte arrays
  val publicKeyBytes = publicKey.q.getEncoded(false)
  val privateKeyBytes = privateKey.d.toByteArray()

  // Normalize the private key
  val normalizedPrivateKeyBytes =
    if (privateKeyBytes.size == 33 && privateKeyBytes[0].toInt() == 0) {
      privateKeyBytes.copyOfRange(1, 33)
    } else {
      privateKeyBytes
    }

  // Return the key pair
  return KeyPair(normalizedPrivateKeyBytes, publicKeyBytes)
}

/** Adjusts the byte array to ensure it's exactly 32 bytes, adding leading zeros if necessary. */
fun adjustTo32Bytes(original: ByteArray): ByteArray =
  if (original.size == 33 && original.first() == 0.toByte()) {
    original.copyOfRange(1, 33)
  } else if (original.size < 32) {
    ByteArray(32 - original.size) { 0 } + original
  } else {
    original
  }

actual fun fromSeed(seed: ByteArray): KeyPair {
  val privateKeyParameters = Ed25519PrivateKeyParameters(seed, 0)
  val publicKeyParameters = privateKeyParameters.generatePublicKey()
  return KeyPair(privateKeyParameters.encoded, publicKeyParameters.encoded)
}

actual fun sha3Hash(input: ByteArray): ByteArray {
  val digest = org.bouncycastle.jcajce.provider.digest.SHA3.Digest256()
  return digest.digest(input)
}

actual fun generateSecp256k1PublicKey(privateKey: ByteArray): ByteArray {
  Security.addProvider(BouncyCastleProvider())
  val ecSpec = SECNamedCurves.getByName("secp256k1")
  val domainParameters = ECDomainParameters(ecSpec.curve, ecSpec.g, ecSpec.n, ecSpec.h)
  val privateKeyD = BigInteger(1, privateKey)
  val q = FixedPointCombMultiplier().multiply(domainParameters.g, privateKeyD)
  val publicKeyParameters = ECPublicKeyParameters(q, domainParameters)
  return publicKeyParameters.q.getEncoded(false)
}
