package com.playground.android.githubclient.presentation.features.contributors

import app.cash.turbine.test
import com.playground.android.githubclient.CoroutinesTestRule
import com.playground.android.githubclient.data.GithubRepository
import com.playground.android.githubclient.test.shared.TestContributor
import com.playground.android.githubclient.test.shared.TestRepositoryCoordinate
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContributorsViewModelTest {

  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  @MockK
  private lateinit var githubRepository: GithubRepository

  private lateinit var contributorsViewModel: ContributorsViewModel

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
    contributorsViewModel = ContributorsViewModel(githubRepository)
  }

  @Test
  fun `Set Loading as initial ViewState`() = runBlocking {
    contributorsViewModel.viewState.test {
      assertEquals(ContributorsViewState.Loading, awaitItem())
    }
  }

  @Test
  fun `When contributors list available then set Data as ViewState`() = runBlocking {

    val repositoryCoordinate = TestRepositoryCoordinate.create()
    val contributorsList = listOf(TestContributor.create())

    every {
      githubRepository.contributorsList(repositoryCoordinate)
    } returns flowOf(Result.success(contributorsList))

    contributorsViewModel.setEvent(
      LoadContributorsViewEvent(repositoryCoordinate)
    )

    val expectedViewState = ContributorsViewState.Data(contributorsList)

    contributorsViewModel.viewState.test {
      assertEquals(expectedViewState, awaitItem())
    }
  }

  @Test
  fun `When error fetching contributors list  then set Error as ViewState`() = runBlocking {

    val repositoryCoordinate = TestRepositoryCoordinate.create()

    every {
      githubRepository.contributorsList(repositoryCoordinate)
    } returns flowOf(Result.failure(Exception()))

    contributorsViewModel.setEvent(
      LoadContributorsViewEvent(repositoryCoordinate)
    )

    val expectedViewState = ContributorsViewState.Error

    contributorsViewModel.viewState.test {
      assertEquals(expectedViewState, awaitItem())
    }
  }
}
