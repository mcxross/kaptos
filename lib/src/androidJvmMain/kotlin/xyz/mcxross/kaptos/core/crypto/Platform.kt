package xyz.mcxross.kaptos.core.crypto

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import xyz.mcxross.kaptos.model.SigningScheme

@Throws(NoSuchAlgorithmException::class)
actual fun generateKeypair(scheme: SigningScheme): KeyPair {

  when (scheme) {
    SigningScheme.Ed25519 -> {
      val kpg = Ed25519KeyPairGenerator()
      kpg.init(Ed25519KeyGenerationParameters(SecureRandom()))
      val kp = kpg.generateKeyPair()
      val sk = kp.private as Ed25519PrivateKeyParameters
      val pk = kp.public as Ed25519PublicKeyParameters
      return KeyPair(sk.encoded, pk.encoded)
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
