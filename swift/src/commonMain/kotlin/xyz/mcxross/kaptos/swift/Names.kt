/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.names.NameExpirationPolicy
import xyz.mcxross.kaptos.names.NameRecord
import xyz.mcxross.kaptos.names.NameService

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Names internal constructor(private val service: NameService) {
  @Throws(Throwable::class)
  suspend fun owner(name: String): AccountAddress? = service.owner(name = name).swiftValue()

  @Throws(Throwable::class)
  suspend fun target(name: String): AccountAddress? = service.target(name = name).swiftValue()

  @Throws(Throwable::class)
  suspend fun expiration(name: String): ULong = service.expiration(name = name).swiftValue()

  @Throws(Throwable::class)
  suspend fun primaryName(accountAddress: AccountAddressInput): String? =
    service.primaryName(accountAddress = accountAddress).swiftValue()

  @Throws(Throwable::class)
  suspend fun getName(name: String): NameRecord? = service.getName(name = name).swiftValue()

  @Throws(Throwable::class)
  suspend fun getAccountNames(
    accountAddress: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosPage<NameRecord> =
    service.getAccountNames(accountAddress = accountAddress, page = page).swiftValue()

  @Throws(Throwable::class)
  suspend fun getAccountDomains(
    accountAddress: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosPage<NameRecord> =
    service.getAccountDomains(accountAddress = accountAddress, page = page).swiftValue()

  @Throws(Throwable::class)
  suspend fun getAccountSubdomains(
    accountAddress: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosPage<NameRecord> =
    service.getAccountSubdomains(accountAddress = accountAddress, page = page).swiftValue()

  @Throws(Throwable::class)
  suspend fun getDomainSubdomains(
    domain: String,
    page: PageRequest = PageRequest(),
  ): AptosPage<NameRecord> = service.getDomainSubdomains(domain = domain, page = page).swiftValue()

  @Throws(Throwable::class)
  suspend fun buildSetTarget(
    sender: AccountAddressInput,
    name: String,
    target: AccountAddressInput,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildSetTarget(sender = sender, name = name, target = target, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildClearTarget(
    sender: AccountAddressInput,
    name: String,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service.buildClearTarget(sender = sender, name = name, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun buildSetPrimary(
    sender: AccountAddressInput,
    name: String? = null,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service.buildSetPrimary(sender = sender, name = name, options = options).swiftValue()

  @Throws(Throwable::class)
  suspend fun buildRegister(
    sender: AccountAddressInput,
    name: String,
    expiration: NameExpirationPolicy,
    transferable: Boolean = false,
    target: AccountAddressInput? = null,
    owner: AccountAddressInput? = null,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildRegister(
        sender = sender,
        name = name,
        expiration = expiration,
        transferable = transferable,
        target = target,
        owner = owner,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildRenew(
    sender: AccountAddressInput,
    name: String,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service.buildRenew(sender = sender, name = name, options = options).swiftValue()
}
