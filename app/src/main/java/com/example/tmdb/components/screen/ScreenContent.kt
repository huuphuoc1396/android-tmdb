package com.example.tmdb.components.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.tmdb.components.error.ErrorDialog
import com.example.tmdb.components.loading.LoadingContent
import com.example.tmdb.states.SingleEventEffect
import com.example.tmdb.states.UiStateViewModel
import com.example.tmdb.states.collectError
import com.example.tmdb.states.collectLoading
import com.example.tmdb.states.collectUiState

@ExperimentalComposeUiApi
@Composable
fun <UiState, Event> ScreenContent(
    viewModel: UiStateViewModel<UiState, Event>,
    modifier: Modifier = Modifier,
    onEventEffect: (event: Event) -> Unit = {},
    content: @Composable (uiState: UiState) -> Unit = {},
) {
    val uiState by viewModel.collectUiState()
    val error by viewModel.collectError()
    val isLoading by viewModel.collectLoading()
    LoadingContent(
        isLoading = isLoading,
        modifier = modifier,
    ) {
        if (error != null) {
            ErrorDialog(
                error = error!!,
                onOkClick = viewModel::hideError,
                onDismiss = viewModel::hideError,
                onRedirectToLogin = viewModel::redirectToLogin,
            )
        }
        content(uiState)
    }

    viewModel.SingleEventEffect { event ->
        onEventEffect(event)
    }
}