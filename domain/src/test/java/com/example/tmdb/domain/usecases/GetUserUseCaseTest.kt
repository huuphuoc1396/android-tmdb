package com.example.tmdb.domain.usecases

import com.example.tmdb.domain.models.user.UserModel
import com.example.tmdb.domain.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetUserUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val userUseCase = GetUserUseCase(
        dispatcher = UnconfinedTestDispatcher(),
        userRepository
    )

    @Test
    fun `get correct user from dataStore`() = runTest {
        val user = UserModel(id = "aptent", name = "Helena Pollard", email = "patrice.trevino@example.com", avatarUrl = "https://www.google.com/#q=docendi")
        coEvery {
            userRepository.getUser()
        } returns flowOf(user)
        userUseCase(Unit).collect { dataStoreUser ->
            dataStoreUser.getOrNull() shouldBe user
        }
    }
}