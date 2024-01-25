package com.example.tmdb.ui.features.splash

import com.example.tmdb.navigations.LoginDestination
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.navigations.SplashDestination
import com.example.tmdb.states.NoUiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    navigator: Navigator
) : NoUiStateViewModel(navigator) {

    companion object {
        private const val SPLASH_DELAY = 1500L
    }

    init {
        showSplash()
    }

    private fun showSplash() {
        launch {
            delay(SPLASH_DELAY)
            navigateTo(
                route = LoginDestination(),
                popUpToRoute = SplashDestination(),
                inclusive = true,
            )
        }
    }
}