package com.playground.android.githubclient

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.playground.android.githubclient.domain.RepositoryId
import com.playground.android.githubclient.presentation.repodetails.RepoDetailsScreen
import com.playground.android.githubclient.presentation.trendingrepos.TrendingReposScreen

@Composable
fun Navigation() {
  val navController = rememberNavController()
  NavHost(navController, startDestination = Screen.TrendingRepos.route) {
    addTrendingRepos(navController)
    addRepoDetails(navController)
  }
}

private fun NavGraphBuilder.addTrendingRepos(navController: NavController) {
  composable(route = Screen.TrendingRepos.route) {
    TrendingReposScreen(
      openRepoDetails = { repositoryId ->
        navController.navigate(Screen.RepoDetails.createRoute(repositoryId)) {
          launchSingleTop = true
        }
      }
    )
  }
}

private fun NavGraphBuilder.addRepoDetails(navController: NavController) {
  composable(route = Screen.RepoDetails.route) {
    RepoDetailsScreen(navController::navigateUp)
  }
}

private sealed class Screen(val route: String) {

  object TrendingRepos : Screen("trending_repos")

  object RepoDetails : Screen("repo_details/{id}") {
    fun createRoute(id: RepositoryId) = "repo_details/$id"
  }
}
