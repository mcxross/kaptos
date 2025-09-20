/*
 * Copyright 2025 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.mcxross.kaptos.exception

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Represents a structured error response from the Aptos REST API. */
@Serializable
data class AptosApiError(
  /** A descriptive message about the error. */
  override val message: String,

  /** A specific error code for more granular error information. */
  @SerialName("error_code") val errorCode: String,

  /** Provides VM error details when submitting transactions. */
  @SerialName("vm_error_code") val vmErrorCode: Long? = null,
) : Exception(message) {

  /** A helper property to conveniently access the typed [AptosErrorCode]. */
  val code: AptosErrorCode
    get() =
      try {
        enumValueOf<AptosErrorCode>(errorCode.uppercase())
      } catch (e: IllegalArgumentException) {
        AptosErrorCode.UNKNOWN_ERROR_CODE
      }
}
