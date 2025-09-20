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
package xyz.mcxross.kaptos.core

import xyz.mcxross.kaptos.model.Result

/**
 * The [Application] object is used to store global state for the SDK.
 *
 * @property graceFull [Boolean] indicating if the SDK is in graceFull mode. This is used to
 *   determine if the SDK should throw exceptions or return [Result.Err].
 */
internal object Application {
  var graceFull: Boolean = false
}
