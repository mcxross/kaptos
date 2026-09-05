/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.fungible

import xyz.mcxross.kaptos.internal.rethrowCancellation
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService

/** Fungible-asset transaction operations exposed as `client.fungibleAssets`. */
interface FungibleAssetService {
  /** Build a transfer between the sender and recipient primary stores. */
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    metadataAddress: AccountAddressInput,
    recipient: AccountAddressInput,
    amount: ULong,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Build a transfer between explicit primary or secondary fungible stores. */
  suspend fun buildStoreTransfer(
    sender: AccountAddressInput,
    fromStore: AccountAddressInput,
    toStore: AccountAddressInput,
    amount: ULong,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>
}

internal class DefaultFungibleAssetService(private val transactions: TransactionService) :
  FungibleAssetService {
  override suspend fun buildTransfer(
    sender: AccountAddressInput,
    metadataAddress: AccountAddressInput,
    recipient: AccountAddressInput,
    amount: ULong,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> = buildSafely {
    transactions.build(
      sender = sender,
      payload =
        TransactionPayload.entryFunction(
          function = "0x1::primary_fungible_store::transfer",
          typeArguments = listOf(METADATA_TYPE),
          arguments =
            listOf(
              MoveArgument.Address(AccountAddress.from(metadataAddress)),
              MoveArgument.Address(AccountAddress.from(recipient)),
              MoveArgument.U64(amount),
            ),
        ),
      options = options,
    )
  }

  override suspend fun buildStoreTransfer(
    sender: AccountAddressInput,
    fromStore: AccountAddressInput,
    toStore: AccountAddressInput,
    amount: ULong,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> = buildSafely {
    transactions.build(
      sender = sender,
      payload =
        TransactionPayload.entryFunction(
          function = "0x1::dispatchable_fungible_asset::transfer",
          typeArguments = listOf(FUNGIBLE_STORE_TYPE),
          arguments =
            listOf(
              MoveArgument.Address(AccountAddress.from(fromStore)),
              MoveArgument.Address(AccountAddress.from(toStore)),
              MoveArgument.U64(amount),
            ),
        ),
      options = options,
    )
  }

  private suspend fun buildSafely(
    block: suspend () -> AptosResult<UnsignedTransaction.Simple>
  ): AptosResult<UnsignedTransaction.Simple> =
    try {
      block()
    } catch (error: Throwable) {
      error.rethrowCancellation()
      AptosResult.Failure(AptosError.Validation("Invalid fungible-asset transfer", error))
    }

  private companion object {
    val METADATA_TYPE = TypeTag.fromString("0x1::fungible_asset::Metadata")
    val FUNGIBLE_STORE_TYPE = TypeTag.fromString("0x1::fungible_asset::FungibleStore")
  }
}
