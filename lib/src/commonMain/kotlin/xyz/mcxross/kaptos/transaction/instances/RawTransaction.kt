/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction.instances

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.transactionPayload
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

data class RawTransaction(
  val sender: AccountAddress,
  val sequenceNumber: ULong,
  val payload: TransactionPayload,
  val maxGasAmount: ULong,
  val gasUnitPrice: ULong,
  val expirationTimestampSecs: ULong,
  val chainId: ChainId,
) {
  fun toBcs(): ByteArray = AptosBcsWriter().also { encode(it) }.toByteArray()

  internal fun encode(writer: AptosBcsWriter) {
    writer.accountAddress(sender)
    writer.u64(sequenceNumber)
    writer.fixed(payload.toBcs())
    writer.u64(maxGasAmount)
    writer.u64(gasUnitPrice)
    writer.u64(expirationTimestampSecs)
    writer.u8(chainId.chainId)
  }

  companion object {
    fun fromBcs(bytes: ByteArray): RawTransaction =
      AptosBcsReader(bytes).let { reader ->
        reader.rawTransaction().also { reader.ensureFinished() }
      }

    internal fun AptosBcsReader.rawTransaction(): RawTransaction =
      RawTransaction(
        sender = accountAddress(),
        sequenceNumber = u64(),
        payload = transactionPayload(),
        maxGasAmount = u64(),
        gasUnitPrice = u64(),
        expirationTimestampSecs = u64(),
        chainId = ChainId(u8()),
      )
  }
}
