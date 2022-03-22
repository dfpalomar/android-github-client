package com.playground.android.githubclient.data

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse.Data
import com.dropbox.android.external.store4.StoreResponse.Error.Exception
import com.dropbox.android.external.store4.StoreResponse.Error.Message
import com.dropbox.android.external.store4.StoreResponse.Loading
import com.dropbox.android.external.store4.StoreResponse.NoNewData
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class GithubRepository @Inject constructor(
  private val repositoriesListStore: Store<String, List<Repository>>,
  private val contributorsListStore: Store<RepositoryCoordinate, List<Contributor>>
) {

  fun search(
    searchTerm: String
  ): Flow<Result<List<Repository>>> = flow {
    repositoriesListStore.stream(
      StoreRequest.cached(key = searchTerm, refresh = true)
    ).collect {
      when (it) {
        is Data -> {
          println("DP: search Data: " + it.value)
          emit(Result.success(it.value))
        }
        is Exception -> {
          println("DP: search Exception: " + it.error)
          // emit(Result.failure(it.error))
        }
        is Loading -> {
          println("DP: search Loading")
        }
        is NoNewData -> {
          println("DP: search NoNewData: " + it)
        }
        is Message -> {
          println("DP: search NoNewData: " + it.message)
        }
      }
    }
  }

  fun listContributors(
    coordinates: RepositoryCoordinate
  ): Flow<Result<List<Contributor>>> = flow {
    contributorsListStore.stream(
      StoreRequest.cached(key = coordinates, refresh = true)
    ).collect {
      when (it) {
        is Data -> {
          println("DP: Data: " + it.value)
          emit(Result.success(it.value))
        }
        is Exception -> {
          println("DP: Exception: " + it.error)
          emit(Result.failure(it.error))
        }
        is Loading -> {
          println("DP: Loading")
        }
        is NoNewData -> {
          println("DP: NoNewData: " + it)
        }
        is Message -> {
          println("DP: NoNewData: " + it.message)
        }
      }
    }
  }
}
