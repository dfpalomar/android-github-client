package com.playground.android.githubclient.data

import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GithubRepository @Inject constructor(
  private val githubApi: GithubApi,
) {

  fun search(
    searchTerm: String
  ): Flow<Result<List<Repository>>> {
    // TODO
    return emptyFlow()
  }

  fun contributorsList(
    repositoryCoordinate: RepositoryCoordinate
  ): Flow<Result<List<Contributor>>> {
    // TODO
    return emptyFlow()
  }
}
