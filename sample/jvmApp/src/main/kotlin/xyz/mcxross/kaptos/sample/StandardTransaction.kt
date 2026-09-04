/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos

/** Standard transfer using `Aptos.transactions`. */
fun standardTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val payload = aptTransfer(sampleAddress("APTOS_RECIPIENT"), 1_000_000uL)
    val committed = transactions.submitAndWait(signer, payload).orThrow()
    println("Committed ${committed.hash}")
  }
}
