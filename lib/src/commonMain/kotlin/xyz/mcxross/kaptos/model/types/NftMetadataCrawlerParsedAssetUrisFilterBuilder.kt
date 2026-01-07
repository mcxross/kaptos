package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class NftMetadataCrawlerParsedAssetUrisFilterBuilder {
  private val andConditions = mutableListOf<Nft_metadata_crawler_parsed_asset_uris_bool_exp>()
  private val orConditions = mutableListOf<Nft_metadata_crawler_parsed_asset_uris_bool_exp>()
  private var notCondition: Nft_metadata_crawler_parsed_asset_uris_bool_exp? = null

  var animationOptimizerRetryCount: Int_comparison_exp? = null
  var assetUri: String_comparison_exp? = null
  var cdnAnimationUri: String_comparison_exp? = null
  var cdnImageUri: String_comparison_exp? = null
  var cdnJsonUri: String_comparison_exp? = null
  var imageOptimizerRetryCount: Int_comparison_exp? = null
  var jsonParserRetryCount: Int_comparison_exp? = null
  var rawAnimationUri: String_comparison_exp? = null
  var rawImageUri: String_comparison_exp? = null

  fun and(block: NftMetadataCrawlerParsedAssetUrisFilterBuilder.() -> Unit) {
    andConditions += NftMetadataCrawlerParsedAssetUrisFilterBuilder().apply(block).build()
  }

  fun or(block: NftMetadataCrawlerParsedAssetUrisFilterBuilder.() -> Unit) {
    orConditions += NftMetadataCrawlerParsedAssetUrisFilterBuilder().apply(block).build()
  }

  fun not(block: NftMetadataCrawlerParsedAssetUrisFilterBuilder.() -> Unit) {
    notCondition = NftMetadataCrawlerParsedAssetUrisFilterBuilder().apply(block).build()
  }

  internal fun build(): Nft_metadata_crawler_parsed_asset_uris_bool_exp =
    Nft_metadata_crawler_parsed_asset_uris_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      animation_optimizer_retry_count = animationOptimizerRetryCount.toOptional(),
      asset_uri = assetUri.toOptional(),
      cdn_animation_uri = cdnAnimationUri.toOptional(),
      cdn_image_uri = cdnImageUri.toOptional(),
      cdn_json_uri = cdnJsonUri.toOptional(),
      image_optimizer_retry_count = imageOptimizerRetryCount.toOptional(),
      json_parser_retry_count = jsonParserRetryCount.toOptional(),
      raw_animation_uri = rawAnimationUri.toOptional(),
      raw_image_uri = rawImageUri.toOptional(),
    )
}

fun nftMetadataCrawlerParsedAssetUrisFilter(
  init: NftMetadataCrawlerParsedAssetUrisFilterBuilder.() -> Unit
): Nft_metadata_crawler_parsed_asset_uris_bool_exp =
  NftMetadataCrawlerParsedAssetUrisFilterBuilder().apply(init).build()
