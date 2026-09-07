/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.UnsignedTransaction

/** Observable lifecycle event emitted by [TransactionWorker]. */
sealed interface TransactionWorkerEvent {
  val requestId: ULong?

  data class Queued(override val requestId: ULong) : TransactionWorkerEvent

  data class Building(
    override val requestId: ULong,
    val sequenceNumber: ULong,
  ) : TransactionWorkerEvent

  data class Submitted(
    override val requestId: ULong,
    val sequenceNumber: ULong,
    val transaction: PendingTransactionResponse,
  ) : TransactionWorkerEvent

  data class Failed(
    override val requestId: ULong,
    val error: AptosError,
  ) : TransactionWorkerEvent

  data class Cancelled(override val requestId: ULong) : TransactionWorkerEvent

  data object Drained : TransactionWorkerEvent {
    override val requestId: ULong? = null
  }
}

/** Whether worker shutdown drains queued work or cancels it immediately. */
enum class TransactionWorkerShutdown {
  Drain,
  Cancel,
}

/**
 * Bounded, single-sender transaction pipeline.
 *
 * The worker reserves sequence numbers only after dequeuing work, retries once after a recognized
 * sequence mismatch, and never blocks callers from observing an individual submission result.
 */
class TransactionWorker(
  scope: CoroutineScope,
  private val sequenceManager: AccountSequenceManager,
  capacity: Int = DEFAULT_CAPACITY,
  private val submitTransaction:
    suspend (UnsignedTransaction) -> AptosResult<PendingTransactionResponse>,
  private val isSequenceMismatch: (AptosError) -> Boolean = ::defaultSequenceMismatch,
) {
  init {
    require(capacity > 0) { "capacity must be positive" }
  }

  private data class Work(
    val requestId: ULong,
    val build: suspend (ULong) -> AptosResult<UnsignedTransaction>,
    val result: CompletableDeferred<AptosResult<PendingTransactionResponse>>,
  )

  private val idMutex = Mutex()
  private var nextRequestId = 0uL
  private val mutableEvents =
    MutableSharedFlow<TransactionWorkerEvent>(extraBufferCapacity = capacity)
  private val channel =
    Channel<Work>(
      capacity = capacity,
      onUndeliveredElement = { work ->
        val error = AptosError.Cancelled("Transaction worker was cancelled")
        work.result.complete(AptosResult.Failure(error))
        mutableEvents.tryEmit(TransactionWorkerEvent.Cancelled(work.requestId))
      },
    )
  private val workerJob: Job = scope.launch { runWorker() }

  /** Hot stream of queue, build, submit, failure, cancellation, and drain events. */
  val events: SharedFlow<TransactionWorkerEvent> = mutableEvents.asSharedFlow()

  /** Queues a transaction factory and returns its independently awaitable result. */
  suspend fun submit(
    build: suspend (sequenceNumber: ULong) -> AptosResult<UnsignedTransaction>
  ): Deferred<AptosResult<PendingTransactionResponse>> {
    val requestId = idMutex.withLock {
      check(nextRequestId != ULong.MAX_VALUE) { "Transaction worker request ID overflow" }
      nextRequestId++
    }
    val result = CompletableDeferred<AptosResult<PendingTransactionResponse>>()
    val work = Work(requestId, build, result)
    try {
      channel.send(work)
      mutableEvents.emit(TransactionWorkerEvent.Queued(requestId))
    } catch (error: Throwable) {
      result.complete(
        AptosResult.Failure(AptosError.Cancelled("Transaction worker is closed", error))
      )
    }
    return result
  }

  /** Drains queued submissions or cancels them immediately, then waits for termination. */
  suspend fun shutdown(mode: TransactionWorkerShutdown = TransactionWorkerShutdown.Drain) {
    when (mode) {
      TransactionWorkerShutdown.Drain -> channel.close()
      TransactionWorkerShutdown.Cancel -> channel.cancel()
    }
    if (mode == TransactionWorkerShutdown.Cancel) workerJob.cancel()
    listOf(workerJob).joinAll()
  }

  private suspend fun runWorker() {
    for (work in channel) {
      try {
        process(work)
      } catch (error: CancellationException) {
        if (!work.result.isCompleted) {
          work.result.complete(
            AptosResult.Failure(AptosError.Cancelled("Transaction submission was cancelled", error))
          )
          mutableEvents.tryEmit(TransactionWorkerEvent.Cancelled(work.requestId))
        }
        throw error
      }
    }
    mutableEvents.emit(TransactionWorkerEvent.Drained)
  }

  private suspend fun process(work: Work) {
    var attempt = 0
    while (attempt < 2) {
      val reserved = sequenceManager.reserve()
      if (reserved is AptosResult.Failure) {
        finishFailure(work, reserved.error)
        return
      }
      val sequenceNumber = (reserved as AptosResult.Success).value
      mutableEvents.emit(TransactionWorkerEvent.Building(work.requestId, sequenceNumber))
      val transaction = work.build(sequenceNumber)
      if (transaction is AptosResult.Failure) {
        finishFailure(work, transaction.error)
        return
      }
      when (val submitted = submitTransaction((transaction as AptosResult.Success).value)) {
        is AptosResult.Success -> {
          work.result.complete(submitted)
          mutableEvents.emit(
            TransactionWorkerEvent.Submitted(work.requestId, sequenceNumber, submitted.value)
          )
          return
        }
        is AptosResult.Failure -> {
          if (attempt == 0 && isSequenceMismatch(submitted.error)) {
            when (val refreshed = sequenceManager.resynchronize()) {
              is AptosResult.Success -> {
                attempt++
                continue
              }
              is AptosResult.Failure -> {
                finishFailure(work, refreshed.error)
                return
              }
            }
          }
          finishFailure(work, submitted.error)
          return
        }
      }
    }
  }

  private suspend fun finishFailure(work: Work, error: AptosError) {
    work.result.complete(AptosResult.Failure(error))
    mutableEvents.emit(TransactionWorkerEvent.Failed(work.requestId, error))
  }

  companion object {
    /** Default maximum number of queued submissions. */
    const val DEFAULT_CAPACITY = 64

    private fun defaultSequenceMismatch(error: AptosError): Boolean {
      if (error !is AptosError.Api) return false
      val text = "${error.errorCode} ${error.message}".lowercase()
      return "sequence_number" in text || "sequence number" in text
    }
  }
}
