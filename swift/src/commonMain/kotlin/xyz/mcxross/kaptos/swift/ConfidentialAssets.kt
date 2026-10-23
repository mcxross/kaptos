/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.confidential.ConfidentialActivityQuery
import xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity
import xyz.mcxross.kaptos.confidential.ConfidentialAssetService
import xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus
import xyz.mcxross.kaptos.confidential.ConfidentialBalance
import xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey
import xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey
import xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
import xyz.mcxross.kaptos.confidential.EffectiveAuditorHint
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.UnsignedTransaction

data class ConfidentialActivityPage(
  val items: List<ConfidentialAssetActivity>,
  val totalCount: Int,
  val request: PageRequest,
  val hasNextPage: Boolean,
)

/** Concrete throwing boundary for the optional ConfidentialAssets module. */
class ConfidentialAssets internal constructor(private val service: ConfidentialAssetService) {
  @Throws(Throwable::class)
  suspend fun getBalance(
    account: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    useCache: Boolean = false,
  ): ConfidentialBalance =
    service
      .getBalance(account = account, token = token, key = key, useCache = useCache)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun getEncryptionKey(
    account: AccountAddressInput,
    token: AccountAddressInput,
    useCache: Boolean = true,
  ): ConfidentialEncryptionKey =
    service.getEncryptionKey(account = account, token = token, useCache = useCache).swiftValue()

  @Throws(Throwable::class)
  suspend fun getAssetAuditorEncryptionKey(
    token: AccountAddressInput,
    useCache: Boolean = true,
  ): ConfidentialEncryptionKey? =
    service.getAssetAuditorEncryptionKey(token = token, useCache = useCache).swiftValue()

  @Throws(Throwable::class)
  suspend fun getEffectiveAuditorHint(
    account: AccountAddressInput,
    token: AccountAddressInput,
  ): EffectiveAuditorHint? =
    service.getEffectiveAuditorHint(account = account, token = token).swiftValue()

  @Throws(Throwable::class)
  suspend fun getStatus(
    account: AccountAddressInput,
    token: AccountAddressInput,
  ): ConfidentialAssetStatus = service.getStatus(account = account, token = token).swiftValue()

  @Throws(Throwable::class)
  suspend fun isEmergencyPaused(): Boolean = service.isEmergencyPaused().swiftValue()

  @Throws(Throwable::class)
  suspend fun getActivities(
    query: ConfidentialActivityQuery = ConfidentialActivityQuery()
  ): ConfidentialActivityPage =
    service.getActivities(query).swiftValue().let {
      ConfidentialActivityPage(it.items, it.totalCount, it.request, it.hasNextPage)
    }

  @Throws(Throwable::class)
  suspend fun buildRegisterBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildRegisterBalance(sender = sender, token = token, key = key, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildDeposit(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    amount: ULong,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildDeposit(sender = sender, token = token, amount = amount, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildWithdraw(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    recipient: AccountAddressInput = sender,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildWithdraw(
        sender = sender,
        token = token,
        key = key,
        amount = amount,
        recipient = recipient,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    voluntaryAuditors: List<ConfidentialEncryptionKey> = emptyList(),
    memo: ByteArray = byteArrayOf(),
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildTransfer(
        sender = sender,
        recipient = recipient,
        token = token,
        key = key,
        amount = amount,
        voluntaryAuditors = voluntaryAuditors,
        memo = memo,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildNormalizeBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildNormalizeBalance(sender = sender, token = token, key = key, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildRolloverPendingBalance(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    pauseIncoming: Boolean = false,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildRolloverPendingBalance(
        sender = sender,
        token = token,
        pauseIncoming = pauseIncoming,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildSetIncomingTransfersPaused(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    paused: Boolean,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildSetIncomingTransfersPaused(
        sender = sender,
        token = token,
        paused = paused,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildRotateEncryptionKey(
    sender: AccountAddressInput,
    token: AccountAddressInput,
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    unpauseIncoming: Boolean = true,
    requireEmptyPendingBalance: Boolean = true,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): UnsignedTransaction =
    service
      .buildRotateEncryptionKey(
        sender = sender,
        token = token,
        currentKey = currentKey,
        newKey = newKey,
        unpauseIncoming = unpauseIncoming,
        requireEmptyPendingBalance = requireEmptyPendingBalance,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun registerBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .registerBalance(signer = signer, token = token, key = key, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun deposit(
    signer: TransactionSigner,
    token: AccountAddressInput,
    amount: ULong,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service.deposit(signer = signer, token = token, amount = amount, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun withdraw(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    recipient: AccountAddressInput = signer.accountAddress,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .withdraw(
        signer = signer,
        token = token,
        key = key,
        amount = amount,
        recipient = recipient,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun transfer(
    signer: TransactionSigner,
    recipient: AccountAddressInput,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    amount: ULong,
    voluntaryAuditors: List<ConfidentialEncryptionKey> = emptyList(),
    memo: ByteArray = byteArrayOf(),
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .transfer(
        signer = signer,
        recipient = recipient,
        token = token,
        key = key,
        amount = amount,
        voluntaryAuditors = voluntaryAuditors,
        memo = memo,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun normalizeBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    key: ConfidentialDecryptionKey,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .normalizeBalance(signer = signer, token = token, key = key, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun rolloverPendingBalance(
    signer: TransactionSigner,
    token: AccountAddressInput,
    pauseIncoming: Boolean = false,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .rolloverPendingBalance(
        signer = signer,
        token = token,
        pauseIncoming = pauseIncoming,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun setIncomingTransfersPaused(
    signer: TransactionSigner,
    token: AccountAddressInput,
    paused: Boolean,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .setIncomingTransfersPaused(
        signer = signer,
        token = token,
        paused = paused,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun rotateEncryptionKey(
    signer: TransactionSigner,
    token: AccountAddressInput,
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    unpauseIncoming: Boolean = true,
    requireEmptyPendingBalance: Boolean = true,
    options: ConfidentialTransactionOptions = ConfidentialTransactionOptions(),
  ): TransactionResponse =
    service
      .rotateEncryptionKey(
        signer = signer,
        token = token,
        currentKey = currentKey,
        newKey = newKey,
        unpauseIncoming = unpauseIncoming,
        requireEmptyPendingBalance = requireEmptyPendingBalance,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class) suspend fun clearCache(): Unit = service.clearCache()
}
