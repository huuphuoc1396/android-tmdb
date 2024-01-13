package com.example.tmdb.domain.usecases

import com.example.tmdb.domain.di.annotations.IoDispatcher
import com.example.tmdb.domain.interactors.Empty
import com.example.tmdb.domain.interactors.StreamUseCase
import com.example.tmdb.domain.models.user.UserModel
import com.example.tmdb.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : StreamUseCase<Empty, UserModel>(dispatcher) {

    override suspend fun execute(params: Empty): Flow<UserModel> {
        return userRepository.getUser()
    }
}