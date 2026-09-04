/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult

@RunWith(AndroidJUnit4::class)
class AndroidConfidentialCryptoSmokeTest {
  @Test
  fun nativeBackendLoadsAndBuildsRegistrationProof() {
    val key = ConfidentialDecryptionKey.generate()
    val result = ConfidentialProofFactory.registration(key, address(1), address(2), 2u)
    assertTrue(result is AptosResult.Success)
    val proof = (result as AptosResult.Success).value

    assertEquals(32, proof.publicKey.toByteArray().size)
    assertEquals(1, proof.sigma.commitment.size)
    assertEquals(1, proof.sigma.response.size)

    key.clear()
  }

  private fun address(last: Int): AccountAddress =
    AccountAddress(ByteArray(32).also { it[31] = last.toByte() })
}
