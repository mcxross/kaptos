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
package xyz.mcxross.kaptos.sample.ui.screen

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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.sample.ui.component.Button
import xyz.mcxross.kaptos.sample.ui.component.ShortenedAddress

@Composable
fun AccountDetails(
    alice: Account?,
    aliceBalance: Long,
    bob: Account?,
    bobBalance: Long,
    transferAmount: Int,
    onTransferAmountChange: (Int) -> Unit,
    onFundAlice: () -> Unit,
    onFundBob: () -> Unit,
    onTransfer: () -> Unit
) {
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
                onClick = onFundAlice,
                text = "Fund Alice",
                tint = Color.White,
            )

            Button(
                onClick = onFundBob,
                text = "Fund Bob",
                tint = Color.White,
            )

            Divider(modifier = Modifier.padding(10.dp), color = Color.DarkGray)

            TextField(
                value = transferAmount.toString(),
                onValueChange = { onTransferAmountChange(it.toIntOrNull() ?: 0) },
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
                KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(10.dp),
            )

            Button(
                onClick = onTransfer,
                text = "Transfer",
                tint = Color.White,
            )
        }
    }
}