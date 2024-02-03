package com.example.tmdb.states

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.ui.LoginDestination
import com.example.tmdb.navigations.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class UiStateViewModel<UiState, Event>(
    navigator: Navigator,
    uiStateDelegate: UiStateDelegate<UiState, Event>,
) : ViewModel(),
    UiStateDelegate<UiState, Event> by uiStateDelegate,
    Navigator by navigator {

    open fun updateAsync(transform: (uiState: UiState) -> UiState) {
        launch { update(transform) }
    }

    open fun showLoading() {
        launch { setLoading(true) }
    }

    open fun hideLoading() {
        launch { setLoading(false) }
    }

    open fun showError(t: Throwable) {
        launch { setError(t) }
    }

    open fun hideError() {
        launch { setError(null) }
    }

    open fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch(
            context = context,
            start = start,
            block = block,
        )
    }

    open fun launchWithLoading(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        showLoading()
        return launch(
            context = context,
            start = start,
            block = block,
        ).also { job ->
            job.invokeOnCompletion {
                hideLoading()
            }
        }
    }

    open fun <T> Flow<T>.injectLoading(): Flow<T> = this
        .onStart { showLoading() }
        .onEach { hideLoading() }

    fun redirectToLogin() {
        launch {
            navigateTo(
                route = LoginDestination(),
                popUpToRoute = LoginDestination(),
                inclusive = true,
            )
        }
    }
}