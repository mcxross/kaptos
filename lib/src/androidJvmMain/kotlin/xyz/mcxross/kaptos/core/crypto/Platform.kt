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
import java.util.*
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.*
import xyz.mcxross.kaptos.model.SigningSchemeInput

@Throws(NoSuchAlgorithmException::class)
actual fun generateKeypair(scheme: SigningSchemeInput): KeyPair {

  when (scheme) {
    SigningSchemeInput.Ed25519 -> {
      val kpg = Ed25519KeyPairGenerator()
      kpg.init(Ed25519KeyGenerationParameters(SecureRandom()))
      val kp = kpg.generateKeyPair()
      val sk = kp.private as Ed25519PrivateKeyParameters
      val pk = kp.public as Ed25519PublicKeyParameters
      return KeyPair(sk.encoded, pk.encoded)
    }
    SigningSchemeInput.Secp256k1Ecdsa -> {
      return KeyPair(byteArrayOf(0), byteArrayOf(0))
    }
    else -> throw NotImplementedError("Only Ed25519 is supported at the moment")
  }
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
