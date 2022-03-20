package com.playground.android.githubclient.data

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

  @GET("search/repositories?sort=stars")
  suspend fun search(@Query("q") query: String): SearchResponseDTO
}

const val QUERY_PARAM_TOPIC = "topic:"
