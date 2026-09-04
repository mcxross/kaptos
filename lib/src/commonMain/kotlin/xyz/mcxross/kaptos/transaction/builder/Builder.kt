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

import kotlin.time.Clock
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey
import xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey
import xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey
import xyz.mcxross.kaptos.core.crypto.SimulationSignatureProvider
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature
import xyz.mcxross.kaptos.extension.parts
import xyz.mcxross.kaptos.internal.getGasPriceEstimation
import xyz.mcxross.kaptos.internal.getInfo
import xyz.mcxross.kaptos.internal.getLedgerInfo
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.EntryFunction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticator.TransactionAuthenticator
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.transaction.instances.SignedTransaction
import xyz.mcxross.kaptos.util.DEFAULT_TXN_EXP_SEC_FROM_NOW
import xyz.mcxross.kaptos.util.NetworkToChainId

internal suspend fun generateRawTransaction(
  aptosConfig: TransportConfig,
  sender: AccountAddressInput,
  payload: TransactionPayload,
  options: TransactionOptions? = null,
): RawTransaction {
  val networkName = aptosConfig.network.name.lowercase()
  val chainId =
    NetworkToChainId[networkName]?.toLong()
      ?: getLedgerInfo(aptosConfig).expect("Could not fetch ledger info").chainId

  val gasUnitPrice =
    options?.gasUnitPrice
      ?: when (val response = getGasPriceEstimation(aptosConfig)) {
        is Result.Ok -> response.value.gasEstimate.toULong()
        is Result.Err -> throw IllegalArgumentException("Could not fetch gas price")
      }

  val sequenceNumber =
    when (val replayProtection = options?.replayProtection) {
      is ReplayProtection.Nonce -> ULong.MAX_VALUE
      is ReplayProtection.SequenceNumber -> replayProtection.value
      null ->
        when (val response = getInfo(aptosConfig, sender)) {
          is Result.Ok -> response.value.sequenceNumber
          is Result.Err -> throw IllegalArgumentException("Could not fetch sequence number")
        }
    }

  val wirePayload =
    when (val replayProtection = options?.replayProtection) {
      is ReplayProtection.Nonce -> payload.withNonce(replayProtection.value)
      else -> payload
    }

  val expirationTimestamp =
    options?.expirationTimestampSecs
      ?: run {
        val now = (Clock.System.now().toEpochMilliseconds() / 1000).toULong()
        val delta = options?.expirationSecondsFromNow ?: DEFAULT_TXN_EXP_SEC_FROM_NOW.toULong()
        require(delta <= ULong.MAX_VALUE - now) { "Transaction expiration overflows Aptos u64" }
        now + delta
      }

  return RawTransaction(
    sender = AccountAddress.from(sender),
    sequenceNumber = sequenceNumber,
    payload = wirePayload,
    maxGasAmount = options?.maxGasAmount ?: 2_000_000uL,
    gasUnitPrice = gasUnitPrice,
    expirationTimestampSecs = expirationTimestamp,
    chainId = ChainId(chainId.toUByte()),
  )
}

private fun TransactionPayload.withNonce(nonce: ULong): TransactionPayload.InnerV1 =
  when (this) {
    is TransactionPayload.EntryFunction ->
      TransactionPayload.InnerV1(
        executable = TransactionExecutable.EntryFunction(call),
        extraConfig = TransactionExtraConfig.V1(replayProtectionNonce = nonce),
      )
    is TransactionPayload.Script ->
      TransactionPayload.InnerV1(
        executable = TransactionExecutable.Script(script),
        extraConfig = TransactionExtraConfig.V1(replayProtectionNonce = nonce),
      )
    is TransactionPayload.Multisig ->
      TransactionPayload.InnerV1(
        executable =
          when (val inner = payload) {
            is MultisigPayload.EntryFunction -> TransactionExecutable.EntryFunction(inner.call)
            is MultisigPayload.Script -> TransactionExecutable.Script(inner.script)
            null -> TransactionExecutable.Empty
          },
        extraConfig =
          TransactionExtraConfig.V1(
            multisigAddress = multisigAddress,
            replayProtectionNonce = nonce,
          ),
      )
    is TransactionPayload.InnerV1 ->
      copy(
        extraConfig =
          when (val config = extraConfig) {
            is TransactionExtraConfig.V1 -> config.copy(replayProtectionNonce = nonce)
          }
      )
    is TransactionPayload.Encrypted ->
      throw IllegalArgumentException(
        "Encrypted payload replay protection must be configured before encryption"
      )
  }

internal suspend fun buildTransaction(
  aptosConfig: TransportConfig,
  inputGenerateTransactionData: InputGenerateTransactionData,
  payload: TransactionPayload,
  feePayerAddress: AccountAddressInput?,
): UnsignedTransaction {
  val rawTxn =
    generateRawTransaction(
      aptosConfig = aptosConfig,
      sender = inputGenerateTransactionData.sender,
      payload = payload,
      options = inputGenerateTransactionData.options,
    )

  val secondarySignerAddresses =
    (inputGenerateTransactionData as? InputGenerateMultiSignerRawTransactionData)
      ?.secondarySignerAddresses
      ?.map(AccountAddress::from)
      .orEmpty()

  return when {
    inputGenerateTransactionData.withFeePayer ->
      UnsignedTransaction.FeePayer(
        rawTransaction = rawTxn,
        secondarySignerAddresses = secondarySignerAddresses,
        feePayerAddress =
          feePayerAddress?.let(AccountAddress::from)
            ?: UnsignedTransaction.EXTERNAL_FEE_PAYER_PLACEHOLDER,
      )
    secondarySignerAddresses.isNotEmpty() ->
      UnsignedTransaction.MultiAgent(rawTxn, secondarySignerAddresses)
    else -> UnsignedTransaction.Simple(rawTxn)
  }
}

internal suspend fun generateTransactionPayload(
  aptosConfig: TransportConfig,
  data: InputGenerateTransactionPayloadDataWithRemoteABI,
): TransactionPayload {
  val functionParts =
    (data as InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI)
      .inputEntryFunctionData
      .function
      .parts()

  val functionAbi: Result<EntryFunctionABI, Exception> =
    data.inputEntryFunctionData.abi?.let { Result.Ok(it) }
      ?: fetchEntryFunctionAbi(
        aptosConfig = aptosConfig,
        moduleAddress = functionParts.first,
        moduleName = functionParts.second,
        functionName = functionParts.third,
      )

  return when (functionAbi) {
    is Result.Ok -> {
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
    is Result.Err ->
      throw IllegalArgumentException(
        "Could not find function ABI for '${functionParts.first}::${functionParts.second}::${functionParts.third}'"
      )
  }
}

internal fun generateTransactionPayloadWithABI(
  data: InputGenerateTransactionPayloadDataWithRemoteABI
): TransactionPayload {
  val functionAbi =
    (data as InputEntryFunctionGenerateTransactionPayloadDataWithRemoteABIWithRemoteABI)
      .inputEntryFunctionData
      .abi

  val parts = data.inputEntryFunctionData.function.parts()

  if (functionAbi != null) {
    if (data.inputEntryFunctionData.typeArguments.size != functionAbi.typeParameters.size) {
      throw IllegalArgumentException(
        "Type argument count does not match the function ABI for '${functionAbi}. Expected ${functionAbi.typeParameters.size}, got '${data.inputEntryFunctionData.typeArguments.size}'"
      )
    }
  }

  if (functionAbi != null) {
    if (data.inputEntryFunctionData.functionArguments.size != functionAbi.parameters.size) {
      throw IllegalArgumentException(
        "Too few arguments for '${parts.first}::${parts.second}::${parts.third}', expected ${functionAbi.parameters.size} but got ${data.inputEntryFunctionData.functionArguments.size}"
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

  return TransactionPayload.EntryFunction(
    EntryFunctionCall(
      module = entryFunctionPayload.moduleName,
      function = entryFunctionPayload.functionName,
      typeArguments = entryFunctionPayload.typeArgs,
      arguments = entryFunctionPayload.args.map { it.toMoveArgument() },
    )
  )
}

private fun EntryFunctionArgument.toMoveArgument(): MoveArgument =
  when (this) {
    is Bool -> MoveArgument.Bool(value)
    is U8 -> MoveArgument.U8(value.toUByte())
    is U16 -> MoveArgument.U16(value)
    is U32 -> MoveArgument.U32(value)
    is U64 -> MoveArgument.U64(value)
    is U128 -> MoveArgument.U128(value)
    is U256 -> MoveArgument.U256(value)
    is AccountAddress -> MoveArgument.Address(this)
    is MoveString -> MoveArgument.StringValue(value)
    is MoveVector<*> -> MoveArgument.Vector(values.map { it.toMoveArgument() })
    is MoveOption<*> -> MoveArgument.Option(value?.toMoveArgument())
    is HexInput -> MoveArgument.PreSerialized(value.decodeHex())
    else -> throw IllegalArgumentException("Unsupported Move argument ${this::class.simpleName}")
  }

private fun String.decodeHex(): ByteArray {
  val normalized = removePrefix("0x").removePrefix("0X")
  require(normalized.length % 2 == 0 && normalized.all { it.isDigit() || it in 'a'..'f' || it in 'A'..'F' }) {
    "Invalid pre-serialized hex argument"
  }
  return ByteArray(normalized.length / 2) { index ->
    normalized.substring(index * 2, index * 2 + 2).toInt(16).toByte()
  }
}

internal suspend fun generateViewFunctionPayload(
  aptosConfig: TransportConfig,
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
        is Result.Ok -> response.value
        is Result.Err ->
          throw IllegalArgumentException(
            "Could not find view function ABI for '${functionParts.first}::${functionParts.second}::${functionParts.third}"
          )
      }
    }

  return generateViewFunctionPayloadWithABI(aptosConfig, inputViewFunctionData, functionAbi)
}

internal fun generateViewFunctionPayloadWithABI(
  aptosConfig: TransportConfig,
  inputViewFunctionData: InputViewFunctionData,
  functionAbi: FunctionABI,
): EntryFunction {
  val parts = inputViewFunctionData.function.parts()

  // Check the type argument count against the ABI
  if (inputViewFunctionData.typeArguments.size != functionAbi.typeParameters.size) {
    throw IllegalArgumentException(
      "Type argument count does not match the function ABI for '${functionAbi}. Expected ${functionAbi.typeParameters.size}, got '${inputViewFunctionData.typeArguments.size}'"
    )
  }

  if (inputViewFunctionData.functionArguments.size != functionAbi.parameters.size) {
    throw IllegalArgumentException(
      "Too few arguments for '${parts.first}::${parts.second}::${parts.third}', expected ${functionAbi.parameters.size} but got ${inputViewFunctionData.functionArguments.size}"
    )
  }

  return EntryFunction(
    moduleName = ModuleId(AccountAddress.fromString(parts.first), Identifier(parts.second)),
    functionName = Identifier(parts.third),
    typeArgs = inputViewFunctionData.typeArguments,
    args = inputViewFunctionData.functionArguments,
  )
}

internal fun sign(signer: Account, transaction: UnsignedTransaction): AccountAuthenticator {
  val message = transaction.signingMessage()
  return signer.signWithAuthenticator(HexInput.fromByteArray(message))
}

internal fun generateSignedTransaction(data: InputSubmitTransactionData): ByteArray {
  val authenticator =
    when (val transaction = data.transaction) {
      is UnsignedTransaction.Simple ->
        TransactionAuthenticator.singleSender(data.senderAuthenticator)
      is UnsignedTransaction.MultiAgent -> {
        TransactionAuthenticator.MultiAgent(
          sender = data.senderAuthenticator,
          secondarySignerAddresses = transaction.secondarySignerAddresses,
          secondarySigners = data.additionalSignersAuthenticators,
        )
      }
      is UnsignedTransaction.FeePayer ->
        TransactionAuthenticator.FeePayer(
          sender = data.senderAuthenticator,
          secondarySignerAddresses = transaction.secondarySignerAddresses,
          secondarySigners = data.additionalSignersAuthenticators,
          feePayerAddress = transaction.feePayerAddress,
          feePayer =
            requireNotNull(data.feePayerAuthenticator) {
              "A fee-payer transaction requires a fee-payer authenticator"
            },
        )
    }
  return SignedTransaction(data.transaction.rawTransaction, authenticator).toBcs()
}

internal fun generateSignedTransactionForSimulation(data: InputSimulateTransactionData): ByteArray {
  val sender = simulationAuthenticator(data.signerPublicKey)
  val secondarySigners =
    if (data.secondarySignerPublicKeys.isEmpty()) {
      val count =
        when (val transaction = data.transaction) {
          is UnsignedTransaction.Simple -> 0
          is UnsignedTransaction.MultiAgent -> transaction.secondarySignerAddresses.size
          is UnsignedTransaction.FeePayer -> transaction.secondarySignerAddresses.size
        }
      List(count) { AccountAuthenticator.NoAccount }
    } else {
      data.secondarySignerPublicKeys.map(::simulationAuthenticator)
    }
  val transactionAuthenticator =
    when (val transaction = data.transaction) {
      is UnsignedTransaction.Simple -> TransactionAuthenticator.singleSender(sender)
      is UnsignedTransaction.MultiAgent ->
        TransactionAuthenticator.MultiAgent(
          sender = sender,
          secondarySignerAddresses = transaction.secondarySignerAddresses,
          secondarySigners = secondarySigners,
        )
      is UnsignedTransaction.FeePayer ->
        TransactionAuthenticator.FeePayer(
          sender = sender,
          secondarySignerAddresses = transaction.secondarySignerAddresses,
          secondarySigners = secondarySigners,
          feePayerAddress = transaction.feePayerAddress,
          feePayer = data.feePayerPublicKey?.let(::simulationAuthenticator)
            ?: AccountAuthenticator.NoAccount,
        )
    }
  return SignedTransaction(data.transaction.rawTransaction, transactionAuthenticator).toBcs()
}

private fun simulationAuthenticator(publicKey: PublicKey): AccountAuthenticator {
  val invalidEd25519Signature = Ed25519Signature(ByteArray(Ed25519Signature.LENGTH))
  return when (publicKey) {
    is Ed25519PublicKey ->
      AccountAuthenticator.Ed25519(publicKey, invalidEd25519Signature)
    is Secp256k1PublicKey ->
      AccountAuthenticator.SingleKey(
        publicKey = AnyPublicKey(publicKey),
        signature = AnySignature(invalidEd25519Signature),
      )
    is Secp256r1PublicKey ->
      AccountAuthenticator.SingleKey(
        publicKey = AnyPublicKey(publicKey),
        signature = AnySignature(invalidEd25519Signature),
      )
    is SimulationSignatureProvider ->
      AccountAuthenticator.SingleKey(
        publicKey = AnyPublicKey(publicKey),
        signature = AnySignature(publicKey.simulationSignature()),
      )
    is AnyPublicKey ->
      AccountAuthenticator.SingleKey(
        publicKey = publicKey,
        signature =
          AnySignature(
            (publicKey.publicKey as? SimulationSignatureProvider)?.simulationSignature()
              ?: invalidEd25519Signature
          ),
      )
    is MultiKey -> {
      val signatures = publicKey.publicKeys.map { AnySignature(invalidEd25519Signature) }
      AccountAuthenticator.MultiKey(
        publicKey = publicKey,
        signature =
          MultiKeySignature(
            signatures = signatures,
            bitmap = MultiKeySignature.createBitmap(publicKey.publicKeys.indices.toList()),
          ),
      )
    }
    is MultiEd25519PublicKey -> {
      val signerIndices = (0..<publicKey.threshold.toInt()).toList()
      AccountAuthenticator.MultiEd25519(
        publicKey = publicKey,
        signature =
          MultiEd25519Signature(
            signatures = List(signerIndices.size) { invalidEd25519Signature },
            bitmap = MultiEd25519Signature.bitmapOf(signerIndices),
          ),
      )
    }
    else -> throw IllegalArgumentException(
      "Unsupported public key used for simulation: ${publicKey::class.simpleName}"
    )
  }
}
