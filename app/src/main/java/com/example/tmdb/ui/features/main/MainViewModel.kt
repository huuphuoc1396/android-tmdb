package com.example.tmdb.ui.features.main

import com.example.tmdb.domain.models.errors.UnauthorizedError
import com.example.tmdb.ui.MovieDetailDestination
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.states.NoUiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigator: Navigator
) : NoUiStateViewModel(navigator) {

    init {
        launch {
            // TODO("Remove later, to fake session timeout")
            delay(10000L)
            setError(UnauthorizedError)
        }
    }

    fun goToDetail(id: String) {
        tryNavigateTo(MovieDetailDestination(id))
    }
}