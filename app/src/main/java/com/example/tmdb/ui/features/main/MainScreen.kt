package com.example.tmdb.ui.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tmdb.R
import com.example.tmdb.components.screen.ScreenContent
import com.example.tmdb.ui.theme.AppTheme

@ExperimentalComposeUiApi
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    ScreenContent(
        viewModel = viewModel,
        modifier = modifier,
    ) {
        MainContent(
            modifier = Modifier,
            onGoToDetailClick = viewModel::goToDetail,
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    onGoToDetailClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Main Screen",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = { onGoToDetailClick("1234") },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(R.string.go_to_detail).uppercase())
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainContent()
    }
}