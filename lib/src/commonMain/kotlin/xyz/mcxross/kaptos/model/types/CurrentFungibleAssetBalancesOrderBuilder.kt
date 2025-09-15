package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentFungibleAssetBalancesOrderBuilder {
  var amount: OrderBy? = null
  var amountV1: OrderBy? = null
  var amountV2: OrderBy? = null

  var assetType: OrderBy? = null
  var assetTypeV1: OrderBy? = null
  var assetTypeV2: OrderBy? = null

  var isFrozen: OrderBy? = null
  var isPrimary: OrderBy? = null

  var lastTransactionTimestamp: OrderBy? = null
  var lastTransactionTimestampV1: OrderBy? = null
  var lastTransactionTimestampV2: OrderBy? = null

  var lastTransactionVersion: OrderBy? = null
  var lastTransactionVersionV1: OrderBy? = null
  var lastTransactionVersionV2: OrderBy? = null

  var ownerAddress: OrderBy? = null
  var storageId: OrderBy? = null
  var tokenStandard: OrderBy? = null

  private var metadata: Fungible_asset_metadata_order_by? = null

  fun metadata(block: FungibleAssetMetadataOrderBuilder.() -> Unit) {
    metadata = FungibleAssetMetadataOrderBuilder().apply(block).build()
  }

  internal fun build(): Current_fungible_asset_balances_order_by =
      Current_fungible_asset_balances_order_by(
          amount = amount?.generated.toOptional(),
          amount_v1 = amountV1?.generated.toOptional(),
          amount_v2 = amountV2?.generated.toOptional(),
          asset_type = assetType?.generated.toOptional(),
          asset_type_v1 = assetTypeV1?.generated.toOptional(),
          asset_type_v2 = assetTypeV2?.generated.toOptional(),
          is_frozen = isFrozen?.generated.toOptional(),
          is_primary = isPrimary?.generated.toOptional(),
          last_transaction_timestamp = lastTransactionTimestamp?.generated.toOptional(),
          last_transaction_timestamp_v1 = lastTransactionTimestampV1?.generated.toOptional(),
          last_transaction_timestamp_v2 = lastTransactionTimestampV2?.generated.toOptional(),
          last_transaction_version = lastTransactionVersion?.generated.toOptional(),
          last_transaction_version_v1 = lastTransactionVersionV1?.generated.toOptional(),
          last_transaction_version_v2 = lastTransactionVersionV2?.generated.toOptional(),
          metadata = metadata.toOptional(),
          owner_address = ownerAddress?.generated.toOptional(),
          storage_id = storageId?.generated.toOptional(),
          token_standard = tokenStandard?.generated.toOptional())
}

fun currentFungibleAssetBalancesOrder(
    init: CurrentFungibleAssetBalancesOrderBuilder.() -> Unit
): Current_fungible_asset_balances_order_by =
    CurrentFungibleAssetBalancesOrderBuilder().apply(init).build()
