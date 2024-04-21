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
 * Controls the number of results that are returned and the starting position of those results.
 *
 * @property offset Specifies the starting position of the query result within the set of data.
 *   Default is 0.
 * @property limit Specifies the maximum number of items or records to return in a query result.
 *   Default is 25.
 */
data class PaginationArgs(val offset: Int? = null, val limit: Int? = null)
