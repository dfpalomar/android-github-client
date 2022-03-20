package com.playground.android.githubclient.presentation.search

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.playground.android.githubclient.R
import com.playground.android.githubclient.R.string
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import com.playground.android.githubclient.presentation.common.ErrorMessage
import com.playground.android.githubclient.presentation.common.IndeterminateProgressIndicator
import com.playground.android.githubclient.presentation.util.rememberFlowWithLifecycle

@Composable
fun SearchScreen(
  showContributors: (RepositoryCoordinate) -> Unit
) {
  val viewModel: SearchViewModel = hiltViewModel()

  val searchViewState: SearchViewState by rememberFlowWithLifecycle(
    viewModel.viewState
  ).collectAsState(initial = SearchViewState.Idle)

  Search(
    searchViewState,
    searchTerm = { viewModel.setEvent(SearchTermChangedViewEvent(it)) },
    showContributors
  )
}

@Composable
fun Search(
  viewState: SearchViewState,
  searchTerm: (String) -> Unit,
  showContributors: (RepositoryCoordinate) -> Unit
) {
  Column(
    Modifier
      .fillMaxSize()
  ) {
    SearchBar(
      value = viewState.searchTerm,
      onTextUpdate = searchTerm
    )
    if (viewState.isLoading) {
      IndeterminateProgressIndicator()
    } else {
      if (viewState.errorFound) {
        ErrorMessage(
          modifier = Modifier.fillMaxSize(),
          message = "There was an error",
        )
      } else {
        if (viewState != SearchViewState.Idle && viewState.repositoryList.isEmpty()) {
          NoResults(viewState.searchTerm)
        } else {
          SearchResultsList(viewState.repositoryList, showContributors)
        }
      }
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(
  modifier: Modifier = Modifier,
  value: String,
  onTextUpdate: (String) -> Unit
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  Surface(
    modifier = Modifier.fillMaxWidth(),
    elevation = 4.dp
  ) {
    TextField(
      value = value,
      modifier = modifier.fillMaxWidth(),
      onValueChange = onTextUpdate,
      leadingIcon = { Icon(Filled.Search, contentDescription = null) },
      trailingIcon = {
        if (value.isNotBlank()) {
          IconButton(onClick = { onTextUpdate("") }) {
            Icon(Filled.Close, contentDescription = null)
          }
        }
      },
      singleLine = true,
      placeholder = { Text(stringResource(string.search_hint)) },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
      textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
      colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent
      )
    )
  }
}

@Composable
private fun NoResults(searchTerm: String) {
  Text(
    modifier = Modifier.fillMaxSize(),
    text = "No results found for \"$searchTerm\"",
    style = MaterialTheme.typography.body1,
    textAlign = TextAlign.Center
  )
}

@Composable
private fun SearchResultsList(
  repositories: List<Repository>,
  showContributors: (RepositoryCoordinate) -> Unit
) {
  LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
    itemsIndexed(repositories) { _, repository ->
      SearchResultListItem(repository, showContributors)
    }
  }
}

@Composable
private fun SearchResultListItem(
  repository: Repository,
  showContributors: (RepositoryCoordinate) -> Unit
) {
  Box(modifier = Modifier
    .fillMaxWidth()
    .background(MaterialTheme.colors.surface)
    .clickable { showContributors(repository.coordinate) }
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = rememberImagePainter(
          data = repository.ownerAvatarUrl,
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
      Column(
        Modifier
          .fillMaxWidth()
          .padding(start = 16.dp)
      ) {
        Text(
          text = repository.name,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = repository.owner,
          style = MaterialTheme.typography.caption
        )
        Row(
          Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null
          )
          Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "${repository.stars}"
          )
          Icon(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(R.drawable.ic_fork),
            contentDescription = null
          )
          Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "${repository.forks}"
          )
          repository.license?.let { license ->
            Icon(
              modifier = Modifier.padding(start = 8.dp),
              imageVector = Icons.Default.Policy,
              contentDescription = null
            )
            Text(
              modifier = Modifier.padding(start = 8.dp),
              text = license,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }
    }
  }
}

@Preview
@Composable
private fun PreviewSearchResultListItem() {
  SearchResultListItem(
    Repository(
      name = "Retrofit",
      owner = "Square",
      ownerAvatarUrl = "",
      stars = 45000,
      forks = 899,
      license = "MIT"
    )
  ) {}
}