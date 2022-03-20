package com.playground.android.githubclient.presentation.search

import androidx.lifecycle.viewModelScope
import com.playground.android.githubclient.core.BaseViewModel
import com.playground.android.githubclient.core.ViewSideEffect
import com.playground.android.githubclient.data.GithubApi
import com.playground.android.githubclient.data.QUERY_PARAM_TOPIC
import com.playground.android.githubclient.data.toRepositoryList
import com.playground.android.githubclient.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
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
  githubApi: GithubApi
) : BaseViewModel<SearchTermChangedViewEvent, SearchViewState, ViewSideEffect>() {

  override fun setInitialState() = SearchViewState.Idle

  private val searchTermFlow = MutableStateFlow("")

  init {
    observeSearchRequest(githubApi)
  }

  @OptIn(FlowPreview::class)
  private fun observeSearchRequest(githubApi: GithubApi) {
    searchTermFlow
      .drop(1)
      .onEach { setState { copy(searchTerm = it, errorFound = false) } }
      .debounce(500)
      .onEach { setState { copy(isLoading = true) } }
      .map { searchTerm ->
        githubApi.search("$QUERY_PARAM_TOPIC$searchTerm").toRepositoryList()
      }
      .onEach { searchResult: List<Repository> ->
        setState { copy(repositoryList = searchResult, isLoading = false, errorFound = false) }
      }
      .catch {
        setState { copy(repositoryList = emptyList(), isLoading = false, errorFound = true) }
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
