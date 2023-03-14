package com.playground.android.githubclient.data

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse.Data
import com.dropbox.android.external.store4.StoreResponse.Error.Exception
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GithubRepository @Inject constructor(
  private val repositoriesListStore: Store<String, List<Repository>>,
  private val contributorsListStore: Store<RepositoryCoordinate, List<Contributor>>
) {

  fun search(
    searchTerm: String
  ): Flow<Result<List<Repository>>> = flow {
    if (searchTerm.isEmpty()) {
      emit(Result.success(emptyList()))
      return@flow
    }
    repositoriesListStore.stream(
      StoreRequest.cached(key = searchTerm, refresh = true)
    ).collect {
      when (it) {
        is Data -> emit(Result.success(it.value))
        else -> {
          // No-op
        }
      }
    }
  }

  fun contributorsList(
    repositoryCoordinate: RepositoryCoordinate
  ): Flow<Result<List<Contributor>>> = flow {
    contributorsListStore.stream(
      StoreRequest.cached(key = repositoryCoordinate, refresh = true)
    ).collect {
      when (it) {
        is Data -> emit(Result.success(it.value))
        is Exception -> emit(Result.failure(it.error))
        else -> {
          // No-op
        }
      }
    }
  }
}
