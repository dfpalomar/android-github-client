package com.playground.android.githubclient.data

import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
  private val githubApi: GithubApi,
) {

  fun search(
    searchTerm: String
  ): Flow<Result<List<Repository>>> = flow {
    val searchResult = runCatching {
      githubApi.search(searchTerm).toRepositoryList()
    }
    emit(searchResult)
  }

  fun contributorsList(
    repositoryCoordinate: RepositoryCoordinate
  ): Flow<Result<List<Contributor>>> = flow {
    val contributorsListResult = runCatching {
      githubApi.contributors(
        owner = repositoryCoordinate.owner,
        repository = repositoryCoordinate.name
      ).map { it.toContributor() }
    }
    emit(contributorsListResult)
  }
}
