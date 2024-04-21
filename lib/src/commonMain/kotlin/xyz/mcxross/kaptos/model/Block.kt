package xyz.mcxross.kaptos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Block(
    @SerialName("block_height")
    val blockHeight: String,
    @SerialName("block_hash")
    val blockHash: String,
    @SerialName("block_timestamp")
    val blockTimestamp: String,
    @SerialName("first_version")
    val firstVersion: String,
    @SerialName("last_version")
    val lastVersion: String,
)
