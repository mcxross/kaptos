/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.objects

import xyz.mcxross.kaptos.account.toAptosError
import xyz.mcxross.kaptos.generated.GetObjectDataQuery
import xyz.mcxross.kaptos.internal.getObjectDataByObjectAddress
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Result

/** Current indexed ownership and transfer state for an Aptos object. */
data class ObjectRecord(
  val address: AccountAddress,
  val owner: AccountAddress,
  val stateKeyHash: String,
  val allowUngatedTransfer: Boolean,
  val lastTransactionVersion: ULong,
  val lastGuidCreationNumber: ULong,
  val isDeleted: Boolean,
)

/** Aptos object queries exposed as `client.objects`. */
interface ObjectService {
  /** Returns the current indexed object, or null when it does not exist. */
  suspend fun get(address: AccountAddressInput): AptosResult<ObjectRecord?>
}

internal class DefaultObjectService(
  private val config: TransportConfig,
) : ObjectService {
  override suspend fun get(address: AccountAddressInput): AptosResult<ObjectRecord?> =
    try {
      when (
        val result =
          getObjectDataByObjectAddress(
            config,
            AccountAddress.from(address),
            sortOrder = null,
            page = null,
          )
      ) {
        is Result.Err -> AptosResult.Failure(result.error.toAptosError())
        is Result.Ok ->
          AptosResult.Success(
            result.value?.toRecord()
          )
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Serialization("Invalid object record returned by indexer", error))
    }
}

internal fun GetObjectDataQuery.Current_object.toRecord(): ObjectRecord =
  ObjectRecord(
    address = AccountAddress.fromString(object_address),
    owner = AccountAddress.fromString(owner_address),
    stateKeyHash = state_key_hash,
    allowUngatedTransfer = allow_ungated_transfer,
    lastTransactionVersion = last_transaction_version.requiredU64("last_transaction_version"),
    lastGuidCreationNumber = last_guid_creation_num.requiredU64("last_guid_creation_num"),
    isDeleted = is_deleted,
  )

private fun Any.requiredU64(field: String): ULong =
  toString().trim('"').toULongOrNull()
    ?: throw IllegalArgumentException("Invalid $field")
