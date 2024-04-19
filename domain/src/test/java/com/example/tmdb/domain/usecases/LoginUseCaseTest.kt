package com.example.tmdb.domain.usecases

import com.example.tmdb.domain.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {
    private val repository: UserRepository = mockk()
    private val loginUseCase = LoginUseCase(
        dispatcher = UnconfinedTestDispatcher(),
        userRepository = repository
    )
    private val correctUserParams = LoginParams("user01","1234")
    @Test
    fun `check if login function in repository has correct inputs`() = runTest {
        loginUseCase(correctUserParams)
        coVerify {
            repository.login(correctUserParams.username, correctUserParams.password)
        }
    }

    @Test
    fun `login success`() = runTest {
        coEvery {
            repository.login(correctUserParams.username, correctUserParams.password)
        } returns Unit
        val result = loginUseCase(correctUserParams)
        result.exceptionOrNull() shouldBe null
    }


    @Test
    fun `login fail`() = runTest {
        val incorrectUserParams = LoginParams("user02","5678")
        val error = Exception("Your username or password is incorrect!")
        coEvery {
            repository.login(incorrectUserParams.username,incorrectUserParams.password)
        } throws error
        val result = loginUseCase(incorrectUserParams)
        result.exceptionOrNull() shouldBe error
    }
}