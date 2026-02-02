/*
 * Copyright 2025 McXross
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

package xyz.mcxross.kaptos.e2e

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.extension.longOrNull
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse
import xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.PaginationArgs
import xyz.mcxross.kaptos.model.PendingTransactionResponse
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse
import xyz.mcxross.kaptos.model.TransactionResponse
import xyz.mcxross.kaptos.model.UserTransactionResponse
import xyz.mcxross.kaptos.model.types.currentFungibleAssetBalancesFilter
import xyz.mcxross.kaptos.model.types.fungibleAssetActivitiesFilter
import xyz.mcxross.kaptos.model.types.fungibleAssetMetadataFilter
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.util.APTOS_COIN
import xyz.mcxross.kaptos.util.APTOS_FA
import xyz.mcxross.kaptos.util.runBlocking
import xyz.mcxross.kaptos.util.toAccountAddress

class FungibleAssetTest {
  private fun ledgerVersionOf(txn: TransactionResponse): Long? =
    when (txn) {
      is UserTransactionResponse -> txn.version.toLongOrNull()
      is BlockMetadataTransactionResponse -> txn.version.toLongOrNull()
      is StateCheckpointTransactionResponse -> txn.version.toLongOrNull()
      is BlockEpilogueTransactionResponse -> txn.version.toLongOrNull()
      is PendingTransactionResponse -> null
    }

  @Test
  fun `it should fetch fungible asset metadata`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))

    val resolution =
      aptos.getFungibleAssetMetadata(
        filter = fungibleAssetMetadataFilter { assetType = stringFilter { eq = APTOS_COIN } }
      )

    when (resolution) {
      is Result.Ok -> {
        val data = resolution.value?.fungible_asset_metadata
        assertEquals(1, data?.size ?: 0, "")
        assertEquals(APTOS_COIN, data?.first()?.asset_type, "")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it should fetch a specific fungible asset metadata by an asset type`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
    when (val resolution = aptos.getFungibleAssetMetadataByAssetType(APTOS_COIN)) {
      is Result.Ok -> {
        assertEquals(APTOS_COIN, resolution.value?.asset_type, "")
      }
      is Result.Err -> fail("")
    }

    when (
      val resolution = aptos.getFungibleAssetMetadataByAssetType("0x1::aptos_coin::testnotexist")
    ) {
      is Result.Ok -> {
        assertNull(resolution.value?.asset_type)
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it should fetch a specific fungible asset metadata by a creator address`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))
    when (
      val resolution =
        aptos.getFungibleAssetMetadataByCreatorAddress(
          "0x0000000000000000000000000000000000000000000000000000000000000001".toAccountAddress()
        )
    ) {
      is Result.Ok -> {
        assertEquals(
          APTOS_FA,
          resolution.value?.fungible_asset_metadata?.first()?.asset_type ?: "",
          "",
        )
      }
      is Result.Err -> fail("")
    }

    // fetch by something that doesn't exist
    when (
      val resolution = aptos.getFungibleAssetMetadataByCreatorAddress("0xc".toAccountAddress())
    ) {
      is Result.Ok -> {
        assertEquals(emptyList(), resolution.value?.fungible_asset_metadata ?: emptyList(), "")
      }
      is Result.Err -> fail("")
    }
  }

  @Test
  fun `it should fetch fungible asset activities with correct number and asset type`() =
    runBlocking {
      val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))

      val resolution =
        aptos.getFungibleAssetActivities(
          filter = fungibleAssetActivitiesFilter { assetType = stringFilter { eq = APTOS_COIN } },
          page = PaginationArgs(limit = 2),
        )

      when (resolution) {
        is Result.Ok -> {
          val data = resolution.value?.fungible_asset_activities
          assertEquals(2, data?.size ?: 3)
          assertEquals(APTOS_COIN, data?.first()?.asset_type)
          assertEquals(APTOS_COIN, data?.last()?.asset_type)
        }
        is Result.Err -> fail("")
      }
    }

  @Test
  fun `it should fetch current fungible asset balance`() = runBlocking {
    val aptos = Aptos(AptosConfig(AptosSettings(Network.DEVNET)))
    val userAccount = Account.generate()
    val minimumLedgerVersion =
      when (val fundResponse = aptos.fundAccount(userAccount.accountAddress, 1_000)) {
        is Result.Ok -> ledgerVersionOf(fundResponse.value)
        is Result.Err -> fail("Funding failed: ${fundResponse.error.message}")
      }

    val resolution =
      aptos.getCurrentFungibleAssetBalances(
        filter =
          currentFungibleAssetBalancesFilter {
            ownerAddress = stringFilter { eq = userAccount.accountAddress.toString() }
            assetType = stringFilter { eq = "0x1::aptos_coin::AptosCoin" }
          },
        minimumLedgerVersion = minimumLedgerVersion,
      )

    when (resolution) {
      is Result.Ok -> {
        val data = resolution.value?.current_fungible_asset_balances
        assertTrue((data?.size ?: 0) >= 1, "Expected at least one APT balance row")
        val aptBalance = data?.firstOrNull { it?.asset_type == APTOS_COIN } ?: fail("")
        assertEquals(APTOS_COIN, aptBalance.asset_type)
        assertEquals(1_000L, aptBalance.amount.longOrNull())
      }
      is Result.Err -> fail("")
    }
  }
}
