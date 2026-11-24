package com.androidquest.weathermate.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.screen.CardColor

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
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp),
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
