/*
 * Copyright 2026 McXross
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.internal

import com.github.michaelbull.result.Result as TransportResult
import com.github.michaelbull.result.fold
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Result

/** Cancellation is control flow and must never become a recoverable SDK failure. */
internal fun Throwable.rethrowCancellation() {
  if (this is CancellationException) throw this
}

/** One classification boundary for exceptions escaping internal operations. */
internal fun Throwable.toAptosError(): AptosError {
  rethrowCancellation()
  return when (this) {
    is AptosSdkError -> toAptosError()
    is AptosIndexerError -> toAptosError()
    is SerializationException -> AptosError.Serialization(message ?: "Invalid Aptos response", this)
    is IllegalArgumentException -> AptosError.Validation(message ?: "Invalid Aptos request", this)
    else -> AptosError.Transport(message ?: "Aptos request failed", this)
  }
}

/** Runs an internal operation without intercepting cancellation or fatal runtime errors. */
internal suspend inline fun <T> executeAptos(block: () -> AptosResult<T>): AptosResult<T> =
  try {
    block()
  } catch (error: Exception) {
    AptosResult.Failure(error.toAptosError())
  }

/** Converts transport results directly, without an intermediate SDK result allocation. */
internal fun <T, E : Throwable> TransportResult<T, E>.toAptosResult(): AptosResult<T> =
  fold(success = { AptosResult.Success(it) }, failure = { AptosResult.Failure(it.toAptosError()) })

/** Adapts internal operations that still return the internal sealed result. */
internal fun <T, E : Throwable> Result<T, E>.toAptosResult(): AptosResult<T> =
  when (this) {
    is Result.Ok -> AptosResult.Success(value)
    is Result.Err -> AptosResult.Failure(error.toAptosError())
  }

/** Decodes a successful response; malformed response values are serialization failures. */
internal inline fun <T, R> AptosResult<T>.mapResponse(
  message: String,
  transform: (T) -> R,
): AptosResult<R> =
  when (this) {
    is AptosResult.Failure -> this
    is AptosResult.Success ->
      try {
        AptosResult.Success(transform(value))
      } catch (error: Exception) {
        error.rethrowCancellation()
        AptosResult.Failure(AptosError.Serialization(message, error))
      }
  }

internal fun AptosSdkError.toAptosError(): AptosError {
  cause?.rethrowCancellation()
  return when (this) {
    is AptosSdkError.Timeout -> AptosError.Timeout(message, this)
    is AptosSdkError.ApiError ->
      if (apiError.errorCode.lowercase() in setOf("feature_under_gating", "unsupported_feature")) {
        AptosError.UnsupportedFeature(
          message = apiError.message,
          cause = this,
          errorCode = apiError.errorCode,
          vmErrorCode = apiError.vmErrorCode,
        )
      } else {
        AptosError.Api(
          message = apiError.message,
          errorCode = apiError.errorCode,
          vmErrorCode = apiError.vmErrorCode,
          cause = this,
        )
      }
    is AptosSdkError.DeserializationError ->
      AptosError.Serialization(message ?: "Unable to deserialize Aptos response", cause)
    is AptosSdkError.NetworkError ->
      AptosError.Transport(message ?: "Network request failed", cause)
    is AptosSdkError.UnknownError -> AptosError.Transport(message ?: "Aptos request failed", cause)
  }
}

internal fun AptosIndexerError.toAptosError(): AptosError {
  cause?.rethrowCancellation()
  return AptosError.Indexer(message ?: "Indexer request failed", this)
}
