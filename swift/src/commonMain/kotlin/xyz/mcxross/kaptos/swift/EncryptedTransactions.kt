/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions
import xyz.mcxross.kaptos.encrypted.EncryptedTransactionService
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.WaitForTransactionOptions

/** Concrete throwing boundary for the optional EncryptedTransactions module. */
class EncryptedTransactions internal constructor(private val service: EncryptedTransactionService) {
  @Throws(Throwable::class)
  suspend fun encrypt(
    transaction: UnsignedTransaction,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
  ): UnsignedTransaction =
    service.encrypt(transaction = transaction, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun signAndSubmit(
    transaction: UnsignedTransaction,
    sender: TransactionSigner,
    secondarySigners: List<TransactionSigner> = emptyList(),
    feePayer: TransactionSigner? = null,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
  ): PendingTransactionResponse =
    service
      .signAndSubmit(
        transaction = transaction,
        sender = sender,
        secondarySigners = secondarySigners,
        feePayer = feePayer,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun signAndSubmit(
    sender: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions? = null,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
  ): PendingTransactionResponse =
    service
      .signAndSubmit(
        sender = sender,
        payload = payload,
        transactionOptions = transactionOptions,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun submitAndWait(
    sender: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions? = null,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
    waitOptions: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): TransactionResponse =
    service
      .submitAndWait(
        sender = sender,
        payload = payload,
        transactionOptions = transactionOptions,
        options = options,
        waitOptions = waitOptions,
      )
      .swiftValue()

  @Throws(Throwable::class) suspend fun clearCache(): Unit = service.clearCache()
}
