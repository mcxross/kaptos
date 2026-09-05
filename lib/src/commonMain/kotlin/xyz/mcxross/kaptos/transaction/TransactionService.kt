/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction

import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.rethrowCancellation
import xyz.mcxross.kaptos.internal.simulateTransaction
import xyz.mcxross.kaptos.internal.submitTransaction
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.internal.waitForTransaction
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.ExternalFeePayerRequest
import xyz.mcxross.kaptos.model.InputSimulateTransactionData
import xyz.mcxross.kaptos.model.InputSubmitTransactionData
import xyz.mcxross.kaptos.model.MoveModuleBytecode
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.SimulationOptions
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.UserTransactionResponse
import xyz.mcxross.kaptos.model.WaitForTransactionOptions
import xyz.mcxross.kaptos.model.map
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.move.MoveArgumentCodec
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.builder.generateRawTransaction
import xyz.mcxross.kaptos.transaction.builder.generateSignedTransaction

/** Kotlin-native transaction operations exposed by [xyz.mcxross.kaptos.Aptos]. */
interface TransactionService {
  /** Resolve an on-chain ABI and encode a typed entry-function payload recursively. */
  suspend fun entryFunctionPayload(
    function: String,
    typeArguments: List<TypeTag> = emptyList(),
    arguments: List<MoveArgument> = emptyList(),
  ): AptosResult<TransactionPayload.EntryFunction>

  /** Preload module ABIs for cached or fully offline entry-function argument encoding. */
  suspend fun preloadModuleAbis(vararg modules: MoveModuleBytecode): AptosResult<Unit>

  /** Builds a single-sender transaction without signing it. */
  suspend fun build(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    options: TransactionOptions? = null,
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a transaction whose secondary signer order is preserved in the signing message. */
  suspend fun buildMultiAgent(
    sender: AccountAddressInput,
    secondarySigners: List<AccountAddressInput>,
    payload: TransactionPayload,
    options: TransactionOptions? = null,
  ): AptosResult<UnsignedTransaction.MultiAgent>

  /**
   * Builds a sponsored transaction. Omitting [feePayer] uses the zero-address placeholder so an
   * external sponsor can be selected after construction.
   */
  suspend fun buildFeePayer(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    secondarySigners: List<AccountAddressInput> = emptyList(),
    feePayer: AccountAddressInput? = null,
    options: TransactionOptions? = null,
  ): AptosResult<UnsignedTransaction.FeePayer>

  /** Produces the sender authenticator without submitting the transaction. */
  suspend fun sign(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
  ): AptosResult<AccountAuthenticator>

  /** Simulates any non-encrypted transaction with optional gas-estimation flags. */
  suspend fun simulate(
    transaction: UnsignedTransaction,
    senderPublicKey: PublicKey,
    secondarySignerPublicKeys: List<PublicKey> = emptyList(),
    feePayerPublicKey: PublicKey? = null,
    options: SimulationOptions = SimulationOptions(),
  ): AptosResult<List<UserTransactionResponse>>

  /** Combines externally produced authenticators and submits the signed BCS transaction. */
  suspend fun submit(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): AptosResult<PendingTransactionResponse>

  /**
   * Computes the canonical on-chain hash of an already authenticated user transaction without
   * submitting it. Applications can persist this hash before the network write and reconcile the
   * exact signed transaction after interruption.
   */
  fun userTransactionHash(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): AptosResult<String> =
    computeUserTransactionHash(
      transaction,
      senderAuthenticator,
      secondaryAuthenticators,
      feePayerAuthenticator,
    )

  /**
   * Serializes a placeholder fee-payer transaction and the sender authenticators for an external
   * Aptos Gas Station. The returned [ExternalFeePayerRequest.fingerprint] can be journaled before
   * the network write, but is deliberately distinct from the final transaction hash because the
   * sponsor's address and authenticator are not yet available.
   */
  fun externalFeePayerRequest(
    transaction: UnsignedTransaction.FeePayer,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
  ): AptosResult<ExternalFeePayerRequest> =
    createExternalFeePayerRequest(transaction, senderAuthenticator, secondaryAuthenticators)

  /** Signs as the sender and submits, retaining explicit secondary and sponsor authenticators. */
  suspend fun signAndSubmit(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
  ): AptosResult<PendingTransactionResponse>

  /**
   * Builds, signs, and submits a payload with client defaults. Secondary signers select a
   * multi-agent transaction; supplying [feePayer] selects a sponsored transaction.
   */
  suspend fun signAndSubmit(
    signer: TransactionSigner,
    payload: TransactionPayload,
    options: TransactionOptions? = null,
    secondarySigners: List<TransactionSigner> = emptyList(),
    feePayer: TransactionSigner? = null,
  ): AptosResult<PendingTransactionResponse> {
    return submitPayload(signer, payload, options, secondarySigners, feePayer)
  }

  /** Signs, submits, and waits for an already-built transaction. */
  suspend fun submitAndWait(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
    secondaryAuthenticators: List<AccountAuthenticator> = emptyList(),
    feePayerAuthenticator: AccountAuthenticator? = null,
    waitOptions: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): AptosResult<TransactionResponse> =
    when (
      val pending =
        signAndSubmit(
          signer,
          transaction,
          secondaryAuthenticators,
          feePayerAuthenticator,
        )
    ) {
      is AptosResult.Failure -> pending
      is AptosResult.Success -> waitForTransaction(pending.value, waitOptions)
    }

  /** Builds, signs, submits, and waits for a payload, including sponsored and multi-agent flows. */
  suspend fun submitAndWait(
    signer: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions? = null,
    secondarySigners: List<TransactionSigner> = emptyList(),
    feePayer: TransactionSigner? = null,
    waitOptions: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): AptosResult<TransactionResponse> =
    when (
      val pending =
        signAndSubmit(
          signer = signer,
          payload = payload,
          options = transactionOptions,
          secondarySigners = secondarySigners,
          feePayer = feePayer,
        )
    ) {
      is AptosResult.Failure -> pending
      is AptosResult.Success -> waitForTransaction(pending.value, waitOptions)
    }

  /** Waits until [hash] commits or the configured wait policy terminates. */
  suspend fun waitForTransaction(
    hash: String,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Waits for a pending response without requiring callers to extract its hash. */
  suspend fun waitForTransaction(
    transaction: PendingTransactionResponse,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): AptosResult<TransactionResponse> = waitForTransaction(transaction.hash, options)
}

internal class DefaultTransactionService(
  private val config: TransportConfig,
  private val defaults: TransactionOptions,
  private val argumentCodec: MoveArgumentCodec = xyz.mcxross.kaptos.internal.moveCodec(config),
) : TransactionService {
  override suspend fun entryFunctionPayload(
    function: String,
    typeArguments: List<TypeTag>,
    arguments: List<MoveArgument>,
  ): AptosResult<TransactionPayload.EntryFunction> =
    argumentCodec.entryFunctionPayload(function, typeArguments, arguments)

  override suspend fun preloadModuleAbis(vararg modules: MoveModuleBytecode): AptosResult<Unit> =
    try {
      argumentCodec.preload(*modules)
      AptosResult.Success(Unit)
    } catch (error: Throwable) {
      error.rethrowCancellation()
      AptosResult.Failure(
        AptosError.Serialization(error.message ?: "Invalid preloaded Move module ABI", error)
      )
    }

  override suspend fun build(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    options: TransactionOptions?,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildRaw(sender, payload, options).map(UnsignedTransaction::Simple)

  override suspend fun buildMultiAgent(
    sender: AccountAddressInput,
    secondarySigners: List<AccountAddressInput>,
    payload: TransactionPayload,
    options: TransactionOptions?,
  ): AptosResult<UnsignedTransaction.MultiAgent> {
    if (secondarySigners.isEmpty()) {
      return AptosResult.Failure(
        AptosError.Validation("A multi-agent transaction requires at least one secondary signer")
      )
    }
    val addresses = parseAddresses(secondarySigners)
    if (addresses is AptosResult.Failure) return addresses
    val senderAddress =
      try {
        AccountAddress.from(sender)
      } catch (error: IllegalArgumentException) {
        return AptosResult.Failure(AptosError.Validation("Invalid sender", error))
      }
    validateSecondarySigners(senderAddress, (addresses as AptosResult.Success).value)?.let {
      return it
    }
    return buildRaw(sender, payload, options).map { raw ->
      UnsignedTransaction.MultiAgent(raw, (addresses as AptosResult.Success).value)
    }
  }

  override suspend fun buildFeePayer(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    secondarySigners: List<AccountAddressInput>,
    feePayer: AccountAddressInput?,
    options: TransactionOptions?,
  ): AptosResult<UnsignedTransaction.FeePayer> {
    val addresses = parseAddresses(secondarySigners)
    if (addresses is AptosResult.Failure) return addresses
    val feePayerAddress =
      try {
        feePayer?.let(AccountAddress::from) ?: UnsignedTransaction.EXTERNAL_FEE_PAYER_PLACEHOLDER
      } catch (error: Throwable) {
        error.rethrowCancellation()
        return AptosResult.Failure(AptosError.Validation("Invalid fee-payer address", error))
      }
    val senderAddress =
      try {
        AccountAddress.from(sender)
      } catch (error: IllegalArgumentException) {
        return AptosResult.Failure(AptosError.Validation("Invalid sender", error))
      }
    validateSecondarySigners(senderAddress, (addresses as AptosResult.Success).value)?.let {
      return it
    }
    return buildRaw(sender, payload, options).map { raw ->
      UnsignedTransaction.FeePayer(
        rawTransaction = raw,
        secondarySignerAddresses = (addresses as AptosResult.Success).value,
        feePayerAddress = feePayerAddress,
      )
    }
  }

  override suspend fun sign(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
  ): AptosResult<AccountAuthenticator> = signer.signTransaction(transaction)

  override suspend fun simulate(
    transaction: UnsignedTransaction,
    senderPublicKey: PublicKey,
    secondarySignerPublicKeys: List<PublicKey>,
    feePayerPublicKey: PublicKey?,
    options: SimulationOptions,
  ): AptosResult<List<UserTransactionResponse>> {
    if (transaction.rawTransaction.payload is TransactionPayload.Encrypted) {
      return AptosResult.Failure(
        AptosError.UnsupportedFeature("Encrypted transactions cannot be simulated")
      )
    }
    validateSimulationSigners(transaction, secondarySignerPublicKeys, feePayerPublicKey)?.let {
      return it
    }
    return executeAptos {
      simulateTransaction(
          config,
          InputSimulateTransactionData(
            signerPublicKey = senderPublicKey,
            transaction = transaction,
            secondarySignerPublicKeys = secondarySignerPublicKeys,
            feePayerPublicKey = feePayerPublicKey,
            options = options,
          ),
        )
        .toAptosResult()
    }
  }

  override suspend fun submit(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator>,
    feePayerAuthenticator: AccountAuthenticator?,
  ): AptosResult<PendingTransactionResponse> {
    validateAuthenticators(transaction, secondaryAuthenticators, feePayerAuthenticator)?.let {
      return it
    }
    return executeAptos {
      submitTransaction(
          config,
          InputSubmitTransactionData(
            transaction = transaction,
            senderAuthenticator = senderAuthenticator,
            feePayerAuthenticator = feePayerAuthenticator,
            additionalSignersAuthenticators = secondaryAuthenticators,
          ),
        )
        .toAptosResult()
    }
  }

  override fun userTransactionHash(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator>,
    feePayerAuthenticator: AccountAuthenticator?,
  ): AptosResult<String> =
    computeUserTransactionHash(
      transaction,
      senderAuthenticator,
      secondaryAuthenticators,
      feePayerAuthenticator,
    )

  override fun externalFeePayerRequest(
    transaction: UnsignedTransaction.FeePayer,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator>,
  ): AptosResult<ExternalFeePayerRequest> =
    createExternalFeePayerRequest(transaction, senderAuthenticator, secondaryAuthenticators)

  override suspend fun signAndSubmit(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
    secondaryAuthenticators: List<AccountAuthenticator>,
    feePayerAuthenticator: AccountAuthenticator?,
  ): AptosResult<PendingTransactionResponse> {
    if (signer.accountAddress != transaction.rawTransaction.sender)
      return AptosResult.Failure(
        AptosError.Validation("Signing account does not match transaction sender")
      )
    validateAuthenticators(transaction, secondaryAuthenticators, feePayerAuthenticator)?.let {
      return it
    }
    return when (val authenticator = sign(signer, transaction)) {
      is AptosResult.Failure -> authenticator
      is AptosResult.Success ->
        submit(
          transaction,
          authenticator.value,
          secondaryAuthenticators,
          feePayerAuthenticator,
        )
    }
  }

  override suspend fun waitForTransaction(
    hash: String,
    options: WaitForTransactionOptions,
  ): AptosResult<TransactionResponse> = executeAptos {
    waitForTransaction(config, hash, options).toAptosResult()
  }

  private suspend fun buildRaw(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    options: TransactionOptions?,
  ): AptosResult<xyz.mcxross.kaptos.transaction.instances.RawTransaction> = executeAptos {
    AptosResult.Success(generateRawTransaction(config, sender, payload, effectiveOptions(options)))
  }

  private fun effectiveOptions(options: TransactionOptions?): TransactionOptions {
    if (options == null) return defaults
    return TransactionOptions(
      maxGasAmount = options.maxGasAmount ?: defaults.maxGasAmount,
      gasUnitPrice = options.gasUnitPrice,
      expirationTimestampSecs = options.expirationTimestampSecs,
      expirationSecondsFromNow =
        when {
          options.expirationTimestampSecs != null -> null
          options.expirationSecondsFromNow != null -> options.expirationSecondsFromNow
          else -> defaults.expirationSecondsFromNow
        },
      replayProtection = options.replayProtection,
    )
  }

  private fun parseAddresses(
    addresses: List<AccountAddressInput>
  ): AptosResult<List<AccountAddress>> =
    try {
      AptosResult.Success(addresses.map(AccountAddress::from))
    } catch (error: Throwable) {
      error.rethrowCancellation()
      AptosResult.Failure(AptosError.Validation("Invalid secondary signer address", error))
    }
}

internal fun computeUserTransactionHash(
  transaction: UnsignedTransaction,
  senderAuthenticator: AccountAuthenticator,
  secondaryAuthenticators: List<AccountAuthenticator>,
  feePayerAuthenticator: AccountAuthenticator?,
): AptosResult<String> {
  validateAuthenticators(transaction, secondaryAuthenticators, feePayerAuthenticator)?.let {
    return it
  }
  return try {
    val signedTransaction =
      generateSignedTransaction(
        InputSubmitTransactionData(
          transaction = transaction,
          senderAuthenticator = senderAuthenticator,
          feePayerAuthenticator = feePayerAuthenticator,
          additionalSignersAuthenticators = secondaryAuthenticators,
        )
      )
    val transactionPrefix = sha3Hash("APTOS::Transaction".encodeToByteArray())
    AptosResult.Success(
      Hex(sha3Hash(transactionPrefix + byteArrayOf(0) + signedTransaction)).toString()
    )
  } catch (error: Throwable) {
    error.rethrowCancellation()
    AptosResult.Failure(
      AptosError.Serialization(error.message ?: "Unable to hash signed transaction", error)
    )
  }
}

internal fun createExternalFeePayerRequest(
  transaction: UnsignedTransaction.FeePayer,
  senderAuthenticator: AccountAuthenticator,
  secondaryAuthenticators: List<AccountAuthenticator>,
): AptosResult<ExternalFeePayerRequest> {
  validateParticipants(transaction)?.let {
    return it
  }
  if (transaction.feePayerAddress != UnsignedTransaction.EXTERNAL_FEE_PAYER_PLACEHOLDER) {
    return AptosResult.Failure(
      AptosError.Validation(
        "An external Gas Station request must use the zero-address fee-payer placeholder"
      )
    )
  }
  if (secondaryAuthenticators.size != transaction.secondarySignerAddresses.size) {
    return AptosResult.Failure(
      AptosError.Validation(
        "Expected ${transaction.secondarySignerAddresses.size} secondary authenticators, " +
          "got ${secondaryAuthenticators.size}"
      )
    )
  }
  return try {
    val transactionBytes = transaction.signingBcs()
    val senderBytes = senderAuthenticator.toBcs()
    val secondaryBytes = secondaryAuthenticators.map(AccountAuthenticator::toBcs)
    val correlationBytes =
      AptosBcsWriter()
        .also { writer ->
          writer.bytes(transactionBytes)
          writer.bytes(senderBytes)
          writer.vector(secondaryBytes) { bytes(it) }
        }
        .toByteArray()
    val prefix = sha3Hash("APTOS::ExternalFeePayerRequest".encodeToByteArray())
    AptosResult.Success(
      ExternalFeePayerRequest(
        transactionBytes = transactionBytes,
        senderAuthenticatorBytes = senderBytes,
        additionalSignersAuthenticatorBytes = secondaryBytes,
        fingerprint = Hex(sha3Hash(prefix + correlationBytes)).toString(),
      )
    )
  } catch (error: Throwable) {
    error.rethrowCancellation()
    AptosResult.Failure(
      AptosError.Serialization(error.message ?: "Unable to serialize Gas Station request", error)
    )
  }
}
