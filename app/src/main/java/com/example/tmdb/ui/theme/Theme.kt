package com.example.tmdb.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val themeColors = lightColors(
    primary = OxfordBlue
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = themeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

