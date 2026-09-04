/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.core.Hex
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.view.MoveViewResult

internal class ConfidentialAssetDataSource(
  private val client: Aptos,
  private val moduleAddress: AccountAddress,
) {
  private val module = "${moduleAddress}::confidential_asset"

  suspend fun balance(
    account: AccountAddress,
    token: AccountAddress,
    key: ConfidentialDecryptionKey,
  ): AptosResult<ConfidentialBalance> =
    coroutineScope {
      val arguments = listOf(JsonPrimitive(account.toString()), JsonPrimitive(token.toString()))
      val available = async { view("get_available_balance", arguments) }
      val pending = async { view("get_pending_balance", arguments) }
      val availableCiphertexts = available.await().flatMap(::parseCiphertexts)
      if (availableCiphertexts is AptosResult.Failure) return@coroutineScope availableCiphertexts
      val pendingCiphertexts = pending.await().flatMap(::parseCiphertexts)
      if (pendingCiphertexts is AptosResult.Failure) return@coroutineScope pendingCiphertexts
      availableCiphertexts as AptosResult.Success
      pendingCiphertexts as AptosResult.Success
      val availableChunks = decrypt(availableCiphertexts.value, key)
      if (availableChunks is AptosResult.Failure) return@coroutineScope availableChunks
      val pendingChunks = decrypt(pendingCiphertexts.value, key)
      if (pendingChunks is AptosResult.Failure) return@coroutineScope pendingChunks
      AptosResult.Success(
        ConfidentialBalance(
          available = availableCiphertexts.value,
          pending = pendingCiphertexts.value,
          availableAmount = ConfidentialAmount((availableChunks as AptosResult.Success).value),
          pendingAmount = ConfidentialAmount((pendingChunks as AptosResult.Success).value),
        )
      )
    }

  suspend fun encryptionKey(
    account: AccountAddress,
    token: AccountAddress,
  ): AptosResult<ConfidentialEncryptionKey> =
    view(
        "get_encryption_key",
        listOf(JsonPrimitive(account.toString()), JsonPrimitive(token.toString())),
      )
      .flatMap { result ->
        parse("Invalid confidential encryption-key response") {
          ConfidentialEncryptionKey(result.single().jsonObject.requiredHex("data"))
        }
      }

  suspend fun assetAuditorKey(token: AccountAddress): AptosResult<ConfidentialEncryptionKey?> =
    view("get_effective_auditor_config", listOf(JsonPrimitive(token.toString()))).flatMap { result ->
      parse("Invalid effective auditor response") {
        val option =
          result.single().jsonObject
            .getValue("config").jsonObject
            .getValue("ek").jsonObject
            .getValue("vec").jsonArray
        option.firstOrNull()?.jsonObject?.requiredHex("data")?.let(::ConfidentialEncryptionKey)
      }
    }

  suspend fun effectiveAuditorHint(
    account: AccountAddress,
    token: AccountAddress,
  ): AptosResult<EffectiveAuditorHint?> =
    view(
        "get_effective_auditor_hint",
        listOf(JsonPrimitive(account.toString()), JsonPrimitive(token.toString())),
      )
      .flatMap { result ->
        parse("Invalid effective auditor hint") {
          val option = result.single().jsonObject.getValue("vec").jsonArray
          option.firstOrNull()?.jsonObject?.let {
            EffectiveAuditorHint(
              isGlobal = it.getValue("is_global").jsonPrimitive.boolean,
              epoch = it.getValue("epoch").jsonPrimitive.content.toULong(),
            )
          }
        }
      }

  suspend fun status(
    account: AccountAddress,
    token: AccountAddress,
  ): AptosResult<ConfidentialAssetStatus> =
    coroutineScope {
      val arguments = listOf(JsonPrimitive(account.toString()), JsonPrimitive(token.toString()))
      val registered = async { booleanView("has_confidential_store", arguments) }
      val normalized = async { booleanView("is_normalized", arguments) }
      val paused = async { booleanView("incoming_transfers_paused", arguments) }
      val values = listOf(registered.await(), normalized.await(), paused.await())
      values.filterIsInstance<AptosResult.Failure>().firstOrNull()
        ?: AptosResult.Success(
          ConfidentialAssetStatus(
            registered = (values[0] as AptosResult.Success).value,
            normalized = (values[1] as AptosResult.Success).value,
            incomingTransfersPaused = (values[2] as AptosResult.Success).value,
          )
        )
    }

  suspend fun emergencyPaused(): AptosResult<Boolean> = booleanView("is_emergency_paused", emptyList())

  suspend fun maxMemoBytes(): AptosResult<Int> =
    view("get_max_memo_bytes", emptyList()).flatMap { result ->
      parse("Invalid maximum memo length") { result.single().jsonPrimitive.content.toInt() }
    }

  private suspend fun booleanView(
    function: String,
    arguments: List<JsonElement>,
  ): AptosResult<Boolean> =
    view(function, arguments).flatMap { result ->
      parse("Invalid $function response") { result.single().jsonPrimitive.boolean }
    }

  private suspend fun view(
    function: String,
    arguments: List<JsonElement>,
  ): AptosResult<List<JsonElement>> =
    client.views.call(function = "$module::$function", arguments = arguments).map { it.values }

  private fun parseCiphertexts(result: List<JsonElement>): AptosResult<List<ConfidentialCiphertext>> =
    parse("Invalid confidential balance response") {
      val value = result.single().jsonObject
      val commitments = value.getValue("P").jsonArray.map { it.jsonObject.requiredHex("data") }
      val handles = value.getValue("R").jsonArray.map { it.jsonObject.requiredHex("data") }
      require(commitments.size == handles.size && commitments.isNotEmpty()) {
        "Confidential balance point vectors have different sizes"
      }
      commitments.zip(handles, ::ConfidentialCiphertext)
    }

  private fun decrypt(
    ciphertexts: List<ConfidentialCiphertext>,
    key: ConfidentialDecryptionKey,
  ): AptosResult<List<UInt>> {
    val chunks = mutableListOf<UInt>()
    for (ciphertext in ciphertexts) {
      when (val value = ciphertext.decrypt(key)) {
        is AptosResult.Failure -> return value
        is AptosResult.Success -> chunks += value.value
      }
    }
    return AptosResult.Success(chunks)
  }
}

private inline fun <T> parse(message: String, block: () -> T): AptosResult<T> =
  try {
    AptosResult.Success(block())
  } catch (error: Throwable) {
    AptosResult.Failure(AptosError.Serialization(message, error))
  }

private fun JsonObject.requiredHex(name: String): ByteArray =
  Hex.fromString(getValue(name).jsonPrimitive.content).toByteArray()

private inline fun <T, R> AptosResult<T>.flatMap(transform: (T) -> AptosResult<R>): AptosResult<R> =
  when (this) {
    is AptosResult.Success -> transform(value)
    is AptosResult.Failure -> this
  }

private inline fun <T, R> AptosResult<T>.map(transform: (T) -> R): AptosResult<R> =
  when (this) {
    is AptosResult.Success -> AptosResult.Success(transform(value))
    is AptosResult.Failure -> this
  }
