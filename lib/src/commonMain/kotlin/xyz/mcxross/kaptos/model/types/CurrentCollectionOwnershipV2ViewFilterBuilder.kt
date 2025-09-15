package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentCollectionOwnershipV2ViewFilterBuilder {
  private val andConditions = mutableListOf<Current_collection_ownership_v2_view_bool_exp>()
  private val orConditions = mutableListOf<Current_collection_ownership_v2_view_bool_exp>()
  private var notCondition: Current_collection_ownership_v2_view_bool_exp? = null

  var collectionId: String_comparison_exp? = null
  var collectionName: String_comparison_exp? = null
  var collectionUri: String_comparison_exp? = null
  var creatorAddress: String_comparison_exp? = null
  var distinctTokens: Bigint_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var ownerAddress: String_comparison_exp? = null
  var singleTokenUri: String_comparison_exp? = null

  fun currentCollection(block: CurrentCollectionsV2FilterBuilder.() -> Unit) {
    this.currentCollection = currentCollectionsV2Filter(block)
  }

  var currentCollection: Current_collections_v2_bool_exp? = null

  fun and(block: CurrentCollectionOwnershipV2ViewFilterBuilder.() -> Unit) {
    andConditions += CurrentCollectionOwnershipV2ViewFilterBuilder().apply(block).build()
  }

  fun or(block: CurrentCollectionOwnershipV2ViewFilterBuilder.() -> Unit) {
    orConditions += CurrentCollectionOwnershipV2ViewFilterBuilder().apply(block).build()
  }

  fun not(block: CurrentCollectionOwnershipV2ViewFilterBuilder.() -> Unit) {
    notCondition = CurrentCollectionOwnershipV2ViewFilterBuilder().apply(block).build()
  }

  internal fun build(): Current_collection_ownership_v2_view_bool_exp =
      Current_collection_ownership_v2_view_bool_exp(
          _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _not = notCondition.toOptional(),
          collection_id = collectionId.toOptional(),
          collection_name = collectionName.toOptional(),
          collection_uri = collectionUri.toOptional(),
          creator_address = creatorAddress.toOptional(),
          current_collection = currentCollection.toOptional(),
          distinct_tokens = distinctTokens.toOptional(),
          last_transaction_version = lastTransactionVersion.toOptional(),
          owner_address = ownerAddress.toOptional(),
          single_token_uri = singleTokenUri.toOptional())
}

fun currentCollectionOwnershipV2ViewFilter(
    init: CurrentCollectionOwnershipV2ViewFilterBuilder.() -> Unit
): Current_collection_ownership_v2_view_bool_exp =
    CurrentCollectionOwnershipV2ViewFilterBuilder().apply(init).build()
