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
package xyz.mcxross.kaptos.exception

/** A typed representation of the specific error codes returned by the Aptos API. */
enum class AptosErrorCode {
  ACCOUNT_NOT_FOUND,
  RESOURCE_NOT_FOUND,
  MODULE_NOT_FOUND,
  STRUCT_FIELD_NOT_FOUND,
  VERSION_NOT_FOUND,
  TRANSACTION_NOT_FOUND,
  TABLE_ITEM_NOT_FOUND,
  BLOCK_NOT_FOUND,
  STATE_VALUE_NOT_FOUND,
  VERSION_PRUNED,
  BLOCK_PRUNED,
  INVALID_INPUT,
  INVALID_TRANSACTION_UPDATE,
  SEQUENCE_NUMBER_TOO_OLD,
  VM_ERROR,
  REJECTED_BY_FILTER,
  HEALTH_CHECK_FAILED,
  MEMPOOL_IS_FULL,
  INTERNAL_ERROR,
  WEB_FRAMEWORK_ERROR,
  BCS_NOT_SUPPORTED,
  API_DISABLED,
  // A fallback for any codes not yet included in this enum
  UNKNOWN_ERROR_CODE,
}
