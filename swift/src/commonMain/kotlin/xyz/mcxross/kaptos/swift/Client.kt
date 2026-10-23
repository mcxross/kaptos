/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.AptosEndpoints
import xyz.mcxross.kaptos.TransactionDefaults
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey
import xyz.mcxross.kaptos.confidential.confidentialAssets
import xyz.mcxross.kaptos.confidential.confidentialDecryptionKey
import xyz.mcxross.kaptos.encrypted.encryptedTransactions
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.SigningSchemeInput

/** Swift configuration keeps transport and serializer implementation types out of call sites. */
data class Configuration(
  val network: Network = Network.DEVNET,
  val fullNode: String? = null,
  val indexer: String? = null,
  val faucet: String? = null,
  val headers: Map<String, String> = emptyMap(),
  val requestTimeoutMillis: Long = 10_000,
  val indexerWaitTimeoutMillis: Long = 10_000,
  val maxGasAmount: ULong = 2_000_000uL,
  val expirationSecondsFromNow: ULong = 20uL,
) {
  internal fun toKotlin(): AptosConfig =
    AptosConfig(
      network = network,
      endpoints = AptosEndpoints(fullNode, indexer, faucet),
      commonHeaders = headers,
      requestTimeoutMillis = requestTimeoutMillis,
      indexerWaitTimeoutMillis = indexerWaitTimeoutMillis,
      transactionDefaults = TransactionDefaults(maxGasAmount, expirationSecondsFromNow),
    )
}

/**
 * Concrete throwing service boundary for Swift export. All requests, encoding, signing, and
 * lifecycle ownership delegate to [Aptos]. Confine a client and its accounts to one Swift actor.
 */
class Client internal constructor(internal val kotlin: Aptos) {
  private val workers = mutableListOf<Worker>()
  private var closed = false

  @Throws(Throwable::class)
  constructor(configuration: Configuration) : this(Aptos(configuration.toKotlin()))

  val accounts: Accounts = Accounts(kotlin.accounts)
  val transactions: Transactions = Transactions(kotlin.transactions)
  val views: Views = Views(kotlin.views)
  val indexer: Indexer = Indexer(kotlin.indexer)
  val ledger: Ledger = Ledger(kotlin.ledger)
  val tables: Tables = Tables(kotlin.tables)
  val objects: Objects = Objects(kotlin.objects)
  val staking: Staking = Staking(kotlin.staking)
  val digitalAssets: DigitalAssets = DigitalAssets(kotlin.digitalAssets)
  val faucet: Faucet = Faucet(kotlin.faucet)
  val fungibleAssets: FungibleAssets = FungibleAssets(kotlin.fungibleAssets)
  val names: Names = Names(kotlin.names)
  val abstraction: Abstraction = Abstraction(kotlin.abstraction)
  val coins: Coins = Coins(kotlin.coins)
  val encryptedTransactions: EncryptedTransactions =
    EncryptedTransactions(kotlin.encryptedTransactions())
  val confidentialAssets: ConfidentialAssets = ConfidentialAssets(kotlin.confidentialAssets())

  @Throws(Throwable::class)
  fun generateConfidentialKey(): ConfidentialDecryptionKey = kotlin.confidentialDecryptionKey()

  /** Local accounts are owned by this client and cleared when it closes. */
  @Throws(Throwable::class)
  fun generateAccount(scheme: SigningSchemeInput = SigningSchemeInput.Ed25519): Account =
    kotlin.account(scheme)

  /** Accepts an algorithm-prefixed AIP-80 key and an optional rotated account address. */
  @Throws(Throwable::class)
  fun importAccount(privateKey: String, address: String? = null): Account =
    kotlin.account(privateKey, address?.let(AccountAddress::fromString))

  suspend fun clearAbiCache() = kotlin.clearAbiCache()

  @Throws(Throwable::class)
  fun worker(signer: TransactionSigner, capacity: Int = 64): Worker {
    check(!closed) { "Client is closed" }
    require(capacity > 0) { "capacity must be positive" }
    return Worker(kotlin, signer, capacity).also { workers += it }
  }

  /** Stops workers before clearing accounts and closing the transport. */
  @Throws(Throwable::class)
  suspend fun closeAsync() {
    workers.forEach { it.shutdown(cancelPending = true) }
    close()
  }

  /** Idempotently clears owned private keys and closes the shared Darwin HTTP transport. */
  @Throws(Throwable::class)
  fun close() {
    if (closed) return
    check(workers.all { it.isStopped }) { "Shutdown workers or await closeAsync before closing" }
    closed = true
    kotlin.close()
  }
}
