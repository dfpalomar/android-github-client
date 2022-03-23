package com.playground.android.githubclient.presentation.features.contributors

import androidx.lifecycle.viewModelScope
import com.playground.android.githubclient.data.GithubRepository
import com.playground.android.githubclient.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
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
      githubRepository.contributorsList(event.repoCoordinate)
        .collect { result ->
          result.fold(
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
}
