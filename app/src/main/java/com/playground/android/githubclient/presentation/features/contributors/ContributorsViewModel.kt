package com.playground.android.githubclient.presentation.features.contributors

import com.playground.android.githubclient.data.GithubRepository
import com.playground.android.githubclient.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContributorsViewModel @Inject constructor(
  private val githubRepository: GithubRepository
) : BaseViewModel<LoadContributorsViewEvent, ContributorsViewState>() {

  override fun setInitialState() = ContributorsViewState.Loading

  override fun handleEvents(event: LoadContributorsViewEvent) {
    loadContributors(event)
  }

  private fun loadContributors(event: LoadContributorsViewEvent) {
    // TODO
  }
}
