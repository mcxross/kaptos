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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.mcxross.kaptos.serialize.MoveBoolSerializer
import xyz.mcxross.kaptos.serialize.MoveListTypeSerializer
import xyz.mcxross.kaptos.serialize.MoveUint8TypeSerializer
import xyz.mcxross.kaptos.serialize.MoveStringSerializer

@Serializable
enum class MoveVisibility {
  @SerialName("public") PUBLIC,
  @SerialName("private") PRIVATE,
  @SerialName("friend") FRIEND,
}

@Serializable
enum class MoveAbility {
  @SerialName("store") STORE,
  @SerialName("drop") DROP,
  @SerialName("key") KEY,
  @SerialName("copy") COPY,
}

@Serializable data class MoveFunctionGenericTypeParam(val constraints: List<MoveAbility>)

@Serializable
data class MoveFunction(
  val name: String,
  val visibility: MoveVisibility,
  @SerialName("is_entry") val isEntry: Boolean,
  @SerialName("is_view") val isView: Boolean,
  @SerialName("generic_type_params") val genericTypeParams: List<MoveFunctionGenericTypeParam>,
  val params: List<String>,
  val `return`: List<String>,
)

@Serializable data class MoveStructField(val name: String, val type: String)

@Serializable
data class MoveStruct(
  val name: String,
  @SerialName("is_native") val isNative: Boolean,
  val abilities: List<MoveAbility>,
  @SerialName("generic_type_params") val genericTypeParams: List<MoveFunctionGenericTypeParam>,
  val fields: List<MoveStructField>,
)

@Serializable
data class MoveModule(
  val address: String,
  val name: String,
  val friends: List<MoveModuleId>,
  @SerialName("exposed_functions") val exposedFunctions: List<MoveFunction>,
  val structs: List<MoveStruct>,
)

@Serializable
sealed class MoveValue {

  @Serializable(with = MoveBoolSerializer::class) data class Bool(val value: Boolean) : MoveValue()

  @Serializable(with = MoveStringSerializer::class)
  data class String(val value: kotlin.String) : MoveValue()

  @Serializable(with = MoveUint8TypeSerializer::class)
  data class MoveUint8Type(val value: Int) : MoveValue()

  @Serializable data class MoveUint16Type(val value: Int) : MoveValue()

  @Serializable data class MoveUint32Type(val value: Int) : MoveValue()

  @Serializable data class MoveUint64Type(val value: kotlin.String) : MoveValue()

  @Serializable data class MoveUint128Type(val value: kotlin.String) : MoveValue()

  @Serializable data class MoveUint256Type(val value: kotlin.String) : MoveValue()

  @Serializable data class MoveAddressType(val value: kotlin.String) : MoveValue()

  @Serializable data class MoveObjectType(val value: kotlin.String) : MoveValue()

  @Serializable data class MoveStructId(val value: kotlin.String) : MoveValue()

  @Serializable data class MoveOptionType(val value: MoveValue?) : MoveValue()

  @Serializable(with = MoveListTypeSerializer::class)
  data class MoveListType(val value: List<MoveValue>) : MoveValue()
}
