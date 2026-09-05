/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.encrypted

import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UserTransactionResponse
import xyz.mcxross.kaptos.move.MoveArgument

/** Opt-in live test for the same single-sender flow used by the JVM encrypted sample. */
class EncryptedTransactionDevnetE2eTest {
  @Test
  fun encryptedTransferCommitsOnDevnet() = runBlocking {
    if (System.getenv("KAPTOS_DEVNET_ENCRYPTED_E2E") != "true") return@runBlocking

    aptos(AptosConfig(network = Network.DEVNET)) {
      val signer = account()
      faucet.fund(signer.accountAddress, 100_000_000uL).getOrFail()
      val payload =
        TransactionPayload.entryFunction(
          function = "0x1::aptos_account::transfer",
          arguments =
            listOf(
              MoveArgument.Address(signer.accountAddress),
              MoveArgument.U64(1_000u),
            ),
        )
      val committed =
        encryptedTransactions()
          .submitAndWait(
            sender = signer,
            payload = payload,
            transactionOptions =
              TransactionOptions(
                // Encrypted payload bytes raise the intrinsic-gas floor above the SDK minimum.
                maxGasAmount = 100_000uL,
                gasUnitPrice = 200uL,
              ),
          )
          .getOrFail()
      val userTransaction = assertIs<UserTransactionResponse>(committed)
      assertTrue(userTransaction.success, userTransaction.vmStatus)
    }
  }

  private fun <T> AptosResult<T>.getOrFail(): T =
    when (this) {
      is AptosResult.Success -> value
      is AptosResult.Failure -> fail(error.message, error.cause)
    }
}
