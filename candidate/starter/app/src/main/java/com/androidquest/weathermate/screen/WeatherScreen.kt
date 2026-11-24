package com.androidquest.weathermate.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidquest.weathermate.components.CitySearch
import com.androidquest.weathermate.components.CurrentWeatherCard
import com.androidquest.weathermate.components.ForecastRow
import com.androidquest.weathermate.data.mock.MockWeatherData
import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.model.DailyForecast



val CardColor = Color(0xFFE6E0E9)

@Composable
fun WeatherScreen(
    current: CurrentWeather,
    forecast: List<DailyForecast>,
    query: String,
    onQueryChange: (String) -> Unit,
    results: List<String>,
    onCitySelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Weather Mate",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            item {
                CitySearch(
                    query = query,
                    onQueryChange = onQueryChange,
                    results = results,
                    onCitySelected = onCitySelected
                )
            }

            item {
                Text(
                    text = "Atualizado agora (dados de exemplo)",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            item { CurrentWeatherCard(current) }

            item {
                Text(
                    text = "Próximos dias",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }

            items(forecast) { day ->
                ForecastRow(day)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(
        current = MockWeatherData.lisbonCurrent,
        forecast = MockWeatherData.lisbon.forecast,
        query = "",
        onQueryChange = {},
        results = MockWeatherData.cities.map { it.city },
        onCitySelected = {}
    )
}