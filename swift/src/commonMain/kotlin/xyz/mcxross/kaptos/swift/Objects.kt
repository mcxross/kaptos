/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.objects.ObjectRecord
import xyz.mcxross.kaptos.objects.ObjectService

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class Objects internal constructor(private val service: ObjectService) {
  @Throws(Throwable::class)
  suspend fun get(address: AccountAddressInput): ObjectRecord? =
    service.get(address = address).swiftValue()
}
