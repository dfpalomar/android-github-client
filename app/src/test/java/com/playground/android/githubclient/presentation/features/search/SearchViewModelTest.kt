package com.playground.android.githubclient.presentation.features.search

import app.cash.turbine.test
import com.playground.android.githubclient.CoroutinesTestRule
import com.playground.android.githubclient.data.GithubRepository
import com.playground.android.githubclient.test.shared.TestRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class SearchViewModelTest {

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  @MockK
  private lateinit var githubRepository: GithubRepository

  private lateinit var searchViewModel: SearchViewModel

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
    searchViewModel = SearchViewModel(githubRepository)
  }

  @Test
  fun `Set Idle as initial state`() = runTest {
    searchViewModel.viewState.test {
      assertEquals(SearchViewState.Idle, awaitItem())
    }
  }

  @Test
  fun `When search produces result then update state with repository list`() = runTest {

    val searchTerm = "android"
    val searchResult = listOf(TestRepository.create())
    val loadingViewState = SearchViewState(
      repositoryList = emptyList(),
      searchTerm = searchTerm,
      isLoading = true,
      errorFound = false
    )
    val searchResultViewState = SearchViewState(
      repositoryList = searchResult,
      searchTerm = searchTerm,
      isLoading = false,
      errorFound = false
    )

    every {
      githubRepository.search(searchTerm)
    } returns flowOf(Result.success(searchResult))

    searchViewModel.setEvent(SearchTermChangedViewEvent(searchTerm))

    searchViewModel.viewState.test {
      assertEquals(loadingViewState, awaitItem())
      assertEquals(searchResultViewState, awaitItem())
    }
  }

  @Test
  fun `When search fails then update state with error`() = runTest {

    val searchTerm = "android"
    val loadingViewState = SearchViewState(
      repositoryList = emptyList(),
      searchTerm = searchTerm,
      isLoading = true,
      errorFound = false
    )
    val searchResultViewState = SearchViewState(
      repositoryList = emptyList(),
      searchTerm = searchTerm,
      isLoading = false,
      errorFound = true
    )

    every {
      githubRepository.search(searchTerm)
    } returns flowOf(Result.failure(Exception()))

    searchViewModel.setEvent(SearchTermChangedViewEvent(searchTerm))

    searchViewModel.viewState.test {
      assertEquals(loadingViewState, awaitItem())
      assertEquals(searchResultViewState, awaitItem())
    }
  }
}
