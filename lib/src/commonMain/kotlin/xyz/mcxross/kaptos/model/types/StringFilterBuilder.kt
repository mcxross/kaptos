package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.String_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class StringFilterBuilder {
  var eq: String? = null
  var gt: String? = null
  var gte: String? = null
  var ilike: String? = null
  var inList: List<String>? = null
  var iregex: String? = null
  var isNull: Boolean? = null
  var like: String? = null
  var lt: String? = null
  var lte: String? = null
  var neq: String? = null
  var nilike: String? = null
  var nin: List<String>? = null
  var niregex: String? = null
  var nlike: String? = null
  var nregex: String? = null
  var nsimilar: String? = null
  var regex: String? = null
  var similar: String? = null

  internal fun build(): String_comparison_exp =
      String_comparison_exp(
          _eq = eq.toOptional(),
          _gt = gt.toOptional(),
          _gte = gte.toOptional(),
          _ilike = ilike.toOptional(),
          _in = inList.toOptional(),
          _iregex = iregex.toOptional(),
          _is_null = isNull.toOptional(),
          _like = like.toOptional(),
          _lt = lt.toOptional(),
          _lte = lte.toOptional(),
          _neq = neq.toOptional(),
          _nilike = nilike.toOptional(),
          _nin = nin.toOptional(),
          _niregex = niregex.toOptional(),
          _nlike = nlike.toOptional(),
          _nregex = nregex.toOptional(),
          _nsimilar = nsimilar.toOptional(),
          _regex = regex.toOptional(),
          _similar = similar.toOptional(),
      )
}

fun stringFilter(init: StringFilterBuilder.() -> Unit): String_comparison_exp =
    StringFilterBuilder().apply(init).build()
