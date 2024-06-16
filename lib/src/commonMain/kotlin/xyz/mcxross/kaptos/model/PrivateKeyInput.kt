package xyz.mcxross.kaptos.model

import xyz.mcxross.kaptos.core.crypto.PrivateKey

/**
 * Input for creating an account from a private key.
 *
 * This is a wrapper around the private key, and optionally an address to associate with the
 * account. We can use this when we want to pass a single object to a function that needs the
 * private key, address, and legacy flag. For example, with infix functions.
 *
 * @param privateKey the private key to create the account from
 * @param address the address to associate with the account
 * @param legacy whether to use the legacy address format
 */
data class PrivateKeyInput(
  val privateKey: PrivateKey,
  val address: AccountAddressInput? = null,
  val legacy: Boolean = true,
)
