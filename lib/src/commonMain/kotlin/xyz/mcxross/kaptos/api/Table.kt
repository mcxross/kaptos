package xyz.mcxross.kaptos.api

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.generated.GetTableItemsDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsMetadataQuery
import xyz.mcxross.kaptos.internal.getTableItemsData
import xyz.mcxross.kaptos.internal.getTableItemsMetadata
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.ProcessorType
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TableItemFilter
import xyz.mcxross.kaptos.model.TableItemSortOrder
import xyz.mcxross.kaptos.model.TableMetadataFilter
import xyz.mcxross.kaptos.model.TableMetadataSortOrder
import xyz.mcxross.kaptos.protocol.Table
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

/**
 * A class for querying Aptos `Table` related data.
 *
 * @property config The [AptosConfig] for connecting to the network.
 */
class Table(override val config: AptosConfig) : Table {

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
  override suspend fun getTableItemsData(
    filter: TableItemFilter,
    sortOrder: List<TableItemSortOrder>?,
    page: PaginationArgs?,
    minimumLedgerVersion: Long?,
  ): Result<GetTableItemsDataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.DEFAULT)
    return getTableItemsData(config, filter, sortOrder, page)
  }

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
  override suspend fun getTableItemsMetadata(
    filter: TableMetadataFilter,
    sortOrder: List<TableMetadataSortOrder>?,
    page: PaginationArgs?,
    minimumLedgerVersion: Long?,
  ): Result<GetTableItemsMetadataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.DEFAULT)
    return getTableItemsMetadata(config, filter, sortOrder, page)
  }
}
