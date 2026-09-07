/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.confidential

import xyz.mcxross.fastkrypto.AptosConfidentialTransferProofInputBytes
import xyz.mcxross.fastkrypto.AptosConfidentialWithdrawProofInputBytes
import xyz.mcxross.fastkrypto.aptosConfidentialKeyRotationProve
import xyz.mcxross.fastkrypto.aptosConfidentialRangeProve
import xyz.mcxross.fastkrypto.aptosConfidentialRegistrationProve
import xyz.mcxross.fastkrypto.aptosConfidentialTransferProve
import xyz.mcxross.fastkrypto.aptosConfidentialWithdrawProve
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult

internal data class SigmaProof(
  val commitment: List<ByteArray>,
  val response: List<ByteArray>,
)

internal data class RegistrationAuthorization(
  val publicKey: ConfidentialEncryptionKey,
  val sigma: SigmaProof,
)

internal data class BalanceAuthorization(
  val newBalance: List<ConfidentialCiphertext>,
  val newAuditorHandles: List<ByteArray>,
  val rangeProof: ByteArray,
  val sigma: SigmaProof,
)

internal data class TransferAuthorization(
  val newBalance: List<ConfidentialCiphertext>,
  val effectiveAuditorNewBalanceHandles: List<ByteArray>,
  val transferBySender: List<ConfidentialCiphertext>,
  val recipientHandles: List<ByteArray>,
  val effectiveAuditorTransferHandles: List<ByteArray>,
  val voluntaryAuditorTransferHandles: List<List<ByteArray>>,
  val newBalanceRangeProof: ByteArray,
  val transferRangeProof: ByteArray,
  val sigma: SigmaProof,
)

internal data class KeyRotationAuthorization(
  val newPublicKey: ConfidentialEncryptionKey,
  val newHandles: List<ByteArray>,
  val sigma: SigmaProof,
)

internal object ConfidentialProofFactory {
  fun registration(
    key: ConfidentialDecryptionKey,
    sender: AccountAddress,
    token: AccountAddress,
    chainId: UByte,
  ): AptosResult<RegistrationAuthorization> =
    cryptoResult("Unable to authorize confidential balance registration") {
      val proof = key.withSecret {
        aptosConfidentialRegistrationProve(
          privateKey = it,
          senderAddress = sender.data.copyOf(),
          tokenAddress = token.data.copyOf(),
          chainId = chainId,
        )
      }
      RegistrationAuthorization(key.encryptionKey, SigmaProof(proof.commitment, proof.response))
    }

  fun withdraw(
    key: ConfidentialDecryptionKey,
    sender: AccountAddress,
    token: AccountAddress,
    chainId: UByte,
    amount: ULong,
    balance: ConfidentialBalance,
    auditor: ConfidentialEncryptionKey?,
  ): AptosResult<BalanceAuthorization> {
    val nextChunks = subtractChunks(balance.availableAmount.chunks, amount)
    if (nextChunks is AptosResult.Failure) return nextChunks
    return balanceAuthorization(
      key = key,
      sender = sender,
      token = token,
      chainId = chainId,
      amount = amount,
      oldBalance = balance.available,
      newChunks = (nextChunks as AptosResult.Success).value,
      auditor = auditor,
    )
  }

  fun normalize(
    key: ConfidentialDecryptionKey,
    sender: AccountAddress,
    token: AccountAddress,
    chainId: UByte,
    balance: ConfidentialBalance,
    auditor: ConfidentialEncryptionKey?,
  ): AptosResult<BalanceAuthorization> =
    balanceAuthorization(
      key = key,
      sender = sender,
      token = token,
      chainId = chainId,
      amount = 0u,
      oldBalance = balance.available,
      newChunks = balance.availableAmount.normalizedChunks,
      auditor = auditor,
    )

  private fun balanceAuthorization(
    key: ConfidentialDecryptionKey,
    sender: AccountAddress,
    token: AccountAddress,
    chainId: UByte,
    amount: ULong,
    oldBalance: List<ConfidentialCiphertext>,
    newChunks: List<UInt>,
    auditor: ConfidentialEncryptionKey?,
  ): AptosResult<BalanceAuthorization> =
    cryptoResult("Unable to authorize confidential balance update") {
      val encrypted = encryptChunks(key.encryptionKey, newChunks).required()
      val auditorEncrypted = auditor?.let {
        encryptChunks(it, newChunks, encrypted.randomness).required()
      }
      val range =
        aptosConfidentialRangeProve(
          values = newChunks.map(UInt::toULong),
          blindings = encrypted.randomness.map(ByteArray::copyOf),
        )
      require(
        range.commitments.zip(encrypted.ciphertexts).all { (expected, actual) ->
          expected.contentEquals(actual.commitment)
        }
      ) {
        "Range-proof commitments do not match the new encrypted balance"
      }
      val sigma = key.withSecret { secret ->
        aptosConfidentialWithdrawProve(
          AptosConfidentialWithdrawProofInputBytes(
            privateKey = secret,
            senderAddress = sender.data.copyOf(),
            tokenAddress = token.data.copyOf(),
            chainId = chainId,
            amount = amount,
            oldCommitments = oldBalance.map(ConfidentialCiphertext::commitment),
            oldHandles = oldBalance.map(ConfidentialCiphertext::handle),
            newCommitments = encrypted.ciphertexts.map(ConfidentialCiphertext::commitment),
            newHandles = encrypted.ciphertexts.map(ConfidentialCiphertext::handle),
            newAmountChunks = newChunks.map(UInt::toULong),
            newRandomness = encrypted.randomness.map(ByteArray::copyOf),
            auditorPublicKey = auditor?.toByteArray(),
            newAuditorHandles =
              auditorEncrypted?.ciphertexts?.map(ConfidentialCiphertext::handle).orEmpty(),
          )
        )
      }
      BalanceAuthorization(
        newBalance = encrypted.ciphertexts,
        newAuditorHandles =
          auditorEncrypted?.ciphertexts?.map(ConfidentialCiphertext::handle).orEmpty(),
        rangeProof = range.proof,
        sigma = SigmaProof(sigma.commitment, sigma.response),
      )
    }

  fun transfer(
    key: ConfidentialDecryptionKey,
    sender: AccountAddress,
    recipient: AccountAddress,
    token: AccountAddress,
    chainId: UByte,
    amount: ULong,
    balance: ConfidentialBalance,
    recipientKey: ConfidentialEncryptionKey,
    voluntaryAuditors: List<ConfidentialEncryptionKey>,
    effectiveAuditor: ConfidentialEncryptionKey?,
  ): AptosResult<TransferAuthorization> {
    val nextChunks = subtractChunks(balance.availableAmount.chunks, amount)
    if (nextChunks is AptosResult.Failure) return nextChunks
    return cryptoResult("Unable to authorize confidential transfer") {
      val newChunks = (nextChunks as AptosResult.Success).value
      val transferChunks = amount.toChunks(TRANSFER_AMOUNT_CHUNK_COUNT)
      val newBalance = encryptChunks(key.encryptionKey, newChunks).required()
      val transferBySender = encryptChunks(key.encryptionKey, transferChunks).required()
      val transferByRecipient =
        encryptChunks(recipientKey, transferChunks, transferBySender.randomness).required()
      val allAuditors = voluntaryAuditors + listOfNotNull(effectiveAuditor)
      val auditorTransfers = allAuditors.map { auditor ->
        encryptChunks(auditor, transferChunks, transferBySender.randomness).required()
      }
      val effectiveNewBalance = effectiveAuditor?.let {
        encryptChunks(it, newChunks, newBalance.randomness).required()
      }
      val newRange =
        aptosConfidentialRangeProve(
          values = newChunks.map(UInt::toULong),
          blindings = newBalance.randomness.map(ByteArray::copyOf),
        )
      val transferRange =
        aptosConfidentialRangeProve(
          values = transferChunks.map(UInt::toULong),
          blindings = transferBySender.randomness.map(ByteArray::copyOf),
        )
      val sigma = key.withSecret { secret ->
        aptosConfidentialTransferProve(
          AptosConfidentialTransferProofInputBytes(
            privateKey = secret,
            senderAddress = sender.data.copyOf(),
            recipientAddress = recipient.data.copyOf(),
            tokenAddress = token.data.copyOf(),
            chainId = chainId,
            recipientPublicKey = recipientKey.toByteArray(),
            oldCommitments = balance.available.map(ConfidentialCiphertext::commitment),
            oldHandles = balance.available.map(ConfidentialCiphertext::handle),
            newCommitments = newBalance.ciphertexts.map(ConfidentialCiphertext::commitment),
            newHandles = newBalance.ciphertexts.map(ConfidentialCiphertext::handle),
            newAmountChunks = newChunks.map(UInt::toULong),
            newRandomness = newBalance.randomness.map(ByteArray::copyOf),
            transferCommitments =
              transferBySender.ciphertexts.map(ConfidentialCiphertext::commitment),
            transferSenderHandles =
              transferBySender.ciphertexts.map(ConfidentialCiphertext::handle),
            transferRecipientHandles =
              transferByRecipient.ciphertexts.map(ConfidentialCiphertext::handle),
            transferAmountChunks = transferChunks.map(UInt::toULong),
            transferRandomness = transferBySender.randomness.map(ByteArray::copyOf),
            hasEffectiveAuditor = effectiveAuditor != null,
            auditorPublicKeys = allAuditors.map(ConfidentialEncryptionKey::toByteArray),
            effectiveNewBalanceHandles =
              effectiveNewBalance?.ciphertexts?.map(ConfidentialCiphertext::handle).orEmpty(),
            auditorTransferHandles =
              auditorTransfers.flatMap { it.ciphertexts.map(ConfidentialCiphertext::handle) },
          )
        )
      }
      val voluntaryTransfers =
        auditorTransfers.take(voluntaryAuditors.size).map { encrypted ->
          encrypted.ciphertexts.map(ConfidentialCiphertext::handle)
        }
      TransferAuthorization(
        newBalance = newBalance.ciphertexts,
        effectiveAuditorNewBalanceHandles =
          effectiveNewBalance?.ciphertexts?.map(ConfidentialCiphertext::handle).orEmpty(),
        transferBySender = transferBySender.ciphertexts,
        recipientHandles = transferByRecipient.ciphertexts.map(ConfidentialCiphertext::handle),
        effectiveAuditorTransferHandles =
          if (effectiveAuditor == null) emptyList()
          else auditorTransfers.last().ciphertexts.map(ConfidentialCiphertext::handle),
        voluntaryAuditorTransferHandles = voluntaryTransfers,
        newBalanceRangeProof = newRange.proof,
        transferRangeProof = transferRange.proof,
        sigma = SigmaProof(sigma.commitment, sigma.response),
      )
    }
  }

  fun rotate(
    currentKey: ConfidentialDecryptionKey,
    newKey: ConfidentialDecryptionKey,
    sender: AccountAddress,
    token: AccountAddress,
    chainId: UByte,
    balance: ConfidentialBalance,
  ): AptosResult<KeyRotationAuthorization> =
    cryptoResult("Unable to authorize confidential encryption-key rotation") {
      val rotation = currentKey.withSecret { currentSecret ->
        newKey.withSecret { newSecret ->
          aptosConfidentialKeyRotationProve(
            currentPrivateKey = currentSecret,
            newPrivateKey = newSecret,
            oldHandles = balance.available.map(ConfidentialCiphertext::handle),
            senderAddress = sender.data.copyOf(),
            tokenAddress = token.data.copyOf(),
            chainId = chainId,
          )
        }
      }
      KeyRotationAuthorization(
        newPublicKey = ConfidentialEncryptionKey(rotation.newPublicKey),
        newHandles = rotation.newHandles,
        sigma = SigmaProof(rotation.proof.commitment, rotation.proof.response),
      )
    }
}

private inline fun <T> cryptoResult(message: String, block: () -> T): AptosResult<T> =
  try {
    AptosResult.Success(block())
  } catch (error: Throwable) {
    AptosResult.Failure(AptosError.Crypto(message, error))
  }

private fun <T> AptosResult<T>.required(): T =
  when (this) {
    is AptosResult.Success -> value
    is AptosResult.Failure -> throw IllegalArgumentException(error.message, error.cause)
  }
