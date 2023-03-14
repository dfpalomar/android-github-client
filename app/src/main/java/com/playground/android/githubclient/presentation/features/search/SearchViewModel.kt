package com.playground.android.githubclient.presentation.features.search

import com.playground.android.githubclient.data.GithubRepository
import com.playground.android.githubclient.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val githubRepository: GithubRepository
) : BaseViewModel<SearchTermChangedViewEvent, SearchViewState>() {

  override fun setInitialState() = SearchViewState.Idle

  override fun handleEvents(event: SearchTermChangedViewEvent) {
    // TODO
  }
}
