package com.playground.android.githubclient.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

  @GET("search/repositories?sort=stars")
  suspend fun search(@Query("q") query: String): SearchResponseDTO

  @GET("repos/{owner}/{repo}/contributors")
  suspend fun contributors(
    @Path("owner") owner: String,
    @Path("repo") repository: String
  ): List<ContributorDTO>
}
