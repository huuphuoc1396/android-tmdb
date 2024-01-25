package com.example.tmdb.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.FlowCollector

@Composable
fun <R> UiStateDelegate<R, *>.collectUiState(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) = this.uiStateFlow.collectAsStateWithLifecycle(
    minActiveState = minActiveState,
)

@Composable
fun <R> UiStateDelegate<R, *>.collectError(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) = this.error.collectAsStateWithLifecycle(
    minActiveState = minActiveState,
)

@Composable
fun <R> UiStateDelegate<R, *>.collectLoading(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) = this.isLoading.collectAsStateWithLifecycle(
    minActiveState = minActiveState,
)

@Composable
fun <State, Event> UiStateDelegate<State, Event>.SingleEventEffect(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    vararg keys: Any?,
    collector: FlowCollector<Event>,
) = LaunchedEffect(Unit, *keys) {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect(collector)
}