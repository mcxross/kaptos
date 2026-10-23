/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import xyz.mcxross.kaptos.swift.Client
import xyz.mcxross.kaptos.swift.Configuration

class ClientLifecycleTest {
  @Test
  fun closedClientCannotStartWorker() {
    val client = Client(Configuration())
    val account = client.generateAccount()
    client.close()
    assertTrue(account.isPrivateKeyCleared)
    assertFailsWith<IllegalStateException> { client.worker(account) }
    client.close()
  }

  @Test
  fun asynchronousCloseStopsWorkersBeforeClearingKeys() = runTest {
    val client = Client(Configuration())
    val account = client.generateAccount()
    val worker = client.worker(account)
    assertFailsWith<IllegalStateException> { client.close() }
    assertTrue(!account.isPrivateKeyCleared)
    client.closeAsync()
    assertTrue(worker.isStopped)
    assertTrue(account.isPrivateKeyCleared)
    assertFailsWith<IllegalStateException> { client.worker(account) }
    client.close()
  }
}
