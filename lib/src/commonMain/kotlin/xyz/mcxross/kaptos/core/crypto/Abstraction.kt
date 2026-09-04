/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

/** Opaque authentication data produced for an on-chain abstraction function. */
class AbstractSignature(value: ByteArray) : Signature() {
  private val bytes = value.copyOf()

  override fun toByteArray(): ByteArray = bytes.copyOf()

  override fun toBcs(): ByteArray = AptosBcsWriter().also { it.bytes(bytes) }.toByteArray()

  override fun equals(other: Any?): Boolean =
    other is AbstractSignature && bytes.contentEquals(other.bytes)

  override fun hashCode(): Int = bytes.contentHashCode()

  override fun toString(): String = "AbstractSignature(${bytes.size} bytes)"
}

/**
 * Address-bound public identity used by account-abstraction signers.
 *
 * Aptos verifies this identity through the account's on-chain authentication function. Its BCS
 * representation is therefore the opaque account-address bytes, encoded as a byte vector, rather
 * than one of the native cryptographic-key variants.
 */
class AbstractPublicKey(val accountAddress: AccountAddress) : AccountPublicKey() {
  override fun authKey(): AuthenticationKey =
    AuthenticationKey(HexInput.fromByteArray(accountAddress.data))

  override fun verifySignature(message: HexInput, signature: Signature): Boolean = false

  override fun toByteArray(): ByteArray = accountAddress.data.copyOf()

  override fun toBcs(): ByteArray =
    AptosBcsWriter().also { it.bytes(accountAddress.data) }.toByteArray()
}
