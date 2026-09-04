/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction.authenticator

import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey
import xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

/** Transaction-level authenticator with Aptos' exact on-wire variants. */
sealed interface TransactionAuthenticator {
  data class Ed25519(
    val publicKey: Ed25519PublicKey,
    val signature: Ed25519Signature,
  ) : TransactionAuthenticator

  data class MultiEd25519(
    val publicKey: MultiEd25519PublicKey,
    val signature: MultiEd25519Signature,
  ) : TransactionAuthenticator

  data class SingleSender(val sender: AccountAuthenticator) : TransactionAuthenticator

  data class MultiAgent(
    val sender: AccountAuthenticator,
    val secondarySignerAddresses: List<AccountAddress>,
    val secondarySigners: List<AccountAuthenticator>,
  ) : TransactionAuthenticator {
    init {
      require(secondarySignerAddresses.size == secondarySigners.size) {
        "Each secondary signer address must have one authenticator"
      }
    }
  }

  data class FeePayer(
    val sender: AccountAuthenticator,
    val secondarySignerAddresses: List<AccountAddress>,
    val secondarySigners: List<AccountAuthenticator>,
    val feePayerAddress: AccountAddress,
    val feePayer: AccountAuthenticator,
  ) : TransactionAuthenticator {
    init {
      require(secondarySignerAddresses.size == secondarySigners.size) {
        "Each secondary signer address must have one authenticator"
      }
    }
  }

  fun toBcs(): ByteArray = AptosBcsWriter().also { encode(it) }.toByteArray()

  companion object {
    /** Uses legacy top-level variants where Aptos requires them, and SingleSender otherwise. */
    fun singleSender(sender: AccountAuthenticator): TransactionAuthenticator =
      when (sender) {
        is AccountAuthenticator.Ed25519 -> Ed25519(sender.publicKey, sender.signature)
        is AccountAuthenticator.MultiEd25519 -> MultiEd25519(sender.publicKey, sender.signature)
        else -> SingleSender(sender)
      }

    fun fromBcs(bytes: ByteArray): TransactionAuthenticator =
      AptosBcsReader(bytes).let { reader ->
        reader.transactionAuthenticator().also { reader.ensureFinished() }
      }
  }
}

internal fun AptosBcsReader.transactionAuthenticator(): TransactionAuthenticator =
  when (val variant = uleb128()) {
    0u ->
      TransactionAuthenticator.Ed25519(
        publicKey = Ed25519PublicKey(bytes()),
        signature = Ed25519Signature(bytes()),
      )
    1u -> {
      val account = multiEd25519AccountAuthenticator()
      TransactionAuthenticator.MultiEd25519(account.publicKey, account.signature)
    }
    2u -> {
      val sender = accountAuthenticator()
      val addresses = vector { accountAddress() }
      val signers = vector { accountAuthenticator() }
      TransactionAuthenticator.MultiAgent(sender, addresses, signers)
    }
    3u -> {
      val sender = accountAuthenticator()
      val addresses = vector { accountAddress() }
      val signers = vector { accountAuthenticator() }
      TransactionAuthenticator.FeePayer(
        sender = sender,
        secondarySignerAddresses = addresses,
        secondarySigners = signers,
        feePayerAddress = accountAddress(),
        feePayer = accountAuthenticator(allowTrailingAbstractionSignature = true),
      )
    }
    4u ->
      TransactionAuthenticator.SingleSender(
        accountAuthenticator(allowTrailingAbstractionSignature = true)
      )
    else -> throw IllegalArgumentException("Unsupported TransactionAuthenticator variant: $variant")
  }

private fun TransactionAuthenticator.encode(writer: AptosBcsWriter) {
  when (this) {
    is TransactionAuthenticator.Ed25519 -> {
      writer.uleb128(0u)
      writer.fixed(publicKey.toBcs())
      writer.fixed(signature.toBcs())
    }
    is TransactionAuthenticator.MultiEd25519 -> {
      writer.uleb128(1u)
      writer.fixed(publicKey.toBcs())
      writer.fixed(signature.toBcs())
    }
    is TransactionAuthenticator.MultiAgent -> {
      writer.uleb128(2u)
      sender.encode(writer)
      writer.vector(secondarySignerAddresses) { accountAddress(it) }
      writer.vector(secondarySigners) { it.encode(this) }
    }
    is TransactionAuthenticator.FeePayer -> {
      writer.uleb128(3u)
      sender.encode(writer)
      writer.vector(secondarySignerAddresses) { accountAddress(it) }
      writer.vector(secondarySigners) { it.encode(this) }
      writer.accountAddress(feePayerAddress)
      feePayer.encode(writer)
    }
    is TransactionAuthenticator.SingleSender -> {
      writer.uleb128(4u)
      sender.encode(writer)
    }
  }
}
