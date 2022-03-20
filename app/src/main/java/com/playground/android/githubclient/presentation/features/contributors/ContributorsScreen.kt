package com.playground.android.githubclient.presentation.features.contributors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import com.playground.android.githubclient.presentation.common.ErrorMessage
import com.playground.android.githubclient.presentation.common.IndeterminateProgressIndicator
import com.playground.android.githubclient.presentation.features.contributors.ContributorsViewState.Data
import com.playground.android.githubclient.presentation.features.contributors.ContributorsViewState.Error
import com.playground.android.githubclient.presentation.features.contributors.ContributorsViewState.Loading
import com.playground.android.githubclient.presentation.util.rememberFlowWithLifecycle

@Composable
fun ContributorsScreen(
  navigateUp: () -> Unit,
  repositoryCoordinate: RepositoryCoordinate,
  showContributorPage: (String) -> Unit
) {
  val viewModel: ContributorsViewModel = hiltViewModel()

  val viewState: ContributorsViewState by rememberFlowWithLifecycle(
    viewModel.viewState
  ).collectAsState(initial = ContributorsViewState.Loading)

  LaunchedEffect(repositoryCoordinate) {
    viewModel.setEvent(LoadContributorsViewEvent(repositoryCoordinate))
  }
  Contributors(repositoryCoordinate.name, navigateUp, viewState, showContributorPage)
}

@Composable
private fun Contributors(
  repoName: String,
  navigateUp: () -> Unit,
  viewState: ContributorsViewState,
  showContributorPage: (String) -> Unit
) {
  Column(Modifier.fillMaxSize()) {
    ContributorsTopAppBAr(repoName, navigateUp)
    when (viewState) {
      is Loading -> IndeterminateProgressIndicator()
      is Data -> ContributorsList(viewState.contributors, showContributorPage)
      is Error -> ErrorMessage(
        modifier = Modifier.fillMaxSize(),
        message = "Error loading contributors"
      )
    }

  }
}

@Composable
fun ContributorsTopAppBAr(
  title: String,
  navigateUp: () -> Unit,
) {
  TopAppBar(
    backgroundColor = MaterialTheme.colors.background,
    contentColor = contentColorFor(MaterialTheme.colors.background),
    navigationIcon = {
      IconButton(onClick = navigateUp) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
      }
    },
    title = {
      Column(Modifier.fillMaxWidth()) {
        Text(text = title)
        Text(
          text = "Contributors",
          style = MaterialTheme.typography.caption
        )
      }
    }
  )
}

@Composable
private fun ContributorsList(
  contributors: List<Contributor>,
  showContributorPage: (String) -> Unit
) {
  LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
    itemsIndexed(contributors) { _, contributor ->
      ContributorListItem(contributor, showContributorPage)
    }
  }
}

@Composable
fun ContributorListItem(
  contributor: Contributor,
  showContributorPage: (String) -> Unit
) {
  Box(modifier = Modifier
    .fillMaxWidth()
    .background(MaterialTheme.colors.surface)
    .clickable { showContributorPage(contributor.accountUrl) }
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = rememberImagePainter(
          data = contributor.avatarUrl,
          builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
          }
        ),
        modifier = Modifier
          .height(56.dp)
          .width(56.dp),
        contentDescription = null
      )
      Text(
        modifier = Modifier.padding(start = 16.dp),
        text = contributor.user,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
