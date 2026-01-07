package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class TokenActivitiesV2FilterBuilder {
  private val andConditions = mutableListOf<Token_activities_v2_bool_exp>()
  private val orConditions = mutableListOf<Token_activities_v2_bool_exp>()
  private var notCondition: Token_activities_v2_bool_exp? = null

  private var aptosNamesFrom: Current_aptos_names_bool_exp? = null
  private var aptosNamesFromAggregate: Current_aptos_names_aggregate_bool_exp? = null
  private var aptosNamesTo: Current_aptos_names_bool_exp? = null
  private var aptosNamesToAggregate: Current_aptos_names_aggregate_bool_exp? = null

  var afterValue: String_comparison_exp? = null
  var beforeValue: String_comparison_exp? = null
  var entryFunctionIdStr: String_comparison_exp? = null
  var eventAccountAddress: String_comparison_exp? = null
  var eventIndex: Bigint_comparison_exp? = null
  var fromAddress: String_comparison_exp? = null
  var isFungibleV2: Boolean_comparison_exp? = null
  var propertyVersionV1: Numeric_comparison_exp? = null
  var toAddress: String_comparison_exp? = null
  var tokenAmount: Numeric_comparison_exp? = null
  var tokenDataId: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null
  var transactionTimestamp: Timestamp_comparison_exp? = null
  var transactionVersion: Bigint_comparison_exp? = null
  var type: String_comparison_exp? = null

  // Property for nested object where a builder is not yet defined.
  var currentTokenData: Current_token_datas_v2_bool_exp? = null

  /** Adds a nested `_and` condition. */
  fun and(block: TokenActivitiesV2FilterBuilder.() -> Unit) {
    andConditions += TokenActivitiesV2FilterBuilder().apply(block).build()
  }

  /** Adds a nested `_or` condition. */
  fun or(block: TokenActivitiesV2FilterBuilder.() -> Unit) {
    orConditions += TokenActivitiesV2FilterBuilder().apply(block).build()
  }

  /** Sets a `_not` condition. */
  fun not(block: TokenActivitiesV2FilterBuilder.() -> Unit) {
    notCondition = TokenActivitiesV2FilterBuilder().apply(block).build()
  }

  /** Sets a nested filter for the `aptos_names_from` relationship. */
  fun aptosNamesFrom(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.aptosNamesFrom = currentAptosNamesFilter(block)
  }

  /** Sets a nested filter for the `aptos_names_from_aggregate` relationship. */
  fun aptosNamesFromAggregate(block: CurrentAptosNamesAggregateFilterBuilder.() -> Unit) {
    this.aptosNamesFromAggregate = currentAptosNamesAggregateFilter(block)
  }

  /** Sets a nested filter for the `aptos_names_to` relationship. */
  fun aptosNamesTo(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.aptosNamesTo = currentAptosNamesFilter(block)
  }

  /** Sets a nested filter for the `aptos_names_to_aggregate` relationship. */
  fun aptosNamesToAggregate(block: CurrentAptosNamesAggregateFilterBuilder.() -> Unit) {
    this.aptosNamesToAggregate = currentAptosNamesAggregateFilter(block)
  }

  internal fun build(): Token_activities_v2_bool_exp =
    Token_activities_v2_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      after_value = afterValue.toOptional(),
      aptos_names_from = aptosNamesFrom.toOptional(),
      aptos_names_from_aggregate = aptosNamesFromAggregate.toOptional(),
      aptos_names_to = aptosNamesTo.toOptional(),
      aptos_names_to_aggregate = aptosNamesToAggregate.toOptional(),
      before_value = beforeValue.toOptional(),
      current_token_data = currentTokenData.toOptional(),
      entry_function_id_str = entryFunctionIdStr.toOptional(),
      event_account_address = eventAccountAddress.toOptional(),
      event_index = eventIndex.toOptional(),
      from_address = fromAddress.toOptional(),
      is_fungible_v2 = isFungibleV2.toOptional(),
      property_version_v1 = propertyVersionV1.toOptional(),
      to_address = toAddress.toOptional(),
      token_amount = tokenAmount.toOptional(),
      token_data_id = tokenDataId.toOptional(),
      token_standard = tokenStandard.toOptional(),
      transaction_timestamp = transactionTimestamp.toOptional(),
      transaction_version = transactionVersion.toOptional(),
      type = type.toOptional(),
    )
}

/** Public DSL entrypoint for building Token_activities_v2_bool_exp. */
fun tokenActivitiesV2Filter(
  init: TokenActivitiesV2FilterBuilder.() -> Unit
): Token_activities_v2_bool_exp = TokenActivitiesV2FilterBuilder().apply(init).build()
