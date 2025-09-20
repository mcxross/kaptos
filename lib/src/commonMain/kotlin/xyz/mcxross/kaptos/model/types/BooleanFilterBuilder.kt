package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Boolean_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class BooleanFilterBuilder {
  var eq: Boolean? = null
  var gt: Boolean? = null
  var gte: Boolean? = null
  var inList: List<Boolean>? = null
  var isNull: Boolean? = null
  var lt: Boolean? = null
  var lte: Boolean? = null
  var neq: Boolean? = null
  var nin: List<Boolean>? = null

  internal fun build(): Boolean_comparison_exp =
      Boolean_comparison_exp(
          _eq = eq.toOptional(),
          _gt = gt.toOptional(),
          _gte = gte.toOptional(),
          _in = inList.toOptional(),
          _is_null = isNull.toOptional(),
          _lt = lt.toOptional(),
          _lte = lte.toOptional(),
          _neq = neq.toOptional(),
          _nin = nin.toOptional())
}

fun booleanFilter(init: BooleanFilterBuilder.() -> Unit): Boolean_comparison_exp =
    BooleanFilterBuilder().apply(init).build()
