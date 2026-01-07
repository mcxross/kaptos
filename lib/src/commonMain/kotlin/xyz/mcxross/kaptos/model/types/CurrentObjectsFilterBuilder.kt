package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentObjectsFilterBuilder {
  private val andConditions = mutableListOf<Current_objects_bool_exp>()
  private val orConditions = mutableListOf<Current_objects_bool_exp>()
  private var notCondition: Current_objects_bool_exp? = null

  var allowUngatedTransfer: Boolean_comparison_exp? = null
  var isDeleted: Boolean_comparison_exp? = null
  var lastGuidCreationNum: Numeric_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var objectAddress: String_comparison_exp? = null
  var ownerAddress: String_comparison_exp? = null
  var stateKeyHash: String_comparison_exp? = null

  fun and(block: CurrentObjectsFilterBuilder.() -> Unit) {
    andConditions += CurrentObjectsFilterBuilder().apply(block).build()
  }

  fun or(block: CurrentObjectsFilterBuilder.() -> Unit) {
    orConditions += CurrentObjectsFilterBuilder().apply(block).build()
  }

  fun not(block: CurrentObjectsFilterBuilder.() -> Unit) {
    notCondition = CurrentObjectsFilterBuilder().apply(block).build()
  }

  internal fun build(): Current_objects_bool_exp =
    Current_objects_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      allow_ungated_transfer = allowUngatedTransfer.toOptional(),
      is_deleted = isDeleted.toOptional(),
      last_guid_creation_num = lastGuidCreationNum.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
      object_address = objectAddress.toOptional(),
      owner_address = ownerAddress.toOptional(),
      state_key_hash = stateKeyHash.toOptional(),
    )
}

fun currentObjectsFilter(init: CurrentObjectsFilterBuilder.() -> Unit): Current_objects_bool_exp =
  CurrentObjectsFilterBuilder().apply(init).build()
