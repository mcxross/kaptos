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

import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.Account
import xyz.mcxross.kaptos.protocol.DigitalAsset
import xyz.mcxross.kaptos.protocol.General
import xyz.mcxross.kaptos.protocol.Transaction

/**
 * [Aptos] is the main entry point to the SDK's APIs. Instantiate to access all functionalities.
 *
 * @param settings [AptosConfig] to configure the SDK.
 */
class Aptos(settings: AptosConfig = AptosConfig()) :
  Account by xyz.mcxross.kaptos.api.Account(settings),
  General by xyz.mcxross.kaptos.api.General(settings),
  Transaction by xyz.mcxross.kaptos.api.Transaction(settings),
  DigitalAsset by xyz.mcxross.kaptos.api.DigitalAsset(settings)
