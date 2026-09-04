/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import kotlin.time.Clock
import xyz.mcxross.fastkrypto.secureRandomBytes
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.model.HexInput

/** Temporary signing key bound into a Keyless proof and OIDC nonce. */
class EphemeralKeyPair(
  private val privateKey: Ed25519PrivateKey,
  val expiryDateSecs: ULong = defaultExpiry(),
  blinder: ByteArray = secureRandomBytes(BLINDER_LENGTH.toUInt()),
) : AutoCloseable {
  val publicKey: Ed25519PublicKey = privateKey.publicKey()
  private val blinderBytes: ByteArray = blinder.copyOf()
  val blinder: ByteArray
    get() = blinderBytes.copyOf()
  val nonce: String

  init {
    require(blinderBytes.size == BLINDER_LENGTH) { "blinder must be $BLINDER_LENGTH bytes" }
    nonce =
      poseidon(
          padAndPackBytesWithLength(ephemeralPublicKeyBcs(), 93) +
            field(expiryDateSecs) +
            blinderBytes.copyOf(32)
        )
        .toUnsignedDecimal()
  }

  val isCleared: Boolean
    get() = privateKey.isCleared

  /** Returns true once the OIDC-bound expiry timestamp has passed. */
  fun isExpired(nowSecs: Long = Clock.System.now().epochSeconds): Boolean =
    nowSecs >= 0 && nowSecs.toULong() > expiryDateSecs

  /** Signs with the ephemeral Ed25519 key after checking expiry and clear state. */
  fun sign(message: ByteArray): Ed25519Signature {
    check(!isExpired()) { "Ephemeral key pair has expired" }
    check(!isCleared) { "Ephemeral key pair has been cleared" }
    return privateKey.sign(HexInput.fromByteArray(message))
  }

  /** Clears the private key and blinder. The public OIDC nonce intentionally remains available. */
  fun clear() {
    if (isCleared) return
    privateKey.clear()
    blinderBytes.fill(0)
  }

  override fun close() = clear()

  /** Serializes the private ephemeral state for application-protected storage. */
  fun toBcs(): ByteArray =
    KeylessBcsWriter().also { writer ->
      writer.uleb128(0u) // EphemeralPublicKeyVariant::Ed25519
      writer.bytes(privateKey.toByteArray())
      writer.u64(expiryDateSecs)
      writer.fixed(blinderBytes)
    }.toByteArray()

  internal fun ephemeralPublicKeyBcs(): ByteArray =
    KeylessBcsWriter().also { writer ->
      writer.uleb128(0u)
      writer.bytes(publicKey.toByteArray())
    }.toByteArray()

  companion object {
    /** Required protocol blinder length. */
    const val BLINDER_LENGTH: Int = 31
    private const val TWO_WEEKS_SECONDS: ULong = 1_209_600u

    /** Generates a new ephemeral key, blinder, and derived OIDC nonce. */
    fun generate(expiryDateSecs: ULong = defaultExpiry()): EphemeralKeyPair =
      EphemeralKeyPair(Ed25519PrivateKey.generate(), expiryDateSecs)

    /** Restores an ephemeral key pair from its canonical BCS form. */
    fun fromBcs(bytes: ByteArray): EphemeralKeyPair =
      KeylessBcsReader(bytes).let { reader ->
        require(reader.uleb128() == 0u) { "Only Ed25519 ephemeral keys are supported" }
        val privateKey = Ed25519PrivateKey(reader.bytes())
        val expiry = reader.u64()
        val blinder = reader.fixed(BLINDER_LENGTH)
        EphemeralKeyPair(privateKey, expiry, blinder).also { reader.ensureFinished() }
      }

    private fun defaultExpiry(): ULong {
      val now = Clock.System.now().epochSeconds.coerceAtLeast(0).toULong()
      val twoWeeksFromNow = now + TWO_WEEKS_SECONDS
      return (twoWeeksFromNow / 3_600u) * 3_600u
    }
  }
}

/** Generates an ephemeral Keyless key pair owned by this client's managed lifecycle. */
fun Aptos.ephemeralKeyPair(): EphemeralKeyPair = own(EphemeralKeyPair.generate())

/** Generates a managed ephemeral Keyless key pair with an explicit expiry. */
fun Aptos.ephemeralKeyPair(expiryDateSecs: ULong): EphemeralKeyPair =
  own(EphemeralKeyPair.generate(expiryDateSecs))
