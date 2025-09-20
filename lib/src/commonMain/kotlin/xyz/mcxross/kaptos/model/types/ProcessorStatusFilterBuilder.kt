package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class ProcessorStatusFilterBuilder {
  private val andConditions = mutableListOf<Processor_status_bool_exp>()
  private val orConditions = mutableListOf<Processor_status_bool_exp>()
  private var notCondition: Processor_status_bool_exp? = null

  var lastSuccessVersion: Bigint_comparison_exp? = null
  var lastTransactionTimestamp: Timestamp_comparison_exp? = null
  var lastUpdated: Timestamp_comparison_exp? = null
  var processor: String_comparison_exp? = null

  fun and(block: ProcessorStatusFilterBuilder.() -> Unit) {
    andConditions += ProcessorStatusFilterBuilder().apply(block).build()
  }

  fun or(block: ProcessorStatusFilterBuilder.() -> Unit) {
    orConditions += ProcessorStatusFilterBuilder().apply(block).build()
  }

  fun not(block: ProcessorStatusFilterBuilder.() -> Unit) {
    notCondition = ProcessorStatusFilterBuilder().apply(block).build()
  }

  internal fun build(): Processor_status_bool_exp =
      Processor_status_bool_exp(
          _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _not = notCondition.toOptional(),
          last_success_version = lastSuccessVersion.toOptional(),
          last_transaction_timestamp = lastTransactionTimestamp.toOptional(),
          last_updated = lastUpdated.toOptional(),
          processor = processor.toOptional())
}

fun processorStatusFilter(
    init: ProcessorStatusFilterBuilder.() -> Unit
): Processor_status_bool_exp = ProcessorStatusFilterBuilder().apply(init).build()
