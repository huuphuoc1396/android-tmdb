package com.example.tmdb.ui.features.login

import app.cash.turbine.test
import com.example.tmdb.ViewModelTest
import com.example.tmdb.domain.functional.wrapFailure
import com.example.tmdb.domain.functional.wrapSuccess
import com.example.tmdb.domain.usecases.LoginParams
import com.example.tmdb.domain.usecases.LoginUseCase
import com.example.tmdb.navigations.NavigationIntent
import com.example.tmdb.navigations.NavigatorImpl
import com.example.tmdb.ui.LoginDestination
import com.example.tmdb.ui.MainDestination
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
class LoginViewModelTest : ViewModelTest() {
    private val navigator = NavigatorImpl()
    private val loginUseCase: LoginUseCase = mockk()
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
    fun `password change each time user types new input`() = runTest {
        val password = "somepassword"
        viewModel.apply {
            updatePassword(password)
            uiStateFlow.test {
                expectMostRecentItem().password shouldBe password
            }
        }
    }

    @Test
    fun `login with correct user name and password`() = runTest {
        viewModel.apply {
            val params = LoginParams("user01", "1234")
            updateUsername(params.username)
            updatePassword(params.password)
            coEvery {
                loginUseCase(params)
            } returns Unit.wrapSuccess()
            login()
            navigationChannel.receiveAsFlow().test {
                expectMostRecentItem() shouldBe NavigationIntent.NavigateTo(
                    route = MainDestination(),
                    popUpToRoute = LoginDestination(),
                    inclusive = true
                )
            }
        }
    }

    @Test
    fun `login with incorrect user name and password`() = runTest {
        viewModel.apply {
            val e = Exception("Incorrect username or password")
            val params = LoginParams("user02", "5678")
            updateUsername(params.username)
            updatePassword(params.password)
            coEvery {
                loginUseCase(params)
            } returns e.wrapFailure()
            login()
            singleEvents.test {
                expectMostRecentItem() shouldBe LoginViewModel.Event.Toast(e.message!!)
            }
        }
    }
}