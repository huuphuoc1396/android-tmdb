package com.example.tmdb.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.delegates.UiStateDelegate
import com.example.tmdb.delegates.UiStateDelegateImpl
import com.example.tmdb.domain.extensions.defaultEmpty
import com.example.tmdb.domain.usecases.LoginParams
import com.example.tmdb.domain.usecases.LoginUseCase
import com.example.tmdb.extensions.launch
import com.example.tmdb.navigations.Navigator
import com.example.tmdb.ui.features.login.LoginViewModel.Event
import com.example.tmdb.ui.features.login.LoginViewModel.UiState
import com.example.tmdb.ui.features.main.MainDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator,
    private val loginUseCase: LoginUseCase,
) : ViewModel(),
    UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState()) {

    data class UiState(
        val username: String = "",
        val password: String = "",
    )

    sealed class Event {
        data class Toast(val message: String) : Event()
    }

    fun updateUsername(username: String) {
        reduceAsync(viewModelScope) { data -> data.copy(username = username) }
    }

    fun updatePassword(password: String) {
        reduceAsync(viewModelScope) { uiState -> uiState.copy(password = password) }
    }

    fun login() {
        launch(loading = this) {
            loginUseCase(
                LoginParams(
                    username = uiState.username,
                    password = uiState.password,
                )
            )
                .onSuccess {
                    navigator.navigateTo(MainDestination())
                }
                .onFailure { t ->
                    sendEvent(Event.Toast(t.message.defaultEmpty()))
                    showError(t)
                }
        }
    }
}