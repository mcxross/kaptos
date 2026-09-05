/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.account.Ed25519Account
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.move.MoveArgument

internal fun sampleConfig(): AptosConfig =
  AptosConfig(
    network = System.getenv("APTOS_NETWORK")?.uppercase()?.let(Network::valueOf) ?: Network.TESTNET
  )

internal fun Aptos.sampleSigner(variable: String = "APTOS_PRIVATE_KEY"): Ed25519Account =
  ed25519Account(requiredEnvironment(variable))

internal fun sampleAddress(variable: String): AccountAddress =
  AccountAddress.fromString(requiredEnvironment(variable))

internal fun aptTransfer(
  recipient: AccountAddress,
  amount: ULong,
): TransactionPayload.EntryFunction =
  TransactionPayload.entryFunction(
    function = "0x1::aptos_account::transfer",
    arguments = listOf(MoveArgument.Address(recipient), MoveArgument.U64(amount)),
  )

internal fun requiredEnvironment(name: String): String =
  System.getenv(name)?.takeIf(String::isNotBlank) ?: error("Set $name before running this sample")

internal fun <T> AptosResult<T>.orThrow(): T =
  when (this) {
    is AptosResult.Success -> value
    is AptosResult.Failure -> throw IllegalStateException(error.message, error.cause)
  }
