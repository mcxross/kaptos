/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PageRequest

/** Confidential-asset event categories emitted by the Aptos indexer. */
enum class ConfidentialActivityType(val wireName: String) {
  Registered("Registered"),
  Deposited("Deposited"),
  Withdrawn("Withdrawn"),
  Transferred("Transferred"),
  Normalized("Normalized"),
  RolledOver("RolledOver"),
  KeyRotated("KeyRotated"),
  IncomingTransfersPauseChanged("IncomingTransfersPauseChanged"),
  AllowListingChanged("AllowListingChanged"),
  ConfidentialityForAssetTypeChanged("ConfidentialityForAssetTypeChanged"),
  GlobalAuditorChanged("GlobalAuditorChanged"),
  AssetSpecificAuditorChanged("AssetSpecificAuditorChanged");

  companion object {
    internal fun fromWire(value: String): ConfidentialActivityType =
      entries.firstOrNull { it.wireName == value }
        ?: throw IllegalArgumentException("Unknown confidential activity type: $value")
  }
}

/** Stable Kotlin model for one confidential-asset indexer row. */
data class ConfidentialAssetActivity(
  val transactionVersion: ULong,
  val eventIndex: ULong,
  val type: ConfidentialActivityType,
  val owner: AccountAddress,
  val ownerPrimaryName: String?,
  val asset: AccountAddress?,
  val counterparty: AccountAddress?,
  val counterpartyPrimaryName: String?,
  val amount: ULong?,
  val eventData: JsonObject,
  val eventDataVersion: String,
  val blockHeight: ULong,
  val transactionSucceeded: Boolean,
  val entryFunction: String?,
  val timestamp: String,
)

/** Filters and pagination for confidential-asset activity lookup. */
data class ConfidentialActivityQuery(
  val owner: AccountAddressInput? = null,
  val asset: AccountAddressInput? = null,
  val types: Set<ConfidentialActivityType> = emptySet(),
  val page: PageRequest = PageRequest(),
)

internal suspend fun Aptos.confidentialActivities(
  query: ConfidentialActivityQuery
): AptosResult<AptosPage<ConfidentialAssetActivity>> {
  val variables =
    try {
      buildJsonObject {
        put(
          "where_condition",
          buildJsonObject {
            query.owner?.let {
              put("owner_address", equality(AccountAddress.from(it).toString()))
            }
            query.asset?.let {
              put("asset_type", equality(AccountAddress.from(it).toString()))
            }
            if (query.types.isNotEmpty()) {
              put(
                "event_type",
                buildJsonObject {
                  put(
                    "_in",
                    buildJsonArray {
                      query.types.sortedBy(ConfidentialActivityType::wireName).forEach {
                        add(JsonPrimitive(it.wireName))
                      }
                    },
                  )
                },
              )
            }
          },
        )
        put("offset", JsonPrimitive(query.page.offset))
        put("limit", JsonPrimitive(query.page.limit))
        put(
          "order_by",
          buildJsonArray {
            add(buildJsonObject { put("transaction_version", JsonPrimitive("desc")) })
          },
        )
      }
    } catch (error: Throwable) {
      return AptosResult.Failure(
        AptosError.Validation("Invalid confidential activity query", error)
      )
    }
  return when (val response = indexer.query(ACTIVITIES_QUERY, variables)) {
    is AptosResult.Failure -> response
    is AptosResult.Success ->
      try {
        val rows = response.value.getValue("confidential_asset_activities").jsonArray
        val count =
          response.value
            .getValue("confidential_asset_activities_aggregate")
            .jsonObject
            .getValue("aggregate")
            .jsonObject
            .getValue("count")
            .jsonPrimitive
            .content
            .toInt()
        AptosResult.Success(
          AptosPage(
            items = rows.map { it.jsonObject.toActivity() },
            totalCount = count,
            request = query.page,
          )
        )
      } catch (error: Throwable) {
        AptosResult.Failure(
          AptosError.Serialization("Invalid confidential activity response", error)
        )
      }
  }
}

private fun equality(value: String): JsonObject = buildJsonObject {
  put("_eq", JsonPrimitive(value))
}

private fun JsonObject.toActivity(): ConfidentialAssetActivity =
  ConfidentialAssetActivity(
    transactionVersion = requiredU64("transaction_version"),
    eventIndex = requiredU64("event_index"),
    type = ConfidentialActivityType.fromWire(getValue("event_type").jsonPrimitive.content),
    owner = AccountAddress.fromString(getValue("owner_address").jsonPrimitive.content),
    ownerPrimaryName = primaryName("owner_primary_aptos_name"),
    asset = optionalContent("asset_type")?.let(AccountAddress::fromString),
    counterparty = optionalContent("counterparty_address")?.let(AccountAddress::fromString),
    counterpartyPrimaryName = primaryName("counterparty_primary_aptos_name"),
    amount = optionalContent("amount")?.let(::parseU64),
    eventData = getValue("event_data").jsonObject,
    eventDataVersion = getValue("event_data_version").jsonPrimitive.content,
    blockHeight = requiredU64("block_height"),
    transactionSucceeded =
      getValue("is_transaction_success").jsonPrimitive.content.toBooleanStrict(),
    entryFunction = optionalContent("entry_function_id_str"),
    timestamp = getValue("transaction_timestamp").jsonPrimitive.content,
  )

private fun JsonObject.requiredU64(name: String): ULong =
  parseU64(getValue(name).jsonPrimitive.content)

private fun parseU64(value: String): ULong =
  value.toULongOrNull() ?: throw IllegalArgumentException("Invalid Aptos u64: $value")

private fun JsonObject.optionalContent(name: String): String? =
  getValue(name).takeUnless { it is JsonNull }?.jsonPrimitive?.contentOrNull

private fun JsonObject.primaryName(name: String): String? {
  val value = getValue(name).jsonArray.firstOrNull()?.jsonObject ?: return null
  val domain = value["domain"]?.jsonPrimitive?.contentOrNull ?: return null
  val subdomain = value["subdomain"]?.takeUnless { it is JsonNull }?.jsonPrimitive?.contentOrNull
  return if (subdomain.isNullOrBlank()) "$domain.apt" else "$subdomain.$domain"
}

private const val ACTIVITIES_QUERY =
  """
  query KaptosConfidentialActivities(
    ${'$'}where_condition: confidential_asset_activities_bool_exp
    ${'$'}offset: Int
    ${'$'}limit: Int
    ${'$'}order_by: [confidential_asset_activities_order_by!]
  ) {
    confidential_asset_activities(
      where: ${'$'}where_condition
      offset: ${'$'}offset
      limit: ${'$'}limit
      order_by: ${'$'}order_by
    ) {
      transaction_version
      event_index
      event_type
      owner_address
      owner_primary_aptos_name: owner_aptos_names(
        where: {is_active: {_eq: true}, is_primary: {_eq: true}}
        order_by: {last_transaction_version: desc}
        limit: 1
      ) { domain subdomain }
      asset_type
      counterparty_address
      counterparty_primary_aptos_name: counterparty_aptos_names(
        where: {is_active: {_eq: true}, is_primary: {_eq: true}}
        order_by: {last_transaction_version: desc}
        limit: 1
      ) { domain subdomain }
      amount
      event_data
      event_data_version
      block_height
      is_transaction_success
      entry_function_id_str
      transaction_timestamp
    }
    confidential_asset_activities_aggregate(where: ${'$'}where_condition) {
      aggregate { count }
    }
  }
  """
