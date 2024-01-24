package com.example.tmdb.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tmdb.ui.features.login.LoginDestination
import com.example.tmdb.ui.features.splash.SplashDestination
import com.example.tmdb.ui.features.splash.SplashScreen
import com.example.tmdb.navigations.composable
import com.example.tmdb.ui.features.login.LoginScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    route: String? = null,
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.fullRoute,
        modifier = modifier,
        route = route,
    ) {
        composable(destination = SplashDestination) {
            SplashScreen()
        }

        composable(destination = LoginDestination) {
            LoginScreen()
        }
    }
}