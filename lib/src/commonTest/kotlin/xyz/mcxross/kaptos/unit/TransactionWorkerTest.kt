/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.CompletableDeferred
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.AccountSequenceManager
import xyz.mcxross.kaptos.transaction.TransactionWorker
import xyz.mcxross.kaptos.transaction.TransactionWorkerShutdown
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.util.runBlocking

class TransactionWorkerTest {
  @Test
  fun sequenceManagerReservesUniqueContiguousNumbers() = runBlocking {
    val manager =
      AccountSequenceManager(AccountAddress.ONE) { AptosResult.Success(40uL) }

    val reserved =
      (0..<20).map { async { assertIs<AptosResult.Success<ULong>>(manager.reserve()).value } }
        .awaitAll()
        .sorted()

    assertEquals((40uL..<60uL).toList(), reserved)
    assertEquals(60uL, manager.peek())
  }

  @Test
  fun workerResynchronizesAndRetriesOneSequenceMismatch() = runBlocking {
    var fetchCount = 0
    val builtWith = mutableListOf<ULong>()
    var submitCount = 0
    val manager =
      AccountSequenceManager(AccountAddress.ONE) {
        fetchCount++
        AptosResult.Success(if (fetchCount == 1) 10uL else 20uL)
      }
    val worker =
      TransactionWorker(
        scope = this,
        sequenceManager = manager,
        capacity = 2,
        submitTransaction = { transaction ->
          submitCount++
          if (submitCount == 1) {
            AptosResult.Failure(
              AptosError.Api("Sequence number is too old", "sequence_number_too_old")
            )
          } else {
            AptosResult.Success(pending(transaction.rawTransaction.sequenceNumber))
          }
        },
      )

    val result =
      worker.submit { sequenceNumber ->
        builtWith += sequenceNumber
        AptosResult.Success(UnsignedTransaction.Simple(raw(sequenceNumber)))
      }.await()

    val submitted = assertIs<AptosResult.Success<PendingTransactionResponse>>(result).value
    assertEquals("20", submitted.sequenceNumber)
    assertEquals(listOf(10uL, 20uL), builtWith)
    assertEquals(2, fetchCount)
    worker.shutdown()
  }

  @Test
  fun cancellationCompletesActiveAndQueuedSubmissions() = runBlocking {
    val manager =
      AccountSequenceManager(AccountAddress.ONE) { AptosResult.Success(0uL) }
    val buildStarted = CompletableDeferred<Unit>()
    val continueBuild = CompletableDeferred<Unit>()
    val worker =
      TransactionWorker(
        scope = this,
        sequenceManager = manager,
        capacity = 1,
        submitTransaction = { AptosResult.Success(pending(it.rawTransaction.sequenceNumber)) },
      )

    val active =
      worker.submit { sequenceNumber ->
        buildStarted.complete(Unit)
        continueBuild.await()
        AptosResult.Success(UnsignedTransaction.Simple(raw(sequenceNumber)))
      }
    buildStarted.await()
    val queued = worker.submit { sequenceNumber ->
      AptosResult.Success(UnsignedTransaction.Simple(raw(sequenceNumber)))
    }

    worker.shutdown(TransactionWorkerShutdown.Cancel)

    assertIs<AptosError.Cancelled>(assertIs<AptosResult.Failure>(active.await()).error)
    assertIs<AptosError.Cancelled>(assertIs<AptosResult.Failure>(queued.await()).error)
  }

  private fun raw(sequenceNumber: ULong): RawTransaction =
    RawTransaction(
      sender = AccountAddress.ONE,
      sequenceNumber = sequenceNumber,
      payload = TransactionPayload.entryFunction("0x1::coin::transfer"),
      maxGasAmount = 2_000uL,
      gasUnitPrice = 1uL,
      expirationTimestampSecs = 100uL,
      chainId = ChainId(4u),
    )

  private fun pending(sequenceNumber: ULong): PendingTransactionResponse =
    PendingTransactionResponse(
      hash = "0x${sequenceNumber}",
      sender = AccountAddress.ONE.toString(),
      sequenceNumber = sequenceNumber.toString(),
      maxGasAmount = "2000",
      gasUnitPrice = "1",
      expirationTimestampSecs = "100",
    )
}
