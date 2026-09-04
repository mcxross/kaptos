/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

/** Kotlin-native offset pagination shared by indexer-backed services. */
data class PageRequest(
  val offset: Int = 0,
  val limit: Int = 20,
) {
  init {
    require(offset >= 0) { "offset must not be negative" }
    require(limit in 1..1_000) { "limit must be between 1 and 1000" }
  }
}

/** A page of domain models with a stable aggregate count. */
data class AptosPage<out T>(
  val items: List<T>,
  val totalCount: Int,
  val request: PageRequest,
) {
  val hasNextPage: Boolean
    get() = request.offset.toLong() + items.size.toLong() < totalCount.toLong()
}

/** An offset page for indexer datasets that do not expose an aggregate-count field. */
data class AptosSlice<out T>(
  val items: List<T>,
  val request: PageRequest,
) {
  val hasNextPage: Boolean
    get() = items.size == request.limit
}
