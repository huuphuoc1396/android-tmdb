package com.example.tmdb.commons.body

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.tmdb.commons.error.ErrorDialog
import com.example.tmdb.commons.loading.LoadingContent
import com.example.tmdb.delegates.CollectEventEffect
import com.example.tmdb.delegates.UiStateDelegate
import com.example.tmdb.delegates.collectErrorWithLifecycle
import com.example.tmdb.delegates.collectLoadingWithLifecycle
import com.example.tmdb.delegates.collectUiStateWithLifecycle
import com.example.tmdb.domain.extensions.defaultEmpty

@ExperimentalComposeUiApi
@Composable
fun <UiState, Event> BodyContent(
    uiStateDelegate: UiStateDelegate<UiState, Event>,
    modifier: Modifier = Modifier,
    onEventEffect: (event: Event) -> Unit = {},
    content: @Composable (uiState: UiState) -> Unit = {},
) {
    val uiState by uiStateDelegate.collectUiStateWithLifecycle()
    val isLoading by uiStateDelegate.collectLoadingWithLifecycle()
    val error by uiStateDelegate.collectErrorWithLifecycle()
    LoadingContent(
        isLoading = isLoading,
        modifier = modifier
    ) {
        if (error != null) {
            ErrorDialog(
                onOkClick = uiStateDelegate::hideError,
                onDismiss = uiStateDelegate::hideError,
                message = error?.message.defaultEmpty(),
            )
        }
        content(uiState)
    }

    uiStateDelegate.CollectEventEffect { event ->
        onEventEffect(event)
    }
}