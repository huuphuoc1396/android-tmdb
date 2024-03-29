package com.example.tmdb.data.services.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenRequest(
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "grant_type")
    val grantType: String = "refresh_token",
)