/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction.instances

import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

/** The exact `0x1::account::RotationProofChallenge` wire representation. */
class RotationProofChallenge(
  val sequenceNumber: ULong,
  val originator: AccountAddress,
  val currentAuthenticationKey: AccountAddress,
  val newPublicKey: AccountPublicKey,
) {
  fun toBcs(): ByteArray =
    AptosBcsWriter()
      .also { writer ->
        writer.accountAddress(AccountAddress.ONE)
        writer.string("account")
        writer.string("RotationProofChallenge")
        writer.u64(sequenceNumber)
        writer.accountAddress(originator)
        writer.accountAddress(currentAuthenticationKey)
        writer.bytes(newPublicKey.toByteArray())
      }
      .toByteArray()
}
