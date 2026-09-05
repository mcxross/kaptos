package xyz.mcxross.kaptos.view.serialization

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.view.MoveViewResult

/** Decode one return value with an explicit serializer; no implicit Move-to-Kotlin coercion. */
fun <T> MoveViewResult.decodeValue(
  index: Int,
  deserializer: DeserializationStrategy<T>,
  json: Json = Json,
): AptosResult<T> {
  if (index !in values.indices)
    return AptosResult.Failure(
      AptosError.Validation("View return index $index is outside ${values.size} returned values")
    )
  return try {
    AptosResult.Success(json.decodeFromJsonElement(deserializer, values[index]))
  } catch (error: CancellationException) {
    throw error
  } catch (error: Exception) {
    AptosResult.Failure(
      AptosError.Serialization("Unable to decode view return value $index", error)
    )
  }
}
