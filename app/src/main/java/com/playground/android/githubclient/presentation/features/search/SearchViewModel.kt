package com.playground.android.githubclient.presentation.features.search

import androidx.lifecycle.viewModelScope
import com.playground.android.githubclient.data.GithubRepository
import com.playground.android.githubclient.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
  private val githubRepository: GithubRepository
) : BaseViewModel<SearchTermChangedViewEvent, SearchViewState>() {

  override fun setInitialState() = SearchViewState.Idle

  private val searchTermFlow = MutableStateFlow("")

  init {
    observeSearchRequest()
  }

  @OptIn(FlowPreview::class)
  private fun observeSearchRequest() {
    searchTermFlow
      .drop(1)
      .onEach { setState { copy(searchTerm = it, errorFound = false) } }
      .debounce(500)
      .onEach { setState { copy(isLoading = true) } }
      .map { searchTerm -> githubRepository.search(searchTerm) }
      .onEach { searchResult ->
        searchResult.fold(
          onSuccess = { repositoryList ->
            setState {
              copy(
                repositoryList = repositoryList,
                isLoading = false,
                errorFound = false
              )
            }
          },
          onFailure = {
            setState {
              copy(
                repositoryList = emptyList(),
                isLoading = false,
                errorFound = true
              )
            }
          }
        )
      }
      .flowOn(Dispatchers.Default)
      .launchIn(viewModelScope)
  }

  override fun handleEvents(event: SearchTermChangedViewEvent) {
    viewModelScope.launch {
      setState { copy(searchTerm = event.term, errorFound = false) }
      searchTermFlow.emit(event.term)
    }
  }
}
