package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Auth_key_account_addresses_order_by
import xyz.mcxross.kaptos.util.toOptional

class AuthKeyAccountAddressesOrderBuilder {
  var accountAddress: OrderBy? = null
  var authKey: OrderBy? = null
  var isAuthKeyUsed: OrderBy? = null
  var lastTransactionVersion: OrderBy? = null

  internal fun build(): Auth_key_account_addresses_order_by =
      Auth_key_account_addresses_order_by(
          account_address = accountAddress?.generated.toOptional(),
          auth_key = authKey?.generated.toOptional(),
          is_auth_key_used = isAuthKeyUsed?.generated.toOptional(),
          last_transaction_version = lastTransactionVersion?.generated.toOptional(),
      )
}

fun authKeyAccountAddressesOrder(
    init: AuthKeyAccountAddressesOrderBuilder.() -> Unit
): Auth_key_account_addresses_order_by = AuthKeyAccountAddressesOrderBuilder().apply(init).build()
