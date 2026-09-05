/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.faucet

import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.fundAccount
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.WaitForTransactionOptions

/** Test-network faucet operations exposed as `client.faucet`. */
interface FaucetService {
  /** Fund [address] with [amount] octas and wait for the funding transaction to commit. */
  suspend fun fund(
    address: AccountAddressInput,
    amount: ULong,
    options: WaitForTransactionOptions = WaitForTransactionOptions(),
  ): AptosResult<TransactionResponse>
}

internal fun interface FaucetDataSource {
  suspend fun fund(
    address: AccountAddress,
    amount: Long,
    options: WaitForTransactionOptions,
  ): AptosResult<TransactionResponse>
}

internal class DefaultFaucetService(private val dataSource: FaucetDataSource) : FaucetService {
  constructor(
    config: TransportConfig
  ) : this(
    FaucetDataSource { address, amount, options ->
      fundAccount(config, address, amount, options).toAptosResult()
    }
  )

  override suspend fun fund(
    address: AccountAddressInput,
    amount: ULong,
    options: WaitForTransactionOptions,
  ): AptosResult<TransactionResponse> {
    if (amount > Long.MAX_VALUE.toULong()) {
      return AptosResult.Failure(
        AptosError.Validation("Faucet amounts must fit the faucet API's signed 64-bit range")
      )
    }

    return executeAptos {
      dataSource.fund(AccountAddress.from(address), amount.toLong(), options)
    }
  }
}
