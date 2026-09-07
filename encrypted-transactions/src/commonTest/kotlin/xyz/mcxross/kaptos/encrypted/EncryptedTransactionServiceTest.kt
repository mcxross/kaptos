package xyz.mcxross.kaptos.encrypted

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import xyz.mcxross.kaptos.ledger.LedgerState
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.BibeCiphertext
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.EncryptedCiphertext
import xyz.mcxross.kaptos.model.FixedBytes12
import xyz.mcxross.kaptos.model.FixedBytes16
import xyz.mcxross.kaptos.model.FixedBytes32
import xyz.mcxross.kaptos.model.FixedBytes64
import xyz.mcxross.kaptos.model.TransactionExecutable
import xyz.mcxross.kaptos.model.TransactionExtraConfig
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction

class EncryptedTransactionServiceTest {
  @Test
  fun realFastKryptoBackendProducesAnAptosCiphertext() {
    // EncryptionKey(G2.generator, 2 * G2.generator), serialized by Aptos TS SDK 7.3.1.
    val encryptionKey =
      hex(
        "6093e02b6052719f607dacd3a088274f65596bd0d09920b61ab5da61bbdc7f5049334cf11213945d57e5ac7d055d042b7e024aa2b2f08f0a91260805272dc51051c6e47ad4fa403b02b4510b647ae3d1770bac0326a805bbefd48056c8c121bdb860aa4edef9c1ed7f729f520e47730a124fd70662a904ba1074728114d1031e1572c6c886f6b57ec72a6178288c47c335771638533957d540a9d2370f17cc7ed5863bc0b995b8825e0ee1ea1e1e4d00dbae81f14b0bf3611b78c952aacab827a053"
      )
    val plaintext = byteArrayOf(1, 2, 3, 4)
    val associatedData = byteArrayOf(5, 6, 7)

    val ciphertext =
      EncryptedCiphertext.fromBcs(
        FastKryptoBatchEncryption.encrypt(encryptionKey, plaintext, associatedData)
      )

    assertContentEquals(associatedData, ciphertext.associatedData.toByteArray())
    assertEquals(plaintext.size + 16, ciphertext.bibeCiphertext.body.size)
    assertEquals(32, ciphertext.bibeCiphertext.id.size)
    assertEquals(288, ciphertext.bibeCiphertext.threeG2Points.size)
  }

  @Test
  fun encryptsOrderlessFeePayerWithExactSignerOrderAndGasFloor() = runTest {
    val sender = address(1)
    val secondary = address(2)
    val feePayer = address(3)
    val replayNonce = ULong.MAX_VALUE - 1uL
    val executable =
      TransactionPayload.entryFunction("0x1::coin::transfer").let {
        TransactionExecutable.EntryFunction(it.call)
      }
    val transaction =
      UnsignedTransaction.FeePayer(
        rawTransaction =
          raw(
            sender = sender,
            payload =
              TransactionPayload.InnerV1(
                executable,
                TransactionExtraConfig.V1(replayProtectionNonce = replayNonce),
              ),
            gasPrice = 1uL,
            sequenceNumber = ULong.MAX_VALUE,
          ),
        secondarySignerAddresses = listOf(secondary),
        feePayerAddress = feePayer,
      )
    val context = FakeContext()
    val crypto = FakeCrypto()
    val service = DefaultEncryptedTransactionService(context, crypto = crypto)

    val result = assertIs<AptosResult.Success<UnsignedTransaction>>(service.encrypt(transaction))
    val encrypted = assertIs<UnsignedTransaction.FeePayer>(result.value)
    assertEquals(200uL, encrypted.rawTransaction.gasUnitPrice)
    val payload = assertIs<TransactionPayload.Encrypted>(encrypted.rawTransaction.payload).payload
    assertEquals(9uL, payload.encryptionEpoch)
    assertEquals(
      replayNonce,
      assertIs<TransactionExtraConfig.V1>(payload.extraConfig).replayProtectionNonce,
    )
    assertEquals(executable.call.module, payload.claimedEntryFunction?.module)
    assertEquals(executable.call.function, payload.claimedEntryFunction?.function)

    val expectedAad =
      byteArrayOf(0) +
        sender.data +
        byteArrayOf(3) +
        signerPair(sender, 1) +
        signerPair(secondary, 2) +
        signerPair(feePayer, 3)
    assertContentEquals(expectedAad, crypto.associatedData)
    assertContentEquals(executable.toBcs() + ByteArray(16) { 7 }, crypto.plaintext)
    assertContentEquals(expectedAad, payload.ciphertext.associatedData.toByteArray())

    assertIs<AptosResult.Success<UnsignedTransaction>>(service.encrypt(transaction))
    assertEquals(1, context.authenticationKeyRequests[sender.toStringLong()])
    assertEquals(1, context.authenticationKeyRequests[secondary.toStringLong()])
    assertEquals(1, context.authenticationKeyRequests[feePayer.toStringLong()])
  }

  @Test
  fun simpleTransactionsDoNotExposeAClaimAndSupportExplicitSenderKey() = runTest {
    val sender = address(4)
    val transaction =
      UnsignedTransaction.Simple(
        raw(sender, TransactionPayload.entryFunction("0x1::coin::transfer"))
      )
    val context = FakeContext()
    val crypto = FakeCrypto()
    val service = DefaultEncryptedTransactionService(context, crypto = crypto)

    val result =
      assertIs<AptosResult.Success<UnsignedTransaction>>(
        service.encrypt(
          transaction,
          EncryptedTransactionOptions(senderAuthenticationKey = FixedBytes32(ByteArray(32) { 42 })),
        )
      )
    val payload =
      assertIs<TransactionPayload.Encrypted>(result.value.rawTransaction.payload).payload
    assertNull(payload.claimedEntryFunction)
    assertTrue(context.authenticationKeyRequests.isEmpty())
    assertContentEquals(ByteArray(32) { 42 }, crypto.associatedData.copyOfRange(67, 99))
  }

  @Test
  fun reportsFeatureAndSignerKeyConfigurationErrors() = runTest {
    val sender = address(5)
    val transaction =
      UnsignedTransaction.Simple(raw(sender, TransactionPayload.entryFunction("0x1::m::f")))
    val unavailable = FakeContext(encryptionKey = null)
    val service = DefaultEncryptedTransactionService(unavailable, crypto = FakeCrypto())

    val unsupported = assertIs<AptosResult.Failure>(service.encrypt(transaction))
    assertIs<AptosError.UnsupportedFeature>(unsupported.error)

    val mismatch =
      assertIs<AptosResult.Failure>(
        DefaultEncryptedTransactionService(FakeContext(), crypto = FakeCrypto())
          .encrypt(
            transaction,
            EncryptedTransactionOptions(
              secondarySignerAuthenticationKeys = listOf(FixedBytes32(ByteArray(32)))
            ),
          )
      )
    assertIs<AptosError.Validation>(mismatch.error)
  }

  private class FakeContext(
    private val encryptionKey: ByteString? = ByteString(byteArrayOf(9, 8, 7))
  ) : EncryptionContext {
    val authenticationKeyRequests = mutableMapOf<String, Int>()

    override suspend fun ledger(): AptosResult<LedgerState> =
      AptosResult.Success(
        LedgerState(
          chainId = ChainId(4u),
          epoch = 9uL,
          version = 10uL,
          oldestVersion = 0uL,
          timestampMicros = 11uL,
          nodeRole = "validator",
          oldestBlockHeight = 0uL,
          blockHeight = 12uL,
          gitHash = null,
          encryptionKey = encryptionKey,
        )
      )

    override suspend fun authenticationKey(address: AccountAddress): AptosResult<String> {
      val id = address.data.last().toUByte().toInt()
      authenticationKeyRequests[address.toStringLong()] =
        authenticationKeyRequests.getOrElse(address.toStringLong()) { 0 } + 1
      return AptosResult.Success("0x" + id.toString(16).padStart(2, '0').repeat(32))
    }
  }

  private class FakeCrypto : BatchEncryptionCrypto {
    var plaintext = byteArrayOf()
    var associatedData = byteArrayOf()

    override fun randomBytes(length: Int): ByteArray = ByteArray(length) { 7 }

    override fun sha3(input: ByteArray): ByteArray =
      ByteArray(32) { index -> (input.size + index).toByte() }

    override fun encrypt(
      encryptionKeyBcs: ByteArray,
      plaintextBcs: ByteArray,
      associatedDataBcs: ByteArray,
    ): ByteArray {
      assertContentEquals(byteArrayOf(9, 8, 7), encryptionKeyBcs)
      plaintext = plaintextBcs.copyOf()
      associatedData = associatedDataBcs.copyOf()
      return EncryptedCiphertext(
          verificationKey = FixedBytes32(ByteArray(32) { 1 }),
          bibeCiphertext =
            BibeCiphertext(
              id = ByteString(ByteArray(32) { 2 }),
              threeG2Points = ByteString(ByteArray(288) { 3 }),
              paddedKey = FixedBytes16(ByteArray(16) { 4 }),
              nonce = FixedBytes12(ByteArray(12) { 5 }),
              body = ByteString(byteArrayOf(6, 7)),
            ),
          associatedData = ByteString(associatedDataBcs),
          signature = FixedBytes64(ByteArray(64) { 8 }),
        )
        .toBcs()
    }
  }

  private fun address(lastByte: Int): AccountAddress =
    AccountAddress(ByteArray(32).also { it[31] = lastByte.toByte() })

  private fun signerPair(address: AccountAddress, keyByte: Int): ByteArray =
    address.data + byteArrayOf(32) + ByteArray(32) { keyByte.toByte() }

  private fun hex(value: String): ByteArray =
    value.chunked(2).map { it.toInt(16).toByte() }.toByteArray()

  private fun raw(
    sender: AccountAddress,
    payload: TransactionPayload,
    gasPrice: ULong = 250uL,
    sequenceNumber: ULong = 0uL,
  ): RawTransaction =
    RawTransaction(
      sender = sender,
      sequenceNumber = sequenceNumber,
      payload = payload,
      maxGasAmount = 2_000uL,
      gasUnitPrice = gasPrice,
      expirationTimestampSecs = 100uL,
      chainId = ChainId(4u),
    )
}
