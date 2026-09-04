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
package xyz.mcxross.kaptos.transaction.instances

import xyz.mcxross.kaptos.transaction.authenticator.transactionAuthenticator
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.transaction.authenticator.TransactionAuthenticator

data class SignedTransaction(
  val rawTxn: RawTransaction,
  val authenticator: TransactionAuthenticator,
) {
  fun toBcs(): ByteArray = rawTxn.toBcs() + authenticator.toBcs()

  companion object {
    fun fromBcs(bytes: ByteArray): SignedTransaction =
      AptosBcsReader(bytes).let { reader ->
        val rawTransaction = with(RawTransaction) { reader.rawTransaction() }
        SignedTransaction(rawTransaction, reader.transactionAuthenticator()).also {
          reader.ensureFinished()
        }
      }
  }
}
