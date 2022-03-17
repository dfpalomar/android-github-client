package com.playground.android.githubclient.presentation.trendingrepos

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.playground.android.githubclient.domain.RepositoryId

@Composable
fun TrendingReposScreen(
  openRepoDetails: (RepositoryId) -> Unit
) {
  val vm: TrendingReposViewModel = hiltViewModel()
  Button(
    modifier = Modifier.padding(top = 8.dp),
    onClick = { openRepoDetails("23") }
  ) {
    Text(
      text = "Open repo details"
    )
  }
}
