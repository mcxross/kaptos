/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.AccountSequenceManager
import xyz.mcxross.kaptos.transaction.TransactionWorker
import xyz.mcxross.kaptos.transaction.TransactionWorkerEvent
import xyz.mcxross.kaptos.transaction.TransactionWorkerShutdown

enum class WorkerEventKind {
  Queued,
  Building,
  Submitted,
  Failed,
  Cancelled,
  Drained,
}

/** Nullable fields are populated only for the corresponding event kind. */
data class WorkerEvent
internal constructor(
  val kind: WorkerEventKind,
  val requestId: ULong?,
  val sequenceNumber: ULong? = null,
  val transaction: PendingTransactionResponse? = null,
  val error: AptosException? = null,
)

/** Await cancellation stops observing a request; queued work keeps its own lifetime. */
class Submission
internal constructor(private val result: Deferred<AptosResult<PendingTransactionResponse>>) {
  @Throws(Throwable::class)
  suspend fun await(): PendingTransactionResponse = result.await().swiftValue()
}

/** A client-owned pipeline. Call shutdown before closing its client or clearing its signer. */
class Worker internal constructor(aptos: Aptos, signer: TransactionSigner, capacity: Int) {
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  private val delegate =
    TransactionWorker(
      scope,
      AccountSequenceManager(signer.accountAddress) { address ->
        when (val account = aptos.accounts.get(address)) {
          is AptosResult.Success -> AptosResult.Success(account.value.sequenceNumber)
          is AptosResult.Failure -> account
        }
      },
      capacity,
      { aptos.transactions.signAndSubmit(signer, it) },
    )
  internal var isStopped: Boolean = false
    private set

  /** Swift export maps this flow to KotlinTypedFlow and asAsyncSequence(). */
  val events: Flow<WorkerEvent> =
    delegate.events.map { event ->
      when (event) {
        is TransactionWorkerEvent.Queued -> WorkerEvent(WorkerEventKind.Queued, event.requestId)
        is TransactionWorkerEvent.Building ->
          WorkerEvent(WorkerEventKind.Building, event.requestId, event.sequenceNumber)
        is TransactionWorkerEvent.Submitted ->
          WorkerEvent(
            WorkerEventKind.Submitted,
            event.requestId,
            event.sequenceNumber,
            event.transaction,
          )
        is TransactionWorkerEvent.Failed ->
          if (event.error is AptosError.Cancelled)
            WorkerEvent(WorkerEventKind.Cancelled, event.requestId)
          else
            WorkerEvent(
              WorkerEventKind.Failed,
              event.requestId,
              error = AptosException(event.error),
            )
        is TransactionWorkerEvent.Cancelled ->
          WorkerEvent(WorkerEventKind.Cancelled, event.requestId)
        TransactionWorkerEvent.Drained -> WorkerEvent(WorkerEventKind.Drained, null)
      }
    }

  @Throws(Throwable::class)
  suspend fun submit(build: suspend (ULong) -> UnsignedTransaction): Submission =
    Submission(delegate.submit { sequence -> signingResult { build(sequence) } })

  @Throws(Throwable::class)
  suspend fun shutdown(cancelPending: Boolean = false) {
    try {
      withContext(NonCancellable) {
        delegate.shutdown(
          if (cancelPending) TransactionWorkerShutdown.Cancel else TransactionWorkerShutdown.Drain
        )
      }
    } finally {
      scope.cancel()
      isStopped = true
    }
  }
}
