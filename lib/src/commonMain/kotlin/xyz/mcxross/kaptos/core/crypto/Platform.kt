package xyz.mcxross.kaptos.core.crypto

import xyz.mcxross.kaptos.model.SigningScheme

expect fun generateKeypair(scheme: SigningScheme) : KeyPair

expect fun fromSeed(seed: ByteArray): KeyPair

expect fun sha3Hash(input: ByteArray): ByteArray