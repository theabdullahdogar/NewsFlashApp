package com.example.madquiz.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.madquiz.ui.screens.DetailScreen
import com.example.madquiz.ui.screens.HomeScreen
import com.example.madquiz.ui.screens.SplashScreen
import com.example.madquiz.ui.viewmodel.NewsViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home   : Screen("home")
    object Detail : Screen("detail")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: NewsViewModel = viewModel()

    NavHost(
        navController  = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel     = viewModel,
                onArticleClick = { article ->
                    viewModel.selectArticle(article)
                    navController.navigate(Screen.Detail.route)
                }
            )
        }

        composable(Screen.Detail.route) {
            DetailScreen(
                viewModel   = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
