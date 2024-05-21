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

/**
 * Representation of a ModuleId that can serialized and deserialized ModuleId means the module
 * address (e.g "0x1") and the module name (e.g "coin")
 *
 * @param address The account address. e.g "0x1"
 * @param name The module name under the "address". e.g "coin"
 */
@Serializable
data class ModuleId(val address: AccountAddress, val name: Identifier) {
  companion object {
    fun fromString(moduleId: MoveModuleId): ModuleId {
      val parts = moduleId.split("::")
      return ModuleId(AccountAddress(parts[0]), Identifier(parts[1]))
    }
  }
}
