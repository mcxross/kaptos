/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/**
 * Suspend-capable signer contract for local keys, wallets, passkeys, and remote custody systems.
 */
interface TransactionSigner {
  val accountAddress: AccountAddress
  val publicKey: PublicKey
  /** Allows optional protocol modules to reject unsupported signer families before signing. */
  val kind: TransactionSignerKind
    get() = TransactionSignerKind.Standard

  /** Signs arbitrary bytes without imposing a display encoding. */
  suspend fun signBytes(message: ByteArray): AptosResult<Signature>

  /** Signs UTF-8 text. Wallet implementations may present this text to the user. */
  suspend fun signText(message: String): AptosResult<Signature>

  /** Signs the correct domain-separated message for [transaction]. */
  suspend fun signTransaction(transaction: UnsignedTransaction): AptosResult<AccountAuthenticator>

  /** Verifies [signature] against arbitrary bytes using this signer's public key. */
  fun verifySignature(message: ByteArray, signature: Signature): Boolean
}

/** Signer family used for feature compatibility checks before sensitive operations. */
enum class TransactionSignerKind {
  Standard,
  Keyless,
  FederatedKeyless,
}
