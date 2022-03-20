package com.playground.android.githubclient.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.playground.android.githubclient.domain.model.RepositoryCoordinate
import com.playground.android.githubclient.presentation.contributors.ContributorsScreen
import com.playground.android.githubclient.presentation.search.SearchScreen

@Composable
fun Navigation() {
  val navController = rememberNavController()
  NavHost(navController, startDestination = Screen.Search.route) {
    addSearch(navController)
    addContributors(navController)
  }
}

private fun NavGraphBuilder.addSearch(navController: NavController) {
  composable(route = Screen.Search.route) {
    SearchScreen(
      showContributors = { repoCoordinate ->
        navController.navigate(Screen.Contributors.createRoute(repoCoordinate)) {
          launchSingleTop = true
        }
      }
    )
  }
}

private fun NavGraphBuilder.addContributors(navController: NavController) {
  composable(route = Screen.Contributors.route) { backStackEntry ->
    val owner = requireNotNull(backStackEntry.arguments?.getString(Screen.ARG_OWNER))
    val name = requireNotNull(backStackEntry.arguments?.getString(Screen.ARG_REPO_NAME))
    val context = LocalContext.current
    ContributorsScreen(
      navigateUp = navController::navigateUp,
      repositoryCoordinate = RepositoryCoordinate(owner = owner, name = name),
      showContributorPage = { url ->
        context.startActivity(
          Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
          }
        )
      }
    )
  }
}

private sealed class Screen(val route: String) {

  object Search : Screen("search")

  object Contributors : Screen("contributors/{$ARG_OWNER}/{$ARG_REPO_NAME}") {
    fun createRoute(coordinate: RepositoryCoordinate) =
      "contributors/${coordinate.owner}/${coordinate.name}"
  }

  companion object {
    const val ARG_OWNER = "owner"
    const val ARG_REPO_NAME = "repo"
  }
}
