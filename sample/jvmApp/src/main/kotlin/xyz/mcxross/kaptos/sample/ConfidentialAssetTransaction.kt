/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.confidential.confidentialDecryptionKey
import xyz.mcxross.kaptos.confidential.confidentialAssets

/** Registers a local confidential encryption key for the selected fungible asset. */
fun confidentialAssetTransaction() = runBlocking {
  aptos(sampleConfig()) {
    val signer = sampleSigner()
    val key = confidentialDecryptionKey()
    val committed =
      confidentialAssets()
        .registerBalance(
          signer = signer,
          token = sampleAddress("APTOS_CONFIDENTIAL_ASSET"),
          key = key,
        )
        .orThrow()
    println("Committed ${committed.hash}")
  }
}
