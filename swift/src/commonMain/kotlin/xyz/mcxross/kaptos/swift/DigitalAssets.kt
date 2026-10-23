/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.swift

import xyz.mcxross.kaptos.digitalasset.CollectionRecord
import xyz.mcxross.kaptos.digitalasset.DigitalAssetActivity
import xyz.mcxross.kaptos.digitalasset.DigitalAssetCollectionOptions
import xyz.mcxross.kaptos.digitalasset.DigitalAssetOwnership
import xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty
import xyz.mcxross.kaptos.digitalasset.DigitalAssetService
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.StructTag
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.UnsignedTransaction

/** Concrete throwing operations for Swift; models and business logic remain in the SDK. */
class DigitalAssets internal constructor(private val service: DigitalAssetService) {
  @Throws(Throwable::class)
  suspend fun getCollection(collectionId: AccountAddressInput): CollectionRecord? =
    service.getCollection(collectionId = collectionId).swiftValue()

  @Throws(Throwable::class)
  suspend fun getCollection(
    creator: AccountAddressInput,
    name: String,
  ): CollectionRecord? = service.getCollection(creator = creator, name = name).swiftValue()

  @Throws(Throwable::class)
  suspend fun getOwned(
    owner: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosPage<DigitalAssetOwnership> = service.getOwned(owner = owner, page = page).swiftValue()

  @Throws(Throwable::class)
  suspend fun getActivity(
    assetId: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosPage<DigitalAssetActivity> =
    service.getActivity(assetId = assetId, page = page).swiftValue()

  @Throws(Throwable::class)
  suspend fun buildCreateCollection(
    sender: AccountAddressInput,
    name: String,
    description: String = "",
    uri: String = "",
    collection: DigitalAssetCollectionOptions = DigitalAssetCollectionOptions(),
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildCreateCollection(
        sender = sender,
        name = name,
        description = description,
        uri = uri,
        collection = collection,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildMint(
    sender: AccountAddressInput,
    collection: String,
    name: String,
    description: String = "",
    uri: String = "",
    properties: List<DigitalAssetProperty> = emptyList(),
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildMint(
        sender = sender,
        collection = collection,
        name = name,
        description = description,
        uri = uri,
        properties = properties,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildMintSoulbound(
    sender: AccountAddressInput,
    recipient: AccountAddressInput,
    collection: String,
    name: String,
    description: String = "",
    uri: String = "",
    properties: List<DigitalAssetProperty> = emptyList(),
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildMintSoulbound(
        sender = sender,
        recipient = recipient,
        collection = collection,
        name = name,
        description = description,
        uri = uri,
        properties = properties,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    recipient: AccountAddressInput,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildTransfer(
        sender = sender,
        assetId = assetId,
        recipient = recipient,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildBurn(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildBurn(sender = sender, assetId = assetId, assetType = assetType, options = options)
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildFreezeTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildFreezeTransfer(
        sender = sender,
        assetId = assetId,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildUnfreezeTransfer(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildUnfreezeTransfer(
        sender = sender,
        assetId = assetId,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildSetName(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    name: String,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildSetName(
        sender = sender,
        assetId = assetId,
        name = name,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildSetDescription(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    description: String,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildSetDescription(
        sender = sender,
        assetId = assetId,
        description = description,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildSetUri(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    uri: String,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildSetUri(
        sender = sender,
        assetId = assetId,
        uri = uri,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildAddProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildAddProperty(
        sender = sender,
        assetId = assetId,
        property = property,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildUpdateProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    property: DigitalAssetProperty,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildUpdateProperty(
        sender = sender,
        assetId = assetId,
        property = property,
        assetType = assetType,
        options = options,
      )
      .swiftValue()

  @Throws(Throwable::class)
  suspend fun buildRemoveProperty(
    sender: AccountAddressInput,
    assetId: AccountAddressInput,
    key: String,
    assetType: StructTag = DigitalAssetService.DEFAULT_DIGITAL_ASSET_TYPE,
    options: TransactionOptions = TransactionOptions(),
  ): UnsignedTransaction.Simple =
    service
      .buildRemoveProperty(
        sender = sender,
        assetId = assetId,
        key = key,
        assetType = assetType,
        options = options,
      )
      .swiftValue()
}
