/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.model.TransactionResponse

/** Swift export omits the model's hash property because KotlinBase inherits NSObject.hash. */
object ResponseValues {
  fun transactionHash(response: TransactionResponse): String = response.hash
}
