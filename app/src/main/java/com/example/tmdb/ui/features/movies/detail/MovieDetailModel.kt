package com.example.tmdb.ui.features.movies.detail

import androidx.lifecycle.SavedStateHandle
import com.example.tmdb.ui.MovieDetailDestination.getMovieId
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.states.NoEvent
import com.example.tmdb.states.UiStateDelegateImpl
import com.example.tmdb.states.UiStateViewModel
import com.example.tmdb.ui.features.movies.detail.MovieDetailModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailModel @Inject constructor(
    navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : UiStateViewModel<UiState, NoEvent>(
    navigator = navigator,
    uiStateDelegate = UiStateDelegateImpl(UiState())
) {

    data class UiState(
        val movieId: String = "",
    )

    init {
        updateAsync { uiState ->
            uiState.copy(movieId = savedStateHandle.getMovieId())
        }
    }
}