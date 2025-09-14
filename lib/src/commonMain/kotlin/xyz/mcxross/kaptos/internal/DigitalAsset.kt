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

package xyz.mcxross.kaptos.internal

import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.exception.AptosIndexerError
import xyz.mcxross.kaptos.extension.asAccountAddress
import xyz.mcxross.kaptos.generated.GetCollectionDataQuery
import xyz.mcxross.kaptos.generated.GetCurrentTokenOwnershipQuery
import xyz.mcxross.kaptos.generated.GetEventsQuery
import xyz.mcxross.kaptos.generated.GetTokenDataQuery
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.model.types.currentCollectionsV2Filter
import xyz.mcxross.kaptos.model.types.currentTokenOwnershipsV2Filter
import xyz.mcxross.kaptos.model.types.numericFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser.parseTypeTag
import xyz.mcxross.kaptos.util.toOptional

suspend fun getCollectionData(
  config: AptosConfig,
  filter: CollectionOwnershipV2Filter,
): Result<GetCollectionDataQuery.Data?, AptosIndexerError> =
  handleQuery { getGraphqlClient(config).query(GetCollectionDataQuery(filter)) }.toResult()

suspend fun getCollectionDataByCollectionId(
  config: AptosConfig,
  collectionId: String,
): Result<GetCollectionDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      val filter = currentCollectionsV2Filter {
        this.collectionId = stringFilter { eq = collectionId }
      }
      getGraphqlClient(config).query(GetCollectionDataQuery(where_condition = filter))
    }
    .toResult()

suspend fun getTokenData(
  config: AptosConfig,
  page: PaginationArgs?,
): Result<GetTokenDataQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetTokenDataQuery(limit = page?.limit.toOptional(), offset = page?.offset.toOptional())
        )
    }
    .toResult()

private val structTag = StructTag(AccountAddress.ONE, "string", "string", emptyList())

private val collectionAbi =
  EntryFunctionABI(
    emptyList(),
    listOf(
      TypeTagStruct(structTag),
      TypeTagU64,
      TypeTagStruct(structTag),
      TypeTagStruct(structTag),
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagBool,
      TypeTagU64,
      TypeTagU64,
    ),
  )

internal suspend fun createCollectionTransaction(
  config: AptosConfig,
  creator: Account,
  name: String,
  description: String,
  uri: String,
  collectionOptions: CreateCollectionOptions,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(description.length <= 2048) { "Description must be less than 2048 characters" }

  require(uri.length <= 512) { "URI must be less than 512 characters" }

  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            // Don't alter the order of the arguments
            entryFunctionData {
              function = "0x4::aptos_token::create_collection"
              functionArguments = functionArguments {
                +MoveString(description)
                +U64(collectionOptions.maxSupply.toULong())
                +MoveString(name)
                +MoveString(uri)
                +Bool(collectionOptions.mutableDescription)
                +Bool(collectionOptions.mutableRoyalty)
                +Bool(collectionOptions.mutableURI)
                +Bool(collectionOptions.mutableTokenDescription)
                +Bool(collectionOptions.mutableTokenName)
                +Bool(collectionOptions.mutableTokenProperties)
                +Bool(collectionOptions.mutableTokenURI)
                +Bool(collectionOptions.tokensBurnableByCreator)
                +Bool(collectionOptions.tokensFreezableByCreator)
                +U64(collectionOptions.royaltyNumerator.toULong())
                +U64(collectionOptions.royaltyDenominator.toULong())
              }
              abi = collectionAbi
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

private val mintDigitalAssetAbi =
  EntryFunctionABI(
    typeParameters = emptyList(),
    parameters =
      listOf(
        TypeTagStruct(type = structTag),
        TypeTagStruct(type = structTag),
        TypeTagStruct(type = structTag),
        TypeTagStruct(type = structTag),
        TypeTagVector(type = TypeTagStruct(type = structTag)),
        TypeTagVector(type = TypeTagStruct(type = structTag)),
        TypeTagVector(type = TypeTagVector.u8()),
      ),
  )

internal suspend fun mintDigitalAssetTransaction(
  config: AptosConfig,
  creator: Account,
  collection: String,
  name: String,
  description: String,
  uri: String,
  propertyKeys: List<String>?,
  propertyTypes: List<String>?,
  propertyValues: List<String>?,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(description.length <= 2048) { "Description must be less than 2048 characters" }
  require(uri.length <= 512) { "URI must be less than 512 characters" }
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::mint"
              functionArguments = functionArguments {
                +MoveString(collection)
                +MoveString(description)
                +MoveString(name)
                +MoveString(uri)
                +MoveVector.string(listOf())
                +MoveVector.string(listOf())
                +MoveVector.string(listOf())
              }
              abi = mintDigitalAssetAbi
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun transferDigitalAssetTransaction(
  config: AptosConfig,
  sender: Account,
  digitalAssetAddress: AccountAddressInput,
  recipient: AccountAddressInput,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split(":::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = sender.accountAddress,
          data =
            entryFunctionData {
              function = "0x1::object::transfer"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
                +AccountAddress.fromString(recipient.value)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun mintSoulBoundTransaction(
  config: AptosConfig,
  account: Account,
  collection: String,
  name: String,
  description: String,
  uri: String,
  recipient: AccountAddressInput,
  propertyKeys: List<String>,
  propertyTypes: List<String>,
  propertyValues: List<String>,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  if (propertyKeys.size != propertyValues.size) {
    throw IllegalArgumentException("Property keys and values must be the same size")
  }

  if (propertyTypes.size != propertyValues.size) {
    throw IllegalArgumentException("Property types and values must be the same size")
  }

  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = account.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::mint_soul_bound"
              functionArguments = functionArguments {
                +MoveString(collection)
                +MoveString(description)
                +MoveString(name)
                +MoveString(uri)
                +MoveVector.string(listOf())
                +MoveVector.string(listOf())
                +MoveVector.string(listOf())
                +AccountAddress.fromString(recipient.value)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun burnDigitalAssetTransaction(
  config: AptosConfig,
  creator: Account,
  digitalAssetAddress: AccountAddressInput,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::burn"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun freezeDigitalAssetTransferTransaction(
  config: AptosConfig,
  creator: Account,
  digitalAssetAddress: AccountAddressInput,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::freeze_transfer"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun unfreezeDigitalAssetTransferTransaction(
  config: AptosConfig,
  creator: Account,
  digitalAssetAddress: AccountAddressInput,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::unfreeze_transfer"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

suspend fun setDigitalAssetDescriptionTransaction(
  config: AptosConfig,
  creator: Account,
  digitalAssetAddress: AccountAddressInput,
  description: String,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(description.length <= 2048) { "Description must be less than 2048 characters" }
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::set_description"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
                +MoveString(description)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

suspend fun setDigitalAssetNameTransaction(
  config: AptosConfig,
  creator: Account,
  digitalAssetAddress: AccountAddressInput,
  name: String,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(name.length <= 512) { "Name must be less than 512 characters" }
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::set_name"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
                +MoveString(name)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

suspend fun setDigitalAssetURITransaction(
  config: AptosConfig,
  creator: Account,
  digitalAssetAddress: AccountAddressInput,
  uri: String,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(uri.length <= 512) { "URI must be less than 512 characters" }
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::set_uri"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
                +MoveString(uri)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun addDigitalAssetPropertyTransaction(
  config: AptosConfig,
  creator: Account,
  propertyKey: String,
  propertyType: PropertyType,
  propertyValue: PropertyValue,
  digitalAssetAddress: AccountAddressInput,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {

  require(propertyKey.isNotBlank()) { "Property key must not be blank" }
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::add_property"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
                +MoveString(propertyKey)
                +MoveString(propertyType.toString())
                +MoveVector.u8(getSinglePropertyValueRaw(propertyValue, propertyType.toString()))
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

internal suspend fun removeDigitalAssetPropertyTransaction(
  config: AptosConfig,
  creator: Account,
  propertyKey: String,
  digitalAssetAddress: AccountAddressInput,
  digitalAssetType: MoveStructId,
  options: InputGenerateTransactionOptions,
): SimpleTransaction {
  require(propertyKey.isNotBlank()) { "Property key must not be blank" }
  require(digitalAssetType.isNotBlank()) { "Digital asset type must not be blank" }
  val moveStructParts = digitalAssetType.split("::")
  val txn =
    generateTransaction(
      aptosConfig = config,
      data =
        InputGenerateSingleSignerRawTransactionData(
          sender = creator.accountAddress,
          data =
            entryFunctionData {
              function = "0x4::aptos_token::remove_property"
              typeArguments = typeArguments {
                +TypeTagStruct(
                  type =
                    StructTag(
                      moveStructParts.first().asAccountAddress(),
                      moveStructParts[1],
                      moveStructParts.last(),
                      emptyList(),
                    )
                )
              }
              functionArguments = functionArguments {
                +AccountAddress.fromString(digitalAssetAddress.value)
                +MoveString(propertyKey)
              }
            },
          options = options,
          withFeePayer = false,
          secondarySignerAddresses = null,
        ),
    )

  return txn as SimpleTransaction
}

private fun getSinglePropertyValueRaw(
  propertyValue: PropertyValue,
  propertyType: String,
): ByteArray {
  val typeTag = parseTypeTag(propertyType)

  if (typeTag.isStruct()) {
    if ((typeTag as TypeTagStruct).isString()) {
      return Bcs.encodeToByteArray(MoveString(propertyValue.toString()))
    }
  }

  throw Exception("Property value type not supported: $propertyType")
}

internal suspend fun getCurrentDigitalAssetOwnership(
  config: AptosConfig,
  digitalAssetAddress: AccountAddressInput,
): Result<GetCurrentTokenOwnershipQuery.Data?, AptosIndexerError> =
  handleQuery {
      val filter = currentTokenOwnershipsV2Filter {
        tokenDataId = stringFilter { eq = digitalAssetAddress.value }
        amount = numericFilter { gt = 0 }
      }
      getGraphqlClient(config).query(GetCurrentTokenOwnershipQuery(where_condition = filter))
    }
    .toResult()

internal suspend fun getEvents(
  config: AptosConfig,
  filter: EventFilter?,
  page: PaginationArgs?,
  sortOrder: List<EventSortOrder>?,
): Result<GetEventsQuery.Data?, AptosIndexerError> =
  handleQuery {
      getGraphqlClient(config)
        .query(
          GetEventsQuery(
            where_condition = filter.toOptional(),
            offset = page?.offset.toOptional(),
            limit = page?.limit.toOptional(),
            order_by = sortOrder.toOptional(),
          )
        )
    }
    .toResult()
