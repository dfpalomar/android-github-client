package com.playground.android.githubclient.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
  modifier: Modifier = Modifier,
  message: String
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = Icons.Default.ErrorOutline,
      tint = MaterialTheme.colors.error,
      contentDescription = null
    )
    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = message,
      textAlign = TextAlign.Center
    )
  }
}
