/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.view

import kotlin.jvm.JvmName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import xyz.mcxross.kaptos.client.postAptosFullNodeAndGetData
import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.mapResponse
import xyz.mcxross.kaptos.internal.moveCodec
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.MimeType
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.toTypeTags
import xyz.mcxross.kaptos.model.typeTagOf
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.move.MoveArgumentCodec

/** Raw, lossless result from a Move view function. */
data class MoveViewResult(val values: List<JsonElement>) {}

/** Typed view calls and explicit raw JSON access. */
interface ViewService {
  /** ABI-validated arguments encoded as BCS. JSON response values remain lossless. */
  suspend fun call(
    function: String,
    typeArguments: List<TypeTag> = emptyList(),
    arguments: List<MoveArgument> = emptyList(),
    ledgerVersion: ULong? = null,
  ): AptosResult<MoveViewResult>

  /** Concise ABI-validated view call accepting vararg arguments inferred as [MoveArgument]s. */
  suspend fun callOf(
    function: String,
    vararg arguments: Any?,
  ): AptosResult<MoveViewResult> =
    call(
      function = function,
      typeArguments = emptyList(),
      arguments = MoveArgument.fromAll(*arguments),
    )

  /** Concise ABI-validated view call with type arguments and vararg arguments. */
  suspend fun callOf(
    function: String,
    typeArguments: List<TypeTag>,
    vararg arguments: Any?,
  ): AptosResult<MoveViewResult> =
    call(
      function = function,
      typeArguments = typeArguments,
      arguments = MoveArgument.fromAll(*arguments),
    )

  /**
   * Sends JSON as supplied, without ABI validation. The fullnode validates raw argument semantics.
   * Calls [function] at an optional historical [ledgerVersion] and preserves JSON return values.
   */
  suspend fun callRaw(
    function: String,
    typeArguments: List<String> = emptyList(),
    arguments: List<JsonElement> = emptyList(),
    ledgerVersion: ULong? = null,
  ): AptosResult<MoveViewResult>
}

/** Concise ABI-validated view call with string type arguments and vararg arguments. */
@JvmName("callOfStrings")
suspend fun ViewService.callOf(
  function: String,
  typeArguments: List<String>,
  vararg arguments: Any?,
): AptosResult<MoveViewResult> =
  callOf(
    function = function,
    typeArguments = typeArguments.toTypeTags(),
    arguments = arguments,
  )

/** Concise ABI-validated view call inferring a single type argument from [T1]. */
@JvmName("callOfReified")
suspend inline fun <reified T1> ViewService.callOf(
  function: String,
  vararg arguments: Any?,
): AptosResult<MoveViewResult> =
  callOf(
    function,
    listOf(typeTagOf<T1>()),
    *arguments,
  )

/** Concise ABI-validated view call inferring two type arguments from [T1] and [T2]. */
@JvmName("callOfReified2")
suspend inline fun <reified T1, reified T2> ViewService.callOf(
  function: String,
  vararg arguments: Any?,
): AptosResult<MoveViewResult> =
  callOf(
    function,
    listOf(typeTagOf<T1>(), typeTagOf<T2>()),
    *arguments,
  )

@Serializable
private data class MoveViewRequest(
  val function: String,
  @SerialName("type_arguments") val typeArguments: List<String>,
  val arguments: List<JsonElement>,
)

internal class DefaultViewService(
  private val config: TransportConfig,
  private val argumentCodec: MoveArgumentCodec = moveCodec(config),
) : ViewService {
  override suspend fun call(
    function: String,
    typeArguments: List<TypeTag>,
    arguments: List<MoveArgument>,
    ledgerVersion: ULong?,
  ): AptosResult<MoveViewResult> = executeAptos {
    val codec = if (ledgerVersion == null) argumentCodec else moveCodec(config, ledgerVersion)
    when (val request = codec.viewRequest(function, typeArguments, arguments)) {
      is AptosResult.Failure -> request
      is AptosResult.Success ->
        postAptosFullNodeAndGetData<List<JsonElement>, ByteArray>(
            RequestOptions.PostAptosRequestOptions(
              aptosConfig = config,
              originMethod = "view",
              path = "view",
              contentType = MimeType.BCS_VIEW_FUNCTION,
              params = ledgerVersion?.let { mapOf("ledger_version" to it.toString()) },
              body = request.value,
            )
          )
          .toAptosResult()
          .mapResponse("Invalid Move view response") { MoveViewResult(it) }
    }
  }

  override suspend fun callRaw(
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
