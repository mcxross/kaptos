package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Order_by
import xyz.mcxross.kaptos.util.toOptional

enum class OrderBy(internal val generated: Order_by) {
  ASC(Order_by.asc),
  ASC_NULLS_FIRST(Order_by.asc_nulls_first),
  ASC_NULLS_LAST(Order_by.asc_nulls_last),
  DESC(Order_by.desc),
  DESC_NULLS_FIRST(Order_by.desc_nulls_first),
  DESC_NULLS_LAST(Order_by.desc_nulls_last);

  fun optional() = generated.toOptional()
}
