package com.example.tmdb.states

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class UiStateViewModel<UiState, Event>(
    initialUiState: UiState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
    uiStateDelegateImpl: UiStateDelegateImpl<UiState, Event> = UiStateDelegateImpl(
        initialUiState = initialUiState,
        singleLiveEventCapacity = singleLiveEventCapacity,
    ),
) : ViewModel(),
    UiStateDelegate<UiState, Event> by uiStateDelegateImpl {

    open fun updateAsync(transform: (uiState: UiState) -> UiState) {
        viewModelScope.launch { update(transform) }
    }

    open fun showLoading() {
        viewModelScope.launch { setLoading(true) }
    }

    open fun hideLoading() {
        viewModelScope.launch { setLoading(false) }
    }

    open fun showError(t: Throwable) {
        viewModelScope.launch { setError(t) }
    }

    open fun hideError() {
        viewModelScope.launch { setError(null) }
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
}