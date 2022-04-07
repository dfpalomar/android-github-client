package com.playground.android.githubclient.test.shared

import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
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

object TestRepository {

  const val ID = 5152285L
  const val NAME = "retrofit"
  const val OWNER = "square"
  const val OWNER_AVATAR_URL = "https://avatars.githubusercontent.com/u/82592?v=4"
  const val STARS = 41906
  const val FORKS = 8823
  const val LICENSEE = "Apache-2.0"
  val TOPICS = listOf("android", "java", "kotlin")

  fun create(
    id: Long = ID,
    name: String = NAME,
    owner: String = OWNER,
    ownerAvatarUrl: String = OWNER_AVATAR_URL,
    stars: Int = STARS,
    forks: Int = FORKS,
    license: String = LICENSEE,
    topics: List<String> = TOPICS
  ) = Repository(
    id = id,
    name = name,
    owner = owner,
    ownerAvatarUrl = ownerAvatarUrl,
    stars = stars,
    forks = forks,
    license = license,
    topics = topics
  )
}