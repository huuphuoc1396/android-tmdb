package com.example.tmdb.ui.features.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tmdb.R
import com.example.tmdb.commons.body.BodyContent
import com.example.tmdb.extensions.showToast
import com.example.tmdb.ui.theme.AppTheme

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    BodyContent(
        uiStateDelegate = viewModel,
        modifier = modifier,
        onEventEffect = { event ->
            when (event) {
                is LoginViewModel.Event.Toast -> {
                    context.showToast(event.message)
                }
            }
        }
    ) { uiState ->
        LoginContent(
            uiState = uiState,
            modifier = modifier,
            onUsernameChange = viewModel::updateUsername,
            onPasswordChange = viewModel::updatePassword,
            onLoginClick = viewModel::login,
        )
    }
}

@Composable
fun LoginContent(
    uiState: LoginViewModel.UiState,
    modifier: Modifier = Modifier,
    onUsernameChange: (username: String) -> Unit = {},
    onPasswordChange: (password: String) -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = uiState.username,
            onValueChange = onUsernameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.username)) })
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
        ) {
            Text(text = stringResource(R.string.login))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginContent(LoginViewModel.UiState())
    }
}