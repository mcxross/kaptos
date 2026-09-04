/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import kotlinx.coroutines.Deferred
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.TransactionSignerKind
import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/** Terminal result delivered to background proof-fetch callbacks. */
sealed interface ProofFetchStatus {
  data object Success : ProofFetchStatus

  data class Failed(val error: AptosError) : ProofFetchStatus
}

/** Shared account behavior for standard and federated Keyless authentication. */
sealed class AbstractKeylessAccount protected constructor(
  override val publicKey: AccountPublicKey,
  final override val accountAddress: AccountAddress,
  val jwt: String,
  val uidKey: String,
  val uidValue: String,
  val audience: String,
  pepper: ByteArray,
  val ephemeralKeyPair: EphemeralKeyPair,
  initialProof: ZeroKnowledgeSignature?,
  private val proofDeferred: Deferred<AptosResult<ZeroKnowledgeSignature>>?,
  verificationKeyHash: ByteArray?,
) : Account() {
  private val pepperBytes: ByteArray = pepper.copyOf()
  private val verificationKeyHashBytes: ByteArray? = verificationKeyHash?.copyOf()
  val pepper: ByteArray
    get() = pepperBytes.copyOf()
  val verificationKeyHash: ByteArray?
    get() = verificationKeyHashBytes?.copyOf()
  private var resolvedProof: ZeroKnowledgeSignature? = initialProof

  init {
    require(pepperBytes.size == KEYLESS_PEPPER_LENGTH) {
      "pepper must be $KEYLESS_PEPPER_LENGTH bytes"
    }
    require(verificationKeyHashBytes == null || verificationKeyHashBytes.size == 32) {
      "verificationKeyHash must be 32 bytes"
    }
  }

  val proof: ZeroKnowledgeSignature?
    get() = resolvedProof

  override val signingScheme: SigningScheme = SigningScheme.SingleKey

  override val isPrivateKeyCleared: Boolean
    get() = ephemeralKeyPair.isCleared

  fun isExpired(): Boolean = ephemeralKeyPair.isExpired()

  /** Waits for a background proof fetch, or returns the already resolved proof. */
  suspend fun awaitProof(): AptosResult<ZeroKnowledgeSignature> {
    resolvedProof?.let { return AptosResult.Success(it) }
    val pending =
      proofDeferred
        ?: return AptosResult.Failure(AptosError.Crypto("No Keyless proof is available"))
    return when (val result = pending.await()) {
      is AptosResult.Success -> result.also { resolvedProof = it.value }
      is AptosResult.Failure -> result
    }
  }

  override fun clearPrivateKey() {
    ephemeralKeyPair.clear()
    pepperBytes.fill(0)
  }

  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator =
    AccountAuthenticator.SingleKey(
      publicKey = AnyPublicKey(publicKey),
      signature = AnySignature(sign(message)),
    )

  override fun sign(message: HexInput): KeylessSignature {
    check(!isExpired()) { "Keyless account's ephemeral key pair has expired" }
    val availableProof =
      resolvedProof ?: error("Keyless proof is not ready; call awaitProof() before signing")
    val claims = parseJwt(jwt, uidKey)
    return KeylessSignature(
      proof = availableProof,
      jwtHeader = claims.headerJson,
      expiryDateSecs = ephemeralKeyPair.expiryDateSecs,
      ephemeralPublicKey = ephemeralKeyPair.publicKey,
      ephemeralSignature = ephemeralKeyPair.sign(message.toByteArray()),
    )
  }

  override fun signTransactionSignature(tx: UnsignedTransaction): KeylessSignature =
    sign(HexInput.fromByteArray(transactionAndProofSigningMessage(tx)))

  override suspend fun signTransaction(
    transaction: UnsignedTransaction,
  ): AptosResult<AccountAuthenticator> =
    try {
      AptosResult.Success(
        AccountAuthenticator.SingleKey(
          publicKey = AnyPublicKey(publicKey),
          signature = AnySignature(signTransactionSignature(transaction)),
        )
      )
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Crypto("Unable to sign Keyless transaction", error))
    }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean = false

  override val kind: TransactionSignerKind
    get() =
      if (this is FederatedKeylessAccount) TransactionSignerKind.FederatedKeyless
      else TransactionSignerKind.Keyless

  /** Canonically serializes the account only after its proof is available. */
  fun toBcs(): ByteArray {
    val availableProof = resolvedProof ?: error("Cannot serialize a Keyless account before proof fetch")
    return KeylessBcsWriter().also { writer ->
      writer.address(accountAddress)
      writer.string(jwt)
      writer.string(uidKey)
      writer.fixed(pepperBytes)
      writer.fixed(ephemeralKeyPair.toBcs())
      writer.fixed(availableProof.toBcs())
      writer.option(verificationKeyHashBytes) { fixed(it) }
      if (this is FederatedKeylessAccount) writer.address(publicKey.jwkAddress)
    }.toByteArray()
  }

  private fun transactionAndProofSigningMessage(transaction: UnsignedTransaction): ByteArray {
    val availableProof =
      resolvedProof ?: error("Keyless proof is not ready; call awaitProof() before signing")
    val value =
      KeylessBcsWriter().also { writer ->
        writer.fixed(transaction.signingBcs())
        writer.uleb128(1u)
        writer.uleb128(0u) // ZkpVariant::Groth16
        writer.fixed(availableProof.proof.toBcs())
      }.toByteArray()
    return sha3Hash("APTOS::TransactionAndProof".encodeToByteArray()) + value
  }
}

/** Standard framework-JWK Keyless account. Create it through [KeylessService] or [create]. */
class KeylessAccount internal constructor(
  publicKey: KeylessPublicKey,
  accountAddress: AccountAddress,
  jwt: String,
  uidKey: String,
  uidValue: String,
  audience: String,
  pepper: ByteArray,
  ephemeralKeyPair: EphemeralKeyPair,
  proof: ZeroKnowledgeSignature?,
  proofDeferred: Deferred<AptosResult<ZeroKnowledgeSignature>>?,
  verificationKeyHash: ByteArray?,
) : AbstractKeylessAccount(
  publicKey,
  accountAddress,
  jwt,
  uidKey,
  uidValue,
  audience,
  pepper,
  ephemeralKeyPair,
  proof,
  proofDeferred,
  verificationKeyHash,
) {
  override val publicKey: KeylessPublicKey
    get() = super.publicKey as KeylessPublicKey

  companion object {
    fun create(
      jwt: String,
      ephemeralKeyPair: EphemeralKeyPair,
      pepper: ByteArray,
      proof: ZeroKnowledgeSignature,
      uidKey: String = "sub",
      address: AccountAddress? = null,
      verificationKeyHash: ByteArray? = null,
    ): KeylessAccount {
      val claims = parseJwt(jwt, uidKey)
      val publicKey = KeylessPublicKey.fromJwt(jwt, pepper, uidKey)
      return KeylessAccount(
        publicKey = publicKey,
        accountAddress = address ?: publicKey.authKey().deriveAddress(),
        jwt = jwt,
        uidKey = uidKey,
        uidValue = claims.uid,
        audience = claims.audience,
        pepper = pepper,
        ephemeralKeyPair = ephemeralKeyPair,
        proof = proof,
        proofDeferred = null,
        verificationKeyHash = verificationKeyHash,
      )
    }

    fun fromBcs(bytes: ByteArray): KeylessAccount = decodeAccount(bytes, federated = false) as KeylessAccount
  }
}

/** Account whose issuer keys come from an account-owned federated JWK resource. */
class FederatedKeylessAccount internal constructor(
  publicKey: FederatedKeylessPublicKey,
  accountAddress: AccountAddress,
  jwt: String,
  uidKey: String,
  uidValue: String,
  audience: String,
  pepper: ByteArray,
  ephemeralKeyPair: EphemeralKeyPair,
  proof: ZeroKnowledgeSignature?,
  proofDeferred: Deferred<AptosResult<ZeroKnowledgeSignature>>?,
  verificationKeyHash: ByteArray?,
) : AbstractKeylessAccount(
  publicKey,
  accountAddress,
  jwt,
  uidKey,
  uidValue,
  audience,
  pepper,
  ephemeralKeyPair,
  proof,
  proofDeferred,
  verificationKeyHash,
) {
  override val publicKey: FederatedKeylessPublicKey
    get() = super.publicKey as FederatedKeylessPublicKey

  companion object {
    fun create(
      jwt: String,
      ephemeralKeyPair: EphemeralKeyPair,
      pepper: ByteArray,
      proof: ZeroKnowledgeSignature,
      jwkAddress: AccountAddress,
      uidKey: String = "sub",
      address: AccountAddress? = null,
      verificationKeyHash: ByteArray? = null,
    ): FederatedKeylessAccount {
      val claims = parseJwt(jwt, uidKey)
      val publicKey = FederatedKeylessPublicKey.fromJwt(jwt, pepper, jwkAddress, uidKey)
      return FederatedKeylessAccount(
        publicKey = publicKey,
        accountAddress = address ?: publicKey.authKey().deriveAddress(),
        jwt = jwt,
        uidKey = uidKey,
        uidValue = claims.uid,
        audience = claims.audience,
        pepper = pepper,
        ephemeralKeyPair = ephemeralKeyPair,
        proof = proof,
        proofDeferred = null,
        verificationKeyHash = verificationKeyHash,
      )
    }

    fun fromBcs(bytes: ByteArray): FederatedKeylessAccount =
      decodeAccount(bytes, federated = true) as FederatedKeylessAccount
  }
}

private fun decodeAccount(bytes: ByteArray, federated: Boolean): AbstractKeylessAccount {
  val reader = KeylessBcsReader(bytes)
  val address = reader.address()
  val jwt = reader.string()
  val uidKey = reader.string()
  val pepper = reader.fixed(KEYLESS_PEPPER_LENGTH)
  require(reader.uleb128() == 0u) { "Only Ed25519 ephemeral keys are supported" }
  val privateKey = Ed25519PrivateKey(reader.bytes())
  val expiry = reader.u64()
  val blinder = reader.fixed(EphemeralKeyPair.BLINDER_LENGTH)
  val ephemeralKeyPair = EphemeralKeyPair(privateKey, expiry, blinder)
  require(reader.uleb128() == 0u) { "Only Groth16 Keyless proofs are supported" }
  val proof = Groth16Proof.fromBcs(reader.fixed(128))
  val horizon = reader.u64()
  val extra = reader.option { string() }
  val overrideAudience = reader.option { string() }
  val training =
    reader.option {
      require(uleb128() == 0u) { "Only Ed25519 training-wheels signatures are supported" }
      Ed25519Signature(bytes())
    }
  val zeroKnowledgeSignature =
    ZeroKnowledgeSignature(proof, horizon, extra, overrideAudience, training)
  val verificationKeyHash = reader.option { fixed(32) }
  val jwkAddress = if (federated) reader.address() else null
  reader.ensureFinished()
  val claims = parseJwt(jwt, uidKey)
  return if (jwkAddress == null) {
    val publicKey = KeylessPublicKey.fromJwt(jwt, pepper, uidKey)
    KeylessAccount(
      publicKey,
      address,
      jwt,
      uidKey,
      claims.uid,
      claims.audience,
      pepper,
      ephemeralKeyPair,
      zeroKnowledgeSignature,
      null,
      verificationKeyHash,
    )
  } else {
    val publicKey = FederatedKeylessPublicKey.fromJwt(jwt, pepper, jwkAddress, uidKey)
    FederatedKeylessAccount(
      publicKey,
      address,
      jwt,
      uidKey,
      claims.uid,
      claims.audience,
      pepper,
      ephemeralKeyPair,
      zeroKnowledgeSignature,
      null,
      verificationKeyHash,
    )
  }
}
