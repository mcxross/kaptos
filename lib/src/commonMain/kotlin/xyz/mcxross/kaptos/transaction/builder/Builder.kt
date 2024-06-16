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
package xyz.mcxross.kaptos.transaction.builder

import kotlinx.datetime.Clock
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.account.Account
import xyz.mcxross.kaptos.extension.parts
import xyz.mcxross.kaptos.internal.getGasPriceEstimation
import xyz.mcxross.kaptos.internal.getInfo
import xyz.mcxross.kaptos.internal.getLedgerInfo
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.EntryFunction
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticatorEd25519
import xyz.mcxross.kaptos.transaction.authenticatior.TransactionAuthenticator
import xyz.mcxross.kaptos.transaction.instances.AnyRawTransactionInstance
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.util.DEFAULT_MAX_GAS_AMOUNT
import xyz.mcxross.kaptos.util.DEFAULT_TXN_EXP_SEC_FROM_NOW
import xyz.mcxross.kaptos.util.NetworkToChainId

suspend fun generateRawTransaction(
  aptosConfig: AptosConfig,
  sender: AccountAddressInput,
  payload: AnyTransactionPayloadInstance,
  options: InputGenerateTransactionOptions? = null,
  feePayerAddress: AccountAddressInput?,
): RawTransaction {

  val chainId: Long =
    if (NetworkToChainId[aptosConfig.network.name] == null) {
      getLedgerInfo(aptosConfig).unwrap().chainId
    } else {
      NetworkToChainId[aptosConfig.network.name]?.toLong()
        ?: throw IllegalArgumentException(
          "Could not find chain ID for network ${aptosConfig.network.name}"
        )
    }

  val gasUnitPrice =
    options?.gasUnitPrice
      ?: getGasPriceEstimation(aptosConfig).let { response ->
        when (response) {
          is Option.None -> throw IllegalArgumentException("Could not fetch gas price")
          is Option.Some -> response.value.gasEstimate
        }
      }

  val sequenceNumber =
    when (val response = getInfo(aptosConfig, sender)) {
      is Option.None -> throw IllegalArgumentException("Could not fetch sequence number")
      is Option.Some -> {
        response.value.sequenceNumber
      }
    }

  return RawTransaction(
    sender = AccountAddress.from(sender),
    sequenceNumber = sequenceNumber.toLong(),
    payload = payload,
    maxGasAmount = options?.maxGasAmount ?: DEFAULT_MAX_GAS_AMOUNT,
    gasUnitPrice = gasUnitPrice,
    expirationTimestampSecs =
      options?.expireTimestamp
        ?: (Clock.System.now().toEpochMilliseconds() / 1000 + DEFAULT_TXN_EXP_SEC_FROM_NOW),
    chainId = ChainId(chainId.toUByte()),
  )
}

suspend fun buildTransaction(
  aptosConfig: AptosConfig,
  inputGenerateTransactionData: InputGenerateTransactionData,
  payload: AnyTransactionPayloadInstance,
  feePayerAddress: AccountAddressInput?,
): AnyRawTransaction {
  val rawTxn =
    generateRawTransaction(
      aptosConfig = aptosConfig,
      sender = inputGenerateTransactionData.sender,
      payload = payload,
      options = inputGenerateTransactionData.options,
      feePayerAddress = feePayerAddress,
    )

  return SimpleTransaction(
    rawTxn,
    if (feePayerAddress != null) AccountAddress.from(feePayerAddress) else null,
  )
}

suspend fun generateTransactionPayload(
  aptosConfig: AptosConfig,
  data: InputGenerateTransactionPayloadDataWithRemoteABI,
): AnyTransactionPayloadInstance {
  val functionParts =
    (data as InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI)
      .inputEntryFunctionData
      .function
      .parts()

  val functionAbi: Option<EntryFunctionABI> =
    data.inputEntryFunctionData.abi?.let { Option.Some(it) }
      ?: fetchEntryFunctionAbi(
        aptosConfig = aptosConfig,
        moduleAddress = functionParts.first,
        moduleName = functionParts.second,
        functionName = functionParts.third,
      )

  return when (functionAbi) {
    is Option.Some -> {
      val inputEntryFunctionData =
        InputEntryFunctionData(
          function = data.inputEntryFunctionData.function,
          typeArguments = data.inputEntryFunctionData.typeArguments,
          functionArguments = data.inputEntryFunctionData.functionArguments,
          abi = functionAbi.value,
        )
      val abiWithRemoteABI =
        InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI(
          inputEntryFunctionData
        )
      generateTransactionPayloadWithABI(abiWithRemoteABI)
    }
    is Option.None ->
      throw IllegalArgumentException(
        "Could not find function ABI for '${functionParts.first}::${functionParts.second}::${functionParts.third}'"
      )
  }
}

fun generateTransactionPayloadWithABI(
  data: InputGenerateTransactionPayloadDataWithRemoteABI
): AnyTransactionPayloadInstance {
  val functionAbi =
    (data as InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI)
      .inputEntryFunctionData
      .abi

  val parts = data.inputEntryFunctionData.function.parts()

  if (functionAbi != null) {
    if (data.inputEntryFunctionData.typeArguments.size != functionAbi.typeParameters.size) {
      throw IllegalArgumentException(
        "Type argument count does not match the function ABI for '${functionAbi}. Expected ${functionAbi.typeParameters.size}, got '${data.inputEntryFunctionData.typeArguments?.size ?: 0}'"
      )
    }
  }

  if (functionAbi != null) {
    if (data.inputEntryFunctionData.functionArguments.size != functionAbi.parameters.size) {
      throw IllegalArgumentException(
        "Too few arguments for '${parts.first}::${parts.second}::${parts.third}', expected ${functionAbi.parameters.size} but got ${data.inputEntryFunctionData.functionArguments?.size ?: 0}"
      )
    }
  }

  val entryFunctionPayload =
    EntryFunction(
      moduleName = ModuleId(AccountAddress.fromString(parts.first), Identifier(parts.second)),
      functionName = Identifier(parts.third),
      typeArgs = data.inputEntryFunctionData.typeArguments,
      args = data.inputEntryFunctionData.functionArguments,
    )

  return TransactionPayloadEntryFunction(entryFunctionPayload)
}

suspend fun generateViewFunctionPayload(
  aptosConfig: AptosConfig,
  inputViewFunctionData: InputViewFunctionData,
): EntryFunction {

  val functionParts = inputViewFunctionData.function.parts()

  val functionAbi: FunctionABI =
    if (inputViewFunctionData.abi != null) {
      inputViewFunctionData.abi
    } else {
      val response =
        fetchViewFunctionAbi(
          aptosConfig,
          functionParts.first,
          functionParts.second,
          functionParts.third,
        )
      when (response) {
        is Option.Some -> response.value
        is Option.None ->
          throw IllegalArgumentException(
            "Could not find view function ABI for '${functionParts.first}::${functionParts.second}::${functionParts.third}"
          )
      }
    }

  return generateViewFunctionPayloadWithABI(aptosConfig, inputViewFunctionData, functionAbi)
}

fun generateViewFunctionPayloadWithABI(
  aptosConfig: AptosConfig,
  inputViewFunctionData: InputViewFunctionData,
  functionAbi: FunctionABI,
): EntryFunction {
  val parts = inputViewFunctionData.function.parts()

  // Check the type argument count against the ABI
  if (inputViewFunctionData.typeArguments.size != functionAbi.typeParameters.size) {
    throw IllegalArgumentException(
      "Type argument count does not match the function ABI for '${functionAbi}. Expected ${functionAbi.typeParameters.size}, got '${inputViewFunctionData.typeArguments?.size ?: 0}'"
    )
  }

  if (inputViewFunctionData.functionArguments.size != functionAbi.parameters.size) {
    throw IllegalArgumentException(
      "Too few arguments for '${parts.first}::${parts.second}::${parts.third}', expected ${functionAbi.parameters.size} but got ${inputViewFunctionData.functionArguments?.size ?: 0}"
    )
  }

  return EntryFunction(
    moduleName = ModuleId(AccountAddress.fromString(parts.first), Identifier(parts.second)),
    functionName = Identifier(parts.third),
    typeArgs = inputViewFunctionData.typeArguments,
    args = inputViewFunctionData.functionArguments,
  )
}

fun sign(signer: Account, transaction: AnyRawTransaction): AccountAuthenticator {
  val message = generateSigningMessage(transaction)
  return signer.signWithAuthenticator(HexInput.fromByteArray(message))
}

fun generateSigningMessage(transaction: AnyRawTransaction): ByteArray =
  xyz.mcxross.kaptos.core.crypto.generateSigningMessage(transaction)

fun generateSignedTransaction(data: InputSubmitTransactionData): ByteArray {
  val transactionToSubmit = deriveTransactionType(data.transaction)
  val txnEd25519Authenticator = data.senderAuthenticator as AccountAuthenticatorEd25519

  val txnAuthenticator =
    TransactionAuthenticator(
      AccountAuthenticatorVariant.Ed25519,
      txnEd25519Authenticator.publicKey,
      txnEd25519Authenticator.signature,
    )

  return Bcs.encodeToByteArray(transactionToSubmit as RawTransaction) + txnAuthenticator.toBcs()
}

fun deriveTransactionType(transaction: AnyRawTransaction): AnyRawTransactionInstance {
  // TODO Handle FeePayerRawTransaction, MultiAgentRawTransaction
  return when (transaction) {
    is SimpleTransaction -> transaction.rawTransaction
    else -> throw IllegalArgumentException("Unimplemented transaction type")
  }
}
