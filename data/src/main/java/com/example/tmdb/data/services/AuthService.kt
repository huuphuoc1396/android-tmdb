package com.example.tmdb.data.services

import com.example.tmdb.data.services.responses.DataResponse
import com.example.tmdb.data.services.responses.user.UserResponse
import retrofit2.http.GET

interface AuthService {

    @GET("/api/v1/user")
    suspend fun getUser(): DataResponse<UserResponse>
}