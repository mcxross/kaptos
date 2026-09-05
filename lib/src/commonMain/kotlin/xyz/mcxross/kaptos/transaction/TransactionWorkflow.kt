package xyz.mcxross.kaptos.transaction

import xyz.mcxross.kaptos.account.TransactionSigner
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

/** One orchestration path used by payload-based submission and confirmation. */
internal suspend fun TransactionService.submitPayload(
  signer: TransactionSigner,
  payload: TransactionPayload,
  options: TransactionOptions?,
  secondarySigners: List<TransactionSigner>,
  feePayer: TransactionSigner?,
): AptosResult<PendingTransactionResponse> {
  validateSecondarySigners(signer.accountAddress, secondarySigners.map { it.accountAddress })?.let {
    return it
  }
  val builtTransaction: AptosResult<UnsignedTransaction> =
    when {
      feePayer != null ->
        buildFeePayer(
          sender = signer.accountAddress,
          secondarySigners = secondarySigners.map(TransactionSigner::accountAddress),
          payload = payload,
          feePayer = feePayer.accountAddress,
          options = options,
        )
      secondarySigners.isNotEmpty() ->
        buildMultiAgent(
          sender = signer.accountAddress,
          secondarySigners = secondarySigners.map(TransactionSigner::accountAddress),
          payload = payload,
          options = options,
        )
      else -> build(signer.accountAddress, payload, options)
    }
  val transaction =
    when (builtTransaction) {
      is AptosResult.Failure -> return builtTransaction
      is AptosResult.Success -> builtTransaction.value
    }

  val secondaryAuthenticators = mutableListOf<AccountAuthenticator>()
  for (secondarySigner in secondarySigners) {
    when (val authenticator = sign(secondarySigner, transaction)) {
      is AptosResult.Failure -> return authenticator
      is AptosResult.Success -> secondaryAuthenticators += authenticator.value
    }
  }
  val feePayerAuthenticator =
    if (feePayer == null) {
      null
    } else {
      when (val authenticator = sign(feePayer, transaction)) {
        is AptosResult.Failure -> return authenticator
        is AptosResult.Success -> authenticator.value
      }
    }
  return signAndSubmit(
    signer = signer,
    transaction = transaction,
    secondaryAuthenticators = secondaryAuthenticators,
    feePayerAuthenticator = feePayerAuthenticator,
  )
}
