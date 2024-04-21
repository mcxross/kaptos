package xyz.mcxross.kaptos.client

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.AptosResponse
import xyz.mcxross.kaptos.model.GraphqlQuery
import xyz.mcxross.kaptos.model.RequestOptions

suspend inline fun <reified V> post(options: RequestOptions.PostRequestOptions<V>): AptosResponse {
  return client.post(options.aptosConfig.getRequestUrl(options.type)) {
    url { appendPathSegments(options.path) }
    contentType(ContentType.Application.Json)
    setBody(options.body)
  }
}

suspend inline fun <reified T, reified V> postAptosFullNode(
  options: RequestOptions.PostAptosRequestOptions<V>
): Pair<AptosResponse, T> {
  val response =
    post<V>(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.FULLNODE,
        originMethod = options.originMethod,
        path = options.path,
        body = options.body,
      )
    )
  return Pair(response, response.body())
}

suspend fun postAptosIndexer(
  options: RequestOptions.PostAptosRequestOptions<GraphqlQuery>
): AptosResponse {
  val response =
    post(
      RequestOptions.PostRequestOptions(
        aptosConfig = options.aptosConfig,
        type = AptosApiType.INDEXER,
        originMethod = options.originMethod,
        path = "",
        body = options.body,
      )
    )
  return response
}
