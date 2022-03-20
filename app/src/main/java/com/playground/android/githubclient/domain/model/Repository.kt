package com.playground.android.githubclient.domain.model

data class Repository(
  val fullName: String,
  val stars: Int,
  val forks: Int,
  val license: String?,
  val ownerAvatarUrl: String
) {
  val owner: String
    get() = fullName.split("/")[0]

  val name: String
    get() = fullName.split("/")[1]
}
