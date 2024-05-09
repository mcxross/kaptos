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

import xyz.mcxross.kaptos.util.DEFAULT_TXN_TIMEOUT_SEC

/**
 * Options for waiting for a transaction to be finalized.
 *
 * @param timeoutSecs The maximum number of seconds to wait for the transaction to be finalized.
 * @param checkSuccess If true, the method will return an error if the transaction is not
 *   successful.
 * @param waitForIndexer If true, the method will wait for the transaction to be indexed by the
 *   indexer.
 */
data class WaitForTransactionOptions(
  val timeoutSecs: Int = DEFAULT_TXN_TIMEOUT_SEC,
  val checkSuccess: Boolean = true,
  val waitForIndexer: Boolean? = null,
)
