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

package xyz.mcxross.kaptos.internal

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result as InternalResult
import com.github.michaelbull.result.fold
import xyz.mcxross.kaptos.model.Result

fun <V, E> InternalResult<V, E>.toResult(): Result<V, E> {
  return fold(success = { Result.Ok(it) }, failure = { Result.Err(it) })
}

fun <V, E> Result<V, E>.toInternalResult(): InternalResult<V, E> {
  return when (this) {
    is Result.Ok -> Ok(this.value)
    is Result.Err -> Err(this.error)
  }
}
