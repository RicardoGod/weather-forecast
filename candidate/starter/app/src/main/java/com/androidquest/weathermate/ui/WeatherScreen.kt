package com.androidquest.weathermate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidquest.weathermate.data.mock.MockWeatherData
import com.androidquest.weathermate.model.WeatherUiModel

@Composable
@Preview(showBackground = true)
fun WeatherScreen(
    weather: WeatherUiModel = MockWeatherData.lisbon,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar a composição do ecrã.
    // Pistas: Column, WeatherHeader, CurrentWeatherCard, DailyForecastItem e LazyColumn.
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Weather Mate", style = MaterialTheme.typography.headlineMedium)
        Text("Constrói aqui o teu ecrã meteorológico.")
    }
}

@Composable
fun WeatherHeader(title: String) {
    // TODO: Criar cabeçalho do ecrã.
}

@Composable
fun CurrentWeatherCard(weather: WeatherUiModel) {
    // TODO: Mostrar cidade, temperatura, condição, máxima e mínima.
}

@Composable
fun DailyForecastItem(item: com.androidquest.weathermate.model.DailyForecast) {
    // TODO: Mostrar dia, condition e temperaturas.
}
