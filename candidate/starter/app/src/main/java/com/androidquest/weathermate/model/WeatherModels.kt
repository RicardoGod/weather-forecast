package com.androidquest.weathermate.model

data class DailyForecast(
    val day: String,
    val condition: String,
    val maxTemperature: Int,
    val minTemperature: Int
)

data class WeatherUiModel(
    val city: String,
    val temperature: Int,
    val condition: String,
    val high: Int,
    val low: Int,
    val forecast: List<DailyForecast>
)
