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

package xyz.mcxross.kaptos.model

enum class MimeType(val type: String) {
  /** JSON representation, used for transaction submission and accept type JSON output */
  JSON("application/json"),
  /** BCS representation, used for accept type BCS output */
  BCS("application/x-bcs"),
  /** BCS representation, used for transaction submission in BCS input */
  BCS_SIGNED_TRANSACTION("application/x.aptos.signed_transaction+bcs"),
  BCS_VIEW_FUNCTION("application/x.aptos.view_function+bcs"),
}
