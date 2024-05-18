package xyz.mcxross.kaptos.model

enum class DeriveScheme(val value: Int) {
  DeriveAuid(251),
  DeriveObjectAddressFromObject(252),
  DeriveObjectAddressFromGuid(253),
  DeriveObjectAddressFromSeed(254),
  DeriveResourceAccountAddress(255),
}
