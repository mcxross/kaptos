/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.coin

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.util.APTOS_COIN

/** Legacy-coin transaction operations exposed as `client.coins`. */
interface CoinService {
  /** Build a coin transfer. New fungible assets should use `client.fungibleAssets`. */
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    amount: ULong,
    coinType: String = APTOS_COIN,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>
}

internal class DefaultCoinService(
  private val transactions: TransactionService,
) : CoinService {
  override suspend fun buildTransfer(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    amount: ULong,
    coinType: String,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    try {
      transactions.build(
        sender = sender,
        payload =
          TransactionPayload.entryFunction(
            function = "0x1::aptos_account::transfer_coins",
            typeArguments = listOf(TypeTag.fromString(coinType)),
            arguments =
              listOf(
                MoveArgument.Address(AccountAddress.from(recipient)),
                MoveArgument.U64(amount),
              ),
          ),
        options = options,
      )
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid coin transfer", error))
    }
}
