package com.example.tmdb.states

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * UiState - must be Data class, immutable
 */
interface UiStateDelegate<UiState, Event> {

    /**
     * Declarative description of the UI based on the current state.
     */
    val uiStateFlow: StateFlow<UiState>

    val singleEvents: Flow<Event>

    val isLoading: StateFlow<Boolean>

    val error: StateFlow<Throwable?>

    /**
     * State is read-only
     * The only way to change the state is to emit[update] an action,
     * an object describing what happened.
     */
    val UiStateDelegate<UiState, Event>.uiState: UiState

    /**
     * Transforms UI state using the specified transformation.
     *
     * @param transform  - function to transform UI state.
     */
    suspend fun UiStateDelegate<UiState, Event>.update(
        transform: (uiState: UiState) -> UiState,
    )

    suspend fun UiStateDelegate<UiState, Event>.sendEvent(event: Event)

    suspend fun setError(error: Throwable?)

    suspend fun setLoading(isLoading: Boolean)
}

/**
 * Implementation of a delegate to manage state.
 * This delegate stores and manages UI state.
 *
 * @param mutexState A mutex for synchronizing state access.
 * @param initialUiState Initial UI state.
 * @param singleLiveEventCapacity Channel capacity for SingleLiveEvent.
 */
class UiStateDelegateImpl<UiState, Event>(
    initialUiState: UiState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
    private val mutexState: Mutex = Mutex(),
    private val mutexLoading: Mutex = Mutex(),
    private val mutexError: Mutex = Mutex(),
) : UiStateDelegate<UiState, Event> {

    /**
     * The source of truth that drives our app.
     */
    private val _uiState = MutableStateFlow(initialUiState)

    override val uiStateFlow: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    override val UiStateDelegate<UiState, Event>.uiState: UiState
        get() = _uiState.value

    private val singleEventsChannel = Channel<Event>(singleLiveEventCapacity)

    override val singleEvents: Flow<Event>
        get() = singleEventsChannel.receiveAsFlow()

    private val _isLoading = MutableStateFlow(false)

    override val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _errorChannel = MutableStateFlow<Throwable?>(null)

    override val error: StateFlow<Throwable?>
        get() = _errorChannel

    override suspend fun UiStateDelegate<UiState, Event>.update(
        transform: (uiState: UiState) -> UiState,
    ) {
        mutexState.withLock {
            _uiState.emit(transform(uiState))
        }
    }

    override suspend fun UiStateDelegate<UiState, Event>.sendEvent(event: Event) {
        singleEventsChannel.send(event)
    }

    override suspend fun setLoading(isLoading: Boolean) {
        mutexLoading.withLock {
            _isLoading.emit(isLoading)
        }
    }

    override suspend fun setError(error: Throwable?) {
        mutexError.withLock {
            _errorChannel.emit(error)
        }
    }
}
