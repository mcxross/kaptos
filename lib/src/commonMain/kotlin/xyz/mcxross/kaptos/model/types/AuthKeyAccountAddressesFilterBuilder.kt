package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class AuthKeyAccountAddressesFilterBuilder {
  private val andConditions = mutableListOf<Auth_key_account_addresses_bool_exp>()
  private val orConditions = mutableListOf<Auth_key_account_addresses_bool_exp>()
  private var notCondition: Auth_key_account_addresses_bool_exp? = null

  var accountAddress: String_comparison_exp? = null
  var authKey: String_comparison_exp? = null
  var isAuthKeyUsed: Boolean_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null

  fun and(block: AuthKeyAccountAddressesFilterBuilder.() -> Unit) {
    andConditions += AuthKeyAccountAddressesFilterBuilder().apply(block).build()
  }

  fun or(block: AuthKeyAccountAddressesFilterBuilder.() -> Unit) {
    orConditions += AuthKeyAccountAddressesFilterBuilder().apply(block).build()
  }

  fun not(block: AuthKeyAccountAddressesFilterBuilder.() -> Unit) {
    notCondition = AuthKeyAccountAddressesFilterBuilder().apply(block).build()
  }

  internal fun build(): Auth_key_account_addresses_bool_exp =
    Auth_key_account_addresses_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      account_address = accountAddress.toOptional(),
      auth_key = authKey.toOptional(),
      is_auth_key_used = isAuthKeyUsed.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
    )
}

fun authKeyAccountAddressesFilter(
  init: AuthKeyAccountAddressesFilterBuilder.() -> Unit
): Auth_key_account_addresses_bool_exp = AuthKeyAccountAddressesFilterBuilder().apply(init).build()
