/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

/** The public, typed outcome of a Kaptos operation. */
sealed interface AptosResult<out T> {
  data class Success<out T>(val value: T) : AptosResult<T>

  data class Failure(val error: AptosError) : AptosResult<Nothing>

  val isSuccess: Boolean
    get() = this is Success

  val isFailure: Boolean
    get() = this is Failure
}

/** Transforms a successful value while retaining a failure unchanged. */
inline fun <T, R> AptosResult<T>.map(transform: (T) -> R): AptosResult<R> =
  when (this) {
    is AptosResult.Success -> AptosResult.Success(transform(value))
    is AptosResult.Failure -> this
  }

/** Chains another typed Aptos operation after a successful value. */
inline fun <T, R> AptosResult<T>.flatMap(transform: (T) -> AptosResult<R>): AptosResult<R> =
  when (this) {
    is AptosResult.Success -> transform(value)
    is AptosResult.Failure -> this
  }

/** Returns the success value, or null for a failure. */
fun <T> AptosResult<T>.getOrNull(): T? = (this as? AptosResult.Success)?.value

/** Returns the failure, or null for success. */
fun <T> AptosResult<T>.errorOrNull(): AptosError? = (this as? AptosResult.Failure)?.error

/** Reduces either branch to one caller-defined result type. */
inline fun <T, R> AptosResult<T>.fold(
  onSuccess: (T) -> R,
  onFailure: (AptosError) -> R,
): R =
  when (this) {
    is AptosResult.Success -> onSuccess(value)
    is AptosResult.Failure -> onFailure(error)
  }

/** Returns the success value or derives a fallback from the typed error. */
inline fun <T> AptosResult<T>.getOrElse(defaultValue: (AptosError) -> T): T =
  when (this) {
    is AptosResult.Success -> value
    is AptosResult.Failure -> defaultValue(error)
  }

/** Performs [action] for success and returns this result unchanged. */
inline fun <T> AptosResult<T>.onSuccess(action: (T) -> Unit): AptosResult<T> = apply {
  if (this is AptosResult.Success) action(value)
}

/** Performs [action] for failure and returns this result unchanged. */
inline fun <T> AptosResult<T>.onFailure(action: (AptosError) -> Unit): AptosResult<T> = apply {
  if (this is AptosResult.Failure) action(error)
}
