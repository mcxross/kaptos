/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.keyless.ephemeralKeyPair
import xyz.mcxross.kaptos.keyless.keyless

/** Keyless flow after the application-owned OIDC redirect has returned a trusted JWT. */
fun keylessTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val keyless = keyless()
    val ephemeral = ephemeralKeyPair()
    // Put ephemeral.nonce in the OIDC authorization request before obtaining this JWT.
    val account =
      keyless
        .deriveStandardAccount(requiredEnvironment("APTOS_KEYLESS_JWT"), ephemeral)
        .orThrow()
    val committed =
      transactions
        .submitAndWait(
          signer = account,
          payload = aptTransfer(sampleAddress("APTOS_RECIPIENT"), 1_000_000uL),
        )
        .orThrow()
    println("Committed ${committed.hash}")
  }
}
