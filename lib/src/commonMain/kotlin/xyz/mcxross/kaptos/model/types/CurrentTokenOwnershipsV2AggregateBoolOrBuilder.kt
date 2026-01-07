package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Boolean_comparison_exp
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_aggregate_bool_exp_bool_or
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_select_column_current_token_ownerships_v2_aggregate_bool_exp_bool_or_arguments_columns
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenOwnershipsV2AggregateBoolOrBuilder(
  private val arguments:
    Current_token_ownerships_v2_select_column_current_token_ownerships_v2_aggregate_bool_exp_bool_or_arguments_columns,
  private val predicate: Boolean_comparison_exp,
) {
  var distinct: Boolean? = null
  private var filter: Current_token_ownerships_v2_bool_exp? = null

  fun filter(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    this.filter = currentTokenOwnershipsV2Filter(block)
  }

  internal fun build(): Current_token_ownerships_v2_aggregate_bool_exp_bool_or =
    Current_token_ownerships_v2_aggregate_bool_exp_bool_or(
      arguments = arguments,
      predicate = predicate,
      distinct = distinct.toOptional(),
      filter = filter.toOptional(),
    )
}
