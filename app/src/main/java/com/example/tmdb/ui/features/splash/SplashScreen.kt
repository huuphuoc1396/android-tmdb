package com.example.tmdb.ui.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "TMDB",
            style = MaterialTheme.typography.h3,
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}