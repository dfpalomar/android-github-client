package com.playground.android.githubclient.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
  modifier: Modifier = Modifier,
  errorMessage: String
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = Icons.Default.MoreVert,
      tint = MaterialTheme.colors.error,
      contentDescription = null
    )
    Spacer(Modifier.padding(top = 8.dp))
    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = errorMessage,
      style = MaterialTheme.typography.caption
    )
  }
}
