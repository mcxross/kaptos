/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.fastkrypto.Bn254PreparedVerifyingKeyBytes
import xyz.mcxross.fastkrypto.bn254PrepareGroth16VerifyingKeyComponents
import xyz.mcxross.fastkrypto.bn254VerifyGroth16
import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnyPublicKeyCompatible
import xyz.mcxross.kaptos.core.crypto.AnySignatureCompatible
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.SimulationSignatureProvider
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AnyPublicKeyVariant
import xyz.mcxross.kaptos.model.AnySignatureVariant
import xyz.mcxross.kaptos.model.HexInput

/** Canonical byte length of an Aptos Keyless pepper. */
const val KEYLESS_PEPPER_LENGTH: Int = 31

/** Canonical byte length of a Keyless identity commitment. */
const val KEYLESS_ID_COMMITMENT_LENGTH: Int = 32

private const val MAX_AUD_BYTES = 120
private const val MAX_UID_KEY_BYTES = 30
private const val MAX_UID_VALUE_BYTES = 330

/** Standard Aptos Keyless account public key. */
class KeylessPublicKey(
  val issuer: String,
  idCommitment: ByteArray,
) : AccountPublicKey(), AnyPublicKeyCompatible, SimulationSignatureProvider {
  private val idCommitmentBytes: ByteArray = idCommitment.copyOf()

  val idCommitment: ByteArray
    get() = idCommitmentBytes.copyOf()

  init {
    require(issuer.isNotBlank()) { "issuer cannot be blank" }
    require(idCommitmentBytes.size == KEYLESS_ID_COMMITMENT_LENGTH) {
      "idCommitment must be $KEYLESS_ID_COMMITMENT_LENGTH bytes"
    }
  }

  override val anyPublicKeyVariant: AnyPublicKeyVariant = AnyPublicKeyVariant.Keyless

  override fun authKey(): AuthenticationKey = AnyPublicKey(this).authKey()

  /** Full proof verification requires a JWK and on-chain configuration; use `KeylessService.verify`. */
  override fun verifySignature(message: HexInput, signature: Signature): Boolean = false

  // Aptos defines a Keyless public key's byte representation as the BCS tuple
  // `(issuer, id_commitment)`, which is also what the TS SDK exposes.
  override fun toByteArray(): ByteArray = toBcs()

  override fun toBcs(): ByteArray =
    KeylessBcsWriter().also {
      it.string(issuer)
      it.bytes(idCommitmentBytes)
    }.toByteArray()

  override fun toAnyPublicKeyBcs(): ByteArray = toBcs()

  override fun simulationSignature(): KeylessSignature = KeylessSignature.simulation()

  companion object {
    fun create(
      issuer: String,
      audience: String,
      uidKey: String,
      uidValue: String,
      pepper: ByteArray,
    ): KeylessPublicKey {
      require(pepper.size == KEYLESS_PEPPER_LENGTH) {
        "pepper must be $KEYLESS_PEPPER_LENGTH bytes"
      }
      val commitment =
        poseidon(
          listOf(
            pepper.copyOf(32),
            hashStringToField(audience, MAX_AUD_BYTES),
            hashStringToField(uidValue, MAX_UID_VALUE_BYTES),
            hashStringToField(uidKey, MAX_UID_KEY_BYTES),
          )
        )
      return KeylessPublicKey(issuer, commitment)
    }

    fun fromJwt(jwt: String, pepper: ByteArray, uidKey: String = "sub"): KeylessPublicKey {
      val claims = parseJwt(jwt, uidKey)
      return create(claims.issuer, claims.audience, uidKey, claims.uid, pepper)
    }

    fun fromBcs(bytes: ByteArray): KeylessPublicKey =
      KeylessBcsReader(bytes).let { reader ->
        KeylessPublicKey(reader.string(), reader.bytes()).also { reader.ensureFinished() }
      }
  }
}

/** Keyless public key whose JWK source is an account-owned federated JWK resource. */
class FederatedKeylessPublicKey(
  val jwkAddress: AccountAddress,
  val keylessPublicKey: KeylessPublicKey,
) : AccountPublicKey(), AnyPublicKeyCompatible, SimulationSignatureProvider {
  override val anyPublicKeyVariant: AnyPublicKeyVariant = AnyPublicKeyVariant.FederatedKeyless

  override fun authKey(): AuthenticationKey = AnyPublicKey(this).authKey()

  override fun verifySignature(message: HexInput, signature: Signature): Boolean = false

  override fun toByteArray(): ByteArray = toBcs()

  override fun toBcs(): ByteArray =
    KeylessBcsWriter().also {
      it.address(jwkAddress)
      it.fixed(keylessPublicKey.toBcs())
    }.toByteArray()

  override fun toAnyPublicKeyBcs(): ByteArray = toBcs()

  override fun simulationSignature(): KeylessSignature = KeylessSignature.simulation()

  companion object {
    fun fromJwt(
      jwt: String,
      pepper: ByteArray,
      jwkAddress: AccountAddress,
      uidKey: String = "sub",
    ): FederatedKeylessPublicKey =
      FederatedKeylessPublicKey(jwkAddress, KeylessPublicKey.fromJwt(jwt, pepper, uidKey))
  }
}

/** Canonically encoded BN254 Groth16 proof points. */
class Groth16Proof(a: ByteArray, b: ByteArray, c: ByteArray) {
  private val aBytes = a.copyOf()
  private val bBytes = b.copyOf()
  private val cBytes = c.copyOf()

  val a: ByteArray
    get() = aBytes.copyOf()

  val b: ByteArray
    get() = bBytes.copyOf()

  val c: ByteArray
    get() = cBytes.copyOf()

  init {
    require(aBytes.size == 32) { "Groth16 proof point a must be 32 bytes" }
    require(bBytes.size == 64) { "Groth16 proof point b must be 64 bytes" }
    require(cBytes.size == 32) { "Groth16 proof point c must be 32 bytes" }
  }

  fun toBcs(): ByteArray = aBytes + bBytes + cBytes

  override fun equals(other: Any?): Boolean =
    other is Groth16Proof &&
      aBytes.contentEquals(other.aBytes) &&
      bBytes.contentEquals(other.bBytes) &&
      cBytes.contentEquals(other.cBytes)

  override fun hashCode(): Int =
    31 * (31 * aBytes.contentHashCode() + bBytes.contentHashCode()) + cBytes.contentHashCode()

  companion object {
    fun fromBcs(bytes: ByteArray): Groth16Proof {
      require(bytes.size == 128) { "Groth16 proof must be 128 bytes" }
      return Groth16Proof(
        bytes.copyOfRange(0, 32),
        bytes.copyOfRange(32, 96),
        bytes.copyOfRange(96, 128),
      )
    }
  }
}

/** Aptos zero-knowledge certificate and optional audience/training-wheels constraints. */
data class ZeroKnowledgeSignature(
  val proof: Groth16Proof,
  val expirationHorizonSecs: ULong,
  val extraField: String? = null,
  val overrideAudience: String? = null,
  val trainingWheelsSignature: Ed25519Signature? = null,
) {
  fun toBcs(): ByteArray =
    KeylessBcsWriter().also { writer ->
      writer.uleb128(0u) // ZkpVariant::Groth16
      writer.fixed(proof.toBcs())
      writer.u64(expirationHorizonSecs)
      writer.option(extraField) { string(it) }
      writer.option(overrideAudience) { string(it) }
      writer.option(trainingWheelsSignature) {
        uleb128(0u) // EphemeralSignatureVariant::Ed25519
        bytes(it.toByteArray())
      }
    }.toByteArray()

  override fun equals(other: Any?): Boolean =
    other is ZeroKnowledgeSignature && toBcs().contentEquals(other.toBcs())

  override fun hashCode(): Int = toBcs().contentHashCode()

  companion object {
    fun fromBcs(bytes: ByteArray): ZeroKnowledgeSignature =
      KeylessBcsReader(bytes).let { reader ->
        reader.zeroKnowledgeSignature().also { reader.ensureFinished() }
      }
  }
}

/** Complete Aptos Keyless signature embedded as `AnySignature::Keyless`. */
class KeylessSignature(
  val proof: ZeroKnowledgeSignature,
  val jwtHeader: String,
  val expiryDateSecs: ULong,
  val ephemeralPublicKey: Ed25519PublicKey,
  val ephemeralSignature: Ed25519Signature,
) : Signature(), AnySignatureCompatible {
  override val anySignatureVariant: AnySignatureVariant = AnySignatureVariant.Keyless

  val keyId: String
    get() =
      keylessJson.parseToJsonElement(jwtHeader).jsonObject["kid"]?.jsonPrimitive?.content
        ?: throw IllegalArgumentException("JWT header is missing 'kid'")

  override fun toByteArray(): ByteArray = toBcs()

  override fun toBcs(): ByteArray =
    KeylessBcsWriter().also { writer ->
      writer.uleb128(0u) // EphemeralCertificateVariant::ZkProof
      writer.fixed(proof.toBcs())
      writer.string(jwtHeader)
      writer.u64(expiryDateSecs)
      writer.uleb128(0u) // EphemeralPublicKeyVariant::Ed25519
      writer.bytes(ephemeralPublicKey.toByteArray())
      writer.uleb128(0u) // EphemeralSignatureVariant::Ed25519
      writer.bytes(ephemeralSignature.toByteArray())
    }.toByteArray()

  override fun toAnySignatureBcs(): ByteArray = toBcs()

  companion object {
    /** Decodes the `AnySignature::Keyless` payload, excluding the AnySignature variant. */
    fun fromBcs(bytes: ByteArray): KeylessSignature =
      KeylessBcsReader(bytes).let { reader ->
        require(reader.uleb128() == 0u) {
          "Only zero-knowledge Keyless certificates are supported"
        }
        val proof = reader.zeroKnowledgeSignature()
        val jwtHeader = reader.string()
        val expiryDateSecs = reader.u64()
        require(reader.uleb128() == 0u) { "Only Ed25519 ephemeral public keys are supported" }
        val ephemeralPublicKey = Ed25519PublicKey(reader.bytes())
        require(reader.uleb128() == 0u) { "Only Ed25519 ephemeral signatures are supported" }
        val ephemeralSignature = Ed25519Signature(reader.bytes())
        KeylessSignature(
          proof = proof,
          jwtHeader = jwtHeader,
          expiryDateSecs = expiryDateSecs,
          ephemeralPublicKey = ephemeralPublicKey,
          ephemeralSignature = ephemeralSignature,
        ).also { reader.ensureFinished() }
      }

    /** Canonical zero-filled signature for fullnode transaction simulation. */
    fun simulation(): KeylessSignature =
      KeylessSignature(
        proof =
          ZeroKnowledgeSignature(
            proof = Groth16Proof(ByteArray(32), ByteArray(64), ByteArray(32)),
            expirationHorizonSecs = 0uL,
          ),
        jwtHeader = "{}",
        expiryDateSecs = 0uL,
        ephemeralPublicKey = Ed25519PublicKey(ByteArray(32)),
        ephemeralSignature = Ed25519Signature(ByteArray(64)),
      )
  }
}

/** RSA JWK stored in Aptos framework or federated Move resources. */
data class MoveJwk(
  val keyId: String,
  val keyType: String,
  val algorithm: String,
  val exponent: String,
  val modulus: String,
) {
  init {
    require(keyId.isNotBlank()) { "keyId cannot be blank" }
  }

  fun toBcs(): ByteArray =
    KeylessBcsWriter().also {
      it.string(keyId)
      it.string(keyType)
      it.string(algorithm)
      it.string(exponent)
      it.string(modulus)
    }.toByteArray()

  internal fun toScalar(): ByteArray {
    require(algorithm == "RS256") { "Only RS256 JWKs are supported" }
    val littleEndian = decodeBase64Url(modulus).reversedArray()
    val fields = littleEndian.asList().chunked(24).map { chunk ->
      ByteArray(32).also { output -> chunk.forEachIndexed { index, byte -> output[index] = byte } }
    } + field(256u)
    return poseidon(fields)
  }

  companion object {
    fun fromBcs(bytes: ByteArray): MoveJwk =
      KeylessBcsReader(bytes).let { reader ->
        MoveJwk(
          keyId = reader.string(),
          keyType = reader.string(),
          algorithm = reader.string(),
          exponent = reader.string(),
          modulus = reader.string(),
        ).also { reader.ensureFinished() }
      }
  }
}

/** Aptos Keyless Groth16 verification key with validated compressed BN254 points. */
class Groth16VerificationKey(
  alphaG1: ByteArray,
  betaG2: ByteArray,
  deltaG2: ByteArray,
  gammaAbcG1: List<ByteArray>,
  gammaG2: ByteArray,
) {
  private val alphaG1Bytes = alphaG1.copyOf()
  private val betaG2Bytes = betaG2.copyOf()
  private val deltaG2Bytes = deltaG2.copyOf()
  private val gammaAbcG1Bytes = gammaAbcG1.map { it.copyOf() }
  private val gammaG2Bytes = gammaG2.copyOf()

  val alphaG1: ByteArray
    get() = alphaG1Bytes.copyOf()

  val betaG2: ByteArray
    get() = betaG2Bytes.copyOf()

  val deltaG2: ByteArray
    get() = deltaG2Bytes.copyOf()

  val gammaAbcG1: List<ByteArray>
    get() = gammaAbcG1Bytes.map { it.copyOf() }

  val gammaG2: ByteArray
    get() = gammaG2Bytes.copyOf()

  init {
    require(alphaG1Bytes.size == 32) { "alphaG1 must be 32 bytes" }
    require(betaG2Bytes.size == 64) { "betaG2 must be 64 bytes" }
    require(deltaG2Bytes.size == 64) { "deltaG2 must be 64 bytes" }
    require(gammaG2Bytes.size == 64) { "gammaG2 must be 64 bytes" }
    require(gammaAbcG1Bytes.size == 2 && gammaAbcG1Bytes.all { it.size == 32 }) {
      "gammaAbcG1 must contain two 32-byte points"
    }
  }

  fun hash(): ByteArray =
    sha3Hash(
      alphaG1Bytes +
        betaG2Bytes +
        deltaG2Bytes +
        gammaAbcG1Bytes[0] +
        gammaAbcG1Bytes[1] +
        gammaG2Bytes
    )

  override fun equals(other: Any?): Boolean =
    other is Groth16VerificationKey && hash().contentEquals(other.hash())

  override fun hashCode(): Int = hash().contentHashCode()

  internal fun prepare(): Bn254PreparedVerifyingKeyBytes =
    bn254PrepareGroth16VerifyingKeyComponents(
      alphaG1 = alphaG1Bytes,
      betaG2 = betaG2Bytes,
      gammaG2 = gammaG2Bytes,
      deltaG2 = deltaG2Bytes,
      gammaAbcG1 = gammaAbcG1Bytes,
    )
}

/** On-chain Keyless verification parameters and size/expiry limits. */
data class KeylessConfiguration(
  val verificationKey: Groth16VerificationKey,
  val maxExpirationHorizonSecs: ULong = 10_000_000uL,
  val trainingWheelsPublicKey: Ed25519PublicKey? = null,
  val maxExtraFieldBytes: Int = 350,
  val maxJwtHeaderBase64Bytes: Int = 300,
  val maxIssuerBytes: Int = 120,
  val maxCommittedEphemeralPublicKeyBytes: Int = 93,
) {
  init {
    require(maxExtraFieldBytes in 1..465)
    require(maxJwtHeaderBase64Bytes in 1..465)
    require(maxIssuerBytes in 1..465)
    require(maxCommittedEphemeralPublicKeyBytes in 1..465)
  }
}

/** Local, offline verification using the same Poseidon statement and Groth16 inputs as Aptos. */
fun verifyKeylessSignature(
  publicKey: AccountPublicKey,
  message: ByteArray,
  signature: KeylessSignature,
  jwk: MoveJwk,
  configuration: KeylessConfiguration,
  nowSecs: ULong,
): Boolean =
  runCatching {
    val inner =
      when (publicKey) {
        is KeylessPublicKey -> publicKey
        is FederatedKeylessPublicKey -> publicKey.keylessPublicKey
        else -> return false
      }
    require(signature.expiryDateSecs >= nowSecs) { "Keyless signature has expired" }
    require(
      signature.proof.expirationHorizonSecs <= configuration.maxExpirationHorizonSecs
    ) { "Keyless expiration horizon exceeds the on-chain maximum" }
    require(
      signature.ephemeralPublicKey.verifySignature(
        HexInput.fromByteArray(message),
        signature.ephemeralSignature,
      )
    ) { "Invalid ephemeral signature" }

    val statement =
      buildList {
        addAll(
          padAndPackBytesWithLength(
            KeylessBcsWriter().also {
              it.uleb128(0u)
              it.bytes(signature.ephemeralPublicKey.toByteArray())
            }.toByteArray(),
            configuration.maxCommittedEphemeralPublicKeyBytes,
          )
        )
        add(inner.idCommitment)
        add(field(signature.expiryDateSecs))
        add(field(signature.proof.expirationHorizonSecs))
        add(hashStringToField(inner.issuer, configuration.maxIssuerBytes))
        add(field(if (signature.proof.extraField == null) 0u else 1u))
        add(
          hashStringToField(
            signature.proof.extraField ?: " ",
            configuration.maxExtraFieldBytes,
          )
        )
        val headerBase64 = encodeBase64Url(signature.jwtHeader.encodeToByteArray())
        add(hashStringToField("$headerBase64.", configuration.maxJwtHeaderBase64Bytes))
        add(jwk.toScalar())
        add(hashStringToField(signature.proof.overrideAudience ?: "", MAX_AUD_BYTES))
        add(field(if (signature.proof.overrideAudience == null) 0u else 1u))
      }
    val publicInputsHash = poseidon(statement)
    require(
      bn254VerifyGroth16(
        preparedKey = configuration.verificationKey.prepare(),
        publicInputs = publicInputsHash,
        proof = signature.proof.proof.toBcs(),
      )
    ) { "Groth16 proof verification failed" }

    configuration.trainingWheelsPublicKey?.let { trainingWheelsKey ->
      val trainingSignature =
        signature.proof.trainingWheelsSignature
          ?: throw IllegalArgumentException("Training-wheels signature is required")
      val proofAndStatement = signature.proof.proof.toBcs() + publicInputsHash
      val signingMessage =
        sha3Hash("APTOS::Groth16ProofAndStatement".encodeToByteArray()) + proofAndStatement
      require(
        trainingWheelsKey.verifySignature(
          HexInput.fromByteArray(signingMessage),
          trainingSignature,
        )
      ) { "Invalid training-wheels signature" }
    }
    true
  }.getOrDefault(false)

private fun encodeBase64Url(value: ByteArray): String {
  val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
  val output = StringBuilder((value.size * 4 + 2) / 3)
  var index = 0
  while (index < value.size) {
    val first = value[index++].toInt() and 0xff
    val second = if (index < value.size) value[index++].toInt() and 0xff else -1
    val third = if (index < value.size) value[index++].toInt() and 0xff else -1
    output.append(alphabet[first shr 2])
    output.append(alphabet[((first and 3) shl 4) or if (second >= 0) second shr 4 else 0])
    if (second >= 0) {
      output.append(alphabet[((second and 15) shl 2) or if (third >= 0) third shr 6 else 0])
    }
    if (third >= 0) output.append(alphabet[third and 63])
  }
  return output.toString()
}
