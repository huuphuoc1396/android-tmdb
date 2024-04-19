package com.example.tmdb.ui.features.login

import app.cash.turbine.test
import com.example.tmdb.ViewModelTest
import com.example.tmdb.domain.repository.UserRepository
import com.example.tmdb.domain.usecases.LoginParams
import com.example.tmdb.domain.usecases.LoginUseCase
import com.example.tmdb.navigations.Navigator
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
class LoginViewModelTest : ViewModelTest() {
    private val navigator: Navigator = mockk()
    private val userRepository: UserRepository = mockk()
    private val loginUseCase: LoginUseCase = LoginUseCase(testDispatcher, userRepository)
    private val viewModel: LoginViewModel = LoginViewModel(navigator, loginUseCase)

    @Test
    fun `username change each time user types new input`() = runTest {
        val userName = "somename"
        viewModel.apply {
            updateUsername(userName)
            uiStateFlow.test {
                expectMostRecentItem().username shouldBe userName
            }
        }
    }

    @Test
    fun `login with correct user name and password`() = runTest {
        viewModel.apply {
            val params = LoginParams("user01", "1234")
            updateUsername(params.username)
            updatePassword(params.password)
            login()
            coVerify {
                loginUseCase(params)
            }
        }
    }
}