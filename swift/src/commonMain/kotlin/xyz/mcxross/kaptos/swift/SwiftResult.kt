/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import kotlinx.coroutines.CancellationException
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult

/** Stable categories shared with the Kotlin result API. */
enum class ErrorCategory {
  Transport,
  Api,
  Indexer,
  Validation,
  Serialization,
  Crypto,
  Timeout,
  UnsupportedFeature,
}

/** A concrete exported exception so Swift can catch errors without inspecting erased generics. */
class AptosException internal constructor(error: AptosError) :
  RuntimeException(error.message, error.cause) {
  val category: ErrorCategory =
    when (error) {
      is AptosError.Transport -> ErrorCategory.Transport
      is AptosError.Api -> ErrorCategory.Api
      is AptosError.Indexer -> ErrorCategory.Indexer
      is AptosError.Validation -> ErrorCategory.Validation
      is AptosError.Serialization -> ErrorCategory.Serialization
      is AptosError.Crypto -> ErrorCategory.Crypto
      is AptosError.Timeout -> ErrorCategory.Timeout
      is AptosError.UnsupportedFeature -> ErrorCategory.UnsupportedFeature
      is AptosError.Cancelled -> error("Cancellation must propagate as CancellationException")
    }

  val apiCode: String? =
    when (error) {
      is AptosError.Api -> error.errorCode
      is AptosError.UnsupportedFeature -> error.errorCode
      else -> null
    }

  val vmCode: Long? =
    when (error) {
      is AptosError.Api -> error.vmErrorCode
      is AptosError.UnsupportedFeature -> error.vmErrorCode
      else -> null
    }
}

internal fun <T> AptosResult<T>.swiftValue(): T =
  when (this) {
    is AptosResult.Success -> value
    is AptosResult.Failure -> {
      if (error is AptosError.Cancelled) {
        throw CancellationException(error.message, error.cause)
      }
      throw AptosException(error)
    }
  }
