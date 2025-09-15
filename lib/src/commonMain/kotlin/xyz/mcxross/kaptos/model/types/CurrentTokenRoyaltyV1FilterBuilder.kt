package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenRoyaltyV1FilterBuilder {
  private val andConditions = mutableListOf<Current_token_royalty_v1_bool_exp>()
  private val orConditions = mutableListOf<Current_token_royalty_v1_bool_exp>()
  private var notCondition: Current_token_royalty_v1_bool_exp? = null

  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var payeeAddress: String_comparison_exp? = null
  var royaltyPointsDenominator: Numeric_comparison_exp? = null
  var royaltyPointsNumerator: Numeric_comparison_exp? = null
  var tokenDataId: String_comparison_exp? = null

  fun and(block: CurrentTokenRoyaltyV1FilterBuilder.() -> Unit) {
    andConditions += CurrentTokenRoyaltyV1FilterBuilder().apply(block).build()
  }

  fun or(block: CurrentTokenRoyaltyV1FilterBuilder.() -> Unit) {
    orConditions += CurrentTokenRoyaltyV1FilterBuilder().apply(block).build()
  }

  fun not(block: CurrentTokenRoyaltyV1FilterBuilder.() -> Unit) {
    notCondition = CurrentTokenRoyaltyV1FilterBuilder().apply(block).build()
  }

  internal fun build(): Current_token_royalty_v1_bool_exp =
      Current_token_royalty_v1_bool_exp(
          _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _not = notCondition.toOptional(),
          last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
          last_transaction_version = lastTransactionVersion.toOptional(),
          payee_address = payeeAddress.toOptional(),
          royalty_points_denominator = royaltyPointsDenominator.toOptional(),
          royalty_points_numerator = royaltyPointsNumerator.toOptional(),
          token_data_id = tokenDataId.toOptional(),
      )
}

fun currentTokenRoyaltyV1Filter(
    init: CurrentTokenRoyaltyV1FilterBuilder.() -> Unit
): Current_token_royalty_v1_bool_exp = CurrentTokenRoyaltyV1FilterBuilder().apply(init).build()
