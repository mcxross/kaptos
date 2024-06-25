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

import io.ktor.http.*

enum class Error(private val code: Int, val message: String) {
  // Move Standard Library Errors
  ABORTED(409, "Concurrency conflict, such as read-modify-write conflict");

  fun asHttpStatusCode(): HttpStatusCode {
    return HttpStatusCode(code, message)
  }
}
