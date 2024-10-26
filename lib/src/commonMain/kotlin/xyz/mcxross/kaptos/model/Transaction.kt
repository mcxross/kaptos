/*
 * Copyright 2024 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.mcxross.kaptos.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.mcxross.kaptos.transaction.instances.RawTransaction

@Serializable
enum class TransactionResponseType {
  @SerialName("pending_transaction") PENDING,
  @SerialName("user_transaction") USER,
  @SerialName("genesis_transaction") GENESIS,
  @SerialName("block_metadata_transaction") BLOCK_METADATA,
  @SerialName("state_checkpoint_transaction") STATE_CHECKPOINT,
  @SerialName("validator_transaction") VALIDATOR,
  @SerialName("block_epilogue_transaction") BLOCK_EPILOGUE,
}

@Serializable
sealed class TransactionResponse {
  abstract val type: TransactionResponseType
}

@Serializable
@SerialName("user_transaction")
data class UserTransactionResponse(
  override val type: TransactionResponseType = TransactionResponseType.USER,
  val version: String,
  val hash: String,
  @SerialName("state_change_hash") val stateChangeHash: String,
  @SerialName("event_root_hash") val eventRootHash: String,
  @SerialName("state_checkpoint_hash") val statecCheckpointHash: String?,
  @SerialName("gas_used") val gasUsed: String,
  val success: Boolean,
  @SerialName("vm_status") val vmStatus: String,
  @SerialName("accumulator_root_hash") val accumulatorRootHash: String,
  // val changes: List<WriteSetChange>,
  val sender: String,
  @SerialName("sequence_number") val sequenceNumber: String,
  @SerialName("max_gas_amount") val maxGasAmount: String,
  @SerialName("gas_unit_price") val gasUnitPrice: String,
  @SerialName("expiration_timestamp_secs") val expirationTimestampSecs: String,
  // val payload: TransactionPayloadResponse,
  // val signature: TransactionSignature?,
  val events: List<Event>,
  val timestamp: String,
) : TransactionResponse()

@Serializable
@SerialName("pending_transaction")
data class PendingTransactionResponse(
  override val type: TransactionResponseType = TransactionResponseType.PENDING,
  val hash: String,
  val sender: String,
  @SerialName("sequence_number") val sequenceNumber: String,
  @SerialName("max_gas_amount") val maxGasAmount: String,
  @SerialName("gas_unit_price") val gasUnitPrice: String,
  @SerialName("expiration_timestamp_secs") val expirationTimestampSecs: String,
) : TransactionResponse()

@Serializable
@SerialName("block_metadata_transaction")
data class BlockMetadataTransactionResponse(
  override val type: TransactionResponseType,
  val version: String,
  val hash: String,
  @SerialName("state_change_hash") val stateChangeHash: String,
  @SerialName("event_root_hash") val eventRootHash: String,
  @SerialName("state_checkpoint_hash") val stateCheckpointHash: String?,
  @SerialName("gas_used") val gasUsed: String,
  val success: Boolean,
  @SerialName("vm_status") val vmStatus: String,
  @SerialName("accumulator_root_hash") val accumulatorRootHash: String,
  // val changes: List<WriteSetChange>,
  val id: String,
  val epoch: String,
  val round: String,
  val events: List<Event>,
  @SerialName("previous_block_votes_bitvec") val previousBlockVotesBitvec: List<Long>,
  val proposer: String,
  @SerialName("failed_proposer_indices") val failedProposerIndices: List<Long>,
  val timestamp: String,
) : TransactionResponse()

@Serializable
@SerialName("state_checkpoint_transaction")
data class StateCheckpointTransactionResponse(
  override val type: TransactionResponseType,
  val version: String,
  val hash: String,
  @SerialName("state_change_hash") val stateChangeHash: String,
  @SerialName("event_root_hash") val eventRootHash: String,
  @SerialName("state_checkpoint_hash") val stateCheckpointHash: String?,
  @SerialName("gas_used") val gasUsed: String,
  val success: Boolean,
  @SerialName("vm_status") val vmStatus: String,
  @SerialName("accumulator_root_hash") val accumulatorRootHash: String,
  // val changes: List<WriteSetChange>,
  val timestamp: String,
) : TransactionResponse()

@Serializable
@SerialName("block_epilogue_transaction")
data class BlockEpilogueTransactionResponse(
  override val type: TransactionResponseType,
  val version: String,
  val hash: String,
  @SerialName("state_change_hash") val stateChangeHash: String,
  @SerialName("event_root_hash") val eventRootHash: String,
  @SerialName("state_checkpoint_hash") val stateCheckpointHash: String?,
  @SerialName("gas_used") val gasUsed: String,
  val success: Boolean,
  @SerialName("vm_status") val vmStatus: String,
  @SerialName("accumulator_root_hash") val accumulatorRootHash: String,
  val timestamp: String,
) : TransactionResponse()

@Serializable sealed class TransactionPayloadResponse

@Serializable
data class ScriptPayloadResponse(
  val type: String,
  val code: String,
  val type_arguments: List<String>,
  val arguments: List<String>,
) : TransactionPayloadResponse()

@Serializable sealed class TransactionSignature

@Serializable
data class TransactionEd25519Signature(
  val type: String,
  val public_key: String,
  val signature: String,
) : TransactionSignature()

@Serializable
data class Guid(
  @SerialName("creation_number") val creationNumber: String? = null,
  @SerialName("account_address") val accountAddress: String? = null,
)

@Serializable
data class Event(
  val guid: Guid,
  @SerialName("sequence_number") val sequenceNumber: String? = null,
  val type: String? = null,
  val data: JsonElement? = null,
)

@Serializable sealed class WriteSetChange

@Serializable
@SerialName("WriteSetChange::Delete")
data class WriteSetChangeDeleteModule(
  val type: String,
  val address: String,
  val state_key_hash: String,
  val module: String,
) : WriteSetChange()

@Serializable abstract class AnyRawTransaction

@Serializable
data class MultiAgentTransaction(val rawTransaction: RawTransaction) : AnyRawTransaction()

@Serializable
data class SimpleTransaction(
    val rawTransaction: RawTransaction,
    var feePayerAddress: AccountAddress?,
    val secondarySignerAddresses: Nothing? = null,
) : AnyRawTransaction()
