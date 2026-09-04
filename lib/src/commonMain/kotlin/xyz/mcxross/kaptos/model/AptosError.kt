/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

/** Stable error categories exposed by the redesigned SDK API. */
sealed interface AptosError {
  val message: String
  val cause: Throwable?

  data class Transport(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Api(
    override val message: String,
    val errorCode: String,
    val vmErrorCode: Long? = null,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Indexer(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Validation(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Serialization(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Crypto(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Timeout(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class Cancelled(
    override val message: String,
    override val cause: Throwable? = null,
  ) : AptosError

  data class UnsupportedFeature(
    override val message: String,
    override val cause: Throwable? = null,
    val errorCode: String? = null,
    val vmErrorCode: Long? = null,
  ) : AptosError
}

/** Thrown only at configuration boundaries that cannot return an [AptosResult]. */
class AptosConfigurationException(val error: AptosError) :
  IllegalArgumentException(error.message, error.cause)
