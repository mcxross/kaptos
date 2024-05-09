package xyz.mcxross.kaptos.model

/**
 * The list of supported Processor types for our indexer api.
 *
 * These can be found from the processor_status table in the indexer database. {@link
 * https://cloud.hasura.io/public/graphiql?endpoint=https://api.mainnet.aptoslabs.com/v1/graphql}
 */
enum class ProcessorType(val value: String) {
  ACCOUNT_TRANSACTION_PROCESSOR("account_transactions_processor"),
  DEFAULT("default_processor"),
  EVENTS_PROCESSOR("events_processor"),
  FUNGIBLE_ASSET_PROCESSOR("fungible_asset_processor"),
  STAKE_PROCESSOR("stake_processor"),
  TOKEN_V2_PROCESSOR("token_v2_processor"),
  USER_TRANSACTION_PROCESSOR("user_transaction_processor"),
}
