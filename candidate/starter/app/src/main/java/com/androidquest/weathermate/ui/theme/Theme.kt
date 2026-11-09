package com.androidquest.weathermate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val WeatherMateColors = lightColorScheme()

@Composable
fun WeatherMateTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WeatherMateColors,
        content = content
    )
}
