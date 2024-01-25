package com.example.tmdb.domain.usecases

import com.example.tmdb.domain.di.annotations.IoDispatcher
import com.example.tmdb.domain.interactors.SingleUseCase
import com.example.tmdb.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : SingleUseCase<LoginParams, Unit>(dispatcher) {
    override suspend fun execute(params: LoginParams) {
        userRepository.login(
            username = params.username,
            password = params.password,
        )
    }
}

data class LoginParams(
    val username: String,
    val password: String,
)