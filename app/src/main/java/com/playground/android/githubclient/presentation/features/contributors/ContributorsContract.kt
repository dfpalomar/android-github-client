package com.playground.android.githubclient.presentation.features.contributors

import com.playground.android.githubclient.presentation.core.ViewEvent
import com.playground.android.githubclient.presentation.core.ViewState
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.RepositoryCoordinate

data class LoadContributorsViewEvent(val repoCoordinate: RepositoryCoordinate) : ViewEvent

sealed class ContributorsViewState : ViewState {
  object Loading : ContributorsViewState()
  object Error : ContributorsViewState()
  data class Data(val contributors: List<Contributor>) : ContributorsViewState()
}
