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

import kotlinx.serialization.Serializable

@Serializable
enum class TypeTagVariants {
  Bool,
  U8,
  U64,
  U128,
  Address,
  Signer,
  Vector,
  Struct,
  U16,
  U32,
  U256,
  Reference, // This is specifically a placeholder and does not represent a real type
  Generic,
  // This is specifically a placeholder and does not represent a real type
}
