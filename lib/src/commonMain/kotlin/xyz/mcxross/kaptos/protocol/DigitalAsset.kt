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

package xyz.mcxross.kaptos.protocol

import kotlin.coroutines.cancellation.CancellationException
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.model.TokenData


interface DigitalAsset {
  @Throws(AptosException::class, CancellationException::class)
  suspend fun getTokenData(offset: Int? = null, limit: Int? = null): Option<TokenData?>
}
