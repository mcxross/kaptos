/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey
import xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.getAccountAddressesForAuthKey
import xyz.mcxross.kaptos.internal.getAuthKeysForPublicKey
import xyz.mcxross.kaptos.internal.getInfo
import xyz.mcxross.kaptos.internal.rethrowCancellation
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AccountData
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.SigningScheme
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.UserTransactionResponse
import xyz.mcxross.kaptos.model.types.OrderBy
import xyz.mcxross.kaptos.model.types.authKeyAccountAddressesFilter
import xyz.mcxross.kaptos.model.types.authKeyAccountAddressesOrder
import xyz.mcxross.kaptos.model.types.booleanFilter
import xyz.mcxross.kaptos.model.types.publicKeyAuthKeysFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.instances.RotationProofChallenge

/** A coin type or fungible-asset metadata object accepted by the unified balance endpoint. */
sealed interface AccountAsset {
  val value: String

  data class Coin(val type: xyz.mcxross.kaptos.model.StructTag) : AccountAsset {
    override val value: String
      get() = type.toString()
  }

  data class FungibleAsset(val metadataAddress: AccountAddress) : AccountAsset {
    override val value: String
      get() = metadataAddress.toString()
  }

  companion object {
    fun coin(type: String): AccountAsset = Coin(xyz.mcxross.kaptos.model.StructTag.fromString(type))

    fun fungibleAsset(metadataAddress: AccountAddressInput): AccountAsset =
      FungibleAsset(AccountAddress.from(metadataAddress))

    fun fungibleAsset(metadataAddress: String): AccountAsset =
      FungibleAsset(AccountAddress.fromString(metadataAddress))
  }
}

/** Controls public-key restoration queries without exposing GraphQL filter types. */
data class AccountLookupOptions(
  val includeUnverified: Boolean = false,
  val includeMultiKey: Boolean = true,
)

/** An on-chain account associated with a public key. */
data class AccountInfo(
  val address: AccountAddress,
  val publicKey: AccountPublicKey,
  val lastTransactionVersion: ULong,
)

/** Kotlin-native account lifecycle operations exposed as `client.accounts`. */
interface AccountService {
  /** Fetches REST account state for [address]. */
  suspend fun get(address: AccountAddressInput): AptosResult<AccountData>

  /** Fetch either a legacy coin or fungible-asset balance from the fullnode REST API. */
  suspend fun getBalance(
    address: AccountAddressInput,
    asset: AccountAsset,
  ): AptosResult<ULong>

  /** Finds current and rotated accounts associated with [publicKey]. */
  suspend fun findByPublicKey(
    publicKey: AccountPublicKey,
    options: AccountLookupOptions = AccountLookupOptions(),
  ): AptosResult<List<AccountInfo>>

  /** Reconstructs locally signable accounts owned by [signer] from on-chain key history. */
  suspend fun deriveOwned(
    signer: Account,
    options: AccountLookupOptions = AccountLookupOptions(),
  ): AptosResult<List<Account>>

  /** Build a challenge-verified rotation signed by both the current and new account keys. */
  suspend fun buildVerifiedRotation(
    currentAccount: Account,
    newAccount: Account,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Build a rotation that records a new public key without a proof from that key. */
  suspend fun buildUnverifiedRotation(
    currentAccount: Account,
    newPublicKey: AccountPublicKey,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>
}

internal data class RestoredAccountAddress(
  val authenticationKey: String,
  val address: AccountAddress,
  val lastTransactionVersion: ULong,
)

internal interface AccountRestorationDataSource {
  suspend fun getAccount(address: AccountAddress): AptosResult<AccountData>

  suspend fun getBalance(
    address: AccountAddress,
    asset: AccountAsset,
  ): AptosResult<ULong> =
    AptosResult.Failure(AptosError.UnsupportedFeature("Balance lookup is not configured"))

  suspend fun getLatestTransactionVersion(address: AccountAddress): AptosResult<ULong>

  suspend fun getRelatedMultiKeys(
    publicKey: AccountPublicKey,
    includeUnverified: Boolean,
  ): AptosResult<List<AccountPublicKey>>

  suspend fun getAddresses(
    authenticationKeys: List<String>,
    includeUnverified: Boolean,
  ): AptosResult<List<RestoredAccountAddress>>
}

internal class DefaultAccountService(
  private val dataSource: AccountRestorationDataSource,
  private val transactions: TransactionService,
) : AccountService {
  constructor(
    config: TransportConfig,
    transactions: TransactionService,
  ) : this(DefaultAccountRestorationDataSource(config), transactions)

  override suspend fun get(address: AccountAddressInput): AptosResult<AccountData> = executeAptos {
    dataSource.getAccount(AccountAddress.from(address))
  }

  override suspend fun getBalance(
    address: AccountAddressInput,
    asset: AccountAsset,
  ): AptosResult<ULong> = executeAptos {
    dataSource.getBalance(AccountAddress.from(address), asset)
  }

  override suspend fun findByPublicKey(
    publicKey: AccountPublicKey,
    options: AccountLookupOptions,
  ): AptosResult<List<AccountInfo>> {
    val candidateKeys = equivalentPublicKeys(publicKey).toMutableList()
    val found = mutableListOf<AccountInfo>()

    for (candidate in candidateKeys) {
      val address = candidate.authKey().deriveAddress()
      when (val account = dataSource.getAccount(address)) {
        is AptosResult.Failure -> {
          val missing =
            account.error is AptosError.Api &&
              account.error.errorCode.equals("account_not_found", ignoreCase = true)
          if (!missing) return account
        }
        is AptosResult.Success -> {
          if (account.value.authenticationKey.equals(candidate.authKey().toString(), true)) {
            when (val version = dataSource.getLatestTransactionVersion(address)) {
              is AptosResult.Failure -> return version
              is AptosResult.Success -> found += AccountInfo(address, candidate, version.value)
            }
          }
        }
      }
    }

    if (options.includeMultiKey && publicKey !is AbstractMultiKey) {
      when (val related = dataSource.getRelatedMultiKeys(publicKey, options.includeUnverified)) {
        is AptosResult.Failure -> return related
        is AptosResult.Success -> candidateKeys += related.value
      }
    }

    val keysByAuthenticationKey = candidateKeys.associateBy { it.authKey().toString() }
    when (
      val restored =
        dataSource.getAddresses(
          keysByAuthenticationKey.keys.toList(),
          options.includeUnverified,
        )
    ) {
      is AptosResult.Failure -> return restored
      is AptosResult.Success ->
        restored.value.forEach { entry ->
          val key =
            keysByAuthenticationKey[entry.authenticationKey]
              ?: return AptosResult.Failure(
                AptosError.Serialization(
                  "Indexer returned an authentication key that was not requested"
                )
              )
          found += AccountInfo(entry.address, key, entry.lastTransactionVersion)
        }
    }

    return AptosResult.Success(
      found
        .distinctBy { it.address.toStringLong() }
        .sortedByDescending(AccountInfo::lastTransactionVersion)
    )
  }

  override suspend fun deriveOwned(
    signer: Account,
    options: AccountLookupOptions,
  ): AptosResult<List<Account>> {
    if (signer is MultiKeyAccount && signer.signers.size == 1) {
      return deriveOwned(signer.signers.single(), options)
    }
    if (signer is MultiEd25519Account && signer.signerPrivateKeys.size == 1) {
      return deriveOwned(Ed25519Account(signer.signerPrivateKeys.single()), options)
    }

    return when (val accounts = findByPublicKey(signer.publicKey, options)) {
      is AptosResult.Failure -> accounts
      is AptosResult.Success -> {
        val owned = accounts.value.mapNotNull { restoreSignerAtAddress(signer, it) }
        AptosResult.Success(owned)
      }
    }
  }

  override suspend fun buildVerifiedRotation(
    currentAccount: Account,
    newAccount: Account,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> {
    if (newAccount !is Ed25519Account && newAccount !is MultiEd25519Account) {
      return AptosResult.Failure(
        AptosError.UnsupportedFeature(
          "Verified rotation supports Ed25519 and MultiEd25519 destination accounts"
        )
      )
    }

    return try {
      val accountData = dataSource.getAccount(currentAccount.accountAddress)
      if (accountData is AptosResult.Failure) return accountData
      val data = (accountData as AptosResult.Success).value
      val sequenceNumber = data.sequenceNumber
      val replayProtection = options.replayProtection
      if (replayProtection is ReplayProtection.Nonce) {
        return AptosResult.Failure(
          AptosError.Validation("Verified key rotation cannot use nonce replay protection")
        )
      }
      if (
        replayProtection is ReplayProtection.SequenceNumber &&
          replayProtection.value != sequenceNumber
      ) {
        return AptosResult.Failure(
          AptosError.Validation(
            "Rotation challenge sequence number must match the transaction sequence number"
          )
        )
      }

      val challenge =
        RotationProofChallenge(
          sequenceNumber = sequenceNumber,
          originator = currentAccount.accountAddress,
          currentAuthenticationKey = AccountAddress.fromString(data.authenticationKey),
          newPublicKey = newAccount.publicKey,
        )
      val challengeBytes = challenge.toBcs()
      val payload =
        TransactionPayload.entryFunction(
          function = "0x1::account::rotate_authentication_key",
          arguments =
            listOf(
              MoveArgument.U8(currentAccount.signingScheme.value.toUByte()),
              MoveArgument.Bytes(currentAccount.publicKey.toByteArray()),
              MoveArgument.U8(newAccount.signingScheme.value.toUByte()),
              MoveArgument.Bytes(newAccount.publicKey.toByteArray()),
              MoveArgument.Bytes(
                currentAccount.sign(HexInput.fromByteArray(challengeBytes)).toByteArray()
              ),
              MoveArgument.Bytes(
                newAccount.sign(HexInput.fromByteArray(challengeBytes)).toByteArray()
              ),
            ),
        )
      transactions.build(
        sender = currentAccount.accountAddress,
        payload = payload,
        options = options.copy(replayProtection = ReplayProtection.SequenceNumber(sequenceNumber)),
      )
    } catch (error: Throwable) {
      error.rethrowCancellation()
      AptosResult.Failure(AptosError.Crypto("Unable to build verified key rotation", error))
    }
  }

  override suspend fun buildUnverifiedRotation(
    currentAccount: Account,
    newPublicKey: AccountPublicKey,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> {
    val scheme =
      signingSchemeFor(newPublicKey)
        ?: return AptosResult.Failure(
          AptosError.UnsupportedFeature(
            "Unsupported authentication key type: ${newPublicKey::class.simpleName}"
          )
        )
    val payload =
      TransactionPayload.entryFunction(
        function = "0x1::account::rotate_authentication_key_from_public_key",
        arguments =
          listOf(
            MoveArgument.U8(scheme.value.toUByte()),
            MoveArgument.Bytes(newPublicKey.toByteArray()),
          ),
      )
    return transactions.build(currentAccount.accountAddress, payload, options)
  }

  private fun restoreSignerAtAddress(signer: Account, info: AccountInfo): Account? =
    when (val key = info.publicKey) {
      is Ed25519PublicKey -> {
        val privateKey = signer.ed25519PrivateKeyOrNull() ?: return null
        if (!privateKey.publicKey().sameKey(key)) null else Ed25519Account(privateKey, info.address)
      }
      is AnyPublicKey -> {
        val privateKey =
          when (signer) {
            is Ed25519Account -> signer.privateKey
            is SingleKeyAccount -> signer.privateKey
            else -> return null
          }
        if (!AnyPublicKey(privateKey.publicKey()).sameKey(key)) null
        else SingleKeyAccount(privateKey, info.address)
      }
      is MultiEd25519PublicKey -> {
        val privateKey = signer.ed25519PrivateKeyOrNull() ?: return null
        if (
          key.threshold.toInt() != 1 || key.publicKeys.none { it.sameKey(privateKey.publicKey()) }
        )
          null
        else MultiEd25519Account(key, listOf(privateKey), info.address)
      }
      is MultiKey -> {
        if (key.signaturesRequired != 1 || !key.publicKeys.any { it.sameKey(signer.publicKey) })
          null
        else MultiKeyAccount(key, listOf(signer), info.address)
      }
      else -> null
    }

  private fun Account.ed25519PrivateKeyOrNull(): Ed25519PrivateKey? =
    when (this) {
      is Ed25519Account -> privateKey
      is SingleKeyAccount -> privateKey as? Ed25519PrivateKey
      else -> null
    }
}

internal class DefaultAccountRestorationDataSource(private val config: TransportConfig) :
  AccountRestorationDataSource {
  override suspend fun getAccount(address: AccountAddress): AptosResult<AccountData> =
    getInfo(config, address).toAptosResult()

  override suspend fun getBalance(
    address: AccountAddress,
    asset: AccountAsset,
  ): AptosResult<ULong> =
    when (
      val result =
        xyz.mcxross.kaptos.client
          .getAptosFullNode<String>(
            RequestOptions.GetAptosRequestOptions(
              aptosConfig = config,
              originMethod = "getBalance",
              path = "accounts/${address.value}/balance/${asset.value}",
            )
          )
          .toAptosResult()
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success -> {
        val raw = result.value.trim().removeSurrounding("\"")
        raw.toULongOrNull()?.let { AptosResult.Success(it) }
          ?: AptosResult.Failure(
            AptosError.Serialization("Invalid u64 balance returned by the fullnode")
          )
      }
    }

  override suspend fun getLatestTransactionVersion(address: AccountAddress): AptosResult<ULong> =
    when (
      val result =
        getAptosFullNode<List<UserTransactionResponse>>(
            RequestOptions.GetAptosRequestOptions(
              aptosConfig = config,
              originMethod = "getLatestTransactionVersionForAddress",
              path = "accounts/${address.value}/transactions",
              params = mapOf("limit" to 1),
            )
          )
          .toAptosResult()
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success -> {
        val version = result.value.firstOrNull()?.version ?: "0"
        version.toULongOrNull()?.let { AptosResult.Success(it) }
          ?: AptosResult.Failure(
            AptosError.Serialization("Invalid transaction version returned by the fullnode")
          )
      }
    }

  override suspend fun getRelatedMultiKeys(
    publicKey: AccountPublicKey,
    includeUnverified: Boolean,
  ): AptosResult<List<AccountPublicKey>> {
    val anyPublicKey =
      when (publicKey) {
        is AnyPublicKey -> publicKey
        is Ed25519PublicKey -> AnyPublicKey(publicKey)
        else ->
          return AptosResult.Failure(
            AptosError.UnsupportedFeature("Related multi-key lookup requires a single public key")
          )
      }
    val filter = publicKeyAuthKeysFilter {
      this.publicKey = stringFilter { eq = anyPublicKey.publicKey.toString() }
      publicKeyType = stringFilter { eq = anyPublicKey.variant.indexerName }
      accountPublicKey = stringFilter { isNull = false }
      if (!includeUnverified) isPublicKeyUsed = booleanFilter { eq = true }
    }

    return when (val result = getAuthKeysForPublicKey(config, filter, null).toAptosResult()) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          AptosResult.Success(
            result.value
              ?.public_key_auth_keys
              .orEmpty()
              .mapNotNull { row ->
                row.account_public_key?.let { decodeRestorationPublicKey(row.signature_type, it) }
              }
              .distinctBy(AccountPublicKey::toString)
          )
        } catch (error: Throwable) {
          error.rethrowCancellation()
          AptosResult.Failure(
            AptosError.Serialization("Invalid account public key returned by the indexer", error)
          )
        }
    }
  }

  override suspend fun getAddresses(
    authenticationKeys: List<String>,
    includeUnverified: Boolean,
  ): AptosResult<List<RestoredAccountAddress>> {
    if (authenticationKeys.isEmpty()) return AptosResult.Success(emptyList())
    val filter = authKeyAccountAddressesFilter {
      authKey = stringFilter { inList = authenticationKeys }
      if (!includeUnverified) isAuthKeyUsed = booleanFilter { eq = true }
    }
    val order = authKeyAccountAddressesOrder { lastTransactionVersion = OrderBy.DESC }
    return when (
      val result = getAccountAddressesForAuthKey(config, filter, listOf(order)).toAptosResult()
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          AptosResult.Success(
            result.value?.auth_key_account_addresses.orEmpty().map { row ->
              RestoredAccountAddress(
                authenticationKey = row.auth_key,
                address = AccountAddress.fromString(row.account_address),
                lastTransactionVersion =
                  row.last_transaction_version.toString().toULongOrNull()
                    ?: throw IllegalArgumentException("Invalid last transaction version"),
              )
            }
          )
        } catch (error: Throwable) {
          error.rethrowCancellation()
          AptosResult.Failure(
            AptosError.Serialization(
              "Invalid account restoration row returned by the indexer",
              error,
            )
          )
        }
    }
  }
}

private fun equivalentPublicKeys(publicKey: AccountPublicKey): List<AccountPublicKey> =
  when {
    publicKey is Ed25519PublicKey -> listOf(publicKey, AnyPublicKey(publicKey))
    publicKey is AnyPublicKey && publicKey.publicKey is Ed25519PublicKey ->
      listOf(publicKey, publicKey.publicKey)
    else -> listOf(publicKey)
  }

private fun signingSchemeFor(publicKey: AccountPublicKey): SigningScheme? =
  when (publicKey) {
    is Ed25519PublicKey -> SigningScheme.Ed25519
    is MultiEd25519PublicKey -> SigningScheme.MultiEd25519
    is AnyPublicKey -> SigningScheme.SingleKey
    is MultiKey -> SigningScheme.MultiKey
    else -> null
  }

private val xyz.mcxross.kaptos.model.AnyPublicKeyVariant.indexerName: String
  get() =
    when (this) {
      xyz.mcxross.kaptos.model.AnyPublicKeyVariant.Ed25519 -> "ed25519"
      xyz.mcxross.kaptos.model.AnyPublicKeyVariant.Secp256k1 -> "secp256k1"
      xyz.mcxross.kaptos.model.AnyPublicKeyVariant.Secp256r1 -> "secp256r1"
      xyz.mcxross.kaptos.model.AnyPublicKeyVariant.Keyless -> "keyless"
      xyz.mcxross.kaptos.model.AnyPublicKeyVariant.FederatedKeyless -> "federated_keyless"
      xyz.mcxross.kaptos.model.AnyPublicKeyVariant.SlhDsaSha2_128s -> "slh_dsa_sha2_128s"
    }

private fun decodeRestorationPublicKey(signatureType: String, value: String): AccountPublicKey {
  val bytes = Hex.fromString(value).toByteArray()
  return when (signatureType) {
    "multi_ed25519_signature" -> {
      require(bytes.size > 1 && (bytes.size - 1) % Ed25519PublicKey.LENGTH == 0) {
        "Invalid MultiEd25519 public key length"
      }
      MultiEd25519PublicKey(
        publicKeys =
          bytes.dropLast(1).chunked(Ed25519PublicKey.LENGTH).map {
            Ed25519PublicKey(it.toByteArray())
          },
        threshold = bytes.last().toUByte(),
      )
    }
    "multi_key_signature" ->
      AptosBcsReader(bytes).let { reader -> reader.multiKey().also { reader.ensureFinished() } }
    else -> throw IllegalArgumentException("Unknown multi-signature type: $signatureType")
  }
}

private fun AccountPublicKey.sameKey(other: AccountPublicKey): Boolean =
  toByteArray().contentEquals(other.toByteArray()) && this::class == other::class

private fun AnyPublicKey.sameKey(other: AccountPublicKey): Boolean =
  when (other) {
    is AnyPublicKey -> toByteArray().contentEquals(other.toByteArray())
    else -> publicKey.toByteArray().contentEquals(other.toByteArray())
  }
