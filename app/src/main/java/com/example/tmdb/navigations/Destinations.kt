package com.example.tmdb.navigations

import androidx.lifecycle.SavedStateHandle
import com.example.tmdb.domain.extensions.defaultEmpty

abstract class Destination(protected val routeName: String, vararg params: String) {
    val route: String = if (params.isEmpty()) {
        routeName
    } else {
        val builder = StringBuilder(routeName)
        params.forEach { param -> builder.append("/{${param}}") }
        builder.toString()
    }

    companion object {
        const val ROUTE_LOGIN = "login"
        const val ROUTE_MAIN = "main"
        const val ROUTE_SPLASH = "splash"
        const val ROUTE_MOVIE_DETAIL = "movieDetail"

        const val KEY_MOVIE_ID = "movieId"
    }
}

open class NoArgumentsDestination(name: String) : Destination(name) {
    operator fun invoke(): String = routeName
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach { param ->
        param.second
            ?.toString()
            ?.let { arg ->
                builder.append("/$arg")
            }
    }

    return builder.toString()
}

data object LoginDestination : NoArgumentsDestination(ROUTE_LOGIN)

data object MainDestination : NoArgumentsDestination(ROUTE_MAIN)

data object SplashDestination : NoArgumentsDestination(ROUTE_SPLASH)

data object MovieDetailDestination : Destination(ROUTE_MOVIE_DETAIL, KEY_MOVIE_ID) {
    operator fun invoke(movieId: String) = routeName.appendParams(
        KEY_MOVIE_ID to movieId,
    )

    fun SavedStateHandle.getMovieId() = get<String>(KEY_MOVIE_ID).defaultEmpty()
}
