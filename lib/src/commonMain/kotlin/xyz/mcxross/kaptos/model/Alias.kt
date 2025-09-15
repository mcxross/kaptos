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

import io.ktor.client.request.*
import io.ktor.client.statement.*
import xyz.mcxross.kaptos.generated.type.Auth_key_account_addresses_bool_exp
import xyz.mcxross.kaptos.generated.type.Auth_key_account_addresses_order_by
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_aptos_names_order_by
import xyz.mcxross.kaptos.generated.type.Current_collection_ownership_v2_view_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_collection_ownership_v2_view_order_by
import xyz.mcxross.kaptos.generated.type.Current_collections_v2_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_collections_v2_order_by
import xyz.mcxross.kaptos.generated.type.Current_fungible_asset_balances_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_fungible_asset_balances_order_by
import xyz.mcxross.kaptos.generated.type.Current_objects_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_objects_order_by
import xyz.mcxross.kaptos.generated.type.Current_token_datas_v2_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_token_datas_v2_order_by
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_bool_exp
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_order_by
import xyz.mcxross.kaptos.generated.type.Events_bool_exp
import xyz.mcxross.kaptos.generated.type.Events_order_by
import xyz.mcxross.kaptos.generated.type.Fungible_asset_activities_bool_exp
import xyz.mcxross.kaptos.generated.type.Fungible_asset_metadata_bool_exp
import xyz.mcxross.kaptos.generated.type.Num_active_delegator_per_pool_bool_exp
import xyz.mcxross.kaptos.generated.type.Num_active_delegator_per_pool_order_by
import xyz.mcxross.kaptos.generated.type.Processor_status_bool_exp
import xyz.mcxross.kaptos.generated.type.Public_key_auth_keys_bool_exp
import xyz.mcxross.kaptos.generated.type.Public_key_auth_keys_order_by
import xyz.mcxross.kaptos.generated.type.Table_items_bool_exp
import xyz.mcxross.kaptos.generated.type.Table_items_order_by
import xyz.mcxross.kaptos.generated.type.Table_metadatas_bool_exp
import xyz.mcxross.kaptos.generated.type.Table_metadatas_order_by
import xyz.mcxross.kaptos.generated.type.Token_activities_v2_bool_exp
import xyz.mcxross.kaptos.generated.type.Token_activities_v2_order_by

typealias AptosResponse = HttpResponse

typealias AptosRequest = HttpRequest

typealias AnyTransactionPayloadInstance = TransactionPayload

typealias FungibleAssetBalanceFilter = Current_fungible_asset_balances_bool_exp

typealias FungibleAssetSortOrder = Current_fungible_asset_balances_order_by

typealias AuthKeyAddressFilter = Auth_key_account_addresses_bool_exp

typealias AuthKeyAddressSortOrder = Auth_key_account_addresses_order_by

typealias CollectionOwnershipV2ViewFilter = Current_collection_ownership_v2_view_bool_exp

typealias CollectionOwnershipV2ViewSortOrder = Current_collection_ownership_v2_view_order_by

typealias CollectionOwnershipV2Filter = Current_collections_v2_bool_exp

typealias CollectionOwnershipV2SortOrder = Current_collections_v2_order_by

typealias TokenOwnershipV2Filter = Current_token_ownerships_v2_bool_exp

typealias TokenOwnershipV2SortOrder = Current_token_ownerships_v2_order_by

typealias PublicKeyAuthKeyFilter = Public_key_auth_keys_bool_exp

typealias PublicKeyAuthKeySortOrder = Public_key_auth_keys_order_by

typealias EventFilter = Events_bool_exp

typealias EventSortOrder = Events_order_by

typealias FungibleAssetActivityFilter = Fungible_asset_activities_bool_exp

typealias FungibleAssetMetadataFilter = Fungible_asset_metadata_bool_exp

typealias AptosNameFilter = Current_aptos_names_bool_exp

typealias AptosNameSortOrder = Current_aptos_names_order_by

typealias ActiveDelegatorCountFilter = Num_active_delegator_per_pool_bool_exp

typealias ActiveDelegatorCountSortOrder = Num_active_delegator_per_pool_order_by

typealias ObjectFilter = Current_objects_bool_exp

typealias ObjectSortOrder = Current_objects_order_by

typealias ProcessorStatusFilter = Processor_status_bool_exp

typealias TableItemFilter = Table_items_bool_exp

typealias TableItemSortOrder = Table_items_order_by

typealias TableMetadataFilter = Table_metadatas_bool_exp

typealias TableMetadataSortOrder = Table_metadatas_order_by

typealias TokenActivityV2Filter = Token_activities_v2_bool_exp

typealias TokenActivityV2SortOrder = Token_activities_v2_order_by

typealias TokenDataV2Filter = Current_token_datas_v2_bool_exp

typealias TokenDataV2SortOrder = Current_token_datas_v2_order_by

typealias ActiveDelegatorPerPoolOrder = Num_active_delegator_per_pool_order_by

typealias MoveModuleId = String

typealias MoveFunctionId = MoveModuleId

typealias MoveStructId = String
