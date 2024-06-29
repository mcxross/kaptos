/*
 * Copyright 2024 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.mcxross.kaptos.internal

import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.client.postAptosFullNode
import xyz.mcxross.kaptos.exception.AbortedException
import xyz.mcxross.kaptos.exception.Error
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.builder.*

internal suspend fun generateTransaction(
  aptosConfig: AptosConfig,
  data: InputGenerateTransactionData,
): AnyRawTransaction {
  val payload = buildTransactionPayload(aptosConfig, data)
  return buildRawTransaction(aptosConfig, data, payload)
}

internal suspend fun buildTransactionPayload(
  aptosConfig: AptosConfig,
  data: InputGenerateTransactionData,
): AnyTransactionPayloadInstance {
  val generateTransactionPayloadData: InputGenerateTransactionPayloadDataWithRemoteABI
  val payload: AnyTransactionPayloadInstance

  when (data) {
    is InputGenerateSingleSignerRawTransactionData -> {
      generateTransactionPayloadData =
        InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI(
          data.data as InputEntryFunctionData
        )
      payload = generateTransactionPayload(aptosConfig, generateTransactionPayloadData)
    }
    else -> throw IllegalArgumentException("Unimplemented transaction data type")
  }

  return payload
}

internal suspend fun buildRawTransaction(
  aptosConfig: AptosConfig,
  data: InputGenerateTransactionData,
  payload: AnyTransactionPayloadInstance,
): AnyRawTransaction {

  var feePayerAddress: AccountAddressInput? = null
  if (isFeePayerTransactionInput(data)) {
    feePayerAddress = AccountAddress.ONE
  }

  return buildTransaction(aptosConfig, data, payload, feePayerAddress)
}

internal fun isFeePayerTransactionInput(data: InputGenerateTransactionData): Boolean {
  return data is InputGenerateSingleSignerRawTransactionData && data.withFeePayer
}

internal fun signTransaction(
  signer: Account,
  transaction: AnyRawTransaction,
): AccountAuthenticator {
  return sign(signer, transaction)
}

internal suspend fun submitTransaction(
  aptosConfig: AptosConfig,
  inputSubmitTransactionData: InputSubmitTransactionData,
): Option<PendingTransactionResponse> {
  val signedTransaction = generateSignedTransaction(inputSubmitTransactionData)

  val response =
    postAptosFullNode<PendingTransactionResponse, ByteArray>(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "submitTransaction",
        path = "transactions",
        contentType = MimeType.BCS_SIGNED_TRANSACTION,
        body = signedTransaction,
      )
    )

  if (response.first.status != Error.ABORTED.asHttpStatusCode()) {
    throw AbortedException()
  }

  return Option.Some(response.second)
}

internal suspend fun simulateTransaction(
  aptosConfig: AptosConfig,
  data: InputSimulateTransactionData,
): Option<List<UserTransactionResponse>> {
  val signedTransaction = generateSignedTransactionForSimulation(data)

  val response =
    postAptosFullNode<List<UserTransactionResponse>, ByteArray>(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "simulateTransaction",
        params =
          mapOf(
            "estimate_gas_unit_price" to data.options.estimateGasUnitPrice,
            "estimate_max_gas_amount" to data.options.estimateMaxGasAmount,
            "estimate_prioritized_gas_unit_price" to data.options.estimatePrioritizedGasUnitPrice,
          ),
        path = "transactions/simulate",
        contentType = MimeType.BCS_SIGNED_TRANSACTION,
        body = signedTransaction,
      )
    )

  return Option.Some(response.second)
}
