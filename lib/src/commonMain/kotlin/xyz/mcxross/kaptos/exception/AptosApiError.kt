/*
 * Copyright 2024 McXross
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

import xyz.mcxross.kaptos.model.AptosRequest
import xyz.mcxross.kaptos.model.AptosResponse

/**
 * The type returned from an API error
 *
 * @property name - the error name "AptosApiError"
 * @property url the url the request was made to
 * @property status - the response status. i.e. 400
 * @property statusText - the response message
 * @property data the response data
 * @property request - the AptosRequest
 */
class AptosApiError(val request: AptosRequest, val response: AptosResponse, message: String) :
  Exception(message) {

  val name: String = "AptosApiError"

  val url: String = response.call.request.url.toString()

  val status: Int = response.status.value

  val statusText: String = response.status.description

  val data: Any = "data currently not supported"

  override fun toString(): String {
    return "AptosApiError: $message - $url - $status - $statusText - $data"
  }
}
