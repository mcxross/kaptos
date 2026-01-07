package xyz.mcxross.kaptos.internal

import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.client.postAptosFullNodeAndGetData
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.exception.AptosSdkError
import xyz.mcxross.kaptos.generated.GetTableItemsDataQuery
import xyz.mcxross.kaptos.generated.GetTableItemsMetadataQuery
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TableItemFilter
import xyz.mcxross.kaptos.model.TableItemRequest
import xyz.mcxross.kaptos.model.TableItemSortOrder
import xyz.mcxross.kaptos.model.TableMetadataFilter
import xyz.mcxross.kaptos.model.TableMetadataSortOrder
import xyz.mcxross.kaptos.util.toOptional

suspend inline fun <reified T> getTableItem(
  aptosConfig: AptosConfig,
  handle: String,
  data: TableItemRequest,
  param: Map<String, Any?>? = null,
): Result<T, AptosSdkError> {
  return postAptosFullNodeAndGetData<T, TableItemRequest>(
      RequestOptions.PostAptosRequestOptions(
        aptosConfig = aptosConfig,
        originMethod = "getTableItem",
        path = "tables/${handle}/item",
        body = data,
        params = param,
      )
    )
    .toResult()
}

internal suspend fun getTableItemsData(
  config: AptosConfig,
  filter: TableItemFilter,
  sortOrder: List<TableItemSortOrder>?,
  page: PaginationArgs?,
): Result<GetTableItemsDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetTableItemsDataQuery(
            where_condition = filter,
            order_by = sortOrder.toOptional(),
            offset = page?.limit.toOptional(),
            limit = page?.limit.toOptional(),
          )
        )
    }
    .toResult()

internal suspend fun getTableItemsMetadata(
  config: AptosConfig,
  filter: TableMetadataFilter,
  sortOrder: List<TableMetadataSortOrder>?,
  page: PaginationArgs?,
): Result<GetTableItemsMetadataQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetTableItemsMetadataQuery(
            where_condition = filter,
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()
