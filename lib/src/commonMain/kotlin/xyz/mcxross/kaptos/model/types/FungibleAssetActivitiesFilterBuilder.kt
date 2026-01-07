package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class FungibleAssetActivitiesFilterBuilder {
  private val andConditions = mutableListOf<Fungible_asset_activities_bool_exp>()
  private val orConditions = mutableListOf<Fungible_asset_activities_bool_exp>()
  private var notCondition: Fungible_asset_activities_bool_exp? = null

  private var metadata: Fungible_asset_metadata_bool_exp? = null
  private var ownerAptosNames: Current_aptos_names_bool_exp? = null

  var amount: Numeric_comparison_exp? = null
  var assetType: String_comparison_exp? = null
  var blockHeight: Bigint_comparison_exp? = null
  var entryFunctionIdStr: String_comparison_exp? = null
  var eventIndex: Bigint_comparison_exp? = null
  var gasFeePayerAddress: String_comparison_exp? = null
  var isFrozen: Boolean_comparison_exp? = null
  var isGasFee: Boolean_comparison_exp? = null
  var isTransactionSuccess: Boolean_comparison_exp? = null
  var ownerAddress: String_comparison_exp? = null
  var storageId: String_comparison_exp? = null
  var storageRefundAmount: Numeric_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null
  var transactionTimestamp: Timestamp_comparison_exp? = null
  var transactionVersion: Bigint_comparison_exp? = null
  var type: String_comparison_exp? = null

  // Property for nested object where a builder is not yet defined.
  var ownerAptosNamesAggregate: Current_aptos_names_aggregate_bool_exp? = null

  /** Adds a nested `_and` condition. */
  fun and(block: FungibleAssetActivitiesFilterBuilder.() -> Unit) {
    andConditions += FungibleAssetActivitiesFilterBuilder().apply(block).build()
  }

  /** Adds a nested `_or` condition. */
  fun or(block: FungibleAssetActivitiesFilterBuilder.() -> Unit) {
    orConditions += FungibleAssetActivitiesFilterBuilder().apply(block).build()
  }

  /** Sets a `_not` condition. */
  fun not(block: FungibleAssetActivitiesFilterBuilder.() -> Unit) {
    notCondition = FungibleAssetActivitiesFilterBuilder().apply(block).build()
  }

  /** Sets a nested filter for `metadata`. */
  fun metadata(block: FungibleAssetMetadataFilterBuilder.() -> Unit) {
    this.metadata = fungibleAssetMetadataFilter(block)
  }

  /** Sets a nested filter for `owner_aptos_names`. */
  fun ownerAptosNames(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.ownerAptosNames = currentAptosNamesFilter(block)
  }

  internal fun build(): Fungible_asset_activities_bool_exp =
    Fungible_asset_activities_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      amount = amount.toOptional(),
      asset_type = assetType.toOptional(),
      block_height = blockHeight.toOptional(),
      entry_function_id_str = entryFunctionIdStr.toOptional(),
      event_index = eventIndex.toOptional(),
      gas_fee_payer_address = gasFeePayerAddress.toOptional(),
      is_frozen = isFrozen.toOptional(),
      is_gas_fee = isGasFee.toOptional(),
      is_transaction_success = isTransactionSuccess.toOptional(),
      metadata = metadata.toOptional(),
      owner_address = ownerAddress.toOptional(),
      owner_aptos_names = ownerAptosNames.toOptional(),
      owner_aptos_names_aggregate = ownerAptosNamesAggregate.toOptional(),
      storage_id = storageId.toOptional(),
      storage_refund_amount = storageRefundAmount.toOptional(),
      token_standard = tokenStandard.toOptional(),
      transaction_timestamp = transactionTimestamp.toOptional(),
      transaction_version = transactionVersion.toOptional(),
      type = type.toOptional(),
    )
}

/** Public DSL entrypoint for building Fungible_asset_activities_bool_exp. */
fun fungibleAssetActivitiesFilter(
  init: FungibleAssetActivitiesFilterBuilder.() -> Unit
): Fungible_asset_activities_bool_exp = FungibleAssetActivitiesFilterBuilder().apply(init).build()
