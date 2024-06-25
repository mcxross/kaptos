package xyz.mcxross.kaptos.transaction.builder

import xyz.mcxross.kaptos.model.AnyRawTransaction

fun generateSigningMessageForTransaction(transaction: AnyRawTransaction): ByteArray {
    // For now, we only support SimpleTransaction type
  return generateSigningMessage(transaction)
}
