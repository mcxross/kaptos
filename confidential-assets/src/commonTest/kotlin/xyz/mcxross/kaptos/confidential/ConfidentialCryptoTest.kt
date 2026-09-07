/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult

class ConfidentialCryptoTest {
  @Test
  fun confidentialAmountsNormalizeCarriesAndRemainLosslessAt128Bits() {
    val unnormalized = ConfidentialAmount(listOf(65_536u, 1u, 0u, 0u, 0u, 0u, 0u, 0u))
    assertEquals("131072", unnormalized.decimal)
    assertEquals(listOf(0u, 2u, 0u, 0u, 0u, 0u, 0u, 0u), unnormalized.normalizedChunks)
    assertEquals(131_072uL, unnormalized.toULong())

    val maximum = ConfidentialAmount(List(8) { 65_535u })
    assertEquals("340282366920938463463374607431768211455", maximum.decimal)
    assertTrue(!maximum.fitsInULong())
  }

  @Test
  fun confidentialKeysAreClearableAndRejectUseAfterClear() {
    val key = ConfidentialDecryptionKey.generate()
    assertEquals(32, key.encryptionKey.toByteArray().size)

    key.clear()

    assertTrue(key.isCleared)
    assertFailsWith<IllegalStateException> { key.encryptionKey }
  }

  @Test
  fun kotlinProofFactoryCoversRegistrationWithdrawalTransferAndRotation() {
    val sender = address(1)
    val recipient = address(2)
    val token = address(3)
    val senderKey = ConfidentialDecryptionKey.generate()
    val recipientKey = ConfidentialDecryptionKey.generate()
    val voluntaryAuditor = ConfidentialDecryptionKey.generate().encryptionKey
    val effectiveAuditor = ConfidentialDecryptionKey.generate().encryptionKey
    val oldChunks = listOf(100u, 1u, 0u, 0u, 0u, 0u, 0u, 0u)
    val old =
      assertIs<AptosResult.Success<EncryptedChunks>>(
          encryptChunks(senderKey.encryptionKey, oldChunks)
        )
        .value
    val pending =
      assertIs<AptosResult.Success<EncryptedChunks>>(
          encryptChunks(senderKey.encryptionKey, List(8) { 0u })
        )
        .value
    val balance =
      ConfidentialBalance(
        available = old.ciphertexts,
        pending = pending.ciphertexts,
        availableAmount = ConfidentialAmount(oldChunks),
        pendingAmount = ConfidentialAmount(List(8) { 0u }),
      )

    val registration =
      assertIs<AptosResult.Success<RegistrationAuthorization>>(
          ConfidentialProofFactory.registration(senderKey, sender, token, 2u)
        )
        .value
    assertEquals(1, registration.sigma.response.size)

    val withdrawal =
      assertIs<AptosResult.Success<BalanceAuthorization>>(
          ConfidentialProofFactory.withdraw(
            key = senderKey,
            sender = sender,
            token = token,
            chainId = 2u,
            amount = 1u,
            balance = balance,
            auditor = effectiveAuditor,
          )
        )
        .value
    assertEquals(AVAILABLE_BALANCE_CHUNK_COUNT, withdrawal.newBalance.size)
    assertEquals(AVAILABLE_BALANCE_CHUNK_COUNT, withdrawal.newAuditorHandles.size)
    assertTrue(withdrawal.rangeProof.isNotEmpty())

    val transfer =
      assertIs<AptosResult.Success<TransferAuthorization>>(
          ConfidentialProofFactory.transfer(
            key = senderKey,
            sender = sender,
            recipient = recipient,
            token = token,
            chainId = 2u,
            amount = 10u,
            balance = balance,
            recipientKey = recipientKey.encryptionKey,
            voluntaryAuditors = listOf(voluntaryAuditor),
            effectiveAuditor = effectiveAuditor,
          )
        )
        .value
    assertEquals(AVAILABLE_BALANCE_CHUNK_COUNT, transfer.newBalance.size)
    assertEquals(TRANSFER_AMOUNT_CHUNK_COUNT, transfer.transferBySender.size)
    assertEquals(1, transfer.voluntaryAuditorTransferHandles.size)
    assertEquals(TRANSFER_AMOUNT_CHUNK_COUNT, transfer.effectiveAuditorTransferHandles.size)

    val nextKey = ConfidentialDecryptionKey.generate()
    val rotation =
      assertIs<AptosResult.Success<KeyRotationAuthorization>>(
          ConfidentialProofFactory.rotate(senderKey, nextKey, sender, token, 2u, balance)
        )
        .value
    assertTrue(
      rotation.newPublicKey.toByteArray().contentEquals(nextKey.encryptionKey.toByteArray())
    )
    assertEquals(AVAILABLE_BALANCE_CHUNK_COUNT, rotation.newHandles.size)
  }

  private fun address(last: Int): AccountAddress =
    AccountAddress(ByteArray(32).also { it[31] = last.toByte() })
}
