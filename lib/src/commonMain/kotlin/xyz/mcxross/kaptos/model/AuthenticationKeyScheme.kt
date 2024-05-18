package xyz.mcxross.kaptos.model

sealed class AuthenticationKeyScheme {
  data class Signing(val scheme: SigningScheme) : AuthenticationKeyScheme()

  data class Derive(val scheme: DeriveScheme) : AuthenticationKeyScheme()
}
