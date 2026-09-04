/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import xyz.mcxross.kaptos.account.Ed25519Account
import xyz.mcxross.kaptos.account.MultiEd25519Account
import xyz.mcxross.kaptos.account.PasskeyAccount
import xyz.mcxross.kaptos.account.WebAuthnSigner
import xyz.mcxross.kaptos.core.crypto.AnyPublicKey
import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey
import xyz.mcxross.kaptos.core.crypto.AptosDerivationPath
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.MnemonicPhrase
import xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey
import xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey
import xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey
import xyz.mcxross.kaptos.core.crypto.WebAuthnSignature
import xyz.mcxross.kaptos.core.crypto.WebAuthnVerificationOptions
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.InputSimulateTransactionData
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.authenticator.TransactionAuthenticator
import xyz.mcxross.kaptos.transaction.builder.generateSignedTransactionForSimulation
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.transaction.instances.SignedTransaction

class AccountAndSimulationTest :
  StringSpec({
    "AIP-80 import and export are typed while legacy hex is explicit" {
      val bytes = ByteArray(32) { (it + 1).toByte() }
      val ed25519 = Ed25519PrivateKey(bytes)
      val secp256k1 = Secp256k1PrivateKey(bytes)
      val secp256r1 = Secp256r1PrivateKey(bytes)

      Ed25519PrivateKey.fromAip80(ed25519.toAip80()).toByteArray() shouldBe bytes
      Secp256k1PrivateKey.fromAip80(secp256k1.toAip80()).toByteArray() shouldBe bytes
      Secp256r1PrivateKey.fromAip80(secp256r1.toAip80()).toByteArray() shouldBe bytes
      Aip80PrivateKey.parse(ed25519.toAip80().value) shouldBe ed25519.toAip80()
      shouldThrow<IllegalArgumentException> { Ed25519PrivateKey.fromAip80("0x01") }
      shouldThrow<IllegalArgumentException> {
        Ed25519PrivateKey.fromAip80(secp256k1.toAip80())
      }
    }

    "BIP-39 derivation matches the official Aptos SDK vectors" {
      val mnemonic =
        MnemonicPhrase.parse(
          "shoot island position soft burden budget tooth cruel issue economy destroy above"
        )

      mnemonic.toString() shouldBe "<redacted mnemonic>"
      mnemonic.derivePrivateKey(AptosDerivationPath.Ed25519()).toAip80().value shouldBe
        "ed25519-priv-0x5d996aa76b3212142792d9130796cd2e11e3c445a93118c08414df4f66bc60ec"
      mnemonic.deriveSecp256k1PrivateKey(AptosDerivationPath.Ecdsa()).toAip80().value shouldBe
        "secp256k1-priv-0x1eec55afc2f72c4ab7b46c84d761739035ac420a2b6b22cef3411adaf91ce1f7"
      AptosDerivationPath.Ed25519(addressIndex = 44u).value shouldBe
        "m/44'/637'/0'/0'/44'"
      AptosDerivationPath.Ecdsa(addressIndex = 44u).value shouldBe "m/44'/637'/0'/0/44"
    }

    "private-key clearing is explicit and rejects later signing" {
      val privateKey = ed25519PrivateKey(1)
      val account = Ed25519Account(privateKey)
      val message = HexInput.fromByteArray("aptos".encodeToByteArray())
      val signature = account.sign(message)

      account.verifySignature(message, signature).shouldBeTrue()
      account.clearPrivateKey()
      account.isPrivateKeyCleared.shouldBeTrue()
      privateKey.toString() shouldBe "<cleared Ed25519 private key>"
      shouldThrow<IllegalStateException> { account.sign(message) }
      shouldThrow<IllegalStateException> { privateKey.toByteArray() }
    }

    "MultiEd25519 signs in public-key order and round-trips its authenticator" {
      val account =
        MultiEd25519Account.fromPrivateKeys(
          privateKeys = listOf(ed25519PrivateKey(2), ed25519PrivateKey(3), ed25519PrivateKey(4)),
          signaturesRequired = 2,
        )
      val message = HexInput.fromByteArray("multi-ed25519".encodeToByteArray())
      val authenticator =
        account.signWithAuthenticator(message)
          .shouldBeInstanceOf<AccountAuthenticator.MultiEd25519>()

      account.verifySignature(message, authenticator.signature).shouldBeTrue()
      AccountAuthenticator.fromBcs(authenticator.toBcs())
        .shouldBeInstanceOf<AccountAuthenticator.MultiEd25519>()
      account.clearPrivateKey()
      account.isPrivateKeyCleared.shouldBeTrue()
    }

    "simulation preserves SingleKey and secondary signer authenticator variants" {
      val sender = Secp256k1PrivateKey(ByteArray(32) { 7 }).publicKey()
      val secondary = ed25519PrivateKey(8).publicKey()
      val transaction =
        UnsignedTransaction.MultiAgent(
          rawTransaction = rawTransaction(),
          secondarySignerAddresses = listOf(AccountAddress.fromString("0x2")),
        )

      val signed =
        SignedTransaction.fromBcs(
          generateSignedTransactionForSimulation(
            InputSimulateTransactionData(
              signerPublicKey = sender,
              transaction = transaction,
              secondarySignerPublicKeys = listOf(secondary),
            )
          )
        )
      val authenticator =
        signed.authenticator.shouldBeInstanceOf<TransactionAuthenticator.MultiAgent>()
      authenticator.sender.shouldBeInstanceOf<AccountAuthenticator.SingleKey>()
        .publicKey.shouldBeInstanceOf<AnyPublicKey>()
      authenticator.secondarySigners.single()
        .shouldBeInstanceOf<AccountAuthenticator.Ed25519>()
    }

    "Secp256r1 matches the pinned TypeScript SDK vector and clears private material" {
      val privateKey = Secp256r1PrivateKey(P256_PRIVATE.hexBytes())
      val publicKey = privateKey.publicKey()
      val message = P256_MESSAGE.hexBytes()
      val signature = privateKey.signBytes(message)

      publicKey.toByteArray().toHex() shouldBe P256_PUBLIC
      signature.toByteArray().toHex() shouldBe P256_SIGNATURE
      publicKey.verifyBytes(message, signature).shouldBeTrue()

      privateKey.clear()
      privateKey.isCleared.shouldBeTrue()
      shouldThrow<IllegalStateException> { privateKey.signBytes(message) }
    }

    "WebAuthn validates its challenge and round-trips the exact Aptos BCS layout" {
      val publicKey = Secp256r1PublicKey(P256_PUBLIC.hexBytes())
      val signature =
        WebAuthnSignature(
          signature = WEBAUTHN_SIGNATURE.hexBytes(),
          authenticatorData = WEBAUTHN_AUTHENTICATOR_DATA.hexBytes(),
          clientDataJson = WEBAUTHN_CLIENT_DATA.hexBytes(),
        )
      signature.verify(
        publicKey,
        WEBAUTHN_MESSAGE.hexBytes(),
        WebAuthnVerificationOptions(expectedOrigin = "https://example.com"),
      ).shouldBeTrue()
      signature.verify(publicKey, "wrong-message".encodeToByteArray()) shouldBe false

      val authenticator =
        AccountAuthenticator.SingleKey(
          publicKey = AnyPublicKey(publicKey),
          signature = AnySignature(signature),
        )
      authenticator.toBcs().toHex() shouldBe WEBAUTHN_ACCOUNT_AUTHENTICATOR
      AccountAuthenticator.fromBcs(authenticator.toBcs()).toBcs().toHex() shouldBe
        WEBAUTHN_ACCOUNT_AUTHENTICATOR
    }

    "PasskeyAccount gives applications an explicit suspend credential boundary" {
      val publicKey = Secp256r1PublicKey(P256_PUBLIC.hexBytes())
      val webAuthnSignature =
        WebAuthnSignature(
          signature = WEBAUTHN_SIGNATURE.hexBytes(),
          authenticatorData = WEBAUTHN_AUTHENTICATOR_DATA.hexBytes(),
          clientDataJson = WEBAUTHN_CLIENT_DATA.hexBytes(),
        )
      val account =
        PasskeyAccount(
          credentialPublicKey = publicKey,
          signer =
            WebAuthnSigner { request ->
              request.challenge.toHex() shouldBe WEBAUTHN_CHALLENGE
              AptosResult.Success(webAuthnSignature)
            },
        )

      val result = account.signBytes(WEBAUTHN_MESSAGE.hexBytes())
      val signature = result.shouldBeInstanceOf<AptosResult.Success<*>>().value
      val anySignature = signature.shouldBeInstanceOf<AnySignature>()
      account.verifySignature(WEBAUTHN_MESSAGE.hexBytes(), anySignature).shouldBeTrue()
    }
  })

private fun ed25519PrivateKey(fill: Int): Ed25519PrivateKey =
  Ed25519PrivateKey(ByteArray(32) { fill.toByte() })

private fun rawTransaction(): RawTransaction =
  RawTransaction(
    sender = AccountAddress.fromString("0x1"),
    sequenceNumber = 0uL,
    payload = TransactionPayload.entryFunction("0x1::coin::transfer"),
    maxGasAmount = 2_000uL,
    gasUnitPrice = 100uL,
    expirationTimestampSecs = 999_999uL,
    chainId = ChainId(4u),
  )

private fun ByteArray.toHex(): String =
  joinToString("") { (it.toInt() and 0xff).toString(16).padStart(2, '0') }

private fun String.hexBytes(): ByteArray =
  chunked(2).map { it.toInt(16).toByte() }.toByteArray()

private const val P256_PRIVATE =
  "0102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f20"
private const val P256_PUBLIC =
  "04515c3d6eb9e396b904d3feca7f54fdcd0cc1e997bf375dca515ad0a6c3b4035f4536be3a50f318fbf9a5475902a221502bef0d57e08c53b2cc0a56f17d9f9354"
private const val P256_MESSAGE = "6170746f732d736563703235367231"
private const val P256_SIGNATURE =
  "948f590fc7abb519506d529196c65eb382d18e48deb06e4ded407c538dfedc326953280b434df9ee34e228a95ec8aa4aea0270ab85f7a89eb0d3173902f367c8"
private const val WEBAUTHN_MESSAGE = "6170746f732d776562617574686e"
private const val WEBAUTHN_CHALLENGE =
  "094236aa1129a84001e0d7a1b4162c944144120cffb0f70f213dff7335ec1b5d"
private const val WEBAUTHN_AUTHENTICATOR_DATA =
  "49960de5880e8c687434170f6476605b8fe4aeb9a28632c7995cf3ba831d97631d00000000"
private const val WEBAUTHN_CLIENT_DATA =
  "7b2274797065223a22776562617574686e2e676574222c226368616c6c656e6765223a22435549327168457071454142344e6568744259736c45464545677a5f735063504954335f637a5873473130222c226f726967696e223a2268747470733a2f2f6578616d706c652e636f6d222c2263726f73734f726967696e223a66616c73657d"
private const val WEBAUTHN_SIGNATURE =
  "c8696a811c53eb2412ad0dd819c0b7055bcec838db6b7a302987f1641720c2041347b4c96ce593eb8e38384360ce6fd63930da78f7ea2f21d95ba39e0c2ef3b1"
private const val WEBAUTHN_ACCOUNT_AUTHENTICATOR =
  "02024104515c3d6eb9e396b904d3feca7f54fdcd0cc1e997bf375dca515ad0a6c3b4035f4536be3a50f318fbf9a5475902a221502bef0d57e08c53b2cc0a56f17d9f9354020040c8696a811c53eb2412ad0dd819c0b7055bcec838db6b7a302987f1641720c2041347b4c96ce593eb8e38384360ce6fd63930da78f7ea2f21d95ba39e0c2ef3b12549960de5880e8c687434170f6476605b8fe4aeb9a28632c7995cf3ba831d97631d0000000084017b2274797065223a22776562617574686e2e676574222c226368616c6c656e6765223a22435549327168457071454142344e6568744259736c45464545677a5f735063504954335f637a5873473130222c226f726967696e223a2268747470733a2f2f6578616d706c652e636f6d222c2263726f73734f726967696e223a66616c73657d"
