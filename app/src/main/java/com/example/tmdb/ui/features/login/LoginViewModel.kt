package com.example.tmdb.ui.features.login

import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.usecases.LoginParams
import com.example.tmdb.domain.usecases.LoginUseCase
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.states.UiStateViewModel
import com.example.tmdb.ui.features.login.LoginViewModel.Event
import com.example.tmdb.ui.features.login.LoginViewModel.UiState
import com.example.tmdb.ui.features.main.MainDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator,
    private val loginUseCase: LoginUseCase,
) : UiStateViewModel<UiState, Event>(UiState()) {

    data class UiState(
        val username: String = "",
        val password: String = "",
    )

    sealed class Event {
        data class Error(val t: Throwable) : Event()
    }

    fun updateUsername(username: String) {
        updateAsync { uiState -> uiState.copy(username = username) }
    }

    fun updatePassword(password: String) {
        updateAsync { uiState -> uiState.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch {
            loginUseCase(
                LoginParams(
                    username = uiState.value.username,
                    password = uiState.value.password,
                )
            )
                .onSuccess {
                    navigator.navigateTo(MainDestination())
                }
                .onFailure { throwable ->
                    sendEvent(Event.Error(throwable))
                }
        }
    }
}