/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlinx.coroutines.CancellationException
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.swift.AptosException
import xyz.mcxross.kaptos.swift.ErrorCategory
import xyz.mcxross.kaptos.swift.swiftValue

class SwiftResultTest {
  @Test
  fun preservesUnsignedAmountsAndNullableSuccess() {
    assertEquals(ULong.MAX_VALUE, AptosResult.Success(ULong.MAX_VALUE).swiftValue())
    assertNull(AptosResult.Success<String?>(null).swiftValue())
  }

  @Test
  fun preservesCategoriesAndAvailableApiCodes() {
    val errors =
      listOf(
        AptosError.Transport("transport") to ErrorCategory.Transport,
        AptosError.Api("api", "sequence_number_too_old", Long.MAX_VALUE) to ErrorCategory.Api,
        AptosError.Indexer("indexer") to ErrorCategory.Indexer,
        AptosError.Validation("validation") to ErrorCategory.Validation,
        AptosError.Serialization("serialization") to ErrorCategory.Serialization,
        AptosError.Crypto("crypto") to ErrorCategory.Crypto,
        AptosError.Timeout("timeout") to ErrorCategory.Timeout,
        AptosError.UnsupportedFeature("unsupported", errorCode = "unsupported", vmErrorCode = 7) to
          ErrorCategory.UnsupportedFeature,
      )
    errors.forEach { (error, category) ->
      val thrown = assertFailsWith<AptosException> { AptosResult.Failure(error).swiftValue() }
      assertEquals(category, thrown.category)
      assertEquals(error.message, thrown.message)
      when (error) {
        is AptosError.Api -> {
          assertEquals(error.errorCode, thrown.apiCode)
          assertEquals(error.vmErrorCode, thrown.vmCode)
        }
        is AptosError.UnsupportedFeature -> {
          assertEquals(error.errorCode, thrown.apiCode)
          assertEquals(error.vmErrorCode, thrown.vmCode)
        }
        else -> {
          assertNull(thrown.apiCode)
          assertNull(thrown.vmCode)
        }
      }
    }
  }

  @Test
  fun propagatesCancellationAndRetainsCause() {
    val cause = CancellationException("cancelled by caller")
    val thrown =
      assertFailsWith<CancellationException> {
        AptosResult.Failure(AptosError.Cancelled("cancelled", cause)).swiftValue()
      }
    assertSame(cause, thrown.cause)
  }
}
