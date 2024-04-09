package com.example.tmdb.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val themeColors = lightColorScheme(
    primary = OxfordBlue
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = themeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

