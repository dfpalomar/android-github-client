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

fun SearchResponseDTO.toRepositoryList() = items.map {
  Repository(
    id = it.id,
    name = it.name,
    owner = it.owner.login,
    stars = it.stargazers_count,
    forks = it.forks_count,
    license = it.license?.spdx_id,
    ownerAvatarUrl = it.owner.avatar_url,
    topics = it.topics
  )
}

fun ContributorDTO.toContributor() = Contributor(
  user = login,
  avatarUrl = avatar_url,
  accountUrl = html_url
)
