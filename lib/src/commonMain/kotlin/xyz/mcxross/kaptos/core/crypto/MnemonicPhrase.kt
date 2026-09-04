/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package xyz.mcxross.kaptos.core.crypto

/** Supported BIP-39 mnemonic sizes. */
enum class MnemonicWordCount(val count: UInt) {
  Words12(12u),
  Words15(15u),
  Words18(18u),
  Words21(21u),
  Words24(24u),
}

/**
 * An Aptos BIP-44 derivation path.
 *
 * The type makes the Ed25519 hardened-path rule impossible to mix up with the ECDSA path rule.
 */
sealed class AptosDerivationPath(val value: String) {
  data class Ed25519(
    val accountIndex: UInt = 0u,
    val changeIndex: UInt = 0u,
    val addressIndex: UInt = 0u,
  ) :
    AptosDerivationPath(
      "m/44'/637'/$accountIndex'/$changeIndex'/$addressIndex'"
    )

  data class Ecdsa(
    val accountIndex: UInt = 0u,
    val changeIndex: UInt = 0u,
    val addressIndex: UInt = 0u,
  ) : AptosDerivationPath("m/44'/637'/$accountIndex'/$changeIndex/$addressIndex")

  override fun toString(): String = value
}

/** A validated BIP-39 phrase whose textual form is redacted by default. */
class MnemonicPhrase private constructor(private val phrase: String) {
  /** Explicitly reveal the mnemonic for secure application-controlled storage. */
  fun reveal(): String = phrase

  fun derivePrivateKey(
    path: AptosDerivationPath.Ed25519 = AptosDerivationPath.Ed25519(),
    passphrase: String = "",
  ): Ed25519PrivateKey =
    Ed25519PrivateKey(
      deriveMnemonicPrivateKey(phrase, passphrase, PrivateKeyType.Ed25519, path.value)
    )

  fun deriveSecp256k1PrivateKey(
    path: AptosDerivationPath.Ecdsa = AptosDerivationPath.Ecdsa(),
    passphrase: String = "",
  ): Secp256k1PrivateKey =
    Secp256k1PrivateKey(
      deriveMnemonicPrivateKey(phrase, passphrase, PrivateKeyType.Secp256k1, path.value)
    )

  fun deriveSecp256r1PrivateKey(
    path: AptosDerivationPath.Ecdsa = AptosDerivationPath.Ecdsa(),
    passphrase: String = "",
  ): Secp256r1PrivateKey =
    Secp256r1PrivateKey(
      deriveMnemonicPrivateKey(phrase, passphrase, PrivateKeyType.Secp256r1, path.value)
    )

  override fun toString(): String = "<redacted mnemonic>"

  companion object {
    /** Parse and validate an English BIP-39 phrase. */
    fun parse(phrase: String): MnemonicPhrase {
      val normalized = phrase.trim().lowercase().split(Regex("\\s+")).joinToString(" ")
      require(validateMnemonic(normalized)) { "Invalid BIP-39 mnemonic phrase" }
      return MnemonicPhrase(normalized)
    }

    /** Generate a new English BIP-39 phrase using FastKrypto's secure randomness. */
    fun generate(wordCount: MnemonicWordCount = MnemonicWordCount.Words24): MnemonicPhrase =
      MnemonicPhrase(generateMnemonic(wordCount.count))
  }
}
