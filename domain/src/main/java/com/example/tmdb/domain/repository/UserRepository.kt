package com.example.tmdb.domain.repository

import com.example.tmdb.domain.models.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(): Flow<UserModel>
}