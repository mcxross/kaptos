/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.AccountLookupOptions
import xyz.mcxross.kaptos.account.AccountRestorationDataSource
import xyz.mcxross.kaptos.account.DefaultAccountService
import xyz.mcxross.kaptos.account.Ed25519Account
import xyz.mcxross.kaptos.account.MultiKeyAccount
import xyz.mcxross.kaptos.account.RestoredAccountAddress
import xyz.mcxross.kaptos.core.crypto.AccountPublicKey
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AccountData
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.ReplayProtection
import xyz.mcxross.kaptos.model.SimulationOptions
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.UserTransactionResponse
import xyz.mcxross.kaptos.model.WaitForTransactionOptions
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.transaction.instances.RotationProofChallenge

class AccountServiceTest :
  StringSpec({
    "public-key lookup merges default and rotated multi-key accounts in activity order" {
      val signer = deterministicAccount(1)
      val singleKey = AnyPublicKey(signer.publicKey)
      val multiKey = MultiKey(listOf(singleKey), signaturesRequired = 1)
      val defaultAddress = signer.accountAddress
      val rotatedAddress = AccountAddress.fromString("0xa11ce")
      val dataSource =
        FakeRestorationDataSource(
          accounts =
            mapOf(
              defaultAddress.toStringLong() to
                AccountData(3uL, signer.publicKey.authKey().toString())
            ),
          versions = mapOf(defaultAddress.toStringLong() to 5uL),
          relatedKeys = listOf(multiKey),
          restored =
            listOf(
              RestoredAccountAddress(
                multiKey.authKey().toString(),
                rotatedAddress,
                9uL,
              ),
              RestoredAccountAddress(
                signer.publicKey.authKey().toString(),
                defaultAddress,
                5uL,
              ),
            ),
        )
      val service = DefaultAccountService(dataSource, RecordingTransactionService())

      val result =
        service.findByPublicKey(
          signer.publicKey,
          AccountLookupOptions(includeUnverified = true),
        )

      val accounts =
        result
          .shouldBeInstanceOf<AptosResult.Success<List<xyz.mcxross.kaptos.account.AccountInfo>>>()
          .value
      accounts shouldHaveSize 2
      accounts.map { it.address } shouldBe listOf(rotatedAddress, defaultAddress)
      accounts.first().publicKey shouldBe multiKey
      dataSource.requestedIncludeUnverified shouldBe true
    }

    "owned-account discovery reconstructs a one-of-N MultiKey signer" {
      val signer = deterministicAccount(2)
      val multiKey = MultiKey(listOf(AnyPublicKey(signer.publicKey)), 1)
      val restoredAddress = AccountAddress.fromString("0xb0b")
      val dataSource =
        FakeRestorationDataSource(
          relatedKeys = listOf(multiKey),
          restored =
            listOf(
              RestoredAccountAddress(
                multiKey.authKey().toString(),
                restoredAddress,
                12uL,
              )
            ),
        )
      val service = DefaultAccountService(dataSource, RecordingTransactionService())

      val account =
        service
          .deriveOwned(signer)
          .shouldBeInstanceOf<AptosResult.Success<List<Account>>>()
          .value
          .single()
          .shouldBeInstanceOf<MultiKeyAccount>()
      account.accountAddress shouldBe restoredAddress
    }

    "verified rotation binds the on-chain sequence number into challenge and transaction" {
      val current = deterministicAccount(3)
      val replacement = deterministicAccount(4)
      val dataSource =
        FakeRestorationDataSource(
          accounts =
            mapOf(
              current.accountAddress.toStringLong() to
                AccountData(7uL, current.publicKey.authKey().toString())
            )
        )
      val transactions = RecordingTransactionService()
      val service = DefaultAccountService(dataSource, transactions)

      service
        .buildVerifiedRotation(current, replacement)
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()

      val payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.module.toString() shouldBe "0x1::account"
      payload.call.function.toString() shouldBe "rotate_authentication_key"
      payload.call.arguments shouldHaveSize 6
      transactions.lastOptions?.replayProtection shouldBe ReplayProtection.SequenceNumber(7uL)
    }

    "unverified rotation uses the destination authentication scheme explicitly" {
      val current = deterministicAccount(5)
      val destination = AnyPublicKey(deterministicAccount(6).publicKey)
      val transactions = RecordingTransactionService()
      val service = DefaultAccountService(FakeRestorationDataSource(), transactions)

      service
        .buildUnverifiedRotation(current, destination)
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()

      val payload = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      payload.call.function.toString() shouldBe "rotate_authentication_key_from_public_key"
      payload.call.arguments.first() shouldBe MoveArgument.U8(2u)
      payload.call.arguments[1].shouldBeInstanceOf<MoveArgument.Bytes>().value shouldBe
        destination.toByteArray()
    }

    "rotation challenge matches the pinned TypeScript SDK vector" {
      val challenge =
        RotationProofChallenge(
          sequenceNumber = 7uL,
          originator = AccountAddress.ONE,
          currentAuthenticationKey = AccountAddress.fromString("0x2"),
          newPublicKey = Ed25519PublicKey(ByteArray(32) { 0x11 }),
        )

      challenge.toBcs().toHex() shouldBe ROTATION_CHALLENGE
    }
  }) {
  private companion object {
    const val ROTATION_CHALLENGE =
      "0000000000000000000000000000000000000000000000000000000000000001076163636f756e7416526f746174696f6e50726f6f664368616c6c656e6765070000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002201111111111111111111111111111111111111111111111111111111111111111"
  }
}

private class FakeRestorationDataSource(
  private val accounts: Map<String, AccountData> = emptyMap(),
  private val versions: Map<String, ULong> = emptyMap(),
  private val relatedKeys: List<AccountPublicKey> = emptyList(),
  private val restored: List<RestoredAccountAddress> = emptyList(),
) : AccountRestorationDataSource {
  var requestedIncludeUnverified: Boolean? = null

  override suspend fun getAccount(address: AccountAddress): AptosResult<AccountData> =
    accounts[address.toStringLong()]?.let { AptosResult.Success(it) }
      ?: AptosResult.Failure(AptosError.Api("Account not found", "account_not_found"))

  override suspend fun getLatestTransactionVersion(address: AccountAddress): AptosResult<ULong> =
    AptosResult.Success(versions[address.toStringLong()] ?: 0uL)

  override suspend fun getRelatedMultiKeys(
    publicKey: AccountPublicKey,
    includeUnverified: Boolean,
  ): AptosResult<List<AccountPublicKey>> {
    requestedIncludeUnverified = includeUnverified
    return AptosResult.Success(relatedKeys)
  }

  override suspend fun getAddresses(
    authenticationKeys: List<String>,
    includeUnverified: Boolean,
  ): AptosResult<List<RestoredAccountAddress>> {
    requestedIncludeUnverified = includeUnverified
    return AptosResult.Success(restored.filter { it.authenticationKey in authenticationKeys })
  }
}

internal class RecordingTransactionService : TransactionService {
  var lastPayload: TransactionPayload? = null
  var lastOptions: TransactionOptions? = null

  override suspend fun build(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    options: TransactionOptions?,
  ): AptosResult<UnsignedTransaction.Simple> {
    lastPayload = payload
    lastOptions = options
    val sequence = (options?.replayProtection as? ReplayProtection.SequenceNumber)?.value ?: 0uL
    return AptosResult.Success(
      UnsignedTransaction.Simple(
        RawTransaction(
          sender = AccountAddress.from(sender),
          sequenceNumber = sequence,
          payload = payload,
          maxGasAmount = options?.maxGasAmount ?: 2_000uL,
          gasUnitPrice = options?.gasUnitPrice ?: 1uL,
          expirationTimestampSecs = options?.expirationTimestampSecs ?: 100uL,
          chainId = ChainId(4u),
        )
      )
    )
  }

  override suspend fun entryFunctionPayload(
    function: String,
    typeArguments: List<TypeTag>,
    arguments: List<MoveArgument>,
  ): AptosResult<TransactionPayload.EntryFunction> = unsupported()

  override suspend fun preloadModuleAbis(
    vararg modules: xyz.mcxross.kaptos.model.MoveModuleBytecode
  ): AptosResult<Unit> = unsupported()

  override suspend fun buildMultiAgent(
    sender: AccountAddressInput,
    secondarySigners: List<AccountAddressInput>,
    payload: TransactionPayload,
    options: TransactionOptions?,
  ): AptosResult<UnsignedTransaction.MultiAgent> = unsupported()

  override suspend fun buildFeePayer(
    sender: AccountAddressInput,
    payload: TransactionPayload,
    secondarySigners: List<AccountAddressInput>,
    feePayer: AccountAddressInput?,
    options: TransactionOptions?,
  ): AptosResult<UnsignedTransaction.FeePayer> = unsupported()

  override suspend fun sign(
    signer: xyz.mcxross.kaptos.account.TransactionSigner,
    transaction: UnsignedTransaction,
  ): AptosResult<AccountAuthenticator> = unsupported()

  override suspend fun simulate(
    transaction: UnsignedTransaction,
    senderPublicKey: PublicKey,
    secondarySignerPublicKeys: List<PublicKey>,
    feePayerPublicKey: PublicKey?,
    options: SimulationOptions,
  ): AptosResult<List<UserTransactionResponse>> = unsupported()

  override suspend fun submit(
    transaction: UnsignedTransaction,
    senderAuthenticator: AccountAuthenticator,
    secondaryAuthenticators: List<AccountAuthenticator>,
    feePayerAuthenticator: AccountAuthenticator?,
  ): AptosResult<PendingTransactionResponse> = unsupported()

  override suspend fun signAndSubmit(
    signer: xyz.mcxross.kaptos.account.TransactionSigner,
    transaction: UnsignedTransaction,
    secondaryAuthenticators: List<AccountAuthenticator>,
    feePayerAuthenticator: AccountAuthenticator?,
  ): AptosResult<PendingTransactionResponse> = unsupported()

  override suspend fun waitForTransaction(
    hash: String,
    options: WaitForTransactionOptions,
  ): AptosResult<TransactionResponse> = unsupported()

  private fun <T> unsupported(): AptosResult<T> =
    AptosResult.Failure(AptosError.UnsupportedFeature("Not used by this test"))
}

private fun deterministicAccount(seed: Byte): Ed25519Account =
  Ed25519Account(Ed25519PrivateKey(ByteArray(32) { seed }))

private fun ByteArray.toHex(): String =
  joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }
