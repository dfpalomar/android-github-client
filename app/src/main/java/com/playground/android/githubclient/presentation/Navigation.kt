package com.playground.android.githubclient.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.playground.android.githubclient.domain.model.RepositoryId
import com.playground.android.githubclient.presentation.contributors.ContributorsScreen
import com.playground.android.githubclient.presentation.search.SearchScreen

@Composable
fun Navigation() {
  val navController = rememberNavController()
  NavHost(navController, startDestination = Screen.TrendingRepos.route) {
    addSearch(navController)
    addContributors(navController)
  }
}

private fun NavGraphBuilder.addSearch(navController: NavController) {
  composable(route = Screen.TrendingRepos.route) {
    SearchScreen(
      showContributors = { repositoryId ->
        navController.navigate(Screen.RepoDetails.createRoute(repositoryId)) {
          launchSingleTop = true
        }
      }
    )
  }
}

private fun NavGraphBuilder.addContributors(navController: NavController) {
  composable(route = Screen.RepoDetails.route) {
    ContributorsScreen(navController::navigateUp)
  }
}

private sealed class Screen(val route: String) {

  object TrendingRepos : Screen("trending_repos")

  object RepoDetails : Screen("repo_details/{id}") {
    fun createRoute(id: RepositoryId) = "repo_details/$id"
  }
}
