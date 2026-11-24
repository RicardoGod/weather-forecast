package com.androidquest.weathermate.data.mock

import com.androidquest.weathermate.data.viewModel.WeatherRepository
import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.model.DailyForecast
import com.androidquest.weathermate.model.WeatherUiModel


/**
 * Dados locais fictícios (sem API).
 * Os composables não conhecem estes dados: recebem-nos pelo ViewModel.
 */
object MockWeatherData : WeatherRepository {

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

    val coimbra = WeatherUiModel(
        city = "Coimbra",
        temperature = 20,
        condition = "Sol",
        high = 23,
        low = 13,
        forecast = listOf(
            DailyForecast("Ter", "Sol", 23, 13),
            DailyForecast("Qua", "Sol", 24, 14),
            DailyForecast("Qui", "Nuvens", 21, 12),
            DailyForecast("Sex", "Chuva", 18, 11)
        )
    )

    val faro = WeatherUiModel(
        city = "Faro",
        temperature = 24,
        condition = "Sol",
        high = 26,
        low = 18,
        forecast = listOf(
            DailyForecast("Ter", "Sol", 26, 18),
            DailyForecast("Qua", "Sol", 27, 19),
            DailyForecast("Qui", "Sol", 25, 18),
            DailyForecast("Sex", "Nuvens", 24, 17)
        )
    )

    val cities = listOf(lisbon, porto, coimbra, faro)

    val lisbonCurrent: CurrentWeather = lisbon.toCurrentWeather()

    override suspend fun getCurrentWeather(city: String): CurrentWeather =
        findCity(city).toCurrentWeather()

    override suspend fun getForecast(city: String): List<DailyForecast> =
        findCity(city).forecast

    private fun findCity(city: String): WeatherUiModel =
        cities.firstOrNull { it.city.equals(city, ignoreCase = true) }
            ?: throw IllegalArgumentException("Cidade não encontrada: $city")

    private fun WeatherUiModel.toCurrentWeather() = CurrentWeather(
        city = city,
        temperature = temperature,
        description = condition,
        max = high,
        min = low
    )
}