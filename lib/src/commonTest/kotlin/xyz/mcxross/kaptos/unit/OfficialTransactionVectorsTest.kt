/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertEquals
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.EntryFunctionCall
import xyz.mcxross.kaptos.model.Identifier
import xyz.mcxross.kaptos.model.ModuleId
import xyz.mcxross.kaptos.model.MultisigPayload
import xyz.mcxross.kaptos.model.ScriptPayload
import xyz.mcxross.kaptos.model.TransactionExecutable
import xyz.mcxross.kaptos.model.TransactionExtraConfig
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TypeTagI128
import xyz.mcxross.kaptos.model.TypeTagI16
import xyz.mcxross.kaptos.model.TypeTagI256
import xyz.mcxross.kaptos.model.TypeTagI32
import xyz.mcxross.kaptos.model.TypeTagI64
import xyz.mcxross.kaptos.model.TypeTagI8
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticator.TransactionAuthenticator
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.transaction.instances.SignedTransaction

class OfficialTransactionVectorsTest {
  private val sender = AccountAddress.fromString("0x1")
  private val secondary = AccountAddress.fromString("0x2")
  private val feePayer = AccountAddress.fromString("0x3")

  private val entry =
    EntryFunctionCall(
      module = ModuleId(sender, Identifier("coin")),
      function = Identifier("transfer"),
      arguments = listOf(MoveArgument.U64(123u)),
    )

  private val entryPayload = TransactionPayload.EntryFunction(entry)

  private val raw =
    RawTransaction(
      sender = sender,
      sequenceNumber = 7uL,
      payload = entryPayload,
      maxGasAmount = 2_000_000uL,
      gasUnitPrice = 100uL,
      expirationTimestampSecs = 999_999uL,
      chainId = ChainId(4u),
    )

  @Test
  fun payloadsMatchPinnedTypeScriptSdkAndRoundTrip() {
    val payloads =
      mapOf(
        ENTRY_PAYLOAD to entryPayload,
        SCRIPT_PAYLOAD to
          TransactionPayload.Script(
            ScriptPayload(byteArrayOf(1, 2, 3), arguments = listOf(MoveArgument.U64(456u)))
          ),
        MULTISIG_PAYLOAD to
          TransactionPayload.Multisig(
            multisigAddress = feePayer,
            payload = MultisigPayload.EntryFunction(entry),
          ),
        ORDERLESS_PAYLOAD to
          TransactionPayload.InnerV1(
            executable = TransactionExecutable.EntryFunction(entry),
            extraConfig =
              TransactionExtraConfig.V1(
                replayProtectionNonce = 0xcafebabedeadbeefuL
              ),
          ),
      )

    payloads.forEach { (expected, payload) ->
      assertEquals(expected, payload.toBcs().toHex())
      assertEquals(expected, TransactionPayload.fromBcs(expected.hexBytes()).toBcs().toHex())
    }
  }

  @Test
  fun rawAndSigningMessagesMatchPinnedTypeScriptSdk() {
    assertEquals(RAW_TRANSACTION, raw.toBcs().toHex())
    assertEquals(RAW_TRANSACTION, RawTransaction.fromBcs(raw.toBcs()).toBcs().toHex())

    val orderlessRaw =
      raw.copy(
        sequenceNumber = ULong.MAX_VALUE,
        payload = TransactionPayload.fromBcs(ORDERLESS_PAYLOAD.hexBytes()),
      )
    assertEquals(ORDERLESS_RAW_TRANSACTION, orderlessRaw.toBcs().toHex())

    assertEquals(SIMPLE_SIGNING_MESSAGE, UnsignedTransaction.Simple(raw).signingMessage().toHex())
    assertEquals(
      MULTI_AGENT_SIGNING_MESSAGE,
      UnsignedTransaction.MultiAgent(raw, listOf(secondary)).signingMessage().toHex(),
    )
    assertEquals(
      FEE_PAYER_SIGNING_MESSAGE,
      UnsignedTransaction.FeePayer(raw, listOf(secondary), feePayer).signingMessage().toHex(),
    )
  }

  @Test
  fun authenticatorsMatchPinnedTypeScriptSdk() {
    val account =
      AccountAuthenticator.Ed25519(
        publicKey = Ed25519PublicKey(ByteArray(32) { 0x11 }),
        signature = Ed25519Signature(ByteArray(64) { 0x22 }),
      )
    assertEquals(ACCOUNT_ED25519, account.toBcs().toHex())
    assertEquals(
      ACCOUNT_ED25519,
      AccountAuthenticator.fromBcs(ACCOUNT_ED25519.hexBytes()).toBcs().toHex(),
    )

    val transaction =
      TransactionAuthenticator.MultiAgent(
        sender = account,
        secondarySignerAddresses = listOf(secondary),
        secondarySigners = listOf(account),
      )
    assertEquals(TRANSACTION_MULTI_AGENT, transaction.toBcs().toHex())
    assertEquals(
      TRANSACTION_MULTI_AGENT,
      TransactionAuthenticator.fromBcs(TRANSACTION_MULTI_AGENT.hexBytes()).toBcs().toHex(),
    )
    assertEquals(SIGNED_MULTI_AGENT, SignedTransaction(raw, transaction).toBcs().toHex())
    assertEquals(
      SIGNED_MULTI_AGENT,
      SignedTransaction.fromBcs(SIGNED_MULTI_AGENT.hexBytes()).toBcs().toHex(),
    )
  }

  @Test
  fun signedTypeTagsMatchPinnedTypeScriptSdkAndRoundTrip() {
    val vectors =
      listOf(
        TypeTagI8 to "0b",
        TypeTagI16 to "0c",
        TypeTagI32 to "0d",
        TypeTagI64 to "0e",
        TypeTagI128 to "0f",
        TypeTagI256 to "10",
      )
    vectors.forEach { (type, expected) ->
      val encoded = AptosBcsWriter().also { it.typeTag(type) }.toByteArray()
      assertEquals(expected, encoded.toHex())
      assertEquals(type, AptosBcsReader(encoded).typeTag())
    }
  }

  private fun ByteArray.toHex(): String = joinToString("") { byte ->
    (byte.toInt() and 0xff).toString(16).padStart(2, '0')
  }

  private fun String.hexBytes(): ByteArray =
    chunked(2).map { it.toInt(16).toByte() }.toByteArray()

  private companion object {
    const val ENTRY_PAYLOAD = "02000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b00000000000000"
    const val SCRIPT_PAYLOAD = "0003010203000101c801000000000000"
    const val MULTISIG_PAYLOAD = "0300000000000000000000000000000000000000000000000000000000000000030100000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b00000000000000"
    const val ORDERLESS_PAYLOAD = "040001000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b00000000000000000001efbeaddebebafeca"
    const val RAW_TRANSACTION = "0000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004"
    const val ORDERLESS_RAW_TRANSACTION = "0000000000000000000000000000000000000000000000000000000000000001ffffffffffffffff040001000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b00000000000000000001efbeaddebebafeca80841e000000000064000000000000003f420f000000000004"
    const val SIMPLE_SIGNING_MESSAGE = "b5e97db07fa0bd0e5598aa3643a9bc6f6693bddc1a9fec9e674a461eaa00b1930000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004"
    const val MULTI_AGENT_SIGNING_MESSAGE = "5efa3c4f02f83a0f4b2d69fc95c607cc02825cc4e7be536ef0992df050d9e67c000000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004010000000000000000000000000000000000000000000000000000000000000002"
    const val FEE_PAYER_SIGNING_MESSAGE = "5efa3c4f02f83a0f4b2d69fc95c607cc02825cc4e7be536ef0992df050d9e67c010000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f0000000000040100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003"
    const val ACCOUNT_ED25519 = "002011111111111111111111111111111111111111111111111111111111111111114022222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222"
    const val TRANSACTION_MULTI_AGENT = "0200201111111111111111111111111111111111111111111111111111111111111111402222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222201000000000000000000000000000000000000000000000000000000000000000201002011111111111111111111111111111111111111111111111111111111111111114022222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222"
    const val SIGNED_MULTI_AGENT = RAW_TRANSACTION + TRANSACTION_MULTI_AGENT
  }
}
