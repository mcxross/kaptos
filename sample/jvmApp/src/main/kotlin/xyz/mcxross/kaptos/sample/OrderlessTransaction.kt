/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.TransactionOptions

/** Nonce-based orderless transfer; the builder sets sequence number to `u64::MAX`. */
fun orderlessTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val recipient = sampleAddress("APTOS_RECIPIENT", default = signer.accountAddress.toString())
    val nonce =
      System.getenv("APTOS_ORDERLESS_NONCE")?.toULongOrNull()
        ?: System.currentTimeMillis().toULong()

    val committed =
      transactions
        .submitAndWait(
          signer = signer,
          function = "0x1::aptos_account::transfer",
          arguments = listOf(recipient, 10_000),
          transactionOptions = TransactionOptions(replayProtection = ReplayProtection.Nonce(nonce)),
        )
        .orThrow()
    println("Committed ${committed.hash}")
  }
}
