package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class CurrentAptosNamesFilterBuilder {
  private val andConditions = mutableListOf<Current_aptos_names_bool_exp>()
  private val orConditions = mutableListOf<Current_aptos_names_bool_exp>()
  private var notCondition: Current_aptos_names_bool_exp? = null

  private var isDomainOwner: Current_aptos_names_bool_exp? = null

  var domain: String_comparison_exp? = null
  var domainExpirationTimestamp: Timestamp_comparison_exp? = null
  var domainWithSuffix: String_comparison_exp? = null
  var expirationTimestamp: Timestamp_comparison_exp? = null
  var isActive: Boolean_comparison_exp? = null
  var isPrimary: Boolean_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var ownerAddress: String_comparison_exp? = null
  var registeredAddress: String_comparison_exp? = null
  var subdomain: String_comparison_exp? = null
  var subdomainExpirationPolicy: Bigint_comparison_exp? = null
  var tokenName: String_comparison_exp? = null
  var tokenStandard: String_comparison_exp? = null

  fun and(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    andConditions += CurrentAptosNamesFilterBuilder().apply(block).build()
  }

  fun or(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    orConditions += CurrentAptosNamesFilterBuilder().apply(block).build()
  }

  fun not(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    notCondition = CurrentAptosNamesFilterBuilder().apply(block).build()
  }

  fun isDomainOwner(block: CurrentAptosNamesFilterBuilder.() -> Unit) {
    this.isDomainOwner = CurrentAptosNamesFilterBuilder().apply(block).build()
  }

  internal fun build(): Current_aptos_names_bool_exp =
    Current_aptos_names_bool_exp(
      _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
      _not = notCondition.toOptional(),
      domain = domain.toOptional(),
      domain_expiration_timestamp = domainExpirationTimestamp.toOptional(),
      domain_with_suffix = domainWithSuffix.toOptional(),
      expiration_timestamp = expirationTimestamp.toOptional(),
      is_active = isActive.toOptional(),
      is_domain_owner = isDomainOwner.toOptional(),
      is_primary = isPrimary.toOptional(),
      last_transaction_version = lastTransactionVersion.toOptional(),
      owner_address = ownerAddress.toOptional(),
      registered_address = registeredAddress.toOptional(),
      subdomain = subdomain.toOptional(),
      subdomain_expiration_policy = subdomainExpirationPolicy.toOptional(),
      token_name = tokenName.toOptional(),
      token_standard = tokenStandard.toOptional(),
    )
}

fun currentAptosNamesFilter(
  init: CurrentAptosNamesFilterBuilder.() -> Unit
): Current_aptos_names_bool_exp = CurrentAptosNamesFilterBuilder().apply(init).build()
