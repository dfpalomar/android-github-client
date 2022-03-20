package com.playground.android.githubclient.domain.model

data class Repository(
  val name: String,
  val owner: String,
  val ownerAvatarUrl: String,
  val stars: Int,
  val forks: Int,
  val license: String?
) {
  val coordinate = RepositoryCoordinate(owner = owner, name = name)
}
