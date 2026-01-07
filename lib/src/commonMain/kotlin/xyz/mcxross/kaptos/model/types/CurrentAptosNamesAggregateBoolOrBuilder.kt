package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Boolean_comparison_exp
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_aggregate_bool_exp_bool_or
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_select_column_current_aptos_names_aggregate_bool_exp_bool_or_arguments_columns
import xyz.mcxross.kaptos.util.toOptional

class CurrentAptosNamesAggregateBoolOrBuilder(
  private val arguments:
    Current_aptos_names_select_column_current_aptos_names_aggregate_bool_exp_bool_or_arguments_columns,
  private val predicate: Boolean_comparison_exp,
) {
  var distinct: Boolean? = null
  private var filter: Current_aptos_names_bool_exp? = null

  fun filter(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.filter = currentAptosNamesFilter(block)
  }

  internal fun build(): Current_aptos_names_aggregate_bool_exp_bool_or =
    Current_aptos_names_aggregate_bool_exp_bool_or(
      arguments = arguments,
      predicate = predicate,
      distinct = distinct.toOptional(),
      filter = filter.toOptional(),
    )
}
