/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.encrypted

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.mcxross.fastkrypto.aptosBatchEncrypt
import xyz.mcxross.fastkrypto.secureRandomBytes
import xyz.mcxross.fastkrypto.sha3256
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.account.TransactionSignerKind
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.ledger.LedgerState
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.ClaimedEntryFunction
import xyz.mcxross.kaptos.model.EncryptedCiphertext
import xyz.mcxross.kaptos.model.EncryptedTransactionPayload
import xyz.mcxross.kaptos.model.FixedBytes32
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.TransactionExecutable
import xyz.mcxross.kaptos.model.TransactionExtraConfig
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.WaitForTransactionOptions
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

private const val DECRYPTION_NONCE_LENGTH = 16
private const val ENCRYPTED_GAS_UNIT_PRICE_FLOOR = 200uL
private const val DECRYPTED_PLAINTEXT_DOMAIN = "APTOS::DecryptedPlaintext"

/** Optional authentication-key overrides used to avoid an on-chain lookup after key rotation. */
data class EncryptedTransactionOptions(
  val senderAuthenticationKey: FixedBytes32? = null,
  val secondarySignerAuthenticationKeys: List<FixedBytes32?>? = null,
  val feePayerAuthenticationKey: FixedBytes32? = null,
  val claimedEntryFunction: ClaimedEntryFunction? = null,
)

/** Aptos batch-encrypted transaction construction and submission. */
interface EncryptedTransactionService {
  /** Encrypt an already-built simple, multi-agent, fee-payer, or orderless transaction. */
  suspend fun encrypt(
    transaction: UnsignedTransaction,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
  ): AptosResult<UnsignedTransaction>

  /** Encrypt, sign every local signer, and submit the transaction. */
  suspend fun signAndSubmit(
    transaction: UnsignedTransaction,
    sender: TransactionSigner,
    secondarySigners: List<TransactionSigner> = emptyList(),
    feePayer: TransactionSigner? = null,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
  ): AptosResult<PendingTransactionResponse>

  /** Builds, encrypts, signs, and submits a conventional single-sender payload. */
  suspend fun signAndSubmit(
    sender: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions? = null,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
  ): AptosResult<PendingTransactionResponse>

  /** Builds, encrypts, signs, submits, and waits for a single-sender payload. */
  suspend fun submitAndWait(
    sender: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions? = null,
    options: EncryptedTransactionOptions = EncryptedTransactionOptions(),
    waitOptions: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): AptosResult<TransactionResponse>

  /** Drop cached ledger and authentication keys, for example after a committed key rotation. */
  suspend fun clearCache()
}

/** Create an encrypted-transaction service bound to this client's transport and account services. */
fun Aptos.encryptedTransactions(): EncryptedTransactionService =
  DefaultEncryptedTransactionService(
    context = ClientEncryptionContext(this),
    transactions = transactions,
    crypto = FastKryptoBatchEncryption,
  )

internal interface EncryptionContext {
  suspend fun ledger(): AptosResult<LedgerState>

  suspend fun authenticationKey(address: AccountAddress): AptosResult<String>
}

private class ClientEncryptionContext(private val client: Aptos) : EncryptionContext {
  override suspend fun ledger(): AptosResult<LedgerState> = client.ledger.info()

  override suspend fun authenticationKey(address: AccountAddress): AptosResult<String> =
    when (val account = client.accounts.get(address)) {
      is AptosResult.Failure -> account
      is AptosResult.Success -> AptosResult.Success(account.value.authenticationKey)
    }
}

internal interface BatchEncryptionCrypto {
  fun randomBytes(length: Int): ByteArray

  fun sha3(input: ByteArray): ByteArray

  fun encrypt(
    encryptionKeyBcs: ByteArray,
    plaintextBcs: ByteArray,
    associatedDataBcs: ByteArray,
  ): ByteArray
}

internal object FastKryptoBatchEncryption : BatchEncryptionCrypto {
  override fun randomBytes(length: Int): ByteArray = secureRandomBytes(length.toUInt())

  override fun sha3(input: ByteArray): ByteArray = sha3256(input)

  override fun encrypt(
    encryptionKeyBcs: ByteArray,
    plaintextBcs: ByteArray,
    associatedDataBcs: ByteArray,
  ): ByteArray =
    aptosBatchEncrypt(
      encryptionKeyBcs = encryptionKeyBcs,
      plaintextBcs = plaintextBcs,
      associatedDataBcs = associatedDataBcs,
    )
}

internal class DefaultEncryptedTransactionService(
  private val context: EncryptionContext,
  private val transactions: TransactionService? = null,
  private val crypto: BatchEncryptionCrypto,
) : EncryptedTransactionService {
  private val cacheMutex = Mutex()
  private val authenticationKeys = mutableMapOf<String, FixedBytes32>()
  private var encryptionKey: CachedEncryptionKey? = null

  override suspend fun encrypt(
    transaction: UnsignedTransaction,
    options: EncryptedTransactionOptions,
  ): AptosResult<UnsignedTransaction> {
    val prepared = preparePayload(transaction.rawTransaction.payload)
    if (prepared is AptosResult.Failure) return prepared
    prepared as AptosResult.Success

    val claim = resolveClaim(transaction, prepared.value, options.claimedEntryFunction)
    if (claim is AptosResult.Failure) return claim

    val signerKeys = resolveSignerKeys(transaction, options)
    if (signerKeys is AptosResult.Failure) return signerKeys

    val ledger = context.ledger()
    if (ledger is AptosResult.Failure) return ledger
    ledger as AptosResult.Success
    val currentEncryptionKey = resolveEncryptionKey(ledger.value)
      ?: return AptosResult.Failure(
        AptosError.UnsupportedFeature(
          "The configured fullnode does not advertise encrypted transaction support"
        )
      )

    return try {
      val nonce = crypto.randomBytes(DECRYPTION_NONCE_LENGTH)
      require(nonce.size == DECRYPTION_NONCE_LENGTH) {
        "Secure random backend returned ${nonce.size} bytes, expected $DECRYPTION_NONCE_LENGTH"
      }
      val plaintext = prepared.value.executable.toBcs() + nonce
      val associatedData =
        encodeAssociatedData(
          sender = transaction.rawTransaction.sender,
          signerKeys = (signerKeys as AptosResult.Success).value,
        )
      val ciphertext =
        EncryptedCiphertext.fromBcs(
          crypto.encrypt(
            encryptionKeyBcs = currentEncryptionKey.key.toByteArray(),
            plaintextBcs = plaintext,
            associatedDataBcs = associatedData,
          )
        )
      require(ciphertext.associatedData.toByteArray().contentEquals(associatedData)) {
        "Batch-encryption backend returned ciphertext for different associated data"
      }
      val domain = crypto.sha3(DECRYPTED_PLAINTEXT_DOMAIN.encodeToByteArray())
      val payloadHash = crypto.sha3(domain + plaintext)
      require(domain.size == 32 && payloadHash.size == 32) {
        "SHA3-256 backend returned a non-32-byte digest"
      }
      val encryptedPayload =
        TransactionPayload.Encrypted(
          EncryptedTransactionPayload(
            ciphertext = ciphertext,
            extraConfig = prepared.value.extraConfig,
            payloadHash = FixedBytes32(payloadHash),
            encryptionEpoch = currentEncryptionKey.epoch,
            claimedEntryFunction = (claim as AptosResult.Success).value,
          )
        )
      AptosResult.Success(transaction.withEncryptedPayload(encryptedPayload))
    } catch (error: Throwable) {
      AptosResult.Failure(
        AptosError.Crypto(error.message ?: "Unable to encrypt transaction payload", error)
      )
    }
  }

  override suspend fun signAndSubmit(
    transaction: UnsignedTransaction,
    sender: TransactionSigner,
    secondarySigners: List<TransactionSigner>,
    feePayer: TransactionSigner?,
    options: EncryptedTransactionOptions,
  ): AptosResult<PendingTransactionResponse> {
    val signers = listOfNotNull(sender, *secondarySigners.toTypedArray(), feePayer)
    if (signers.any { it.kind != TransactionSignerKind.Standard }) {
      return AptosResult.Failure(
        AptosError.UnsupportedFeature("Keyless signers cannot sign encrypted transactions")
      )
    }
    val signerValidation = validateSigners(transaction, sender, secondarySigners, feePayer)
    if (signerValidation != null) return AptosResult.Failure(signerValidation)

    val encrypted = encrypt(transaction, options)
    if (encrypted is AptosResult.Failure) return encrypted
    encrypted as AptosResult.Success

    val senderAuthenticator = sender.signTransaction(encrypted.value)
    if (senderAuthenticator is AptosResult.Failure) return senderAuthenticator
    val secondaryAuthenticators = mutableListOf<AccountAuthenticator>()
    for (signer in secondarySigners) {
      when (val authenticator = signer.signTransaction(encrypted.value)) {
        is AptosResult.Failure -> return authenticator
        is AptosResult.Success -> secondaryAuthenticators += authenticator.value
      }
    }
    val feePayerAuthenticator =
      if (feePayer == null) {
        null
      } else {
        when (val authenticator = feePayer.signTransaction(encrypted.value)) {
          is AptosResult.Failure -> return authenticator
          is AptosResult.Success -> authenticator.value
        }
      }
    val transactionService = transactions
      ?: return AptosResult.Failure(
        AptosError.UnsupportedFeature("Transaction submission is not configured")
      )
    return transactionService.submit(
      transaction = encrypted.value,
      senderAuthenticator = (senderAuthenticator as AptosResult.Success).value,
      secondaryAuthenticators = secondaryAuthenticators,
      feePayerAuthenticator = feePayerAuthenticator,
    )
  }

  override suspend fun signAndSubmit(
    sender: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions?,
    options: EncryptedTransactionOptions,
  ): AptosResult<PendingTransactionResponse> {
    val transactionService = transactions
      ?: return AptosResult.Failure(
        AptosError.UnsupportedFeature("Transaction submission is not configured")
      )
    return when (
      val transaction =
        transactionService.build(sender.accountAddress, payload, transactionOptions)
    ) {
      is AptosResult.Failure -> transaction
      is AptosResult.Success -> signAndSubmit(transaction.value, sender, options = options)
    }
  }

  override suspend fun submitAndWait(
    sender: TransactionSigner,
    payload: TransactionPayload,
    transactionOptions: TransactionOptions?,
    options: EncryptedTransactionOptions,
    waitOptions: WaitForTransactionOptions,
  ): AptosResult<TransactionResponse> {
    val transactionService = transactions
      ?: return AptosResult.Failure(
        AptosError.UnsupportedFeature("Transaction submission is not configured")
      )
    return when (
      val pending = signAndSubmit(sender, payload, transactionOptions, options)
    ) {
      is AptosResult.Failure -> pending
      is AptosResult.Success ->
        transactionService.waitForTransaction(pending.value, waitOptions)
    }
  }

  override suspend fun clearCache() {
    cacheMutex.withLock {
      authenticationKeys.clear()
      encryptionKey = null
    }
  }

  private suspend fun resolveEncryptionKey(ledger: LedgerState): CachedEncryptionKey? {
    val advertised = ledger.encryptionKey ?: return null
    val cached = cacheMutex.withLock { encryptionKey }
    if (cached != null && cached.epoch == ledger.epoch) return cached
    return CachedEncryptionKey(ledger.epoch, ByteString(advertised.toByteArray())).also { value ->
      cacheMutex.withLock { encryptionKey = value }
    }
  }

  private suspend fun resolveSignerKeys(
    transaction: UnsignedTransaction,
    options: EncryptedTransactionOptions,
  ): AptosResult<List<SignerAuthenticationKey>> {
    val secondaryAddresses = transaction.secondarySignerAddresses()
    val overrides = options.secondarySignerAuthenticationKeys
    if (secondaryAddresses.isEmpty() && !overrides.isNullOrEmpty()) {
      return AptosResult.Failure(
        AptosError.Validation(
          "secondarySignerAuthenticationKeys was provided for a transaction without secondary signers"
        )
      )
    }
    if (overrides != null && overrides.size != secondaryAddresses.size) {
      return AptosResult.Failure(
        AptosError.Validation(
          "Expected ${secondaryAddresses.size} secondary authentication-key entries, got ${overrides.size}"
        )
      )
    }

    val feePayerAddress = (transaction as? UnsignedTransaction.FeePayer)?.feePayerAddress
    val hasOnChainFeePayer = feePayerAddress != null && !feePayerAddress.isZero()
    if (options.feePayerAuthenticationKey != null && !hasOnChainFeePayer) {
      return AptosResult.Failure(
        AptosError.Validation(
          "feePayerAuthenticationKey requires a non-zero fee-payer address"
        )
      )
    }

    val result = mutableListOf<SignerAuthenticationKey>()
    val senderKey =
      resolveAuthenticationKey(transaction.rawTransaction.sender, options.senderAuthenticationKey)
    if (senderKey is AptosResult.Failure) return senderKey
    result +=
      SignerAuthenticationKey(
        transaction.rawTransaction.sender,
        (senderKey as AptosResult.Success).value,
      )

    for ((index, address) in secondaryAddresses.withIndex()) {
      when (val key = resolveAuthenticationKey(address, overrides?.get(index))) {
        is AptosResult.Failure -> return key
        is AptosResult.Success -> result += SignerAuthenticationKey(address, key.value)
      }
    }
    if (hasOnChainFeePayer) {
      when (val key = resolveAuthenticationKey(feePayerAddress, options.feePayerAuthenticationKey)) {
        is AptosResult.Failure -> return key
        is AptosResult.Success -> result += SignerAuthenticationKey(feePayerAddress, key.value)
      }
    }
    return AptosResult.Success(result)
  }

  private suspend fun resolveAuthenticationKey(
    address: AccountAddress,
    override: FixedBytes32?,
  ): AptosResult<FixedBytes32> {
    if (override != null) return AptosResult.Success(override)
    val cacheKey = address.toStringLong()
    cacheMutex.withLock { authenticationKeys[cacheKey] }?.let {
      return AptosResult.Success(it)
    }
    return when (val fetched = context.authenticationKey(address)) {
      is AptosResult.Failure -> fetched
      is AptosResult.Success ->
        try {
          val key = FixedBytes32(Hex.fromString(fetched.value).toByteArray())
          cacheMutex.withLock { authenticationKeys[cacheKey] = key }
          AptosResult.Success(key)
        } catch (error: Throwable) {
          AptosResult.Failure(
            AptosError.Serialization("Invalid authentication key returned by fullnode", error)
          )
        }
    }
  }
}

private data class CachedEncryptionKey(val epoch: ULong, val key: ByteString)

private data class SignerAuthenticationKey(
  val address: AccountAddress,
  val authenticationKey: FixedBytes32,
)

private data class PreparedPayload(
  val executable: TransactionExecutable,
  val extraConfig: TransactionExtraConfig,
)

private fun preparePayload(payload: TransactionPayload): AptosResult<PreparedPayload> {
  val prepared =
    when (payload) {
      is TransactionPayload.EntryFunction ->
        PreparedPayload(
          TransactionExecutable.EntryFunction(payload.call),
          TransactionExtraConfig.V1(),
        )
      is TransactionPayload.Script ->
        PreparedPayload(TransactionExecutable.Script(payload.script), TransactionExtraConfig.V1())
      is TransactionPayload.Multisig ->
        PreparedPayload(
          executable =
            when (val inner = payload.payload) {
              is xyz.mcxross.kaptos.model.MultisigPayload.EntryFunction ->
                TransactionExecutable.EntryFunction(inner.call)
              is xyz.mcxross.kaptos.model.MultisigPayload.Script ->
                TransactionExecutable.Script(inner.script)
              null -> TransactionExecutable.Empty
            },
          extraConfig = TransactionExtraConfig.V1(multisigAddress = payload.multisigAddress),
        )
      is TransactionPayload.InnerV1 -> PreparedPayload(payload.executable, payload.extraConfig)
      is TransactionPayload.Encrypted ->
        return AptosResult.Failure(
          AptosError.Validation("The transaction payload is already encrypted")
        )
    }
  if (prepared.executable == TransactionExecutable.Encrypted) {
    return AptosResult.Failure(
      AptosError.UnsupportedFeature("The encrypted executable sentinel cannot be encrypted client-side")
    )
  }
  return AptosResult.Success(prepared)
}

private fun resolveClaim(
  transaction: UnsignedTransaction,
  payload: PreparedPayload,
  requested: ClaimedEntryFunction?,
): AptosResult<ClaimedEntryFunction?> {
  val hasFeePayer = transaction is UnsignedTransaction.FeePayer
  val hasMultisig =
    (payload.extraConfig as? TransactionExtraConfig.V1)?.multisigAddress != null
  if (!hasFeePayer && !hasMultisig) return AptosResult.Success(null)

  val entry = payload.executable as? TransactionExecutable.EntryFunction
  if (requested != null) {
    if (entry == null) {
      return AptosResult.Failure(
        AptosError.Validation(
          "claimedEntryFunction is only valid when the plaintext is an entry function"
        )
      )
    }
    if (!entry.call.module.address.sameAddress(requested.module.address) ||
      entry.call.module.name != requested.module.name
    ) {
      return AptosResult.Failure(
        AptosError.Validation("claimedEntryFunction.module must match the plaintext entry function")
      )
    }
    if (requested.function != null && requested.function != entry.call.function) {
      return AptosResult.Failure(
        AptosError.Validation("claimedEntryFunction.function must match the plaintext entry function")
      )
    }
    return AptosResult.Success(requested)
  }
  return AptosResult.Success(
    entry?.let { ClaimedEntryFunction(it.call.module, it.call.function) }
  )
}

private fun UnsignedTransaction.withEncryptedPayload(
  payload: TransactionPayload.Encrypted
): UnsignedTransaction {
  val gasPrice =
    if (rawTransaction.gasUnitPrice < ENCRYPTED_GAS_UNIT_PRICE_FLOOR) {
      ENCRYPTED_GAS_UNIT_PRICE_FLOOR
    } else {
      rawTransaction.gasUnitPrice
    }
  val raw = rawTransaction.copy(payload = payload, gasUnitPrice = gasPrice)
  return when (this) {
    is UnsignedTransaction.Simple -> copy(rawTransaction = raw)
    is UnsignedTransaction.MultiAgent -> copy(rawTransaction = raw)
    is UnsignedTransaction.FeePayer -> copy(rawTransaction = raw)
  }
}

private fun UnsignedTransaction.secondarySignerAddresses(): List<AccountAddress> =
  when (this) {
    is UnsignedTransaction.Simple -> emptyList()
    is UnsignedTransaction.MultiAgent -> secondarySignerAddresses
    is UnsignedTransaction.FeePayer -> secondarySignerAddresses
  }

private fun validateSigners(
  transaction: UnsignedTransaction,
  sender: TransactionSigner,
  secondarySigners: List<TransactionSigner>,
  feePayer: TransactionSigner?,
): AptosError.Validation? {
  if (!sender.accountAddress.sameAddress(transaction.rawTransaction.sender)) {
    return AptosError.Validation("Sender signer address does not match the transaction sender")
  }
  val expectedSecondary = transaction.secondarySignerAddresses()
  if (secondarySigners.size != expectedSecondary.size) {
    return AptosError.Validation(
      "Expected ${expectedSecondary.size} secondary signers, got ${secondarySigners.size}"
    )
  }
  if (secondarySigners.indices.any {
      !secondarySigners[it].accountAddress.sameAddress(expectedSecondary[it])
    }
  ) {
    return AptosError.Validation("Secondary signers must match transaction order")
  }
  return when (transaction) {
    is UnsignedTransaction.FeePayer -> {
      when {
        feePayer == null -> AptosError.Validation("A fee-payer transaction requires a fee-payer signer")
        transaction.feePayerAddress.isZero() ->
          AptosError.Validation("Resolve the external fee-payer address before signing")
        !feePayer.accountAddress.sameAddress(transaction.feePayerAddress) ->
          AptosError.Validation("Fee-payer signer address does not match the transaction")
        else -> null
      }
    }
    else ->
      if (feePayer != null) {
        AptosError.Validation("A fee-payer signer was provided for a non-fee-payer transaction")
      } else {
        null
      }
  }
}

private fun encodeAssociatedData(
  sender: AccountAddress,
  signerKeys: List<SignerAuthenticationKey>,
): ByteArray =
  BcsOutput().apply {
    uleb128(0u)
    fixed(sender.data)
    uleb128(signerKeys.size.toUInt())
    signerKeys.forEach {
      fixed(it.address.data)
      bytes(it.authenticationKey.toByteArray())
    }
  }.toByteArray()

private class BcsOutput {
  private val output = mutableListOf<Byte>()

  fun uleb128(value: UInt) {
    var remaining = value
    do {
      var byte = (remaining and 0x7fu).toInt()
      remaining = remaining shr 7
      if (remaining != 0u) byte = byte or 0x80
      output += byte.toByte()
    } while (remaining != 0u)
  }

  fun fixed(bytes: ByteArray) {
    output.addAll(bytes.asList())
  }

  fun bytes(bytes: ByteArray) {
    require(bytes.size.toLong() <= UInt.MAX_VALUE.toLong()) { "BCS value is too large" }
    uleb128(bytes.size.toUInt())
    fixed(bytes)
  }

  fun toByteArray(): ByteArray = output.toByteArray()
}

private fun AccountAddress.sameAddress(other: AccountAddress): Boolean =
  data.contentEquals(other.data)

private fun AccountAddress.isZero(): Boolean = data.all { it == 0.toByte() }
