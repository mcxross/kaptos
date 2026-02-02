package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Jsonb_cast_exp
import xyz.mcxross.kaptos.generated.type.Jsonb_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class JsonbFilterBuilder {
  var cast: Jsonb_cast_exp? = null
  var containedIn: Any? = null
  var contains: Any? = null
  var eq: Any? = null
  var gt: Any? = null
  var gte: Any? = null
  var hasKey: String? = null
  var hasKeysAll: List<String>? = null
  var hasKeysAny: List<String>? = null
  var inList: List<Any>? = null
  var isNull: Boolean? = null
  var lt: Any? = null
  var lte: Any? = null
  var neq: Any? = null
  var nin: List<Any>? = null

  internal fun build(): Jsonb_comparison_exp =
    Jsonb_comparison_exp(
      _cast = cast.toOptional(),
      _contained_in = containedIn.toOptional(),
      _contains = contains.toOptional(),
      _eq = eq.toOptional(),
      _gt = gt.toOptional(),
      _gte = gte.toOptional(),
      _has_key = hasKey.toOptional(),
      _has_keys_all = hasKeysAll.toOptional(),
      _has_keys_any = hasKeysAny.toOptional(),
      _in = inList.toOptional(),
      _is_null = isNull.toOptional(),
      _lt = lt.toOptional(),
      _lte = lte.toOptional(),
      _neq = neq.toOptional(),
      _nin = nin.toOptional(),
    )
}

fun jsonbFilter(init: JsonbFilterBuilder.() -> Unit): Jsonb_comparison_exp =
  JsonbFilterBuilder().apply(init).build()
