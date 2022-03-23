package com.playground.android.githubclient.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.playground.android.githubclient.presentation.util.TAG_PROGRESS_INDICATOR

@Composable
fun IndeterminateProgressIndicator(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier.fillMaxSize().testTag(TAG_PROGRESS_INDICATOR),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(color = Color.Gray)
  }
}
