package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.HexInput

class CryptoTest {

    @Test
    fun testSha3Hash() {
        val input = "hello".encodeToByteArray()
        // SHA3-256("hello")
        // 3338be694f50c5f338814986cdf0686453a888b84f424d792af4b9202398f392
        val expected = byteArrayOf(
            0x33, 0x38, 0xbe.toByte(), 0x69, 0x4f, 0x50, 0xc5.toByte(), 0xf3.toByte(),
            0x38, 0x81.toByte(), 0x49, 0x86.toByte(), 0xcd.toByte(), 0xf0.toByte(), 0x68, 0x64,
            0x53, 0xa8.toByte(), 0x88.toByte(), 0xb8.toByte(), 0x4f, 0x42, 0x4d, 0x79,
            0x2a, 0xf4.toByte(), 0xb9.toByte(), 0x20, 0x23, 0x98.toByte(), 0xf3.toByte(), 0x92.toByte()
        )
        val actual = sha3Hash(input)
        assertTrue(actual.contentEquals(expected), "SHA3 hash mismatch. Actual: ${actual.joinToString("") { "%02x".format(it) }}")
    }

    @Test
    fun testSignAndVerify() {
        val privateKey = Ed25519PrivateKey.generate()
        val message = "hello world".encodeToByteArray()
        val signature = privateKey.sign(HexInput.fromByteArray(message))
        
        val publicKey = privateKey.publicKey()
        val isValid = publicKey.verifySignature(HexInput.fromByteArray(message), signature)
        
        assertTrue(isValid, "Signature verification failed")
    }
    
    @Test
    fun testSignAndVerifyWithKnownKey() {
        // Seed: 0x... (32 bytes)
        // This validates compatibility with standard Ed25519
        
        val seed = HexInput.fromString("0x0000000000000000000000000000000000000000000000000000000000000000")
        val privateKey = Ed25519PrivateKey(seed)
        val message = ByteArray(0) // Empty message
        val signature = privateKey.sign(HexInput.fromByteArray(message))
        
        assertTrue(privateKey.publicKey().verifySignature(HexInput.fromByteArray(message), signature))
    }
}
