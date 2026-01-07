package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class FungibleAssetMetadataFilterBuilder {
  private val andConditions = mutableListOf<Fungible_asset_metadata_bool_exp>()
  private val orConditions = mutableListOf<Fungible_asset_metadata_bool_exp>()
  private var notCondition: Fungible_asset_metadata_bool_exp? = null

  var assetType: String_comparison_exp? = null
  var creatorAddress: String_comparison_exp? = null
  var decimals: Int_comparison_exp? = null
  var iconUri: String_comparison_exp? = null
  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var maximumV2: Numeric_comparison_exp? = null
  var name: String_comparison_exp? = null
  var projectUri: String_comparison_exp? = null
  var supplyAggregatorTableHandleV1: String_comparison_exp? = null
  var supplyAggregatorTableKeyV1: String_comparison_exp? = null
  var supplyV2: Numeric_comparison_exp? = null
  var symbol: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null

  fun and(block: FungibleAssetMetadataFilterBuilder.() -> Unit) {
    andConditions += FungibleAssetMetadataFilterBuilder().apply(block).build()
  }

  fun or(block: FungibleAssetMetadataFilterBuilder.() -> Unit) {
    orConditions += FungibleAssetMetadataFilterBuilder().apply(block).build()
  }

  fun not(block: FungibleAssetMetadataFilterBuilder.() -> Unit) {
    notCondition = FungibleAssetMetadataFilterBuilder().apply(block).build()
  }

  internal fun build(): Fungible_asset_metadata_bool_exp =
    Fungible_asset_metadata_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      asset_type = assetType.toOptional(),
      creator_address = creatorAddress.toOptional(),
      decimals = decimals.toOptional(),
      icon_uri = iconUri.toOptional(),
      last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
      maximum_v2 = maximumV2.toOptional(),
      name = name.toOptional(),
      project_uri = projectUri.toOptional(),
      supply_aggregator_table_handle_v1 = supplyAggregatorTableHandleV1.toOptional(),
      supply_aggregator_table_key_v1 = supplyAggregatorTableKeyV1.toOptional(),
      supply_v2 = supplyV2.toOptional(),
      symbol = symbol.toOptional(),
      token_standard = tokenStandard.toOptional(),
    )
}

fun fungibleAssetMetadataFilter(
  init: FungibleAssetMetadataFilterBuilder.() -> Unit
): Fungible_asset_metadata_bool_exp = FungibleAssetMetadataFilterBuilder().apply(init).build()
