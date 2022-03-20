package com.playground.android.githubclient.presentation.features.search

import com.playground.android.githubclient.presentation.core.ViewEvent
import com.playground.android.githubclient.presentation.core.ViewState
import com.playground.android.githubclient.domain.model.Repository

data class SearchTermChangedViewEvent(val term: String) : ViewEvent

data class SearchViewState(
  val repositoryList: List<Repository>,
  val searchTerm: String,
  val isLoading: Boolean,
  val errorFound: Boolean
) : ViewState {

  companion object {
    val Idle = SearchViewState(
      isLoading = false,
      searchTerm = "",
      repositoryList = emptyList(),
      errorFound = false
    )
  }
}
