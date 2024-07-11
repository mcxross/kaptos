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
package xyz.mcxross.kaptos.sample.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Button(
  onClick: () -> Unit,
  text: String,
  tint: Color,
  borderColor: Color = MaterialTheme.colors.secondaryVariant,
) =
  Button(
    onClick = onClick,
    modifier = Modifier.height(65.dp).fillMaxWidth().padding(horizontal = 6.dp, vertical = 4.dp),
    shape = RoundedCornerShape(15.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = tint),
    border = BorderStroke(1.dp, borderColor),
    elevation = ButtonDefaults.elevation(0.dp),
  ) {
    Text(text = text)
  }
