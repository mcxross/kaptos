/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.indexer

import io.ktor.client.call.body
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xyz.mcxross.kaptos.client.post
import xyz.mcxross.kaptos.internal.executeAptos
import xyz.mcxross.kaptos.internal.mapResponse
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.flatMap

/** Lossless GraphQL access for optional SDK modules with independently versioned schemas. */
interface IndexerService {
  /** Executes [document] with [variables], returning only the GraphQL `data` object. */
  suspend fun query(
    document: String,
    variables: JsonObject = buildJsonObject {},
  ): AptosResult<JsonObject>
}

@Serializable private data class GraphqlRequest(val query: String, val variables: JsonObject)

@Serializable
private data class GraphqlResponse(
  val data: JsonObject? = null,
  val errors: JsonArray? = null,
)

internal class DefaultIndexerService(private val config: TransportConfig) : IndexerService {
  override suspend fun query(
    document: String,
    variables: JsonObject,
  ): AptosResult<JsonObject> {
    if (document.isBlank()) {
      return AptosResult.Failure(AptosError.Validation("GraphQL document must not be blank"))
    }
    return executeAptos {
      post(
          RequestOptions.PostRequestOptions(
            aptosConfig = config,
            type = AptosApiType.INDEXER,
            originMethod = "queryIndexer",
            path = "",
            body = GraphqlRequest(document, variables),
          )
        )
        .toAptosResult()
        .mapResponse("Invalid indexer response") {
          it.body<GraphqlResponse>()
        }
        .flatMap { graphql ->
          if (!graphql.errors.isNullOrEmpty()) {
            AptosResult.Failure(AptosError.Indexer(graphql.errors.toString()))
          } else {
            graphql.data?.let { AptosResult.Success(it) }
              ?: AptosResult.Failure(AptosError.Indexer("Indexer returned no data"))
          }
        }
    }
  }
}
