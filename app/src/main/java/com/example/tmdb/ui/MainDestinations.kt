package com.example.tmdb.ui

import androidx.lifecycle.SavedStateHandle
import com.example.tmdb.domain.extensions.defaultEmpty
import com.example.tmdb.navigations.Destination
import com.example.tmdb.navigations.NoArgumentsDestination
import com.example.tmdb.navigations.appendParams

private const val ROUTE_LOGIN = "login"
private const val ROUTE_MAIN = "main"
private const val ROUTE_SPLASH = "splash"
private const val ROUTE_MOVIE_DETAIL = "movieDetail"

private const val KEY_MOVIE_ID = "movieId"

data object LoginDestination : NoArgumentsDestination(ROUTE_LOGIN)

data object MainDestination : NoArgumentsDestination(ROUTE_MAIN)

data object SplashDestination : NoArgumentsDestination(ROUTE_SPLASH)

data object MovieDetailDestination : Destination(ROUTE_MOVIE_DETAIL, KEY_MOVIE_ID) {
    operator fun invoke(movieId: String) = routeName.appendParams(
        KEY_MOVIE_ID to movieId,
    )

    fun SavedStateHandle.getMovieId() = get<String>(KEY_MOVIE_ID).defaultEmpty()
}
