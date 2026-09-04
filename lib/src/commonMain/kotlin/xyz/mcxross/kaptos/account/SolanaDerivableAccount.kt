/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.sha3Hash
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.TransactionExecutable
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

/** Application-owned Solana wallet callback. The input is the complete SIWS message to sign. */
fun interface SolanaMessageSigner {
  suspend fun sign(message: ByteArray): AptosResult<ByteArray>
}

/**
 * Framework-native Solana derivable account abstraction.
 *
 * The SDK builds the exact SIWS message from the transaction, including its entry function and
 * chain ID. Applications retain control of credential acquisition through [SolanaMessageSigner].
 * Only entry-function transactions are supported because the on-chain authenticator requires an
 * entry-function name.
 */
class SolanaDerivableAccount private constructor(
  private val identity: SolanaIdentity,
  private val messageSigner: SolanaMessageSigner,
) : DerivableAbstractedAccount(
  authenticationFunction = AUTHENTICATION_FUNCTION,
  abstractPublicKey = identity.bcs,
  signer = AbstractionSigner(messageSigner::sign),
) {
  constructor(
    publicKey: ByteArray,
    domain: String,
    signer: SolanaMessageSigner,
  ) : this(createIdentity(publicKey, domain), signer)

  /** Raw 32-byte Ed25519 public key used by the Solana wallet. */
  val solanaPublicKey: ByteArray
    get() = identity.publicKey.copyOf()

  /** Base58 form embedded in the SIWS message. */
  val base58PublicKey: String
    get() = identity.base58PublicKey

  /** Application domain embedded in the SIWS message. */
  val domain: String
    get() = identity.domain

  override suspend fun signTransaction(
    transaction: UnsignedTransaction
  ): AptosResult<AccountAuthenticator> {
    val entryFunction = transaction.entryFunctionName()
      ?: return AptosResult.Failure(
        AptosError.UnsupportedFeature(
          "Solana derivable accounts support entry-function transactions only"
        )
      )
    val function = authenticationFunction
    val abstractionMessage = AbstractedAccount.signingMessage(transaction.signingMessage(), function)
    val digest = sha3Hash(abstractionMessage)
    val message =
      siwsMessage(
        domain = identity.domain,
        base58PublicKey = identity.base58PublicKey,
        entryFunction = entryFunction,
        chainId = transaction.rawTransaction.chainId.chainId,
        digest = digest,
      )
    return when (val result = messageSigner.sign(message.copyOf())) {
      is AptosResult.Failure -> result
      is AptosResult.Success -> {
        if (result.value.size != SIGNATURE_LENGTH) {
          AptosResult.Failure(
            AptosError.Crypto(
              "Solana Ed25519 signatures must be $SIGNATURE_LENGTH bytes, got ${result.value.size}"
            )
          )
        } else {
          val abstractSignature =
            AptosBcsWriter()
              .also { writer ->
                writer.uleb128(SIWS_MESSAGE_V1)
                writer.bytes(result.value)
              }
              .toByteArray()
          AptosResult.Success(
            AccountAuthenticator.Abstraction(
              function = function,
              signingMessageDigest = ByteString(digest),
              signature = ByteString(abstractSignature),
              accountIdentity = ByteString(identity.bcs),
            )
          )
        }
      }
    }
  }

  companion object {
    private const val SIGNATURE_LENGTH = 64
    private const val SIWS_MESSAGE_V1 = 0u
    private const val BASE58_ALPHABET =
      "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
    private val AUTHENTICATION_FUNCTION =
      AuthenticationFunction.parse("0x1::solana_derivable_account::authenticate")

    /** Uses an SDK Ed25519 account as a local Solana-compatible signer. */
    fun fromEd25519(
      signer: Ed25519Account,
      domain: String = "kaptos.example",
    ): SolanaDerivableAccount =
      SolanaDerivableAccount(
        publicKey = signer.publicKey.toByteArray(),
        domain = domain,
        signer =
          SolanaMessageSigner { message ->
            try {
              AptosResult.Success(
                signer.privateKey.sign(HexInput.fromByteArray(message)).toByteArray()
              )
            } catch (error: Throwable) {
              AptosResult.Failure(
                AptosError.Crypto("Unable to sign the Solana account-abstraction message", error)
              )
            }
          },
      )

    /** Exact SIWS message verified by `0x1::solana_derivable_account::authenticate`. */
    fun siwsMessage(
      domain: String,
      base58PublicKey: String,
      entryFunction: String,
      chainId: UByte,
      digest: ByteArray,
    ): ByteArray {
      require(domain.isNotBlank()) { "Solana account-abstraction domain must not be blank" }
      require('\n' !in domain && '\r' !in domain) {
        "Solana account-abstraction domain must not contain line breaks"
      }
      require(entryFunction.isNotBlank()) { "Entry function must not be blank" }
      require(digest.size == 32) { "Account-abstraction digest must be 32 bytes" }
      val networkName =
        when (chainId.toInt()) {
          1 -> "mainnet"
          2 -> "testnet"
          4 -> "local"
          else -> "custom network: ${chainId.toInt()}"
        }
      return buildString {
          append(domain)
          append(" wants you to sign in with your Solana account:\n")
          append(base58PublicKey)
          append("\n\nPlease confirm you explicitly initiated this request from ")
          append(domain)
          append(". You are approving to execute transaction ")
          append(entryFunction)
          append(" on Aptos blockchain (")
          append(networkName)
          append(").\n\nNonce: 0x")
          digest.forEach { append((it.toInt() and 0xff).toString(16).padStart(2, '0')) }
        }
        .encodeToByteArray()
    }

    private fun createIdentity(publicKey: ByteArray, domain: String): SolanaIdentity {
      require(publicKey.size == 32) { "Solana Ed25519 public keys must be 32 bytes" }
      require(domain.isNotBlank()) { "Solana account-abstraction domain must not be blank" }
      require('\n' !in domain && '\r' !in domain) {
        "Solana account-abstraction domain must not contain line breaks"
      }
      val key = publicKey.copyOf()
      val base58PublicKey = key.toBase58()
      val bcs =
        AptosBcsWriter()
          .also { writer ->
            writer.string(base58PublicKey)
            writer.string(domain)
          }
          .toByteArray()
      return SolanaIdentity(key, base58PublicKey, domain, bcs)
    }

    private fun ByteArray.toBase58(): String {
      val leadingZeroes = takeWhile { it == 0.toByte() }.size
      val digits = IntArray(size * 2)
      var digitCount = 0
      for (index in leadingZeroes until size) {
        var carry = this[index].toInt() and 0xff
        var digitIndex = 0
        while (digitIndex < digitCount || carry != 0) {
          val value = digits[digitIndex] * 256 + carry
          digits[digitIndex] = value % 58
          carry = value / 58
          digitIndex++
        }
        digitCount = digitIndex
      }
      return buildString(leadingZeroes + digitCount) {
        repeat(leadingZeroes) { append(BASE58_ALPHABET[0]) }
        for (index in digitCount - 1 downTo 0) append(BASE58_ALPHABET[digits[index]])
      }
    }
  }
}

private data class SolanaIdentity(
  val publicKey: ByteArray,
  val base58PublicKey: String,
  val domain: String,
  val bcs: ByteArray,
)

private fun UnsignedTransaction.entryFunctionName(): String? {
  val call =
    when (val payload = rawTransaction.payload) {
      is TransactionPayload.EntryFunction -> payload.call
      is TransactionPayload.InnerV1 ->
        (payload.executable as? TransactionExecutable.EntryFunction)?.call
      else -> null
    } ?: return null
  return "${call.module.address}::${call.module.name}::${call.function}"
}
