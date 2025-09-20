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

package xyz.mcxross.kaptos

import xyz.mcxross.kaptos.core.Application
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.*

/**
 * [Aptos] is the main entry point to the SDK's APIs. Instantiate to access all functionalities.
 *
 * @param config [AptosConfig] to optionally configure the SDK.
 */
class Aptos(
  override val config: AptosConfig = AptosConfig(),
  graceFull: Boolean = Application.graceFull,
) :
  Account by xyz.mcxross.kaptos.api.Account(config),
  Coin by xyz.mcxross.kaptos.api.Coin(config),
  General by xyz.mcxross.kaptos.api.General(config),
  Transaction by xyz.mcxross.kaptos.api.Transaction(config),
  Table by xyz.mcxross.kaptos.api.Table(config),
  Object by xyz.mcxross.kaptos.api.Object(config),
  Staking by xyz.mcxross.kaptos.api.Staking(config),
  DigitalAsset by xyz.mcxross.kaptos.api.DigitalAsset(config),
  Faucet by xyz.mcxross.kaptos.api.Faucet(config),
  FungibleAsset by xyz.mcxross.kaptos.api.FungibleAsset(config),
  Ans by xyz.mcxross.kaptos.api.Ans(config) {

  init {
    Application.graceFull = graceFull
  }

  /**
   * Returns the current graceFull setting.
   *
   * @return [Boolean] indicating if the SDK is in graceFull mode.
   */
  fun isGraceFull(): Boolean {
    return Application.graceFull
  }
}
