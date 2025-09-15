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

package xyz.mcxross.kaptos.util

const val APTOS_COIN = "0x1::aptos_coin::AptosCoin"

const val APTOS_FA = "0x000000000000000000000000000000000000000000000000000000000000000a"

const val RAW_TRANSACTION_SALT = "APTOS::RawTransaction"

const val RAW_TRANSACTION_WITH_DATA_SALT = "APTOS::RawTransactionWithData"

val DEFAULT_CLIENT_HEADERS = mapOf("x-aptos-client" to "aptos-kmp-sdk/0.1.0")

/**
 * The default number of seconds to wait for a transaction to be processed.
 *
 * This time is the amount of time that the SDK will wait for a transaction to be processed when
 * waiting for the results of the transaction. It may take longer based on network connection and
 * network load.
 */
const val DEFAULT_TXN_TIMEOUT_SEC = 20

/**
 * The default max gas amount when none is given.
 *
 * This is the maximum number of gas units that will be used by a transaction before being rejected.
 *
 * Note that max gas amount varies based on the transaction. A larger transaction will go over this
 * default gas amount, and the value will need to be changed for the specific transaction.
 */
const val DEFAULT_MAX_GAS_AMOUNT = 200000L

/**
 * The default transaction expiration seconds from now.
 *
 * This time is how long until the blockchain nodes will reject the transaction.
 *
 * Note that the transaction expiration time varies based on network connection and network load. It
 * may need to be increased for the transaction to be processed.
 */
const val DEFAULT_TXN_EXP_SEC_FROM_NOW = 20L
