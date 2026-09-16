/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.model.AptosCoin
import xyz.mcxross.kaptos.view.callOf

/** Standard transfer using `Aptos.transactions`. */
fun standardTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val recipient = sampleAddress("APTOS_RECIPIENT", default = signer.accountAddress.toString())

    val balanceBefore = views.callOf<AptosCoin>("0x1::coin::balance", signer.accountAddress)
    println("Account: ${signer.accountAddress}")
    println("Balance before: $balanceBefore")

    val committed =
      transactions
        .submitAndWait(
          signer = signer,
          function = "0x1::aptos_account::transfer",
          arguments = listOf(recipient, 10_000),
        )
        .orThrow()

    println("Committed ${committed.hash}")

    val balanceAfter = views.callOf<AptosCoin>("0x1::coin::balance", signer.accountAddress)
    println("Balance after: $balanceAfter")
  }
}
