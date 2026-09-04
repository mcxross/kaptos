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
    val nonce =
      requiredEnvironment("APTOS_ORDERLESS_NONCE").toULongOrNull()
        ?: error("APTOS_ORDERLESS_NONCE must be an unsigned 64-bit integer")
    val committed =
      transactions
        .submitAndWait(
          signer = signer,
          payload = aptTransfer(sampleAddress("APTOS_RECIPIENT"), 1_000_000uL),
          transactionOptions =
            TransactionOptions(replayProtection = ReplayProtection.Nonce(nonce)),
        )
        .orThrow()
    println("Committed ${committed.hash}")
  }
}
