package com.playground.android.githubclient.test.shared

import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.RepositoryCoordinate

object TestContributor {

  const val USER_NAME = "JakeWharton"
  const val AVATAR_URL = "https://avatars.githubusercontent.com/u/66577?v=4"
  const val ACCOUNT_URL = "https://github.com/JakeWharton"

  fun create(
    user: String = USER_NAME,
    avatarUrl: String = AVATAR_URL,
    accountUrl: String = ACCOUNT_URL
  ) = Contributor(user = user, avatarUrl = avatarUrl, accountUrl = accountUrl)
}

object TestRepositoryCoordinate {

  const val OWNER = "square"
  const val NAME = "retrofit"

  fun create(
    owner: String = OWNER,
    name: String = NAME
  ) = RepositoryCoordinate(owner = owner, name = name)
}
