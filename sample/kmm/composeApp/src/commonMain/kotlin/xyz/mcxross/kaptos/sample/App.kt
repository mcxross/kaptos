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
package xyz.mcxross.kaptos.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.sample.model.transfer
import xyz.mcxross.kaptos.sample.ui.component.BalanceRefresher
import xyz.mcxross.kaptos.sample.ui.screen.AccountDetails
import xyz.mcxross.kaptos.sample.ui.screen.CreateAccountsScreen

@Composable
fun App() {

  val aptos = remember { Aptos() }

  val accountCreated = remember { mutableStateOf(false) }

  var alice by remember { mutableStateOf<Account?>(null) }
  var aliceBalance by remember { mutableStateOf(0L) }

  var bob by remember { mutableStateOf<Account?>(null) }
  var bobBalance by remember { mutableStateOf(0L) }

  var transferAmount by remember { mutableStateOf(0) }

  val coroutineScope by rememberUpdatedState(rememberCoroutineScope())

  // This state is used to trigger balance updates.
  val updateTrigger = remember { mutableIntStateOf(0) }

  LaunchedEffect(key1 = updateTrigger.intValue, key2 = accountCreated) {
    if (accountCreated.value) {
      aliceBalance = fetchBalance(aptos, alice)
      bobBalance = fetchBalance(aptos, bob)
    }
  }

  BalanceRefresher(updateTrigger)

  MaterialTheme(
    colors =
      MaterialTheme.colors.copy(
        primary = Color(0xFF6200EE),
        primaryVariant = Color(0xFF3700B3),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFF2B2D30),
      )
  ) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
      if (!accountCreated.value) {
        CreateAccountsScreen(
          onCreateAccounts = {
            coroutineScope.launch {
              alice = Account.generate()
              alice?.accountAddress?.let { aptos.fundAccount(it, 1_000_000_000) }
              bob = Account.generate()
              bob?.accountAddress?.let { aptos.fundAccount(it, 2_000_000_000) }
              accountCreated.value = true
            }
          }
        )
      } else {
        AccountDetails(
          alice = alice,
          aliceBalance = aliceBalance,
          bob = bob,
          bobBalance = bobBalance,
          transferAmount = transferAmount,
          onTransferAmountChange = { transferAmount = it },
          onFundAlice = {
            coroutineScope.launch {
              alice?.let { aptos.fundAccount(it.accountAddress, 1_000_000_000) }
            }
          },
          onFundBob = {
            coroutineScope.launch {
              bob?.let { aptos.fundAccount(it.accountAddress, 2_000_000_000) }
            }
          },
          onTransfer = {
            coroutineScope.launch {
              try {
                alice?.let {
                  bob?.let { bobAccount ->
                    transfer(aptos, it, bobAccount, transferAmount)
                    updateTrigger.value++
                  }
                }
              } catch (e: Exception) {
                e.printStackTrace()
              }
            }
          },
        )
      }
    }
  }
}

suspend fun fetchBalance(aptos: Aptos, account: Account?): Long {
  return account?.let {
    when (val balance = aptos.getAccountAPTAmount(it.accountAddress)) {
      is Option.Some -> balance.value.div(100_000_000)
      is Option.None -> 0L
    }
  } ?: 0L
}
