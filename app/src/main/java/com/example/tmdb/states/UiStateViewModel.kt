package com.example.tmdb.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class UiStateViewModel<UiState, Event>(
    initialUiState: UiState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
    private val mutexState: Mutex = Mutex(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    private val _singleEvent = Channel<Event>(singleLiveEventCapacity)

    open val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    open val singleEvent: Flow<Event>
        get() = _singleEvent.receiveAsFlow()

    open suspend fun update(
        transform: suspend (uiState: UiState) -> UiState,
    ) {
        mutexState.withLock {
            _uiState.emit(transform(_uiState.value))
        }
    }

    open fun updateAsync(
        transform: suspend (state: UiState) -> UiState,
    ): Job {
        return viewModelScope.launch {
            update { state -> transform(state) }
        }
    }

    open suspend fun sendEvent(event: Event) {
        _singleEvent.send(event)
    }

    open fun trySendEvent(event: Event) {
        _singleEvent.trySend(event)
    }

    @Composable
    fun collectAsUiStateWithLifecycle(
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    ) = this.uiState.collectAsStateWithLifecycle(
        minActiveState = minActiveState,
    )

    @Composable
    fun LaunchedSingleEventEffect(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
        vararg keys: Any?,
        collector: FlowCollector<Event>,
    ) = LaunchedEffect(Unit, *keys) {
        singleEvent.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = lifecycleState,
        ).collect(collector)
    }
}