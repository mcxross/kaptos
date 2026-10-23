/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.fungible.FungibleAssetService
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.UnsignedTransaction

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class FungibleAssets internal constructor(private val service: FungibleAssetService) {
  @Throws(Throwable::class)
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    metadataAddress: AccountAddressInput,
    recipient: AccountAddressInput,
    amount: ULong,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildTransfer(
        sender = sender,
        metadataAddress = metadataAddress,
        recipient = recipient,
        amount = amount,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildStoreTransfer(
    sender: AccountAddressInput,
    fromStore: AccountAddressInput,
    toStore: AccountAddressInput,
    amount: ULong,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildStoreTransfer(
        sender = sender,
        fromStore = fromStore,
        toStore = toStore,
        amount = amount,
        options = options,
      )
      .swiftValue()
}
