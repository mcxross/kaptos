/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.ledger

import xyz.mcxross.kaptos.client.getAptosFullNode
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.internal.mapResponse
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Block
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.LedgerInfo
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.transaction.instances.ChainId

/** Current fullnode ledger state with every Aptos `u64` represented losslessly. */
data class LedgerState(
  val chainId: ChainId,
  val epoch: ULong,
  val version: ULong,
  val oldestVersion: ULong,
  val timestampMicros: ULong,
  val nodeRole: String,
  val oldestBlockHeight: ULong,
  val blockHeight: ULong,
  val gitHash: String?,
  /** Raw BCS encryption-key bytes when encrypted transaction submission is available. */
  val encryptionKey: ByteString?,
)

/** Block identity and inclusive transaction-version range returned by a fullnode. */
data class LedgerBlock(
  val height: ULong,
  val hash: String,
  val timestampMicros: ULong,
  val firstVersion: ULong,
  val lastVersion: ULong,
)

/** Read-only ledger operations exposed as `client.ledger`. */
interface LedgerService {
  /** Fetches the latest ledger state and advertised encrypted-transaction key. */
  suspend fun info(): AptosResult<LedgerState>

  /** Fetches the block containing [version]. */
  suspend fun blockAtVersion(version: ULong): AptosResult<LedgerBlock>

  /** Fetches a block by its chain height. */
  suspend fun blockAtHeight(height: ULong): AptosResult<LedgerBlock>
}

internal interface LedgerDataSource {
  suspend fun info(): AptosResult<LedgerState>

  suspend fun blockAtVersion(version: ULong): AptosResult<LedgerBlock>

  suspend fun blockAtHeight(height: ULong): AptosResult<LedgerBlock>
}

internal class DefaultLedgerService(private val dataSource: LedgerDataSource) : LedgerService {
  constructor(config: TransportConfig) : this(DefaultLedgerDataSource(config))

  override suspend fun info(): AptosResult<LedgerState> = dataSource.info()

  override suspend fun blockAtVersion(version: ULong): AptosResult<LedgerBlock> =
    dataSource.blockAtVersion(version)

  override suspend fun blockAtHeight(height: ULong): AptosResult<LedgerBlock> =
    dataSource.blockAtHeight(height)
}

internal class DefaultLedgerDataSource(private val config: TransportConfig) : LedgerDataSource {
  override suspend fun info(): AptosResult<LedgerState> =
    request<LedgerInfo>("getLedgerInfo", "/").mapResponse("Invalid ledger information") {
      it.toState()
    }

  override suspend fun blockAtVersion(version: ULong): AptosResult<LedgerBlock> =
    request<Block>("getBlockByVersion", "blocks/by_version/$version").mapResponse(
      "Invalid ledger block"
    ) {
      it.toLedgerBlock()
    }

  override suspend fun blockAtHeight(height: ULong): AptosResult<LedgerBlock> =
    request<Block>("getBlockByHeight", "blocks/by_height/$height").mapResponse(
      "Invalid ledger block"
    ) {
      it.toLedgerBlock()
    }

  private suspend inline fun <reified T> request(
    origin: String,
    path: String,
  ): AptosResult<T> =
    getAptosFullNode<T>(
        RequestOptions.GetAptosRequestOptions(
          aptosConfig = config,
          originMethod = origin,
          path = path,
        )
      )
      .toAptosResult()
}

private fun LedgerInfo.toState(): LedgerState =
  LedgerState(
    chainId = ChainId(chainId.toUByte()),
    epoch = epoch.requiredU64("epoch"),
    version = ledgerVersion.requiredU64("ledger_version"),
    oldestVersion = oldestLedgerVersion.requiredU64("oldest_ledger_version"),
    timestampMicros = ledgerTimestamp.requiredU64("ledger_timestamp"),
    nodeRole = nodeRole,
    oldestBlockHeight = oldestBlockHeight.requiredU64("oldest_block_height"),
    blockHeight = blockHeight.requiredU64("block_height"),
    gitHash = gitHash.takeIf(String::isNotBlank),
    encryptionKey = encryptionKey?.let { ByteString(Hex.fromString(it).toByteArray()) },
  )

private fun Block.toLedgerBlock(): LedgerBlock =
  LedgerBlock(
    height = blockHeight.requiredU64("block_height"),
    hash = blockHash,
    timestampMicros = blockTimestamp.requiredU64("block_timestamp"),
    firstVersion = firstVersion.requiredU64("first_version"),
    lastVersion = lastVersion.requiredU64("last_version"),
  )

private fun String.requiredU64(field: String): ULong =
  toULongOrNull() ?: throw IllegalArgumentException("Invalid $field: $this")
