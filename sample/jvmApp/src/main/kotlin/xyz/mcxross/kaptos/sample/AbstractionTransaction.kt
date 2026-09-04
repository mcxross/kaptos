/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.account.SolanaDerivableAccount
import xyz.mcxross.kaptos.model.TransactionOptions

/** Funds a framework-native derivable account, then submits a SIWS-authenticated transfer. */
fun abstractionTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val fundingAccount = sampleSigner()
    val abstracted =
      SolanaDerivableAccount.fromEd25519(
        signer = fundingAccount,
        domain = System.getenv("APTOS_ABSTRACTION_DOMAIN") ?: "kaptos.example",
      )

    transactions
      .submitAndWait(
        signer = fundingAccount,
        payload = aptTransfer(abstracted.accountAddress, 20_000_000uL),
      )
      .orThrow()
    val committed =
      transactions
        .submitAndWait(
          signer = abstracted,
          payload = aptTransfer(sampleAddress("APTOS_RECIPIENT"), 1_000_000uL),
          transactionOptions = TransactionOptions(maxGasAmount = 50_000uL),
        )
        .orThrow()
    println("Committed ${committed.hash} from ${abstracted.accountAddress}")
  }
}
