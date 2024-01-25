package com.example.tmdb.components.error

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tmdb.R
import com.example.tmdb.domain.extensions.defaultEmpty
import com.example.tmdb.domain.models.errors.NoConnectionError
import com.example.tmdb.domain.models.errors.ServerError
import com.example.tmdb.domain.models.errors.UnauthorizedError

@Composable
fun ErrorDialog(
    error: Throwable,
    onOkClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onRedirectToLogin: () -> Unit = {},
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.error))
        },
        text = {
            Text(text = error.userReadableMessage())
        },
        onDismissRequest = {
            onDismiss()
            if (error.shouldRedirectToLogin()) {
                onRedirectToLogin()
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onOkClick()
                    if (error.shouldRedirectToLogin()) {
                        onRedirectToLogin()
                    }
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
    )
}

@Composable
fun Throwable.userReadableMessage(): String {
    return when (this) {
        NoConnectionError -> stringResource(R.string.no_connection_error)

        UnauthorizedError -> stringResource(R.string.unauthorized_error)

        is ServerError -> serverMsg.ifEmpty { stringResource(R.string.unknown_error) }

        else -> message.defaultEmpty()
    }
}

fun Throwable.shouldRedirectToLogin() = this is UnauthorizedError
