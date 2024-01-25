package com.example.tmdb.states

import com.example.tmdb.states.NoUiStateViewModel.NoEvent
import com.example.tmdb.states.NoUiStateViewModel.NoUiState

abstract class NoUiStateViewModel : UiStateViewModel<NoUiState, NoEvent>(NoUiState) {

    data object NoUiState

    data object NoEvent
}