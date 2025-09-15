package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentFungibleAssetBalancesFilterBuilder {
  private val andConditions = mutableListOf<Current_fungible_asset_balances_bool_exp>()
  private val orConditions = mutableListOf<Current_fungible_asset_balances_bool_exp>()
  private var notCondition: Current_fungible_asset_balances_bool_exp? = null

  var amount: Numeric_comparison_exp? = null
  var amountV1: Numeric_comparison_exp? = null
  var amountV2: Numeric_comparison_exp? = null

  var assetType: String_comparison_exp? = null
  var assetTypeV1: String_comparison_exp? = null
  var assetTypeV2: String_comparison_exp? = null

  var isFrozen: Boolean_comparison_exp? = null
  var isPrimary: Boolean_comparison_exp? = null

  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastTransactionTimestampV1: Timestamp_comparison_exp? = null
  var lastTransactionTimestampV2: Timestamp_comparison_exp? = null

  var lastTransactionVersion: Bigint_comparison_exp? = null
  var lastTransactionVersionV1: Bigint_comparison_exp? = null
  var lastTransactionVersionV2: Bigint_comparison_exp? = null

  var metadata: Fungible_asset_metadata_bool_exp? = null
  var ownerAddress: String_comparison_exp? = null
  var storageId: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null

  fun and(block: CurrentFungibleAssetBalancesFilterBuilder.() -> Unit) {
    andConditions += CurrentFungibleAssetBalancesFilterBuilder().apply(block).build()
  }

  fun or(block: CurrentFungibleAssetBalancesFilterBuilder.() -> Unit) {
    orConditions += CurrentFungibleAssetBalancesFilterBuilder().apply(block).build()
  }

  fun not(block: CurrentFungibleAssetBalancesFilterBuilder.() -> Unit) {
    notCondition = CurrentFungibleAssetBalancesFilterBuilder().apply(block).build()
  }

  internal fun build(): Current_fungible_asset_balances_bool_exp =
      Current_fungible_asset_balances_bool_exp(
          _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _not = notCondition.toOptional(),
          amount = amount.toOptional(),
          amount_v1 = amountV1.toOptional(),
          amount_v2 = amountV2.toOptional(),
          asset_type = assetType.toOptional(),
          asset_type_v1 = assetTypeV1.toOptional(),
          asset_type_v2 = assetTypeV2.toOptional(),
          is_frozen = isFrozen.toOptional(),
          is_primary = isPrimary.toOptional(),
          last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
          last_transaction_timestamp_v1 = lastTransactionTimestampV1.toOptional(),
          last_transaction_timestamp_v2 = lastTransactionTimestampV2.toOptional(),
          last_transaction_version = lastTransactionVersion.toOptional(),
          last_transaction_version_v1 = lastTransactionVersionV1.toOptional(),
          last_transaction_version_v2 = lastTransactionVersionV2.toOptional(),
          metadata = metadata.toOptional(),
          owner_address = ownerAddress.toOptional(),
          storage_id = storageId.toOptional(),
          token_standard = tokenStandard.toOptional())
}

fun currentFungibleAssetBalancesFilter(
    init: CurrentFungibleAssetBalancesFilterBuilder.() -> Unit
): Current_fungible_asset_balances_bool_exp =
    CurrentFungibleAssetBalancesFilterBuilder().apply(init).build()
