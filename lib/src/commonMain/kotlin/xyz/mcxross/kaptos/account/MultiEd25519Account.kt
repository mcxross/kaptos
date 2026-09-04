/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey
import xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/** A legacy K-of-N Ed25519 account with immutable signer ordering. */
class MultiEd25519Account(
  override val publicKey: MultiEd25519PublicKey,
  privateKeys: List<Ed25519PrivateKey>,
  address: AccountAddressInput? = null,
) : Account() {
  internal val signerPrivateKeys: List<Ed25519PrivateKey> = privateKeys.toList()
  private val signers: List<IndexedSigner>

  override val accountAddress: AccountAddress =
    address?.let(AccountAddress::from) ?: publicKey.authKey().deriveAddress()

  override val signingScheme: SigningScheme = SigningScheme.MultiEd25519

  override val isPrivateKeyCleared: Boolean
    get() = signers.all { it.privateKey.isCleared }

  init {
    require(privateKeys.size >= publicKey.threshold.toInt()) {
      "At least ${publicKey.threshold} private keys are required"
    }
    val indexed =
      signerPrivateKeys.map { privateKey ->
        val signerPublicKey = privateKey.publicKey().toByteArray()
        val index = publicKey.publicKeys.indexOfFirst { it.toByteArray().contentEquals(signerPublicKey) }
        require(index >= 0) { "A private key does not belong to this MultiEd25519 public key" }
        IndexedSigner(index, privateKey)
      }
    require(indexed.map(IndexedSigner::index).distinct().size == indexed.size) {
      "Duplicate MultiEd25519 private key"
    }
    signers = indexed.sortedBy(IndexedSigner::index)
  }

  override fun clearPrivateKey() = signers.forEach { it.privateKey.clear() }

  override fun signWithAuthenticator(message: HexInput): AccountAuthenticator =
    AccountAuthenticator.MultiEd25519(publicKey, sign(message))

  override fun sign(message: HexInput): MultiEd25519Signature =
    MultiEd25519Signature(
      signatures = signers.map { it.privateKey.sign(message) },
      bitmap = MultiEd25519Signature.bitmapOf(signers.map(IndexedSigner::index)),
    )

  override fun signTransactionSignature(tx: UnsignedTransaction): MultiEd25519Signature =
    sign(HexInput.fromByteArray(tx.signingMessage()))

  override fun verifySignature(message: HexInput, signature: Signature): Boolean =
    publicKey.verifySignature(message, signature)

  private data class IndexedSigner(val index: Int, val privateKey: Ed25519PrivateKey)

  companion object {
    fun fromPrivateKeys(
      privateKeys: List<Ed25519PrivateKey>,
      signaturesRequired: Int,
      address: AccountAddressInput? = null,
    ): MultiEd25519Account {
      require(signaturesRequired in 1..privateKeys.size) {
        "signaturesRequired must be between 1 and ${privateKeys.size}"
      }
      val publicKey =
        MultiEd25519PublicKey(
          publicKeys = privateKeys.map(Ed25519PrivateKey::publicKey),
          threshold = signaturesRequired.toUByte(),
        )
      return MultiEd25519Account(publicKey, privateKeys, address)
    }

    fun generate(
      numberOfKeys: Int,
      signaturesRequired: Int,
      address: AccountAddressInput? = null,
    ): MultiEd25519Account {
      require(numberOfKeys in MultiEd25519PublicKey.MIN_KEYS..MultiEd25519PublicKey.MAX_KEYS) {
        "numberOfKeys must be between ${MultiEd25519PublicKey.MIN_KEYS} and ${MultiEd25519PublicKey.MAX_KEYS}"
      }
      return fromPrivateKeys(
        privateKeys = List(numberOfKeys) { Ed25519PrivateKey.generate() },
        signaturesRequired = signaturesRequired,
        address = address,
      )
    }
  }
}
