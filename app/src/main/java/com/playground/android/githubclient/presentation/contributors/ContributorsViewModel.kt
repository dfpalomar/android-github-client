package com.playground.android.githubclient.presentation.contributors

import androidx.lifecycle.viewModelScope
import com.playground.android.githubclient.core.BaseViewModel
import com.playground.android.githubclient.domain.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class ContributorsViewModel @Inject constructor(
  private val githubRepository: GithubRepository
) : BaseViewModel<LoadContributorsViewEvent, ContributorsViewState>() {

  override fun setInitialState() = ContributorsViewState.Loading

  override fun handleEvents(event: LoadContributorsViewEvent) {
    loadContributors(event)
  }

  private fun loadContributors(event: LoadContributorsViewEvent) {
    viewModelScope.launch {
      githubRepository.listContributors(event.repoCoordinate).fold(
        onSuccess = { contributorsList ->
          setState { ContributorsViewState.Data(contributorsList) }
        },
        onFailure = {
          setState { ContributorsViewState.Error }
        }
      )
    }
  }
}
