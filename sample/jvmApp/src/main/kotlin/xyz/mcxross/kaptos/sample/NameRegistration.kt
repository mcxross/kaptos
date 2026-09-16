/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlin.time.Clock
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.names.NameExpirationPolicy
import xyz.mcxross.kaptos.util.runBlocking

/** Registers a unique testnet ANS domain and verifies its owner through the on-chain router. */
fun nameRegistration() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val name =
      System.getenv("KAPTOS_ANS_TEST_NAME") ?: "kaptos-sdk-${Clock.System.now().epochSeconds}"
    println("Registering $name.apt")
    val unsigned =
      names
        .buildRegister(
          sender = signer.accountAddress,
          name = name,
          expiration = NameExpirationPolicy.Domain,
        )
        .orThrow()
    val committed = transactions.submitAndWait(signer, unsigned).orThrow()
    val owner = names.owner(name).orThrow()

    check(owner == signer.accountAddress) {
      "Expected $name owner to be ${signer.accountAddress}, got $owner"
    }
    println("Registered $name.apt in ${committed.hash}")
  }
}
