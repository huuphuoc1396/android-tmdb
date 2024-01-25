package com.example.tmdb.states

import com.example.tmdb.navigations.Navigator
import com.example.tmdb.states.NoUiStateViewModel.NoEvent
import com.example.tmdb.states.NoUiStateViewModel.NoUiState

abstract class NoUiStateViewModel(
    navigator: Navigator
) : UiStateViewModel<NoUiState, NoEvent>(
    navigator = navigator,
    uiStateDelegate = UiStateDelegateImpl(NoUiState),
) {

    data object NoUiState

    data object NoEvent
}