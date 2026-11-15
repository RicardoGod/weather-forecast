package com.androidquest.weathermate.data.mock

import com.androidquest.weathermate.model.DailyForecast
import com.androidquest.weathermate.model.WeatherUiModel

object MockWeatherData {

    val lisbon = WeatherUiModel(
        city = "Lisboa",
        temperature = 22,
        condition = "Parcialmente nublado",
        high = 24,
        low = 17,
        forecast = listOf(
            DailyForecast("Ter", "Sol", 24, 17),
            DailyForecast("Qua", "Nuvens", 23, 16),
            DailyForecast("Qui", "Chuva ligeira", 20, 15),
            DailyForecast("Sex", "Sol", 25, 18)
        )
    )

    val porto = WeatherUiModel(
        city = "Porto",
        temperature = 18,
        condition = "Nublado",
        high = 20,
        low = 14,
        forecast = listOf(
            DailyForecast("Ter", "Nuvens", 20, 14),
            DailyForecast("Qua", "Chuva", 17, 12),
            DailyForecast("Qui", "Sol", 21, 15),
            DailyForecast("Sex", "Sol", 22, 16)
        )
    )

    val cities = listOf(
        lisbon,
        porto
    )
}
