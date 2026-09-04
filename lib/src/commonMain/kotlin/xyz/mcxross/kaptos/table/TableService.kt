/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.table

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xyz.mcxross.kaptos.account.toAptosError
import xyz.mcxross.kaptos.generated.GetTableItemsDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsMetadataQuery
import xyz.mcxross.kaptos.internal.getTableItem
import xyz.mcxross.kaptos.internal.getTableItemsData
import xyz.mcxross.kaptos.internal.getTableItemsMetadata
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.AptosSlice
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TableItemRequest
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.model.types.tableItemsFilter
import xyz.mcxross.kaptos.model.types.tableMetadatasFilter

/** One historical table write returned by the indexer. */
data class TableItem(
  val decodedKey: String,
  val decodedValue: String?,
  val key: String,
  val tableHandle: String,
  val transactionVersion: ULong,
  val writeSetChangeIndex: ULong,
)

/** Move key and value types registered for a table handle. */
data class TableMetadata(
  val handle: String,
  val keyType: String,
  val valueType: String,
)

/** REST and indexer table operations exposed as `client.tables`. */
interface TableService {
  /** Reads one table value from REST, optionally at [ledgerVersion]. */
  suspend fun getItem(
    handle: String,
    keyType: String,
    valueType: String,
    key: JsonElement,
    ledgerVersion: ULong? = null,
  ): AptosResult<JsonElement>

  /** Reads a table value whose Move key is represented as a JSON string. */
  suspend fun getItem(
    handle: String,
    keyType: String,
    valueType: String,
    key: String,
    ledgerVersion: ULong? = null,
  ): AptosResult<JsonElement> =
    getItem(handle, keyType, valueType, JsonPrimitive(key), ledgerVersion)

  /** Reads a table value whose Move key is represented as a JSON number. */
  suspend fun getItem(
    handle: String,
    keyType: String,
    valueType: String,
    key: Number,
    ledgerVersion: ULong? = null,
  ): AptosResult<JsonElement> =
    getItem(handle, keyType, valueType, JsonPrimitive(key), ledgerVersion)

  /** Reads a table value whose Move key is a boolean. */
  suspend fun getItem(
    handle: String,
    keyType: String,
    valueType: String,
    key: Boolean,
    ledgerVersion: ULong? = null,
  ): AptosResult<JsonElement> =
    getItem(handle, keyType, valueType, JsonPrimitive(key), ledgerVersion)

  /** Returns a typed page of indexed writes for [handle]. */
  suspend fun getItems(
    handle: String,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosSlice<TableItem>>

  /** Returns indexed Move type metadata for [handle]. */
  suspend fun getMetadata(
    handle: String,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosSlice<TableMetadata>>
}

internal class DefaultTableService(
  private val config: TransportConfig,
) : TableService {
  override suspend fun getItem(
    handle: String,
    keyType: String,
    valueType: String,
    key: JsonElement,
    ledgerVersion: ULong?,
  ): AptosResult<JsonElement> {
    if (handle.isBlank() || keyType.isBlank() || valueType.isBlank()) {
      return AptosResult.Failure(
        AptosError.Validation("Table handle, key type, and value type must not be blank")
      )
    }
    return try {
      val params = ledgerVersion?.let { mapOf("ledger_version" to it.toString()) }
      when (
        val result =
          getTableItem<JsonElement>(
            config,
            handle,
            TableItemRequest(key_type = keyType, value_type = valueType, key = key),
            params,
          )
      ) {
        is Result.Ok -> AptosResult.Success(result.value)
        is Result.Err -> AptosResult.Failure(result.error.toAptosError())
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid table item request", error))
    }
  }

  override suspend fun getItems(
    handle: String,
    page: PageRequest,
  ): AptosResult<AptosSlice<TableItem>> {
    if (handle.isBlank()) return AptosResult.Failure(AptosError.Validation("Table handle is blank"))
    val filter = tableItemsFilter { tableHandle = stringFilter { eq = handle } }
    return when (
      val result =
        getTableItemsData(
          config,
          filter,
          sortOrder = null,
          page = PaginationArgs(offset = page.offset, limit = page.limit),
        )
    ) {
      is Result.Err -> AptosResult.Failure(result.error.toAptosError())
      is Result.Ok ->
        try {
          AptosResult.Success(
            AptosSlice(
              items =
                result.value?.table_items.orEmpty().map(GetTableItemsDataQuery.Table_item::toRecord),
              request = page,
            )
          )
        } catch (error: Throwable) {
          AptosResult.Failure(AptosError.Serialization("Invalid table item returned by indexer", error))
        }
    }
  }

  override suspend fun getMetadata(
    handle: String,
    page: PageRequest,
  ): AptosResult<AptosSlice<TableMetadata>> {
    if (handle.isBlank()) return AptosResult.Failure(AptosError.Validation("Table handle is blank"))
    val filter = tableMetadatasFilter { this.handle = stringFilter { eq = handle } }
    return when (
      val result =
        getTableItemsMetadata(
          config,
          filter,
          sortOrder = null,
          page = PaginationArgs(offset = page.offset, limit = page.limit),
        )
    ) {
      is Result.Err -> AptosResult.Failure(result.error.toAptosError())
      is Result.Ok ->
        AptosResult.Success(
          AptosSlice(
            items =
              result.value?.table_metadatas.orEmpty().map(
                GetTableItemsMetadataQuery.Table_metadata::toRecord
              ),
            request = page,
          )
        )
    }
  }
}

internal fun GetTableItemsDataQuery.Table_item.toRecord(): TableItem =
  TableItem(
    decodedKey = decoded_key.toString(),
    decodedValue = decoded_value?.toString(),
    key = key,
    tableHandle = table_handle,
    transactionVersion = transaction_version.requiredU64("transaction_version"),
    writeSetChangeIndex = write_set_change_index.requiredU64("write_set_change_index"),
  )

internal fun GetTableItemsMetadataQuery.Table_metadata.toRecord(): TableMetadata =
  TableMetadata(handle = handle, keyType = key_type, valueType = value_type)

private fun Any.requiredU64(field: String): ULong =
  toString().trim('"').toULongOrNull()
    ?: throw IllegalArgumentException("Invalid $field")
