/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos

import io.ktor.client.HttpClient
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.AccountAbstractionService
import xyz.mcxross.kaptos.account.AccountService
import xyz.mcxross.kaptos.account.DefaultAccountAbstractionService
import xyz.mcxross.kaptos.account.DefaultAccountService
import xyz.mcxross.kaptos.account.Ed25519Account
import xyz.mcxross.kaptos.account.SingleKeyAccount
import xyz.mcxross.kaptos.fungible.DefaultFungibleAssetService
import xyz.mcxross.kaptos.fungible.FungibleAssetService
import xyz.mcxross.kaptos.faucet.DefaultFaucetService
import xyz.mcxross.kaptos.faucet.FaucetService
import xyz.mcxross.kaptos.coin.CoinService
import xyz.mcxross.kaptos.coin.DefaultCoinService
import xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.PrivateKey
import xyz.mcxross.kaptos.core.crypto.PrivateKeyType
import xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey
import xyz.mcxross.kaptos.digitalasset.DefaultDigitalAssetService
import xyz.mcxross.kaptos.digitalasset.DigitalAssetService
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.FaucetConfig
import xyz.mcxross.kaptos.model.FullNodeConfig
import xyz.mcxross.kaptos.model.IndexerConfig
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.SigningSchemeInput
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.transaction.DefaultTransactionService
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.names.DefaultNameService
import xyz.mcxross.kaptos.names.NameService
import xyz.mcxross.kaptos.ledger.DefaultLedgerService
import xyz.mcxross.kaptos.ledger.LedgerService
import xyz.mcxross.kaptos.view.DefaultViewService
import xyz.mcxross.kaptos.view.ViewService
import xyz.mcxross.kaptos.indexer.DefaultIndexerService
import xyz.mcxross.kaptos.indexer.IndexerService
import xyz.mcxross.kaptos.objects.DefaultObjectService
import xyz.mcxross.kaptos.objects.ObjectService
import xyz.mcxross.kaptos.staking.DefaultStakingService
import xyz.mcxross.kaptos.staking.StakingService
import xyz.mcxross.kaptos.table.DefaultTableService
import xyz.mcxross.kaptos.table.TableService

/** Optional endpoint overrides. Unspecified endpoints are derived from [AptosConfig.network]. */
data class AptosEndpoints(
  val fullNode: String? = null,
  val indexer: String? = null,
  val faucet: String? = null,
)

/** Headers and optional bearer token applied to one Aptos endpoint. */
data class AptosEndpointConfig(
  val headers: Map<String, String> = emptyMap(),
  val authToken: String? = null,
  /** Headers resolved immediately before each request, suitable for short-lived session tokens. */
  val requestHeaders: suspend () -> Map<String, String> = { emptyMap() },
)

/** Defaults used when a transaction build does not provide per-request options. */
data class TransactionDefaults(
  val maxGasAmount: ULong = 2_000_000uL,
  val expirationSecondsFromNow: ULong = 20uL,
)

/**
 * Immutable configuration for the namespaced [Aptos] API.
 *
 * An injected [httpClient] remains caller-owned. When it is omitted, [Aptos] creates and
 * closes one shared transport for all of its services.
 */
data class AptosConfig(
  val network: Network = Network.DEVNET,
  val endpoints: AptosEndpoints = AptosEndpoints(),
  val commonHeaders: Map<String, String> = emptyMap(),
  val fullNode: AptosEndpointConfig = AptosEndpointConfig(),
  val indexer: AptosEndpointConfig = AptosEndpointConfig(),
  val faucet: AptosEndpointConfig = AptosEndpointConfig(),
  val requestTimeoutMillis: Long = 10_000,
  val indexerWaitTimeoutMillis: Long = 10_000,
  val archivalFallback: Boolean = true,
  val namesContractAddress: AccountAddress? = null,
  val transactionDefaults: TransactionDefaults = TransactionDefaults(),
  val httpClient: HttpClient? = null,
) {
  init {
    require(requestTimeoutMillis > 0) { "requestTimeoutMillis must be positive" }
    require(indexerWaitTimeoutMillis > 0) { "indexerWaitTimeoutMillis must be positive" }
    require(transactionDefaults.maxGasAmount >= 2_000uL) {
      "maxGasAmount must be at least 2000"
    }
  }

  internal fun toTransportConfig(): TransportConfig =
    TransportConfig(
      AptosSettings(
        network = network,
        fullNode = endpoints.fullNode,
        indexer = endpoints.indexer,
        faucet = endpoints.faucet,
        client = httpClient,
        commonHeaders = commonHeaders,
        fullNodeConfig = FullNodeConfig(fullNode.headers, fullNode.requestHeaders),
        indexerConfig = IndexerConfig(indexer.headers, indexer.requestHeaders),
        faucetConfig = FaucetConfig(faucet.headers, faucet.authToken, faucet.requestHeaders),
        archivalFallback = archivalFallback,
        requestTimeoutMillis = requestTimeoutMillis,
        indexerWaitTimeoutMillis = indexerWaitTimeoutMillis,
      )
    )
}

/**
 * Kotlin-native, namespaced entry point for Aptos APIs.
 *
 * Use [aptos] for one workflow so clients and SDK-owned accounts are cleaned up automatically.
 * Construct this class directly when an application needs a long-lived client.
 */
class Aptos(val settings: AptosConfig = AptosConfig()) : AutoCloseable {
  internal val config: TransportConfig = settings.toTransportConfig()
  private val ownedResources = mutableListOf<AutoCloseable>()
  private var isClosed = false

  /**
   * Shared Ktor transport used by this client and optional Kaptos modules. Its lifecycle remains
   * owned by [Aptos], unless it was supplied in [AptosConfig].
   */
  val transportClient: HttpClient
    get() = config.httpClient

  /** Transaction building, signing, simulation, submission, and confirmation operations. */
  val transactions: TransactionService =
    DefaultTransactionService(
      config = config,
      defaults =
        TransactionOptions(
          maxGasAmount = settings.transactionDefaults.maxGasAmount,
          expirationSecondsFromNow = settings.transactionDefaults.expirationSecondsFromNow,
        ),
    )
  /** Account lookup, balance, discovery, and authentication-key rotation operations. */
  val accounts: AccountService = DefaultAccountService(config, transactions)

  /** Account-abstraction status and authentication-function transaction builders. */
  val abstraction: AccountAbstractionService =
    DefaultAccountAbstractionService(config, transactions)

  /** Legacy coin-standard transaction builders. */
  val coins: CoinService = DefaultCoinService(transactions)

  /** Ledger metadata and block lookup operations. */
  val ledger: LedgerService = DefaultLedgerService(config)

  /** Read-only Move view-function calls. */
  val views: ViewService = DefaultViewService(config)

  /** Low-level typed GraphQL execution for queries not covered by a domain service. */
  val indexer: IndexerService = DefaultIndexerService(config)

  /** REST and indexer table operations. */
  val tables: TableService = DefaultTableService(config)

  /** Object lookup operations. */
  val objects: ObjectService = DefaultObjectService(config)

  /** Delegated-staking reads. */
  val staking: StakingService = DefaultStakingService(config)

  /** Digital-asset reads and transaction builders. */
  val digitalAssets: DigitalAssetService = DefaultDigitalAssetService(config, transactions)

  /** Faucet funding operations for networks that advertise a faucet. */
  val faucet: FaucetService = DefaultFaucetService(config)

  /** Fungible-asset transaction builders. */
  val fungibleAssets: FungibleAssetService = DefaultFungibleAssetService(transactions)

  /** Aptos Names Service reads and transaction builders. */
  val names: NameService =
    DefaultNameService(config, transactions, settings.namesContractAddress)

  /**
   * Transfers [resource] ownership to this client. Owned resources are closed in reverse order
   * before the transport. Most applications use the higher-level account factories instead.
   */
  fun <T : AutoCloseable> own(resource: T): T {
    if (isClosed) {
      resource.close()
      error("Aptos has already been closed")
    }
    ownedResources += resource
    return resource
  }

  /**
   * Creates an SDK-owned local account. Its private key is cleared automatically when this client
   * closes, including when used inside [aptos].
   */
  fun account(
    privateKey: PrivateKey,
    address: AccountAddressInput? = null,
  ): Account =
    try {
      own(Account.fromPrivateKey(privateKey, address))
    } catch (error: Throwable) {
      privateKey.clear()
      throw error
    }

  /** Creates an SDK-owned legacy Ed25519 account while retaining its concrete account type. */
  fun ed25519Account(
    privateKey: Ed25519PrivateKey,
    address: AccountAddressInput? = null,
  ): Ed25519Account =
    try {
      own(Ed25519Account(privateKey, address))
    } catch (error: Throwable) {
      privateKey.clear()
      throw error
    }

  /** Creates an SDK-owned legacy Ed25519 account from a validated AIP-80 private key. */
  fun ed25519Account(
    privateKey: Aip80PrivateKey,
    address: AccountAddressInput? = null,
  ): Ed25519Account = ed25519Account(Ed25519PrivateKey.fromAip80(privateKey), address)

  /** Creates an SDK-owned legacy Ed25519 account from a canonical AIP-80 string. */
  fun ed25519Account(
    privateKey: String,
    address: AccountAddressInput? = null,
  ): Ed25519Account = ed25519Account(Aip80PrivateKey.parse(privateKey), address)

  /** Creates an SDK-owned SingleKey account while retaining its concrete account type. */
  fun singleKeyAccount(
    privateKey: PrivateKey,
    address: AccountAddressInput? = null,
  ): SingleKeyAccount =
    try {
      own(SingleKeyAccount(privateKey, address))
    } catch (error: Throwable) {
      privateKey.clear()
      throw error
    }

  /** Generates an SDK-owned SingleKey account; Ed25519 is the default key algorithm. */
  fun singleKeyAccount(
    signingScheme: SigningSchemeInput = SigningSchemeInput.Ed25519,
    address: AccountAddressInput? = null,
  ): SingleKeyAccount {
    val privateKey =
      when (signingScheme) {
        SigningSchemeInput.Ed25519 -> Ed25519PrivateKey.generate()
        SigningSchemeInput.Secp256k1 -> Secp256k1PrivateKey.generate()
        SigningSchemeInput.Secp256r1 ->
          throw IllegalArgumentException(
            "Secp256r1 transaction signing requires an application-owned PasskeyAccount"
          )
      }
    return singleKeyAccount(privateKey, address)
  }

  /** Creates an SDK-owned account from a validated AIP-80 private key. */
  fun account(
    privateKey: Aip80PrivateKey,
    address: AccountAddressInput? = null,
  ): Account =
    when (privateKey.type) {
      PrivateKeyType.Ed25519 -> ed25519Account(privateKey, address)
      PrivateKeyType.Secp256k1 -> account(Secp256k1PrivateKey.fromAip80(privateKey), address)
      PrivateKeyType.Secp256r1 ->
        throw IllegalArgumentException(
          "Secp256r1 transaction signing requires an application-owned PasskeyAccount"
        )
    }

  /** Creates an SDK-owned account from a canonical algorithm-prefixed AIP-80 string. */
  fun account(
    privateKey: String,
    address: AccountAddressInput? = null,
  ): Account = account(Aip80PrivateKey.parse(privateKey), address)

  /** Generates an SDK-owned local account. Ed25519 is the conventional Aptos default. */
  fun account(
    signingScheme: SigningSchemeInput = SigningSchemeInput.Ed25519,
    address: AccountAddressInput? = null,
  ): Account {
    val privateKey =
      when (signingScheme) {
        SigningSchemeInput.Ed25519 -> Ed25519PrivateKey.generate()
        SigningSchemeInput.Secp256k1 -> Secp256k1PrivateKey.generate()
        SigningSchemeInput.Secp256r1 ->
          throw IllegalArgumentException(
            "Secp256r1 transaction signing requires an application-owned PasskeyAccount"
          )
      }
    return account(privateKey, address)
  }

  /**
   * Clears SDK-owned secrets and closes SDK-created transports. Injected transports remain
   * caller-owned. Calling this method more than once has no effect.
   */
  override fun close() {
    if (isClosed) return
    isClosed = true
    var failure: Throwable? = null
    fun recordFailure(error: Throwable) {
      failure?.addSuppressed(error) ?: run { failure = error }
    }
    ownedResources.asReversed().forEach { resource ->
      try {
        resource.close()
      } catch (error: Throwable) {
        recordFailure(error)
      }
    }
    ownedResources.clear()
    try {
      config.close()
    } catch (error: Throwable) {
      recordFailure(error)
    }
    failure?.let { throw it }
  }
}

/**
 * Runs one Aptos workflow with managed lifecycle semantics.
 *
 * The client always closes after [block]. Accounts created through [Aptos.account] are also
 * cleared automatically. Long-lived applications can still construct [Aptos] directly.
 */
suspend fun <T> aptos(
  config: AptosConfig = AptosConfig(),
  block: suspend Aptos.() -> T,
): T {
  val client = Aptos(config)
  val result =
    try {
      client.block()
    } catch (error: Throwable) {
      try {
        client.close()
      } catch (closeError: Throwable) {
        error.addSuppressed(closeError)
      }
      throw error
    }
  client.close()
  return result
}
