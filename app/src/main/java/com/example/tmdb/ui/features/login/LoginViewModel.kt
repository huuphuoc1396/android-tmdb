package com.example.tmdb.ui.features.login

import com.example.tmdb.domain.extensions.defaultEmpty
import com.example.tmdb.domain.usecases.LoginParams
import com.example.tmdb.domain.usecases.LoginUseCase
import com.example.tmdb.navigations.MainDestination
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.states.UiStateDelegateImpl
import com.example.tmdb.states.UiStateViewModel
import com.example.tmdb.ui.features.login.LoginViewModel.Event
import com.example.tmdb.ui.features.login.LoginViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    navigator: Navigator,
    private val loginUseCase: LoginUseCase,
) : UiStateViewModel<UiState, Event>(
    navigator = navigator,
    uiStateDelegate = UiStateDelegateImpl(UiState()),
) {

    data class UiState(
        val username: String = "",
        val password: String = "",
    )

    sealed class Event {
        data class Toast(val message: String) : Event()
    }

    fun updateUsername(username: String) {
        updateAsync { data -> data.copy(username = username) }
    }

    fun updatePassword(password: String) {
        updateAsync { uiState -> uiState.copy(password = password) }
    }

    fun login() {
        launchWithLoading {
            val loginParam = LoginParams(
                username = uiState.username,
                password = uiState.password,
            )
            loginUseCase(loginParam)
                .onSuccess {
                    navigateTo(
                        route = MainDestination(),
                        popUpToRoute = MainDestination(),
                        inclusive = true,
                    )
                }
                .onFailure { t ->
                    sendEvent(Event.Toast(t.message.defaultEmpty()))
                    showError(t)
                }
        }
    }
}