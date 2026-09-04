/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import xyz.mcxross.kaptos.account.Ed25519Account
import xyz.mcxross.kaptos.model.HexInput

@RunWith(AndroidJUnit4::class)
class AndroidCryptoSmokeTest {
  @Test
  fun nativeBackendLoadsAndSigns() {
    val account = Ed25519Account.generate()
    val message = HexInput.fromByteArray("kaptos-android-smoke".encodeToByteArray())
    val signature = account.sign(message)

    assertTrue(account.verifySignature(message, signature))

    account.clearPrivateKey()
    assertTrue(account.isPrivateKeyCleared)
  }
}
