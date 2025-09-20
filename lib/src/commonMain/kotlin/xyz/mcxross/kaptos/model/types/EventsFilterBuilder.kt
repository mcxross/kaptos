package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class EventsFilterBuilder {
  private val andConditions = mutableListOf<Events_bool_exp>()
  private val orConditions = mutableListOf<Events_bool_exp>()
  private var notCondition: Events_bool_exp? = null

  private var data: Jsonb_comparison_exp? = null

  var accountAddress: String_comparison_exp? = null
  var creationNumber: Bigint_comparison_exp? = null
  var eventIndex: Bigint_comparison_exp? = null
  var indexedType: String_comparison_exp? = null
  var sequenceNumber: Bigint_comparison_exp? = null
  var transactionBlockHeight: Bigint_comparison_exp? = null
  var transactionVersion: Bigint_comparison_exp? = null
  var type: String_comparison_exp? = null

  fun and(block: EventsFilterBuilder.() -> Unit) {
    andConditions += EventsFilterBuilder().apply(block).build()
  }

  fun or(block: EventsFilterBuilder.() -> Unit) {
    orConditions += EventsFilterBuilder().apply(block).build()
  }

  fun not(block: EventsFilterBuilder.() -> Unit) {
    notCondition = EventsFilterBuilder().apply(block).build()
  }

  fun data(block: JsonbFilterBuilder.() -> Unit) {
    this.data = jsonbFilter(block)
  }

  internal fun build(): Events_bool_exp =
      Events_bool_exp(
          _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _not = notCondition.toOptional(),
          account_address = accountAddress.toOptional(),
          creation_number = creationNumber.toOptional(),
          `data` = data.toOptional(),
          event_index = eventIndex.toOptional(),
          indexed_type = indexedType.toOptional(),
          sequence_number = sequenceNumber.toOptional(),
          transaction_block_height = transactionBlockHeight.toOptional(),
          transaction_version = transactionVersion.toOptional(),
          type = type.toOptional())
}

fun eventsFilter(init: EventsFilterBuilder.() -> Unit): Events_bool_exp =
    EventsFilterBuilder().apply(init).build()
