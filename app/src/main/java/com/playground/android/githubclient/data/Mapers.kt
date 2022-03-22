package com.playground.android.githubclient.data

import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository

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
