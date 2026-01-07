package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Current_objects_order_by
import xyz.mcxross.kaptos.util.toOptional

class CurrentObjectsOrderBuilder {
  var allowUngatedTransfer: OrderBy? = null
  var isDeleted: OrderBy? = null
  var lastGuidCreationNum: OrderBy? = null
  var lastTransactionVersion: OrderBy? = null
  var objectAddress: OrderBy? = null
  var ownerAddress: OrderBy? = null
  var stateKeyHash: OrderBy? = null

  internal fun build(): Current_objects_order_by =
    Current_objects_order_by(
      allow_ungated_transfer = allowUngatedTransfer?.generated.toOptional(),
      is_deleted = isDeleted?.generated.toOptional(),
      last_guid_creation_num = lastGuidCreationNum?.generated.toOptional(),
      last_transaction_version = lastTransactionVersion?.generated.toOptional(),
      object_address = objectAddress?.generated.toOptional(),
      owner_address = ownerAddress?.generated.toOptional(),
      state_key_hash = stateKeyHash?.generated.toOptional(),
    )
}

fun currentObjectsOrder(init: CurrentObjectsOrderBuilder.() -> Unit): Current_objects_order_by =
  CurrentObjectsOrderBuilder().apply(init).build()
