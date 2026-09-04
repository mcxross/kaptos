/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

import xyz.mcxross.kaptos.extension.parts
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.bcs.littleEndianSignedToDecimal
import xyz.mcxross.kaptos.transaction.bcs.littleEndianUnsignedToDecimal

/** Exact wire model for Aptos transaction payload variants. */
sealed interface TransactionPayload {
  fun toBcs(): ByteArray = AptosBcsWriter().also { encode(it) }.toByteArray()

  data class Script(val script: ScriptPayload) : TransactionPayload

  data class EntryFunction(val call: EntryFunctionCall) : TransactionPayload

  data class Multisig(
    val multisigAddress: AccountAddress,
    val payload: MultisigPayload? = null,
  ) : TransactionPayload

  /** V1 inner payload used by orderless transactions and future transaction extensions. */
  data class InnerV1(
    val executable: TransactionExecutable,
    val extraConfig: TransactionExtraConfig = TransactionExtraConfig.V1(),
  ) : TransactionPayload

  data class Encrypted(val payload: EncryptedTransactionPayload) : TransactionPayload

  companion object {
    /** Concise factory for the most common payload form. */
    fun entryFunction(
      function: String,
      typeArguments: List<TypeTag> = emptyList(),
      arguments: List<MoveArgument> = emptyList(),
    ): EntryFunction {
      val (address, module, name) = function.parts()
      return EntryFunction(
        EntryFunctionCall(
          module = ModuleId(AccountAddress.fromString(address), Identifier(module)),
          function = Identifier(name),
          typeArguments = typeArguments,
          arguments = arguments,
        )
      )
    }

    fun fromBcs(bytes: ByteArray): TransactionPayload =
      AptosBcsReader(bytes).let { reader -> reader.transactionPayload().also { reader.ensureFinished() } }
  }
}

data class EntryFunctionCall(
  val module: ModuleId,
  val function: Identifier,
  val typeArguments: List<TypeTag> = emptyList(),
  val arguments: List<MoveArgument> = emptyList(),
)

class ScriptPayload(
  bytecode: ByteArray,
  val typeArguments: List<TypeTag> = emptyList(),
  val arguments: List<MoveArgument> = emptyList(),
) {
  private val bytecodeBytes = bytecode.copyOf()
  val bytecode: ByteArray
    get() = bytecodeBytes.copyOf()

  override fun equals(other: Any?): Boolean =
    other is ScriptPayload &&
      bytecodeBytes.contentEquals(other.bytecodeBytes) &&
      typeArguments == other.typeArguments &&
      arguments == other.arguments

  override fun hashCode(): Int =
    31 * (31 * bytecodeBytes.contentHashCode() + typeArguments.hashCode()) + arguments.hashCode()

  override fun toString(): String =
    "ScriptPayload(bytecode=${bytecodeBytes.size} bytes, typeArguments=$typeArguments, arguments=$arguments)"
}

sealed interface MultisigPayload {
  data class EntryFunction(val call: EntryFunctionCall) : MultisigPayload

  data class Script(val script: ScriptPayload) : MultisigPayload
}

sealed interface TransactionExecutable {
  /** Exact Aptos BCS bytes used inside an encrypted plaintext. */
  fun toBcs(): ByteArray = AptosBcsWriter().also { it.executable(this) }.toByteArray()

  data class Script(val script: ScriptPayload) : TransactionExecutable

  data class EntryFunction(val call: EntryFunctionCall) : TransactionExecutable

  data object Empty : TransactionExecutable

  /** Server-side sentinel placed in a payload after decryption. */
  data object Encrypted : TransactionExecutable

  companion object {
    fun fromBcs(bytes: ByteArray): TransactionExecutable =
      AptosBcsReader(bytes).let { reader -> reader.executable().also { reader.ensureFinished() } }
  }
}

sealed interface TransactionExtraConfig {
  fun toBcs(): ByteArray = AptosBcsWriter().also { it.extraConfig(this) }.toByteArray()

  data class V1(
    val multisigAddress: AccountAddress? = null,
    val replayProtectionNonce: ULong? = null,
  ) : TransactionExtraConfig

  companion object {
    fun fromBcs(bytes: ByteArray): TransactionExtraConfig =
      AptosBcsReader(bytes).let { reader -> reader.extraConfig().also { reader.ensureFinished() } }
  }
}

/** Batch-encryption wire record. Cryptographic construction lives in kaptos-encrypted-transactions. */
data class EncryptedTransactionPayload(
  val ciphertext: EncryptedCiphertext,
  val extraConfig: TransactionExtraConfig,
  val payloadHash: FixedBytes32,
  val encryptionEpoch: ULong,
  val claimedEntryFunction: ClaimedEntryFunction? = null,
)

data class ClaimedEntryFunction(
  val module: ModuleId,
  val function: Identifier? = null,
)

data class EncryptedCiphertext(
  val verificationKey: FixedBytes32,
  val bibeCiphertext: BibeCiphertext,
  val associatedData: ByteString,
  val signature: FixedBytes64,
) {
  fun toBcs(): ByteArray = AptosBcsWriter().also { it.encryptedCiphertext(this) }.toByteArray()

  companion object {
    fun fromBcs(bytes: ByteArray): EncryptedCiphertext =
      AptosBcsReader(bytes).let { reader ->
        reader.encryptedCiphertext().also { reader.ensureFinished() }
      }
  }
}

data class BibeCiphertext(
  val id: ByteString,
  val threeG2Points: ByteString,
  val paddedKey: FixedBytes16,
  val nonce: FixedBytes12,
  val body: ByteString,
) {
  init {
    require(threeG2Points.size == 288) { "Batch-encryption ciphertext must contain three 96-byte G2 points" }
  }
}

/** Immutable byte sequence with content equality. */
open class ByteString(value: ByteArray) {
  private val bytes = value.copyOf()
  val size: Int
    get() = bytes.size

  fun toByteArray(): ByteArray = bytes.copyOf()

  override fun equals(other: Any?): Boolean = other is ByteString && bytes.contentEquals(other.bytes)

  override fun hashCode(): Int = bytes.contentHashCode()

  override fun toString(): String = "ByteString($size bytes)"
}

class FixedBytes12(value: ByteArray) : ByteString(value) {
  init {
    require(size == 12) { "Expected 12 bytes, got $size" }
  }
}

class FixedBytes16(value: ByteArray) : ByteString(value) {
  init {
    require(size == 16) { "Expected 16 bytes, got $size" }
  }
}

class FixedBytes32(value: ByteArray) : ByteString(value) {
  init {
    require(size == 32) { "Expected 32 bytes, got $size" }
  }
}

class FixedBytes64(value: ByteArray) : ByteString(value) {
  init {
    require(size == 64) { "Expected 64 bytes, got $size" }
  }
}

private fun TransactionPayload.encode(writer: AptosBcsWriter) {
  when (this) {
    is TransactionPayload.Script -> {
      writer.uleb128(0u)
      writer.script(script)
    }
    is TransactionPayload.EntryFunction -> {
      writer.uleb128(2u)
      writer.entryFunction(call)
    }
    is TransactionPayload.Multisig -> {
      writer.uleb128(3u)
      writer.accountAddress(multisigAddress)
      writer.option(payload) { multisigPayload(it) }
    }
    is TransactionPayload.InnerV1 -> {
      writer.uleb128(4u)
      writer.uleb128(0u)
      writer.executable(executable)
      writer.extraConfig(extraConfig)
    }
    is TransactionPayload.Encrypted -> {
      writer.uleb128(5u)
      writer.uleb128(0u)
      writer.encryptedPayload(payload)
    }
  }
}

private fun AptosBcsWriter.entryFunction(value: EntryFunctionCall) {
  accountAddress(value.module.address)
  string(value.module.name.toString())
  string(value.function.toString())
  vector(value.typeArguments) { typeTag(it) }
  vector(value.arguments) { bytes(it.toBcs()) }
}

private fun AptosBcsWriter.script(value: ScriptPayload) {
  bytes(value.bytecode)
  vector(value.typeArguments) { typeTag(it) }
  vector(value.arguments) { scriptArgument(it) }
}

private fun AptosBcsWriter.scriptArgument(value: MoveArgument) {
  when (value) {
    is MoveArgument.U8 -> {
      uleb128(0u)
      u8(value.value)
    }
    is MoveArgument.U64 -> {
      uleb128(1u)
      u64(value.value)
    }
    is MoveArgument.U128 -> {
      uleb128(2u)
      fixed(value.toBcs())
    }
    is MoveArgument.Address -> {
      uleb128(3u)
      accountAddress(value.value)
    }
    is MoveArgument.Bytes -> {
      uleb128(4u)
      bytes(value.value)
    }
    is MoveArgument.StringValue -> {
      uleb128(4u)
      bytes(value.value.encodeToByteArray())
    }
    is MoveArgument.Vector -> {
      require(value.values.all { it is MoveArgument.U8 }) {
        "Only vector<u8> is supported by script arguments"
      }
      uleb128(4u)
      bytes(value.values.map { (it as MoveArgument.U8).value.toByte() }.toByteArray())
    }
    is MoveArgument.Bool -> {
      uleb128(5u)
      bool(value.value)
    }
    is MoveArgument.U16 -> {
      uleb128(6u)
      u16(value.value)
    }
    is MoveArgument.U32 -> {
      uleb128(7u)
      u32(value.value)
    }
    is MoveArgument.U256 -> {
      uleb128(8u)
      fixed(value.toBcs())
    }
    is MoveArgument.PreSerialized -> {
      uleb128(9u)
      bytes(value.value)
    }
    is MoveArgument.I8 -> {
      uleb128(10u)
      fixed(value.toBcs())
    }
    is MoveArgument.I16 -> {
      uleb128(11u)
      fixed(value.toBcs())
    }
    is MoveArgument.I32 -> {
      uleb128(12u)
      fixed(value.toBcs())
    }
    is MoveArgument.I64 -> {
      uleb128(13u)
      fixed(value.toBcs())
    }
    is MoveArgument.I128 -> {
      uleb128(14u)
      fixed(value.toBcs())
    }
    is MoveArgument.I256 -> {
      uleb128(15u)
      fixed(value.toBcs())
    }
    is MoveArgument.Option,
    is MoveArgument.Struct,
    is MoveArgument.Enum ->
      throw IllegalArgumentException("${value::class.simpleName} is not supported by script arguments")
  }
}

private fun AptosBcsWriter.multisigPayload(value: MultisigPayload) {
  when (value) {
    is MultisigPayload.EntryFunction -> {
      uleb128(0u)
      entryFunction(value.call)
    }
    is MultisigPayload.Script -> {
      uleb128(1u)
      script(value.script)
    }
  }
}

private fun AptosBcsWriter.executable(value: TransactionExecutable) {
  when (value) {
    is TransactionExecutable.Script -> {
      uleb128(0u)
      script(value.script)
    }
    is TransactionExecutable.EntryFunction -> {
      uleb128(1u)
      entryFunction(value.call)
    }
    TransactionExecutable.Empty -> uleb128(2u)
    TransactionExecutable.Encrypted -> uleb128(3u)
  }
}

private fun AptosBcsWriter.extraConfig(value: TransactionExtraConfig) {
  when (value) {
    is TransactionExtraConfig.V1 -> {
      uleb128(0u)
      option(value.multisigAddress) { accountAddress(it) }
      option(value.replayProtectionNonce) { u64(it) }
    }
  }
}

private fun AptosBcsWriter.encryptedPayload(value: EncryptedTransactionPayload) {
  encryptedCiphertext(value.ciphertext)
  extraConfig(value.extraConfig)
  fixed(value.payloadHash.toByteArray())
  u64(value.encryptionEpoch)
  option(value.claimedEntryFunction) {
    accountAddress(it.module.address)
    string(it.module.name.toString())
    option(it.function) { function -> string(function.toString()) }
  }
}

private fun AptosBcsWriter.encryptedCiphertext(value: EncryptedCiphertext) {
  val ciphertext = value
  bytes(ciphertext.verificationKey.toByteArray())
  bytes(ciphertext.bibeCiphertext.id.toByteArray())
  bytes(ciphertext.bibeCiphertext.threeG2Points.toByteArray())
  fixed(ciphertext.bibeCiphertext.paddedKey.toByteArray())
  fixed(ciphertext.bibeCiphertext.nonce.toByteArray())
  bytes(ciphertext.bibeCiphertext.body.toByteArray())
  bytes(ciphertext.associatedData.toByteArray())
  fixed(ciphertext.signature.toByteArray())
}

internal fun AptosBcsReader.transactionPayload(): TransactionPayload =
  when (val variant = uleb128()) {
    0u -> TransactionPayload.Script(script())
    2u -> TransactionPayload.EntryFunction(entryFunction())
    3u ->
      TransactionPayload.Multisig(
        multisigAddress = accountAddress(),
        payload = option { multisigPayload() },
      )
    4u -> {
      require(uleb128() == 0u) { "Unsupported TransactionInnerPayload variant" }
      TransactionPayload.InnerV1(executable(), extraConfig())
    }
    5u -> {
      require(uleb128() == 0u) { "Only EncryptedPayload::Encrypted is supported" }
      TransactionPayload.Encrypted(encryptedPayload())
    }
    else -> throw IllegalArgumentException("Unsupported TransactionPayload variant: $variant")
  }

private fun AptosBcsReader.entryFunction(): EntryFunctionCall =
  EntryFunctionCall(
    module = ModuleId(accountAddress(), Identifier(string())),
    function = Identifier(string()),
    typeArguments = vector { typeTag() },
    arguments = vector { MoveArgument.PreSerialized(bytes()) },
  )

private fun AptosBcsReader.script(): ScriptPayload =
  ScriptPayload(
    bytecode = bytes(),
    typeArguments = vector { typeTag() },
    arguments = vector { scriptArgument() },
  )

private fun AptosBcsReader.scriptArgument(): MoveArgument =
  when (val variant = uleb128()) {
    0u -> MoveArgument.U8(u8())
    1u -> MoveArgument.U64(u64())
    2u -> MoveArgument.U128(littleEndianUnsignedToDecimal(fixed(16)))
    3u -> MoveArgument.Address(accountAddress())
    4u -> MoveArgument.Bytes(bytes())
    5u -> MoveArgument.Bool(bool())
    6u -> MoveArgument.U16(u16())
    7u -> MoveArgument.U32(u32())
    8u -> MoveArgument.U256(littleEndianUnsignedToDecimal(fixed(32)))
    9u -> MoveArgument.PreSerialized(bytes())
    10u -> MoveArgument.I8(u8().toByte())
    11u -> MoveArgument.I16(u16().toShort())
    12u -> MoveArgument.I32(u32().toInt())
    13u -> MoveArgument.I64(u64().toLong())
    14u -> MoveArgument.I128(littleEndianSignedToDecimal(fixed(16)))
    15u -> MoveArgument.I256(littleEndianSignedToDecimal(fixed(32)))
    else -> throw IllegalArgumentException("Unsupported script argument variant: $variant")
  }

private fun AptosBcsReader.multisigPayload(): MultisigPayload =
  when (val variant = uleb128()) {
    0u -> MultisigPayload.EntryFunction(entryFunction())
    1u -> MultisigPayload.Script(script())
    else -> throw IllegalArgumentException("Unsupported multisig payload variant: $variant")
  }

private fun AptosBcsReader.executable(): TransactionExecutable =
  when (val variant = uleb128()) {
    0u -> TransactionExecutable.Script(script())
    1u -> TransactionExecutable.EntryFunction(entryFunction())
    2u -> TransactionExecutable.Empty
    3u -> TransactionExecutable.Encrypted
    else -> throw IllegalArgumentException("Unsupported TransactionExecutable variant: $variant")
  }

private fun AptosBcsReader.extraConfig(): TransactionExtraConfig {
  require(uleb128() == 0u) { "Unsupported TransactionExtraConfig variant" }
  return TransactionExtraConfig.V1(
    multisigAddress = option { accountAddress() },
    replayProtectionNonce = option { u64() },
  )
}

private fun AptosBcsReader.encryptedPayload(): EncryptedTransactionPayload {
  val ciphertext = encryptedCiphertext()
  val extraConfig = extraConfig()
  val payloadHash = FixedBytes32(fixed(32))
  val epoch = u64()
  val claimed =
    option {
      ClaimedEntryFunction(
        module = ModuleId(accountAddress(), Identifier(string())),
        function = option { Identifier(string()) },
      )
    }
  return EncryptedTransactionPayload(ciphertext, extraConfig, payloadHash, epoch, claimed)
}

private fun AptosBcsReader.encryptedCiphertext(): EncryptedCiphertext =
  EncryptedCiphertext(
    verificationKey = FixedBytes32(bytes()),
    bibeCiphertext =
      BibeCiphertext(
        id = ByteString(bytes()),
        threeG2Points = ByteString(bytes()),
        paddedKey = FixedBytes16(fixed(16)),
        nonce = FixedBytes12(fixed(12)),
        body = ByteString(bytes()),
      ),
    associatedData = ByteString(bytes()),
    signature = FixedBytes64(fixed(64)),
  )
