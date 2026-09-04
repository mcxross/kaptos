/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos

/** Fee-payer transfer with independently produced sender and sponsor authenticators. */
fun sponsoredTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val sender = sampleSigner()
    val sponsor = sampleSigner("APTOS_SPONSOR_PRIVATE_KEY")
    val committed =
      transactions
        .submitAndWait(
          signer = sender,
          payload = aptTransfer(sampleAddress("APTOS_RECIPIENT"), 1_000_000uL),
          feePayer = sponsor,
        )
        .orThrow()
    println("Committed ${committed.hash}")
  }
}
