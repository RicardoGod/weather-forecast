package com.androidquest.weathermate.screen


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ---------- Modelos ----------

data class CurrentWeather(
    val city: String,
    val temperature: Int,
    val description: String,
    val max: Int,
    val min: Int
)

data class DailyForecast(
    val day: String,
    val condition: String,
    val max: Int,
    val min: Int
)

// ---------- Activity ----------

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                WeatherScreen()
//            }
//        }
//    }
//}

// ---------- Ecrã ----------

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

// ---------- Cartão principal ----------

@Composable
fun CurrentWeatherCard(weather: CurrentWeather) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = weather.city,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "${weather.temperature}°",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = androidx.compose.ui.unit.TextUnit(
                        72f,
                        androidx.compose.ui.unit.TextUnitType.Sp
                    )
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = weather.description,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Text(
                    text = "Máx. ${weather.max}°",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Mín. ${weather.min}°",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// ---------- Linha de previsão ----------

@Composable
fun ForecastRow(forecast: DailyForecast) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = forecast.day,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = forecast.condition,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "${forecast.max}° / ${forecast.min}°",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private val CardColor = Color(0xFFE6E0E9)

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun WeatherScreenPreview() {
    MaterialTheme {
        WeatherScreen1()
    }
}