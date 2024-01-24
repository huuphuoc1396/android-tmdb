package com.example.tmdb.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.ui.features.login.LoginDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    companion object {
        private const val SPLASH_DELAY = 1500L
    }

    init {
        showSplash()
    }

    private fun showSplash() {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            navigator.navigateTo(
                route = LoginDestination(),
                popUpToRoute = SplashDestination(),
                inclusive = true,
            )
        }
    }
}