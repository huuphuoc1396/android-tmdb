package com.example.tmdb.commons.error

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tmdb.R

@Composable
fun ErrorDialog(
    onOkClick: () -> Unit,
    onDismiss: () -> Unit,
    message: String,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.error))
        },
        text = {
            Text(text = message)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onOkClick
            ) {
                Text(stringResource(R.string.ok))
            }
        },
    )
}