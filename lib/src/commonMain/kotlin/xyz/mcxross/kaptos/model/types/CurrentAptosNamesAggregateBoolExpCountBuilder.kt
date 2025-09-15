package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Current_aptos_names_aggregate_bool_exp_count
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_select_column
import xyz.mcxross.kaptos.generated.type.Int_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class CurrentAptosNamesAggregateBoolExpCountBuilder(private val predicate: Int_comparison_exp) {
  var arguments: List<Current_aptos_names_select_column>? = null
  var distinct: Boolean? = null
  private var filter: Current_aptos_names_bool_exp? = null

  /** Sets a filter for the aggregation. Only rows that satisfy this condition will be counted. */
  fun filter(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.filter = currentAptosNamesFilter(block)
  }

  internal fun build(): Current_aptos_names_aggregate_bool_exp_count =
      Current_aptos_names_aggregate_bool_exp_count(
          predicate = predicate,
          arguments = arguments.toOptional(),
          distinct = distinct.toOptional(),
          filter = filter.toOptional())
}
