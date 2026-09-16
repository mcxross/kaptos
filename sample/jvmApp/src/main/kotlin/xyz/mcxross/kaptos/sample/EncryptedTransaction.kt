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
import xyz.mcxross.kaptos.model.TransactionPayload

/** Encrypts the executable payload when the selected network advertises an encryption key. */
fun encryptedTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val recipient = sampleAddress("APTOS_RECIPIENT", default = signer.accountAddress.toString())
    val committed =
      encryptedTransactions()
        .submitAndWait(
          sender = signer,
          payload =
            TransactionPayload.entryFunctionOf(
              "0x1::aptos_account::transfer",
              recipient,
              10_000uL,
            ),
        )
        .orThrow()

    println("Committed ${committed.hash}")
  }
}
