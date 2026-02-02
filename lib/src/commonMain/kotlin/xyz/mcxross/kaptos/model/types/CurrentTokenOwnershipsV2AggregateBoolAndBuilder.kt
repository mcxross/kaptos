package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenOwnershipsV2AggregateBoolAndBuilder(
  private val arguments:
    Current_token_ownerships_v2_select_column_current_token_ownerships_v2_aggregate_bool_exp_bool_and_arguments_columns,
  private val predicate: Boolean_comparison_exp,
) {
  var distinct: Boolean? = null
  private var filter: Current_token_ownerships_v2_bool_exp? = null

  fun filter(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    this.filter = currentTokenOwnershipsV2Filter(block)
  }

  internal fun build(): Current_token_ownerships_v2_aggregate_bool_exp_bool_and =
    Current_token_ownerships_v2_aggregate_bool_exp_bool_and(
      arguments = arguments,
      predicate = predicate,
      distinct = distinct.toOptional(),
      filter = filter.toOptional(),
    )
}

fun currentTokenOwnershipsV2AggregateBoolAnd(
  arguments:
    Current_token_ownerships_v2_select_column_current_token_ownerships_v2_aggregate_bool_exp_bool_and_arguments_columns,
  predicate: Boolean_comparison_exp,
  init: CurrentTokenOwnershipsV2AggregateBoolAndBuilder.() -> Unit = {},
): Current_token_ownerships_v2_aggregate_bool_exp_bool_and =
  CurrentTokenOwnershipsV2AggregateBoolAndBuilder(arguments, predicate).apply(init).build()
