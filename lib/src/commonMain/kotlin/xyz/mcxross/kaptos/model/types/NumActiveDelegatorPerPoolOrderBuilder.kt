package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Num_active_delegator_per_pool_order_by
import xyz.mcxross.kaptos.util.toOptional

class NumActiveDelegatorPerPoolOrderBuilder {
  var numActiveDelegator: OrderBy? = null
  var poolAddress: OrderBy? = null

  internal fun build(): Num_active_delegator_per_pool_order_by =
    Num_active_delegator_per_pool_order_by(
      num_active_delegator = numActiveDelegator?.generated.toOptional(),
      pool_address = poolAddress?.generated.toOptional(),
    )
}

fun numActiveDelegatorPerPoolOrder(
  init: NumActiveDelegatorPerPoolOrderBuilder.() -> Unit
): Num_active_delegator_per_pool_order_by =
  NumActiveDelegatorPerPoolOrderBuilder().apply(init).build()
