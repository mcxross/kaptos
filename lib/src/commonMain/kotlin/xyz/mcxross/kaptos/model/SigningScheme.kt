package xyz.mcxross.kaptos.model

enum class SigningScheme(val value: Int) {
  Ed25519(0),
  MultiEd25519(1),
  SingleKey(2),
  MultiKey(3),
}
