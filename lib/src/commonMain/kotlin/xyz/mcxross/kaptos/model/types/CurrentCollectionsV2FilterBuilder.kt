package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentCollectionsV2FilterBuilder {
  private val andConditions = mutableListOf<Current_collections_v2_bool_exp>()
  private val orConditions = mutableListOf<Current_collections_v2_bool_exp>()
  private var notCondition: Current_collections_v2_bool_exp? = null

  private var cdnAssetUris: Nft_metadata_crawler_parsed_asset_uris_bool_exp? = null

  var collectionId: String_comparison_exp? = null
  var collectionName: String_comparison_exp? = null
  var collectionProperties: Jsonb_comparison_exp? = null
  var creatorAddress: String_comparison_exp? = null
  var currentSupply: Numeric_comparison_exp? = null
  var description: String_comparison_exp? = null
  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var maxSupply: Numeric_comparison_exp? = null
  var mutableDescription: Boolean_comparison_exp? = null
  var mutableUri: Boolean_comparison_exp? = null
  var tableHandleV1: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null
  var totalMintedV2: Numeric_comparison_exp? = null
  var uri: String_comparison_exp? = null

  fun and(block: CurrentCollectionsV2FilterBuilder.() -> Unit) {
    andConditions += CurrentCollectionsV2FilterBuilder().apply(block).build()
  }

  fun or(block: CurrentCollectionsV2FilterBuilder.() -> Unit) {
    orConditions += CurrentCollectionsV2FilterBuilder().apply(block).build()
  }

  fun not(block: CurrentCollectionsV2FilterBuilder.() -> Unit) {
    notCondition = CurrentCollectionsV2FilterBuilder().apply(block).build()
  }

  fun cdnAssetUris(block: NftMetadataCrawlerParsedAssetUrisFilterBuilder.() -> Unit) {
    cdnAssetUris = nftMetadataCrawlerParsedAssetUrisFilter(block)
  }

  internal fun build(): Current_collections_v2_bool_exp =
    Current_collections_v2_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      cdn_asset_uris = cdnAssetUris.toOptional(),
      collection_id = collectionId.toOptional(),
      collection_name = collectionName.toOptional(),
      collection_properties = collectionProperties.toOptional(),
      creator_address = creatorAddress.toOptional(),
      current_supply = currentSupply.toOptional(),
      description = description.toOptional(),
      last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
      max_supply = maxSupply.toOptional(),
      mutable_description = mutableDescription.toOptional(),
      mutable_uri = mutableUri.toOptional(),
      table_handle_v1 = tableHandleV1.toOptional(),
      token_standard = tokenStandard.toOptional(),
      total_minted_v2 = totalMintedV2.toOptional(),
      uri = uri.toOptional(),
    )
}

fun currentCollectionsV2Filter(
  init: CurrentCollectionsV2FilterBuilder.() -> Unit
): Current_collections_v2_bool_exp = CurrentCollectionsV2FilterBuilder().apply(init).build()
