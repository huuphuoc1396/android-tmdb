package com.example.tmdb.states

import com.example.tmdb.navigations.Navigator

abstract class NoUiStateViewModel(
    navigator: Navigator
) : UiStateViewModel<NoUiState, NoEvent>(
    navigator = navigator,
    uiStateDelegate = UiStateDelegateImpl(NoUiState),
)

data object NoUiState

data object NoEvent