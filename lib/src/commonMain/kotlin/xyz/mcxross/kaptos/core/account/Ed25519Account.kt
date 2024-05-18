package xyz.mcxross.kaptos.core.account

import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.SigningScheme

/**
 * Signer implementation for the Ed25519 authentication scheme. This extends an [Ed25519Account] by
 * adding signing capabilities through an [Ed25519PrivateKey].
 *
 * Note: Generating a signer instance does not create the account on-chain.
 */
class Ed25519Account(val privateKey: Ed25519PrivateKey, val address: AccountAddressInput? = null) :
  Account() {

  /** Public key associated with the account */
  override var publicKey: Ed25519PublicKey
    get() = privateKey.publicKey()

  /** Account address associated with the account */
  override var accountAddress: AccountAddress
    get() =
      if (address != null) {
        AccountAddress.from(address)
      } else {
        publicKey.authKey().deriveAddress()
      }

  /** Signing scheme used to sign transactions */
  override val signingScheme: SigningScheme
    get() = SigningScheme.Ed25519

  init {
    this.publicKey = privateKey.publicKey() as Ed25519PublicKey
    this.accountAddress =
      if (address != null) {
        AccountAddress.from(address)
      } else {
        this.publicKey.authKey().deriveAddress()
      }
  }

  fun signWithAuthenticator(message: HexInput): Ed25519Signature {
    val signature = this.privateKey.sign(message)
    TODO("Not yet implemented: We should call the actual Ed25519 sign")
  }

  override fun sign(message: HexInput): Signature {
    return this.signWithAuthenticator(message)
  }

  companion object {
    fun generate(): Ed25519Account {
      val privateKey = Ed25519PrivateKey.generate()
      return Ed25519Account(privateKey)
    }
  }
}
