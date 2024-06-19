package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.model.*

/** Interface for all `ANS` operations */
interface Ans {

  /**
   * Retrieve the owner address of a domain name or subdomain name from the contract.
   *
   * @param name - A string of the name to retrieve
   * @returns an [Option] containing the owner address if the name is owned, None otherwise
   */
  suspend fun getOwnerAddress(name: String): Option<AccountAddress>

  /**
   * Retrieve the expiration time of a domain name or subdomain name from the contract.
   *
   * @param name - A string of the name to retrieve
   * @returns an [Option] containing the expiration time if the name is owned, None otherwise
   */
  suspend fun getExpiration(name: String): Option<Long>

  /**
   * Retrieve the target address of a domain or subdomain name. This is the address the name points
   * to for use on chain. Note, the target address can point to addresses that are not the owner of
   * the name
   *
   * @param name - A string of the name: primary, primary.apt, secondary.primary,
   *   secondary.primary.apt, etc.
   * @returns AccountAddress if the name has a target, undefined otherwise
   */
  suspend fun getTargetAddress(name: String): Option<AccountAddress>

  /**
   * Sets the target address of a domain or subdomain name. This is the address the name points to
   * for use on chain. Note, the target address can point to addresses that are not the owner of the
   * name
   *
   * @param sender - The sender of the transaction
   * @param name - A string of the name: test.aptos.apt, test.apt, test, test.aptos, etc.
   * @param address - A AccountAddressInput of the address to set the domain or subdomain to
   * @param options - An optional [InputGenerateTransactionOptions] to configure the transaction
   * @returns [SimpleTransaction]
   */
  suspend fun setTargetAddress(
    sender: AccountAddress,
    name: String,
    address: AccountAddressInput,
    options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
  ): SimpleTransaction

  /**
   * Retrieve the primary name for an account. An account can have multiple names that target it,
   * but only a single name that is primary. An account also may not have a primary name.
   *
   * @param address - A AccountAddressInput (address) of the account
   * @returns an [Option] containing the primary name if the account has one, None otherwise
   */
  suspend fun getPrimaryName(address: AccountAddressInput): Option<String>
}
