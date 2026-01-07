package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenOwnershipsV2AggregateFilterBuilder {
  private var count: Current_token_ownerships_v2_aggregate_bool_exp_count? = null
  private var boolAnd: Current_token_ownerships_v2_aggregate_bool_exp_bool_and? = null
  private var boolOr: Current_token_ownerships_v2_aggregate_bool_exp_bool_or? = null

  fun count(
    predicate: Int_comparison_exp,
    block: CurrentTokenOwnershipsV2AggregateBoolExpCountBuilder.() -> Unit = {},
  ) {
    this.count =
      CurrentTokenOwnershipsV2AggregateBoolExpCountBuilder(predicate).apply(block).build()
  }

  fun boolAnd(
    arguments:
      Current_token_ownerships_v2_select_column_current_token_ownerships_v2_aggregate_bool_exp_bool_and_arguments_columns,
    predicate: Boolean_comparison_exp,
    block: CurrentTokenOwnershipsV2AggregateBoolAndBuilder.() -> Unit = {},
  ) {
    this.boolAnd =
      CurrentTokenOwnershipsV2AggregateBoolAndBuilder(arguments, predicate).apply(block).build()
  }

  fun boolOr(
    arguments:
      Current_token_ownerships_v2_select_column_current_token_ownerships_v2_aggregate_bool_exp_bool_or_arguments_columns,
    predicate: Boolean_comparison_exp,
    block: CurrentTokenOwnershipsV2AggregateBoolOrBuilder.() -> Unit = {},
  ) {
    this.boolOr =
      CurrentTokenOwnershipsV2AggregateBoolOrBuilder(arguments, predicate).apply(block).build()
  }

  internal fun build(): Current_token_ownerships_v2_aggregate_bool_exp =
    Current_token_ownerships_v2_aggregate_bool_exp(
      count = count.toOptional(),
      bool_and = boolAnd.toOptional(),
      bool_or = boolOr.toOptional(),
    )
}

fun currentTokenOwnershipsV2AggregateFilter(
  init: CurrentTokenOwnershipsV2AggregateFilterBuilder.() -> Unit
): Current_token_ownerships_v2_aggregate_bool_exp =
  CurrentTokenOwnershipsV2AggregateFilterBuilder().apply(init).build()
