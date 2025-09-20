package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Events_order_by
import xyz.mcxross.kaptos.util.toOptional

class EventsOrderBuilder {
  var accountAddress: OrderBy? = null
  var creationNumber: OrderBy? = null
  var data: OrderBy? = null
  var eventIndex: OrderBy? = null
  var indexedType: OrderBy? = null
  var sequenceNumber: OrderBy? = null
  var transactionBlockHeight: OrderBy? = null
  var transactionVersion: OrderBy? = null
  var type: OrderBy? = null

  internal fun build(): Events_order_by =
      Events_order_by(
          account_address = accountAddress?.generated.toOptional(),
          creation_number = creationNumber?.generated.toOptional(),
          `data` = data?.generated.toOptional(),
          event_index = eventIndex?.generated.toOptional(),
          indexed_type = indexedType?.generated.toOptional(),
          sequence_number = sequenceNumber?.generated.toOptional(),
          transaction_block_height = transactionBlockHeight?.generated.toOptional(),
          transaction_version = transactionVersion?.generated.toOptional(),
          type = type?.generated.toOptional())
}

fun eventsOrder(init: EventsOrderBuilder.() -> Unit): Events_order_by =
    EventsOrderBuilder().apply(init).build()
