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

/**
 * The list of supported Processor types for our indexer api.
 *
 * These can be found from the processor_status table in the indexer database. {@link
 * https://cloud.hasura.io/public/graphiql?endpoint=https://api.mainnet.aptoslabs.com/v1/graphql}
 */
enum class ProcessorType(val value: String) {
  ACCOUNT_RESTORATION_PROCESSOR("account_restoration_processor"),
  ACCOUNT_TRANSACTION_PROCESSOR("account_transactions_processor"),
  DEFAULT("default_processor"),
  EVENTS_PROCESSOR("events_processor"),
  FUNGIBLE_ASSET_PROCESSOR("fungible_asset_processor"),
  STAKE_PROCESSOR("stake_processor"),
  TOKEN_V2_PROCESSOR("token_v2_processor"),
  USER_TRANSACTION_PROCESSOR("user_transaction_processor"),
  OBJECT_PROCESSOR("objects_processor"),
}
