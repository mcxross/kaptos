package xyz.mcxross.kaptos.protocol

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetTableItemsDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsMetadataQuery
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.LedgerVersionQueryParam
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TableItemFilter
import xyz.mcxross.kaptos.model.TableItemRequest
import xyz.mcxross.kaptos.model.TableItemSortOrder
import xyz.mcxross.kaptos.model.TableMetadataFilter
import xyz.mcxross.kaptos.model.TableMetadataSortOrder

/** An interface for querying Aptos `Table` related data. */
interface Table {

  val config: AptosConfig

  /**
   * Queries for table items data with optional filtering and pagination.
   *
   * ## Usage
   *
   * ```kotlin
   * // First, get a table handle from a known resource
   * val resourceResult = aptos.getAccountResource<SupplyWrapper>(...)
   * val handle = resourceResult.value.data.supply.vec.first().aggregator.vec.first().handle
   *
   * // Then, query for items in that table
   * val filter = tableItemsFilter {
   * tableHandle = stringFilter { eq = handle }
   * transactionVersion = bigintFilter { eq = 0 }
   * }
   * val resolution = aptos.getTableItemsData(filter = filter)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved table items data: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving table items data: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param filter Conditions to filter the response.
   * @param sortOrder An optional list of sorting options for the results.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getTableItemsData(
    filter: TableItemFilter,
    sortOrder: List<TableItemSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetTableItemsDataQuery.Data?, AptosIndexerError>

  /**
   * Queries for the metadata of table items, allowing for filtering and pagination.
   *
   * ## Usage
   *
   * ```kotlin
   * // First, get a table handle from a known resource
   * val resourceResult = aptos.getAccountResource<SupplyWrapper>(...)
   * val handle = resourceResult.value.data.supply.vec.first().aggregator.vec.first().handle
   *
   * // Then, query for the table's metadata
   * val filter = tableMetadatasFilter { this.handle = stringFilter { eq = handle } }
   * val resolution = aptos.getTableItemsMetadata(filter = filter)
   *
   * when (resolution) {
   * is Result.Ok -> {
   * val data = resolution.value
   * println("Successfully retrieved table metadata: $data")
   * }
   * is Result.Err -> {
   * println("Error retrieving table metadata: ${resolution.error.message}")
   * }
   * }
   * ```
   *
   * @param filter Conditions to filter the response.
   * @param sortOrder An optional list of sorting options for the results.
   * @param page Optional pagination arguments (`limit` and `offset`).
   * @param minimumLedgerVersion An optional ledger version. The function will wait for the indexer
   *   to be at or beyond this version before querying.
   * @return A `Result` which is either `Result.Ok` containing the query data, or `Result.Err`
   *   containing an [AptosIndexerError].
   */
  suspend fun getTableItemsMetadata(
    filter: TableMetadataFilter,
    sortOrder: List<TableMetadataSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetTableItemsMetadataQuery.Data?, AptosIndexerError>
}

/**
 * Retrieves a specific item from a table, identified by the table's handle and the item's key.
 *
 * ## Usage
 *
 * ```kotlin
 * // First, get a table handle and key from a known resource
 * val resourceResult = aptos.getAccountResource<SupplyWrapper>(...)
 * val (handle, key) = resourceResult.value.data.supply.vec.first().aggregator.vec.first()
 *
 * // Then, retrieve the specific item from that table
 * val resolution = aptos.getTableItem<Long>(
 * handle = handle,
 * data = TableItemRequest(key_type = "address", value_type = "u128", key = key),
 * )
 *
 * when (resolution) {
 * is Result.Ok -> {
 * val data = resolution.value
 * println("Successfully retrieved table item value: $data")
 * }
 * is Result.Err -> {
 * println("Error retrieving table item: ${resolution.error.message}")
 * }
 * }
 * ```
 *
 * @param T The data class to deserialize the table item's value into.
 * @param handle A pointer to where the table is stored.
 * @param data An object that describes the table item, including its key and value types.
 * @param param An optional ledger version to query.
 * @return A `Result` which is either `Result.Ok` containing the deserialized table item of type
 *   `T`, or `Result.Err` containing an [AptosSdkError].
 */
suspend inline fun <reified T> Table.getTableItem(
  handle: String,
  data: TableItemRequest,
  param: LedgerVersionQueryParam? = null,
): Result<T, AptosSdkError> {
  return xyz.mcxross.kaptos.internal.getTableItem<T>(this.config, handle, data, param?.toMap())
}
