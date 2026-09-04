/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Identifier
import xyz.mcxross.kaptos.model.ModuleId
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction
import xyz.mcxross.kaptos.view.DefaultViewService

/** Current on-chain account-abstraction configuration for an account. */
data class AccountAbstractionStatus(
  val authenticationFunctions: List<AuthenticationFunction>,
) {
  val isEnabled: Boolean
    get() = authenticationFunctions.isNotEmpty()
}

/** Account-abstraction operations exposed as `client.abstraction`. */
interface AccountAbstractionService {
  /** Returns every currently active authentication function for [accountAddress]. */
  suspend fun status(accountAddress: AccountAddressInput): AptosResult<AccountAbstractionStatus>

  /** Checks whether [authenticationFunction] is active for [accountAddress]. */
  suspend fun isEnabled(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction,
  ): AptosResult<Boolean>

  /** Build a transaction that enables an additional authentication function. */
  suspend fun buildEnable(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /**
   * Build a transaction that disables one authentication function, or removes the dispatchable
   * authenticator entirely when [authenticationFunction] is null.
   */
  suspend fun buildDisable(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction? = null,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>
}

internal interface AccountAbstractionDataSource {
  suspend fun getAuthenticationFunctions(
    accountAddress: AccountAddress
  ): AptosResult<List<AuthenticationFunction>>
}

internal class DefaultAccountAbstractionService(
  private val dataSource: AccountAbstractionDataSource,
  private val transactions: TransactionService,
) : AccountAbstractionService {
  constructor(config: TransportConfig, transactions: TransactionService) :
    this(DefaultAccountAbstractionDataSource(config), transactions)

  override suspend fun status(
    accountAddress: AccountAddressInput
  ): AptosResult<AccountAbstractionStatus> =
    try {
      when (val functions = dataSource.getAuthenticationFunctions(AccountAddress.from(accountAddress))) {
        is AptosResult.Failure -> functions
        is AptosResult.Success ->
          AptosResult.Success(AccountAbstractionStatus(functions.value.distinct()))
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid account address", error))
    }

  override suspend fun isEnabled(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction,
  ): AptosResult<Boolean> =
    when (val status = status(accountAddress)) {
      is AptosResult.Failure -> status
      is AptosResult.Success ->
        AptosResult.Success(authenticationFunction in status.value.authenticationFunctions)
    }

  override suspend fun buildEnable(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildFunctionChange(
      accountAddress = accountAddress,
      function = "0x1::account_abstraction::add_authentication_function",
      authenticationFunction = authenticationFunction,
      options = options,
    )

  override suspend fun buildDisable(
    accountAddress: AccountAddressInput,
    authenticationFunction: AuthenticationFunction?,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    if (authenticationFunction == null) {
      transactions.build(
        sender = accountAddress,
        payload =
          TransactionPayload.entryFunction(
            function = "0x1::account_abstraction::remove_authenticator"
          ),
        options = options,
      )
    } else {
      buildFunctionChange(
        accountAddress = accountAddress,
        function = "0x1::account_abstraction::remove_authentication_function",
        authenticationFunction = authenticationFunction,
        options = options,
      )
    }

  private suspend fun buildFunctionChange(
    accountAddress: AccountAddressInput,
    function: String,
    authenticationFunction: AuthenticationFunction,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    transactions.build(
      sender = accountAddress,
      payload =
        TransactionPayload.entryFunction(
          function = function,
          arguments =
            listOf(
              MoveArgument.Address(authenticationFunction.module.address),
              MoveArgument.StringValue(authenticationFunction.module.name.toString()),
              MoveArgument.StringValue(authenticationFunction.function.toString()),
            ),
        ),
      options = options,
    )
}

internal class DefaultAccountAbstractionDataSource(
  config: TransportConfig,
) : AccountAbstractionDataSource {
  private val views = DefaultViewService(config)

  override suspend fun getAuthenticationFunctions(
    accountAddress: AccountAddress
  ): AptosResult<List<AuthenticationFunction>> {
    return when (
      val result =
        views.call(
          function = "0x1::account_abstraction::dispatchable_authenticator",
          arguments = listOf(JsonPrimitive(accountAddress.toStringLong())),
        )
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          decodeAuthenticationFunctions(
            result.value.values.map {
              Json.decodeFromJsonElement<DispatchableAuthenticatorOptionWire>(it)
            }
          )
        } catch (error: Throwable) {
          AptosResult.Failure(
            AptosError.Serialization("Invalid account-abstraction view response", error)
          )
        }
    }
  }
}

@Serializable
private data class DispatchableAuthenticatorOptionWire(
  val vec: List<List<DispatchableFunctionWire>>,
)

@Serializable
private data class DispatchableFunctionWire(
  @SerialName("module_address") val moduleAddress: String,
  @SerialName("module_name") val moduleName: String,
  @SerialName("function_name") val functionName: String,
)

private fun decodeAuthenticationFunctions(
  response: List<DispatchableAuthenticatorOptionWire>
): AptosResult<List<AuthenticationFunction>> =
  try {
    require(response.size == 1) { "Expected one account-abstraction view result" }
    val option = response.single().vec
    require(option.size <= 1) { "Invalid Move option returned for dispatchable authenticator" }
    AptosResult.Success(
      option.singleOrNull().orEmpty().map { function ->
        AuthenticationFunction(
          module =
            ModuleId(
              AccountAddress.fromString(function.moduleAddress),
              Identifier(function.moduleName),
            ),
          function = Identifier(function.functionName),
        )
      }
    )
  } catch (error: Throwable) {
    AptosResult.Failure(
      AptosError.Serialization("Invalid account-abstraction view response", error)
    )
  }
