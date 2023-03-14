package com.playground.android.githubclient.presentation.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
          message = stringResource(string.search_loading_error),
        )
      } else {
        if (viewState.repositoryList.isEmpty() && viewState.searchTerm.isNotEmpty()) {
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
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 24.dp),
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
  // TODO
}
