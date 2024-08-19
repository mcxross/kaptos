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
package xyz.mcxross.kaptos.extension

import xyz.mcxross.kaptos.model.MoveFunctionId
import xyz.mcxross.kaptos.model.MoveStructId
import xyz.mcxross.kaptos.util.getFunctionParts

/** An extension function to convert a [MoveFunctionId] to its parts */
fun MoveFunctionId.parts(): Triple<String, String, String> = getFunctionParts(this)

fun MoveStructId.structParts(): Triple<String, String, String> =
  this.split("::").let { Triple(it[0], it[1], it[2]) }
