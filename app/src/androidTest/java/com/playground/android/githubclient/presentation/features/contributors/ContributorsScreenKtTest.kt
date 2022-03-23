package com.playground.android.githubclient.presentation.features.contributors

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.playground.android.githubclient.presentation.util.TAG_CONTRIBUTORS_SCREEN_TITLE
import com.playground.android.githubclient.presentation.util.TAG_ERROR_VIEW
import com.playground.android.githubclient.presentation.util.TAG_PROGRESS_INDICATOR
import com.playground.android.githubclient.presentation.util.TAG_TOOLBAR_BACK_BUTTON
import com.playground.android.githubclient.presentation.util.TAG_USER_AVATAR_VIEW
import com.playground.android.githubclient.test.shared.TestContributor
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ContributorsScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun Display_Repository_Name_In_The_Toolbar() {

    val repositoryName = "retrofit"

    composeTestRule.setContent {
      Contributors(
        repoName = repositoryName,
        navigateUp = {},
        viewState = ContributorsViewState.Loading,
        showContributorPage = {}
      )
    }
    composeTestRule.onNodeWithTag(TAG_CONTRIBUTORS_SCREEN_TITLE).assertTextEquals(repositoryName)
  }

  @Test
  fun When_User_Press_Toolbar_Back_Button_Then_Navigate_Up() {

    val navigateUpMock: () -> Unit = mock()

    composeTestRule.setContent {
      Contributors(
        repoName = "",
        navigateUp = navigateUpMock,
        viewState = ContributorsViewState.Loading,
        showContributorPage = {}
      )
    }
    composeTestRule.onNodeWithTag(TAG_TOOLBAR_BACK_BUTTON).performClick()

    verify(navigateUpMock).invoke()
  }

  @Test
  fun When_View_State_Is_Loading_Then_Show_Progress_Indicator() {

    composeTestRule.setContent {
      Contributors(
        repoName = "",
        navigateUp = {},
        viewState = ContributorsViewState.Loading,
        showContributorPage = {}
      )
    }
    composeTestRule.onNodeWithTag(TAG_PROGRESS_INDICATOR).assertIsDisplayed()
  }

  @Test
  fun When_View_State_Is_Error_Then_Show_Error_View() {

    composeTestRule.setContent {
      Contributors(
        repoName = "",
        navigateUp = {},
        viewState = ContributorsViewState.Error,
        showContributorPage = {}
      )
    }
    composeTestRule.onNodeWithTag(TAG_ERROR_VIEW).assertIsDisplayed()
  }

  @Test
  fun When_View_State_Is_Data_Then_Show_Contributors_List() {

    composeTestRule.setContent {
      Contributors(
        repoName = "",
        navigateUp = {},
        viewState = ContributorsViewState.Data(listOf(TestContributor.create())),
        showContributorPage = {}
      )
    }

    composeTestRule.onNodeWithTag(
      TAG_USER_AVATAR_VIEW,
      useUnmergedTree = true
    ).assertIsDisplayed()

    composeTestRule
      .onNode(hasText(TestContributor.USER_NAME))
      .assertIsDisplayed()
  }

  @Test
  fun When_User_Tap_On_A_Contributor_Then_Open_Contributor_Page() {

    val showContributorPageMock: (String) -> Unit = mock()

    composeTestRule.setContent {
      Contributors(
        repoName = "",
        navigateUp = {},
        viewState = ContributorsViewState.Data(listOf(TestContributor.create())),
        showContributorPage = showContributorPageMock
      )
    }
    composeTestRule
      .onNode(hasText(TestContributor.USER_NAME))
      .performClick()

    verify(showContributorPageMock).invoke(TestContributor.ACCOUNT_URL)
  }
}
