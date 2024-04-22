package xyz.mcxross.kaptos.model

sealed class TypeTagOrString {
  data class TypeTag(val value: TypeTag?) : TypeTagOrString()

  data class Str(val value: Str?) : TypeTagOrString()
}
