package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentAptosNamesAggregateFilterBuilder {
  private var count: Current_aptos_names_aggregate_bool_exp_count? = null
  private var boolAnd: Current_aptos_names_aggregate_bool_exp_bool_and? = null
  private var boolOr: Current_aptos_names_aggregate_bool_exp_bool_or? = null

  fun count(
      predicate: Int_comparison_exp,
      block: CurrentAptosNamesAggregateBoolExpCountBuilder.() -> Unit = {}
  ) {
    this.count = CurrentAptosNamesAggregateBoolExpCountBuilder(predicate).apply(block).build()
  }

  fun boolAnd(
      arguments:
          Current_aptos_names_select_column_current_aptos_names_aggregate_bool_exp_bool_and_arguments_columns,
      predicate: Boolean_comparison_exp,
      block: CurrentAptosNamesAggregateBoolAndBuilder.() -> Unit = {}
  ) {
    this.boolAnd =
        CurrentAptosNamesAggregateBoolAndBuilder(arguments, predicate).apply(block).build()
  }

  fun boolOr(
      arguments:
          Current_aptos_names_select_column_current_aptos_names_aggregate_bool_exp_bool_or_arguments_columns,
      predicate: Boolean_comparison_exp,
      block: CurrentAptosNamesAggregateBoolOrBuilder.() -> Unit = {}
  ) {
    this.boolOr = CurrentAptosNamesAggregateBoolOrBuilder(arguments, predicate).apply(block).build()
  }

  internal fun build(): Current_aptos_names_aggregate_bool_exp =
      Current_aptos_names_aggregate_bool_exp(
          count = count.toOptional(),
          bool_and = boolAnd.toOptional(),
          bool_or = boolOr.toOptional())
}

fun currentAptosNamesAggregateFilter(
    init: CurrentAptosNamesAggregateFilterBuilder.() -> Unit
): Current_aptos_names_aggregate_bool_exp =
    CurrentAptosNamesAggregateFilterBuilder().apply(init).build()
