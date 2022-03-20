package com.playground.android.githubclient.data

import com.playground.android.githubclient.domain.Repository

data class SearchResponseDTO(
  val items: List<RepositoryDTO>
)

data class RepositoryDTO(
  val full_name: String,
  val owner: OwnerDTO,
  val stargazers_count: Int,
  val forks_count: Int,
  val license: LicenseDTO?
)

data class OwnerDTO(
  val login: String,
  val avatar_url: String
)

data class LicenseDTO(
  val spdx_id: String
)

fun SearchResponseDTO.toRepositoryList() = items.map {
  Repository(
    fullName = it.full_name,
    stars = it.stargazers_count,
    forks = it.forks_count,
    license = it.license?.spdx_id,
    ownerAvatarUrl = it.owner.avatar_url
  )
}

