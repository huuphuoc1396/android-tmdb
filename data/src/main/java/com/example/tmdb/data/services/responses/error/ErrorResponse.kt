package com.example.tmdb.data.services.responses.error

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "status")
    val status: Int,
    @Json(name = "error")
    val error: String,
)
