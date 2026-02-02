package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Int_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class IntFilterBuilder {
  var eq: Int? = null
  var gt: Int? = null
  var gte: Int? = null
  var inList: List<Int>? = null
  var isNull: Boolean? = null
  var lt: Int? = null
  var lte: Int? = null
  var neq: Int? = null
  var nin: List<Int>? = null

  internal fun build(): Int_comparison_exp =
    Int_comparison_exp(
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

fun intFilter(init: IntFilterBuilder.() -> Unit): Int_comparison_exp =
  IntFilterBuilder().apply(init).build()
