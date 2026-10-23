/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.account.AccountAbstractionService
import xyz.mcxross.kaptos.account.AccountAbstractionStatus
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Abstraction internal constructor(private val service: AccountAbstractionService) {
  @Throws(Throwable::class)
  suspend fun status(accountAddress: AccountAddressInput): AccountAbstractionStatus =
    service.status(accountAddress = accountAddress).swiftValue()

  @Throws(Throwable::class)
  suspend fun isEnabled(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction,
  ): Boolean =
    service
      .isEnabled(accountAddress = accountAddress, authenticationFunction = authenticationFunction)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildEnable(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildEnable(
        accountAddress = accountAddress,
        authenticationFunction = authenticationFunction,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildDisable(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction? = null,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildDisable(
        accountAddress = accountAddress,
        authenticationFunction = authenticationFunction,
        options = options,
      )
      .swiftValue()
}
