/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.digitalasset

import kotlin.time.Instant
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.generated.GetAccountOwnedTokensQuery
import xyz.mcxross.kaptos.generated.GetCollectionDataQuery
import xyz.mcxross.kaptos.generated.GetTokenActivityQuery
import xyz.mcxross.kaptos.generated.fragment.CurrentTokenOwnershipFields
import xyz.mcxross.kaptos.generated.fragment.TokenActivitiesFields
import xyz.mcxross.kaptos.generated.type.Current_token_ownerships_v2_order_by
import xyz.mcxross.kaptos.generated.type.Token_activities_v2_order_by
import xyz.mcxross.kaptos.internal.handleQuery
import xyz.mcxross.kaptos.internal.rethrowCancellation
import xyz.mcxross.kaptos.internal.toAptosResult
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.StructTag
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.TypeTagStruct
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.model.types.OrderBy
import xyz.mcxross.kaptos.model.types.currentCollectionsV2Filter
import xyz.mcxross.kaptos.model.types.currentTokenOwnershipsV2Filter
import xyz.mcxross.kaptos.model.types.numericFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.model.types.tokenActivitiesV2Filter
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsWriter
import xyz.mcxross.kaptos.transaction.bcs.unsignedDecimalToLittleEndian
import xyz.mcxross.kaptos.util.toOptional

/** Public collection representation independent of generated GraphQL models. */
data class CollectionRecord(
  val id: AccountAddress,
  val creator: AccountAddress,
  val name: String,
  val description: String,
  val uri: String,
  val tokenStandard: String,
  val currentSupply: ULong,
  val maxSupply: ULong?,
  val totalMinted: ULong?,
  val mutableDescription: Boolean?,
  val mutableUri: Boolean?,
  val lastTransactionVersion: ULong,
  val lastTransactionTimestamp: Instant,
)

/** Stable, generated-model-free metadata for one digital asset. */
data class DigitalAssetMetadata(
  val id: AccountAddress,
  val collectionId: AccountAddress,
  val name: String,
  val description: String,
  val uri: String,
  val tokenStandard: String,
)

/** Current indexed ownership row for a digital asset. */
data class DigitalAssetOwnership(
  val asset: DigitalAssetMetadata?,
  val assetId: AccountAddress,
  val owner: AccountAddress,
  val storageId: AccountAddress,
  val amount: ULong,
  val tokenStandard: String,
  val isSoulbound: Boolean?,
  val isFungible: Boolean?,
  val lastTransactionVersion: ULong,
  val lastTransactionTimestamp: Instant,
)

/** One indexed digital-asset activity event. */
data class DigitalAssetActivity(
  val assetId: AccountAddress,
  val type: String,
  val from: AccountAddress?,
  val to: AccountAddress?,
  val eventAccount: AccountAddress,
  val amount: ULong,
  val tokenStandard: String,
  val entryFunction: String?,
  val transactionVersion: ULong,
  val transactionTimestamp: Instant,
)

/** One typed property mutation; the value determines its on-chain property type. */
data class DigitalAssetProperty(
  val key: String,
  val value: DigitalAssetPropertyValue,
) {
  init {
    require(key.isNotBlank()) { "Digital-asset property key must not be blank" }
  }
}

/** Collection behavior used by [DigitalAssetService.buildCreateCollection]. */
data class DigitalAssetCollectionOptions(
  val maxSupply: ULong = ULong.MAX_VALUE,
  val mutableDescription: Boolean = true,
  val mutableRoyalty: Boolean = true,
  val mutableUri: Boolean = true,
  val mutableAssetDescription: Boolean = true,
  val mutableAssetName: Boolean = true,
  val mutableAssetProperties: Boolean = true,
  val mutableAssetUri: Boolean = true,
  val assetsBurnableByCreator: Boolean = true,
  val assetsFreezableByCreator: Boolean = true,
  val royaltyNumerator: ULong = 0uL,
  val royaltyDenominator: ULong = 1uL,
)

/** Move-compatible value accepted by typed digital-asset property mutations. */
sealed interface DigitalAssetPropertyValue {
  data class Bool(val value: Boolean) : DigitalAssetPropertyValue

  data class U8(val value: UByte) : DigitalAssetPropertyValue

  data class U16(val value: UShort) : DigitalAssetPropertyValue

  data class U32(val value: UInt) : DigitalAssetPropertyValue

  data class U64(val value: ULong) : DigitalAssetPropertyValue

  data class U128(val value: String) : DigitalAssetPropertyValue {
    init {
      unsignedDecimalToLittleEndian(value, 16)
    }
  }

  data class U256(val value: String) : DigitalAssetPropertyValue {
    init {
      unsignedDecimalToLittleEndian(value, 32)
    }
  }

  data class Address(val value: AccountAddress) : DigitalAssetPropertyValue

  data class Text(val value: String) : DigitalAssetPropertyValue

  class Bytes(value: ByteArray) : DigitalAssetPropertyValue {
    private val bytes = value.copyOf()

    val value: ByteArray
      get() = bytes.copyOf()

    override fun equals(other: Any?): Boolean = other is Bytes && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()
  }
}

/** Digital-asset operations exposed as `client.digitalAssets`. */
interface DigitalAssetService {
  /** Looks up a collection by object address. */
  suspend fun getCollection(collectionId: AccountAddressInput): AptosResult<CollectionRecord?>

  /** Looks up a collection by its [creator] and display [name]. */
  suspend fun getCollection(
    creator: AccountAddressInput,
    name: String,
  ): AptosResult<CollectionRecord?>

  /** Returns a typed page of digital assets currently owned by [owner]. */
  suspend fun getOwned(
    owner: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosPage<DigitalAssetOwnership>>

  /** Returns a typed page of activity for [assetId]. */
  suspend fun getActivity(
    assetId: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosPage<DigitalAssetActivity>>

  /** Builds a collection-creation transaction with explicit mutability and royalty options. */
  suspend fun buildCreateCollection(
    sender: AccountAddressInput,
    name: String,
    description: String = "",
    uri: String = "",
    collection: DigitalAssetCollectionOptions = DigitalAssetCollectionOptions(),
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a mint transaction for a standard digital asset. */
  suspend fun buildMint(
    sender: AccountAddressInput,
    collection: String,
    name: String,
    description: String = "",
    uri: String = "",
    properties: List<DigitalAssetProperty> = emptyList(),
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a mint transaction whose new asset is initially soulbound to [recipient]. */
  suspend fun buildMintSoulbound(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    collection: String,
    name: String,
    description: String = "",
    uri: String = "",
    properties: List<DigitalAssetProperty> = emptyList(),
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds an ownership transfer for [assetId]. */
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    recipient: AccountAddressInput,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a burn transaction for [assetId]. */
  suspend fun buildBurn(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a creator-authorized transfer freeze for [assetId]. */
  suspend fun buildFreezeTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a creator-authorized transfer unfreeze for [assetId]. */
  suspend fun buildUnfreezeTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a transaction that changes an asset's display name. */
  suspend fun buildSetName(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    name: String,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a transaction that changes an asset's description. */
  suspend fun buildSetDescription(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    description: String,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a transaction that changes an asset's metadata URI. */
  suspend fun buildSetUri(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    uri: String,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a typed property-add transaction. */
  suspend fun buildAddProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a typed property-update transaction. */
  suspend fun buildUpdateProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a transaction that removes property [key]. */
  suspend fun buildRemoveProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    key: String,
    assetType: StructTag = DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  companion object {
    val DEFAULT_DIGITAL_ASSET_TYPE: StructTag = StructTag.fromString("0x4::token::Token")
  }
}

internal interface DigitalAssetDataSource {
  suspend fun getCollection(
    collectionId: AccountAddress?,
    creator: AccountAddress?,
    name: String?,
  ): AptosResult<CollectionRecord?>

  suspend fun getOwned(
    owner: AccountAddress,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetOwnership>>

  suspend fun getActivity(
    assetId: AccountAddress,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetActivity>>
}

internal class DefaultDigitalAssetService(
  private val dataSource: DigitalAssetDataSource,
  private val transactions: TransactionService,
) : DigitalAssetService {
  constructor(
    config: TransportConfig,
    transactions: TransactionService,
  ) : this(DefaultDigitalAssetDataSource(config), transactions)

  override suspend fun getCollection(
    collectionId: AccountAddressInput
  ): AptosResult<CollectionRecord?> =
    safely("Invalid collection address") {
      dataSource.getCollection(AccountAddress.from(collectionId), null, null)
    }

  override suspend fun getCollection(
    creator: AccountAddressInput,
    name: String,
  ): AptosResult<CollectionRecord?> =
    safely("Invalid collection lookup") {
      require(name.isNotBlank()) { "Collection name must not be blank" }
      dataSource.getCollection(null, AccountAddress.from(creator), name)
    }

  override suspend fun getOwned(
    owner: AccountAddressInput,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetOwnership>> =
    safely("Invalid owner address") { dataSource.getOwned(AccountAddress.from(owner), page) }

  override suspend fun getActivity(
    assetId: AccountAddressInput,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetActivity>> =
    safely("Invalid digital-asset address") {
      dataSource.getActivity(AccountAddress.from(assetId), page)
    }

  override suspend fun buildCreateCollection(
    sender: AccountAddressInput,
    name: String,
    description: String,
    uri: String,
    collection: DigitalAssetCollectionOptions,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    safely("Invalid digital-asset collection") {
      require(name.isNotBlank()) { "Collection name must not be blank" }
      validateMetadata(description, uri)
      transactions.build(
        sender = sender,
        payload =
          TransactionPayload.entryFunction(
            function = "0x4::aptos_token::create_collection",
            arguments =
              listOf(
                MoveArgument.StringValue(description),
                MoveArgument.U64(collection.maxSupply),
                MoveArgument.StringValue(name),
                MoveArgument.StringValue(uri),
                MoveArgument.Bool(collection.mutableDescription),
                MoveArgument.Bool(collection.mutableRoyalty),
                MoveArgument.Bool(collection.mutableUri),
                MoveArgument.Bool(collection.mutableAssetDescription),
                MoveArgument.Bool(collection.mutableAssetName),
                MoveArgument.Bool(collection.mutableAssetProperties),
                MoveArgument.Bool(collection.mutableAssetUri),
                MoveArgument.Bool(collection.assetsBurnableByCreator),
                MoveArgument.Bool(collection.assetsFreezableByCreator),
                MoveArgument.U64(collection.royaltyNumerator),
                MoveArgument.U64(collection.royaltyDenominator),
              ),
          ),
        options = options,
      )
    }

  override suspend fun buildMint(
    sender: AccountAddressInput,
    collection: String,
    name: String,
    description: String,
    uri: String,
    properties: List<DigitalAssetProperty>,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildMintPayload(
      function = "mint",
      sender = sender,
      recipient = null,
      collection = collection,
      name = name,
      description = description,
      uri = uri,
      properties = properties,
      options = options,
    )

  override suspend fun buildMintSoulbound(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    collection: String,
    name: String,
    description: String,
    uri: String,
    properties: List<DigitalAssetProperty>,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildMintPayload(
      function = "mint_soul_bound",
      sender = sender,
      recipient = recipient,
      collection = collection,
      name = name,
      description = description,
      uri = uri,
      properties = properties,
      options = options,
    )

  override suspend fun buildTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    recipient: AccountAddressInput,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    safely("Invalid digital-asset transfer") {
      buildAssetMutation(
        function = "0x1::object::transfer",
        sender = sender,
        assetId = assetId,
        assetType = assetType,
        arguments = listOf(MoveArgument.Address(AccountAddress.from(recipient))),
        options = options,
      )
    }

  override suspend fun buildBurn(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildAssetMutation("0x4::aptos_token::burn", sender, assetId, assetType, options = options)

  override suspend fun buildFreezeTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildAssetMutation(
      "0x4::aptos_token::freeze_transfer",
      sender,
      assetId,
      assetType,
      options = options,
    )

  override suspend fun buildUnfreezeTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildAssetMutation(
      "0x4::aptos_token::unfreeze_transfer",
      sender,
      assetId,
      assetType,
      options = options,
    )

  override suspend fun buildSetName(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    name: String,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> {
    return safely("Invalid digital-asset name") {
      require(name.isNotBlank()) { "Digital-asset name must not be blank" }
      buildAssetMutation(
        "0x4::aptos_token::set_name",
        sender,
        assetId,
        assetType,
        listOf(MoveArgument.StringValue(name)),
        options,
      )
    }
  }

  override suspend fun buildSetDescription(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    description: String,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> {
    return safely("Invalid digital-asset description") {
      require(description.length <= MAX_DESCRIPTION_LENGTH) {
        "Description must be at most $MAX_DESCRIPTION_LENGTH characters"
      }
      buildAssetMutation(
        "0x4::aptos_token::set_description",
        sender,
        assetId,
        assetType,
        listOf(MoveArgument.StringValue(description)),
        options,
      )
    }
  }

  override suspend fun buildSetUri(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    uri: String,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> {
    return safely("Invalid digital-asset URI") {
      require(uri.length <= MAX_URI_LENGTH) { "URI must be at most $MAX_URI_LENGTH characters" }
      buildAssetMutation(
        "0x4::aptos_token::set_uri",
        sender,
        assetId,
        assetType,
        listOf(MoveArgument.StringValue(uri)),
        options,
      )
    }
  }

  override suspend fun buildAddProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildProperty("add_property", sender, assetId, property, assetType, options)

  override suspend fun buildUpdateProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildProperty("update_property", sender, assetId, property, assetType, options)

  override suspend fun buildRemoveProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    key: String,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    safely("Invalid digital-asset property removal") {
      require(key.isNotBlank()) { "Digital-asset property key must not be blank" }
      buildAssetMutation(
        "0x4::aptos_token::remove_property",
        sender,
        assetId,
        assetType,
        listOf(MoveArgument.StringValue(key)),
        options,
      )
    }

  private suspend fun buildMintPayload(
    function: String,
    sender: AccountAddressInput,
    recipient: AccountAddressInput?,
    collection: String,
    name: String,
    description: String,
    uri: String,
    properties: List<DigitalAssetProperty>,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    safely("Invalid digital-asset mint") {
      require(collection.isNotBlank()) { "Collection name must not be blank" }
      require(name.isNotBlank()) { "Digital-asset name must not be blank" }
      validateMetadata(description, uri)
      val arguments =
        mutableListOf<MoveArgument>(
          MoveArgument.StringValue(collection),
          MoveArgument.StringValue(description),
          MoveArgument.StringValue(name),
          MoveArgument.StringValue(uri),
          MoveArgument.Vector(properties.map { MoveArgument.StringValue(it.key) }),
          MoveArgument.Vector(properties.map { MoveArgument.StringValue(it.value.typeName()) }),
          MoveArgument.Vector(properties.map { MoveArgument.Bytes(it.value.toRawBytes()) }),
        )
      recipient?.let { arguments += MoveArgument.Address(AccountAddress.from(it)) }
      transactions.build(
        sender = sender,
        payload =
          TransactionPayload.entryFunction(
            function = "0x4::aptos_token::$function",
            arguments = arguments,
          ),
        options = options,
      )
    }

  private suspend fun buildAssetMutation(
    function: String,
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag,
    arguments: List<MoveArgument> = emptyList(),
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    safely("Invalid digital-asset mutation") {
      transactions.build(
        sender = sender,
        payload =
          TransactionPayload.entryFunction(
            function = function,
            typeArguments = listOf(TypeTagStruct(assetType)),
            arguments = listOf(MoveArgument.Address(AccountAddress.from(assetId))) + arguments,
          ),
        options = options,
      )
    }

  private fun validateMetadata(description: String, uri: String) {
    require(description.length <= MAX_DESCRIPTION_LENGTH) {
      "Description must be at most $MAX_DESCRIPTION_LENGTH characters"
    }
    require(uri.length <= MAX_URI_LENGTH) { "URI must be at most $MAX_URI_LENGTH characters" }
  }

  private suspend fun buildProperty(
    function: String,
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    safely("Invalid digital-asset property mutation") {
      transactions.build(
        sender = sender,
        payload =
          TransactionPayload.entryFunction(
            function = "0x4::aptos_token::$function",
            typeArguments = listOf(TypeTagStruct(assetType)),
            arguments =
              listOf(
                MoveArgument.Address(AccountAddress.from(assetId)),
                MoveArgument.StringValue(property.key),
                MoveArgument.StringValue(property.value.typeName()),
                MoveArgument.Bytes(property.value.toRawBytes()),
              ),
          ),
        options = options,
      )
    }

  private suspend fun <T> safely(
    message: String,
    block: suspend () -> AptosResult<T>,
  ): AptosResult<T> =
    try {
      block()
    } catch (error: Throwable) {
      error.rethrowCancellation()
      AptosResult.Failure(AptosError.Validation(message, error))
    }

  private companion object {
    const val MAX_DESCRIPTION_LENGTH = 2_048
    const val MAX_URI_LENGTH = 512
  }
}

internal class DefaultDigitalAssetDataSource(private val config: TransportConfig) :
  DigitalAssetDataSource {
  override suspend fun getCollection(
    collectionId: AccountAddress?,
    creator: AccountAddress?,
    name: String?,
  ): AptosResult<CollectionRecord?> {
    val filter = currentCollectionsV2Filter {
      collectionId?.let { this.collectionId = stringFilter { eq = it.toStringLong() } }
      creator?.let { creatorAddress = stringFilter { eq = it.toStringLong() } }
      name?.let { collectionName = stringFilter { eq = it } }
    }
    return when (
      val result = handleQuery {
        getGraphqlClient(config).query(GetCollectionDataQuery(filter, limit = 1.toOptional()))
      }
        .toAptosResult()
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        mapData(result.value?.current_collections_v2?.firstOrNull()) {
          it.toRecord()
        }
    }
  }

  override suspend fun getOwned(
    owner: AccountAddress,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetOwnership>> {
    val filter = currentTokenOwnershipsV2Filter {
      ownerAddress = stringFilter { eq = owner.toStringLong() }
      amount = numericFilter { gt = 0 }
    }
    val order =
      Current_token_ownerships_v2_order_by(last_transaction_version = OrderBy.DESC.optional())
    return when (
      val result = handleQuery {
        getGraphqlClient(config)
          .query(
            GetAccountOwnedTokensQuery(
              where_condition = filter,
              offset = page.offset.toOptional(),
              limit = page.limit.toOptional(),
              order_by = listOf(order).toOptional(),
            )
          )
      }
        .toAptosResult()
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          val data = requireNotNull(result.value) { "Indexer returned no owned-asset data" }
          AptosResult.Success(
            AptosPage(
              items =
                data.current_token_ownerships_v2.map {
                  it.currentTokenOwnershipFields.toRecord()
                },
              totalCount = data.current_token_ownerships_v2_aggregate.aggregate?.count ?: 0,
              request = page,
            )
          )
        } catch (error: Throwable) {
          error.rethrowCancellation()
          serializationFailure("Invalid owned digital-asset row", error)
        }
    }
  }

  override suspend fun getActivity(
    assetId: AccountAddress,
    page: PageRequest,
  ): AptosResult<AptosPage<DigitalAssetActivity>> {
    val filter = tokenActivitiesV2Filter {
      tokenDataId = stringFilter { eq = assetId.toStringLong() }
    }
    val order = Token_activities_v2_order_by(transaction_version = OrderBy.DESC.optional())
    return when (
      val result = handleQuery {
        getGraphqlClient(config)
          .query(
            GetTokenActivityQuery(
              where_condition = filter,
              offset = page.offset.toOptional(),
              limit = page.limit.toOptional(),
              order_by = listOf(order).toOptional(),
            )
          )
      }
        .toAptosResult()
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          val data = requireNotNull(result.value) { "Indexer returned no activity data" }
          AptosResult.Success(
            AptosPage(
              items = data.token_activities_v2.map { it.tokenActivitiesFields.toRecord() },
              totalCount = data.token_activities_v2_aggregate.aggregate?.count ?: 0,
              request = page,
            )
          )
        } catch (error: Throwable) {
          error.rethrowCancellation()
          serializationFailure("Invalid digital-asset activity row", error)
        }
    }
  }
}

private inline fun <T, R> mapData(value: T?, transform: (T) -> R): AptosResult<R?> =
  try {
    AptosResult.Success(value?.let(transform))
  } catch (error: Throwable) {
    error.rethrowCancellation()
    serializationFailure("Invalid collection row", error)
  }

private fun <T> serializationFailure(message: String, error: Throwable): AptosResult<T> =
  AptosResult.Failure(AptosError.Serialization(message, error))

internal fun GetCollectionDataQuery.Current_collections_v2.toRecord(): CollectionRecord =
  CollectionRecord(
    id = AccountAddress.fromString(collection_id),
    creator = AccountAddress.fromString(creator_address),
    name = collection_name,
    description = description,
    uri = uri,
    tokenStandard = token_standard,
    currentSupply = current_supply.requiredU64("current_supply"),
    maxSupply = max_supply.optionalU64("max_supply"),
    totalMinted = total_minted_v2.optionalU64("total_minted_v2"),
    mutableDescription = mutable_description,
    mutableUri = mutable_uri,
    lastTransactionVersion = last_transaction_version.requiredU64("last_transaction_version"),
    lastTransactionTimestamp =
      last_transaction_timestamp.requiredInstant("last_transaction_timestamp"),
  )

internal fun CurrentTokenOwnershipFields.toRecord(): DigitalAssetOwnership =
  DigitalAssetOwnership(
    asset =
      current_token_data?.let {
        DigitalAssetMetadata(
          id = AccountAddress.fromString(it.token_data_id),
          collectionId = AccountAddress.fromString(it.collection_id),
          name = it.token_name,
          description = it.description,
          uri = it.token_uri,
          tokenStandard = it.token_standard,
        )
      },
    assetId = AccountAddress.fromString(token_data_id),
    owner = AccountAddress.fromString(owner_address),
    storageId = AccountAddress.fromString(storage_id),
    amount = amount.requiredU64("amount"),
    tokenStandard = token_standard,
    isSoulbound = is_soulbound_v2,
    isFungible = is_fungible_v2,
    lastTransactionVersion = last_transaction_version.requiredU64("last_transaction_version"),
    lastTransactionTimestamp =
      last_transaction_timestamp.requiredInstant("last_transaction_timestamp"),
  )

internal fun TokenActivitiesFields.toRecord(): DigitalAssetActivity =
  DigitalAssetActivity(
    assetId = AccountAddress.fromString(token_data_id),
    type = type,
    from = from_address?.let(AccountAddress::fromString),
    to = to_address?.let(AccountAddress::fromString),
    eventAccount = AccountAddress.fromString(event_account_address),
    amount = token_amount.requiredU64("token_amount"),
    tokenStandard = token_standard,
    entryFunction = entry_function_id_str,
    transactionVersion = transaction_version.requiredU64("transaction_version"),
    transactionTimestamp = transaction_timestamp.requiredInstant("transaction_timestamp"),
  )

private fun DigitalAssetPropertyValue.typeName(): String =
  when (this) {
    is DigitalAssetPropertyValue.Bool -> "bool"
    is DigitalAssetPropertyValue.U8 -> "u8"
    is DigitalAssetPropertyValue.U16 -> "u16"
    is DigitalAssetPropertyValue.U32 -> "u32"
    is DigitalAssetPropertyValue.U64 -> "u64"
    is DigitalAssetPropertyValue.U128 -> "u128"
    is DigitalAssetPropertyValue.U256 -> "u256"
    is DigitalAssetPropertyValue.Address -> "address"
    is DigitalAssetPropertyValue.Text -> "0x1::string::String"
    is DigitalAssetPropertyValue.Bytes -> "vector<u8>"
  }

private fun DigitalAssetPropertyValue.toRawBytes(): ByteArray =
  when (this) {
    is DigitalAssetPropertyValue.Bool -> byteArrayOf(if (value) 1 else 0)
    is DigitalAssetPropertyValue.U8 -> byteArrayOf(value.toByte())
    is DigitalAssetPropertyValue.U16 -> AptosBcsWriter().also { it.u16(value) }.toByteArray()
    is DigitalAssetPropertyValue.U32 -> AptosBcsWriter().also { it.u32(value) }.toByteArray()
    is DigitalAssetPropertyValue.U64 -> AptosBcsWriter().also { it.u64(value) }.toByteArray()
    is DigitalAssetPropertyValue.U128 -> unsignedDecimalToLittleEndian(value, 16)
    is DigitalAssetPropertyValue.U256 -> unsignedDecimalToLittleEndian(value, 32)
    is DigitalAssetPropertyValue.Address -> value.data.copyOf()
    is DigitalAssetPropertyValue.Text -> AptosBcsWriter().also { it.string(value) }.toByteArray()
    is DigitalAssetPropertyValue.Bytes -> AptosBcsWriter().also { it.bytes(value) }.toByteArray()
  }

private fun Any?.optionalU64(field: String): ULong? = this?.requiredU64(field)

private fun Any.requiredU64(field: String): ULong {
  val raw = toString().trim('"')
  return raw.toULongOrNull() ?: throw IllegalArgumentException("Invalid $field: $raw")
}

private fun Any.requiredInstant(field: String): Instant {
  val raw = toString().trim('"')
  val hasOffset = raw.endsWith('Z') || OFFSET_SUFFIX.containsMatchIn(raw)
  return try {
    Instant.parse(if (hasOffset) raw else "${raw}Z")
  } catch (error: Throwable) {
    error.rethrowCancellation()
    throw IllegalArgumentException("Invalid $field: $raw", error)
  }
}

private val OFFSET_SUFFIX = Regex("[+-]\\d{2}:\\d{2}$")
