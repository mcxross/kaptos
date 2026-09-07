/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult

/** Concurrency-safe reservation of a sender's Aptos sequence numbers. */
class AccountSequenceManager(
  val accountAddress: AccountAddress,
  private val fetchSequenceNumber: suspend (AccountAddress) -> AptosResult<ULong>,
) {
  private val mutex = Mutex()
  private var nextSequenceNumber: ULong? = null

  /** Reserves one sequence number, fetching chain state on the first reservation. */
  suspend fun reserve(): AptosResult<ULong> = mutex.withLock {
    val current =
      nextSequenceNumber
        ?: when (val fetched = fetchSequenceNumber(accountAddress)) {
          is AptosResult.Success -> fetched.value
          is AptosResult.Failure -> return@withLock fetched
        }
    if (current == ULong.MAX_VALUE) {
      return@withLock AptosResult.Failure(
        AptosError.Validation("Account sequence number cannot be incremented past u64::MAX")
      )
    }
    nextSequenceNumber = current + 1uL
    AptosResult.Success(current)
  }

  /** Replaces local state with the current on-chain sequence number. */
  suspend fun resynchronize(): AptosResult<ULong> = mutex.withLock {
    when (val fetched = fetchSequenceNumber(accountAddress)) {
      is AptosResult.Success -> {
        nextSequenceNumber = fetched.value
        fetched
      }
      is AptosResult.Failure -> fetched
    }
  }

  /** Forces the next reservation to fetch chain state again. */
  suspend fun invalidate() {
    mutex.withLock { nextSequenceNumber = null }
  }

  /** Returns the next locally available number, or null before initialization. */
  suspend fun peek(): ULong? = mutex.withLock { nextSequenceNumber }
}
