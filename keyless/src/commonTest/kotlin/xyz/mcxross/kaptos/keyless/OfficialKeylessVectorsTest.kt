/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction

/** Golden vectors from aptos-ts-sdk 7.3.1, commit 82b310449d7adf184540730ffdd754ada18d8ca6. */
class OfficialKeylessVectorsTest {
  @Test
  fun publicKeyCommitmentAndAuthenticationKeyMatchOfficialFixture() {
    val publicKey = KeylessPublicKey.fromJwt(JWT, PEPPER.hexBytes())

    assertTrue(publicKey.idCommitment.contentEquals(ID_COMMITMENT.hexBytes()))
    assertEquals(PUBLIC_KEY_BCS, publicKey.toBcs().hex())
    assertEquals("0x$PUBLIC_KEY_BCS", publicKey.toString())
    assertEquals(AUTHENTICATION_KEY, publicKey.authKey().toString())
    assertEquals(AUTHENTICATION_KEY, publicKey.authKey().deriveAddress().toString())

    val anyPublicKey = AnyPublicKey(publicKey).toBcs()
    assertEquals(3, anyPublicKey.first().toInt())
    assertTrue(anyPublicKey.copyOfRange(1, anyPublicKey.size).contentEquals(publicKey.toBcs()))
  }

  @Test
  fun ephemeralNonceMatchesOfficialFixture() {
    val ephemeral = fixtureEphemeralKeyPair()

    assertEquals(EXPECTED_NONCE, ephemeral.nonce)
    assertEquals(9_876_543_210uL, ephemeral.expiryDateSecs)
    assertFalse(ephemeral.isCleared)
    assertTrue(EphemeralKeyPair.fromBcs(ephemeral.toBcs()).toBcs().contentEquals(ephemeral.toBcs()))
  }

  @Test
  fun proofAndCompleteSignatureRoundTripOfficialBcs() {
    val proofBytes = PROOF_BCS.hexBytes()
    val proof = ZeroKnowledgeSignature.fromBcs(proofBytes)
    assertTrue(proof.toBcs().contentEquals(proofBytes))

    val signatureBytes = SIGNATURE_BCS.hexBytes()
    val signature = KeylessSignature.fromBcs(signatureBytes)
    assertEquals("test-rsa", signature.keyId)
    assertTrue(signature.toBcs().contentEquals(signatureBytes))

    val anySignature = AnySignature(signature).toBcs()
    assertEquals(3, anySignature.first().toInt())
    assertTrue(anySignature.copyOfRange(1, anySignature.size).contentEquals(signatureBytes))
  }

  @Test
  fun cryptographicByteModelsAreDefensivelyImmutableAndUseContentEquality() {
    val sourceA = ByteArray(32) { 1 }
    val sourceB = ByteArray(64) { 2 }
    val sourceC = ByteArray(32) { 3 }
    val proof = Groth16Proof(sourceA, sourceB, sourceC)
    val equivalent = Groth16Proof(sourceA.copyOf(), sourceB.copyOf(), sourceC.copyOf())
    val encoded = proof.toBcs()

    sourceA.fill(9)
    sourceB.fill(9)
    sourceC.fill(9)
    proof.a.fill(8)
    proof.b.fill(8)
    proof.c.fill(8)

    assertEquals(equivalent, proof)
    assertEquals(equivalent.hashCode(), proof.hashCode())
    assertTrue(encoded.contentEquals(proof.toBcs()))

    val ephemeral = fixtureEphemeralKeyPair()
    val ephemeralBcs = ephemeral.toBcs()
    ephemeral.blinder.fill(7)
    assertTrue(ephemeralBcs.contentEquals(ephemeral.toBcs()))
  }

  @Test
  fun offlineGroth16AndTrainingWheelsVerificationMatchesOfficialFixture() {
    val publicKey = KeylessPublicKey.fromJwt(JWT, PEPPER.hexBytes())
    val signature = KeylessSignature.fromBcs(SIGNATURE_BCS.hexBytes())

    assertTrue(
      verifyKeylessSignature(
        publicKey = publicKey,
        message = MESSAGE,
        signature = signature,
        jwk = MoveJwk.fromBcs(JWK_BCS.hexBytes()),
        configuration = CONFIGURATION,
        nowSecs = 9_876_543_209u,
      )
    )
    assertFalse(
      verifyKeylessSignature(
        publicKey = publicKey,
        message = "wrong message".encodeToByteArray(),
        signature = signature,
        jwk = MoveJwk.fromBcs(JWK_BCS.hexBytes()),
        configuration = CONFIGURATION,
        nowSecs = 9_876_543_209u,
      )
    )
  }

  @Test
  fun accountSignsAndSerializesIdiomaticRoundTrip() {
    val account =
      KeylessAccount.create(
        jwt = JWT,
        ephemeralKeyPair = fixtureEphemeralKeyPair(),
        pepper = PEPPER.hexBytes(),
        proof = ZeroKnowledgeSignature.fromBcs(PROOF_BCS.hexBytes()),
      )
    val signature = account.sign(MESSAGE.input())

    assertTrue(
      verifyKeylessSignature(
        publicKey = account.publicKey,
        message = MESSAGE,
        signature = signature,
        jwk = MoveJwk.fromBcs(JWK_BCS.hexBytes()),
        configuration = CONFIGURATION,
        nowSecs = 9_876_543_209u,
      )
    )

    val restored = KeylessAccount.fromBcs(account.toBcs())
    assertEquals(account.accountAddress, restored.accountAddress)
    assertEquals(account.publicKey.toString(), restored.publicKey.toString())
    assertTrue(account.toBcs().contentEquals(restored.toBcs()))
  }

  @Test
  fun transactionSignerBindsTheGroth16ProofIntoTheSigningMessage() = runTest {
    val proof = ZeroKnowledgeSignature.fromBcs(PROOF_BCS.hexBytes())
    val account =
      KeylessAccount.create(
        jwt = JWT,
        ephemeralKeyPair = fixtureEphemeralKeyPair(),
        pepper = PEPPER.hexBytes(),
        proof = proof,
      )
    val transaction =
      UnsignedTransaction.Simple(
        RawTransaction(
          sender = AccountAddress.ONE,
          sequenceNumber = 0uL,
          payload = TransactionPayload.entryFunction("0x1::coin::transfer"),
          maxGasAmount = 2_000uL,
          gasUnitPrice = 100uL,
          expirationTimestampSecs = 9_876_543_000uL,
          chainId = ChainId(4u),
        )
      )

    val authenticator =
      assertIs<AptosResult.Success<AccountAuthenticator>>(account.signTransaction(transaction)).value
    val single = assertIs<AccountAuthenticator.SingleKey>(authenticator)
    val signature = assertIs<KeylessSignature>(single.signature.signature)
    val transactionAndProof =
      KeylessBcsWriter().also { writer ->
        writer.fixed(transaction.signingBcs())
        writer.uleb128(1u)
        writer.uleb128(0u)
        writer.fixed(proof.proof.toBcs())
      }.toByteArray()
    val expectedMessage =
      sha3Hash("APTOS::TransactionAndProof".encodeToByteArray()) + transactionAndProof

    assertTrue(
      signature.ephemeralPublicKey.verifySignature(
        expectedMessage.input(),
        signature.ephemeralSignature,
      )
    )
    assertFalse(
      signature.ephemeralPublicKey.verifySignature(
        transaction.signingMessage().input(),
        signature.ephemeralSignature,
      )
    )
  }

  private fun fixtureEphemeralKeyPair(): EphemeralKeyPair =
    EphemeralKeyPair(
      privateKey = Ed25519PrivateKey(ByteArray(32) { 0x11 }),
      expiryDateSecs = 9_876_543_210uL,
      blinder = ByteArray(31),
    )

  companion object {
    private val MESSAGE = "hello world".encodeToByteArray()
    private const val EXPECTED_NONCE =
      "19643698861265567804909913130507247814526913570228316417377965105681796173098"
    private const val JWT =
      "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InRlc3QtcnNhIn0.eyJpc3MiOiJ0ZXN0Lm9pZGMucHJvdmlkZXIiLCJhdWQiOiJ0ZXN0LWtleWxlc3MtZGFwcCIsInN1YiI6InRlc3QtdXNlci0wIiwiZW1haWwiOiJ0ZXN0QGFwdG9zbGFicy5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaWF0Ijo5ODc2NTQzMjA5LCJleHAiOjk4NzY1NDMyMTAsIm5vbmNlIjoiMTk2NDM2OTg4NjEyNjU1Njc4MDQ5MDk5MTMxMzA1MDcyNDc4MTQ1MjY5MTM1NzAyMjgzMTY0MTczNzc5NjUxMDU2ODE3OTYxNzMwOTgifQ.C6QG9WyEIAqYEiLkY8-5yqTKYtCzmnu2RM4P7iqr17toRXhL2ZqCiQYgE2TpY60RlOqBI7_aiHOlxJRvF_iQghEQQSWkgWhkcjVkSvBJW0IHm0IrSRl9ZytQHi6x0vPa8bUff5L--9JfxMiH27wOTrGtTA1n8Fz3G8JKQfYNQF2VawzytJu3lywduRj6pZw9-FFTgPqPsZWQvwhiX75Tgud976CpDusKOrPAM3rA9fXgKo_aTKeOPiEIm11ezI1bsOJ3B4JhsxLT5vszZ11Ywytst8XXwqWHjnulkJWjM9QfVUJhsO-jEQ5T_dYDqMVnnkdzjJyMRbvgbyNPUkvx8Q"
    private const val PEPPER =
      "772714089792b0bc8c621843bd88599627c74564c47cb4dc7bc0196914a56c"
    private const val ID_COMMITMENT =
      "bdc98aab184dc40bbb5c483410ccac4c0b2ef20eeac8d568cf25125e9cdafc0f"
    private const val PUBLIC_KEY_BCS =
      "12746573742e6f6964632e70726f766964657220bdc98aab184dc40bbb5c483410ccac4c0b2ef20eeac8d568cf25125e9cdafc0f"
    private const val AUTHENTICATION_KEY =
      "0x3d255a4ea36dfedc32205a522f440064fab38fb2d8cf727642d113cb8d43045f"
    private const val PROOF_BCS =
      "00ac1c3add4fa703c66a940e9e947a71bcdb8f30258e72460c01f63d6236d9b2a835188c3ac199bea3905270b9660ddafacbf4f9addb93a3e235e9703ca72c40258362273f596f93594499527ca4802ef40cf0166ba3bd2d65d1a7f50562060127dcdcd995f9ae5e193582cce456f3ddfe8c0c935719ad8636a8e777369279d5a480969800000000000000010040d6433ea43090d25fc4f4a15c362a98a5343dcf4e29e3854f3b74d0d99a0b43abd9955c55a7d20f47372a5802a0e26cc4f860969109d48c9e989dab8287c41501"
    private const val SIGNATURE_BCS =
      "000028edb9b770bd33823ed3aa95d6a464ee61a6370f3662f9edb205e1fad45e3c943296fed377bcece279bd6f68649fdab2f81ca3e83b34fce490492574e2943f04e4f9bfda70c4325e4567bcb58c1a7ccbd67ff9ba618e03be794be0483141bc15a436433070367193c102fbad99c1fed866e34b1f624d64ecfd818a09aa62a41b80969800000000000000010040119893806295fa773fa806a1f5e0754055f773e8a2ca72a0442c23945c004f011c3a03ed218f246e0d758032f16de78b9c93b6868b0b81bea083c114e6d855052c7b22616c67223a225253323536222c22747970223a224a5754222c226b6964223a22746573742d727361227dea16b04c020000000020d04ab232742bb4ab3a1368bd4615e4e6d0224ab71a016baf8520a332c97787370040be6bc1c26488a31fdb030ccd1546e0dcb6dd4ffbada797040deba21231ce894470f1aef38272fe4c77725e77945c6c3b67c6c16d29e2d3ccf4f30bf4374b0f08"
    private const val JWK_BCS =
      "08746573742d727361035253410552533235360441514142d6027935456673315a7a69734c4c4b4341525376547a7467576a354a465033373738645a57742d6f643738666d4f5a4678656d33615f6159624f58534a546f5270383632646f3050784a3450444d706d7177563566374b706c4649364e737751562d57507566514838496148585a74755064436a504f634879626344694c6b4f31326430644736695a51557a79706a414a6636334150636164696f2d344a444e576c4743355f4f775f5851396c495937316b544d6954396c6b434364305a787145696647746e4a653578536f5a6f614d524b72766c4f772d523669566a4c557450416b356879555839354c444b787741522d6f73686e6a37676d4154656a676132457648396f7a646e334d38476f31315053446130344f517850634132354f6f445466784c765432384c5270535872626d55575a2d4f5f6c4774446c335a41746a4967755947456f62546b344e3131655273734339354377"

    private val CONFIGURATION =
      KeylessConfiguration(
        verificationKey =
          Groth16VerificationKey(
            alphaG1 = "e2f26dbea299f5223b646cb1fb33eadb059d9407559d7441dfd902e3a79a4d2d".hexBytes(),
            betaG2 = "abb73dc17fbc13021e2471e0c08bd67d8401f52b73d6d07483794cad4778180e0c06f33bbc4c79a9cadef253a68084d382f17788f885c9afd176f7cb2f036789".hexBytes(),
            deltaG2 = "b106619932d0ef372c46909a2492e246d5de739aa140e27f2c71c0470662f125219049cfe15e4d140d7e4bb911284aad1cad19880efb86f2d9dd4b1bb344ef8f".hexBytes(),
            gammaAbcG1 =
              listOf(
                "6123b6fea40de2a7e3595f9c35210da8a45a7e8c2f7da9eb4548e9210cfea81a".hexBytes(),
                "32a9b8347c512483812ee922dc75952842f8f3083edb6fe8d5c3c07e1340b683".hexBytes(),
              ),
            gammaG2 = "edf692d95cbdde46ddda5ef7d422436779445c5e66006a42761e1f12efde0018c212f3aeb785e49712e7a9353349aaf1255dfb31b7bf60723a480d9293938e19".hexBytes(),
          ),
        trainingWheelsPublicKey =
          xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey(
            "1388de358cf4701696bd58ed4b96e9d670cbbb914b888be1ceda6374a3098ed4".hexBytes()
          ),
      )
  }
}

private fun ByteArray.input() = xyz.mcxross.kaptos.model.HexInput.fromByteArray(this)

private fun String.hexBytes(): ByteArray {
  val value = removePrefix("0x")
  require(value.length % 2 == 0)
  return ByteArray(value.length / 2) { index ->
    value.substring(index * 2, index * 2 + 2).toInt(16).toByte()
  }
}
