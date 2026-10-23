/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.faucet.FaucetService
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.WaitForTransactionOptions

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Faucet internal constructor(private val service: FaucetService) {
  @Throws(Throwable::class)
  suspend fun fund(
    address: AccountAddressInput,
    amount: ULong,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): TransactionResponse =
    service.fund(address = address, amount = amount, options = options).swiftValue()
}
