package com.playground.android.githubclient.data

data class SearchResponseDTO(
  val items: List<RepositoryDTO>
)

data class RepositoryDTO(
  val name: String,
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

data class ContributorDTO(
  val login: String,
  val avatar_url: String,
  val html_url: String
)
