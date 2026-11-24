package com.androidquest.weathermate.data.viewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidquest.weathermate.screen.WeatherScreen

/**
 * Liga o ViewModel ao ecrã e trata dos 3 estados: carregando, erro e sucesso.
 */
@Composable
fun WeatherRoute(viewModel: WeatherViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    when (val s = state) {
        is WeatherUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is WeatherUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = s.message, style = MaterialTheme.typography.bodyLarge)
                Button(
                    onClick = { viewModel.loadWeather("Lisboa") },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Tentar novamente")
                }
            }
        }

        is WeatherUiState.Success -> {
            WeatherScreen(current = s.current, forecast = s.forecast)
        }
    }
}
