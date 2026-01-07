package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Bigint_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class BigintFilterBuilder {
  var eq: Any? = null
  var gt: Any? = null
  var gte: Any? = null
  var inList: List<Any>? = null
  var isNull: Boolean? = null
  var lt: Any? = null
  var lte: Any? = null
  var neq: Any? = null
  var nin: List<Any>? = null

  internal fun build(): Bigint_comparison_exp =
    Bigint_comparison_exp(
      _eq = eq.toOptional(),
      _gt = gt.toOptional(),
      _gte = gte.toOptional(),
      _in = inList.toOptional(),
      _is_null = isNull.toOptional(),
      _lt = lt.toOptional(),
      _lte = lte.toOptional(),
      _neq = neq.toOptional(),
      _nin = nin.toOptional(),
    )
}

fun bigintFilter(init: BigintFilterBuilder.() -> Unit): Bigint_comparison_exp =
  BigintFilterBuilder().apply(init).build()
