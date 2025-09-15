package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Fungible_asset_metadata_order_by
import xyz.mcxross.kaptos.util.toOptional

class FungibleAssetMetadataOrderBuilder {
  var assetType: OrderBy? = null
  var creatorAddress: OrderBy? = null
  var decimals: OrderBy? = null
  var iconUri: OrderBy? = null
  var lastTransactionTimestamp: OrderBy? = null
  var lastTransactionVersion: OrderBy? = null
  var maximumV2: OrderBy? = null
  var name: OrderBy? = null
  var projectUri: OrderBy? = null
  var supplyAggregatorTableHandleV1: OrderBy? = null
  var supplyAggregatorTableKeyV1: OrderBy? = null
  var supplyV2: OrderBy? = null
  var symbol: OrderBy? = null
  var tokenStandard: OrderBy? = null

  internal fun build(): Fungible_asset_metadata_order_by =
      Fungible_asset_metadata_order_by(
          asset_type = assetType?.generated.toOptional(),
          creator_address = creatorAddress?.generated.toOptional(),
          decimals = decimals?.generated.toOptional(),
          icon_uri = iconUri?.generated.toOptional(),
          last_transaction_timestamp = lastTransactionTimestamp?.generated.toOptional(),
          last_transaction_version = lastTransactionVersion?.generated.toOptional(),
          maximum_v2 = maximumV2?.generated.toOptional(),
          name = name?.generated.toOptional(),
          project_uri = projectUri?.generated.toOptional(),
          supply_aggregator_table_handle_v1 = supplyAggregatorTableHandleV1?.generated.toOptional(),
          supply_aggregator_table_key_v1 = supplyAggregatorTableKeyV1?.generated.toOptional(),
          supply_v2 = supplyV2?.generated.toOptional(),
          symbol = symbol?.generated.toOptional(),
          token_standard = tokenStandard?.generated.toOptional(),
      )
}

fun fungibleAssetMetadataOrder(
    init: FungibleAssetMetadataOrderBuilder.() -> Unit
): Fungible_asset_metadata_order_by = FungibleAssetMetadataOrderBuilder().apply(init).build()
