/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.ExternalFeePayerRequest
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.SimulationOptions
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.UserTransactionResponse
import xyz.mcxross.kaptos.model.WaitForTransactionOptions
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.instances.RawTransaction

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Transactions internal constructor(private val service: TransactionService) {
  /**
   * Imports canonical RawTransaction BCS, preserving the original network and expiration fields.
   */
  @Throws(Throwable::class)
  fun fromBcs(bytes: ByteArray): UnsignedTransaction.Simple =
    UnsignedTransaction.Simple(RawTransaction.fromBcs(bytes))

  @Throws(Throwable::class)
  suspend fun entryFunctionPayload(
    function: String,
    typeArguments: List<TypeTag> = emptyList(),
    arguments: List<MoveArgument> = emptyList(),
  ): TransactionPayload.EntryFunction =
    service
      .entryFunctionPayload(
        function = function,
        typeArguments = typeArguments,
        arguments = arguments,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun build(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    options: TransactionOptions? = null,
  ): UnsignedTransaction.Simple =
    service.build(sender = sender, payload = payload, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun buildMultiAgent(
    sender: AccountAddressInput,
    secondarySigners: List<AccountAddressInput>,
    payload: TransactionPayload,
    options: TransactionOptions? = null,
  ): UnsignedTransaction.MultiAgent =
    service
      .buildMultiAgent(
        sender = sender,
        secondarySigners = secondarySigners,
        payload = payload,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildFeePayer(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    secondarySigners: List<AccountAddressInput> = emptyList(),
    feePayer: AccountAddressInput? = null,
    options: TransactionOptions? = null,
  ): UnsignedTransaction.FeePayer =
    service
      .buildFeePayer(
        sender = sender,
        payload = payload,
        secondarySigners = secondarySigners,
        feePayer = feePayer,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun sign(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
  ): AccountAuthenticator = service.sign(signer = signer, transaction = transaction).swiftValue()

  @Throws(Throwable::class)
  suspend fun simulate(
    transaction: UnsignedTransaction,
    senderPublicKey: PublicKey,
    secondarySignerPublicKeys: List<PublicKey> = emptyList(),
    feePayerPublicKey: PublicKey? = null,
    options: SimulationOptions = SimulationOptions(),
  ): List<UserTransactionResponse> =
    service
      .simulate(
        transaction = transaction,
        senderPublicKey = senderPublicKey,
        secondarySignerPublicKeys = secondarySignerPublicKeys,
        feePayerPublicKey = feePayerPublicKey,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun submit(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): PendingTransactionResponse =
    service
      .submit(
        transaction = transaction,
        senderAuthenticator = senderAuthenticator,
        secondaryAuthenticators = secondaryAuthenticators,
        feePayerAuthenticator = feePayerAuthenticator,
      )
      .swiftValue()

  @Throws(Throwable::class)
  fun userTransactionHash(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): String =
    service
      .userTransactionHash(
        transaction = transaction,
        senderAuthenticator = senderAuthenticator,
        secondaryAuthenticators = secondaryAuthenticators,
        feePayerAuthenticator = feePayerAuthenticator,
      )
      .swiftValue()

  @Throws(Throwable::class)
  fun externalFeePayerRequest(
    transaction: UnsignedTransaction.FeePayer,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
  ): ExternalFeePayerRequest =
    service
      .externalFeePayerRequest(
        transaction = transaction,
        senderAuthenticator = senderAuthenticator,
        secondaryAuthenticators = secondaryAuthenticators,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun signAndSubmit(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): PendingTransactionResponse =
    service
      .signAndSubmit(
        signer = signer,
        transaction = transaction,
        secondaryAuthenticators = secondaryAuthenticators,
        feePayerAuthenticator = feePayerAuthenticator,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun signAndSubmit(
    signer: TransactionSigner,
    payload: TransactionPayload,
    options: TransactionOptions? = null,
    secondarySigners: List<TransactionSigner> = emptyList(),
    feePayer: TransactionSigner? = null,
  ): PendingTransactionResponse =
    service
      .signAndSubmit(
        signer = signer,
        payload = payload,
        options = options,
        secondarySigners = secondarySigners,
        feePayer = feePayer,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun submitAndWait(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
    waitOptions: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): TransactionResponse =
    service
      .submitAndWait(
        signer = signer,
        transaction = transaction,
        secondaryAuthenticators = secondaryAuthenticators,
        feePayerAuthenticator = feePayerAuthenticator,
        waitOptions = waitOptions,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun submitAndWait(
    signer: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions? = null,
    secondarySigners: List<TransactionSigner> = emptyList(),
    feePayer: TransactionSigner? = null,
    waitOptions: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): TransactionResponse =
    service
      .submitAndWait(
        signer = signer,
        payload = payload,
        transactionOptions = transactionOptions,
        secondarySigners = secondarySigners,
        feePayer = feePayer,
        waitOptions = waitOptions,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun waitForTransaction(
    hash: String,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): TransactionResponse = service.waitForTransaction(hash = hash, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun waitForTransaction(
    transaction: PendingTransactionResponse,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): TransactionResponse =
    service.waitForTransaction(transaction = transaction, options = options).swiftValue()
}
