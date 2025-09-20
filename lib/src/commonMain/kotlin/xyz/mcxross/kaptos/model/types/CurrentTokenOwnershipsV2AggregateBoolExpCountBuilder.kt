package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_aggregate_bool_exp_count
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_select_column
import xyz.mcxross.kaptos.generated.type.Int_comparison_exp
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenOwnershipsV2AggregateBoolExpCountBuilder(
    private val predicate: Int_comparison_exp
) {
  var arguments: List<Current_token_ownerships_v2_select_column>? = null
  var distinct: Boolean? = null
  private var filter: Current_token_ownerships_v2_bool_exp? = null

  fun filter(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    this.filter = currentTokenOwnershipsV2Filter(block)
  }

  internal fun build(): Current_token_ownerships_v2_aggregate_bool_exp_count =
      Current_token_ownerships_v2_aggregate_bool_exp_count(
          predicate = predicate,
          arguments = arguments.toOptional(),
          distinct = distinct.toOptional(),
          filter = filter.toOptional(),
      )
}
