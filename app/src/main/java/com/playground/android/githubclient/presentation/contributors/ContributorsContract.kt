package com.playground.android.githubclient.presentation.contributors

import com.playground.android.githubclient.core.ViewEvent
import com.playground.android.githubclient.core.ViewState
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.RepositoryCoordinate

data class LoadContributorsViewEvent(val repoCoordinate: RepositoryCoordinate) : ViewEvent

sealed class ContributorsViewState : ViewState {
  object Loading : ContributorsViewState()
  object Error : ContributorsViewState()
  data class Data(val contributors: List<Contributor>) : ContributorsViewState()
}
