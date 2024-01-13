package com.example.tmdb.domain.models.errors

sealed class ApiError : Error() {

    data object NoConnection : ApiError()

    data class Server(
        val code: Int,
        val serverMsg: String,
    ) : ApiError()

    data object Unauthorized: ApiError()
}