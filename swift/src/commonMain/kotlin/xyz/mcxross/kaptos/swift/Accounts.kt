/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.AccountAsset
import xyz.mcxross.kaptos.account.AccountInfo
import xyz.mcxross.kaptos.account.AccountLookupOptions
import xyz.mcxross.kaptos.account.AccountService
import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AccountData
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.UnsignedTransaction

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Accounts internal constructor(private val service: AccountService) {
  @Throws(Throwable::class)
  suspend fun get(address: AccountAddressInput): AccountData =
    service.get(address = address).swiftValue()

  @Throws(Throwable::class)
  suspend fun getBalance(
    address: AccountAddressInput,
    asset: AccountAsset,
  ): ULong = service.getBalance(address = address, asset = asset).swiftValue()

  @Throws(Throwable::class)
  suspend fun findByPublicKey(
    publicKey: AccountPublicKey,
    options: AccountLookupOptions = AccountLookupOptions(),
  ): List<AccountInfo> =
    service.findByPublicKey(publicKey = publicKey, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun deriveOwned(
    signer: Account,
    options: AccountLookupOptions = AccountLookupOptions(),
  ): List<Account> = service.deriveOwned(signer = signer, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun buildVerifiedRotation(
    currentAccount: Account,
    newAccount: Account,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildVerifiedRotation(
        currentAccount = currentAccount,
        newAccount = newAccount,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildUnverifiedRotation(
    currentAccount: Account,
    newPublicKey: AccountPublicKey,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildUnverifiedRotation(
        currentAccount = currentAccount,
        newPublicKey = newPublicKey,
        options = options,
      )
      .swiftValue()
}
