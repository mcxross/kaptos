package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class NumActiveDelegatorPerPoolFilterBuilder {
  private val andConditions = mutableListOf<Num_active_delegator_per_pool_bool_exp>()
  private val orConditions = mutableListOf<Num_active_delegator_per_pool_bool_exp>()
  private var notCondition: Num_active_delegator_per_pool_bool_exp? = null

  var numActiveDelegator: Bigint_comparison_exp? = null
  var poolAddress: String_comparison_exp? = null

  fun and(block: NumActiveDelegatorPerPoolFilterBuilder.() -> Unit) {
    andConditions += NumActiveDelegatorPerPoolFilterBuilder().apply(block).build()
  }

  fun or(block: NumActiveDelegatorPerPoolFilterBuilder.() -> Unit) {
    orConditions += NumActiveDelegatorPerPoolFilterBuilder().apply(block).build()
  }

  fun not(block: NumActiveDelegatorPerPoolFilterBuilder.() -> Unit) {
    notCondition = NumActiveDelegatorPerPoolFilterBuilder().apply(block).build()
  }

  internal fun build(): Num_active_delegator_per_pool_bool_exp =
    Num_active_delegator_per_pool_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      num_active_delegator = numActiveDelegator.toOptional(),
      pool_address = poolAddress.toOptional(),
    )
}

fun numActiveDelegatorPerPoolFilter(
  init: NumActiveDelegatorPerPoolFilterBuilder.() -> Unit
): Num_active_delegator_per_pool_bool_exp =
  NumActiveDelegatorPerPoolFilterBuilder().apply(init).build()
