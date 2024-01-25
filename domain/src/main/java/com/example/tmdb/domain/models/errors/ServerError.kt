package com.example.tmdb.domain.models.errors

data class ServerError(
    val code: Int,
    val serverMsg: String,
) : Error()