/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.transaction.authenticator

import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey
import xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature
import xyz.mcxross.kaptos.core.crypto.Secp256k1Signature
import xyz.mcxross.kaptos.core.crypto.WebAuthnSignature
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature
import xyz.mcxross.kaptos.model.ByteString
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.Identifier
import xyz.mcxross.kaptos.model.ModuleId
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter

data class AuthenticationFunction(
  val module: ModuleId,
  val function: Identifier,
) {
  override fun toString(): String = "$module::$function"

  companion object {
    fun parse(value: String): AuthenticationFunction {
      val parts = value.split("::")
      require(parts.size == 3 && parts.all(String::isNotBlank)) {
        "Authentication function must be address::module::function"
      }
      return AuthenticationFunction(
        module =
          ModuleId(
            xyz.mcxross.kaptos.model.AccountAddress.fromString(parts[0]),
            Identifier(parts[1]),
          ),
        function = Identifier(parts[2]),
      )
    }
  }
}

/** Account-level authentication data attached to a transaction signer. */
sealed interface AccountAuthenticator {
  data class Ed25519(
    val publicKey: Ed25519PublicKey,
    val signature: Ed25519Signature,
  ) : AccountAuthenticator

  data class MultiEd25519(
    val publicKey: MultiEd25519PublicKey,
    val signature: MultiEd25519Signature,
  ) : AccountAuthenticator

  data class SingleKey(
    val publicKey: AnyPublicKey,
    val signature: AnySignature,
  ) : AccountAuthenticator

  data class MultiKey(
    val publicKey: xyz.mcxross.kaptos.core.crypto.multikey.MultiKey,
    val signature: MultiKeySignature,
  ) : AccountAuthenticator

  /** Explicitly skips account authentication during simulation. */
  data object NoAccount : AccountAuthenticator

  /** Authenticator produced by an on-chain account-abstraction function. */
  data class Abstraction(
    val function: AuthenticationFunction,
    val signingMessageDigest: ByteString,
    val signature: ByteString,
    val accountIdentity: ByteString? = null,
  ) : AccountAuthenticator

  fun toBcs(): ByteArray = AptosBcsWriter().also { encode(it) }.toByteArray()

  companion object {
    fun fromBcs(bytes: ByteArray): AccountAuthenticator =
      AptosBcsReader(bytes).let { reader ->
        reader.accountAuthenticator(allowTrailingAbstractionSignature = true).also {
          reader.ensureFinished()
        }
      }
  }
}

internal fun AptosBcsReader.accountAuthenticator(
  allowTrailingAbstractionSignature: Boolean = false
): AccountAuthenticator =
  when (val variant = uleb128()) {
    0u ->
      AccountAuthenticator.Ed25519(
        publicKey = Ed25519PublicKey(bytes()),
        signature = Ed25519Signature(bytes()),
      )
    1u -> multiEd25519AccountAuthenticator()
    2u ->
      AccountAuthenticator.SingleKey(
        publicKey = anyPublicKey(),
        signature = anySignature(),
      )
    3u -> {
      val publicKeys = vector { anyPublicKey() }
      val threshold = u8().toInt()
      val signatures = vector { anySignature() }
      val bitmap = bytes()
      AccountAuthenticator.MultiKey(
        publicKey = MultiKey(publicKeys, threshold),
        signature = MultiKeySignature(signatures, bitmap),
      )
    }
    4u -> AccountAuthenticator.NoAccount
    5u -> {
      val function =
        AuthenticationFunction(
          module = ModuleId(accountAddress(), Identifier(string())),
          function = Identifier(string()),
        )
      when (val abstractionVariant = uleb128()) {
        0u -> {
          require(allowTrailingAbstractionSignature) {
            "V1 abstraction authenticators can only be decoded at the end of a BCS value"
          }
          AccountAuthenticator.Abstraction(
            function = function,
            signingMessageDigest = ByteString(bytes()),
            signature = ByteString(fixed(remaining)),
          )
        }
        1u ->
          AccountAuthenticator.Abstraction(
            function = function,
            signingMessageDigest = ByteString(bytes()),
            signature = ByteString(bytes()),
            accountIdentity = ByteString(bytes()),
          )
        else ->
          throw IllegalArgumentException(
            "Unsupported abstract authentication data variant: $abstractionVariant"
          )
      }
    }
    else -> throw IllegalArgumentException("Unsupported AccountAuthenticator variant: $variant")
  }

internal fun AptosBcsReader.multiEd25519AccountAuthenticator(): AccountAuthenticator.MultiEd25519 {
  val publicKeyBytes = bytes()
  require(publicKeyBytes.size >= 65 && (publicKeyBytes.size - 1) % 32 == 0) {
    "Invalid MultiEd25519 public key length"
  }
  val threshold = publicKeyBytes.last().toUByte()
  val publicKeys = publicKeyBytes.dropLast(1).chunked(32).map { Ed25519PublicKey(it.toByteArray()) }
  val signatureBytes = bytes()
  require(signatureBytes.size >= 4 && (signatureBytes.size - 4) % 64 == 0) {
    "Invalid MultiEd25519 signature length"
  }
  val signatureCount = (signatureBytes.size - 4) / 64
  return AccountAuthenticator.MultiEd25519(
    publicKey = MultiEd25519PublicKey(publicKeys, threshold),
    signature =
      MultiEd25519Signature(
        signatures =
          (0..<signatureCount).map { index ->
            Ed25519Signature(signatureBytes.copyOfRange(index * 64, index * 64 + 64))
          },
        bitmap = signatureBytes.copyOfRange(signatureBytes.size - 4, signatureBytes.size),
      ),
  )
}

private fun AptosBcsReader.anySignature(): AnySignature =
  when (val variant = uleb128()) {
    0u -> AnySignature(Ed25519Signature(bytes()))
    1u -> AnySignature(Secp256k1Signature(HexInput.fromByteArray(bytes())))
    2u -> {
      val webAuthnVariant = uleb128()
      require(webAuthnVariant == 0u) {
        "Unsupported WebAuthn signature variant: $webAuthnVariant"
      }
      AnySignature(WebAuthnSignature(bytes(), bytes(), bytes()))
    }
    else -> throw IllegalArgumentException("Unsupported AnySignature variant: $variant")
  }

internal fun AccountAuthenticator.encode(writer: AptosBcsWriter) {
  when (this) {
    is AccountAuthenticator.Ed25519 -> {
      writer.uleb128(0u)
      writer.fixed(publicKey.toBcs())
      writer.fixed(signature.toBcs())
    }
    is AccountAuthenticator.MultiEd25519 -> {
      writer.uleb128(1u)
      writer.fixed(publicKey.toBcs())
      writer.fixed(signature.toBcs())
    }
    is AccountAuthenticator.SingleKey -> {
      writer.uleb128(2u)
      writer.fixed(publicKey.toBcs())
      writer.fixed(signature.toBcs())
    }
    is AccountAuthenticator.MultiKey -> {
      writer.uleb128(3u)
      writer.fixed(publicKey.toBcs())
      writer.fixed(signature.toBcs())
    }
    AccountAuthenticator.NoAccount -> writer.uleb128(4u)
    is AccountAuthenticator.Abstraction -> {
      writer.uleb128(5u)
      writer.accountAddress(function.module.address)
      writer.string(function.module.name.toString())
      writer.string(function.function.toString())
      writer.uleb128(if (accountIdentity == null) 0u else 1u)
      writer.bytes(signingMessageDigest.toByteArray())
      if (accountIdentity == null) {
        writer.fixed(signature.toByteArray())
      } else {
        writer.bytes(signature.toByteArray())
        writer.bytes(accountIdentity.toByteArray())
      }
    }
  }
}
