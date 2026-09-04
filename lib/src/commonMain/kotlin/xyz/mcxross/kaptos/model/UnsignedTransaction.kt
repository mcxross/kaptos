/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.util.RAW_TRANSACTION_SALT
import xyz.mcxross.kaptos.util.RAW_TRANSACTION_WITH_DATA_SALT

/** A transaction before account authenticators are attached. */
sealed interface UnsignedTransaction {
  val rawTransaction: RawTransaction

  data class Simple(override val rawTransaction: RawTransaction) : UnsignedTransaction

  data class MultiAgent(
    override val rawTransaction: RawTransaction,
    val secondarySignerAddresses: List<AccountAddress>,
  ) : UnsignedTransaction

  data class FeePayer(
    override val rawTransaction: RawTransaction,
    val secondarySignerAddresses: List<AccountAddress> = emptyList(),
    val feePayerAddress: AccountAddress = EXTERNAL_FEE_PAYER_PLACEHOLDER,
  ) : UnsignedTransaction

  /** Exact bytes covered by the transaction signature, before domain separation. */
  fun signingBcs(): ByteArray =
    when (this) {
      is Simple -> rawTransaction.toBcs()
      is MultiAgent ->
        AptosBcsWriter()
          .also { writer ->
            writer.uleb128(0u)
            rawTransaction.encode(writer)
            writer.vector(secondarySignerAddresses) { accountAddress(it) }
          }
          .toByteArray()
      is FeePayer ->
        AptosBcsWriter()
          .also { writer ->
            writer.uleb128(1u)
            rawTransaction.encode(writer)
            writer.vector(secondarySignerAddresses) { accountAddress(it) }
            writer.accountAddress(feePayerAddress)
          }
          .toByteArray()
    }

  fun signingMessage(): ByteArray {
    val salt = if (this is Simple) RAW_TRANSACTION_SALT else RAW_TRANSACTION_WITH_DATA_SALT
    return sha3Hash(salt.encodeToByteArray()) + signingBcs()
  }

  companion object {
    /** Used while an external sponsor is expected to provide its address and authenticator later. */
    val EXTERNAL_FEE_PAYER_PLACEHOLDER: AccountAddress = AccountAddress.ZERO
  }
}

/** Selects conventional sequence-number or AIP-122 nonce replay protection. */
sealed interface ReplayProtection {
  data class SequenceNumber(val value: ULong) : ReplayProtection

  data class Nonce(val value: ULong) : ReplayProtection
}

/** The immutable transaction and authenticator produced when a sponsor signs. */
data class FeePayerSignature(
  val transaction: UnsignedTransaction.FeePayer,
  val authenticator: AccountAuthenticator,
)

/**
 * Opaque BCS values accepted by an external Aptos Gas Station.
 *
 * [transactionBytes] is the `RawTransactionWithData::FeePayer` value signed by the sender, not a
 * partially assembled `SignedTransaction`. The external sponsor supplies its address and
 * authenticator before submitting the final transaction.
 */
data class ExternalFeePayerRequest(
  val transactionBytes: ByteArray,
  val senderAuthenticatorBytes: ByteArray,
  val additionalSignersAuthenticatorBytes: List<ByteArray> = emptyList(),
  /** Stable client-side correlation value. This is not an on-chain transaction hash. */
  val fingerprint: String,
)
