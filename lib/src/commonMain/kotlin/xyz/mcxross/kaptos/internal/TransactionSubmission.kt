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

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.client.postAptosFullNode
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.builder.*

internal suspend fun generateTransaction(
  aptosConfig: TransportConfig,
  data: InputGenerateTransactionData,
): UnsignedTransaction {
  val payload = buildTransactionPayload(aptosConfig, data)
  return buildRawTransaction(aptosConfig, data, payload)
}

internal suspend fun buildTransactionPayload(
  aptosConfig: TransportConfig,
  data: InputGenerateTransactionData,
): TransactionPayload {
  val generateTransactionPayloadData: InputGenerateTransactionPayloadDataWithRemoteABI
  val payload: TransactionPayload

  generateTransactionPayloadData =
    InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI(
      data.data as InputEntryFunctionData
    )
  payload = generateTransactionPayload(aptosConfig, generateTransactionPayloadData)

  return payload
}

internal suspend fun buildRawTransaction(
  aptosConfig: TransportConfig,
  data: InputGenerateTransactionData,
  payload: TransactionPayload,
): UnsignedTransaction {
  return buildTransaction(aptosConfig, data, payload, feePayerAddress = null)
}

internal fun isFeePayerTransactionInput(data: InputGenerateTransactionData): Boolean {
  return data.withFeePayer
}

internal fun signTransaction(
  signer: Account,
  transaction: UnsignedTransaction,
): AccountAuthenticator {
  return sign(signer, transaction)
}

internal fun signAsFeePayer(
  signer: Account,
  transaction: UnsignedTransaction,
): FeePayerSignature {
  require(transaction is UnsignedTransaction.FeePayer) {
    "The transaction must be built as a fee-payer transaction"
  }
  val sponsored = transaction.copy(feePayerAddress = signer.accountAddress)
  return FeePayerSignature(
    transaction = sponsored,
    authenticator = signTransaction(signer, sponsored),
  )
}

internal suspend fun submitTransaction(
  aptosConfig: TransportConfig,
  inputSubmitTransactionData: InputSubmitTransactionData,
): Result<PendingTransactionResponse, AptosSdkError> {
  val signedTransaction = generateSignedTransaction(inputSubmitTransactionData)

  val res =
    postAptosFullNode<PendingTransactionResponse, ByteArray>(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "submitTransaction",
        path = "transactions",
        contentType = MimeType.BCS_SIGNED_TRANSACTION,
        body = signedTransaction,
      )
    )

  val value = res.get()
  return if (value != null) {
    Result.Ok(value.second)
  } else {
    Result.Err(
      res.getError()
        ?: AptosSdkError.UnknownError(IllegalStateException("Missing transaction response"))
    )
  }
}

internal suspend fun signAndSubmitAsFeePayer(
  aptosConfig: TransportConfig,
  feePayer: Account,
  senderAuthenticator: AccountAuthenticator,
  transaction: UnsignedTransaction,
): Result<PendingTransactionResponse, Exception> {

  val sponsorship = signAsFeePayer(feePayer, transaction)

  return submitTransaction(
    aptosConfig,
    InputSubmitTransactionData(
      transaction = sponsorship.transaction,
      senderAuthenticator = senderAuthenticator,
      feePayerAuthenticator = sponsorship.authenticator,
    ),
  )
}

internal suspend fun simulateTransaction(
  aptosConfig: TransportConfig,
  data: InputSimulateTransactionData,
): Result<List<UserTransactionResponse>, AptosSdkError> {
  val signedTransaction = generateSignedTransactionForSimulation(data)

  val resolution =
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

  val simulated = resolution.get()
  return if (simulated != null) {
    Result.Ok(simulated.second)
  } else {
    Result.Err(
      resolution.getError()
        ?: AptosSdkError.UnknownError(IllegalStateException("Missing simulation response"))
    )
  }
}

internal suspend fun publicPackageTransaction(
  aptosConfig: TransportConfig,
  account: AccountAddressInput,
  metadataBytes: HexInput,
  moduleBytecode: List<HexInput>,
  options: TransactionOptions,
): UnsignedTransaction.Simple {
  val totalByteCode = moduleBytecode.map { MoveVector.u8(it) }

  val packagePublishAbi =
    EntryFunctionABI(
      emptyList(),
      listOf(TypeTagVector.u8(), TypeTagVector(type = TypeTagVector.u8())),
    )

  val anyRawTxn =
    generateTransaction(
      aptosConfig = aptosConfig,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = account,
          data =
            entryFunctionData {
              function = "0x1::code::publish_package_txn"
              args(MoveVector.u8(metadataBytes), MoveVector(totalByteCode))
              abi = packagePublishAbi
            },
          options = options,
          withFeePayer = false,
        ),
    )

  return anyRawTxn as UnsignedTransaction.Simple
}
