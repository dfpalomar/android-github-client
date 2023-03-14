package com.playground.android.githubclient.domain.model

import androidx.room.TypeConverters
import com.playground.android.githubclient.data.Converter

@TypeConverters(Converter::class)
data class Repository(
  val id: Long,
  val name: String,
  val owner: String,
  val ownerAvatarUrl: String,
  val stars: Int,
  val forks: Int,
  val license: String?,
  val topics: List<String>
)

val Repository.coordinate: RepositoryCoordinate
  get() = RepositoryCoordinate(owner = owner, name = name)
