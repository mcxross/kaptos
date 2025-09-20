package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Table_metadatas_order_by
import xyz.mcxross.kaptos.util.toOptional

class TableMetadatasOrderBuilder {
  var handle: OrderBy? = null
  var keyType: OrderBy? = null
  var valueType: OrderBy? = null

  internal fun build(): Table_metadatas_order_by =
      Table_metadatas_order_by(
          handle = handle?.generated.toOptional(),
          key_type = keyType?.generated.toOptional(),
          value_type = valueType?.generated.toOptional())
}

fun tableMetadatasOrder(init: TableMetadatasOrderBuilder.() -> Unit): Table_metadatas_order_by =
    TableMetadatasOrderBuilder().apply(init).build()
