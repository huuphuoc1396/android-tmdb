package com.example.tmdb.data.services

import com.example.tmdb.data.services.requests.RefreshTokenRequest
import com.example.tmdb.data.services.responses.DataResponse
import com.example.tmdb.data.services.responses.token.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface NonAuthService {
    @POST("/api/v1/refreshToken")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): DataResponse<RefreshTokenResponse>
}