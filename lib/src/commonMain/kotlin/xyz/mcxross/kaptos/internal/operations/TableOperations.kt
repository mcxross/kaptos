package xyz.mcxross.kaptos.internal.operations

import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetTableItemsDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsMetadataQuery
import xyz.mcxross.kaptos.internal.getTableItemsData
import xyz.mcxross.kaptos.internal.getTableItemsMetadata
import xyz.mcxross.kaptos.model.LedgerVersionQueryParam
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.ProcessorType
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TableItemFilter
import xyz.mcxross.kaptos.model.TableItemRequest
import xyz.mcxross.kaptos.model.TableItemSortOrder
import xyz.mcxross.kaptos.model.TableMetadataFilter
import xyz.mcxross.kaptos.model.TableMetadataSortOrder
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.util.waitForIndexerOnVersion

internal class TableOperations(val config: TransportConfig) {

  suspend fun getTableItemsData(
    filter: TableItemFilter,
    sortOrder: List<TableItemSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetTableItemsDataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.DEFAULT)
    return getTableItemsData(config, filter, sortOrder, page)
  }

  suspend fun getTableItemsMetadata(
    filter: TableMetadataFilter,
    sortOrder: List<TableMetadataSortOrder>? = null,
    page: PaginationArgs? = null,
    minimumLedgerVersion: Long? = null,
  ): Result<GetTableItemsMetadataQuery.Data?, AptosIndexerError> {
    waitForIndexerOnVersion(config, minimumLedgerVersion, ProcessorType.DEFAULT)
    return getTableItemsMetadata(config, filter, sortOrder, page)
  }
}

internal suspend inline fun <reified T> TableOperations.getTableItem(
  handle: String,
  data: TableItemRequest,
  param: LedgerVersionQueryParam? = null,
): Result<T, AptosSdkError> {
  return xyz.mcxross.kaptos.internal.getTableItem<T>(this.config, handle, data, param?.toMap())
}
