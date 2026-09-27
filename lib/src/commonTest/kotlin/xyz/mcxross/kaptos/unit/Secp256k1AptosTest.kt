/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import xyz.mcxross.kaptos.account.SingleKeyAccount
import xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey
import xyz.mcxross.kaptos.model.HexInput

// Expected values come from @aptos-labs/ts-sdk for the same key and message.
class Secp256k1AptosTest :
  StringSpec({
    val key = Secp256k1PrivateKey.fromAip80("secp256k1-priv-0x" + "11".repeat(32))
    val message = HexInput.fromByteArray("flare".encodeToByteArray())

    "public key, signature and address match the Aptos TS SDK" {
      key.publicKey().toString() shouldBe
        "0x044f355bdcb7cc0af728ef3cceb9615d90684bb5b2ca5f859ab0f0b704075871aa385b6b1b8ead809ca67454d9683fcf2ba03456d6fe2c4abe2b07f0fbdbb2f1c1"
      key.sign(message).toString() shouldBe
        "0x5fd8d67ff016b9980faaa40885b7db827aa6ad123437ba0f96cc825f9187af532da425f48045a7dfb612cc8a8cf7e96a9701d176a680614730e6deb9464040fa"
      SingleKeyAccount(key).accountAddress.toString() shouldBe
        "0xdcb099897acadccb1862210aaf0408f16aad89d6a321203fde2f814b4f7fef53"
    }

    "signatures verify against the public key" {
      val signature = key.sign(message)
      key.publicKey().verifySignature(message, signature) shouldBe true
      key
        .publicKey()
        .verifySignature(HexInput.fromByteArray("other".encodeToByteArray()), signature) shouldBe
        false
    }
  })
