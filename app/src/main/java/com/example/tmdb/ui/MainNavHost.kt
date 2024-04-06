package com.example.tmdb.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tmdb.navigations.composable
import com.example.tmdb.ui.features.login.LoginScreen
import com.example.tmdb.ui.features.main.MainScreen
import com.example.tmdb.ui.features.movies.detail.MovieDetailScreen
import com.example.tmdb.ui.features.splash.SplashScreen

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    route: String? = null,
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route,
        modifier = modifier,
        route = route,
    ) {
        composable(destination = SplashDestination) {
            SplashScreen()
        }

        composable(destination = LoginDestination) {
            LoginScreen()
        }

        composable(destination = MainDestination) {
            MainScreen()
        }

        composable(destination = MovieDetailDestination) {
            MovieDetailScreen()
        }
    }
}