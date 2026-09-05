package xyz.mcxross.kaptos.transaction

import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator

internal val UnsignedTransaction.secondaryAddresses: List<AccountAddress>
  get() =
    when (this) {
      is UnsignedTransaction.Simple -> emptyList()
      is UnsignedTransaction.MultiAgent -> secondarySignerAddresses
      is UnsignedTransaction.FeePayer -> secondarySignerAddresses
    }

internal fun validateSecondarySigners(
  sender: AccountAddress,
  secondary: List<AccountAddress>,
): AptosResult.Failure? =
  when {
    secondary.distinct().size != secondary.size ->
      invalidSigners("Secondary signer addresses must be unique")
    sender in secondary -> invalidSigners("The sender cannot also be a secondary signer")
    else -> null
  }

internal fun validateParticipants(transaction: UnsignedTransaction): AptosResult.Failure? {
  validateSecondarySigners(transaction.rawTransaction.sender, transaction.secondaryAddresses)?.let {
    return it
  }
  if (transaction is UnsignedTransaction.MultiAgent && transaction.secondaryAddresses.isEmpty())
    return invalidSigners("A multi-agent transaction requires at least one secondary signer")
  return null
}

internal fun validateAuthenticators(
  transaction: UnsignedTransaction,
  secondary: List<AccountAuthenticator>,
  feePayer: AccountAuthenticator?,
): AptosResult.Failure? {
  validateParticipants(transaction)?.let {
    return it
  }
  if (secondary.size != transaction.secondaryAddresses.size)
    return invalidSigners(
      "Expected ${transaction.secondaryAddresses.size} secondary authenticators, got ${secondary.size}"
    )
  return when {
    transaction is UnsignedTransaction.FeePayer && feePayer == null ->
      invalidSigners("A fee-payer transaction requires a fee-payer authenticator")
    transaction !is UnsignedTransaction.FeePayer && feePayer != null ->
      invalidSigners("A non-sponsored transaction cannot have a fee-payer authenticator")
    else -> null
  }
}

internal fun validateSimulationSigners(
  transaction: UnsignedTransaction,
  secondary: List<PublicKey>,
  feePayer: PublicKey?,
): AptosResult.Failure? {
  validateParticipants(transaction)?.let {
    return it
  }
  if (secondary.isNotEmpty() && secondary.size != transaction.secondaryAddresses.size)
    return invalidSigners(
      "Expected ${transaction.secondaryAddresses.size} secondary-signer public keys, got ${secondary.size}"
    )
  if (transaction !is UnsignedTransaction.FeePayer && feePayer != null)
    return invalidSigners("A non-sponsored transaction cannot have a fee-payer public key")
  return null
}

private fun invalidSigners(message: String) = AptosResult.Failure(AptosError.Validation(message))
