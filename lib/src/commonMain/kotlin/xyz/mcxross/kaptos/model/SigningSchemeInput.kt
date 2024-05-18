package xyz.mcxross.kaptos.model

enum class SigningSchemeInput(val value: Int) {
  Ed25519(0),
  Secp256k1Ecdsa(2),
}
