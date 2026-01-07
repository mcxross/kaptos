package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Table_items_order_by
import xyz.mcxross.kaptos.util.toOptional

class TableItemsOrderBuilder {
  var decodedKey: OrderBy? = null
  var decodedValue: OrderBy? = null
  var key: OrderBy? = null
  var tableHandle: OrderBy? = null
  var transactionVersion: OrderBy? = null
  var writeSetChangeIndex: OrderBy? = null

  internal fun build(): Table_items_order_by =
    Table_items_order_by(
      decoded_key = decodedKey?.generated.toOptional(),
      decoded_value = decodedValue?.generated.toOptional(),
      key = key?.generated.toOptional(),
      table_handle = tableHandle?.generated.toOptional(),
      transaction_version = transactionVersion?.generated.toOptional(),
      write_set_change_index = writeSetChangeIndex?.generated.toOptional(),
    )
}

fun tableItemsOrder(init: TableItemsOrderBuilder.() -> Unit): Table_items_order_by =
  TableItemsOrderBuilder().apply(init).build()
