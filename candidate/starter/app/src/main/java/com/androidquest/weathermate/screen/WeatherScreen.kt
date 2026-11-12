package com.androidquest.weathermate.screen


import androidx.compose.foundation.layout.Arrangement
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
import com.androidquest.weathermate.components.CurrentWeatherCard
import com.androidquest.weathermate.components.ForecastRow
import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.model.DailyForecast


@Composable
fun WeatherScreen1(
    current: CurrentWeather = CurrentWeather(
        city = "Lisboa",
        temperature = 22,
        description = "Parcialmente nublado",
        max = 24,
        min = 17
    ),
    forecast: List<DailyForecast> = listOf(
        DailyForecast("Ter", "Sol", 24, 17),
        DailyForecast("Qua", "Nuvens", 23, 16),
        DailyForecast("Qui", "Chuva ligeira", 20, 15),
        DailyForecast("Sex", "Sol", 25, 18)
    )
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Weather Mate",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 0.dp, bottom = 8.dp)
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

 val CardColor = Color(0xFFE6E0E9)

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun WeatherScreenPreview() {
    MaterialTheme {
        WeatherScreen1()
    }
}