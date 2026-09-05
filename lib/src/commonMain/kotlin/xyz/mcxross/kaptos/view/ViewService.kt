/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.view

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import xyz.mcxross.kaptos.client.postAptosFullNodeAndGetData
import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.mapResponse
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.TransportConfig

/** Raw, lossless result from a Move view function. */
data class MoveViewResult(val values: List<JsonElement>)

/** JSON-view operations for modules whose return structs are not known to core Kaptos. */
interface ViewService {
  /**
   * Calls [function] at an optional historical [ledgerVersion] and preserves JSON return values.
   */
  suspend fun call(
    function: String,
    typeArguments: List<String> = emptyList(),
    arguments: List<JsonElement> = emptyList(),
    ledgerVersion: ULong? = null,
  ): AptosResult<MoveViewResult>
}

@Serializable
private data class MoveViewRequest(
  val function: String,
  @SerialName("type_arguments") val typeArguments: List<String>,
  val arguments: List<JsonElement>,
)

internal class DefaultViewService(private val config: TransportConfig) : ViewService {
  override suspend fun call(
    function: String,
    typeArguments: List<String>,
    arguments: List<JsonElement>,
    ledgerVersion: ULong?,
  ): AptosResult<MoveViewResult> {
    if (function.split("::").size != 3) {
      return AptosResult.Failure(
        AptosError.Validation("Move view function must use address::module::function")
      )
    }
    return executeAptos {
      postAptosFullNodeAndGetData<List<JsonElement>, MoveViewRequest>(
          RequestOptions.PostAptosRequestOptions(
            aptosConfig = config,
            originMethod = "view",
            path = "view",
            params = ledgerVersion?.let { mapOf("ledger_version" to it.toString()) },
            body = MoveViewRequest(function, typeArguments, arguments),
          )
        )
        .toAptosResult()
        .mapResponse("Invalid Move view response") { MoveViewResult(it) }
    }
  }
}
