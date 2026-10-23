/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.ledger.LedgerBlock
import xyz.mcxross.kaptos.ledger.LedgerService
import xyz.mcxross.kaptos.ledger.LedgerState

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Ledger internal constructor(private val service: LedgerService) {
  @Throws(Throwable::class) suspend fun info(): LedgerState = service.info().swiftValue()

  @Throws(Throwable::class)
  suspend fun blockAtVersion(version: ULong): LedgerBlock =
    service.blockAtVersion(version = version).swiftValue()

  @Throws(Throwable::class)
  suspend fun blockAtHeight(height: ULong): LedgerBlock =
    service.blockAtHeight(height = height).swiftValue()
}
