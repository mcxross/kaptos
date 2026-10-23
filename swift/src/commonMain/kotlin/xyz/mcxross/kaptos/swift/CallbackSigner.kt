/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import kotlinx.coroutines.CancellationException
import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/**
 * A wallet or custody signer owned by Swift. Kotlin retains these callbacks while this signer is
 * reachable. Swift export propagates coroutine cancellation in both directions. The transaction
 * callback must sign [UnsignedTransaction.signingMessage], including its domain.
 */
class CallbackSigner(
  override val accountAddress: AccountAddress,
  override val publicKey: PublicKey,
  private val bytes: suspend (ByteArray) -> Signature,
  private val text: suspend (String) -> Signature,
  private val transaction: suspend (UnsignedTransaction) -> AccountAuthenticator,
) : TransactionSigner {
  override suspend fun signBytes(message: ByteArray): AptosResult<Signature> = signingResult {
    bytes(message.copyOf())
  }

  override suspend fun signText(message: String): AptosResult<Signature> = signingResult {
    text(message)
  }

  override suspend fun signTransaction(
    transaction: UnsignedTransaction
  ): AptosResult<AccountAuthenticator> = signingResult { this.transaction(transaction) }

  override fun verifySignature(message: ByteArray, signature: Signature): Boolean =
    publicKey.verifySignature(HexInput.fromByteArray(message), signature)
}

internal suspend fun <T> signingResult(block: suspend () -> T): AptosResult<T> =
  try {
    AptosResult.Success(block())
  } catch (error: CancellationException) {
    throw error
  } catch (error: Throwable) {
    AptosResult.Failure(AptosError.Crypto(error.message ?: "Swift signer failed", error))
  }
