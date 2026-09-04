/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import xyz.mcxross.kaptos.faucet.DefaultFaucetService
import xyz.mcxross.kaptos.faucet.FaucetDataSource
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult

class FaucetServiceTest :
  StringSpec({
    "passes a validated unsigned amount to the transport" {
    var requestedAmount: Long? = null
    val service =
      DefaultFaucetService(
        FaucetDataSource { _, amount, _ ->
          requestedAmount = amount
          AptosResult.Failure(AptosError.Transport("fixture"))
        }
      )

      service.fund(AccountAddress.fromString("0x1"), 100_000_000uL)

      requestedAmount shouldBe 100_000_000L
    }

    "rejects amounts outside the faucet range before transport" {
      var called = false
      val service =
        DefaultFaucetService(
          FaucetDataSource { _, _, _ ->
            called = true
            error("must not be called")
          }
        )

      val result = service.fund(AccountAddress.fromString("0x1"), ULong.MAX_VALUE)

      called shouldBe false
      result.shouldBeInstanceOf<AptosResult.Failure>().error
        .shouldBeInstanceOf<AptosError.Validation>()
    }
  })
