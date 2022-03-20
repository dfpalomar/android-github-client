package com.playground.android.githubclient.presentation.trendingrepos

import com.playground.android.githubclient.core.ViewEvent
import com.playground.android.githubclient.core.ViewState
import com.playground.android.githubclient.domain.Repository

data class SearchTopicViewEvent(val text: String) : ViewEvent

data class TrendingReposViewState(
  val repositoryList: List<Repository>,
  val searchTerm: String,
  val isLoading: Boolean,
  val errorFound: Boolean
) : ViewState {

  companion object {
    val Idle = TrendingReposViewState(
      isLoading = false,
      searchTerm = "",
      repositoryList = emptyList(),
      errorFound = false
    )
  }
}
