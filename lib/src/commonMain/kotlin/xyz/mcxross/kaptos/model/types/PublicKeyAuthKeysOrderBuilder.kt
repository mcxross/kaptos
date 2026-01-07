package xyz.mcxross.kaptos.model.types

import xyz.mcxross.kaptos.generated.type.Public_key_auth_keys_order_by
import xyz.mcxross.kaptos.util.toOptional

class PublicKeyAuthKeysOrderBuilder {
  var accountPublicKey: OrderBy? = null
  var authKey: OrderBy? = null
  var isPublicKeyUsed: OrderBy? = null
  var lastTransactionVersion: OrderBy? = null
  var publicKey: OrderBy? = null
  var publicKeyType: OrderBy? = null
  var signatureType: OrderBy? = null

  internal fun build(): Public_key_auth_keys_order_by =
    Public_key_auth_keys_order_by(
      account_public_key = accountPublicKey?.generated.toOptional(),
      auth_key = authKey?.generated.toOptional(),
      is_public_key_used = isPublicKeyUsed?.generated.toOptional(),
      last_transaction_version = lastTransactionVersion?.generated.toOptional(),
      public_key = publicKey?.generated.toOptional(),
      public_key_type = publicKeyType?.generated.toOptional(),
      signature_type = signatureType?.generated.toOptional(),
    )
}

fun publicKeyAuthKeysOrder(
  init: PublicKeyAuthKeysOrderBuilder.() -> Unit
): Public_key_auth_keys_order_by = PublicKeyAuthKeysOrderBuilder().apply(init).build()
