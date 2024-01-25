package com.example.tmdb.navigations

open class Destination(protected val name: String, vararg params: String) {
    val route: String = if (params.isEmpty()) name else {
        val builder = StringBuilder(name)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }
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
