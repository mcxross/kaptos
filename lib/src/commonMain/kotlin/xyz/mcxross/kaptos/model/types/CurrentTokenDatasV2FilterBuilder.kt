package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentTokenDatasV2FilterBuilder {
  private val andConditions = mutableListOf<Current_token_datas_v2_bool_exp>()
  private val orConditions = mutableListOf<Current_token_datas_v2_bool_exp>()
  private var notCondition: Current_token_datas_v2_bool_exp? = null

  private var aptosName: Current_aptos_names_bool_exp? = null
  private var cdnAssetUris: Nft_metadata_crawler_parsed_asset_uris_bool_exp? = null
  private var currentCollection: Current_collections_v2_bool_exp? = null
  private var currentRoyaltyV1: Current_token_royalty_v1_bool_exp? = null
  private var currentTokenOwnerships: Current_token_ownerships_v2_bool_exp? = null
  private var currentTokenOwnershipsAggregate: Current_token_ownerships_v2_aggregate_bool_exp? =
    null
  private var tokenProperties: Jsonb_comparison_exp? = null

  var collectionId: String_comparison_exp? = null
  var decimals: Bigint_comparison_exp? = null
  var description: String_comparison_exp? = null
  var isDeletedV2: Boolean_comparison_exp? = null
  var isFungibleV2: Boolean_comparison_exp? = null
  var largestPropertyVersionV1: Numeric_comparison_exp? = null
  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var maximum: Numeric_comparison_exp? = null
  var supply: Numeric_comparison_exp? = null
  var tokenDataId: String_comparison_exp? = null
  var tokenName: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null
  var tokenUri: String_comparison_exp? = null

  fun and(block: CurrentTokenDatasV2FilterBuilder.() -> Unit) {
    andConditions += CurrentTokenDatasV2FilterBuilder().apply(block).build()
  }

  fun or(block: CurrentTokenDatasV2FilterBuilder.() -> Unit) {
    orConditions += CurrentTokenDatasV2FilterBuilder().apply(block).build()
  }

  fun not(block: CurrentTokenDatasV2FilterBuilder.() -> Unit) {
    notCondition = CurrentTokenDatasV2FilterBuilder().apply(block).build()
  }

  fun aptosName(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.aptosName = currentAptosNamesFilter(block)
  }

  fun cdnAssetUris(block: NftMetadataCrawlerParsedAssetUrisFilterBuilder.() -> Unit) {
    this.cdnAssetUris = nftMetadataCrawlerParsedAssetUrisFilter(block)
  }

  fun currentCollection(block: CurrentCollectionsV2FilterBuilder.() -> Unit) {
    this.currentCollection = currentCollectionsV2Filter(block)
  }

  fun currentRoyaltyV1(block: CurrentTokenRoyaltyV1FilterBuilder.() -> Unit) {
    this.currentRoyaltyV1 = currentTokenRoyaltyV1Filter(block)
  }

  fun currentTokenOwnerships(block: CurrentTokenOwnershipsV2FilterBuilder.() -> Unit) {
    this.currentTokenOwnerships = currentTokenOwnershipsV2Filter(block)
  }

  fun currentTokenOwnershipsAggregate(
    block: CurrentTokenOwnershipsV2AggregateFilterBuilder.() -> Unit
  ) {
    this.currentTokenOwnershipsAggregate = currentTokenOwnershipsV2AggregateFilter(block)
  }

  fun tokenProperties(block: JsonbFilterBuilder.() -> Unit) {
    this.tokenProperties = jsonbFilter(block)
  }

  internal fun build(): Current_token_datas_v2_bool_exp =
    Current_token_datas_v2_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      aptos_name = aptosName.toOptional(),
      cdn_asset_uris = cdnAssetUris.toOptional(),
      collection_id = collectionId.toOptional(),
      current_collection = currentCollection.toOptional(),
      current_royalty_v1 = currentRoyaltyV1.toOptional(),
      current_token_ownerships = currentTokenOwnerships.toOptional(),
      current_token_ownerships_aggregate = currentTokenOwnershipsAggregate.toOptional(),
      decimals = decimals.toOptional(),
      description = description.toOptional(),
      is_deleted_v2 = isDeletedV2.toOptional(),
      is_fungible_v2 = isFungibleV2.toOptional(),
      largest_property_version_v1 = largestPropertyVersionV1.toOptional(),
      last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
      maximum = maximum.toOptional(),
      supply = supply.toOptional(),
      token_data_id = tokenDataId.toOptional(),
      token_name = tokenName.toOptional(),
      token_properties = tokenProperties.toOptional(),
      token_standard = tokenStandard.toOptional(),
      token_uri = tokenUri.toOptional(),
    )
}

fun currentTokenDatasV2Filter(
  init: CurrentTokenDatasV2FilterBuilder.() -> Unit
): Current_token_datas_v2_bool_exp = CurrentTokenDatasV2FilterBuilder().apply(init).build()
