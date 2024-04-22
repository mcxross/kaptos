package xyz.mcxross.kaptos.model

enum class TokenStandard {
  V1,
  V2
}

data class TokenStandardArg(val tokenStandard: TokenStandard? = null)
