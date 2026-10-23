/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.coin.CoinService
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.util.APTOS_COIN

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Coins internal constructor(private val service: CoinService) {
  @Throws(Throwable::class)
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    amount: ULong,
    coinType: String = APTOS_COIN,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildTransfer(
        sender = sender,
        recipient = recipient,
        amount = amount,
        coinType = coinType,
        options = options,
      )
      .swiftValue()
}
