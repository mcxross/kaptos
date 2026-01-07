package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class TableItemsFilterBuilder {
  private val andConditions = mutableListOf<Table_items_bool_exp>()
  private val orConditions = mutableListOf<Table_items_bool_exp>()
  private var notCondition: Table_items_bool_exp? = null

  private var decodedKey: Jsonb_comparison_exp? = null
  private var decodedValue: Jsonb_comparison_exp? = null

  var key: String_comparison_exp? = null
  var tableHandle: String_comparison_exp? = null
  var transactionVersion: Bigint_comparison_exp? = null
  var writeSetChangeIndex: Bigint_comparison_exp? = null

  fun and(block: TableItemsFilterBuilder.() -> Unit) {
    andConditions += TableItemsFilterBuilder().apply(block).build()
  }

  fun or(block: TableItemsFilterBuilder.() -> Unit) {
    orConditions += TableItemsFilterBuilder().apply(block).build()
  }

  fun not(block: TableItemsFilterBuilder.() -> Unit) {
    notCondition = TableItemsFilterBuilder().apply(block).build()
  }

  fun decodedKey(block: JsonbFilterBuilder.() -> Unit) {
    this.decodedKey = jsonbFilter(block)
  }

  fun decodedValue(block: JsonbFilterBuilder.() -> Unit) {
    this.decodedValue = jsonbFilter(block)
  }

  internal fun build(): Table_items_bool_exp =
    Table_items_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      decoded_key = decodedKey.toOptional(),
      decoded_value = decodedValue.toOptional(),
      key = key.toOptional(),
      table_handle = tableHandle.toOptional(),
      transaction_version = transactionVersion.toOptional(),
      write_set_change_index = writeSetChangeIndex.toOptional(),
    )
}

fun tableItemsFilter(init: TableItemsFilterBuilder.() -> Unit): Table_items_bool_exp =
  TableItemsFilterBuilder().apply(init).build()
