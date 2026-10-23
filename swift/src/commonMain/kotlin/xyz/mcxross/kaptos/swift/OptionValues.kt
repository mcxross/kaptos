/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.TransactionOptions

/** Avoids Beta2's NSNumber-to-nullable-unsigned input bridge. Swift conveniences accept UInt64?. */
object OptionValues {
  @Throws(Throwable::class)
  fun transaction(
    maxGasAmountDecimal: String? = null,
    gasUnitPriceDecimal: String? = null,
    expirationTimestampSecsDecimal: String? = null,
    expirationSecondsFromNowDecimal: String? = null,
    sequenceNumberDecimal: String? = null,
    nonceDecimal: String? = null,
  ): TransactionOptions {
    require(sequenceNumberDecimal == null || nonceDecimal == null) {
      "Choose sequence-number or nonce replay protection"
    }
    val replay =
      sequenceNumberDecimal?.let {
        ReplayProtection.SequenceNumber(unsigned(it, "sequenceNumber")!!)
      } ?: nonceDecimal?.let { ReplayProtection.Nonce(unsigned(it, "nonce")!!) }
    return TransactionOptions(
      maxGasAmount = unsigned(maxGasAmountDecimal, "maxGasAmount"),
      gasUnitPrice = unsigned(gasUnitPriceDecimal, "gasUnitPrice"),
      expirationTimestampSecs = unsigned(expirationTimestampSecsDecimal, "expirationTimestampSecs"),
      expirationSecondsFromNow =
        unsigned(expirationSecondsFromNowDecimal, "expirationSecondsFromNow"),
      replayProtection = replay,
    )
  }
}

internal fun unsigned(decimal: String?, name: String): ULong? = decimal?.let {
  it.toULongOrNull() ?: throw AptosException(AptosError.Validation("Invalid u64 $name"))
}
