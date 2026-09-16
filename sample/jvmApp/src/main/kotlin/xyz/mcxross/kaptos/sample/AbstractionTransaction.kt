/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.account.SolanaDerivableAccount
import xyz.mcxross.kaptos.aptos
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
        function = "0x1::aptos_account::transfer",
        arguments = listOf(abstracted.accountAddress, 20_000_000),
      )
      .orThrow()
    val committed =
      transactions
        .submitAndWait(
          signer = abstracted,
          function = "0x1::aptos_account::transfer",
          arguments =
            listOf(
              sampleAddress("APTOS_RECIPIENT", default = fundingAccount.accountAddress.toString()),
              1_000_000,
            ),
          transactionOptions = TransactionOptions(maxGasAmount = 50_000uL),
        )
        .orThrow()

    println("Committed ${committed.hash} from ${abstracted.accountAddress}")
  }
}
