package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenOwnershipsV2FilterBuilder {
  private val andConditions = mutableListOf<Current_token_ownerships_v2_bool_exp>()
  private val orConditions = mutableListOf<Current_token_ownerships_v2_bool_exp>()
  private var notCondition: Current_token_ownerships_v2_bool_exp? = null

  private var composedNfts: Current_token_ownerships_v2_bool_exp? = null
  private var tokenPropertiesMutatedV1: Jsonb_comparison_exp? = null

  var amount: Numeric_comparison_exp? = null
  var isFungibleV2: Boolean_comparison_exp? = null
  var isSoulboundV2: Boolean_comparison_exp? = null
  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var nonTransferrableByOwner: Boolean_comparison_exp? = null
  var ownerAddress: String_comparison_exp? = null
  var propertyVersionV1: Numeric_comparison_exp? = null
  var storageId: String_comparison_exp? = null
  var tableTypeV1: String_comparison_exp? = null
  var tokenDataId: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null

  var composedNftsAggregate: Current_token_ownerships_v2_aggregate_bool_exp? = null
  var currentTokenData: Current_token_datas_v2_bool_exp? = null

  fun and(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    andConditions += CurrentTokenOwnershipsV2FilterBuilder().apply(block).build()
  }

  fun or(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    orConditions += CurrentTokenOwnershipsV2FilterBuilder().apply(block).build()
  }

  fun not(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    notCondition = CurrentTokenOwnershipsV2FilterBuilder().apply(block).build()
  }

  fun composedNfts(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    composedNfts = CurrentTokenOwnershipsV2FilterBuilder().apply(block).build()
  }

  fun tokenPropertiesMutatedV1(block: JsonbFilterBuilder.() -> Unit) {
    tokenPropertiesMutatedV1 = jsonbFilter(block)
  }

  internal fun build(): Current_token_ownerships_v2_bool_exp =
    Current_token_ownerships_v2_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      amount = amount.toOptional(),
      composed_nfts = composedNfts.toOptional(),
      composed_nfts_aggregate = composedNftsAggregate.toOptional(),
      current_token_data = currentTokenData.toOptional(),
      is_fungible_v2 = isFungibleV2.toOptional(),
      is_soulbound_v2 = isSoulboundV2.toOptional(),
      last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
      non_transferrable_by_owner = nonTransferrableByOwner.toOptional(),
      owner_address = ownerAddress.toOptional(),
      property_version_v1 = propertyVersionV1.toOptional(),
      storage_id = storageId.toOptional(),
      table_type_v1 = tableTypeV1.toOptional(),
      token_data_id = tokenDataId.toOptional(),
      token_properties_mutated_v1 = tokenPropertiesMutatedV1.toOptional(),
      token_standard = tokenStandard.toOptional(),
    )
}

fun currentTokenOwnershipsV2Filter(
  init: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit
): Current_token_ownerships_v2_bool_exp =
  CurrentTokenOwnershipsV2FilterBuilder().apply(init).build()
