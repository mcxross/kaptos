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
import xyz.mcxross.graphql.client.types.KotlinxGraphQLResponse
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.client.indexerClient
import xyz.mcxross.kaptos.exception.AptosException
import xyz.mcxross.kaptos.extension.asAccountAddress
import xyz.mcxross.kaptos.extension.structParts
import xyz.mcxross.kaptos.generated.GetCollectionData
import xyz.mcxross.kaptos.generated.GetTokenData
import xyz.mcxross.kaptos.generated.inputs.String_comparison_exp
import xyz.mcxross.kaptos.generated.inputs.current_collections_v2_bool_exp
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.typetag.TypeTagParser.parseTypeTag

suspend fun getCollectionData(
  config: AptosConfig,
  creatorAddress: AccountAddressInput,
  collectionName: String,
  tokenStandard: TokenStandard?,
): Option<CollectionData> {
  val collectionData =
    GetCollectionData(
      GetCollectionData.Variables(
        where_condition =
          current_collections_v2_bool_exp(
            creator_address = String_comparison_exp(_eq = creatorAddress.value),
            collection_name = String_comparison_exp(_eq = collectionName),
            token_standard = tokenStandard?.let { String_comparison_exp(_eq = it.name) },
          )
      )
    )

  val response: KotlinxGraphQLResponse<CollectionData> =
    try {
      indexerClient(config).execute(collectionData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

suspend fun getCollectionDataByCollectionId(
  config: AptosConfig,
  collectionId: String,
  minimumLedgerVersion: Long?,
): Option<CollectionData> {
  val collectionData =
    GetCollectionData(
      GetCollectionData.Variables(
        where_condition =
          current_collections_v2_bool_exp(collection_id = String_comparison_exp(_eq = collectionId))
      )
    )

  val response: KotlinxGraphQLResponse<CollectionData> =
    try {
      indexerClient(config).execute(collectionData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

suspend fun getTokenData(config: AptosConfig, offset: Int?, limit: Int?): Option<TokenData> {
  val tokenData = GetTokenData(GetTokenData.Variables(offset = offset, limit = limit))

  val response: KotlinxGraphQLResponse<GetTokenData.Result> =
    try {
      indexerClient(config).execute(tokenData)
    } catch (e: Exception) {
      throw AptosException("GraphQL query execution failed: $e")
    }

  val data = response.data ?: return Option.None

  return Option.Some(data)
}

private val structTag = StructTag(AccountAddress.ONE, "string", "string", emptyList())

private val collectionAbi =
  EntryFunctionABI(
    emptyList(),
    listOf(
      TypeTagStruct(type = structTag),
      TypeTagU64(),
      TypeTagStruct(type = structTag),
      TypeTagStruct(type = structTag),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagBool(),
      TypeTagU64(),
      TypeTagU64(),
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
  val moveStructParts = digitalAssetType.structParts()
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
                      moveStructParts.first.asAccountAddress(),
                      moveStructParts.second,
                      moveStructParts.third,
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
