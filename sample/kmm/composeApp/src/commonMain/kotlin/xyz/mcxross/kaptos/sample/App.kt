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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.core.account.Account
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.sample.model.transfer
import xyz.mcxross.kaptos.sample.ui.component.BalanceRefresher
import xyz.mcxross.kaptos.sample.ui.component.Button
import xyz.mcxross.kaptos.sample.ui.component.ShortenedAddress

@Composable
fun App() {

  val aptos = remember { Aptos() }

  val accountCreated = remember { mutableStateOf(false) }

  var alice by remember { mutableStateOf<Account?>(null) }
  var aliceBalance by remember { mutableStateOf(0L) }

  var bob by remember { mutableStateOf<Account?>(null) }
  var bobBalance by remember { mutableStateOf(0L) }

  var transferAmount by remember { mutableStateOf(0) }

  val coroutineScope = rememberCoroutineScope()

  // This state is used to trigger balance updates.
  val updateTrigger = remember { mutableIntStateOf(0) }

  LaunchedEffect(key1 = updateTrigger.intValue, key2 = accountCreated) {
    if (accountCreated.value) {
      alice?.let {
        aliceBalance =
          when (val balance = aptos.getAccountAPTAmount(it.accountAddress)) {
            is Option.Some -> balance.value.div(100_000_000)
            is Option.None -> 0L
          }
      }
      bob?.let {
        bobBalance =
          when (val balance = aptos.getAccountAPTAmount(it.accountAddress)) {
            is Option.Some -> balance.value.div(100_000_000)
            is Option.None -> 0L
          }
      }
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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Text(
              "This Demo app demonstrates the use of Kaptos. We create accounts and transfer funds between them.",
              modifier = Modifier.padding(20.dp),
              fontWeight = FontWeight.ExtraLight,
              color = Color.White,
            )
            Button(
              onClick = {
                coroutineScope.launch {
                  alice = Account.generate()
                  alice?.accountAddress?.let { aptos.fundAccount(it, 1_000_000_000) }
                  bob = Account.generate()
                  bob?.accountAddress?.let { aptos.fundAccount(it, 2_000_000_000) }
                  accountCreated.value = true
                }
              },
              text = "Create Accounts",
              tint = Color.White,
            )
          }
        }
      } else {
        Column(modifier = Modifier.padding(10.dp)) {
          Row {
            Text("Alice:   ", fontSize = 16.sp, color = Color.White)
            alice?.let { ShortenedAddress(it.accountAddress.toString()) }
          }

          Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
              "$aliceBalance",
              modifier = Modifier.align(Alignment.Center),
              fontSize = 56.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
            )
          }

          Divider(modifier = Modifier.padding(10.dp), color = Color.DarkGray)

          Row {
            Text("Bob:   ", fontSize = 16.sp, color = Color.White)
            bob?.let { ShortenedAddress(it.accountAddress.toString()) }
          }

          Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
              "$bobBalance",
              modifier = Modifier.align(Alignment.Center),
              fontSize = 56.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
            )
          }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Button(
              {
                coroutineScope.launch {
                  alice?.let { aptos.fundAccount(it.accountAddress, 1_000_000_000) }
                }
              },
              "Fund Alice",
              Color.White,
            )

            Button(
              {
                coroutineScope.launch {
                  bob?.let { aptos.fundAccount(it.accountAddress, 2_000_000_000) }
                }
              },
              "Fund Bob",
              Color.White,
            )

            Divider(modifier = Modifier.padding(10.dp), color = Color.DarkGray)

            TextField(
              value = transferAmount.toString(),
              onValueChange = { transferAmount = it.toIntOrNull() ?: 0 },
              textStyle = MaterialTheme.typography.body2,
              colors =
                TextFieldDefaults.textFieldColors(
                  textColor = Color.White,
                  backgroundColor = Color.DarkGray,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
                ),
              label = { Text("Transfer Amount", color = Color.White) },
              keyboardOptions =
                KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
              modifier = Modifier.padding(10.dp),
            )

            Button(
              onClick = {
                coroutineScope.launch {
                  alice?.let {
                    bob?.let { bobAccount ->
                      transfer(aptos, it, bobAccount, transferAmount)
                      updateTrigger.value++
                    }
                  }
                }
              },
              text = "Transfer",
              tint = Color.White,
            )
          }
        }
      }
    }
  }
}
