package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class TableMetadatasFilterBuilder {
  private val andConditions = mutableListOf<Table_metadatas_bool_exp>()
  private val orConditions = mutableListOf<Table_metadatas_bool_exp>()
  private var notCondition: Table_metadatas_bool_exp? = null

  var handle: String_comparison_exp? = null
  var keyType: String_comparison_exp? = null
  var valueType: String_comparison_exp? = null

  fun and(block: TableMetadatasFilterBuilder.() -> Unit) {
    andConditions += TableMetadatasFilterBuilder().apply(block).build()
  }

  fun or(block: TableMetadatasFilterBuilder.() -> Unit) {
    orConditions += TableMetadatasFilterBuilder().apply(block).build()
  }

  fun not(block: TableMetadatasFilterBuilder.() -> Unit) {
    notCondition = TableMetadatasFilterBuilder().apply(block).build()
  }

  internal fun build(): Table_metadatas_bool_exp =
    Table_metadatas_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      handle = handle.toOptional(),
      key_type = keyType.toOptional(),
      value_type = valueType.toOptional(),
    )
}

fun tableMetadatasFilter(init: TableMetadatasFilterBuilder.() -> Unit): Table_metadatas_bool_exp =
  TableMetadatasFilterBuilder().apply(init).build()
