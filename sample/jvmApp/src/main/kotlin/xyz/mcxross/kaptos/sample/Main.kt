/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.util.runBlocking

/** Run one modern sample; transaction samples read credentials from the environment. */
fun main(arguments: Array<String>) {
  when (val sample = arguments.firstOrNull() ?: "stadard") {
    "standard" -> standardTransaction()
    "sponsored" -> sponsoredTransaction()
    "orderless" -> orderlessTransaction()
    "abstraction" -> abstractionTransaction()
    "keyless" -> keylessTransaction()
    "encrypted" -> encryptedTransaction()
    "confidential" -> confidentialAssetTransaction()
    "account" -> accountLifecycle()
    "multi-key" -> multiKeyAccount()
    else -> runBlocking{
      val aptos = Aptos()
      println(aptos.ledger.blockAtVersion(59uL))
    }
  }
}
