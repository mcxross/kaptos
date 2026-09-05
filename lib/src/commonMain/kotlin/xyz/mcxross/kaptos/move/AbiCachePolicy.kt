package xyz.mcxross.kaptos.move

/** Limits fetched ABI staleness and memory use. Preloads are explicitly pinned by the caller. */
data class AbiCachePolicy(val ttlMillis: Long = 60_000, val maxEntries: Int = 128) {
  init {
    require(ttlMillis >= 0) { "ABI cache TTL must be non-negative; zero disables fetched caching" }
    require(maxEntries > 0) { "ABI cache capacity must be positive" }
  }
}
