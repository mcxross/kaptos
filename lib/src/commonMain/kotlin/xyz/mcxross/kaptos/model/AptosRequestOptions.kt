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

import xyz.mcxross.kaptos.client.ClientConfig

abstract class RequestOptions {

  /** The config for the API client */
  abstract val aptosConfig: AptosConfig

  /** The name of the API method */
  abstract val originMethod: String

  /** The URL path to the API method */
  abstract val path: String

  /** The content type of the request body */
  abstract val contentType: MimeType

  /** The accepted content type of the response of the API */
  abstract val acceptType: MimeType

  /** The query parameters for the request */
  abstract val params: Map<String, Any?>?

  /** Specific client overrides for this request to override aptosConfig */
  abstract val overrides: ClientConfig?

  data class AptosRequestOptions(
    override val aptosConfig: AptosConfig,

    /** The type of API endpoint to call e.g. fullnode, indexer, etc */
    var type: AptosApiType,

    /** The name of the API method */
    override val originMethod: String,

    /** The URL path to the API method */
    override val path: String,

    /** The content type of the request body */
    override val contentType: MimeType = MimeType.JSON,

    /** The accepted content type of the response of the API */
    override val acceptType: MimeType = MimeType.JSON,

    /** The query parameters for the request */
    override val params: Map<String, Any?>? = null,

    /** Specific client overrides for this request to override aptosConfig */
    override val overrides: ClientConfig? = null,
  ) : RequestOptions()

  data class GetAptosRequestOptions(

    /** The config for the API client */
    override val aptosConfig: AptosConfig,

    /** The name of the API method */
    override val originMethod: String,

    /** The URL path to the API method */
    override val path: String,

    /** The content type of the request body */
    override val contentType: MimeType = MimeType.JSON,

    /** The accepted content type of the response of the API */
    override val acceptType: MimeType = MimeType.JSON,

    /** The query parameters for the request */
    override val params: Map<String, Any?>? = null,

    /** Specific client overrides for this request to override aptosConfig */
    override val overrides: ClientConfig? = null,
  ) : RequestOptions()

  data class PostRequestOptions<T>(
    /** The config for the API client */
    override val aptosConfig: AptosConfig,
    /** The type of API endpoint to call e.g. fullnode, indexer, etc */
    var type: AptosApiType,
    /** The name of the API method */
    override val originMethod: String,
    /** The URL path to the API method */
    override val path: String,

    /** The content type of the request body */
    override val contentType: MimeType = MimeType.JSON,

    /** The accepted content type of the response of the API */
    override val acceptType: MimeType = MimeType.JSON,

    /** The query parameters for the request */
    override val params: Map<String, Any?>? = null,

    /** The body of the request, should match the content type of the request */
    var body: T? = null,

    /** Specific client overrides for this request to override aptosConfig */
    override val overrides: ClientConfig? = null,
  ) : RequestOptions()

  data class PostAptosRequestOptions<T>(

    /** The config for the API client */
    override val aptosConfig: AptosConfig,

    /** The name of the API method */
    override val originMethod: String,

    /** The URL path to the API method */
    override val path: String,

    /** The content type of the request body */
    override val contentType: MimeType = MimeType.JSON,

    /** The accepted content type of the response of the API */
    override val acceptType: MimeType = MimeType.JSON,

    /** The query parameters for the request */
    override val params: Map<String, Any?>? = null,

    /** The body of the request, should match the content type of the request */
    var body: T? = null,

    /** Specific client overrides for this request to override aptosConfig */
    override val overrides: ClientConfig? = null,
  ) : RequestOptions()
}
