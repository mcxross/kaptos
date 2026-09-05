/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/** Move package configuration for confidential-asset operations. */
data class ConfidentialAssetConfig(
  val moduleAddress: AccountAddress = AccountAddress.fromString("0x1")
)

/** Fee-payer and gas settings shared by confidential transaction builders. */
data class ConfidentialTransactionOptions(
  val transaction: TransactionOptions = TransactionOptions(),
  val feePayer: TransactionSigner? = null,
  val externalFeePayerAddress: AccountAddressInput? = null,
) {
  init {
    require(feePayer == null || externalFeePayerAddress == null) {
      "Choose either a local fee payer or an external fee-payer address"
    }
  }
}

/** High-level confidential-balance reads, builders, and committed mutations. */
interface ConfidentialAssetService {
  /** Decrypts the account's available and pending confidential balance. */
  suspend fun getBalance(
    account: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    useCache: Boolean = false,
  ): AptosResult<ConfidentialBalance>

  /** Returns the account's registered confidential encryption key. */
  suspend fun getEncryptionKey(
    account: AccountAddressInput,
    token: AccountAddressInput,
    useCache: Boolean = true,
  ): AptosResult<ConfidentialEncryptionKey>

  /** Returns the asset-wide auditor key when global auditing is configured. */
  suspend fun getAssetAuditorEncryptionKey(
    token: AccountAddressInput,
    useCache: Boolean = true,
  ): AptosResult<ConfidentialEncryptionKey?>

  /** Returns the effective global or account-specific auditor epoch. */
  suspend fun getEffectiveAuditorHint(
    account: AccountAddressInput,
    token: AccountAddressInput,
  ): AptosResult<EffectiveAuditorHint?>

  /** Returns registration and incoming-transfer pause state for an account and asset. */
  suspend fun getStatus(
    account: AccountAddressInput,
    token: AccountAddressInput,
  ): AptosResult<ConfidentialAssetStatus>

  /** Reports whether the confidential-asset module is emergency paused. */
  suspend fun isEmergencyPaused(): AptosResult<Boolean>

  /** Returns a typed page of indexed confidential-asset activity. */
  suspend fun getActivities(
    query: ConfidentialActivityQuery = ConfidentialActivityQuery()
  ): AptosResult<AptosPage<ConfidentialAssetActivity>>

  /** Builds a confidential-balance registration transaction. */
  suspend fun buildRegisterBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a public-to-confidential deposit transaction. */
  suspend fun buildDeposit(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    amount: ULong,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a confidential-to-public withdrawal transaction and its proofs. */
  suspend fun buildWithdraw(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    recipient: AccountAddressInput = sender,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a confidential transfer with optional voluntary auditor keys and memo. */
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    voluntaryAuditors: List<ConfidentialEncryptionKey> = emptyList(),
    memo: ByteArray = byteArrayOf(),
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a transaction that compacts the available balance ciphertext. */
  suspend fun buildNormalizeBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a transaction that rolls the pending balance into the available balance. */
  suspend fun buildRolloverPendingBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    pauseIncoming: Boolean = false,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a transaction that pauses or resumes incoming confidential transfers. */
  suspend fun buildSetIncomingTransfersPaused(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    paused: Boolean,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Builds a proof-backed confidential encryption-key rotation transaction. */
  suspend fun buildRotateEncryptionKey(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    unpauseIncoming: Boolean = true,
    requireEmptyPendingBalance: Boolean = true,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Registers a confidential balance, waits for commitment, and invalidates affected caches. */
  suspend fun registerBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Deposits public funds, waits for commitment, and invalidates affected caches. */
  suspend fun deposit(
    signer: TransactionSigner,
    token: AccountAddressInput,
    amount: ULong,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Withdraws confidential funds and waits for the transaction to commit. */
  suspend fun withdraw(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    recipient: AccountAddressInput = signer.accountAddress,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Transfers confidential funds and waits for the transaction to commit. */
  suspend fun transfer(
    signer: TransactionSigner,
    recipient: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    voluntaryAuditors: List<ConfidentialEncryptionKey> = emptyList(),
    memo: ByteArray = byteArrayOf(),
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Normalizes the available balance and waits for commitment. */
  suspend fun normalizeBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Rolls over the pending balance and waits for commitment. */
  suspend fun rolloverPendingBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    pauseIncoming: Boolean = false,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Changes incoming-transfer pause state and waits for commitment. */
  suspend fun setIncomingTransfersPaused(
    signer: TransactionSigner,
    token: AccountAddressInput,
    paused: Boolean,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Rotates the encryption key and waits for commitment before invalidating key caches. */
  suspend fun rotateEncryptionKey(
    signer: TransactionSigner,
    token: AccountAddressInput,
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    unpauseIncoming: Boolean = true,
    requireEmptyPendingBalance: Boolean = true,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Clears all decrypted balance and encryption-key caches. */
  suspend fun clearCache()
}

/** Bind confidential-asset operations to this client's transport and transaction services. */
fun Aptos.confidentialAssets(
  config: ConfidentialAssetConfig = ConfidentialAssetConfig()
): ConfidentialAssetService = DefaultConfidentialAssetService(this, config)

internal class DefaultConfidentialAssetService(
  private val client: Aptos,
  config: ConfidentialAssetConfig,
) : ConfidentialAssetService {
  private val dataSource = ConfidentialAssetDataSource(client, config.moduleAddress)
  private val module = "${config.moduleAddress}::confidential_asset"
  private val cacheMutex = Mutex()
  private val balances = mutableMapOf<String, ConfidentialBalance>()
  private val encryptionKeys = mutableMapOf<String, ConfidentialEncryptionKey>()
  private val auditorKeys = mutableMapOf<String, ConfidentialEncryptionKey?>()

  override suspend fun getBalance(
    account: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    useCache: Boolean,
  ): AptosResult<ConfidentialBalance> {
    val address = account.addressOrFailure() ?: return invalidAddress("account")
    val asset = token.addressOrFailure() ?: return invalidAddress("token")
    val cacheKey = key(address, asset)
    if (useCache)
      cacheMutex
        .withLock { balances[cacheKey] }
        ?.let {
          return AptosResult.Success(it)
        }
    val result = dataSource.balance(address, asset, key)
    if (result is AptosResult.Success) cacheMutex.withLock { balances[cacheKey] = result.value }
    return result
  }

  override suspend fun getEncryptionKey(
    account: AccountAddressInput,
    token: AccountAddressInput,
    useCache: Boolean,
  ): AptosResult<ConfidentialEncryptionKey> {
    val address = account.addressOrFailure() ?: return invalidAddress("account")
    val asset = token.addressOrFailure() ?: return invalidAddress("token")
    val cacheKey = key(address, asset)
    if (useCache)
      cacheMutex
        .withLock { encryptionKeys[cacheKey] }
        ?.let {
          return AptosResult.Success(it)
        }
    val result = dataSource.encryptionKey(address, asset)
    if (result is AptosResult.Success)
      cacheMutex.withLock { encryptionKeys[cacheKey] = result.value }
    return result
  }

  override suspend fun getAssetAuditorEncryptionKey(
    token: AccountAddressInput,
    useCache: Boolean,
  ): AptosResult<ConfidentialEncryptionKey?> {
    val asset = token.addressOrFailure() ?: return invalidAddress("token")
    val cacheKey = asset.toString()
    if (useCache)
      cacheMutex
        .withLock {
          if (auditorKeys.containsKey(cacheKey)) AptosResult.Success(auditorKeys[cacheKey])
          else null
        }
        ?.let {
          return it
        }
    val result = dataSource.assetAuditorKey(asset)
    if (result is AptosResult.Success) cacheMutex.withLock { auditorKeys[cacheKey] = result.value }
    return result
  }

  override suspend fun getEffectiveAuditorHint(
    account: AccountAddressInput,
    token: AccountAddressInput,
  ): AptosResult<EffectiveAuditorHint?> {
    val address = account.addressOrFailure() ?: return invalidAddress("account")
    val asset = token.addressOrFailure() ?: return invalidAddress("token")
    return dataSource.effectiveAuditorHint(address, asset)
  }

  override suspend fun getStatus(
    account: AccountAddressInput,
    token: AccountAddressInput,
  ): AptosResult<ConfidentialAssetStatus> {
    val address = account.addressOrFailure() ?: return invalidAddress("account")
    val asset = token.addressOrFailure() ?: return invalidAddress("token")
    return dataSource.status(address, asset)
  }

  override suspend fun isEmergencyPaused(): AptosResult<Boolean> = dataSource.emergencyPaused()

  override suspend fun getActivities(
    query: ConfidentialActivityQuery
  ): AptosResult<AptosPage<ConfidentialAssetActivity>> = client.confidentialActivities(query)

  override suspend fun buildRegisterBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    val chainId = chainId()
    if (chainId is AptosResult.Failure) return chainId
    val authorization =
      ConfidentialProofFactory.registration(
        key,
        senderAddress,
        tokenAddress,
        (chainId as AptosResult.Success).value,
      )
    if (authorization is AptosResult.Failure) return authorization
    authorization as AptosResult.Success
    return build(
      senderAddress,
      payload(
        "register_raw",
        address(tokenAddress),
        bytes(authorization.value.publicKey.toByteArray()),
        pointVector(authorization.value.sigma.commitment),
        pointVector(authorization.value.sigma.response),
      ),
      options,
    )
  }

  override suspend fun buildDeposit(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    amount: ULong,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    if (amount == 0uL) {
      return AptosResult.Failure(AptosError.Validation("Deposit amount must be positive"))
    }
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    return build(
      senderAddress,
      payload("deposit", address(tokenAddress), MoveArgument.U64(amount)),
      options,
    )
  }

  override suspend fun buildWithdraw(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    recipient: AccountAddressInput,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    if (amount == 0uL) {
      return AptosResult.Failure(
        AptosError.Validation("Use normalizeBalance for a zero-amount balance update")
      )
    }
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    val recipientAddress = recipient.addressOrFailure() ?: return invalidAddress("recipient")
    val inputs = proofInputs(senderAddress, tokenAddress, key)
    if (inputs is AptosResult.Failure) return inputs
    inputs as AptosResult.Success
    val authorization =
      ConfidentialProofFactory.withdraw(
        key,
        senderAddress,
        tokenAddress,
        inputs.value.chainId,
        amount,
        inputs.value.balance,
        inputs.value.auditor,
      )
    if (authorization is AptosResult.Failure) return authorization
    authorization as AptosResult.Success
    return build(
      senderAddress,
      payload(
        "withdraw_to_raw",
        address(tokenAddress),
        address(recipientAddress),
        MoveArgument.U64(amount),
        commitments(authorization.value.newBalance),
        handles(authorization.value.newBalance),
        pointVector(authorization.value.newAuditorHandles),
        bytes(authorization.value.rangeProof),
        pointVector(authorization.value.sigma.commitment),
        pointVector(authorization.value.sigma.response),
      ),
      options,
    )
  }

  override suspend fun buildTransfer(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    voluntaryAuditors: List<ConfidentialEncryptionKey>,
    memo: ByteArray,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    if (amount == 0uL) {
      return AptosResult.Failure(AptosError.Validation("Transfer amount must be positive"))
    }
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val recipientAddress = recipient.addressOrFailure() ?: return invalidAddress("recipient")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    if (senderAddress == recipientAddress) {
      return AptosResult.Failure(
        AptosError.Validation("A confidential transfer cannot target its sender")
      )
    }
    val memoLimit = dataSource.maxMemoBytes()
    if (memoLimit is AptosResult.Failure) return memoLimit
    if (memo.size > (memoLimit as AptosResult.Success).value) {
      return AptosResult.Failure(
        AptosError.Validation("Memo exceeds the ${memoLimit.value}-byte on-chain limit")
      )
    }
    val recipientStatus = dataSource.status(recipientAddress, tokenAddress)
    if (recipientStatus is AptosResult.Failure) return recipientStatus
    if ((recipientStatus as AptosResult.Success).value.incomingTransfersPaused) {
      return AptosResult.Failure(AptosError.Validation("Recipient incoming transfers are paused"))
    }
    val recipientKey = getEncryptionKey(recipientAddress, tokenAddress)
    if (recipientKey is AptosResult.Failure) return recipientKey
    val inputs = proofInputs(senderAddress, tokenAddress, key)
    if (inputs is AptosResult.Failure) return inputs
    inputs as AptosResult.Success
    val authorization =
      ConfidentialProofFactory.transfer(
        key = key,
        sender = senderAddress,
        recipient = recipientAddress,
        token = tokenAddress,
        chainId = inputs.value.chainId,
        amount = amount,
        balance = inputs.value.balance,
        recipientKey = (recipientKey as AptosResult.Success).value,
        voluntaryAuditors = voluntaryAuditors,
        effectiveAuditor = inputs.value.auditor,
      )
    if (authorization is AptosResult.Failure) return authorization
    authorization as AptosResult.Success
    val value = authorization.value
    return build(
      senderAddress,
      payload(
        "confidential_transfer_raw",
        address(tokenAddress),
        address(recipientAddress),
        commitments(value.newBalance),
        handles(value.newBalance),
        pointVector(value.effectiveAuditorNewBalanceHandles),
        commitments(value.transferBySender),
        handles(value.transferBySender),
        pointVector(value.recipientHandles),
        pointVector(value.effectiveAuditorTransferHandles),
        pointVector(voluntaryAuditors.map(ConfidentialEncryptionKey::toByteArray)),
        nestedPointVector(value.voluntaryAuditorTransferHandles),
        bytes(value.newBalanceRangeProof),
        bytes(value.transferRangeProof),
        pointVector(value.sigma.commitment),
        pointVector(value.sigma.response),
        bytes(memo),
      ),
      options,
    )
  }

  override suspend fun buildNormalizeBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    val inputs = proofInputs(senderAddress, tokenAddress, key)
    if (inputs is AptosResult.Failure) return inputs
    inputs as AptosResult.Success
    val authorization =
      ConfidentialProofFactory.normalize(
        key,
        senderAddress,
        tokenAddress,
        inputs.value.chainId,
        inputs.value.balance,
        inputs.value.auditor,
      )
    if (authorization is AptosResult.Failure) return authorization
    authorization as AptosResult.Success
    return build(
      senderAddress,
      payload(
        "normalize_raw",
        address(tokenAddress),
        commitments(authorization.value.newBalance),
        handles(authorization.value.newBalance),
        pointVector(authorization.value.newAuditorHandles),
        bytes(authorization.value.rangeProof),
        pointVector(authorization.value.sigma.commitment),
        pointVector(authorization.value.sigma.response),
      ),
      options,
    )
  }

  override suspend fun buildRolloverPendingBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    pauseIncoming: Boolean,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    val status = dataSource.status(senderAddress, tokenAddress)
    if (status is AptosResult.Failure) return status
    if (!(status as AptosResult.Success).value.normalized) {
      return AptosResult.Failure(
        AptosError.Validation("Normalize the available balance before rolling over pending funds")
      )
    }
    return build(
      senderAddress,
      payload(
        if (pauseIncoming) "rollover_pending_balance_and_pause" else "rollover_pending_balance",
        address(tokenAddress),
      ),
      options,
    )
  }

  override suspend fun buildSetIncomingTransfersPaused(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    paused: Boolean,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    return build(
      senderAddress,
      payload(
        "set_incoming_transfers_paused",
        address(tokenAddress),
        MoveArgument.Bool(paused),
      ),
      options,
    )
  }

  override suspend fun buildRotateEncryptionKey(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    unpauseIncoming: Boolean,
    requireEmptyPendingBalance: Boolean,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val senderAddress = sender.addressOrFailure() ?: return invalidAddress("sender")
    val tokenAddress = token.addressOrFailure() ?: return invalidAddress("token")
    val balance = getBalance(senderAddress, tokenAddress, currentKey)
    if (balance is AptosResult.Failure) return balance
    balance as AptosResult.Success
    if (requireEmptyPendingBalance && balance.value.pendingAmount.decimal != "0") {
      return AptosResult.Failure(
        AptosError.Validation("Pending confidential balance must be zero before key rotation")
      )
    }
    val status = dataSource.status(senderAddress, tokenAddress)
    if (status is AptosResult.Failure) return status
    if (!(status as AptosResult.Success).value.incomingTransfersPaused) {
      return AptosResult.Failure(
        AptosError.Validation("Pause incoming confidential transfers before rotating the key")
      )
    }
    val chainId = chainId()
    if (chainId is AptosResult.Failure) return chainId
    val authorization =
      ConfidentialProofFactory.rotate(
        currentKey,
        newKey,
        senderAddress,
        tokenAddress,
        (chainId as AptosResult.Success).value,
        balance.value,
      )
    if (authorization is AptosResult.Failure) return authorization
    authorization as AptosResult.Success
    return build(
      senderAddress,
      payload(
        "rotate_encryption_key_raw",
        address(tokenAddress),
        bytes(authorization.value.newPublicKey.toByteArray()),
        MoveArgument.Bool(unpauseIncoming),
        pointVector(authorization.value.newHandles),
        pointVector(authorization.value.sigma.commitment),
        pointVector(authorization.value.sigma.response),
      ),
      options,
    )
  }

  override suspend fun registerBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildRegisterBalance(signer.accountAddress, token, key, options)
    }

  override suspend fun deposit(
    signer: TransactionSigner,
    token: AccountAddressInput,
    amount: ULong,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildDeposit(signer.accountAddress, token, amount, options)
    }

  override suspend fun withdraw(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    recipient: AccountAddressInput,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildWithdraw(signer.accountAddress, token, key, amount, recipient, options)
    }

  override suspend fun transfer(
    signer: TransactionSigner,
    recipient: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    voluntaryAuditors: List<ConfidentialEncryptionKey>,
    memo: ByteArray,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, recipient, options) {
      buildTransfer(
        signer.accountAddress,
        recipient,
        token,
        key,
        amount,
        voluntaryAuditors,
        memo,
        options,
      )
    }

  override suspend fun normalizeBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildNormalizeBalance(signer.accountAddress, token, key, options)
    }

  override suspend fun rolloverPendingBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    pauseIncoming: Boolean,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildRolloverPendingBalance(signer.accountAddress, token, pauseIncoming, options)
    }

  override suspend fun setIncomingTransfersPaused(
    signer: TransactionSigner,
    token: AccountAddressInput,
    paused: Boolean,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildSetIncomingTransfersPaused(signer.accountAddress, token, paused, options)
    }

  override suspend fun rotateEncryptionKey(
    signer: TransactionSigner,
    token: AccountAddressInput,
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    unpauseIncoming: Boolean,
    requireEmptyPendingBalance: Boolean,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> =
    mutate(signer, token, null, options) {
      buildRotateEncryptionKey(
        signer.accountAddress,
        token,
        currentKey,
        newKey,
        unpauseIncoming,
        requireEmptyPendingBalance,
        options,
      )
    }

  override suspend fun clearCache() {
    cacheMutex.withLock {
      balances.clear()
      encryptionKeys.clear()
      auditorKeys.clear()
    }
  }

  private suspend fun mutate(
    signer: TransactionSigner,
    token: AccountAddressInput,
    additionalAccount: AccountAddressInput?,
    options: ConfidentialTransactionOptions,
    build: suspend () -> AptosResult<UnsignedTransaction>,
  ): AptosResult<TransactionResponse> {
    val transaction = build()
    if (transaction is AptosResult.Failure) return transaction
    val committed = submit(signer, (transaction as AptosResult.Success).value, options)
    if (committed is AptosResult.Success) {
      invalidate(signer.accountAddress, token)
      additionalAccount?.let { invalidate(it, token) }
    }
    return committed
  }

  private suspend fun submit(
    signer: TransactionSigner,
    transaction: UnsignedTransaction,
    options: ConfidentialTransactionOptions,
  ): AptosResult<TransactionResponse> {
    val senderAuthenticator = signer.signTransaction(transaction)
    if (senderAuthenticator is AptosResult.Failure) return senderAuthenticator
    val feePayerAuthenticator: AccountAuthenticator? =
      if (transaction is UnsignedTransaction.FeePayer) {
        val feePayer =
          options.feePayer
            ?: return AptosResult.Failure(
              AptosError.Validation(
                "Submission requires the local fee-payer signer used to build the transaction"
              )
            )
        when (val signed = feePayer.signTransaction(transaction)) {
          is AptosResult.Failure -> return signed
          is AptosResult.Success -> signed.value
        }
      } else null
    val pending =
      client.transactions.submit(
        transaction = transaction,
        senderAuthenticator = (senderAuthenticator as AptosResult.Success).value,
        feePayerAuthenticator = feePayerAuthenticator,
      )
    if (pending is AptosResult.Failure) return pending
    return client.transactions.waitForTransaction((pending as AptosResult.Success).value.hash)
  }

  private suspend fun build(
    sender: AccountAddress,
    payload: TransactionPayload,
    options: ConfidentialTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val feePayer = options.feePayer?.accountAddress ?: options.externalFeePayerAddress
    return if (feePayer == null) {
      when (val built = client.transactions.build(sender, payload, options.transaction)) {
        is AptosResult.Success -> AptosResult.Success(built.value)
        is AptosResult.Failure -> built
      }
    } else {
      when (
        val built =
          client.transactions.buildFeePayer(
            sender = sender,
            payload = payload,
            feePayer = feePayer,
            options = options.transaction,
          )
      ) {
        is AptosResult.Success -> AptosResult.Success(built.value)
        is AptosResult.Failure -> built
      }
    }
  }

  private suspend fun proofInputs(
    sender: AccountAddress,
    token: AccountAddress,
    key: ConfidentialDecryptionKey,
  ): AptosResult<ProofInputs> {
    val balance = getBalance(sender, token, key)
    if (balance is AptosResult.Failure) return balance
    val auditor = getAssetAuditorEncryptionKey(token)
    if (auditor is AptosResult.Failure) return auditor
    val chainId = chainId()
    if (chainId is AptosResult.Failure) return chainId
    return AptosResult.Success(
      ProofInputs(
        balance = (balance as AptosResult.Success).value,
        auditor = (auditor as AptosResult.Success).value,
        chainId = (chainId as AptosResult.Success).value,
      )
    )
  }

  private suspend fun chainId(): AptosResult<UByte> =
    when (val ledger = client.ledger.info()) {
      is AptosResult.Success -> AptosResult.Success(ledger.value.chainId.chainId)
      is AptosResult.Failure -> ledger
    }

  private suspend fun invalidate(account: AccountAddressInput, token: AccountAddressInput) {
    val address = account.addressOrFailure() ?: return
    val asset = token.addressOrFailure() ?: return
    cacheMutex.withLock {
      balances.remove(key(address, asset))
      encryptionKeys.remove(key(address, asset))
      auditorKeys.remove(asset.toString())
    }
  }

  private fun payload(function: String, vararg arguments: MoveArgument): TransactionPayload =
    TransactionPayload.entryFunction("$module::$function", arguments = arguments.toList())

  private data class ProofInputs(
    val balance: ConfidentialBalance,
    val auditor: ConfidentialEncryptionKey?,
    val chainId: UByte,
  )
}

private fun address(value: AccountAddress): MoveArgument = MoveArgument.Address(value)

private fun bytes(value: ByteArray): MoveArgument = MoveArgument.Bytes(value)

private fun pointVector(values: List<ByteArray>): MoveArgument =
  MoveArgument.Vector(values.map(::bytes))

private fun nestedPointVector(values: List<List<ByteArray>>): MoveArgument =
  MoveArgument.Vector(values.map(::pointVector))

private fun commitments(values: List<ConfidentialCiphertext>): MoveArgument =
  pointVector(values.map(ConfidentialCiphertext::commitment))

private fun handles(values: List<ConfidentialCiphertext>): MoveArgument =
  pointVector(values.map(ConfidentialCiphertext::handle))

private fun AccountAddressInput.addressOrFailure(): AccountAddress? =
  try {
    AccountAddress.from(this)
  } catch (_: Throwable) {
    null
  }

private fun <T> invalidAddress(name: String): AptosResult<T> =
  AptosResult.Failure(AptosError.Validation("Invalid $name address"))

private fun key(account: AccountAddress, token: AccountAddress): String = "$account::$token"
