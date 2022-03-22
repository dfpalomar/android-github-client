package com.playground.android.githubclient.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.playground.android.githubclient.data.GithubApi
import com.playground.android.githubclient.data.RepositoryDAO
import com.playground.android.githubclient.data.toContributor
import com.playground.android.githubclient.data.toRepositoryList
import com.playground.android.githubclient.domain.model.Contributor
import com.playground.android.githubclient.domain.model.Repository
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flow

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {

  @Provides
  fun provideRepositoriesListStore(
    githubApi: GithubApi,
    repositoryDAO: RepositoryDAO
  ): Store<String, List<Repository>> = StoreBuilder
    .from(
      fetcher = Fetcher.ofFlow { searchTerm: String ->
        flow {
          emit(githubApi.search(searchTerm).toRepositoryList())
        }
      },
      sourceOfTruth = SourceOfTruth.of(
        reader = { searchTerm -> repositoryDAO.search(searchTerm) },
        writer = { _, list: List<Repository> ->
          list.forEach { repositoryDAO.insert(it) }
        },
        deleteAll = { repositoryDAO.deleteAll() }
      )
    )
    .build()

  @Provides
  fun provideContributorsListStore(
    githubApi: GithubApi,
  ): Store<RepositoryCoordinate, List<Contributor>> = StoreBuilder
    .from(
      fetcher = Fetcher.ofFlow { coordinates: RepositoryCoordinate ->
        flow<List<Contributor>> {
          emit(
            githubApi.contributors(coordinates.owner, coordinates.name).map { it.toContributor() }
          )
        }
      }
    )
    .build()
}
