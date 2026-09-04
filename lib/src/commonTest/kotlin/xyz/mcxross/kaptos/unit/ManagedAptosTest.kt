/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.model.Network

class ManagedAptosTest :
  StringSpec({
    "managed workflows clear SDK-owned accounts" {
      lateinit var managedAccount: Account

      aptos(AptosConfig(network = Network.LOCAL)) {
        managedAccount = account()
      }

      managedAccount.isPrivateKeyCleared.shouldBeTrue()
    }

    "managed workflows clear accounts when the operation fails" {
      lateinit var managedAccount: Account

      shouldThrow<IllegalStateException> {
        aptos(AptosConfig(network = Network.LOCAL)) {
          managedAccount = account()
          error("operation failed")
        }
      }

      managedAccount.isPrivateKeyCleared.shouldBeTrue()
    }

    "account factories transfer private-key ownership to the client" {
      val privateKey = Ed25519PrivateKey.generate()
      val client = Aptos(AptosConfig(network = Network.LOCAL))

      client.account(privateKey)
      client.close()
      client.close()

      privateKey.isCleared.shouldBeTrue()
    }

    "owned resources close in reverse registration order" {
      val closed = mutableListOf<Int>()
      val client = Aptos(AptosConfig(network = Network.LOCAL))
      client.own(RecordingResource(1, closed))
      client.own(RecordingResource(2, closed))

      client.close()

      closed.shouldContainExactly(2, 1)
    }

    "resources offered to a closed client are closed immediately" {
      val closed = mutableListOf<Int>()
      val client = Aptos(AptosConfig(network = Network.LOCAL))
      client.close()

      shouldThrow<IllegalStateException> { client.own(RecordingResource(1, closed)) }

      closed.shouldContainExactly(1)
    }
  })

private class RecordingResource(
  private val id: Int,
  private val closed: MutableList<Int>,
) : AutoCloseable {
  override fun close() {
    closed += id
  }
}
