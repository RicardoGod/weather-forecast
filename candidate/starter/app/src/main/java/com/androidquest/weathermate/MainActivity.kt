package com.androidquest.weathermate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.androidquest.weathermate.ui.WeatherScreen
import com.androidquest.weathermate.ui.theme.WeatherMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherMateTheme {
                WeatherScreen()
            }
        }
    }
}
