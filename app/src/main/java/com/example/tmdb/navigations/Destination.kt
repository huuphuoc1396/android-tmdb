package com.example.tmdb.navigations

abstract class Destination(protected val routeName: String, vararg params: String) {
    val route: String = if (params.isEmpty()) {
        routeName
    } else {
        val builder = StringBuilder(routeName)
        params.forEach { param -> builder.append("/{${param}}") }
        builder.toString()
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