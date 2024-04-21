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

interface QueryParams {
  fun toMap(): Map<String, Any?>
}

class LedgerVersionQueryParam : QueryParams {
  var ledgerVersion: Long? = null

  override fun toMap(): Map<String, Any?> {
    return mapOf("ledger_version" to ledgerVersion)
  }
}

class SpecificPaginationQueryParams : QueryParams {
  var ledgerVersion: Long? = null
  var limit: Long? = null
  var start: Long? = null

  override fun toMap(): Map<String, Any?> {
    return mapOf("ledger_version" to ledgerVersion, "limit" to limit, "start" to start)
  }
}

class TransactionQueryParams : QueryParams {
  var withTransactions: Boolean? = null

  override fun toMap(): Map<String, Any?> {
    return mapOf("with_transactions" to withTransactions)
  }
}

class PaginationQueryParams : QueryParams {
  var offset: Long? = null
  var limit: Long? = null

  override fun toMap(): Map<String, Any?> {
    return mapOf("limit" to limit, "start" to offset)
  }
}
