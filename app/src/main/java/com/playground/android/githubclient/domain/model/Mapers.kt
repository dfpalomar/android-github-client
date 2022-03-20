package com.playground.android.githubclient.domain.model

import com.playground.android.githubclient.data.ContributorDTO
import com.playground.android.githubclient.data.SearchResponseDTO

fun SearchResponseDTO.toRepositoryList() = items.map {
  Repository(
    name = it.name,
    owner = it.owner.login,
    stars = it.stargazers_count,
    forks = it.forks_count,
    license = it.license?.spdx_id,
    ownerAvatarUrl = it.owner.avatar_url
  )
}

fun ContributorDTO.toContributor() = Contributor(
  user = login,
  avatarUrl = avatar_url,
  accountUrl = html_url
)
