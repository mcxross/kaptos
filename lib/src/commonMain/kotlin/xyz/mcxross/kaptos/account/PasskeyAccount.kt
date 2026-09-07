/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.WebAuthnSignature
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/** Input passed to application-owned passkey credential acquisition. */
class WebAuthnSigningRequest internal constructor(challenge: ByteArray) {
  private val challengeBytes = challenge.copyOf()

  /** SHA3-256 of the exact bytes Kaptos expects the passkey assertion to authorize. */
  val challenge: ByteArray
    get() = challengeBytes.copyOf()
}

/** Application bridge to a platform passkey or external WebAuthn implementation. */
fun interface WebAuthnSigner {
  suspend fun sign(request: WebAuthnSigningRequest): AptosResult<WebAuthnSignature>
}

/** A transaction signer backed by an application-owned P-256 WebAuthn credential. */
class PasskeyAccount(
  private val credentialPublicKey: Secp256r1PublicKey,
  private val signer: WebAuthnSigner,
  address: AccountAddressInput? = null,
) : TransactionSigner {
  override val publicKey = AnyPublicKey(credentialPublicKey)

  override val accountAddress: AccountAddress =
    address?.let(AccountAddress::from) ?: publicKey.authKey().deriveAddress()

  override suspend fun signBytes(message: ByteArray): AptosResult<Signature> {
    val signature = signer.sign(WebAuthnSigningRequest(sha3Hash(message)))
    return when (signature) {
      is AptosResult.Failure -> signature
      is AptosResult.Success -> {
        if (signature.value.verify(credentialPublicKey, message)) {
          AptosResult.Success(AnySignature(signature.value))
        } else {
          AptosResult.Failure(
            AptosError.Crypto("The WebAuthn assertion did not authorize the signing message")
          )
        }
      }
    }
  }

  override suspend fun signText(message: String): AptosResult<Signature> =
    signBytes(message.encodeToByteArray())

  override suspend fun signTransaction(
    transaction: UnsignedTransaction
  ): AptosResult<AccountAuthenticator> =
    when (val signature = signBytes(transaction.signingMessage())) {
      is AptosResult.Failure -> signature
      is AptosResult.Success -> {
        val anySignature =
          signature.value as? AnySignature
            ?: return AptosResult.Failure(
              AptosError.Crypto("Passkey signer returned an incompatible signature")
            )
        AptosResult.Success(AccountAuthenticator.SingleKey(publicKey, anySignature))
      }
    }

  override fun verifySignature(message: ByteArray, signature: Signature): Boolean {
    val actual = if (signature is AnySignature) signature.signature else signature
    return actual is WebAuthnSignature && actual.verify(credentialPublicKey, message)
  }
}
