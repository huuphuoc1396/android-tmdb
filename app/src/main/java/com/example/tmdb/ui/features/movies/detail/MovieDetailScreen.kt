package com.example.tmdb.ui.features.movies.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tmdb.R
import com.example.tmdb.components.screen.ScreenContent
import com.example.tmdb.ui.theme.AppTheme

@ExperimentalComposeUiApi
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailModel = hiltViewModel(),
) {
    ScreenContent(
        viewModel = viewModel,
        modifier = modifier,
    ) {
        MovieDetailContent(it, Modifier)
    }
}

@Composable
private fun MovieDetailContent(
    uiState: MovieDetailModel.UiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.movie_detail_id, uiState.movieId),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Preview
@Composable
fun MovieDetailContentPreview() {
    AppTheme {
        MovieDetailContent(MovieDetailModel.UiState(movieId = "1234"))
    }
}