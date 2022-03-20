package com.playground.android.githubclient.domain

import com.playground.android.githubclient.data.GithubApi
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import com.playground.android.githubclient.domain.model.toContributor
import com.playground.android.githubclient.domain.model.toRepositoryList
import javax.inject.Inject

const val QUERY_PARAM_TOPIC = "topic:"

class GithubRepository @Inject constructor(
  private val githubApi: GithubApi
) {

  suspend fun search(
    searchTerm: String
  ): Result<List<Repository>> = runCatching {
    githubApi.search("$QUERY_PARAM_TOPIC$searchTerm").toRepositoryList()
  }

  suspend fun listContributors(
    coordinates: RepositoryCoordinate
  ): Result<List<Contributor>> = runCatching {
    githubApi.contributors(coordinates.owner, coordinates.name).map { it.toContributor() }
  }
}
