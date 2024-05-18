package xyz.mcxross.kaptos.core.crypto

import java.security.KeyPairGenerator
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
  val kpg = KeyPairGenerator.getInstance("Ed25519")
  val kp = kpg.generateKeyPair()
  val sk: ByteArray = kp.private.encoded
  val pk: ByteArray = kp.public.encoded
  return KeyPair(sk.copyOfRange(12, sk.size), pk.copyOfRange(12, pk.size))
}

actual fun fromSeed(seed: ByteArray): KeyPair {
  val secureRandom = SecureRandom(seed)
  val keyGenParams = Ed25519KeyGenerationParameters(secureRandom)
  val keyPairGenerator = Ed25519KeyPairGenerator()
  keyPairGenerator.init(keyGenParams)
  val keyPair = keyPairGenerator.generateKeyPair()
  val privateKey = keyPair.private as Ed25519PrivateKeyParameters
  val publicKey = keyPair.public as Ed25519PublicKeyParameters
  return KeyPair(privateKey.encoded, publicKey.encoded)
}

actual fun sha3Hash(input: ByteArray): ByteArray {
  val digest = org.bouncycastle.jcajce.provider.digest.SHA3.Digest256()
  return digest.digest(input)
}
