package com.example.tmdb.domain.repository

import com.example.tmdb.domain.models.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUser()

    fun getUser(): Flow<UserModel>
}