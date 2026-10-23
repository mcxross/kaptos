/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import xyz.mcxross.kaptos.indexer.IndexerService
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.toTypeTags
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.table.TableItem
import xyz.mcxross.kaptos.table.TableMetadata
import xyz.mcxross.kaptos.table.TableService
import xyz.mcxross.kaptos.view.ViewService

/** Raw JSON remains a UTF-8 string, preserving Aptos decimal strings for Swift Decodable. */
class Views internal constructor(private val service: ViewService) {
  @Throws(Throwable::class)
  suspend fun call(
    function: String,
    typeArguments: List<String> = emptyList(),
    arguments: List<MoveArgument> = emptyList(),
    ledgerVersionDecimal: String? = null,
  ): String =
    JsonArray(
        service
          .call(
            function,
            typeArguments.toTypeTags(),
            arguments,
            unsigned(ledgerVersionDecimal, "ledgerVersion"),
          )
          .swiftValue()
          .values
      )
      .toString()

  @Throws(Throwable::class)
  suspend fun callRaw(
    function: String,
    typeArguments: List<String> = emptyList(),
    argumentsJson: String = "[]",
    ledgerVersionDecimal: String? = null,
  ): String {
    val arguments =
      Json.parseToJsonElement(argumentsJson) as? JsonArray
        ?: throw AptosException(AptosError.Validation("View arguments must be a JSON array"))
    return JsonArray(
        service
          .callRaw(
            function,
            typeArguments,
            arguments,
            unsigned(ledgerVersionDecimal, "ledgerVersion"),
          )
          .swiftValue()
          .values
      )
      .toString()
  }
}

class Indexer internal constructor(private val service: IndexerService) {
  @Throws(Throwable::class)
  suspend fun query(document: String, variablesJson: String = "{}"): String {
    val variables =
      Json.parseToJsonElement(variablesJson) as? JsonObject
        ?: throw AptosException(AptosError.Validation("GraphQL variables must be a JSON object"))
    return service.query(document, variables).swiftValue().toString()
  }
}

/** Concrete table page avoids exporting the generic AptosSlice across the Swift boundary. */
data class TablePage(val items: List<TableItem>, val hasNextPage: Boolean)

class Tables internal constructor(private val service: TableService) {
  @Throws(Throwable::class)
  suspend fun getItem(
    handle: String,
    keyType: String,
    valueType: String,
    keyJson: String,
    ledgerVersionDecimal: String? = null,
  ): String =
    service
      .getItem(
        handle,
        keyType,
        valueType,
        Json.parseToJsonElement(keyJson),
        unsigned(ledgerVersionDecimal, "ledgerVersion"),
      )
      .swiftValue()
      .toString()

  @Throws(Throwable::class)
  suspend fun getItems(handle: String, page: PageRequest = PageRequest()): TablePage {
    val result = service.getItems(handle, page).swiftValue()
    return TablePage(result.items, result.hasNextPage)
  }

  @Throws(Throwable::class)
  suspend fun getMetadata(handle: String): List<TableMetadata> =
    service.getMetadata(handle).swiftValue().items
}
