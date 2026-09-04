/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.AbstractPublicKey
import xyz.mcxross.kaptos.core.crypto.AbstractSignature
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

/** Application-owned callback that produces authentication data for an on-chain function. */
fun interface AbstractionSigner {
  suspend fun sign(digest: ByteArray): AptosResult<ByteArray>
}

/** A transaction signer whose authentication logic is implemented by a Move function. */
open class AbstractedAccount(
  override val accountAddress: AccountAddress,
  val authenticationFunction: AuthenticationFunction,
  signer: AbstractionSigner,
  accountIdentity: ByteArray? = null,
) : TransactionSigner {
  override val publicKey = AbstractPublicKey(accountAddress)

  private var signer: AbstractionSigner = signer
  private val identityBytes = accountIdentity?.copyOf()

  /** Replace application-owned signing context after wallet or policy state changes. */
  fun updateSigner(signer: AbstractionSigner) {
    this.signer = signer
  }

  override suspend fun signBytes(message: ByteArray): AptosResult<Signature> =
    when (val result = signer.sign(message.copyOf())) {
      is AptosResult.Failure -> result
      is AptosResult.Success -> AptosResult.Success(AbstractSignature(result.value))
    }

  override suspend fun signText(message: String): AptosResult<Signature> =
    signBytes(message.encodeToByteArray())

  override suspend fun signTransaction(
    transaction: UnsignedTransaction
  ): AptosResult<AccountAuthenticator> {
    val abstractionSigningMessage =
      signingMessage(transaction.signingMessage(), authenticationFunction)
    val digest = sha3Hash(abstractionSigningMessage)
    return when (val signature = signer.sign(digest.copyOf())) {
      is AptosResult.Failure -> signature
      is AptosResult.Success -> {
        val authenticationData =
          if (identityBytes == null) AbstractSignature(signature.value).toBcs()
          else signature.value
        AptosResult.Success(
          AccountAuthenticator.Abstraction(
            function = authenticationFunction,
            signingMessageDigest = ByteString(digest),
            signature = ByteString(authenticationData),
            accountIdentity = identityBytes?.let(::ByteString),
          )
        )
      }
    }
  }

  override fun verifySignature(message: ByteArray, signature: Signature): Boolean = false

  companion object {
    private const val SIGNING_DATA_SALT = "APTOS::AASigningData"

    /** Exact bytes that are hashed and passed to an abstraction signer for a transaction. */
    fun signingMessage(
      originalSigningMessage: ByteArray,
      authenticationFunction: AuthenticationFunction,
    ): ByteArray {
      val message =
        AptosBcsWriter()
          .also { writer ->
            writer.uleb128(0u)
            writer.bytes(originalSigningMessage)
            writer.accountAddress(authenticationFunction.module.address)
            writer.string(authenticationFunction.module.name.toString())
            writer.string(authenticationFunction.function.toString())
          }
          .toByteArray()
      return sha3Hash(SIGNING_DATA_SALT.encodeToByteArray()) + message
    }
  }
}

/** An abstracted account whose address is deterministically derived from an identity byte string. */
open class DerivableAbstractedAccount(
  authenticationFunction: AuthenticationFunction,
  abstractPublicKey: ByteArray,
  signer: AbstractionSigner,
) : AbstractedAccount(
    accountAddress = computeAccountAddress(authenticationFunction, abstractPublicKey),
    authenticationFunction = authenticationFunction,
    signer = signer,
    accountIdentity = abstractPublicKey,
  ) {
  private val publicKeyBytes = abstractPublicKey.copyOf()

  val abstractPublicKey: ByteArray
    get() = publicKeyBytes.copyOf()

  companion object {
    const val ADDRESS_DOMAIN_SEPARATOR: UByte = 5u

    fun computeAccountAddress(
      authenticationFunction: AuthenticationFunction,
      accountIdentity: ByteArray,
    ): AccountAddress {
      val functionBytes =
        AptosBcsWriter()
          .also { writer ->
            writer.accountAddress(authenticationFunction.module.address)
            writer.string(authenticationFunction.module.name.toString())
            writer.string(authenticationFunction.function.toString())
          }
          .toByteArray()
      val identityBytes =
        AptosBcsWriter().also { it.bytes(accountIdentity) }.toByteArray()
      return AccountAddress(
        sha3Hash(functionBytes + identityBytes + byteArrayOf(ADDRESS_DOMAIN_SEPARATOR.toByte()))
      )
    }
  }
}
