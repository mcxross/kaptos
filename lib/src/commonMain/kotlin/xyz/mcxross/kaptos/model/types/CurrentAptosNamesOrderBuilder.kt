package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Current_aptos_names_order_by
import xyz.mcxross.kaptos.util.toOptional

class CurrentAptosNamesOrderBuilder {
  private var isDomainOwner: Current_aptos_names_order_by? = null

  var domain: OrderBy? = null
  var domainExpirationTimestamp: OrderBy? = null
  var domainWithSuffix: OrderBy? = null
  var expirationTimestamp: OrderBy? = null
  var isActive: OrderBy? = null
  var isPrimary: OrderBy? = null
  var lastTransactionVersion: OrderBy? = null
  var ownerAddress: OrderBy? = null
  var registeredAddress: OrderBy? = null
  var subdomain: OrderBy? = null
  var subdomainExpirationPolicy: OrderBy? = null
  var tokenName: OrderBy? = null
  var tokenStandard: OrderBy? = null

  fun isDomainOwner(block: CurrentAptosNamesOrderBuilder.() -> Unit) {
    this.isDomainOwner = CurrentAptosNamesOrderBuilder().apply(block).build()
  }

  internal fun build(): Current_aptos_names_order_by =
      Current_aptos_names_order_by(
          domain = domain?.generated.toOptional(),
          domain_expiration_timestamp = domainExpirationTimestamp?.generated.toOptional(),
          domain_with_suffix = domainWithSuffix?.generated.toOptional(),
          expiration_timestamp = expirationTimestamp?.generated.toOptional(),
          is_active = isActive?.generated.toOptional(),
          is_domain_owner = isDomainOwner.toOptional(),
          is_primary = isPrimary?.generated.toOptional(),
          last_transaction_version = lastTransactionVersion?.generated.toOptional(),
          owner_address = ownerAddress?.generated.toOptional(),
          registered_address = registeredAddress?.generated.toOptional(),
          subdomain = subdomain?.generated.toOptional(),
          subdomain_expiration_policy = subdomainExpirationPolicy?.generated.toOptional(),
          token_name = tokenName?.generated.toOptional(),
          token_standard = tokenStandard?.generated.toOptional())
}

fun currentAptosNamesOrder(
    init: CurrentAptosNamesOrderBuilder.() -> Unit
): Current_aptos_names_order_by = CurrentAptosNamesOrderBuilder().apply(init).build()
