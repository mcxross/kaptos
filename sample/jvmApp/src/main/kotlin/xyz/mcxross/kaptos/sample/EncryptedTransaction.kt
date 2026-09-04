/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.encrypted.encryptedTransactions

/** Encrypts the executable payload when the selected network advertises an encryption key. */
fun encryptedTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val committed =
      encryptedTransactions()
        .submitAndWait(
          sender = signer,
          payload = aptTransfer(sampleAddress("APTOS_RECIPIENT"), 1_000_000uL),
        )
        .orThrow()
    println("Committed ${committed.hash}")
  }
}
