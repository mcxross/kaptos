package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.*
import xyz.mcxross.kaptos.util.toOptional

class PublicKeyAuthKeysFilterBuilder {
  private val andConditions = mutableListOf<Public_key_auth_keys_bool_exp>()
  private val orConditions = mutableListOf<Public_key_auth_keys_bool_exp>()
  private var notCondition: Public_key_auth_keys_bool_exp? = null

  var accountPublicKey: String_comparison_exp? = null
  var authKey: String_comparison_exp? = null
  var isPublicKeyUsed: Boolean_comparison_exp? = null
  var lastTransactionVersion: Bigint_comparison_exp? = null
  var publicKey: String_comparison_exp? = null
  var publicKeyType: String_comparison_exp? = null
  var signatureType: String_comparison_exp? = null

  fun and(block: PublicKeyAuthKeysFilterBuilder.() -> Unit) {
    andConditions += PublicKeyAuthKeysFilterBuilder().apply(block).build()
  }

  fun or(block: PublicKeyAuthKeysFilterBuilder.() -> Unit) {
    orConditions += PublicKeyAuthKeysFilterBuilder().apply(block).build()
  }

  fun not(block: PublicKeyAuthKeysFilterBuilder.() -> Unit) {
    notCondition = PublicKeyAuthKeysFilterBuilder().apply(block).build()
  }

  internal fun build(): Public_key_auth_keys_bool_exp =
      Public_key_auth_keys_bool_exp(
          _and = andConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _or = orConditions.takeIf { it.isNotEmpty() }.toOptional(),
          _not = notCondition.toOptional(),
          account_public_key = accountPublicKey.toOptional(),
          auth_key = authKey.toOptional(),
          is_public_key_used = isPublicKeyUsed.toOptional(),
          last_transaction_version = lastTransactionVersion.toOptional(),
          public_key = publicKey.toOptional(),
          public_key_type = publicKeyType.toOptional(),
          signature_type = signatureType.toOptional())
}

fun publicKeyAuthKeysFilter(
    init: PublicKeyAuthKeysFilterBuilder.() -> Unit
): Public_key_auth_keys_bool_exp = PublicKeyAuthKeysFilterBuilder().apply(init).build()
